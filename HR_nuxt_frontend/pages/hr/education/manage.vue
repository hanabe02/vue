<script setup lang="ts">
import { eduStore } from "@/store/hr/edu";
import { ref } from "vue";
import { VDataTable } from "vuetify/labs/VDataTable";

const router = useRouter();
import { useEmpStore } from '@/store/hr/emp'

const empStore = useEmpStore()
empStore.loadEmpCode()

console.log("현재 로그인된 직원 코드:", empStore.empCode)

// 로그인된 empCode가 'EMP-01'이 아닌 경우 페이지 이동
if (empStore.empCode !== 'EMP-01' && empStore.empCode !== 'EMP-00') {
  router.push('/hr/confinement/unauthorized')
}


const store = eduStore()

const selectYear = ref('')
const startDate = ref('')
const endDate = ref('')
const selectStatus = ref('')
const selectedRows = ref([])
const dataTableRef = ref(null)

const eduList: any = ref([])

const headers = [
  { title: '교육코드', key: 'eduCode', width: 100 },
  { title: '교육명', key: 'eduName', width: '150px' },
  { title: '직원코드', key: 'empCode', width: 120 },
  { title: '직원명', key: 'empName', width: 100 },
  { title: '부서코드', key: 'deptCode', width: 120 },
  { title: '부서명', key: 'deptName', width: 120 },
  { title: '교육 이수 내역', key: 'eduHistory', width: '150px' },
  { title: '승인상태', key: 'status', width: 120 },
  { title: '검색년도', key: 'searchYear', width: '150px' },
]

const statusList = [
  { title: '미승인', value: '신청완료' },
  { title: '승인요청', value: '승인요청' },
  { title: '반려', value: '승인반려' },
  { title: '승인완료', value: '승인완료' },
]

const isSuccessFunc = ref(false)
const snackbarColor = ref("success");  // 스낵바 색상 (기본값: 성공)
const snackbarType = ref("success");  // 스낵바 타입 (기본값: success)
const snackbarText = ref("완료되었습니다.");  // 스낵바 텍스트 (기본값: 완료 메시지)

const resetSelection = () => {
  selectedRows.value = []
}

const searchEduStatus = async () => {
  try {
    // const searchYear = selectYear.value
    const status = selectStatus.value
    const sDate = startDate.value
    const eDate = endDate.value

    const selectData = {
      // searchYear,
      sDate,
      eDate,
      status,
    }

    console.log(selectData)

    await store.SEARCH_EDU_LIST_STATUS(selectData)
    eduList.value = Array.isArray(store.selEduList) ? store.selEduList : []
    console.log('조회 성공', eduList.value)
  } catch (error: any) {
    throw new Error(error)
  }
  resetSelection()
}

const approvalRequest = async () => {
  if (empStore.empCode !== 'EMP-00') {
    snackbarColor.value = 'error';
    snackbarText.value = '⛔ 권한이 없습니다. 관리자에게 문의해주세요.';
    snackbarType.value = 'error';
    isSuccessFunc.value = true;
    return;
  }
  console.log('승인요청 실행', selectedRows.value)

  const hasApproved = selectedRows.value.some(row => row.status === '승인완료');

  if (hasApproved) {
    snackbarColor.value = 'error'
    snackbarText.value = '✅ 이미 승인완료된 항목입니다.'
    snackbarType.value = 'error'
    isSuccessFunc.value = true

    return
  }

  for (const row of selectedRows.value) {
    const { searchYear, eduCode } = row

    const approvalEdu = {
      searchYear,
      eduCode,
      status: '승인요청',
    }

    await store.APPRO_EDU_LIST(approvalEdu)
  }

  searchEduStatus()
  resetSelection()
}

const deleteEdu = async () => {
  console.log('삭제실행', selectedRows.value)

  const hasApproved = selectedRows.value.some(row => row.status === '승인완료');
  const hasRequested = selectedRows.value.some(row => row.status === '승인요청');
  const hasRejected = selectedRows.value.some(row => row.status === '반려');

  if (hasApproved) {
    snackbarColor.value = "error";
    snackbarText.value = "✅ 승인완료된 항목은 삭제할 수 없습니다.";
    snackbarType.value = "error";
    isSuccessFunc.value = true;
    return;
  }

  if (hasRequested) {
    snackbarColor.value = "warning";
    snackbarText.value = "⚠️ 승인요청 중인 항목은 삭제할 수 없습니다.";
    snackbarType.value = "warning";
    isSuccessFunc.value = true;
    return;
  }

  if (hasRejected) {
    snackbarColor.value = "info";
    snackbarText.value = "ℹ️ 승인반려 항목은 관리자 확인 후 삭제 가능합니다.";
    snackbarType.value = "info";
    isSuccessFunc.value = true;
    return;
  }

  try {
    for (const row of selectedRows.value) {
      const { searchYear, eduCode, empCode, status } = row

      const removeEdu = {
        searchYear,
        eduCode,
        empCode,
        status,
      }

      await store.DELETE_EDU_LIST(removeEdu)
    }

    searchEduStatus()
    await nextTick()
    resetSelection()
  }
  catch (error) {
    console.error('삭제 중 오류 발생:', error.message)
  }
}

watch(selectedRows, (newVal) => {
  console.log('[선택된 행]', newVal)
})
</script>

<template>
    <h1 class="mb-6">📋 교육 관리 및 승인요청</h1>
      <VCard class="pa-6">
        <VRow class="align-end mb-4">
      <!-- <VCol cols="12" md="2">
        <VSelect
          v-model="selectYear"
          class="ml-4"
          label="검색년도"
          :items="['2020', '2021', '2022', '2023', '2024', '2025']"
          variant="outlined"
        />
      </VCol> -->
      <VCol cols="12" sm="3" mt="4">
        <!-- <AppTextField v-model="startDate" type="date" label="시작일" placeholder="시작일"   variant="outlined" dense  hide-details  style="padding: 6px 10px"/> -->
        <VTextField
      v-model="startDate"
      type="date"
      label="시작일"
      placeholder="시작일"
      variant="outlined"
      density="compact"
      class="compact-textfield"
    />
 </VCol>
      <VCol cols="12" sm="3" mt="4">
        <VTextField
    v-model="endDate"
    type="date"
    label="종료일"
    placeholder="종료일"
    variant="outlined"
    density="compact"
    class="compact-textfield"
  />
        <!-- <AppTextField v-model="endDate" type="date" label="종료일" placeholder="종료일" /> -->
      </VCol>
      <VCol cols="12" md="2">
        <VSelect
          v-model="selectStatus"
          label="승인상태"
          :items="statusList"
          variant="outlined"
          density="compact"
          class="compact-textfield"
        />
      </VCol>
      <!-- <VCol cols="12" sm="6" class="d-flex justify-end align-end"> -->
      <VCol cols="12" class="d-flex justify-end align-end">
      <VBtn class="ma-1" color="primary" @click="searchEduStatus"> 조회 </VBtn>
    <VBtn class="ma-1" color="success" @click="approvalRequest"> 승인요청 </VBtn>
    <VBtn class="ma-1" color="error" @click="deleteEdu"> 삭제 </VBtn>
      </VCol>
    </VRow>
      <VDataTable
      ref="dataTableRef"
      v-model="selectedRows"
      class="mt-8"
      :headers="headers"
      :items="eduList"
      :items-per-page="10"
      show-select
      item-value="eduCode"
      return-object
    />
</VCard>
<VSnackbar
    v-model="isSuccessFunc"
    location="top"
    :timeout="3000"
    :color="snackbarColor"
  >
    <VAlert
      density="compact"
      :type="snackbarType"
      title="알림"
      :text="snackbarText"
    />
  </VSnackbar>
</template>
