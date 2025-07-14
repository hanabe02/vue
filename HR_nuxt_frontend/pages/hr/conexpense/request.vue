<script setup lang="ts">
import { conExpStore } from "@/store/hr/conexpense";
import axios from "axios";
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

const store = conExpStore();

const flag = ref(false);

const isAddSuccessAlertVisible = ref(false);
const isEditSuccessAlertVisible = ref(false);

// const now = new Date()
// const formattedDate = now.toLocaleString('default', { year: 'numeric', month: '2-digit', day: '2-digit' })

const headers = [
  { title: "신청번호", key: "reqNum" },
  { title: "신청자코드", key: "empCode" },
  { title: "신청일자", key: "reqDate" },
  { title: "경조구분", key: "conType" },
  { title: "대상자", key: "trgt" },
  { title: "경조(시작)일", key: "conTermStart" },
  { title: "경조(종료)일", key: "conTermEnd" },
  { title: "경조금액", key: "conAmt" },
];

const conExpenseList: Ref<any[]> = ref([]);

const startDate = ref("");
const endDate = ref("");

// const searchEmpCode = ref('EMP-01')
const searchEmpCode = ref("");

const empList = ref([]);
const employeeName = ref("");

const relList = [
  { title: "본인", value: "본인" },
  { title: "자녀", value: "자녀" },
  { title: "배우자", value: "배우자" },
  { title: "본인 및 배우자의 부모", value: "본인 및 배우자의 부모" },
  {
    title: "본인 및 배우자의 조부모·외조부모",
    value: "본인 및 배우자의 조부모·외조부모",
  },
  { title: "자녀와 그 자녀의 배우자", value: "자녀와 그 자녀의 배우자" },
  { title: "본인 및 배우자의 형제·자매", value: "본인 및 배우자의 형제·자매" },
];

const bankList = [
  { title: "농협", value: "농협" },
  { title: "국민은행", value: "국민은행" },
  { title: "신한은행", value: "신한은행" },
  { title: "카카오뱅크", value: "카카오뱅크" },
  { title: "우체국", value: "우체국" },
];

const reqDetail = ref({
  reqNum: "NEW",
  empCode: "",
  empName: "",
  reqDate: "",
  conType: "",
  trgt: "",
  conTermStart: "",
  conTermEnd: "",
  conAmt: "",
  rel: "",
  conPlace: "",
  bank: "",
  accNum: "",
  accHolder: "",
  note: "",
});

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
const searchRequest = async (params: any) => {
  console.log("신청내역 검색", params);

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

// 신규 등록 세팅
const addNewConExpense = () => {
  reqDetail.value.reqNum = "NEW";
  reqDetail.value.empCode = "";
  reqDetail.value.empName = "";
  reqDetail.value.reqDate = "";
  reqDetail.value.conType = "";
  reqDetail.value.trgt = "";
  reqDetail.value.conTermStart = "";
  reqDetail.value.conTermEnd = "";
  reqDetail.value.conAmt = "";
  reqDetail.value.rel = "";
  reqDetail.value.conPlace = "";
  reqDetail.value.bank = "";
  reqDetail.value.accNum = "";
  reqDetail.value.accHolder = "";
  reqDetail.value.note = "";

  flag.value = false;
};

// 저장(신규 + 수정)
const submitRequest = async () => {
  console.log("경조비 신청(신규)");

  const response = await store.REGI_CONEXP(reqDetail.value);

  console.log("rr", response);

  if (flag.value) isEditSuccessAlertVisible.value = true;
  else isAddSuccessAlertVisible.value = true;

  addNewConExpense();
};

// 상세보기
const showDetail = (selected: any, row: any) => {
  console.log(store.conExpenseList);
  console.log(store.conExpenseList[0]);
  reqDetail.value.reqNum = store.conExpenseList[0].reqNum;
  reqDetail.value.empCode = store.conExpenseList[0].empCode;
  reqDetail.value.empName = store.conExpenseList[0].empName;
  reqDetail.value.reqDate = store.conExpenseList[0].reqDate;
  reqDetail.value.conType = store.conExpenseList[0].conType;
  reqDetail.value.trgt = store.conExpenseList[0].trgt;
  reqDetail.value.conTermStart = store.conExpenseList[0].conTermStart;
  reqDetail.value.conTermEnd = store.conExpenseList[0].conTermEnd;
  reqDetail.value.conAmt = store.conExpenseList[0].conAmt;
  reqDetail.value.rel = store.conExpenseList[0].rel;
  reqDetail.value.conPlace = store.conExpenseList[0].conPlace;
  reqDetail.value.bank = store.conExpenseList[0].bank;
  reqDetail.value.accNum = store.conExpenseList[0].accNum;
  reqDetail.value.accHolder = store.conExpenseList[0].accHolder;
  reqDetail.value.note = store.conExpenseList[0].note;

  flag.value = true;
};

onMounted(() => {
  fetchEmpList();
  fetchEmployeeName();
});
</script>

<template>
  <h1 class="mb-6">경조비 신청</h1>
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
    <VCardTitle> ✨ 경조비 신청내역 </VCardTitle>
    <VCardText>
      <VDataTable
        :headers="headers"
        :items="conExpenseList"
        :items-per-page="10"
        @click:row="showDetail"
      />
    </VCardText>
  </VCard>
  <br />
  <!-- 경조비 신청 테이블 -->
  <VCard>
    <VCardTitle class="mt-3"> ✨ 경조비 신청 </VCardTitle>

    <VRow class="ma-2">
      <VCol cols="12" md="4">
        <AppTextField v-model="reqDetail.reqNum" label="신청번호" disabled />
      </VCol>
      <VCol cols="12" md="4">
        <AppTextField
          v-model="reqDetail.empCode"
          label="신청자사번"
          placeholder="EMP-00"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppTextField
          v-model="reqDetail.empName"
          label="신청자성명"
          placeholder="홍길동"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppDateTimePicker
          v-model="reqDetail.reqDate"
          append-inner-icon="tabler-calendar"
          label="신청일자"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppSelect
          v-model="reqDetail.conType"
          label="경조구분"
          :items="['결혼', '출산', '사망', '입양']"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppSelect
          v-model="reqDetail.rel"
          label="가족관계"
          placeholder="선택"
          :items="relList"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppTextField v-model="reqDetail.trgt" label="대상자 성명" />
      </VCol>
      <VCol cols="12" md="4">
        <AppDateTimePicker
          v-model="reqDetail.conTermStart"
          append-inner-icon="tabler-calendar"
          label="경조일자(시작일)"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppDateTimePicker
          v-model="reqDetail.conTermEnd"
          append-inner-icon="tabler-calendar"
          label="경조일자(종료일)"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppTextField v-model="reqDetail.conAmt" label="신청금액" />
      </VCol>
      <VCol cols="12" md="8">
        <AppTextField v-model="reqDetail.conPlace" label="경조장소" />
      </VCol>
      <VCol cols="12" md="4">
        <AppSelect
          v-model="reqDetail.bank"
          label="은행"
          placeholder="선택"
          :items="bankList"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppTextField
          v-model="reqDetail.accNum"
          label="계좌번호"
          placeholder="'-'를 제외하고 입력해주세요."
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppTextField
          v-model="reqDetail.accHolder"
          label="예금주"
          placeholder="홍길동"
        />
      </VCol>
      <VCol>
        <AppTextField v-model="reqDetail.note" label="비고" />
      </VCol>
    </VRow>

    <VRow class="text-right mr-2 mb-2">
      <VCol>
        <VBtn
          class="mr-2"
          variant="outlined"
          color="info"
          @click="addNewConExpense"
        >
          신규
        </VBtn>
        <VBtn color="primary" variant="outlined" @click="submitRequest">
          저장
        </VBtn>
      </VCol>
    </VRow>
  </VCard>
  <!-- 신규 등록 알림 스낵바 -->
  <VSnackbar
    v-model="isAddSuccessAlertVisible"
    location="top"
    color="success"
    variant="tonal"
    timeout="1500"
  >
    신청되었습니다.
  </VSnackbar>
  <!-- 신청 수정 알림 스낵바 -->
  <VSnackbar
    v-model="isEditSuccessAlertVisible"
    location="top"
    color="blue-grey"
    variant="tonal"
    timeout="1500"
  >
    수정되었습니다.
  </VSnackbar>
</template>
