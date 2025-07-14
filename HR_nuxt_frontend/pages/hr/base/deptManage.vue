<script setup lang="ts">
import { affairStore } from '@/store/hr/affair';
import { baseStore } from '@/store/hr/base';
import { VDataTable } from 'vuetify/labs/VDataTable';
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



const deptlist = ref([])
const isDialogVisible = ref(false)
const workplaceList = ref([])
const deptName = ref('')
const tab = ref('personal-info')
const selectedWorkplace = ref('')
const today = ref('')

const daySetting = () => {
  const date = new Date()
  let month = date.getMonth() + 1
  let day = date.getDate()
  if (month.toString().length === 1) { month = `0${month}` };
  if (day.toString().length === 1)
    day = `0${day}`
  today.value = `${date.getFullYear()}-${month}-${day}`
}

const fetchData = async () => {
  try {
    await baseStore().FIND_DEPT_MANAGE_LIST()
    deptlist.value = [...baseStore().deptManageList] // 배열을 복사하여 새로운 참조로 업데이트
  }
  catch (error) {
    console.error('데이터 불러오기 중 에러:', error)
  }
}

const headers = [
  { title: '사업자명', key: 'workplaceName' },
  { title: '부서코드', key: 'deptCode' },
  { title: '부서명', key: 'deptName' },
  { title: '책임자', key: 'head' },
  { title: '부서인원', key: 'count' },
  { title: '부서시작일', key: 'deptStartDate' },
]

const fetchWorkplace = async () => {
  await baseStore().FETCH_WORKPLACELIST()

  const workplaceListData = baseStore().workplaceList || {}

  workplaceList.value = Object.values(workplaceListData).map(item => ({
    title: item.workplaceName,
    value: { deptCode: item.workplaceCode, deptName: item.workplaceName },
  }))
}

const saveDepartment = async () => {
  console.log(selectedWorkplace.value.deptName)
  console.log(deptName.value)
  let num = '0'
  const deptList = deptlist.value

  console.log(deptList)
  for (let i = 0; i < deptList.length; i++) {
    const dept = deptList[i]
    const deptCodeNo = dept.deptCode
    const deptNo = deptCodeNo.split('-')[1]
    if (deptNo > num) {
      num = deptNo
    }
    else { }
  }
  const newDeptCode = `DPT-${++num}`

  console.log(newDeptCode)

  const addDeptData = {
    deptCode: newDeptCode,
    deptName: deptName.value,
    workplaceCode: selectedWorkplace.value.deptCode,
    workplaceName: selectedWorkplace.value.deptName,
    companyCode: null,
    deptStartDate: today.value,
  }

  await baseStore().ADD_DEPT(addDeptData)
  await fetchData()
  isDialogVisible.value = false
}

const openModal = async row => {
  await affairStore().FETCH_DEPT_EMP_LIST(row.item.deptCode)

  const data = {
    deptCode: row.item.deptCode,
    deptName: row.item.deptName,
    workplaceCode: row.item.workplaceCode,
    count: row.item.count,
    empList: affairStore().selected_dept_Emp_List,
  }

  baseStore().SET_DEPT_ROW(data)
  baseStore().SET_DIALOG(true)
}

onBeforeMount(() => {
  fetchData()
  fetchWorkplace()
  daySetting()
})
</script>

<template>
  <h1 class="mb-6">
    부서정보관리
  </h1>
  <VCard>
    <VTabs v-model="tab" height="70">
      <VTab value="personal-info">
        부서정보관리
      </VTab>
    </VTabs>
  </VCard>
  <VCard flat>
    <VCardText>
      <VWindow v-model="tab" class="disable-tab-transition">
        <VWindowItem value="personal-info">
          <VDialog v-model="isDialogVisible" max-width="600">
            <template #activator="{ props }">
              <VCol cols="12" md="2" offset-md="10">
                <VBtn v-bind="props" class="ml-md-2" @click="isDialogVisible = true">
                  부서추가
                </VBtn>
              </VCol>
            </template>

            <DialogCloseBtn @click="isDialogVisible = false" />

            <VCard title="부서추가">
              <VCardText>
                <VRow>
                  <VCol cols="12" sm="6">
                    <AppSelect v-model="selectedWorkplace" :items="workplaceList" label="등록할 부서명의 사업장을 선택해주세요" />
                  </VCol>
                  <VCol cols="12" sm="6">
                    <AppTextField v-model="deptName" label="등록할 부서명을 입력해주세요" placeholder="" />
                  </VCol>
                </VRow>
              </VCardText>

              <VCardText class="d-flex justify-end flex-wrap gap-3">
                <VBtn variant="tonal" color="secondary" @click="isDialogVisible = false">
                  Close
                </VBtn>
                <VBtn @click="saveDepartment">
                  Save
                </VBtn>
              </VCardText>
            </VCard>
          </VDialog>
          <MonthTap />
        </VWindowItem>
      </VWindow>
    </VCardText>
  </VCard>
  <VDataTable :headers="headers" :items="deptlist" :items-per-page="5" @click:row="(_, row) => openModal(row)" />
  <DeptModal :fetch-data="fetchData" />
</template>
