<script lang="ts" setup>
import { salaryStore } from '@/store/hr/salary';
import { defineProps } from 'vue';

const props = defineProps(['fetchData2'])

const iswarningFunc = ref(false)
const isSuccessFunc = ref(false)

const rowList = computed(() => {
  return ref(salaryStore().rowList).value
})

const editBase = async () => {
  const editData = {
    hobongRatio: rowList.value.hobongRatio,
    baseSalary: rowList.value.baseSalary,
    positionCode: rowList.value.positionCode,
    deptCode: rowList.value.deptCode,
  }

  if (Object.values(editData).some(value => value === '' || value === null)) {
    iswarningFunc.value = true
  }
  else {
    await salaryStore().EDIT_BASE_SALARY(editData)
    await props.fetchData2()
    salaryStore().SET_DIALOG(false)
    isSuccessFunc.value = true
  }
}

const deleteBase = async () => {
  const params = {
    deptCode: rowList.value.deptCode,
    positionCode: rowList.value.positionCode,
  }

  await salaryStore().DELETE_BASE_SALARY(params)
  await props.fetchData2()
  salaryStore().SET_DIALOG(false)
  isSuccessFunc.value = true
}
</script>

<template>
  <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
    <VDialog v-model="salaryStore().isDialogVisible" max-width="600">
      <!-- Dialog close btn -->
      <DialogCloseBtn @click="salaryStore().SET_DIALOG(false)" />

      <!-- Dialog Content -->
      <VCard title="기준 급여 추가">
        <VCardText>
          <VDivider class="mb-6" />
          <VRow>
            <VCol cols="12" sm="4">
              <AppTextField v-model="rowList.deptName" label="부서명" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="rowList.deptCode" label="직급 번호" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="rowList.positionName" label="직급명" disabled />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="rowList.baseSalary" label="기본급" />
            </VCol>
            <VCol cols="12" sm="4">
              <AppTextField v-model="rowList.hobongRatio" label="호봉인상률" />
            </VCol>
          </VRow>
        </VCardText>
        <VDivider />
        <VCardText class="d-flex justify-end flex-wrap gap-3 mt-2">
          <VBtn @click="editBase">
            수정
          </VBtn>
          <VBtn @click="deleteBase">
            삭제
          </VBtn>
        </VCardText>
      </VCard>
    </VDialog>
  </div>
  <VCard />
  <VSnackbar v-model="iswarningFunc" location="top" :timeout="3000" color="warning">
    <VAlert density="compact" type="warning" title="warning!" text="빈칸을 기입해주세요." />
  </VSnackbar>
  <VSnackbar v-model="isSuccessFunc" location="top" :timeout="3000" color="success">
    <VAlert density="compact" type="success" title="success!" text="완료되었습니다." />
  </VSnackbar>
</template>
