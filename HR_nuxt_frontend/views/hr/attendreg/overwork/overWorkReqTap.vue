<script setup lang="ts">
import { attenStore } from '@/store/hr/attendance';
import { baseStore } from '@/store/hr/base';
import type { DeptItemProps, EmpItemProps } from '@/types/attendance/types';
import axios from 'axios';
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



const selectedDeptCode = ref('')
const selectedEmpCode = ref('')
const dept_list: any = ref([])
const emp_list_by_dept: any = ref([])
const reqDate = ref('')
const startTime = ref('')
const endTime = ref('')
const today = new Date().toISOString().substring(0, 10)

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

// 초과근무 등록
const insertElastic = async () => {
  if (reqDate.value === '' || selectedEmpCode.value === '' || startTime.value === '' || endTime.value === '') {
    alert('모든 항목을 선택해주세요.')
  } else {
    await axios.post('http://localhost:8282/hr/attendance/insertElastic', {
      empCode: selectedEmpCode.value,
      applyDay: reqDate.value,
      startTime: startTime.value,
      endTime: endTime.value,
      eno: ''
    }).then(
      res => {
        console.log([insertElastic], res.data)
        alert('초과근무 신청이 완료되었습니다. ')
      }).catch(e => {
        console.error(e)
        alert('다시 시도해 주세요.')
      })
  }
}

watch(endTime, () => { console.log(endTime.value) })
</script>

<template>
  <VContainer class="mb-6">
    <VRow>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="reqDate" label="요청날짜" :placeholder="today" />
      </VCol>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="startTime" label="시작시간" placeholder="시작시간"
          :config="{ enableTime: true, noCalendar: true, dateFormat: 'Hi' }" />
      </VCol>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="endTime" label="종료시간" placeholder="종료시간"
          :config="{ enableTime: true, noCalendar: true, dateFormat: 'Hi' }" />
      </VCol>
      <VCol cols="12" md="3">
        <AppSelect v-model="selectedDeptCode" label="부서조회" placeholder="부서조회" :items="dept_list" clearable
          clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="3">
        <AppSelect v-model="selectedEmpCode" label="사원조회" placeholder="사원조회" :items="emp_list_by_dept" clearable
          clear-icon="tabler-x" />
      </VCol>
    </VRow>
    <VRow>
      <VCol>
        <AppTextarea label="신청사유" placeholder="신청사유를 작성해주세요." />
      </VCol>
    </VRow>
    <VRow>
      <VCol cols="12" md="2" offset-md="10">
        <VBtn class="mt-3" width="155" @click="insertElastic">
          <VIcon start icon="tabler-checkbox" />신청
        </VBtn>
      </VCol>
    </VRow>
  </VContainer>
</template>
