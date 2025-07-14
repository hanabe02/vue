<script lang="ts" setup>
import DailysalaryTapModal from '@/components/hr/dailylabor/DailysalaryTapModal.vue';
import { dailyLaborStore } from '@/store/hr/dailylabor';
import type { DailyLaborSalaryTax, Headers, SalaryData } from '@/types/dailylabor/types';
import { VDataTable } from 'vuetify/labs/VDataTable';

const search = ref('')
const isSuccessFunc = ref(false)
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
    deadlineRequest: 'N',
    status: 'Y',
  }

  await DailyLaborStore.FETCH_SALARY_TAX(payload)
  dailyLaborSalaryTaxList.value = await DailyLaborStore.dailyLaborSalaryTaxList
}

const deadline = async () => {
  console.log(selectedRows.value)

  const salDataList: SalaryData[] = []

  selectedRows.value.forEach((element: DailyLaborSalaryTax) => {
    const salData = {
      employeeId: element.employeeId,
      workDate: element.workDate.substring(0, 10),
    }

    salDataList.push(salData)
  })
  console.log(salDataList)

  await DailyLaborStore.UPDATE_SALARY_TAX(salDataList)
  await fetchData()
  isSuccessFunc.value = true
}

const deletebtn = async () => {
  console.log(selectedRows.value)

  const salDataList: SalaryData[] = []

  selectedRows.value.forEach((element: DailyLaborSalaryTax) => {
    const salData = {
      employeeId: element.employeeId,
      workDate: element.workDate.substring(0, 10),
    }

    salDataList.push(salData)
  })
  console.log(salDataList)

  await DailyLaborStore.DELETE_SALARY_TAX(salDataList)
  await fetchData()
  isSuccessFunc.value = true
}

const handleRowClick = async row => {
  console.log(row.item)

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
      <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
        <VBtn @click="deadline">
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
    }" show-select item-value="employeeId" return-object @click:row="(_, row) => handleRowClick(row)">
      <template #item.deptName="{ item }">
        {{ item.deptName }}
      </template>
    </VDataTable>
  </VCard>
  <DailysalaryTapModal />
  <VSnackbar v-model="isSuccessFunc" location="top" :timeout="3000" color="success">
    <VAlert density="compact" type="success" title="success!" text="완료되었습니다." />
  </VSnackbar>
</template>
