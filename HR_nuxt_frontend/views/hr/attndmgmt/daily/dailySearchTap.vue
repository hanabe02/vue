<script lang="ts" setup>
import { attenStore } from '@/store/hr/attendance';
import { baseStore } from '@/store/hr/base';
import type { AttenHeaders, DeptItemProps, EmpItemProps } from '@/types/attendance/types';
import { VDataTable } from 'vuetify/labs/VDataTable';

const selectedSearchType = ref('')
const selectedDeptCode = ref('')
const selectedEmpCode = ref('')
const selectedStatus = ref('')
const dept_list: any = ref([])
const emp_list_by_dept: any = ref([])
const day_attd_list: any = ref([])
const startDate = ref('')
const endDate = ref('')
const updatedRows = ref({})
const rejectedRows = ref({});
const selectedRows = ref([]);

const headers: AttenHeaders[] = [
  { key: 'applyDays', title: '날짜', width: 120 },
  { key: 'empName', title: '사원명', width: 120 },
  { key: 'dayAttdName', title: '구분', width: 120 },
  { key: 'attendTime', title: '시작시간', width: 120 },
  { key: 'quitTime', title: '종료시간', width: 120 },
  { key: 'workHour', title: '근무시간', width: 120 },
  { key: 'halfHoildayStatus', title: '반차여부', width: 120 },
  { key: 'lateWhether', title: '지각여부', width: 120 },
  { key: 'overWorkHour', title: '연장근무', width: 120 },
  { key: 'nightWorkHour', title: '심야근무', width: 120 },
  { key: 'earlyLeaveHour', title: '조퇴시간', width: 120 },
  { key: 'leaveHour', title: '외출시간', width: 120 },
  { key: 'finalizeStatus', title: '승인여부', width: 120 },
]

const searchType = ['전체사원조회', '선택사원조회']

const applyStatusType = ['ALL', 'Y', 'N']

// 페이지가 로드되자마자 실행
onMounted(() => {
  fetchDeptList()
})

// 부서코드가 변경될때마다 함수 실행
watch(selectedDeptCode, async () => {
  await fetchEmpList()
})

// 부서리스트 조회
const fetchDeptList = async () => {
  await baseStore().FETCH_DEPTLIST()
  dept_list.value = Object.values(baseStore().deptList).map((item: DeptItemProps) => {
    return { title: item.deptName, value: item.deptCode }
  })
}

// 부서의 사원 조회
const fetchEmpList = async () => {
  await attenStore().FETCH_EMPLIST_BY_DEPT(selectedDeptCode.value)
  emp_list_by_dept.value = Object.values(attenStore().empList).map((item: EmpItemProps) => {
    return { title: item.empName, value: item.empCode }
  })
}

// 사원 조회
const searchByType = async () => {
  if (selectedSearchType.value === '전체사원조회') {
    await attenStore().SEARCH_DAYATTDMGT_BY_STATUS({
      startDate: startDate.value, endDate: endDate.value, applyStatus: selectedStatus.value,
    })
    day_attd_list.value = attenStore().dayAttdMgtList
    console.log('ALL[day_attd_list.value]', day_attd_list.value)
  }
  else if (selectedSearchType.value === '선택사원조회') {
    await attenStore().SEARCH_DAYATTDMGT_BY_STATUS_BY_EMP_CODE({
      startDate: startDate.value, endDate: endDate.value, applyStatus: selectedStatus.value, empCode: selectedEmpCode.value,
    })
    day_attd_list.value = attenStore().dayAttdMgtList
    console.log('EMP[day_attd_list.value]', day_attd_list.value)
  }
}

// 근태 승인/취소
const updateStatus = async (title: string) => {
  switch (title) {
    case '근태승인':
      console.log('근태승인 클릭')
      console.log('selectedRows', selectedRows.value)
      updatedRows.value = selectedRows.value.map((row: any) => {
        return { ...row, status: 'update' };
      });
      console.log('updatedRows', updatedRows.value)
      await attenStore().UPDATE_DAY_ATTDMGT_APPLYSTATUS(updatedRows.value);
      alert('근태 승인 완료되었습니다.')
      searchByType()
      break;

    case '승인취소':
      console.log('근태취소 클릭')
      console.log('selectedRows', selectedRows.value)
      rejectedRows.value = selectedRows.value.map((row: any) => {
        return { ...row, status: 'cancel' };
      });
      console.log('rejectedRows', rejectedRows.value)
      await attenStore().UPDATE_DAY_ATTDMGT_APPLYSTATUS(rejectedRows.value);
      alert('승인 취소 완료되었습니다.')
      searchByType()
      break;

    default:
      break
  }
}

// 선택한 행의 데이터를 가져옴, 행하나 가져오는 함수
// const getRowData = (event: any, row: any) => {
//   selectedRow.value = row.item
//   console.log('[selectedRow]', row.item)
//   console.log('[applyDays]', row.item.applyDaysFormat)
//   selectedApplyDay.value = row.item.applyDaysFormat
// }
</script>

<template>
  <VContainer>
    <VRow>
      <VCol cols="12" md="2" offset-md="6">
        <VBtn class="mt-6" height="43" width="150" @click="searchByType">
          <VIcon start icon="tabler-search" />현황 조회
        </VBtn>
      </VCol>
      <VCol cols="12" md="2">
        <VBtn class="mt-6" height="43" width="150" @click="updateStatus('근태승인')">
          <VIcon start icon="tabler-square-key" />근태 승인
        </VBtn>
      </VCol>
      <VCol cols="12" md="2">
        <VBtn class="mt-6" height="43" width="150" @click="updateStatus('승인취소')">
          <VIcon start icon="tabler-square-x" />승인 취소
        </VBtn>
      </VCol>
    </VRow>
    <VRow class="mt-6">
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedSearchType" :items="searchType" label="조회선택타입" placeholder="조회선택타입" clearable
          clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedDeptCode" :items="dept_list" label="부서조회" placeholder="부서조회" clearable
          :disabled="selectedSearchType === '전체사원조회'" clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedEmpCode" :items="emp_list_by_dept" label="사원조회" placeholder="사원조회" clearable
          :disabled="selectedSearchType === '전체사원조회'" clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="startDate" label="시작일" placeholder="시작일" />
      </VCol>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="endDate" label="종료일" placeholder="종료일" />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedStatus" :items="applyStatusType" label="승인상태" placeholder="승인상태" clearable
          clear-icon="tabler-x" />
      </VCol>
    </VRow>
    <VRow class="mt-6">
      <VCol>
        <VDataTable :headers="headers" :items="day_attd_list" :items-per-page="10" show-select return-object
          v-model="selectedRows" item-value="empCode" />
      </VCol>
    </VRow>
  </VContainer>
</template>

