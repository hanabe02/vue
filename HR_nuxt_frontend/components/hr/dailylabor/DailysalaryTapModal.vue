<script lang="ts" setup>
import { dailyLaborStore } from '@/store/hr/dailylabor';
import type { DailyLaborSalaryTax } from '@/types/dailylabor/types';

const DailyLaborStore = dailyLaborStore()
const detailSalaryTax = computed<DailyLaborSalaryTax>(() => { return DailyLaborStore.detailSalaryTax })

const totalSal = computed(() => {
  return Number(detailSalaryTax.value.totalPay) - Number(detailSalaryTax.value.totalTax)
})
</script>

<template>
  <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
    <VDialog v-model="DailyLaborStore.isModal" max-width="800">
      <!-- Dialog close btn -->
      <DialogCloseBtn @click="DailyLaborStore.MODAL_SET(false)" />

      <!-- Dialog Content -->
      <VCard>
        <VCardText>
          <h3 class="mb-6">
            {{ detailSalaryTax.employeeName }}님의 {{ detailSalaryTax.workDate.substring(0, 10) }} 상세 급여 입니다.
          </h3>
          <VDivider class="mb-6" />
          <h4 class="mb-6">
            지급항목
          </h4>
          <VRow>
            <VCol cols="12" sm="4">
              <AppTextField v-model="detailSalaryTax.normalPay" label="기본급" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="detailSalaryTax.overtimePay" label="연장수당" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="detailSalaryTax.nightPayRate" label="야간수당" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="detailSalaryTax.totalPay" label="지급합계" disabled />
            </VCol>
          </VRow>
          <VDivider class="mt-6" />
        </VCardText>
        <VCardText>
          <h4 class="mb-6">
            공제항목
          </h4>
          <VRow>
            <VCol cols="12" sm="4">
              <AppTextField v-model="detailSalaryTax.nationTax" label="국민연금" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="detailSalaryTax.healthTax" label="건강보험" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="detailSalaryTax.empInsureTax" label="고용보험" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="detailSalaryTax.longTermTax" label="장기요양보험" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="detailSalaryTax.incomeTax" label="소득세" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="detailSalaryTax.localIncomeTax" label="주민세" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="detailSalaryTax.totalTax" label="공제합계" disabled />
            </VCol>
          </VRow>
        </VCardText>
        <VCardText>
          <VDivider class="mb-6" />
          <h4 class="mb-6">
            차인지급액
          </h4>
          <VRow>
            <VCol cols="12" sm="4">
              <AppTextField v-model="totalSal" disabled />
            </VCol>
          </VRow>
        </VCardText>
        <VDivider />
        <VCardText class="d-flex justify-end flex-wrap gap-3 mt-2">
          <VBtn @click="DailyLaborStore.MODAL_SET(false)">
            확인
          </VBtn>
        </VCardText>
      </VCard>
    </VDialog>
  </div>
  <VCard />
</template>
