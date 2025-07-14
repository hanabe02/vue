<script setup lang="ts">
import { attenStore } from "@/store/hr/attendance";
import type { AttenHeaders } from "@/types/attendance/types";
import { VDataTable } from "vuetify/lib/labs/VDataTable/index.mjs";

const selectedYearMonth = ref("");
const thisYear = new Date().getFullYear();
const monthsProps: any = ref([]);
const annual_vacation_mgt_result = ref([]);

const annualProps: AttenHeaders[] = [
  { title: "사원명", key: "empName" },
  { title: "적용년월", key: "applyYearMonth" },
  { title: "연차사용개수", key: "monthlyLeave" },
  { title: "남은연차", key: "remainingHoliday" },
  { title: "마감여부", key: "finalizeStatus" },
];

onMounted(() => {
  makeYearMonths();
});

const makeYearMonths = () => {
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

const createAnnual = async () => {
  await attenStore()
    .BETCH_VACATION_CREATE(selectedYearMonth.value)
    .then((res) => {
      console.log("annualVacationMgtResult", res.data.annualVacationMgtResult);
      annual_vacation_mgt_result.value = res.data.annualVacationMgtResult;
    })
    .catch((e) => console.error(e));
  alert("연차 사용개수를 조회했습니다.");
};
</script>

<template>
  <VContainer>
    <VRow>
      <VCol cols="12" md="3" offset-md="6">
        <AppSelect
          v-model="selectedYearMonth"
          :items="monthsProps"
          label="년월선택"
          placeholder="년월선택"
          clearable
          clear-icon="tabler-x"
        />
      </VCol>
      <VCol cols="12" md="3">
        <VBtn class="mt-6 ml-3" height="43" width="230" @click="createAnnual">
          <VIcon start icon="tabler-search" />연차사용횟수 조회
        </VBtn>
      </VCol>
    </VRow>
    <VRow class="mt-6">
      <VCol>
        <VDataTable
          :headers="annualProps"
          :items="annual_vacation_mgt_result"
          :items-per-page="10"
        />
      </VCol>
    </VRow>
  </VContainer>
</template>
