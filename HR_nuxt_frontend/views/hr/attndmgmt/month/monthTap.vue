<script lang="ts" setup>
import { ref } from "vue";
import { VDataTable } from "vuetify/labs/VDataTable";
import type { AttenHeaders } from "@/types/attendance/types";
import { attenStore } from "@/store/hr/attendance";

const selectedSearchType = ref("");
const selectedDeptCode = ref("");
const selectedEmpCode = ref("");
const selectedStatus = ref("");
const selectedApplyDay = ref("");
const dept_list: any = ref([]);
const emp_list_by_dept: any = ref([]);
const day_attd_list: any = ref([]);
const startDate = ref("");
const endDate = ref("");
const selectedRow: any = ref([]);
const updateRow = ref({});

const headers: AttenHeaders[] = [
  { key: "applyDays", title: "날짜", width: 120 },
  { key: "empName", title: "사원명", width: 120 },
  { key: "dayAttdName", title: "구분", width: 120 },
  { key: "attendTime", title: "시작시간", width: 120 },
  { key: "quitTime", title: "종료시간", width: 120 },
  { key: "workHour", title: "근무시간", width: 120 },
  { key: "halfHoildayStatus", title: "반차여부", width: 120 },
  { key: "lateWhether", title: "지각여부", width: 120 },
  { key: "overWorkHour", title: "연장근무", width: 120 },
  { key: "nightWorkHour", title: "심야근무", width: 120 },
  { key: "earlyLeaveHour", title: "조퇴시간", width: 120 },
  { key: "leaveHour", title: "외출시간", width: 120 },
  { key: "finalizeStatus", title: "승인여부", width: 120 },
];

const data = [
  {
    applyDays: "2024-6",
    empName: "강대국",
    dayAttdName: "구분",
    attendTime: "0900",
    quitTime: "1800",
    workHour: "185",
    halfHoildayStatus: "0",
    lateWhether: "2",
    overWorkHour: "25",
    nightWorkHour: "15",
    earlyLeaveHour: "5",
    leaveHour: "5",
    finalizeStatus: "N",
  },
];
const regiMonthEnd = async () => {
  await attenStore().BATCH_DAY_ATTDMGT(date.value);
  dayAttdMgtList.value = attenStore().dayAttdMgtList;
  console.log("[dayAttdMgtList]", dayAttdMgtList.value);
  alert("마감");
};

/**
 * 프로시저가 없음. 구현불가
 */
</script>

<template>
  <VContainer class="mt-2">
    <VRow>
      <!-- <VCol cols="12" md="2" offset-md="6">
        <VBtn class="mt-6" height="43" width="150">
          <VIcon start icon="tabler-search" />현황 조회
        </VBtn>
      </VCol> -->
      <VCol cols="12" md="2" offset-md="10">
        <VBtn class="mt-6" height="43" width="150" @click="regiMonthEnd">
          <VIcon start icon="tabler-square-key" />전체 마감
        </VBtn>
      </VCol>
      <VCol cols="12" md="2">
        <!-- <VBtn class="mt-6" height="43" width="150">
          <VIcon start icon="tabler-square-x" />마감 취소
        </VBtn> -->
      </VCol>
    </VRow>
    <VRow class="mt-6">
      <!-- <VCol cols="12" md="2">
        <AppSelect
          label="조회선택타입"
          placeholder="조회"
          :items="['English', 'Spanish', 'French', 'Russian', 'German']"
          clearable
          clear-icon="tabler-x"
        />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect
          label="부서조회"
          placeholder="사원조회"
          :items="['English', 'Spanish', 'French', 'Russian', 'German']"
          clearable
          clear-icon="tabler-x"
        />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect
          label="사원조회"
          placeholder="사원조회"
          :items="['English', 'Spanish', 'French', 'Russian', 'German']"
          clearable
          clear-icon="tabler-x"
        />
      </VCol>
      <VCol cols="12" md="2">
        <AppDateTimePicker
          v-model="date1"
          label="시작시간"
          placeholder="시작시간"
        />
      </VCol>
      <VCol cols="12" md="2">
        <AppDateTimePicker
          v-model="date2"
          label="종료시간"
          placeholder="종료시간"
        />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect
          label="승인상태"
          placeholder="승인상태"
          :items="['English', 'Spanish', 'French', 'Russian', 'German']"
          clearable
          clear-icon="tabler-x"
        />
      </VCol> -->
    </VRow>
  </VContainer>
  <VRow class="mt-6">
    <VCol>
      
      <VDataTable :headers="headers" :items="data" :items-per-page="10" />
    </VCol>
  </VRow>
</template>
