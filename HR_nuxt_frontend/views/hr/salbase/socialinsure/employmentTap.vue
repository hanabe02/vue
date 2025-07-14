<script lang="ts" setup>
import { salaryStore } from '@/store/hr/salary';
import { VDataTable } from 'vuetify/labs/VDataTable';

const baseInsureList = ref(salaryStore().baseInsureList)
const inputSalary = ref(0)

const Employmentheaders1 = [
  { title: '총액', key: 'standard', sortable: false },
  { title: '근로자 부담금(실업급여)', key: 'empInsure', sortable: false },
  { title: '사업자 부담금(실업급여+고용안정직능개발 부담금)', key: 'workInsure', sortable: false },
]

const Employmentheaders2 = [
  { title: '구분', key: 'note', sortable: false },
  { title: '보험료율(%)', key: 'standard', sortable: false },
  { title: '근로자(%)', key: 'empInsure', sortable: false },
  { title: '사업주(%)', key: 'workInsure', sortable: false },
]

const Employment1 = ref([
  { standard: '', empInsure: '', workInsure: '' },
])

const Employment2 = computed(() => [
  {
    note: '실업급여',
    standard: baseInsureList.value.wrkinsureRates,
    empInsure: baseInsureList.value.wrkinsureRates / 2,
    workInsure: baseInsureList.value.wrkinsureRates / 2,
  },
  {
    note: '고용안정, 직업능력 개발사업(150인미만)',
    standard: baseInsureList.value.jobstabilRates,
    empInsure: '-',
    workInsure: baseInsureList.value.jobstabilRates,
  },
])

const calculate = () => {
  Employment1.value[0].empInsure = inputSalary.value * (baseInsureList.value.wrkinsureRates / 2) / 100
  Employment1.value[0].workInsure = (inputSalary.value * (baseInsureList.value.wrkinsureRates / 2) / 100) + (inputSalary.value * baseInsureList.value.jobstabilRates / 100)
  Employment1.value[0].standard = Employment1.value[0].empInsure + Employment1.value[0].workInsure
}

watch(() => salaryStore().baseInsureList, newValue => {
  baseInsureList.value = newValue
})
</script>

<template>
  <VContainer class="mb-6">
    <h1 class="text-h3 mb-6">
      고용 연금 모의계산
    </h1>
    <VDivider class="mb-6" />

    <!-- 텍스트 필드와 버튼 영역 시작 -->
    <VRow>
      <VCol cols="12" sm="6">
        <!-- 텍스트 필드 -->
        <VTextField v-model="inputSalary" label="월 급여 입력하세요" />
      </VCol>
      <VCol cols="12" sm="6">
        <!-- 버튼 -->
        <VBtn @click="calculate">
          모의계산
        </VBtn>
      </VCol>
    </VRow>
    <VDivider class="mt-6 mb-6" />
    <VDataTable class="mb-12" :headers="Employmentheaders1" :items="Employment1">
      <template #item.standard="{ item }">
        <AppTextField v-model="item.standard" disabled />
      </template>
      <template #item.empInsure="{ item }">
        <AppTextField v-model="item.empInsure" disabled />
      </template>
      <template #item.workInsure="{ item }">
        <AppTextField v-model="item.workInsure" disabled />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
    <VDataTable class="mb-12" :headers="Employmentheaders2" :items="Employment2">
      <template #item.standard="{ item }">
        <AppTextField v-model="item.standard" disabled />
      </template>
      <template #item.empInsure="{ item }">
        <AppTextField v-model="item.empInsure" disabled />
      </template>
      <template #item.workInsure="{ item }">
        <AppTextField v-model="item.workInsure" disabled />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
  </vcontainer>
</template>
