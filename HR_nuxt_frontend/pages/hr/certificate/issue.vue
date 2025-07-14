<script setup lang="ts">
import { attenStore } from "@/store/hr/attendance";
import { baseStore } from "@/store/hr/base";
import { certStore } from "@/store/hr/certificate";
import axios from "axios";
import { ref } from "vue";
import { VDataTable } from "vuetify/labs/VDataTable";
import AddModal from "../../../components/hr/certificate/addDialog.vue";
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

const tab = ref("personal-info");
const startDate = ref("");
const endDate = ref("");
const selectedDeptCode = ref("");
const dept_list: any = ref([]);
const emp_list_by_dept: any = ref([]);
const selectedEmpCode = ref("");
const certificate_list = ref([]);

const certificateNo = ref("");
const category = ref("");
const requestDate = ref("");
const empCode = ref("");
const empName = ref("");
const deptName = ref("");
const usageName = ref("");
const etc = ref("");

const items = ["재직증명서", "경력증명서", "급여명세서"];

const use = [
  { title: "금융기관 제출용", value: "ELM001" },
  { title: "관공서 제출용", value: "ELM002" },
  { title: "회사 제출용", value: "ELM003" },
  { title: "기타 제출용", value: "ELM004" },
];

const headers = [
  { title: "신청번호", key: "certificateNo" },
  { title: "신청일자", key: "requestDate" },
  { title: "사원명", key: "empName" },
  { title: "증명서구분", key: "category" },
  { title: "증명서용도", key: "usageName" },
  { title: "반려사유", key: "rejectCause" },
  { title: "승인상태", key: "approvalStatus" },
];

const openModal = () => {
  attenStore().SET_DIALOG_BTN(true);
};

// 페이지가 로드되자마자 실행
onMounted(() => {
  fetchDeptList();
});

watch(selectedDeptCode, () => {
  fetchEmpList();
  selectedEmpCode.value = "";
});

// 부서리스트 조회
const fetchDeptList = async () => {
  await baseStore().FETCH_DEPTLIST();
  dept_list.value = Object.values(baseStore().deptList).map(
    (item: DeptItemProps) => {
      return { title: item.deptName, value: item.deptCode };
    }
  );
};

// 부서의 사원 조회
const fetchEmpList = async () => {
  await attenStore().FETCH_EMPLIST_BY_DEPT(selectedDeptCode.value);
  emp_list_by_dept.value = Object.values(attenStore().empList).map(
    (item: EmpItemProps) => {
      return {
        title: item.empName,
        value: { empName: item.empName, empCode: item.empCode },
      };
    }
  );
};

const fetchData = async () => {
  if (
    selectedEmpCode.value === "" ||
    startDate.value === "" ||
    endDate.value === ""
  ) {
    alert("모든 항목을 선택해주세요.");

    return;
  }

  console.log(selectedEmpCode.value.empCode);
  await axios
    .get("http://localhost:8282/hr/certificate/selectCertificateList", {
      params: {
        empCode: selectedEmpCode.value.empCode,
        startDate: startDate.value,
        endDate: endDate.value,
      },
    })
    .then((res) => {
      console.log(res.data);
      certificate_list.value = res.data.certificateList;
    })
    .catch((e) => console.error(e));
};

// 행 클릭
const onRowClick = async (event: any, item: any) => {
  try {
    console.log("[clickRow]", item);
    certificateNo.value = item.item.certificateNo;
    category.value = item.item.category;
    requestDate.value = item.item.requestDate;
    empCode.value = item.item.empCode;
    empName.value = item.item.empName;
    deptName.value = item.item.deptName;
    usageName.value = item.item.usageName;
    etc.value = item.item.etc;
  } catch (e) {
    console.error(e);
  }
};

// 수정
const updateCert = () => {
  if (certificateNo.value === "") {
    alert("수정할 건을 선택해주세요.");

    return;
  }

  const upRow = {
    certificateNo: certificateNo.value,
    usageCode: usageName.value,
    category: category.value,
    requestDate: requestDate.value,
    etc: etc.value,
  };

  // 일단 임시로 처음에 usegeCode의 value가 아닌 title이 출력되는 것을 막아놓음
  if (
    usageName.value === "금융기관 제출용" ||
    usageName.value === "관공서 제출용" ||
    usageName.value === "회사 제출용" ||
    usageName.value === "기타 제출용"
  ) {
    alert("제출 용도를 다시 선택해주세요.");

    return;
  }
  console.log(upRow);
  certStore().UPDATE_CERT_REQUEST(upRow);
  alert("수정이 완료되었습니다. ");
  fetchData();
};

// 삭제
const deleteCert = () => {
  if (certificateNo.value === "") {
    alert("삭제할 건을 선택해주세요.");

    return;
  }
  const delRow = { certificateNo: certificateNo.value };

  console.log(delRow);
  certStore().REMOVE_CERT_REQUEST(delRow);
  alert("삭제가 완료되었습니다. ");
  fetchData();
};

// 반려나 승인취소된 건을 수정시 다시 승인대기 상태로 변환
</script>

<template>
  <VCard>
    <VTabs v-model="tab" height="70">
      <VTab value="personal-info"> 증명서 관리 </VTab>
    </VTabs>
  </VCard>
  <VCard flat>
    <VCardText>
      <VWindow v-model="tab" class="disable-tab-transition">
        <VWindowItem value="personal-info">
          <VCard class="mb-6">
            <VCardText>
              <VRow>
                <VCol cols="12" sm="2">
                  <AppSelect
                    v-model="selectedDeptCode"
                    label="부서명"
                    :items="dept_list"
                    clearable
                    clear-icon="tabler-x"
                  />
                </VCol>
                <VCol cols="12" sm="3">
                  <AppSelect
                    v-model="selectedEmpCode"
                    label="사원명"
                    :items="emp_list_by_dept"
                    clearable
                    clear-icon="tabler-x"
                  />
                </VCol>
                <VCol cols="12" sm="3" mt="4">
                  <AppTextField
                    v-model="startDate"
                    type="date"
                    label="시작일"
                    placeholder="시작일"
                  />
                </VCol>
                <VCol cols="12" sm="3" mt="4">
                  <AppTextField
                    v-model="endDate"
                    type="date"
                    label="종료일"
                    placeholder="종료일"
                  />
                </VCol>
                <VBtn class="mt-9" @click="fetchData"> 조회 </VBtn>
              </VRow>
            </VCardText>
          </VCard>
        </VWindowItem>
      </VWindow>
    </VCardText>
  </VCard>
  <VCard>
    <!-- 증명서 발급 신청 -->
    <VCardText>
      증명서신청내역
      <br />
      <br />
      <br />
      <VDataTable
        :headers="headers"
        :items="certificate_list"
        :items-per-page="10"
        @click:row="onRowClick"
      />
    </VCardText>
  </VCard>
  <br />
  <VCard>
    <VCardText>증명서신청</VCardText>
    <VRow class="ma-2">
      <VCol cols="12" md="4">
        <AppTextField v-model="certificateNo" label="신청번호" read-only />
      </VCol>
      <VCol cols="12" md="4">
        <AppSelect
          v-model="category"
          label="증명서구분"
          :items="items"
          append-inner-icon="tabler-search"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppDateTimePicker
          v-model="requestDate"
          append-inner-icon="tabler-calendar"
          label="신청일자"
        />
      </VCol>
      <VCol cols="12" md="4">
        <AppTextField v-model="deptName" label="부서" read-only />
      </VCol>
      <VCol cols="12" md="4">
        <AppTextField v-model="empCode" label="사원번호" read-only />
      </VCol>
      <VCol cols="12" md="4">
        <AppTextField v-model="empName" label="성명" read-only />
      </VCol>
      <VCol>
        <AppSelect v-model="usageName" label="용도" :items="use" />
      </VCol>
      <VCol>
        <AppTextField v-model="etc" label="비고" />
      </VCol>
    </VRow>

    <VRow class="text-right mr-2 mb-2">
      <VCol>
        <VBtn color="primary mr-2" class="mr-2" @click="openModal"> 신규 </VBtn>
        <VBtn class="mr-2" color="primary" @click="deleteCert"> 삭제 </VBtn>
        <VBtn color="primary" @click="updateCert"> 저장 </VBtn>
      </VCol>
    </VRow>
  </VCard>
  <AddModal />
</template>
