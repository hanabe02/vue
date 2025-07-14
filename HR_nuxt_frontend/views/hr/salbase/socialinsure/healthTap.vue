<script lang="ts" setup>
import { salaryStore } from '@/store/hr/salary';
import { VDataTable } from 'vuetify/labs/VDataTable';

const baseInsureList = ref(salaryStore().baseInsureList)
const inputSalary = ref(0)

const Healthheaders1 = [
  { title: '구분', key: 'note', sortable: false },
  { title: '연금보험료(전체)', key: 'insureRate', sortable: false },
  { title: '근로자 부담금', key: 'empHealth', sortable: false },
  { title: '사업자 부담금', key: 'workHealth', sortable: false },
]

const Healthheaders2 = [
  { title: '구분', key: 'note', sortable: false },
  { title: '기준액', key: 'standard', sortable: false },
  { title: '보험료율(%)', key: 'insureRate', sortable: false },
  { title: '근로자(%)', key: 'empHealth', sortable: false },
  { title: '사업주(%)', key: 'workHealth', sortable: false },
]

const Health1 = ref([
  { note: '건강보험료', insureRate: '', empHealth: '', workHealth: '' },
  { note: '장기요양보험료', insureRate: '', empHealth: '', workHealth: '' },
])

const Health2 = computed(() => [
  {
    note: '건강보험료',
    standard: '보수월액',
    insureRate: baseInsureList.value.healthinsureRates,
    empHealth: baseInsureList.value.healthinsureRates / 2,
    workHealth: baseInsureList.value.healthinsureRates / 2,
  },
  {
    note: '장기요양보험료',
    standard: '건강보험료',
    insureRate: baseInsureList.value.longtermcareRate,
    empHealth: baseInsureList.value.longtermcareRate / 2,
    workHealth: baseInsureList.value.longtermcareRate / 2,
  },
])

const calculate = () => {
  const healthInsure = Health2.value[0].insureRate / 100
  const longTermCareRate = Health2.value[1].insureRate / 100

  Health1.value[0].insureRate = Math.round(inputSalary.value * healthInsure)
  Health1.value[0].empHealth = Math.round((inputSalary.value * healthInsure) / 2)
  Health1.value[0].workHealth = Math.round((inputSalary.value * healthInsure) / 2)
  Health1.value[1].insureRate = Math.round(inputSalary.value * healthInsure * longTermCareRate / 10) * 10
  Health1.value[1].empHealth = Math.round(((inputSalary.value * healthInsure * longTermCareRate / 10) * 10) / 2)
  Health1.value[1].workHealth = Math.round(((inputSalary.value * healthInsure * longTermCareRate / 10) * 10) / 2)
}

watch(() => salaryStore().baseInsureList, newValue => {
  baseInsureList.value = newValue
})
</script>

<template>
  <VContainer class="mb-6">
    <h1 class="text-h3 mb-6">
      건강 연금 모의계산
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
    <VDataTable class="mb-12" :headers="Healthheaders1" :items="Health1">
      <template #item.insureRate="{ item }">
        <AppTextField v-model="item.insureRate" disabled />
      </template>
      <template #item.empHealth="{ item }">
        <AppTextField v-model="item.empHealth" disabled />
      </template>
      <template #item.workHealth="{ item }">
        <AppTextField v-model="item.workHealth" disabled />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
    <VDataTable class="mb-12" :headers="Healthheaders2" :items="Health2">
      <template #item.insureRate="{ item }">
        <AppTextField v-model="item.insureRate" disabled />
      </template>
      <template #item.empHealth="{ item }">
        <AppTextField v-model="item.empHealth" disabled />
      </template>
      <template #item.workHealth="{ item }">
        <AppTextField v-model="item.workHealth" disabled />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
    <VCard>
      <VCardText class="pt-2">
        <h3>INFO</h3><br>
        <span>
          <h4>❗ 계산내용은 직장가입자의 보수월액에 따른 모의계산결과로서 실제와 다를 수 있으며 보수(월급) 외 소득(연 3,400만원 초과)이 있으면<br>
            실제 보험료와는 차이가 있을 수 있습니다.</h4><br>
          <h4>❕ 예) 보수월액이 2,000,000원일 때, 계산방법(2023년 기준)</h4><br>
          <p>
            직장가입자 본인 부담금(건강보험료 + 장기요양보험료) 79,980원 <br>
            건강보험료 : 2,000,000(보수월액) x 3.545% = 70,900원 <br>
            ㆍ장기요양보험료 : 70,900원(건강보험료) x ( 0.9082% / 7.09% ) = 9,080원(원단위 절사) <br>
            신장기요양보험료율(0.9082%) = 장기요양보험료율(12.81%) x 건강보험료율(7.09%) <br>
            ⇨ 사업장에서 납부할 보험료 = 본인 부담금(50%) + 사업장 부담금(50%) <br>
            = 79,980원 + 79,980원 = 159,960원 <br>
          </p>
        </span>
      </VCardText>
    </VCard>
  </vcontainer>
</template>
