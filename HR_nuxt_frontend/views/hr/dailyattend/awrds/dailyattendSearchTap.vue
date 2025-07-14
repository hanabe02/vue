<script lang="ts" setup>
import { dailyLaborStore } from '@/store/hr/dailylabor';
import type { EmployeeInfo, Headers } from '@/types/dailylabor/types';
import { VDataTable } from 'vuetify/labs/VDataTable';

const search = ref('')
const dailyLaborAttendList = ref<EmployeeInfo[]>([])
const DailyLaborStore = dailyLaborStore()
const dateTimePicker = ref('')
const approval = 'Y'

const headers: Headers[] = [
  { title: '직원명', key: 'employeeName', align: 'center' },
  { title: '프로젝트', key: 'project', align: 'center' },
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

onMounted(fetchData)
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
    </VCardText>
    <VDivider />
    <VDataTable :headers="headers" :items="filteredData" :items-per-page="5" :footer-props="{
      'items-per-page-options': [5, 10, 20],
      'show-current-page': true,
      'show-first-last-page': true,
    }" item-value="employeeId" return-object>
      <template #item.deptName="{ item }">
        {{ item.deptName }}
      </template>
    </VDataTable>
  </VCard>
</template>
