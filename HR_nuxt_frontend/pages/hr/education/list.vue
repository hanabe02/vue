<script setup lang="ts">
import { ref, onMounted } from "vue";
import axios from "axios";
import { VDataTable } from "vuetify/labs/VDataTable";

const eduList = ref([]);
const selectedRows = ref([]);

const headers = [
  { title: "교육코드", key: "eduCode" },
  { title: "교육명", key: "eduName" },
  { title: "직원코드", key: "empCode" },
  { title: "직원명", key: "empName" },
  { title: "부서코드", key: "deptCode" },
  { title: "부서명", key: "deptName" },
  { title: "교육 이수 내역", key: "eduHistory" },
  { title: "승인상태", key: "status" },
  { title: "년도", key: "searchYear" },
];

const loadEduList = async () => {
  const response = await axios.get("http://localhost:8282/hr/edu/eduList");
  eduList.value = response.data.eduList;
  // eduList.value = response.data.eduList.filter((edu: any) => edu.status === "미승인");
};

// ✅ 모달 관련 상태
const dialog = ref(false)

const editForm = ref({
  eduCode: '',
  eduName: '',
  empCode: '',
  empName: '',
  deptCode: '',
  deptName: '',
  eduHistory: '형식은 "YYYYMMDD 이수완료"로 입력해주세요. ex)20250416 이수완료',
  searchYear: '',
  status: '',
})

const isSuccessFunc = ref(false)
const snackbarColor = ref("success");  // 스낵바 색상 (기본값: 성공)
const snackbarType = ref("success");  // 스낵바 타입 (기본값: success)
const snackbarText = ref("완료되었습니다.");  // 스낵바 텍스트 (기본값: 완료 메시지)

const defaultEduHistoryText = '형식은 "YYYYMMDD 이수완료"로 입력해주세요. ex)20250416 이수완료'

const handleEduHistoryFocus = () => {
  if (editForm.value.eduHistory === defaultEduHistoryText) {
    editForm.value.eduHistory = ''
  }
}

const handleEduHistoryBlur = () => {
  if (!editForm.value.eduHistory.trim()) {
    editForm.value.eduHistory = defaultEduHistoryText
  }
}

// 수정 버튼 클릭 시 모달 오픈
const openUpdateDialog = () => {
  const hasApproved = selectedRows.value.some(row => row.status === '승인완료');

  if (hasApproved) {
    snackbarColor.value = 'error'
    snackbarText.value = '✅ 이미 승인완료된 항목입니다.'
    snackbarType.value = 'error'
    isSuccessFunc.value = true

    return
  }

  if (selectedRows.value.length !== 1) {
    alert("수정은 하나만 선택해주세요!");
    return;
  }

  editForm.value = { ...selectedRows.value[0] } // 선택 항목 복사

  if (!editForm.value.eduHistory) {
     editForm.value.eduHistory = '형식은 "YYYYMMDD 이수완료"로 입력해주세요. ex)20250416 이수완료'
  }

  dialog.value = true
  selectedRows.value = []
}

// 수정 저장
const saveUpdate = async () => {
  try {
    await axios.post('http://localhost:8282/hr/edu/checkEduHistory', editForm.value)
    dialog.value = false;
    await loadEduList(); // 새로고침
  } catch (error) {
    console.error("수정 실패:", error);
  }
};

// 신청취소 (삭제)
const cancelApplication = async () => {
  const hasApproved = selectedRows.value.some(row => row.status === '승인완료');

  if (hasApproved) {
    snackbarColor.value = 'error'
    snackbarText.value = '✅ 이미 승인완료된 항목입니다.'
    snackbarType.value = 'error'
    isSuccessFunc.value = true

    return
  }

  for (const row of selectedRows.value) {
    const { searchYear, eduCode, empCode, status } = row
    await axios.post('http://localhost:8282/hr/edu/delReqEdu', {
      searchYear,
      eduCode,
      empCode,
      status,
      
      // params: {
      //   searchYear: row.searchYear,
      //   eduCode: row.eduCode,
      //   empCode: row.empCode,
      //   status: row.status
      // }
    })
  }
  await loadEduList();
}

onMounted(() => {
  loadEduList()
})
</script>

<template>
  <h1 class="mb-4">📋 미승인 교육 신청 내역</h1>

  <VCard>
    <VCardText>
      <VBtn class="mr-2" color="primary" @click="openUpdateDialog">수정</VBtn>
      <VBtn color="error" @click="cancelApplication">신청취소</VBtn>

      <VDataTable
        v-model="selectedRows"
        :headers="headers"
        :items="eduList"
        item-value="eduCode"
        show-select
        return-object
        class="mt-4"
        :items-per-page="10"
      />
    </VCardText>
  </VCard>

  <!-- ✅ 수정 다이얼로그 -->
  <VDialog v-model="dialog" max-width="600">
  <VCard>
    <VCardTitle>교육 신청 수정</VCardTitle>
    <VCardText>
      <VForm>
        <VTextField label="교육명" v-model="editForm.eduName" class="mb-4" />
        <VTextarea label="교육 이수 내역" v-model="editForm.eduHistory" @focus="handleEduHistoryFocus" @blur="handleEduHistoryBlur" />
      </VForm>
    </VCardText>
    <VCardActions class="justify-end">
      <VBtn variant="text" @click="dialog = false">취소</VBtn>
      <VBtn color="primary" @click="saveUpdate">저장</VBtn>
    </VCardActions>
  </VCard>
</VDialog>
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
