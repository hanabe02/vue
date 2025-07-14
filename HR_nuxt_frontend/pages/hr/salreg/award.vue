<script lang="ts" setup>
import { paginationMeta } from '@/server/utils/paginationMeta'
import { affairStore } from '@/store/hr/affair'
import { attenStore } from '@/store/hr/attendance'
import { baseStore } from '@/store/hr/base'
import { salaryStore } from '@/store/hr/salary'
import { VDataTable } from 'vuetify/labs/VDataTable'
import { useRouter } from "vue-router"; // router 가져오기
import { inject } from 'vue';

const router = useRouter();
import { useEmpStore } from '@/store/hr/emp'

const empStore = useEmpStore()
empStore.loadEmpCode()

console.log("현재 로그인된 직원 코드:", empStore.empCode)

// 로그인된 empCode가 'EMP-01'이 아닌 경우 페이지 이동
if (empStore.empCode !== 'EMP-01' && empStore.empCode !== 'EMP-00') {
  router.push('/hr/confinement/unauthorized')
}


const isSuccessFunc = ref(false)
const isinputPersonalBonus = ref(false)
const isinputDetpBonus = ref(false)
const selectedEmp = ref('')
const selectedRatio = ref('')
const selectedDept = ref('')
const selectedRows = ref([])
const deptlistDetail: any = ref([])
const bonusList = ref([])
const empList: any = ref([])

const today = ref('')
const lastMonth = ref('')
const isDialogVisible1 = ref(false)
const isDialogVisible2 = ref(false)

const headers = [
  { title: '해당 년월', key: 'applyYearMonth', align: 'center' },
  { title: '사원 명', key: 'empName', align: 'center' },
  { title: '지급액', key: 'bonus', align: 'center' },
  { title: '분류', key: 'detailCodeName', align: 'center' },
  { title: '승인상태', key: 'finalizeStatus', align: 'center' },
]

const percent = [
  { title: '100%', value: 1 },
  { title: '200%', value: 2 },
  { title: '300%', value: 3 },
  { title: '400%', value: 4 },
  { title: '500%', value: 5 },
]

const findBonus = async () => {
  const date = new Date()

  today.value = `${date.getFullYear()}-${date.getMonth() + 1}`
  lastMonth.value = `${date.getFullYear()}-${date.getMonth()}`

  const initData = {
    applyYearMonth: today.value,
    empCode: '%',
    detailCodeName: '성과급',
    finalizeStatus: '%',
  }

  await salaryStore().FIND_BONUS(initData)
  bonusList.value = await salaryStore().bonusList
}

const findEmpList = async () => {
  await affairStore().FETCH_ALL_EMP()

  const empListData = await affairStore().all_emp_list

  empList.value = await empListData
    ? Object.values(empListData).map(item => {
      return { title: item.empName, value: { empCode: item.empCode, empName: item.empName } }
    })
    : []
}

const findListDetail = async () => {
  await baseStore().FETCH_DEPTLIST_DETAIL()

  const empDeptListDetail = await baseStore().deptlistDetail

  deptlistDetail.value = await empDeptListDetail
    ? Object.values(empDeptListDetail).map(item => {
      return { title: item.deptName, value: { empCode: item.deptCode, empName: item.empName } }
    })
    : []
}

const inputPersonalBonus = async () => {
  if (selectedEmp.value === '' || selectedRatio.value === ''
    || selectedEmp.value === null || selectedRatio.value === null) {
    isinputPersonalBonus.value = true
  }
  else {
    const salData = {
      applyYearMonth: lastMonth.value,
      empCode: selectedEmp.value.empCode,
    }

    await salaryStore().SEARCH_SALARY_INIT(salData)

    const newPersonalBonus = {
      applyYearMonth: today.value,
      empName: selectedEmp.value.empName,
      empCode: selectedEmp.value.empCode,
      bonus: salaryStore().initSalaryList[0].salary * selectedRatio.value,
      bonusCode: 'BON002',
      detailCodeName: '성과급',
      finalizeStatus: 'N',
    }

    selectedEmp.value = ''
    selectedRatio.value = ''

    await salaryStore().ADD_BONUS(newPersonalBonus)
    await findBonus()
    isDialogVisible1.value = false
    isSuccessFunc.value = true
  }
}

const inputDetpBonus = async () => {
  if (selectedDept.value === '' || selectedRatio.value === ''
    || selectedDept.value === null || selectedRatio.value === null) {
    isinputDetpBonus.value = true
  }
  else {
    await attenStore().FETCH_EMPLIST_BY_DEPT(selectedDept.value.empCode)

    for (const e of attenStore().empList) {
      const salDate = {
        applyYearMonth: lastMonth.value,
        empCode: e.empCode,
      }

      await salaryStore().SEARCH_SALARY_INIT(salDate)

      const newPersonalBonus = {
        applyYearMonth: today.value,
        empName: e.empName,
        empCode: e.empCode,
        bonus: salaryStore().initSalaryList[0].salary * selectedRatio.value,
        bonusCode: 'BON002',
        detailCodeName: '성과급',
        finalizeStatus: 'N',
      }

      await salaryStore().ADD_BONUS(newPersonalBonus)
      isSuccessFunc.value = true
    }

    await findBonus()
    isDialogVisible2.value = false
  }
}

const deleteBonus = () => {
  selectedRows.value.forEach(e => {
    const deleteBonusData = {
      empCode: e,
      applyYearMonth: today.value,
      bonusCode: 'BON002',
    }

    salaryStore().DELETE_BONUS(deleteBonusData).then(() => findBonus())
  })
  isDialogVisible1.value = false
  isSuccessFunc.value = true
}

onBeforeMount(() => {
  findBonus()
  findEmpList()
  findListDetail()
})

const options = ref({ page: 1, itemsPerPage: 5, sortBy: [''], sortDesc: [false] })
</script>

<template>
  <h1 class="mb-6">성과급 등록</h1>
  <VCard>
    <VCardText class="d-flex flex-wrap py-4 gap-4">
      <div class="me-3 d-flex gap-3">
        <AppSelect :model-value="options.itemsPerPage" :items="[
          { value: 5, title: '5' },
          { value: 10, title: '10' },
          { value: 25, title: '25' },
          { value: 50, title: '50' },
          { value: -1, title: 'All' },
        ]" style="inline-size: 6.25rem;" @update:model-value="options.itemsPerPage = parseInt($event, 10)" />
      </div>
      <VSpacer />

      <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
        <VBtn prepend-icon="tabler-plus" @click="isDialogVisible1 = true">
          성과금 등록(개인)
        </VBtn>
        <VBtn prepend-icon="tabler-plus" @click="isDialogVisible2 = true">
          성과금 등록(부서)
        </VBtn>
        <VBtn prepend-icon="tabler-minus" @click="deleteBonus">
          성과금 삭제
        </VBtn>
      </div>
    </VCardText>
    <VDivider />
    <VDataTable v-model="selectedRows" :headers="headers" :items="bonusList" :items-per-page="options.itemsPerPage"
      :page="options.page" :options="options" item-value="empCode" show-select>
      <template #bottom>
        <VDivider />
        <VCardText class="pt-2">
          <div class="d-flex flex-wrap justify-center justify-sm-space-between gap-y-2 mt-2">
            <p class="text-sm text-disabled mb-0">
              {{ paginationMeta({ page: options.page, itemsPerPage: options.itemsPerPage }, bonusList.length) }}
            </p>
            <VPagination v-model="options.page" :total-visible="$vuetify.display.smAndDown ? 3 : 5"
              :length="Math.ceil(bonusList.length / options.itemsPerPage)" />
          </div>
        </VCardText>
      </template>
    </VDataTable>
  </VCard>
  <VDialog v-model="isDialogVisible1" max-width="600">
    <!-- Dialog close btn -->
    <DialogCloseBtn @click="isDialogVisible1 = !isDialogVisible1" />

    <!-- Dialog Content -->
    <VCard title="성과금 등록(개인)">
      <VCardText>
        <VRow>
          <VCol cols="12" sm="6">
            <AppSelect v-model="selectedEmp" :items="empList" label="사원명" clearable clear-icon="tabler-x" />
          </VCol>
          <VCol cols="12" sm="6">
            <AppSelect v-model="selectedRatio" :items="percent" label="배율" clearable clear-icon="tabler-x" />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3">
        <VBtn variant="tonal" color="secondary" @click="isDialogVisible1 = false">
          Close
        </VBtn>
        <VBtn @click="inputPersonalBonus">
          Save
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
  <VDialog v-model="isDialogVisible2" max-width="600">
    <!-- Dialog close btn -->
    <DialogCloseBtn @click="isDialogVisible2 = !isDialogVisible2" />

    <!-- Dialog Content -->
    <VCard title="성과금 등록(부서)">
      <VCardText>
        <VRow>
          <VCol cols="12" sm="6">
            <AppSelect v-model="selectedDept" :items="deptlistDetail" label="부서명" clearable clear-icon="tabler-x" />
          </VCol>
          <VCol cols="12" sm="6">
            <AppSelect v-model="selectedRatio" :items="percent" label="배율" clearable clear-icon="tabler-x" />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3 mt-2">
        <VBtn variant="tonal" color="secondary" @click="isDialogVisible2 = false">
          Close
        </VBtn>
        <VBtn @click="inputDetpBonus">
          Save
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
  <VSnackbar v-model="isinputPersonalBonus" location="top" :timeout="3000" color="warning">
    <VAlert density="compact" type="warning" title="warning!" text="사원명, 배율을 선택해주세요." />
  </VSnackbar>
  <VSnackbar v-model="isinputDetpBonus" location="top" :timeout="3000" color="warning">
    <VAlert density="compact" type="warning" title="warning!" text="부서명, 배율을 선택해주세요." />
  </VSnackbar>
  <VSnackbar v-model="isSuccessFunc" location="top" :timeout="3000" color="success">
    <VAlert density="compact" type="success" title="success!" text="완료되었습니다." />
  </VSnackbar>
</template>
