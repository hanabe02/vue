<script setup lang="ts">
import axios from "axios";
import { onMounted, ref } from "vue";
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


const headers = [
  { title: "사번", key: "empCode" },
  { title: "직원명", key: "empName" },
  { title: "부서이름", key: "deptName" },
];

// 부서리스트 조회
const deptOptions = [
  { title: "대기발령", key: "DPT-00" },
  { title: "총무부", key: "DPT-01" },
  { title: "영업부", key: "DPT-02" },
  { title: "생산부", key: "DPT-03" },
  { title: "구매부", key: "DPT-04" },
  { title: "인사부", key: "DPT-05" },
  { title: "개발부", key: "DPT-07" },
  { title: "기획부", key: "DPT-08" },
  { title: "홍보부", key: "DPT-09" },
  { title: "기술부", key: "DPT-10" },
  { title: "파견부서", key: "DPT-11" },
];

// 근무지리스트 조회
const WKPOptions = [
  { title: "(주)세계전자 본사", key: "BRC-01" },
  { title: "(주)세계전자 울산지점", key: "BRC-02" },
];

const appointEmp = [
  { title: "사원이름", key: "empName" },
  { title: "근무부서", key: "deptName" },
  { title: "근무지", key: "workPlaceName" },
  { title: "직급", key: "positionName" },
  { title: "발령부서", key: "appointDept" },
  { title: "발령근무지", key: "appointWP" },
  { title: "승진직급", key: "appointLevel" },
  { title: "호봉승급", key: "appointHobong" },
  { title: "발령", key: "appointType" },
  { title: "발령일", key: "appointDate" },
];

const levelOptions = [
  { title: "사장", value: "POS-01" },
  { title: "이사", value: "POS-02" },
  { title: "부장", value: "POS-03" },
  { title: "차장", value: "POS-04" },
  { title: "과장", value: "POS-05" },
  { title: "대리", value: "POS-06" },
  { title: "사원", value: "POS-07" },
  { title: "인턴", value: "POS-08" },
  { title: "계약직", value: "POS-09" },
];

const hobongOptions = [];

for (let i = 1; i <= 30; i++) {
  hobongOptions.push({
    title: `${i}호봉`,
    key: `HB-${i.toString().padStart(2, "0")}`,
  });
}

const typeOptions = [
  { title: "승진", key: "promoted" },
  { title: "전근", key: "transfer" },
  { title: "전보", key: "transference" },
  { title: "퇴직", key: "retirement" },
];

const isSuccessFunc = ref(false);
const empList = ref([]);
const selectedRows = ref([]);
const searchQuery = ref("");

const handleSearch = ref({
  enabled: true,
  trigger: "enter",
  skipDiacrities: true,
  placeholder: "직원 정보",
});

// 직원리스트 조회
const fetchData = async () => {
  try {
    const response = await axios.get("http://localhost:8282/hr/affair/emp");

    console.log("[empList]", response.data.empList);
    empList.value = response.data.empList;
  } catch (error) {
    console.error(error);
  }
};

const appRegi = async () => {
  console.log(selectedRows.value);

  // 등록 버튼 클릭 시 수행할 동작
  console.log("발령을 등록합니다.");

  try {
    // selectedRow에 있는 데이터를 서버로 전송 또는 원하는 동작 수행
    await axios.post(
      "http://localhost:8282/hr/affair/appoint",
      selectedRows.value
    );

    selectedRows.value = [];

    fetchData();
    isSuccessFunc.value = true;
  } catch (error) {
    console.error("발령 등록 에러: ", error);
  }
};

const cancelApplication = () => {
  // 취소 버튼이 클릭되었을 때 수행할 동작
  console.log("발령 신청을 취소합니다.");
  selectedRows.value = [];
};

onMounted(() => {
  fetchData();
});
</script>

<template>
  <h1 class="mb-6">인사발령 신청</h1>
  <VCard>
    <VContainer>
      <!-- 직원 전체 조회 table -->
      <VTextField
        v-model="searchQuery"
        density="compact"
        placeholder="직원 정보 검색"
        append-inner-icon="tabler-search"
        single-line
        hide-details
        dense
        outlined
        @input="handleSearch"
      />
      <VDataTable
        v-model="selectedRows"
        :headers="headers"
        :items="empList"
        :items-per-page="5"
        :footer-props="{
          'items-per-page-options': [5, 10, 20],
          'show-current-page': true,
          'show-first-last-page': true,
        }"
        :search="searchQuery"
        align="center"
        show-select
        item-value="empCode"
        return-object
      >
        <template #item.deptName="{ item }">
          {{ item.deptName }}
        </template>
      </VDataTable>
    </VContainer>
  </VCard>
  <br />
  <div>
    <h2>발령 대상자</h2>
  </div>
  <VCard>
    <VContainer>
      <VDataTable :headers="appointEmp" :items="selectedRows">
        <template #item.appointDept="{ item }">
          <VSelect v-model="item.appointDept" :items="deptOptions" />
        </template>
        <template #item.appointWP="{ item }">
          <VSelect v-model="item.appointWP" :items="WKPOptions" />
        </template>
        <template #item.appointLevel="{ item }">
          <VSelect v-model="item.appointLevel" :items="levelOptions" />
        </template>
        <template #item.appointHobong="{ item }">
          <VSelect v-model="item.appointHobong" :items="hobongOptions" />
        </template>
        <template #item.appointType="{ item }">
          <VSelect v-model="item.appointType" :items="typeOptions" />
        </template>

        <template #item.appointDate="{ item }">
          <VRow>
            <VCol>
              <AppDateTimePicker
                v-model="item.appointDate"
                show-adjacent-months
                show-decade-navs
                button-only
                right
              />
            </VCol>
          </VRow>
        </template>
      </VDataTable>
    </VContainer>
  </VCard>
  <VRow>
    <VCol>
      <VBtn class="ma-2" color="primary" @click="appRegi"> 등록 </VBtn>
      <VBtn color="secondary" @click="cancelApplication"> 취소 </VBtn>
    </VCol>
  </VRow>
  <VSnackbar
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
  </VSnackbar>
</template>
