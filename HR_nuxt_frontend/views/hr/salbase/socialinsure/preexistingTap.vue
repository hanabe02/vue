<script lang="ts" setup>
import { salaryStore } from '@/store/hr/salary';
import { VDataTable } from 'vuetify/labs/VDataTable';

const baseInsureList = ref(salaryStore().baseInsureList)
const inputSalary = ref(0)

const preexistingheaders1 = [
  { title: '보험료 총액', key: 'note', sortable: false },
  { title: '산재보험료', key: 'industRate', sortable: false },
]

const preexistingheaders2 = [
  { title: '구분', key: 'note', sortable: false },
  { title: '요율(%)', key: 'industRate', sortable: false },
]

const preexisting1 = ref([
  { note: '', industRate: '' },
])

const preexisting2 = computed(() => [
  {
    note: '업종요율(전기기계기구·정밀기구·전자제품 제조업)',
    industRate: baseInsureList.value.industinsureRates,
  },
  {
    note: '출퇴근재해요율',
    industRate: baseInsureList.value.industinsurecharRates,
  },
])

const calculate = () => {
  preexisting1.value[0].industRate = inputSalary.value * (preexisting2.value[0].industRate / 100 + preexisting2.value[1].industRate / 100)

  preexisting1.value[0].note = preexisting1.value[0].industRate
}

watch(() => salaryStore().baseInsureList, newValue => {
  baseInsureList.value = newValue
})
</script>

<template>
  <VContainer class="mb-6">
    <h1 class="text-h3 mb-6">
      산재 연금 모의계산
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
    <VDataTable class="mb-12" :headers="preexistingheaders1" :items="preexisting1">
      <template #item.note="{ item }">
        <AppTextField v-model="item.note" disabled />
      </template>
      <template #item.industRate="{ item }">
        <AppTextField v-model="item.industRate" disabled />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
    <VDataTable class="mb-12" :headers="preexistingheaders2" :items="preexisting2">
      <template #item.industRate="{ item }">
        <AppTextField v-model="item.industRate" disabled />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
    <VCard>
      <VCardText class="pt-2">
        <h3>INFO</h3><br>
        <span>
          <h4>❗ 보험료 식: 보수총액(월평균보수) × 보험료율 ÷ 1,000</h4><br>
        </span>
      </VCardText>
    </VCard>
  </vcontainer>
</template>
