<script lang="ts" setup>
import { dailyLaborStore } from '@/store/hr/dailylabor';
import type { DailyLaborTax, Header } from '@/types/dailylabor/types';
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



const DailyLaborStore = dailyLaborStore()
const TaxList = ref<DailyLaborTax[]>([])

const headers: Header[] = [
  { title: '요율(%)', key: 'standard' },
  { title: '근로자(%)', key: 'empPercent' },
  { title: '사업주(%)', key: 'workPercent' },
  { title: '계산법', key: 'note' },
]

const IncomeHeaders: Header[] = [
  { title: '구분', key: 'gubun' },
  { title: '계산법', key: 'note' },
]

const fetchData = async () => {
  await DailyLaborStore.FETCH_TAX('Y')

  TaxList.value = await DailyLaborStore.dailyLaborTaxList
  console.log(DailyLaborStore.dailyLaborTaxList)
}

const newNationPension = computed(() => [
  {
    standard: TaxList.value.nationPensionRates,
    empPercent: TaxList.value.nationPensionRates / 2,
    workPercent: TaxList.value.nationPensionRates / 2,
    note: `임금(노무비)총액*${TaxList.value.nationPensionRates / 2}%`,
  },

])

const newHealthInsure = computed(() => [
  {
    standard: TaxList.value.healthInsureRates,
    empPercent: TaxList.value.healthInsureRates / 2,
    workPercent: TaxList.value.healthInsureRates / 2,
    note: `임금(노무비)총액*${TaxList.value.healthInsureRates / 2}%`,
  },
])

const newlongTermCareRate = computed(() => [
  {
    standard: TaxList.value.longTermCareRate,
    empPercent: TaxList.value.longTermCareRate / 2,
    workPercent: TaxList.value.longTermCareRate / 2,
    note: `건강보험료*${TaxList.value.longTermCareRate / 2}%`,
  },
])

const newEmpInsure = computed(() => [
  {
    standard: TaxList.value.empInsureRates,
    empPercent: TaxList.value.empInsureRates / 2,
    workPercent: TaxList.value.empInsureRates / 2,
    note: `임금(노무비)총액*${TaxList.value.empInsureRates}%`,
  },
])

const newIndustInsure = computed(() => [
  {
    standard: TaxList.value.wrkInsureRates,
    empPercent: '납부안함',
    workPercent: '업종별로 상이함',
    note: '',
  },
])

const newIncome = computed(() => [
  {
    gubun: '일당 150,000원 이상 공제',
    note: `일당-150,000*${TaxList.value.incomeTaxRates}%`,
  },
])

const newLocalIncome = computed(() => [
  {
    gubun: `소득세의 ${TaxList.value.localIncomeTaxRates}%`,
    note: `소득세*${TaxList.value.localIncomeTaxRates}%`,
  },
])

const addNationalPension = async () => {
  console.log(TaxList.value)
}

onMounted(fetchData)
</script>

<template>
  <h1 class="mb-6">
    일용근로 공제관리
  </h1>
  <VCard>
    <VContainer class="mb-6">
      <VCardText class="d-flex flex-wrap py-4 gap-4">
        <h1 class="text-h3">
          국민연금
        </h1>
        <VSpacer />
        <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
          <VBtn @click="addNationalPension">
            공제 기준 등록
          </VBtn>
        </div>
      </VCardText>
      <VDivider />
      <VDataTable class="mb-12" :headers="headers" :items="newNationPension">
        <template #item.standard="{ item }">
          <AppTextField v-model="item.standard" />
        </template>
        <template #item.empPercent="{ item }">
          <AppTextField v-model="item.empPercent" disabled />
        </template>
        <template #item.workPercent="{ item }">
          <AppTextField v-model="item.workPercent" disabled />
        </template>
        <template #bottom>
          <VDivider />
        </template>
      </VDataTable>
      <VCardText class="d-flex flex-wrap py-4 gap-4">
        <h1 class="text-h3">
          건강보험
        </h1>
        <VSpacer />
      </VCardText>
      <VDivider />
      <VDataTable class="mb-12" :headers="headers" :items="newHealthInsure">
        <template #item.standard="{ item }">
          <AppTextField v-model="item.standard" />
        </template>
        <template #item.empPercent="{ item }">
          <AppTextField v-model="item.empPercent" disabled />
        </template>
        <template #item.workPercent="{ item }">
          <AppTextField v-model="item.workPercent" disabled />
        </template>
        <template #bottom>
          <VDivider />
        </template>
      </VDataTable>
      <VCardText class="d-flex flex-wrap py-4 gap-4">
        <h1 class="text-h3">
          장기요양보험
        </h1>
        <VSpacer />
      </VCardText>
      <VDivider />
      <VDataTable class="mb-12" :headers="headers" :items="newlongTermCareRate">
        <template #item.standard="{ item }">
          <AppTextField v-model="item.standard" />
        </template>
        <template #item.empPercent="{ item }">
          <AppTextField v-model="item.empPercent" disabled />
        </template>
        <template #item.workPercent="{ item }">
          <AppTextField v-model="item.workPercent" disabled />
        </template>
        <template #bottom>
          <VDivider />
        </template>
      </VDataTable>
      <VCardText class="d-flex flex-wrap py-4 gap-4">
        <h1 class="text-h3">
          고용보험
        </h1>
        <VSpacer />
      </VCardText>
      <VDivider />
      <VDataTable class="mb-12" :headers="headers" :items="newEmpInsure">
        <template #item.standard="{ item }">
          <AppTextField v-model="item.standard" />
        </template>
        <template #item.empPercent="{ item }">
          <AppTextField v-model="item.empPercent" disabled />
        </template>
        <template #item.workPercent="{ item }">
          <AppTextField v-model="item.workPercent" disabled />
        </template>
        <template #bottom>
          <VDivider />
        </template>
      </VDataTable>
      <VCardText class="d-flex flex-wrap py-4 gap-4">
        <h1 class="text-h3">
          산재보험
        </h1>
        <VSpacer />
      </VCardText>
      <VDivider />
      <VDataTable class="mb-12" :headers="headers" :items="newIndustInsure">
        <template #item.standard="{ item }">
          <AppTextField v-model="item.standard" />
        </template>
        <template #item.empPercent="{ item }">
          <AppTextField v-model="item.empPercent" disabled />
        </template>
        <template #item.workPercent="{ item }">
          <AppTextField v-model="item.workPercent" disabled />
        </template>
        <template #bottom>
          <VDivider />
        </template>
      </VDataTable>
      <VCardText class="d-flex flex-wrap py-4 gap-4">
        <h1 class="text-h3">
          소득세
        </h1>
        <VSpacer />
      </VCardText>
      <VDivider />
      <VDataTable class="mb-12" :headers="IncomeHeaders" :items="newIncome">
        <template #bottom>
          <VDivider />
        </template>
      </VDataTable>
      <VCardText class="d-flex flex-wrap py-4 gap-4">
        <h1 class="text-h3">
          지방세
        </h1>
        <VSpacer />
      </VCardText>
      <VDivider />
      <VDataTable class="mb-12" :headers="IncomeHeaders" :items="newLocalIncome">
        <template #bottom>
          <VDivider />
        </template>
      </VDataTable>
    </VContainer>
  </VCard>
</template>
