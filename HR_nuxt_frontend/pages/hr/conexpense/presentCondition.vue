<script setup lang="ts">
import { conExpStore } from "@/store/hr/conexpense";
import axios from "axios";
import { ref } from "vue";
import { VDataTable } from "vuetify/labs/VDataTable";
import { useRouter } from "vue-router"; // router 가져오기
import { inject } from "vue";

const router = useRouter();
import { useEmpStore } from "@/store/hr/emp";

const empStore = useEmpStore();
empStore.loadEmpCode();

console.log("현재 로그인된 직원 코드:", empStore.empCode);

// 로그인된 empCode가 'EMP-01'이 아닌 경우 페이지 이동
if (empStore.empCode !== "EMP-01" && empStore.empCode !== "EMP-00") {
  router.push("/hr/confinement/unauthorized");
}

const headers = [
  { title: "신청번호", key: "reqNum" },
  { title: "신청자", key: "empName" },
  { title: "신청일자", key: "reqDate" },
  { title: "경조구분", key: "conType" },
  { title: "관계", key: "rel" },
  { title: "경조(시작)일", key: "conTermStart" },
  { title: "경조(종료)일", key: "conTermEnd" },
  { title: "경조금액", key: "conAmt" },
  { title: "상태", key: "approvalStatus" },
];

const startDate = ref("");
const endDate = ref("");

// const searchEmpCode = ref('EMP-01')
const searchEmpCode = ref("");
const empList = ref([]);
const employeeName = ref("");

const store = conExpStore();
const conExpenseList: Ref<any[]> = ref([]);

const selectedItems = ref([]);

const removeRequest = (reqNum: any) => {
  console.log("오냐");
  store.DELETE_CONEXP(reqNum);
};

const isDeleteAlertVisible = ref(false);

// 직원명 조회
const fetchEmpList = async () => {
  try {
    const res = await axios.get("http://localhost:8282/hr/affair/emp");

    const empData = res.data.empList;

    empList.value = empData.map((e: any) => ({
      title: e.deptName,
      value: { deptName: e.deptName, deptCode: e.deptCode },
    }));
  } catch (error) {
    console.error(error);
  }
};

const fetchEmployeeName = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8282/hr/affair/emp?empCode=${searchEmpCode.value}`
    );

    const targetEmployee = response.data.empList.find(
      (emp: { empCode: string }) => emp.empCode === searchEmpCode.value
    );

    if (targetEmployee) {
      // 찾은 직원의 성명 추출
      const fetchedName = targetEmployee.empName;

      // 성명 업데이트
      employeeName.value = fetchedName;
    } else {
      // 해당 사번에 해당하는 직원이 없을 경우 처리
      employeeName.value = "";
    }
  } catch (error) {
    console.error("Error fetching employee name:", error);
  }
};

// 검색
const searchRequest = async () => {
  console.log("신청내역 검색");

  const reqData: any = {
    startDate: startDate.value,
    endDate: endDate.value,
  };

  if (searchEmpCode.value && searchEmpCode.value.trim() !== "") {
    reqData.searchEmpCode = searchEmpCode.value;
  }

  await store.FETCH_REQ_DETAIL(reqData);
  conExpenseList.value = store.conExpenseList;
};

// 삭제
const deleteRequest = async () => {
  console.log("삭제");
  console.log(selectedItems.value);
  console.log(selectedItems.value[0]); // 20231216REQ0001
  console.log(selectedItems.value[1]); // 한가닥

  await selectedItems.value.forEach((val: any) => {
    removeRequest(val);
  });

  isDeleteAlertVisible.value = true;

  await searchRequest();
};

onMounted(() => {
  fetchEmpList();
  fetchEmployeeName();
});
</script>

<template>
  <h1 class="mb-6">경조비 신청현황</h1>
  <VCard>
    <VContainer>
      <VRow>
        <span class="mt-5 mb-5 ml-3">신청일자</span>
        <VCol cols="12" md="2">
          <AppDateTimePicker
            v-model="startDate"
            append-inner-icon="tabler-calendar"
            placeholder="2024-06-01"
          />
        </VCol>
        <span class="mt-5 mb-5">~</span>
        <VCol cols="12" md="2">
          <AppDateTimePicker
            v-model="endDate"
            append-inner-icon="tabler-calendar"
            placeholder="2024-06-30"
          />
        </VCol>
        <!-- <span class="mt-5 mb-5 ml-3">사원번호</span> -->
        <VCol cols="12" md="2">
          <AppTextField
            v-model="searchEmpCode"
            append-inner-icon="tabler-search"
            placeholder="사원번호"
          />
        </VCol>
        <VCol class="ml-1" cols="12" md="2">
          <VBtn @click="searchRequest"> 검색 </VBtn>
        </VCol>
      </VRow>
    </VContainer>
    <!-- 경조비 신청내역 테이블 -->
    <VCardTitle> ✨ 경조비 신청현황 </VCardTitle>
    <VCardText>
      <VDataTable
        v-model="selectedItems"
        :headers="headers"
        :items="conExpenseList"
        :items-per-page="10"
        item-value="reqNum"
        show-select
      />
    </VCardText>
    <VRow class="text-right mr-2 mb-2">
      <VCol>
        <VBtn
          class="mr-2"
          variant="outlined"
          color="error"
          @click="deleteRequest"
        >
          삭제
        </VBtn>
      </VCol>
    </VRow>
  </VCard>
  <!-- 삭제 알림 스낵바 -->
  <VSnackbar
    v-model="isDeleteAlertVisible"
    location="top"
    color="error"
    variant="tonal"
    timeout="1500"
  >
    삭제되었습니다.
  </VSnackbar>
</template>
