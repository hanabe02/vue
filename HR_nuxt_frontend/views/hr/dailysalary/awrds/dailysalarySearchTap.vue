<script lang="ts" setup>
import DailysalaryTapModal from '@/components/hr/dailylabor/DailysalaryTapModal.vue';
import { dailyLaborStore } from '@/store/hr/dailylabor';
import type { DailyLaborSalaryTax, DailySalarySearchTapRow, Headers } from '@/types/dailylabor/types';
import { VDataTable } from 'vuetify/labs/VDataTable';

const search = ref('')
const dailyLaborSalaryTaxList = ref<DailyLaborSalaryTax[]>([])
const DailyLaborStore = dailyLaborStore()
const selectedRows = ref([])
const dateTimePicker = ref('')

const headers: Headers[] = [
  { title: '사번', key: 'employeeId', align: 'center' },
  { title: '직원명', key: 'employeeName', align: 'center' },
  { title: '총 급여', key: 'totalPay', align: 'center' },
  { title: '정상급여', key: 'normalPay', align: 'center' },
  { title: '연장급여', key: 'overtimePay', align: 'center' },
  { title: '야간급여', key: 'nightPayRate', align: 'center' },
  { title: '공제', key: 'totalTax', align: 'center' },
]

const fetchData = async () => {
  const payload = {
    workDate: dateTimePicker.value,
    deadlineRequest: 'Y',
    status: 'Y',
  }

  await DailyLaborStore.FETCH_SALARY_TAX(payload)
  dailyLaborSalaryTaxList.value = await DailyLaborStore.dailyLaborSalaryTaxList
}

const handleRowClick = async (row: DailySalarySearchTapRow) => {
  console.log(row)

  await DailyLaborStore.DETAIL_SALARY_TAX(row.item)
  await DailyLaborStore.MODAL_SET(true)
}

const filteredData = computed(() => {
  return dailyLaborSalaryTaxList.value.filter((emp: any) => {
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
        <AppDateTimePicker v-model="dateTimePicker" placeholder="급여날짜 검색" />
      </VCol>
    </VCardText>
    <VDivider />
    <VDataTable v-model="selectedRows" :headers="headers" :items="filteredData" :items-per-page="5" :footer-props="{
      'items-per-page-options': [5, 10, 20],
      'show-current-page': true,
      'show-first-last-page': true,
    }" item-value="employeeId" return-object @click:row="(_, row) => handleRowClick(row)">
      <template #item.deptName="{ item }">
        {{ item.deptName }}
      </template>
    </VDataTable>
  </VCard>
  <DailysalaryTapModal />
</template>
