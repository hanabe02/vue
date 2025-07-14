<script setup lang="ts">
import { attenStore } from '@/store/hr/attendance';
import { baseStore } from '@/store/hr/base';
import type { AttenProps, DeptItemProps, EmpItemProps, ItemPropsTypes } from '@/types/attendance/types';

const reqDate = ref('')
const start = ref('')
const startDate = ref('')
const startTime = ref('')
const end = ref('')
const endDate = ref('')
const endTime = ref('')
const selectedRest = ref('')
const selectedRestCode = ref('')
const selectedRestName = ref('')
const selectedDeptCode = ref('')
const selectedEmpCode = ref('')
const cause = ref('')
const dept_list: any = ref([])
const emp_list_by_dept: any = ref([])
const today = new Date().toISOString().substring(0, 10)

const eduProps: AttenProps = [
  { text: '출장', value: 'ASC002' },
  { text: '교육', value: 'ASC003' },
]

// select component에 사용되는 함수
const itemProps = (item: ItemPropsTypes) => {
  return {
    title: item.text,
    value: item.value,
  }
}

// 페이지가 로드되자마자 실행
onMounted(() => {
  fetchDeptList()
})

// selectedRest가 변경될때마다 변경된 restProps의 속성들을 restName, restCode로 등록
watch(selectedRest, (newValue, oldValue) => {
  const selectedItem = eduProps.find(item => item.value === newValue)
  if (selectedItem) {
    selectedRestName.value = selectedItem.text
    selectedRestCode.value = selectedItem.value
    console.log('Selected Title:', selectedRestName.value)
    console.log('Selected value:', selectedRestCode.value)
  }
})

// 시작일이 변경될때마다 시작일과 시작시간을 나눔
watch(start, newDateTime => {
  updateDateTime(newDateTime, startDate, startTime)
  console.log('startDate:', startDate.value)
  console.log('startTime:', startTime.value)
})

// 종료일이 변경될때마다 종료일과 종료시간을 나눔
watch(end, newDateTime => {
  updateDateTime(newDateTime, endDate, endTime)
  console.log('endDate:', endDate.value)
  console.log('endTime:', endTime.value)
})

// 날짜을 년월일과 시간으로 나누는 함수
const updateDateTime = (newDateTime: any, newDate: any, newTime: any) => {
  const [date, time] = newDateTime.split(' ')
  const [year, month, day] = date.split('-')

  newDate.value = `${year}-${month}-${day}`
  newTime.value = time
}

// computed를 사용하여 numberOfDays 계산
const numberOfDays = computed(() => {
  if (endDate.value && startDate.value) {
    const diffDate = new Date(endDate.value).getTime() - new Date(startDate.value).getTime()
    const dayDiff = diffDate / (1000 * 60 * 60 * 24) + 1

    if (endDate.value === startDate.value)
      return 0.5
    else
      return dayDiff
  }
  else {
    return 0
  }
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

// 출장/교육 등록
const registerRest = async () => {
  if (reqDate.value === '' || selectedEmpCode.value === '' || selectedRestCode.value === '' || startTime.value === '' || endTime.value === '' || cause.value === '') {
    alert('모든 항목을 선택해주세요.')
  } else {
    attenStore().INSERT_REQUEST_REST_ATTD({
      empCode: selectedEmpCode.value,
      restTypeCode: selectedRestCode.value,
      restTypeName: selectedRestName.value,
      requestDate: reqDate.value,
      startDate: startDate.value,
      endDate: endDate.value,
      cause: cause.value,
      applovalStatus: '승인대기',
      startTime: startTime.value,
      endTime: endTime.value,
      numberOfDays: numberOfDays.value,
    }).then(res => {
      console.log('[출장/교육등록 성공]', res.data)
      alert('신청이 등록되었습니다. ')
    }).catch(e => {
      console.error(e)
      alert('다시 시도해 주세요.')
    })
  }
}

</script>

<template>
  <VContainer class="mb-6">
    <VRow>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="reqDate" label="요청날짜" :placeholder="today" />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedRest" label="신청유형" placeholder="신청유형" :items="eduProps" :item-props="itemProps"
          clearable clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedDeptCode" label="부서조회" placeholder="부서조회" :items="dept_list" clearable
          clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedEmpCode" label="사원조회" placeholder="사원조회" :items="emp_list_by_dept" clearable
          clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="start" label="시작시간" placeholder="시작시간"
          :config="{ enableTime: true, dateFormat: 'Y-m-d Hi' }" />
      </VCol>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="end" label="종료시간" placeholder="종료시간"
          :config="{ enableTime: true, dateFormat: 'Y-m-d Hi' }" />
      </VCol>
    </VRow>
    <VRow>
      <VCol>
        <AppTextarea v-model="cause" label="신청사유" placeholder="신청사유를 작성해주세요." />
      </VCol>
    </VRow>
    <VRow>
      <VCol cols="12" md="2" offset-md="8">
        <p class="mt-5 ml-15">
          사용일수: {{ numberOfDays }}
        </p>
      </VCol>
      <VCol cols="12" md="2" offset-md="">
        <VBtn class="mt-3" width="155" @click="registerRest">
          <VIcon start icon="tabler-checkbox" />신청
        </VBtn>
      </VCol>
    </VRow>
  </VContainer>
</template>
