<script lang="ts" setup>
import { attenStore } from "@/store/hr/attendance";
import type {
  AttenHeaders,
  AttenProps,
  DayAttdProps,
  ItemPropsTypes,
} from "@/types/attendance/types";
import axios from "axios";
import { VDataTable } from "vuetify/labs/VDataTable";
import { useRouter } from "vue-router"; // router 가져오기
import { inject } from "vue";

// 0423 수정
const selectedHour = ref("");
const selectedMinute = ref("");

const hourOptions = Array.from({ length: 24 }, (_, i) =>
  i.toString().padStart(2, "0")
);

// 분 입력 유효성 검사 (0~59)
const validateMinute = (v: string) => {
  const num = Number(v);
  if (!v) return true;
  if (/^\d{1,2}$/.test(v) && num >= 0 && num <= 59) return true;
  return "0~59 사이 숫자를 입력하세요";
};

// 최종 HHmm 형식으로 저장될 변수
watch([selectedHour, selectedMinute], () => {
  if (selectedHour.value && selectedMinute.value) {
    const hour = selectedHour.value.padStart(2, "0");
    const minute = selectedMinute.value.padStart(2, "0");
    time.value = `${hour}${minute}`; // 예: "1930"
    console.log(time.value);
  }
});
// 0423 수정
const router = useRouter();
import { useEmpStore } from "@/store/hr/emp";

const empStore = useEmpStore();
empStore.loadEmpCode();

console.log("현재 로그인된 직원 코드:", empStore.empCode);

// 로그인된 empCode가 'EMP-01'이 아닌 경우 페이지 이동
if (empStore.empCode !== "EMP-01" && empStore.empCode !== "EMP-00") {
  router.push("/hr/confinement/unauthorized");
}

// Dialog
const isEmployeeDialogVisible = ref(false);
const isAttendanceDialogVisible = ref(false);
const isModifyDialogVisible = ref(false);

// DateTimePicker
const applyDay = ref("");
const selectedDept: globalThis.Ref<string> = ref("");
const dept_list = ref([]);
const selectedEmp: globalThis.Ref<string> = ref("");
const emp_list = ref([]);
const time = ref("");
const day_attd_list = ref([]);
const selectedAttdTypeText = ref("");
const selectedAttdTypeValue = ref("");
const selectedRow = ref({});
const modalStatus = ref("insert");
const targetTime = ref("");
const targetDayAttdCode = ref("");

// Table Columns
const headers: AttenHeaders[] = [
  { title: "이름", key: "empName" },
  { title: "일렬번호", key: "dayAttdCode" },
  { title: "승인날짜", key: "applyDay" },
  { title: "근태관리코드", key: "attdTypeCode" },
  { title: "근태내용", key: "attdTypeName" },
  { title: "시간", key: "time" },
];

const attdType: AttenProps = [
  { text: "출근", value: "ADC001" },
  { text: "퇴근", value: "ADC002" },
  { text: "공외출", value: "ADC003" },
  { text: "공귀사", value: "ADC007" },
  { text: "귀사(무단)", value: "ADC010" },
  { text: "외출(무단)", value: "ADC012" },
];

// select component에 사용되는 함수
const itemProps = (item: ItemPropsTypes) => {
  return {
    title: item.text,
    value: item.value,
  };
};

watch(selectedAttdTypeValue, (newValue, oldValue) => {
  const selectedItem = attdType.find((item) => item.value === newValue);
  if (selectedItem) {

    selectedAttdTypeText.value = selectedItem.text;
    selectedAttdTypeValue.value = selectedItem.value;
    console.log("Selected Title:", selectedAttdTypeText.value);
    console.log("Selected value:", selectedAttdTypeValue.value);
    }
});

// 페이지가 로드되자마자 실행
onMounted(() => {
  fetchDeptList();
});

// selectedDept가 변경될때마다 fetchEmpList 호출
watch(selectedDept, (newValue, oldValue) => {
  if (newValue !== oldValue) fetchEmpList();
});

// 부서리스트 조회
const fetchDeptList = async () => {
  try {
    const res = await axios.get("http://localhost:8282/hr/base/deptList");
    const deptData = res.data.deptlist;

    dept_list.value = deptData.map((e: any) => ({
      value: e.deptCode,
      text: e.deptName,
    }));
    console.log("[deptList]", dept_list.value);
  } catch (error) {
    console.error(error);
  }
};

// 직원리스트 조회
const fetchEmpList = async () => {
  try {
    console.log("[selectedDept]", selectedDept.value);

    const deptCode = selectedDept.value;
    const res = await axios.get(
      "http://localhost:8282/hr/attendance/findEmpListByDept",
      { params: { deptCode } }
    );
    const empData = res.data.empList;

    console.log("[empData]", empData);
    emp_list.value = empData.map((e: any) => ({
      value: e.empCode,
      text: e.empName,
    }));
    console.log("[empList]", emp_list.value);
  } catch (error) {
    console.error(error);
  }
};

// 일근태리스트 조회
const fetchDayAttdList = async () => {
  if (applyDay.value === "") {
    alert("조회할 신청일자를 선택해주세요.");
    isEmployeeDialogVisible.value = !isEmployeeDialogVisible.value;
  } else {
    try {
      console.log("[applyDay]", applyDay.value);
      console.log("[empCode]", selectedEmp.value);

      const res = await axios.get(
        "http://localhost:8282/hr/attendance/dayAttendance",
        { params: { applyDay: applyDay.value, empCode: selectedEmp.value } }
      );

      console.log("[day_attd_list.value]", res.data.dayAttdList);
      day_attd_list.value = res.data.dayAttdList;
      isEmployeeDialogVisible.value = false;
    } catch (e) {
      console.error(e);
    }
  }
};

// 일근태 추가 - 선택
const insertDayAttd = async () => {
  if (applyDay.value === "") {
    alert("조회할 신청일자를 선택해주세요.");
    
    isEmployeeDialogVisible.value = !isEmployeeDialogVisible.value;
  } else {
    try {
      const dayAttd: DayAttdProps = {
        empCode: selectedEmp.value,
        applyDay: applyDay.value,
        attdTypeCode: selectedAttdTypeValue.value,
        attdTypeName: selectedAttdTypeText.value,
        time: time.value,
      };
console.log("데이터 확인",dayAttd)
      if (Object.values(dayAttd).some((datavalue) => datavalue == "")) {
        alert("모든 항목을 선택해주세요.");
      } else {
        const res = await axios.post(
          "http://localhost:8282/hr/attendance/dayAttendance",
          dayAttd
        );

        console.log("[저장]", res.data);
        alert("신청이 완료되었습니다. ");
        fetchDayAttdList();
        isAttendanceDialogVisible.value = false;

        // window.location.reload
        // reset()
      }
    } catch (e) {
      console.error(e);
    }
  }
};

// 일근태 수정
const updateDayAttd = async () => {
  console.log("selectedAttdTypeValue.value", selectedAttdTypeValue.value);
  console.log("selectedAttdTypeText.value", selectedAttdTypeText.value);
  console.log("selectedEmp.value", selectedEmp.value);
  console.log("targetDayAttdCode", targetDayAttdCode.value);

  if (applyDay.value === "") {
    alert("조회할 신청일자를 선택해주세요.");
    isEmployeeDialogVisible.value = !isEmployeeDialogVisible.value;
  } else {
    if (
      selectedAttdTypeText.value === "" ||
      selectedAttdTypeValue.value === ""
    ) {
      alert("근태 유형을 선택해주세요.");
    } else {
      await attenStore()
        .UPDATE_DAYATTD({
          attdTypeCode: selectedAttdTypeValue.value,
          attdTypeName: selectedAttdTypeText.value,
          empCode: selectedEmp.value,
          dayAttdCode: targetDayAttdCode.value,
        })
        .then((res) => {
          console.log("[수정완료]", res.data);
          alert("수정이 완료되었습니다.");
          fetchDayAttdList();
        })
        .catch((e) => console.error(e));
      isAttendanceDialogVisible.value = false;
      modalStatus.value = "insert";
    }
  }
};

// 일근태 삭제
const deleteDayAttd = () => {
  console.log("삭제");
  console.log("selectedEmp.value", selectedEmp.value);
  console.log("targetDayAttdCode", targetDayAttdCode.value);

  if (applyDay.value === "") {
    alert("조회할 신청일자를 선택해주세요.");
    isEmployeeDialogVisible.value = !isEmployeeDialogVisible.value;
  } else {
    const deleteData = {
      empCode: selectedEmp.value,
      dayAttdCode: targetDayAttdCode.value,
    };

    attenStore()
      .DELETE_DAT_ATTD(deleteData)
      .then((res) => {
        console.log("[삭제결과]", res.data);
        isAttendanceDialogVisible.value = false;
        modalStatus.value = "insert";
        alert("삭제가 완료되었습니다.");
        fetchDayAttdList();
      })
      .catch((e) => console.error(e));
  }
};

// 행 클릭
const onRowClick = async (event: any, item: any) => {
  try {
    console.log("[clickRow]", item);
    targetTime.value = item.item.time;
    targetDayAttdCode.value = item.item.dayAttdCode;
    console.log("[targetTime]", item.item.time);
    modalStatus.value = "update";
    isAttendanceDialogVisible.value = true;
    console.log(isAttendanceDialogVisible.value);
  } catch (e) {
    console.error(e);
  }
};
</script>

<template>
  <h1 class="mb-6">일근태등록</h1>
  <div>
    <VCard>
      <VContainer class="mt-6">
        <VForm @submit.prevent="() => {}">
          <VRow>
            <!--  👉 날짜 선택 -->
            <VCol cols="24" md="3" offset-md="5">
              <AppDateTimePicker
                v-model="applyDay"
                placeholder="날짜선택"
                prepend-icon="tabler-calendar-event"
              />
            </VCol>

            <!-- 👉 사원 조회 -->
            <VCol cols="24" md="2" offset-md="">
              <VDialog v-model="isEmployeeDialogVisible" width="500">
                <!-- 사원 조회 버튼 -->
                <template #activator="{ props }">
                  <VBtn
                    v-bind="props"
                    prepend-icon="tabler-user-search"
                    width="350"
                  >
                    사원조회
                  </VBtn>
                </template>

                <!-- 다이얼로그 닫기 버튼 -->
                <DialogCloseBtn
                  @click="isEmployeeDialogVisible = !isEmployeeDialogVisible"
                />

                <!-- 사원조회 다이얼로그 테이블 -->
                <VCard title="사원조회">
                  <VContainer>
                    <VRow>
                      <VCol>
                        <VSelect
                          v-model="selectedDept"
                          class="mb-3"
                          label="부서명"
                          :items="dept_list"
                          :item-props="itemProps"
                          variant="outlined"
                          clearable
                          clear-icon="tabler-x"
                        />
                      </VCol>
                      <VCol>
                        <VTextField
                          v-model="selectedDept"
                          class="mb-3"
                          label="부서코드"
                          readonly
                          variant="outlined"
                        />
                      </VCol>
                    </VRow>
                    <VRow>
                      <VCol>
                        <VSelect
                          v-model="selectedEmp"
                          class="mb-3"
                          label="사원명"
                          :items="emp_list"
                          :item-props="itemProps"
                          variant="outlined"
                          clearable
                          clear-icon="tabler-x"
                        />
                      </VCol>
                      <VCol>
                        <VTextField
                          v-model="selectedEmp"
                          class="mb-3"
                          label="사원코드"
                          readonly
                          variant="outlined"
                        />
                      </VCol>
                    </VRow>
                  </VContainer>
                  <VCardText class="d-flex justify-end">
                    <VBtn @click="fetchDayAttdList"> 선택 </VBtn>
                  </VCardText>
                </VCard>
              </VDialog>
            </VCol>

            <!-- 👉 일 근태 추가/수정,삭제 -->
            <VCol cols="24" md="2" offset-md="">
              <VDialog v-model="isAttendanceDialogVisible" width="500">
                <template #activator="{ props }">
                  <VBtn
                    v-bind="props"
                    prepend-icon="tabler-calendar-plus"
                    @click="modalStatus = 'insert'"
                  >
                    일근태 추가
                  </VBtn>
                </template>

                <VContainer>
                  <DialogCloseBtn
                    @click="
                      isAttendanceDialogVisible = !isAttendanceDialogVisible
                    "
                  />
                </VContainer>
                <VCard
                  :title="`${
                    modalStatus === 'insert'
                      ? '일근태 추가'
                      : '일근태 수정/삭제'
                  }`"
                >
                  <VContainer>
                    <VRow>
                      <VCol>
                        <VTextField
                          v-model="selectedEmp"
                          class="mb-3"
                          label="사원명"
                          readonly
                          variant="outlined"
                        />
                      </VCol>
                      <VCol>
                        <VTextField
                          v-model="selectedEmp"
                          class="mb-3"
                          label="사원코드"
                          readonly
                          variant="outlined"
                        />
                      </VCol>
                    </VRow>
                    <VRow>
                      <VCol>
                        <VSelect
                          v-model="selectedAttdTypeValue"
                          class="mb-3"
                          label="근무타입"
                          :items="attdType"
                          :item-props="itemProps"
                          variant="outlined"
                          clearable
                          clear-icon="tabler-x"
                        />
                      </VCol>
                      <VCol>
                        <VRow>
                          <!-- 시(hour) 선택 드롭다운 -->
                          <VCol cols="6">
                            <VSelect
                              v-model="selectedHour"
                              :items="hourOptions"
                              label="시"
                              variant="outlined"
                              dense
                              clearable
                              style="min-width: 120px"
                            />
                          </VCol>

                          <!-- 분(minute) 직접 입력 -->
                          <VCol cols="6">
                            <VTextField
                              v-model="selectedMinute"
                              label="분"
                              variant="outlined"
                              dense
                              :rules="[validateMinute]"
                              maxlength="2"
                            />
                          </VCol>
                        </VRow>
                        <!-- <VTextField
                          v-if="modalStatus === 'insert'"
                          v-model="time"
                          class="mb-3"
                          label="시간"
                          readonly
                          variant="outlined"
                        />
                        <VTextField
                          v-if="modalStatus === 'update'"
                          v-model="targetTime"
                          class="mb-3"
                          label="시간"
                          readonly
                          variant="outlined"
                        /> -->
                      </VCol>
                    </VRow>
                  </VContainer>
                  <VCardText class="d-flex justify-end">
                    <VBtn
                      v-if="modalStatus === 'insert'"
                      @click="insertDayAttd"
                    >
                      추가
                    </VBtn>
                    <VBtn
                      v-if="modalStatus === 'update'"
                      @click="updateDayAttd"
                    >
                      수정
                    </VBtn>
                    <VBtn
                      v-if="modalStatus === 'update'"
                      class="ml-3"
                      @click="deleteDayAttd"
                    >
                      삭제
                    </VBtn>
                  </VCardText>
                </VCard>
              </VDialog>
            </VCol>
          </VRow>
        </VForm>

        <!-- 👉 일근태 등록 테이블 -->
        <VRow>
          <VDataTable
            class="mt-6"
            :headers="headers"
            :items="day_attd_list"
            :items-per-page="5"
            height="350"
            @click:row="onRowClick"
          />
        </VRow>
      </VContainer>
    </VCard>
  </div>
</template>
