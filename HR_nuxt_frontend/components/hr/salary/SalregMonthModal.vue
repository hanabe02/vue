<script lang="ts" setup>
import { salaryStore } from '@/store/hr/salary';
import { defineProps } from 'vue';
import { computed } from 'vue'


const 국민연금 = computed(() => salaryStore().monthDeductionList?.[0]?.price ?? '')
const 건강보험 = computed(() => salaryStore().monthDeductionList?.[1]?.price ?? '')
const 고용보험 = computed(() => salaryStore().monthDeductionList?.[2]?.price ?? '')
const 장기요양보험 = computed(() => salaryStore().monthDeductionList?.[3]?.price ?? '')
const 소득세 = computed(() => salaryStore().monthDeductionList?.[4]?.price ?? '')
const 주민세 = computed(() => salaryStore().monthDeductionList?.[5]?.price ?? '')

const props = defineProps(['fetchData2'])

const salaryList = computed(() => {
  return salaryStore().salaryList
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

const pauseMonthSalary = async () => {
  props.fetchData2()
  await salaryStore().SET_DIALOG(false)
  salaryStore().SET_DIALOG_BTN(false)
}

const updateMonthSalary = async () => {
  const updateMonthSalaryData = {
    totalPayment: salaryList.value.totalPayment,
    finalizeStatus: 'Y',
    realSalary: salaryList.value.realSalary,
    mealAllowance: salaryList.value.mealAllowance,
    ownerCarSub: salaryList.value.ownerCarSub,
    empCode: salaryList.value.empCode,
    applyYearMonth: salaryList.value.applyYearMonth,
  }

  await salaryStore().MONTH_SAL_FINAL(updateMonthSalaryData)

await salaryStore().FETCH_SALARY_DETAIL({
    empCode: updateMonthSalaryData.empCode,
    applyYearMonth: updateMonthSalaryData.applyYearMonth,
    deptCode: salaryList.value.deptCode,

    
  })
console.log('deptcode:',salaryList.value.deptCode);
  
  await salaryStore().FIND_TAX({
    empCode: updateMonthSalaryData.empCode,
    applyYearMonth: updateMonthSalaryData.applyYearMonth,
  })

  props.fetchData2()
  await salaryStore().SET_DIALOG(false)
  salaryStore().SET_DIALOG_BTN(false)
}

const generateSalary = async () => {
  const selected = salaryStore().selectedrow
console.log("선택된 데이터",selected)
console.log("salarylist내용",salaryStore().salaryList);
  try{
  await salaryStore().SALARY_PROCESS(selected)
  await salaryStore().FIND_TAX(selected)

   await salaryStore().FETCH_SALARY_DETAIL({
      empCode: selected.empCode,
      applyYearMonth: selected.applyYearMonth,
      deptCode: selected.deptCode
    })

  const updateMonthSalaryData = {
      totalPayment: totalPayment.value,
   
      realSalary: totaldifference.value,
      mealAllowance: salaryList.value.mealAllowance,
      ownerCarSub: salaryList.value.ownerCarSub,
      empCode: salaryList.value.empCode,
      applyYearMonth: salaryList.value.applyYearMonth,
    }

await salaryStore().MONTH_SAL_FINAL(updateMonthSalaryData)
  props.fetchData2()
  alert("급여 생성이 완료되었습니다.")

  salaryStore().SET_DIALOG(false)
}catch(err){
    console.error("급여 생성 실패", err)
    alert("급여 생성 중 오류가 발생했습니다.")
}
}

const deleteMonthSalary = async () => {
  const deleteMonthSalaryData = {
    empCode: salaryList.value.empCode,
    applyYearMonth: salaryList.value.applyYearMonth,
  }

  salaryStore().DELETE_MONTH_SALARY(deleteMonthSalaryData)

  props.fetchData2()
  await salaryStore().SET_DIALOG(false)
  salaryStore().SET_DIALOG_BTN(false)
}
</script>

<template>
  <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
    <VDialog v-model="salaryStore().isDialogVisible" max-width="600">
      <DialogCloseBtn @click="salaryStore().SET_DIALOG(false)" />

      <VCard title="월 급여 조회">
        <VCardText>
          <VDivider class="mb-6" />
          <h4 class="mb-6">지급항목  </h4>
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
              <AppTextField v-model="salaryList.mealAllowance" label="식대" />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="salaryList.ownerCarSub" label="자가운전보조금" />
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
          <h4 class="mb-6">공제항목</h4>
          <VRow>
            <VCol cols="12" sm="4">
              <AppTextField :model-value="국민연금" label="국민연금" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField :model-value="건강보험" label="건강보험" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField :model-value="고용보험" label="고용보험" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField :model-value="장기요양보험" label="장기요양보험" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField :model-value="소득세" label="소득세" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField :model-value="주민세" label="주민세" disabled />
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
               <VBtn v-if="salaryStore().isDialogBtn === 'create'" @click="generateSalary">생성</VBtn>

          <VBtn v-if="salaryStore().isDialogBtn === true" @click="deleteMonthSalary">삭제</VBtn>
          <VBtn v-if="salaryStore().isDialogBtn === true" @click="pauseMonthSalary">승인대기</VBtn>
          <VBtn v-if="salaryStore().isDialogBtn === true" @click="updateMonthSalary">승인</VBtn>
      
          <VBtn v-else @click="salaryStore().SET_DIALOG(false)">확인</VBtn>
        </VCardText>
      </VCard>
    </VDialog>
  </div>
</template>