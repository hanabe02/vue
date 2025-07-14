<script lang="ts" setup>
import { dailyLaborStore } from '@/store/hr/dailylabor';
import type { BatchAttendData, EmployeeInfo, Headers } from '@/types/dailylabor/types';
import { VDataTable } from 'vuetify/labs/VDataTable';

const search = ref('')
const isSuccessFunc = ref(false)
const dailyLaborAttendList = ref<EmployeeInfo[]>([])
const DailyLaborStore = dailyLaborStore()
const selectedRows = ref([])
const dateTimePicker = ref('')
const approval = 'N'

const headers: Headers[] = [
  { title: '사번', key: 'employeeId', align: 'center' },
  { title: '직원명', key: 'employeeName', align: 'center' },
  { title: '출근', key: 'workStartTime', align: 'center' },
  { title: '퇴근', key: 'workEndTime', align: 'center' },
  { title: '총 근무시간', key: 'totalWorkTime', align: 'center' },
  { title: '정상근무', key: 'workTime', align: 'center' },
  { title: '연장근무', key: 'workOverTime', align: 'center' },
  { title: '야간근무', key: 'workNightTime', align: 'center' },
]

const fetchData = async () => {
  await DailyLaborStore.FETCH_ATTEND(dateTimePicker.value, approval)
  dailyLaborAttendList.value = await DailyLaborStore.dailyLaborAttendList
}

const deadlinebtn = async () => {
  console.log(selectedRows.value)

  const attendDataList: BatchAttendData[] = []

  selectedRows.value.forEach((element: EmployeeInfo) => {
    const attendData: BatchAttendData = {
      employeeId: element.employeeId,
      workDate: element.workDate.substring(0, 10),
    }

    attendDataList.push(attendData)
  })
  console.log(attendDataList)

  await DailyLaborStore.UPDATE_ATTEND_APPROVAL(attendDataList)
  await fetchData()
  isSuccessFunc.value = true
}

const deletebtn = async () => {
  console.log(selectedRows.value)

  const attendDataList: BatchAttendData[] = []

  selectedRows.value.forEach((element: EmployeeInfo) => {
    const attendData: BatchAttendData = {
      employeeId: element.employeeId,
      workDate: element.workDate.substring(0, 10),
    }

    attendDataList.push(attendData)
  })
  console.log(attendDataList)

  await DailyLaborStore.DELETE_ATTEND(attendDataList)
  await fetchData()
  isSuccessFunc.value = true
}

const filteredData = computed(() => {
  return dailyLaborAttendList.value.filter((emp: any) => {
    return Object.values(emp).some(field => {
      if (typeof field === 'string')
        return field.toLowerCase().includes(search.value.toLowerCase())

      return false
    })
  })
})

watch([dateTimePicker], fetchData, { immediate: true })
</script>

<template>
  <VCard>
    <VCardText class="d-flex flex-wrap py-4 gap-4">
      <VCol>
        <div style="inline-size: 10rem;">
          <AppTextField v-model="search" density="compact" placeholder="검색" append-inner-icon="tabler-search" single-line
            hide-details dense outlined />
        </div>
      </VCol>
      <VSpacer />
      <VCol md="2">
        <AppDateTimePicker v-model="dateTimePicker" placeholder="근태날짜 검색" />
      </VCol>
      <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
        <VBtn @click="deadlinebtn">
          <VIcon start icon="tabler-checkbox" />마감
        </VBtn>
        <VBtn @click="deletebtn">
          <VIcon start icon="tabler-square-x" />삭제
        </VBtn>
      </div>
    </VCardText>
    <VDivider />
    <VDataTable v-model="selectedRows" :headers="headers" :items="filteredData" :items-per-page="5" :footer-props="{
      'items-per-page-options': [5, 10, 20],
      'show-current-page': true,
      'show-first-last-page': true,
    }" show-select item-value="employeeId" return-object>
      <template #item.deptName="{ item }">
        {{ item.deptName }}
      </template>
    </VDataTable>
  </VCard>
  <VSnackbar v-model="isSuccessFunc" location="top" :timeout="3000" color="success">
    <VAlert density="compact" type="success" title="success!" text="완료되었습니다." />
  </VSnackbar>
</template>
