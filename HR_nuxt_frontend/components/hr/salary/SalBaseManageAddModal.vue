<script lang="ts" setup>
import { salaryStore } from '@/store/hr/salary';
import { defineProps } from 'vue';

const props = defineProps(['fetchData2'])

const isSuccessFunc = ref(false)
const isaddBase = ref(false)
const baseSalary = ref('')
const hobongRatio = ref('')
const selectedDept = ref('')
const selectedPos = ref('')

// const posList = computed(() => { return salaryStore().posList })
const deptList = computed(() => { return salaryStore().deptList })

// 사유가 있는 친구
const positions = [
  { value: 'POS-01', title: '사장' },
  { value: 'POS-02', title: '이사' },
  { value: 'POS-03', title: '부장' },
  { value: 'POS-04', title: '차장' },
  { value: 'POS-05', title: '과장' },
  { value: 'POS-06', title: '대리' },
  { value: 'POS-07', title: '사원' },
  { value: 'POS-08', title: '인턴' },
  { value: 'POS-09', title: '계약직' },
]

const addBase = async () => {
  const addData = {
    deptCode: selectedDept.value,
    positionCode: selectedPos.value,
    baseSalary: baseSalary.value,
    hobongRatio: hobongRatio.value,
  }

  if (Object.values(addData).some(value => value === '' || value === null)) {
    isaddBase.value = true
  }
  else {
    await salaryStore().ADD_BASE_SALARY(addData)
    await props.fetchData2()
    salaryStore().SET_DIALOG_BTN(false)
    isSuccessFunc.value = true
  }
}
</script>

<template>
  <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
    <VDialog v-model="salaryStore().isDialogBtn" max-width="600">
      <!-- Dialog close btn -->
      <DialogCloseBtn @click="salaryStore().SET_DIALOG_BTN(false)" />

      <!-- Dialog Content -->
      <VCard title="기준 급여 추가">
        <VCardText>
          <VDivider class="mb-6" />
          <VRow>
            <VCol cols="12" sm="6">
              <AppSelect v-model="selectedDept" :items="deptList" label="부서명" clearable clear-icon="tabler-x" />
            </VCol>
            <VCol cols="12" sm="6">
              <AppSelect v-model="selectedPos" :items="positions" label="직급명" clearable clear-icon="tabler-x" />
            </VCol>
            <VCol cols="12" sm="6">
              <AppTextField v-model="baseSalary" label="기본급" />
            </VCol>
            <VCol cols="12" sm="6">
              <AppTextField v-model="hobongRatio" label="호봉인상률" />
            </VCol>
          </VRow>
        </VCardText>
        <VDivider />
        <VCardText class="d-flex justify-end flex-wrap gap-3 mt-2">
          <VBtn @click="addBase">
            추가
          </VBtn>
        </VCardText>
      </VCard>
    </VDialog>
  </div>
  <VCard />
  <VSnackbar v-model="isaddBase" location="top" :timeout="3000" color="warning">
    <VAlert density="compact" type="warning" title="warning!" text="부서명, 직급명, 기본급, 호봉인상률을 선택해주세요." />
  </VSnackbar>
  <VSnackbar v-model="isSuccessFunc" location="top" :timeout="3000" color="success">
    <VAlert density="compact" type="success" title="success!" text="완료되었습니다." />
  </VSnackbar>
</template>
