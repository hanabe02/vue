<script lang="ts" setup>
import { baseStore } from '@/store/hr/base';
import { salaryStore } from '@/store/hr/salary';
import { VDataTable } from 'vuetify/labs/VDataTable';

const baseInsureList = ref(salaryStore().baseInsureList)
const selectedYear = ref(baseStore().selectYear)
const inputSalary = ref(0)

const nationalheaders1 = [
  { title: '연금보험료(전체)', key: 'nationalPension', sortable: false },
  { title: '근로자 부담금', key: 'empNation', sortable: false },
  { title: '사업자 부담금', key: 'workNation', sortable: false },
]

const nationalheaders2 = [
  { title: '비고', key: 'note', sortable: false },
  { title: '연금보험료(전체)(%)', key: 'nationalPension', sortable: false },
  { title: '근로자(%)', key: 'empNation', sortable: false },
  { title: '사업주(%)', key: 'workNation', sortable: false },
]

const national1 = ref([
  { nationalPension: '', empNation: '', workNation: '' },
])

const national2 = computed(() => [
  {
    note: '기준 소득월액',
    nationalPension: baseInsureList.value.nationpensionRates,
    empNation: baseInsureList.value.nationpensionRates / 2,
    workNation: baseInsureList.value.nationpensionRates / 2,
  },
])

const calculate = () => {
  const nationPension = baseInsureList.value.nationpensionRates / 100
  if (inputSalary.value <= 350000) {
    inputSalary.value = 350000
    national1.value[0].nationalPension = inputSalary.value * nationPension
    national1.value[0].empNation = (inputSalary.value * nationPension) / 2
    national1.value[0].workNation = (inputSalary.value * nationPension) / 2
  }
  else if (inputSalary.value >= 5530000) {
    inputSalary.value = 5530000
    national1.value[0].nationalPension = inputSalary.value * nationPension
    national1.value[0].empNation = (inputSalary.value * nationPension) / 2
    national1.value[0].workNation = (inputSalary.value * nationPension) / 2
  }
  else {
    national1.value[0].nationalPension = inputSalary.value * nationPension
    national1.value[0].empNation = (inputSalary.value * nationPension) / 2
    national1.value[0].workNation = (inputSalary.value * nationPension) / 2
  }
}

watch(() => baseStore().selectYear, newValue => {
  selectedYear.value = newValue
})

watch(() => salaryStore().baseInsureList, newValue => {
  baseInsureList.value = newValue
})
</script>

<template>
  <VContainer class="mb-6">
    <h1 class="text-h3 mb-6">
      국민 연금 모의계산
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
    <VDataTable class="mb-12" :headers="nationalheaders1" :items="national1">
      <template #item.nationalPension="{ item }">
        <AppTextField v-model="item.nationalPension" disabled />
      </template>
      <template #item.empNation="{ item }">
        <AppTextField v-model="item.empNation" disabled />
      </template>
      <template #item.workNation="{ item }">
        <AppTextField v-model="item.workNation" disabled />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
    <VDataTable class="mb-12" :headers="nationalheaders2" :items="national2">
      <template #item.nationalPension="{ item }">
        <AppTextField v-model="item.nationalPension" disabled />
      </template>
      <template #item.empNation="{ item }">
        <AppTextField v-model="item.empNation" disabled />
      </template>
      <template #item.workNation="{ item }">
        <AppTextField v-model="item.workNation" disabled />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
    <VCard>
      <VCardText class="pt-2">
        <h3>INFO</h3><br>
        <span>
          <h4>❗ 연금보험료 = 기준소득월액 X 9%(연금보험료율)</h4><br>
          <h4>❕ 기준소득월액</h4><br>
          <p>
            기준소득월액이란 국민연금의 보험료 및 급여 산정을 위하여 가입자가 신고한 소득월액에서 천원미만을 절사한 금액을 말하며, <br>
            최저 35만원에서 최고 553만원까지의 범위로 결정하게 됩니다. <br>
            따라서, 신고한 소득월액이 35만원보다 적으면 35만원을 기준소득월액으로 하고,
            553만원보다 많으면 553만원을 기준소득월액으로 합니다. (2022.7.1 기준)
          </p>
          <h4>❕ 기준소득월액 상한액과 하한액</h4><br>
          <p>
            기준소득월액 상한액과 하한액은 국민연금 사업장가입자와 지역가입자 전원(납부예외자 제외)의 평균소득월액의 3년간 변동하는 비율을 반영하여 <br>
            매년 3월말까지 보건복지부 장관이 고시하며 해당연도 7월부터 1년간 적용합니다.
          </p>
        </span>
      </VCardText>
    </VCard>
  </vcontainer>
</template>
