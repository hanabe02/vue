<script lang="ts" setup>
import { salaryStore } from '@/store/hr/salary';

const salaryList = computed(() => {
  return salaryStore().rowList
})

const monthDeductionList = computed(() => {
  return salaryStore().monthDeductionList
})

const totalTaxSal = computed(() => {
  return Number(salaryList.value.salary) + Number(salaryList.value.bonus)
})

const nonTaxSal = computed(() => {
  return Number(salaryList.value.mealAllowance) + Number(salaryList.value.ownerCarSub)
})

const totalPayment = computed(() => {
  return Number(totalTaxSal.value) + Number(nonTaxSal.value)
})

const totaldeduction = computed(() => {
  let total = 0

  for (let i = 0; i < monthDeductionList.value.length; i++) {
    const price = Number(monthDeductionList.value[i].price)

    total += price
  }

  return total
})

const totaldifference = computed(() => {
  return Number(totalPayment.value) - Number(totaldeduction.value)
})

const title = `${salaryList.empName}님의 ${salaryList.applyYearMonth} 급여 입니다.`
</script>

<template>
  <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
    <VDialog v-model="salaryStore().isDialogVisible" max-width="600">
      <!-- Dialog close btn -->
      <DialogCloseBtn @click="salaryStore().SET_DIALOG(false)" />

      <!-- Dialog Content -->
      <VCard>
        <VCardText>
          <h3 class="mb-6">
            [{{ salaryList.empName }}]님의 {{ salaryList.applyYearMonth }} 급여 입니다.
          </h3>
          <VDivider class="mb-6" />
          <h4 class="mb-6">
            지급항목
          </h4>
          <VRow>
            <VCol cols="12" sm="4">
              <AppTextField v-model="salaryList.salary" label="기본금" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="salaryList.totalExtSal" label="연장야간수당" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="salaryList.bonus" label="상여" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="salaryList.mealAllowance" label="식대" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="salaryList.ownerCarSub" label="자가운전보조금" disabled />
            </VCol>
          </VRow>
          <VRow>
            <VCol cols="12" sm="4">
              <AppTextField v-model="totalTaxSal" label="과세" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="nonTaxSal" label="비과세" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="totalPayment" label="지급합계" disabled />
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
              <AppTextField v-model="monthDeductionList[0].price" label="국민연금" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="monthDeductionList[1].price" label="건강보험" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="monthDeductionList[2].price" label="고용보험" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="monthDeductionList[3].price" label="장기요양보험" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="monthDeductionList[4].price" label="소득세" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="monthDeductionList[5].price" label="주민세" disabled />
            </VCol>
          </VRow>
          <VRow>
            <VCol cols="12" sm="4">
              <AppTextField v-model="totaldeduction" label="공제합계" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="totaldifference" label="차인지급액" disabled />
            </VCol>
          </VRow>
        </VCardText>
        <VDivider />
        <VCardText class="d-flex justify-end flex-wrap gap-3 mt-2">
          <VBtn @click="salaryStore().SET_DIALOG(false)">
            확인
          </VBtn>
        </VCardText>
      </VCard>
    </VDialog>
  </div>
  <VCard />
</template>
