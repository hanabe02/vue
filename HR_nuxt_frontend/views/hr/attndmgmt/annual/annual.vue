<script setup lang="ts">
import { attenStore } from "@/store/hr/attendance";
import { baseStore } from "@/store/hr/base";
import type {
  AttenHeaders,
  DeptItemProps,
  EmpItemProps,
} from "@/types/attendance/types";
import { VDataTable } from "vuetify/lib/labs/VDataTable/index.mjs";

const selectedSearchType = ref("");
const selectedDeptCode = ref("");
const selectedEmpCode = ref("");
const selectedStatus = ref("");
const dept_list: any = ref([]);
const emp_list_by_dept: any = ref([]);
const selectedMonth = ref("");
const thisYear = new Date().getFullYear();
const monthsProps: any = ref([]);
const applyStatusType = ["ALL", "Y", "N"];
const annual_vacation_mgt_list = ref([]);
const selectedRows = ref([]);
const updatedRows = ref({});
const rejectedRows = ref({});

const annualProps: AttenHeaders[] = [
  { title: "사원명", key: "empName" },
  { title: "적용년월", key: "applyYearMonth" },
  { title: "연차사용개수", key: "monthlyLeave" },
  { title: "남은연차", key: "remainingHoliday" },
  { title: "마감여부", key: "finalizeStatus" },
];

onMounted(() => {
  makeMonths();
  fetchDeptList();
});

const makeMonths = () => {
  const years = [2021, 2022, 2023, 2024]; // 원하는 년도들을 배열로 저장

  years.forEach((year) => {
    for (let i = 1; i < 13; i++) {
      const monthValue = i < 10 ? `0${i}` : `${i}`;

      monthsProps.value.push({
        value: `${year}${monthValue}`,
        title: `${year}년 ${monthValue}월`,
      });
    }
  });

  console.log("[monthsProps]", monthsProps.value);
};

// 부서코드가 변경될때마다 함수 실행
watch(selectedDeptCode, async () => {
  await fetchEmpList();
});

// 부서리스트 조회
const fetchDeptList = async () => {
  await baseStore().FETCH_DEPTLIST();
  dept_list.value = Object.values(baseStore().deptList).map(
    (item: DeptItemProps) => {
      return { title: item.deptName, value: item.deptCode };
    }
  );
  console.log(dept_list.value);
};

// 부서의 사원 조회
const fetchEmpList = async () => {
  await attenStore().FETCH_EMPLIST_BY_DEPT(selectedDeptCode.value);
  emp_list_by_dept.value = Object.values(attenStore().empList).map(
    (item: EmpItemProps) => {
      return { title: item.empName, value: item.empCode };
    }
  );
};

const fetchAnnualVactionMgtList = async () => {
  await attenStore().SEARCH_ANNUAL_VACTION_MGTLIST({
    empCode: selectedEmpCode.value,
    year: selectedMonth.value,
  });
  annual_vacation_mgt_list.value = attenStore().annualVacationMgtList;
};

const searchAnnual = () => {
  fetchAnnualVactionMgtList();
};

// 연차 승인/취소
const updateStatus = async (title: string) => {
  console.log("휴가 승인 클릭");
  console.log("selectedRows", selectedRows.value);
  // 각 행도 프록시이고 배열도 프록시상태이므로 map으로 각각의 행의 프록시 상태를 객체상태로 변환해줌
  updatedRows.value = selectedRows.value.map((row: any) => {
    return { ...row };
  });
  console.log("updatedRows", updatedRows.value);
  await attenStore()
    .UPDATE_VACATION_APPLYSTATUS(updatedRows.value)
    .then((res) => console.log(res.data))
    .catch((e) => console.error(e));
  switch (title) {
    case "휴가 승인":
      console.log("휴가 승인 클릭");
      alert("승인 완료되었습니다.");
      break;
    case "승인 취소":
      console.log("승인 취소 클릭");
      alert("승인취소 완료되었습니다.");
      break;
    default:
      break;
  }
  selectedRows.value = [];
  fetchAnnualVactionMgtList();
};
</script>

<template>
  <VContainer>
    <VRow>
      <VCol cols="12" md="3" offset-md="5">
        <VBtn class="mt-6 ml-9" height="43" width="200" @click="searchAnnual">
          <VIcon start icon="tabler-search" />연차신청 내역조회
        </VBtn>
      </VCol>
      <VCol cols="12" md="2">
        <VBtn
          class="mt-6"
          height="43"
          width="150"
          @click="updateStatus('휴가 승인')"
        >
          <VIcon start icon="tabler-square-key" />휴가 승인
        </VBtn>
      </VCol>
      <VCol cols="12" md="2">
        <VBtn
          class="mt-6"
          height="43"
          width="150"
          @click="updateStatus('승인 취소')"
        >
          <VIcon start icon="tabler-square-x" />승인 취소
        </VBtn>
      </VCol>
    </VRow>
    <VRow class="mt-6">
      <VCol cols="12" md="4">
        <AppSelect
          v-model="selectedDeptCode"
          :items="dept_list"
          label="부서조회"
          placeholder="부서조회"
          clearable
          :disabled="selectedSearchType === '전체사원조회'"
          clear-icon="tabler-x"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppSelect
          v-model="selectedEmpCode"
          :items="emp_list_by_dept"
          label="사원조회"
          placeholder="사원조회"
          clearable
          :disabled="selectedSearchType === '전체사원조회'"
          clear-icon="tabler-x"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppSelect
          v-model="selectedMonth"
          :items="monthsProps"
          label="년월선택"
          placeholder="년월선택"
          clearable
          clear-icon="tabler-x"
        />
      </VCol>
    </VRow>
    <VRow class="mt-6">
      <VCol>
        <VDataTable
          :headers="annualProps"
          :items="annual_vacation_mgt_list"
          :items-per-page="10"
          show-select
          return-object
          v-model="selectedRows"
          item-value="empCode"
        />
      </VCol>
    </VRow>
  </VContainer>
</template>
