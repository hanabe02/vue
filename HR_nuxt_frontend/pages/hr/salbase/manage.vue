<script lang="ts" setup>
import SalBaseManageAddModal from '@/components/hr/salary/SalBaseManageAddModal.vue'
import SalBaseManageEditModal from '@/components/hr/salary/SalBaseManageEditModal.vue'
import { paginationMeta } from '@/server/utils/paginationMeta'
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



const selecteddeptCode = ref('')
const deptList: any = ref([])
const baseSalaryList = ref([])
const posList = ref([])

const options = ref({ page: 1, itemsPerPage: 5, sortBy: [''], sortDesc: [false] })

const headers = [
  { title: '부서명', key: 'deptName', align: 'center' },
  { title: '직급 번호', key: 'positionCode', align: 'center' },
  { title: '직급명', key: 'positionName', align: 'center' },
  { title: '기본급', key: 'baseSalary', align: 'center' },
  { title: '호봉인상률', key: 'hobongRatio', align: 'center' },
]

const fetchData = async () => {
  await salaryStore().SET_DIALOG_BTN(false)
  await baseStore().FETCH_DEPTLIST()
  await baseStore().FETCH_POSLIST()

  const deptListdata = await baseStore().deptList

  deptList.value = deptListdata
    ? Object.values(deptListdata).map(item => {
      return { title: item.deptName, value: item.deptCode }
    })
    : []
  posList.value = baseStore().posList
}

const fetchData2 = async () => {
  if (selecteddeptCode.value === '' || selecteddeptCode.value === null) {
    baseSalaryList.value = []
  }
  else {
    await salaryStore().FETCH_BASE_SALARY(selecteddeptCode.value)
    baseSalaryList.value = salaryStore().baseSalaryList
  }
}

const addBaseSalModal = async () => {
  console.log(posList.value)

  const posListData = posList.value
    ? Object.values(posList.value).map(item => {
      return { title: item.positionName, value: item.positionCode }
    })
    : []

  await salaryStore().SET_POS_LIST(posListData)
  await salaryStore().SET_DEPT_LIST(deptList._rawValue)
  await salaryStore().SET_DIALOG_BTN(true)
}

const handleRowClick = async row => {
  const rowData = {
    deptName: row.item.deptName,
    deptCode: row.item.deptCode,
    positionName: row.item.positionName,
    baseSalary: row.item.baseSalary,
    hobongRatio: row.item.hobongRatio,
    positionCode: row.item.positionCode,
  }

  await salaryStore().SET_ROW_LIST(rowData)
  await salaryStore().SET_DIALOG(true)
}

watch([selecteddeptCode], fetchData2, { immediate: true })
onBeforeMount(fetchData)
</script>

<template>
  <h1 class="mb-6">기준급여관리</h1>
  <VCard class="mb-6">
    <VCardText>
      <VRow>
        <VCol cols="12" sm="4">
          <AppSelect v-model="selecteddeptCode" label="부서" :items="deptList" clearable clear-icon="tabler-x" />
        </VCol>
      </VRow>
    </VCardText>
  </VCard>

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
        <VBtn prepend-icon="tabler-plus" @click="addBaseSalModal">
          급여 기준 추가
        </VBtn>
      </div>
    </VCardText>
    <VDivider />
    <VDataTable :headers="headers" :items="baseSalaryList" :items-per-page="options.itemsPerPage" :page="options.page"
      :options="options" @click:row="(_, row) => handleRowClick(row)">
      <template #bottom>
        <VDivider />
        <VCardText class="pt-2">
          <div class="d-flex flex-wrap justify-center justify-sm-space-between gap-y-2 mt-2">
            <p class="text-sm text-disabled mb-0">
              {{ paginationMeta({ page: options.page, itemsPerPage: options.itemsPerPage }, baseSalaryList.length) }}
            </p>
            <VPagination v-model="options.page" :total-visible="$vuetify.display.smAndDown ? 3 : 5"
              :length="Math.ceil(baseSalaryList.length / options.itemsPerPage)" />
          </div>
        </VCardText>
      </template>
    </VDataTable>
  </VCard>
  <SalBaseManageEditModal :fetch-data2="fetchData2" />
  <SalBaseManageAddModal :fetch-data2="fetchData2" />
</template>
