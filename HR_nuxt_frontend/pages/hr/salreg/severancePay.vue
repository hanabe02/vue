<script lang="ts" setup>
import { paginationMeta } from "@/server/utils/paginationMeta";
import { affairStore } from "@/store/hr/affair";
import { salaryStore } from "@/store/hr/salary";
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



const options = ref({
  page: 1,
  itemsPerPage: 5,
  sortBy: [""],
  sortDesc: [false],
});

const isSuccessFunc = ref(false);
const isaddfaild = ref(false);
const selectedEmp = ref("");
const selectedDate = ref("");

const selectedWork = computed(() => {
  const hireDate = new Date(selectedEmp.value.hireDate);
  const retireDate = new Date(selectedDate.value);
  return Math.abs(
    (retireDate.getTime() - hireDate.getTime()) / (1000 * 60 * 60 * 24)
  );
});

const selectedDivision = ref("");
const selectedRows = ref([]);
const isDialogVisible = ref(false);
const severanceList = ref([]);
const allEmpList = ref([]);
const empListData = ref([]);
const dayData = ref([]);
const division = [{ title: "퇴직금", value: "퇴직금" }];

const headers = [
  { title: "신청일", key: "applyDate", align: "center" },
  { title: "구분", key: "severanceType", align: "center" },
  { title: "사원명", key: "empName", align: "center" },
  { title: "근속기간", key: "workDays", align: "center" },
  { title: "입사일", key: "hireDate", align: "center" },
  { title: "퇴사일", key: "retireDate", align: "center" },
  { title: "차인지금액", key: "realSeverancePay", align: "center" },
];

const fetchData = async () => {
  const date = new Date();
  dayData.value = {
    firstDay: `${date.getFullYear()}-1-1`,
    today: `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`,
  };
  await salaryStore().FIND_SEVERANCE_PAY(dayData.value);
  severanceList.value = salaryStore().severanceList;
  console.log(dayData.value);
  console.log(severanceList.value);
};

const findEmpList = async () => {
  await affairStore().FETCH_ALL_EMP();
  empListData.value = await affairStore().all_emp_list;
  allEmpList.value = (await empListData.value)
    ? Object.values(empListData.value).map((item) => {
        return {
          title: item.empName,
          value: { empCode: item.empCode, hireDate: item.hireDate },
        };
      })
    : [];
};

const addSeverancePay = async () => {
  const addSeverancePayData = {
    workDays: selectedWork.value,
    hireDate: new Date(selectedEmp.value.hireDate),
    retireDate: new Date(selectedDate.value),
    empCode: selectedEmp.value.empCode,
    severanceType: selectedDivision.value,
    applyDate: new Date(dayData.value.today),
    firstDay: new Date(dayData.value.firstDay),
    today: new Date(dayData.value.today),
  };

  console.log(addSeverancePayData);
  await salaryStore().ADD_SEVERANCE_PAY(addSeverancePayData);
  await fetchData();
  isDialogVisible.value = false;
  if (selectedWork.value < 365) isaddfaild.value = true;
  else isSuccessFunc.value = true;
};

const deletebtn = async () => {
  await salaryStore().DELETE_SEVERANCE(selectedRows.value[0]);
  await fetchData();
  isSuccessFunc.value = true;
};

onBeforeMount(async () => {
  fetchData();
  findEmpList();
});
</script>

<template>
  <h1 class="mb-6">퇴직금 등록</h1>
  <VCard>
    <VCardText class="d-flex flex-wrap py-4 gap-4">
      <div class="me-3 d-flex gap-3">
        <AppSelect
          :model-value="options.itemsPerPage"
          :items="[
            { value: 5, title: '5' },
            { value: 10, title: '10' },
            { value: 25, title: '25' },
            { value: 50, title: '50' },
            { value: -1, title: 'All' },
          ]"
          style="inline-size: 6.25rem"
          @update:model-value="options.itemsPerPage = parseInt($event, 10)"
        />
      </div>
      <VSpacer />
      <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
        <VDialog v-model="isDialogVisible" max-width="600">
          <!-- Dialog Activator -->
          <template #activator="{ props }">
            <VBtn v-bind="props" prepend-icon="tabler-plus"> 퇴직금 등록 </VBtn>
          </template>
          <!-- Dialog close btn -->
          <DialogCloseBtn @click="isDialogVisible = !isDialogVisible" />
          <!-- Dialog Content -->
          <VCard title="퇴직금 등록">
            <VCardText>
              <VRow>
                <VCol cols="12" sm="6">
                  <AppSelect
                    v-model="selectedEmp"
                    :items="allEmpList"
                    label="직원명"
                  />
                </VCol>
                <VCol cols="12" sm="6">
                  <AppTextField
                    v-model="selectedDate"
                    label="퇴직일"
                    type="date"
                  />
                </VCol>
                <VCol cols="12" sm="6">
                  <AppTextField
                    v-model="selectedWork"
                    label="재직일수"
                    disabled
                  />
                </VCol>
                <VCol cols="12" sm="6">
                  <AppSelect
                    v-model="selectedDivision"
                    :items="division"
                    label="구분"
                  />
                </VCol>
              </VRow>
            </VCardText>
            <VCardText class="d-flex justify-end flex-wrap gap-3 mt-2">
              <VBtn @click="addSeverancePay"> 등록 </VBtn>
            </VCardText>
          </VCard>
        </VDialog>
        <VBtn prepend-icon="tabler-minus" @click="deletebtn">
          퇴직금 삭제
        </VBtn>
      </div>
    </VCardText>
    <VDivider />
    <VDataTable
      v-model="selectedRows"
      :headers="headers"
      :items="severanceList"
      :items-per-page="options.itemsPerPage"
      :page="options.page"
      :options="options"
      item-value="empCode"
      show-select
      singel-select
    >
      <template #bottom>
        <VDivider />
        <VCardText class="pt-2">
          <div
            class="d-flex flex-wrap justify-center justify-sm-space-between gap-y-2 mt-2"
          >
            <p class="text-sm text-disabled mb-0">
              {{
                paginationMeta(
                  { page: options.page, itemsPerPage: options.itemsPerPage },
                  severanceList.length
                )
              }}
            </p>
            <VPagination
              v-model="options.page"
              :total-visible="$vuetify.display.smAndDown ? 3 : 5"
              :length="Math.ceil(severanceList.length / options.itemsPerPage)"
            />
          </div>
        </VCardText>
      </template>
    </VDataTable>
    <VSnackbar
      v-model="isaddfaild"
      location="top"
      :timeout="3000"
      color="warning"
    >
      <VAlert
        density="compact"
        type="warning"
        title="warning!"
        text="근속일수가 1년이 안됩니다."
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
  </VCard>
</template>
