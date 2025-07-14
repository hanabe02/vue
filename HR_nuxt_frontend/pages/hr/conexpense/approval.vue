<script setup lang="ts">
import { conExpStore } from "@/store/hr/conexpense";
import { useEmpStore } from "@/store/hr/emp";
import axios from "axios";
import { ref, onMounted } from "vue";
import { VDataTable } from "vuetify/labs/VDataTable";
import { useRouter } from "vue-router";

const router = useRouter();

const empStore = useEmpStore();
empStore.loadEmpCode();

console.log("현재 로그인된 직원 코드:", empStore.empCode);

// 로그인된 empCode가 'EMP-01'이 아닌 경우 페이지 이동
if (empStore.empCode !== "EMP-01" && empStore.empCode !== "EMP-00") {
  router.push("/hr/confinement/unauthorized");
}

const store = conExpStore();
const conExpenseList = ref([]);
const selectedItems = ref([]);

const startDate = ref("");
const endDate = ref("");
const searchEmpCode = ref("");
const employeeName = ref("");

const headers = [
  { title: "신청번호", key: "reqNum" },
  { title: "신청자", key: "empName" },
  { title: "신청일자", key: "reqDate" },
  { title: "경조구분", key: "conType" },
  { title: "관계", key: "rel" },
  { title: "경조일(시작)", key: "conTermStart" },
  { title: "경조일(종료)", key: "conTermEnd" },
  { title: "금액", key: "conAmt" },
  { title: "상태", key: "approvalStatus" },
];

// 신청목록 검색
const searchRequest = async () => {
  const reqData: any = {
    startDate: startDate.value,
    endDate: endDate.value,
    searchEmpCode: searchEmpCode.value.trim() !== "" ? searchEmpCode.value : "undefined"
  };

  await store.FETCH_REQ_DETAIL(reqData);
  conExpenseList.value = store.conExpenseList;
};

// 승인처리
const approveRequests = async () => {
  await store.APPROVE_CONEXP(selectedItems.value);
  await searchRequest();
};

// 반려처리
const rejectRequests = async () => {
  await store.REJECT_CONEXP(selectedItems.value);
  await searchRequest();
};


onMounted(() => {
  searchRequest();
});
</script>

<template>
  <h1 class="mb-6">경조비 신청승인</h1>

  <VCard>
    <VContainer>
      <VRow>
        <span class="mt-5 ml-3">신청일자</span>
        <VCol cols="12" md="2">
          <AppDateTimePicker
            v-model="startDate"
            append-inner-icon="tabler-calendar"
          />
        </VCol>
        <span class="mt-5">~</span>
        <VCol cols="12" md="2">
          <AppDateTimePicker
            v-model="endDate"
            append-inner-icon="tabler-calendar"
          />
        </VCol>
        <VCol cols="12" md="2">
          <AppTextField
            v-model="searchEmpCode"
            append-inner-icon="tabler-search"
            placeholder="사원번호"
          />
        </VCol>
        <VCol cols="12" md="2">
          <VBtn color="primary" @click="searchRequest">검색</VBtn>
        </VCol>
      </VRow>
    </VContainer>

    <VCardTitle>✨ 신청 승인 대기 목록</VCardTitle>
    <VCardText>
      <VDataTable
        v-model="selectedItems"
        :headers="headers"
        :items="conExpenseList"
        item-value="reqNum"
        show-select
        :items-per-page="10"
      />
    </VCardText>

    <VRow class="text-right mr-2 mb-2">
      <VCol>
        <VBtn
          variant="outlined"
          color="success"
          class="mr-2"
          @click="approveRequests"
        >
          승인
        </VBtn>
        <VBtn variant="outlined" color="error" @click="rejectRequests">
          반려
        </VBtn>
      </VCol>
    </VRow>
  </VCard>
</template>
