<script lang="ts" setup>
import { dailyLaborStore } from "@/store/hr/dailylabor";
import type {
  DailyLaborSalary,
  Headers,
  SalaryData,
} from "@/types/dailylabor/types";
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



const search = ref("");
const isSuccessFunc = ref(false);
const DailyLaborStore = dailyLaborStore();
const dateTimePicker = ref("");
const dailyLaborSalaryList = ref<DailyLaborSalary[]>([]);
const selectedRows = ref([]);

const headers: Headers[] = [
  { title: "사번", key: "employeeId", align: "center" },
  { title: "직원명", key: "employeeName", align: "center" },
  { title: "총 급여", key: "totalPay", align: "center" },
  { title: "정상급여", key: "normalPay", align: "center" },
  { title: "연장급여", key: "overtimePay", align: "center" },
  { title: "야간급여", key: "nightPayRate", align: "center" },
];

const fetchData = async () => {
  const payload = {
    date: dateTimePicker.value,
    deadline: "N",
    status: "N",
  };

  await DailyLaborStore.FIND_SALARY_LIST(payload);
  dailyLaborSalaryList.value = await DailyLaborStore.dailyLaborSalaryList;
};

const producebtn = async () => {
  const payload = {
    workDate: dateTimePicker.value,
  };

  await DailyLaborStore.INSERT_SALARY_LIST(payload);

  await fetchData();
  isSuccessFunc.value = true;
};

const registerbtn = async () => {
  const payload: SalaryData[] = [];

  selectedRows.value.forEach((element: DailyLaborSalary) => {
    const slaryData: SalaryData = {
      employeeId: element.employeeId,
      workDate: element.workDate.substring(0, 10),
    };

    payload.push(slaryData);
  });

  await DailyLaborStore.UPDATE_SALARY_LIST(payload);
  await fetchData();
  isSuccessFunc.value = true;
};

const deletebtn = async () => {
  const payload: SalaryData[] = [];

  selectedRows.value.forEach((element: DailyLaborSalary) => {
    const slaryData: SalaryData = {
      employeeId: element.employeeId,
      workDate: element.workDate.substring(0, 10),
    };

    payload.push(slaryData);
  });
  await DailyLaborStore.DELETE_SALARY_LIST(payload);
  await fetchData();
  isSuccessFunc.value = true;
};

const filteredData = computed(() => {
  return dailyLaborSalaryList.value.filter((emp: any) => {
    return Object.values(emp).some((field) => {
      if (typeof field === "string")
        return field.toLowerCase().includes(search.value.toLowerCase());

      return false;
    });
  });
});

watch([dateTimePicker], fetchData, { immediate: true });
</script>

<template>
  <h1 class="mb-6">일용근로 급여등록</h1>
  <VCard>
    <VCardText class="d-flex flex-wrap py-4 gap-4">
      <VCol>
        <div style="inline-size: 10rem">
          <AppTextField
            v-model="search"
            density="compact"
            placeholder="검색"
            append-inner-icon="tabler-search"
            single-line
            hide-details
            dense
            outlined
          />
        </div>
      </VCol>
      <VSpacer />
      <VCol md="2">
        <AppDateTimePicker
          v-model="dateTimePicker"
          placeholder="급여등록 날짜 검색"
        />
      </VCol>
      <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
        <VBtn @click="producebtn">
          <VIcon start icon="tabler-checkbox" />생성
        </VBtn>
        <VBtn @click="registerbtn">
          <VIcon start icon="tabler-checkbox" />등록
        </VBtn>
        <VBtn @click="deletebtn">
          <VIcon start icon="tabler-square-x" />삭제
        </VBtn>
      </div>
    </VCardText>
    <VDivider />
    <VDataTable
      v-model="selectedRows"
      :headers="headers"
      :items="filteredData"
      :items-per-page="5"
      :footer-props="{
        'items-per-page-options': [5, 10, 20],
        'show-current-page': true,
        'show-first-last-page': true,
      }"
      show-select
      item-value="employeeId"
      return-object
    >
      <template #item.deptName="{ item }">
        {{ item.deptName }}
      </template>
    </VDataTable>
  </VCard>
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
