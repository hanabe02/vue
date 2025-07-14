<script lang="ts" setup>
import { onMounted } from '#imports'
import { baseStore } from '@/store/hr/base'
import { dailyLaborStore } from '@/store/hr/dailylabor'
import type { DeptList, EmployeData, ProjectInfo, ProjectList } from '@/types/base/types'
import type { DailyLaborEmp, DailyManageRowType, Headers, Select } from '@/types/dailylabor/types'
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


const isfalidclick = ref(false)

const isfaildinsert = ref(false)
const isSuccessFunc = ref(false)
const search = ref('')
const BaseStore = baseStore()
const DailyLaborStore = dailyLaborStore()
const fieldDisabled = ref(false)
const dailyLaborEmpList = ref<DailyLaborEmp[]>([])
const deptList = ref<DeptList[]>([])
const projectList = ref<ProjectList[]>([])

const nationality: Select[] = [
  { value: '내국인', title: '내국인' },
  { value: '외국인', title: '외국인' },
]

const normalPayType: Select[] = [
  { value: '일급', title: '일급' },
  { value: '주급', title: '주급' },
  { value: '월급', title: '월급' },
]

const detailDailyLaborEmp = ref<DailyLaborEmp>({
  employeeId: '',
  employeeName: '',
  residentialNumber: '',
  nationality: '',
  department: '',
  project: '',
  workStartDate: '',
  workEndDate: '',
  employmentInsuranceEligibility: false,
  normalPayType: '',
  normalPay: 0,
  overtimePayRate: 0,
  nightPayRate: 0,
  bankName: '',
  accountNumber: '',
  accountHolder: '',
  phoneNumber: '',
  email: '',
  status: '',
})

const headers: Headers[] = [
  { title: '사원번호', key: 'employeeId', align: 'center' },
  { title: '성명', key: 'employeeName', align: 'center' },
  { title: '주민등록번호', key: 'residentialNumber', align: 'center' },
]

const fetchData = async () => {
  await DailyLaborStore.SEARCH_EMP_LIST()
  dailyLaborEmpList.value = await DailyLaborStore.dailyLaborEmpList

  await BaseStore.FETCH_DEPTLIST()
  deptList.value = await BaseStore.deptList.map((e: EmployeData) => ({
    title: e.deptName,
    value: e.deptCode,
  }))

  await BaseStore.FETCH_PROJECT()
  projectList.value = await BaseStore.projectList.map((e: ProjectInfo) => ({
    title: e.projectName,
    value: e.projectName,
  }))
}

const resetDetailDailyLaborEmpList = () => {
  fieldDisabled.value = false
  detailDailyLaborEmp.value = {
    employeeId: '',
    employeeName: '',
    residentialNumber: '',
    nationality: '',
    department: '',
    project: '',
    workStartDate: '',
    workEndDate: '',
    employmentInsuranceEligibility: false,
    normalPayType: '',
    normalPay: '',
    overtimePayRate: '',
    nightPayRate: '',
    bankName: '',
    accountNumber: '',
    accountHolder: '',
    phoneNumber: '',
    email: '',
    status: '',
  }
}

const handleRowClick = async (row: DailyManageRowType) => {
  fieldDisabled.value = true
  await DailyLaborStore.FETCH_EMP_DETAIL(row.item.employeeId)

  detailDailyLaborEmp.value = await DailyLaborStore.dailyLaborEmpDetailInfo
  if (detailDailyLaborEmp.value.employmentInsuranceEligibility === 'Y')
    detailDailyLaborEmp.value.employmentInsuranceEligibility = true
  else detailDailyLaborEmp.value.employmentInsuranceEligibility = false
}

const updateEmpList = async () => {
  if (detailDailyLaborEmp.value.employmentInsuranceEligibility === true)
    detailDailyLaborEmp.value.employmentInsuranceEligibility = 'Y'
  else detailDailyLaborEmp.value.employmentInsuranceEligibility = 'N'
  const targetEmployeeId = detailDailyLaborEmp.value.employeeId
  const employeeIds = dailyLaborEmpList.value.map(employee => employee.employeeId)
  const isEmployeeIdExists = employeeIds.includes(targetEmployeeId)
  if (!fieldDisabled.value) {
    if (!isEmployeeIdExists)
      isfalidclick.value = true

    else isfalidclick.value = true
  }
  else if (isEmployeeIdExists) {
    await DailyLaborStore.DELETE_EMP(detailDailyLaborEmp.value)
    await fetchData()
    isSuccessFunc.value = true
  }
}

const insertEmpList = async () => {
  if (detailDailyLaborEmp.value.employmentInsuranceEligibility === true)
    detailDailyLaborEmp.value.employmentInsuranceEligibility = 'Y'
  else detailDailyLaborEmp.value.employmentInsuranceEligibility = 'N'
  const targetEmployeeId = detailDailyLaborEmp.value.employeeId
  const employeeIds = dailyLaborEmpList.value.map(employee => employee.employeeId)
  const isEmployeeIdExists = employeeIds.includes(targetEmployeeId)
  if (!fieldDisabled.value) {
    if (!isEmployeeIdExists) {
      await DailyLaborStore.INSERT_EMP(detailDailyLaborEmp.value)
      await fetchData()
      isSuccessFunc.value = true
    }
    isfaildinsert.value = true
  }
  else if (isEmployeeIdExists) {
    await DailyLaborStore.UPDATE_EMP(detailDailyLaborEmp.value)
    isSuccessFunc.value = true
  }
}

const filteredData = computed(() => {
  return dailyLaborEmpList.value.filter((emp: any) => {
    return Object.values(emp).some(field => {
      if (typeof field === 'string')
        return field.toLowerCase().includes(search.value.toLowerCase())

      return false
    })
  })
})

onMounted(fetchData)
</script>

<template>
  <h1 class="mb-6">
    일용근로 직원관리
  </h1>
  <VCard class="mb-6">
    <VCardText>
      <VRow>
        <VCol cols="12" sm="4">
          <AppTextField v-model="search" density="compact" label="검색" append-inner-icon="tabler-search" single-line
            hide-details dense outlined />
        </VCol>
      </VRow>
    </VCardText>
  </Vcard>
  <VRow>
    <VCol md="4">
      <VCard class="mb-6" height="750" title="일용직 사원 목록">
        <VDivider />
        <VCardText>
          <VDataTable :headers="headers" :items="filteredData" :items-per-page="10"
            @click:row="(_, row) => handleRowClick(row)" />
        </VCardText>
      </VCard>
    </VCol>

    <VCol md="8">
      <VCard class="mb-6" height="750" title="기본정보">
        <VDivider class="pt-2" />
        <VContainer>
          <VRow class="pt-2">
            <VCol v-if="fieldDisabled" sm="4" cols="10">
              <AppTextField v-model="detailDailyLaborEmp.employeeId" label="사원 번호" placeholder="LAB" disabled />
            </VCol>
            <VCol v-else sm="4" cols="10">
              <AppTextField v-model="detailDailyLaborEmp.employeeId" label="사원 번호" placeholder="LAB" />
            </VCol>
            <VCol sm="4" cols="10">
              <AppTextField v-model="detailDailyLaborEmp.employeeName" label="성명" placeholder="홍길동" />
            </VCol>
            <VCol sm="4" cols="10">
              <AppTextField v-model="detailDailyLaborEmp.residentialNumber" label="주민등록번호" placeholder="123456-1234567" />
            </VCol>
            <VCol sm="4" cols="10">
              <AppSelect v-model="detailDailyLaborEmp.nationality" :items="nationality" label="내/외국인" />
            </VCol>

            <VCol sm="4" cols="10">
              <AppSelect v-model="detailDailyLaborEmp.department" :items="deptList" label="부서" />
            </VCol>

            <VCol sm="4" cols="10">
              <AppSelect v-model="detailDailyLaborEmp.project" label="프로젝트" :items="projectList" />
            </VCol>
          </VRow>
          <VRow>
            <VCol sm="4" cols="10">
              <AppDateTimePicker v-model="detailDailyLaborEmp.workStartDate" label="근무시작일" />
            </VCol>

            <VCol sm="4" cols="10">
              <AppDateTimePicker v-model="detailDailyLaborEmp.workEndDate" label="근무종료일" />
            </VCol>
            <VCol sm="4" cols="10" class="d-flex align-center">
              <VCheckbox v-model="detailDailyLaborEmp.employmentInsuranceEligibility" label="보험대상여부" />
            </VCol>
          </VRow>
          <VRow>
            <VCol sm="4" cols="10">
              <AppSelect v-model="detailDailyLaborEmp.normalPayType" :items="normalPayType" label="정상급여유형" />
            </VCol>

            <VCol sm="4" cols="10">
              <AppTextField v-model="detailDailyLaborEmp.normalPay" label="정상급여" placeholder="0" />
            </VCol>
            <VCol sm="4" cols="10">
              <AppTextField v-model="detailDailyLaborEmp.overtimePayRate" label="연장급여(시급)" placeholder="0" />
            </VCol>
            <VCol sm="4" cols="10">
              <AppTextField v-model="detailDailyLaborEmp.nightPayRate" label="야간급여(시급)" placeholder="0" />
            </VCol>
            <VCol sm="4" cols="10">
              <AppSelect v-model="detailDailyLaborEmp.bankName" :items="['하나', '신한', '농협', '경남']" label="은행" />
            </vcol>
            <VCol sm="4" cols="10">
              <AppTextField v-model="detailDailyLaborEmp.accountNumber" label="계좌번호" />
            </vcol>
            <VCol sm="4" cols="10">
              <AppTextField v-model="detailDailyLaborEmp.accountHolder" label="예금주" />
            </vcol>
            <VCol sm="4" cols="10">
              <AppTextField v-model="detailDailyLaborEmp.phoneNumber" label="핸드폰" placeholder="010-1234-5678" />
            </vcol>
            <VCol sm="4" cols="10">
              <AppTextField v-model="detailDailyLaborEmp.email" label="이메일" placeholder="seoulit@seoulit.com" />
            </vcol>
          </VRow>
          <VDivider class="mt-10 mb-6" />
          <div class="d-flex align-center justify-end flex-wrap gap-4">
            <VBtn @click="resetDetailDailyLaborEmpList">
              신규
            </VBtn>
            <VBtn @click="updateEmpList">
              마감
            </VBtn>
            <VBtn @click="insertEmpList">
              저장
            </VBtn>
          </div>
        </VContainer>
      </VCard>
    </VCol>
  </VRow>
  <!-- 급해서 -->
  <VSnackbar v-model="isfalidclick" location="top" :timeout="3000" color="warning">
    <VAlert density="compact" type="warning" title="warning!" text="사원 클릭하여 마감처리바랍니다." />
  </VSnackbar>
  <VSnackbar v-model="isfaildinsert" location="top" :timeout="3000" color="warning">
    <VAlert density="compact" type="warning" title="warning!" text="존재하는 사원 번호입니다." />
  </VSnackbar>
  <VSnackbar v-model="isSuccessFunc" location="top" :timeout="3000" color="success">
    <VAlert density="compact" type="success" title="success!" text="완료되었습니다." />
  </VSnackbar>
</template>
