<script setup lang="ts">
import HolidayModal from "@/components/hr/base/holidayModal.vue";
import { baseStore } from "@/store/hr/base";
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
  { title: "날짜", key: "applyDay" },
  { title: "명칭", key: "holidayName" },
  { title: "종류", key: "holidayType" },
];

const holidayData = ref("");
const holidayName = ref("");
const isDialogVisible = ref(false);
const date = ref("");
const selectedMonthData = ref("");
const selectedYear = ref("");
const selectedMonth = ref("");
const selectHolidayType = ref("");
const holidayTypeList = ref([]);
const monthList: any = ref([]);
const yearList: any = ref([]);
const holidayList: any = ref([]);
const tab = ref("personal-info");

const fetchData = async () => {
  await baseStore().FETCH_MONTHLIST();
  await baseStore().FETCH_YEARLIST();

  const monthListData = baseStore().monthList;
  const yearListData = baseStore().yearList;

  monthList.value = monthListData
    ? Object.values(monthListData).map((item) => item.month)
    : [];
  yearList.value = yearListData
    ? Object.values(yearListData).map((item) => item.year)
    : [];
};

const fetchHoliday = async () => {
  if (selectedYear.value) {
    await baseStore().FETCH_HOLIDAYLIST(selectedYear.value);
    holidayList.value = baseStore().holidayList || [];
  }
};

const test = async () => {
  try {
    const month = !selectedMonth.value ? "%" : selectedMonth.value;
    const formattedMonth =
      month.trim().length === 1 && month !== "%" ? `0${month}` : month;

    selectedMonthData.value = formattedMonth;

    // 여기서 문자열 내의 띄어쓰기를 제거
    const applyday = `${selectedYear.value}-${formattedMonth}-%`.replace(
      /\s/g,
      ""
    );

    console.log("applyday:", applyday);

    await baseStore().FETCH_HOLIDAYLIST(applyday);

    // 로그에 데이터 출력
    console.log("holidayList:", baseStore().holidayList);

    console.log("여긴데");
    holidayList.value = baseStore().holidayList;
  } catch (error) {
    console.error("휴일 목록 불러오기 중 에러:", error);
  }
};

const holiday = [
  { title: "명절", value: { holidayType: "명절", holidayCode: "HOL001" } },
  { title: "국경일", value: { holidayType: "국경일", holidayCode: "HOL002" } },
  {
    title: "종교기념일",
    value: { holidayType: "종교기념일", holidayCode: "HOL003" },
  },
  {
    title: "임시공휴일",
    value: { holidayType: "임시공휴일", holidayCode: "HOL005" },
  },
  {
    title: "대체공휴일",
    value: { holidayType: "대체공휴일", holidayCode: "HOL006" },
  },
  {
    title: "법정기념일",
    value: { holidayType: "법정기념일", holidayCode: "HOL004" },
  },
];

watch([selectedYear, selectedMonth], test, { immediate: true });

onBeforeMount(() => {
  fetchData();
});

const addDept = async () => {
  const insertData = {
    applyDay: date.value,
    holidayName: holidayName.value,
    holidayType: holidayData.value.holidayType,
    holidayCode: holidayData.value.holidayCode,
  };

  console.log(insertData);
  await baseStore().ADD_HOLIDAY(insertData);
  await test();
  isDialogVisible.value = !isDialogVisible;
};

const openModal = async (row) => {
  console.log(row.item);

  // // 가져온 데이터를 store에 저장
  baseStore().SET_HOLIDAY_ROW(row.item);

  // // 모달 열기
  baseStore().SET_DIALOG(true);
};
</script>

<template>
  <h1 class="mb-6">휴일정보관리</h1>
  <VCard>
    <VTabs v-model="tab" height="70">
      <VTab value="personal-info"> 휴일정보관리 </VTab>
    </VTabs>
  </VCard>
  <VCard flat>
    <VCardText>
      <VWindow v-model="tab" class="disable-tab-transition">
        <VWindowItem value="personal-info">
          <VCard class="mb-6">
            <VCardText>
              <VRow>
                <VCol cols="12" sm="4">
                  <AppSelect
                    v-model="selectedYear"
                    label="해당 연도"
                    placeholder="해당 연도"
                    :items="yearList"
                    clearable
                    clear-icon="tabler-x"
                  />
                </VCol>

                <VCol cols="12" sm="4">
                  <AppSelect
                    v-model="selectedMonth"
                    label="해당 월"
                    placeholder="해당 월"
                    :items="monthList"
                    clearable
                    clear-icon="tabler-x"
                  />
                </VCol>
                <VDialog v-model="isDialogVisible" max-width="600">
                  <!-- Dialog Activator -->
                  <template #activator="{ props }">
                    <VSpacer>
                      <VCol md="7">
                        <VBtn v-bind="props" class="mt-md-5"> 휴일추가 </VBtn>
                      </VCol>
                    </VSpacer>
                  </template>

                  <!-- Dialog close btn -->
                  <DialogCloseBtn @click="isDialogVisible = !isDialogVisible" />

                  <!-- Dialog Content -->
                  <VCard title="휴일추가">
                    <VCardText>
                      <VRow>
                        <VCol cols="12" sm="6">
                          <AppTextField
                            v-model="date"
                            type="date"
                            label="적용일"
                            placeholder="Select date"
                          />
                        </VCol>
                        <VCol cols="12" sm="6">
                          <AppTextField
                            v-model="holidayName"
                            label="휴일명칭"
                            placeholder=""
                          />
                        </VCol>
                        <VCol cols="12" sm="6">
                          <AppSelect
                            v-model="holidayData"
                            :items="holiday"
                            label="종류"
                          />
                        </VCol>
                      </VRow>
                    </VCardText>

                    <VCardText class="d-flex justify-end flex-wrap gap-3">
                      <VBtn
                        variant="tonal"
                        color="secondary"
                        @click="isDialogVisible = false"
                      >
                        Close
                      </VBtn>
                      <VBtn @click="addDept"> Save </VBtn>
                    </VCardText>
                  </VCard>
                </VDialog>
              </VRow>
            </VCardText>
          </VCard>
          <MonthTap />
        </VWindowItem>
      </VWindow>
    </VCardText>
  </VCard>
  <VDataTable
    :headers="headers"
    :items="holidayList"
    :items-per-page="5"
    @click:row="(_, row) => openModal(row)"
  />
  <HolidayModal :test="test" />
</template>
