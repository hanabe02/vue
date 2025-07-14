<script setup lang="ts">
import { attenStore } from "@/store/hr/attendance";
import type { AttenHeaders } from "@/types/attendance/types";
import { VDataTable } from "vuetify/labs/VDataTable";

const date = ref("");
const dayAttdMgtList = ref([]);
const dayRegiselect = ref([]);

const headers: AttenHeaders[] = [
  { key: "applyDays", title: "날짜", width: 120 },
  { key: "empName", title: "사원명", width: 120 },
  { key: "attendTime", title: "시작시간", width: 120 },
  { key: "quitTime", title: "종료시간", width: 120 },
  { key: "workHour", title: "근무시간", width: 120 },
  { key: "halfHolidayStatus", title: "반차여부", width: 120 },
  { key: "lateWhether", title: "지각여부", width: 120 },
  { key: "overWorkHour", title: "연장근무", width: 120 },
  { key: "nightWorkHour", title: "심야근무", width: 120 },
  { key: "earlyLeaveHour", title: "조퇴시간", width: 120 },
  { key: "leaveHour", title: "외출시간", width: 120 },
  { key: "finalizeStatus", title: "승인여부", width: 120 },
];

// 마감관리 - 등록마감
const regiEnd = async () => {
  await attenStore().BATCH_DAY_ATTDMGT(date.value);
  dayAttdMgtList.value = attenStore().dayAttdMgtList;
  console.log("[dayAttdMgtList]", dayAttdMgtList.value);
};

//일집계
const regiselect = async () => {
  console.log("runDailyAggregation 실행");
  await attenStore().runDailyAggregation(date.value);
  dayAttdMgtList.value = attenStore().dayAttdMgtList;
  console.log("[일집계 결과]", dayAttdMgtList.value);
};

const formatTime = (value: number | string): string => {
  if (!value) return "-";
  const str = value.toString().padStart(4, "0");  // '800' -> '0800'
  const hours = str.slice(0, 2);
  const minutes = str.slice(2, 4);
  return `${hours}:${minutes}`;
};

const formatHour = (value: number | string): string => {
  if (!value || value === "0") return "00:00";
  const num = typeof value === "string" ? parseInt(value) : value;
  const hours = Math.floor(num / 100).toString().padStart(2, "0");
  const minutes = (num % 100).toString().padStart(2, "0");
  return `${hours}:${minutes}`;
};

</script>

<template>
  <VContainer>
    <VRow>
      <VCol md="3" cols="12" offset-md="7">
        <AppDateTimePicker v-model="date" label="마감일자" placeholder="yyyy/mm/dd" />
      </VCol>
      <VCol cols="12" md="2" class="mb-7">
        <VBtn class="mt-6" height="43" width="150" @click="regiEnd">
          <VIcon start icon="tabler-checkbox" />등록 마감
        </VBtn>

        <VBtn class="mt-6" height="43" width="150" @click="regiselect">
          <VIcon start icon="tabler-checkbox" />
          일근태 집계
        </VBtn>
      </VCol>
    </VRow>
  </VContainer>
  <VDataTable :headers="headers" :items="dayAttdMgtList || []" :items-per-page="10">
    <template v-slot:item.attendTime="{ item }">
      {{ formatTime(item.attendTime) }}
    </template>

    <template v-slot:item.quitTime="{ item }">
      {{ formatTime(item.quitTime) }}
    </template>

    <template v-slot:item.workHour="{ item }">
      {{ formatHour(item.workHour) }}
    </template>

    <template v-slot:item.overWorkHour="{ item }">
      {{ formatHour(item.overWorkHour) }}
    </template>

    <template v-slot:item.nightWorkHour="{ item }">
      {{ formatHour(item.nightWorkHour) }}
    </template>

    <template v-slot:item.earlyLeaveHour="{ item }">
      {{ formatHour(item.earlyLeaveHour) }}
    </template>

    <template v-slot:item.leaveHour="{ item }">
      {{ formatHour(item.leaveHour) }}
    </template>
  </VDataTable>
</template>
