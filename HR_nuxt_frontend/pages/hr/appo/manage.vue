<script lang="ts" setup>
import { affairStore } from "@/store/hr/affair";
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


const iswarningFunc = ref(false);
const isSuccessFunc = ref(false);
const store = affairStore();
const myTableRef = ref();
const selectedItems = ref([]);

const headers = [
  { title: "발령번호", key: "appointNo" },
  { title: "사번", key: "empCode" },
  { title: "직원명", key: "empName" },
  { title: "부서", key: "deptName" },
  { title: "발령부서", key: "appointDept" },
  { title: "직급", key: "positionName" },
  { title: "발령직급", key: "appointLevel" },
  { title: "승급호봉", key: "appointHobong" },
  { title: "근무지", key: "workPlaceName" },
  { title: "발령근무지", key: "appointWP" },
];

const appointmentsList = ref([]);
const search = ref<string>("");
const perPage = ref(5); // 페이지당 표시되는 아이템 수
const currentPage = ref(1); // 현재 페이지

const pagination = {
  enabled: true,
  setCurrentPage: 2,
};

const fetchData = async () => {
  try {
    console.log("여기요");

    // 발령 신청 내역 조회
    await store.FETCH_APPOINT_LIST();
    appointmentsList.value = store.all_appointments_list;
  } catch (error) {
    console.error("Failed to fetch appointments list", error);
  }
};

// 발령 승인, 반려, 삭제 등의 동작을 수행하는 메소드들
const appointApprove = async () => {
  if (selectedItems.value.length === 0) {
    iswarningFunc.value = true;
  } else {
    for (const val of selectedItems.value) {
      const matchingAppointments = appointmentsList.value.filter(
        (item) => item.appointNo === val
      );

      // Check if matchingAppointments is not empty
      if (matchingAppointments.length > 0) {
        const rawAppointments = matchingAppointments.map((appointment) =>
          toRaw(appointment)
        );

        rawAppointments[0].status = "승인";
        console.log("발령 승인", rawAppointments[0]);

        await store.UPDATE_APPOINT_APPROVE_EMP(rawAppointments[0]);
      }
    }
    isSuccessFunc.value = true;
    await fetchData();
  }
};

const appointReject = async () => {
  if (selectedItems.value.length === 0) {
    iswarningFunc.value = true;
  } else {
    for (const val of selectedItems.value) {
      const matchingAppointments = appointmentsList.value.filter(
        (item) => item.appointNo === val
      );

      // Check if matchingAppointments is not empty
      if (matchingAppointments.length > 0) {
        const rawAppointments = matchingAppointments.map((appointment) =>
          toRaw(appointment)
        );

        rawAppointments[0].status = "반려";
        console.log("발령 반려", rawAppointments[0]);

        await store.UPDATE_APPOINT_APPROVE_EMP(rawAppointments[0]);
      }
    }
    isSuccessFunc.value = true;
    await fetchData();
  }
};

const appointDelete = async () => {
  if (selectedItems.value.length === 0) {
    iswarningFunc.value = true;
  } else {
    for (const val of selectedItems.value) {
      const matchingAppointments = appointmentsList.value.filter(
        (item) => item.appointNo === val
      );

      // Check if matchingAppointments is not empty
      if (matchingAppointments.length > 0) {
        const rawAppointments = matchingAppointments.map((appointment) =>
          toRaw(appointment)
        );

        await store.DELETE_APPOINT_EMP(rawAppointments[0].appointNo);
      }
    }
    isSuccessFunc.value = true;
    await fetchData();
  }
};

onMounted(fetchData);
</script>

<template>
  <h1 class="mb-6">인사발령 관리</h1>
  <VCard class="mt-3">
    <!-- 검색 필드 -->
    <VCardText class="d-flex flex-wrap py-4 gap-4">
      <div style="inline-size: 10rem">
        <AppTextField
          v-model="search"
          density="compact"
          placeholder="Search"
          append-inner-icon="tabler-search"
          single-line
          hide-details
          dense
          outlined
        />
      </div>
      <!-- 각 행의 동작 버튼 -->
      <VRow class="text-right">
        <VCol>
          <VBtn class="ma-2" color="primary" @click="appointApprove">
            승인
          </VBtn>
          <VBtn class="ma-2" color="secondary" @click="appointReject">
            반려
          </VBtn>
          <VBtn class="ma-2" color="error" @click="appointDelete"> 삭제 </VBtn>
        </VCol>
      </VRow>
    </VCardText>
    <VDivider />
    <!-- 직원 전체 조회 table -->
    <VDataTable
      ref="myTableRef"
      v-model="selectedItems"
      :headers="headers"
      :items="appointmentsList"
      :search="search"
      :items-per-page="perPage"
      :page="currentPage"
      :pagination="pagination"
      item-value="appointNo"
      show-select
    />
  </VCard>
  <VSnackbar
    v-model="iswarningFunc"
    location="top"
    :timeout="3000"
    color="warning"
  >
    <VAlert
      density="compact"
      type="warning"
      title="warning!"
      text="선택 해주세요."
    />
  </VSnackbar>
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
