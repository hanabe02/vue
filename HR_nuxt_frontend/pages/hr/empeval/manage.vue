<script setup lang="ts">
import { evalStore } from "@/store/hr/eval";
import { ref } from "vue";
import { VDataTable } from "vuetify/labs/VDataTable";
import { useRouter } from "vue-router"; // router 가져오기
import { inject } from 'vue';

const router = useRouter();
import { useEmpStore } from '@/store/hr/emp'

const empStore = useEmpStore()
empStore.loadEmpCode()

console.log("현재 로그인된 직원 코드:", empStore.empCode)

// 로그인된 empCode가 'EMP-01'이 아닌 경우 페이지 이동
if (empStore.empCode !== 'EMP-01' && empStore.empCode !== 'EMP-00') {
  router.push('/hr/confinement/unauthorized')
}


const store = evalStore();

const headers = [
  { title: "이름", key: "empName", width: 80 },
  { title: "부서", key: "deptName", width: 80 },
  { title: "직급", key: "positionName", width: 80 },
  { title: "교육", key: "durationOfTraining", width: 80 },
  { title: "자격증", key: "numberOfCertificate", width: 100 },
  { title: "근태", key: "numberOfRestDay", width: 80 },
  { title: "휴가", key: "remainingHoliday", width: 80 },
  { title: "결격여부", key: "disqualification", width: 110 },
  { title: "장기근속", key: "career", width: 110 },
  { title: "총 점수", key: "score", width: 100 },
  { title: "등급", key: "grade", width: 80 },
  { title: "승인상태", key: "status", width: 110 },
];

const isSuccessFunc = ref(false);
const selectYear = ref("");
const selectStatus = ref("");
const selectedRows = ref([]);

const statusList = [
  { title: "미승인", value: "미승인" },
  { title: "승인", value: "승인완료" },
  { title: "반려", value: "승인반려" },
];

const evalList: any = ref([]);

const snackbarColor = ref("success");  // 스낵바 색상 (기본값: 성공)
const snackbarType = ref("success");  // 스낵바 타입 (기본값: success)
const snackbarText = ref("완료되었습니다.");  // 스낵바 텍스트 (기본값: 완료 메시지)

const resetSelection = () => {
  selectedRows.value = [];
};

const searchEvalStatus = async () => {
  try {
    const searchYear = selectYear.value;
    const status = selectStatus.value;

    const selectData = {
      searchYear,
      status,
    };

    console.log(selectData);

    await store.SEARCH_EMP_EVAL_STATUS(selectData);
    evalList.value = await store.evalList;
    console.log(evalList.value);
  } catch (error: any) {
    throw new Error(error);
  }
};

const approveEmpEval = async () => {
  console.log("승인");
  console.log("one", selectedRows.value);

  for (const row of selectedRows.value) {
    const { searchYear, empCode } = row;

    const approvedEval = {
      searchYear,
      empCode,
      status: "승인완료",
    };

    await store.EDIT_EMP_EVAL(approvedEval);
  }

  searchEvalStatus();
  resetSelection();
  // isSuccessFunc.value = true;
};

const referEmpEval = async () => {
  console.log("반려");

  console.log("two", selectedRows.value);

  for (const row of selectedRows.value) {
    const { searchYear, empCode } = row;

    const referEval = {
      searchYear,
      empCode,
      status: "승인반려",
    };

    await store.EDIT_EMP_EVAL(referEval);
  }

  searchEvalStatus();
  resetSelection();
  // isSuccessFunc.value = true;
};

const deleteEmpEval = async () => {
  console.log("삭제");
  console.log("three", selectedRows.value);

  for (const row of selectedRows.value) {
    if (row.status === '승인완료' || row.status === '승인반려') {
      // 상태가 '승인완료' 또는 '승인반려'인 경우 삭제 불가 메시지 표시
      snackbarColor.value = "error";
      snackbarType.value = "error";
      snackbarText.value = "승인완료 및 승인반려 상태인 인사고과 정보는 삭제할 수 없습니다.";
      isSuccessFunc.value = true;  // 스낵바 표시

      console.log('승인완료 및 승인반려 상태인 인사고과 정보는 삭제할 수 없습니다.');
      return;
    }
  }
  try {
    // 삭제 작업 전에 초기화 작업 또는 필요한 작업 수행

    for (const row of selectedRows.value) {
      const { searchYear, empCode } = row;

      const removeEval = {
        searchYear,
        empCode,
      };

      await store.DELETE_EMP_EVAL(removeEval);
    }

    searchEvalStatus();
    isSuccessFunc.value = true;
    resetSelection();
  } catch (error) {
    console.error("삭제 중 오류 발생:", error.message);
  }

  // evalList.value = evalList.value.filter(
  //   (item) => !selectedRows.value.includes(item)
  // );

  // selectedRows.value = [];
};
</script>

<template>
  <h1 class="mb-6">인사고과 관리</h1>
  <VCard>
    <br />
    <VRow>
      <VCol>
        <VSelect
          v-model="selectYear"
          class="ml-4"
          label="검색년도"
          :items="['2020', '2021', '2022', '2023', '2024', '2025']"
          variant="outlined"
        />
      </VCol>
      <VCol>
        <VSelect
          v-model="selectStatus"
          label="승인상태"
          :items="statusList"
          variant="outlined"
        />
      </VCol>
      <VCol>
        <VBtn color="primary" @click="searchEvalStatus"> 승인상태 조회 </VBtn>
      </VCol>
    </VRow>
    <VDataTable
      v-model="selectedRows"
      class="mt-8"
      :headers="headers"
      :items="evalList"
      :items-per-page="10"
      show-select
      item-value="empCode"
      return-object
    />
    <VRow>
      <VCol>
        <VBtn class="ma-1" color="primary" @click="approveEmpEval"> 승인 </VBtn>
        <VBtn class="ma-1" color="secondary" @click="referEmpEval"> 반려 </VBtn>
        <VBtn class="ma-1" color="error" @click="deleteEmpEval"> 삭제 </VBtn>
      </VCol>
    </VRow>
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
  <!-- <VSnackbar
    v-model="isSuccessFunc"
    location="top"
    :timeout="3000"
    color="success"
  >
    <VAlert
      density="compact"
      type="success"
      title="success!"
      text="완료되었습니다."
    />
  </VSnackbar> -->
  <br>
  <VCard title="<등급 산정 기준>">
      <VCardText>
        <span>근태</span>
        <p>결근 없음, +20 / 1~2일 결근, +16 / 3~4일 결근, +12 / 5일 이상 결근, +8 / 자주 지각, +10 이하</p>
        <span>직급</span>
        <p>사원, +10 / 대리, +12 / 과장, +15 / 차장, +18 / 부장 이상, +20</p>
        <span>결격(징계 등)여부</span>
        <p>결격 사유 없음, +10 / 결격 사유 있음, +0</p>
        <span>교육이수</span>
        <p>직무 관련 교육 이수 5회 이상, +15 / 직무 관련 교육 이수 3~4회, +10 / 직무 관련 교육 이수 1~2회, +6 / 직무 관련 교육 이수내역 없음, +0</p>
        <span>근속년수</span>
        <p>10년 이상, +10 / 5~9년 근속, +7 / 1~4년 근속, +5 / 1년 미만 근속, +2</p>
        <span>자격증</span>
        <p>직무 관련 자격증 보유, +15 / 일부 관련 자격증 보유, +6 / 자격증 없음, +0</p>
        <span>휴가 사용</span>
        <p>휴가 사용 적절, +10 / 과도한 휴가 사용, +5 / 휴가 사용 적지 않음, +2</p>
        <span>등급산정</span>
        <p>
          90점 이상, S / 90점 미만 ~ 80점 이상, A / 80점 미만 ~ 70점 이상, B / 70점 미만 ~ 60점 이상, C / 60점 미만 ~ 50점 이상, D / 50점 미만, E
        </p>
      </VCardText>
    </VCard>

</template>
