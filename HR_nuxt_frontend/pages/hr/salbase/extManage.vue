<script lang="ts" setup>
import { paginationMeta } from "@/server/utils/paginationMeta";
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



const isSuccessFunc = ref(false);
const isaddBaseExt = ref(false);
const extSalCode = ref("");
const extSalName = ref("");
const ratio = ref("");
const isDialogBtn = ref(false);
const isDialogVisible = ref(false);
const baseExtSalList = ref([]);

const options = ref({
  page: 1,
  itemsPerPage: 5,
  sortBy: [""],
  sortDesc: [false],
});

const headers = [
  { title: "초과근무코드", key: "extSalCode" },
  { title: "초과근무종류", key: "extSalName" },
  { title: "배율", key: "ratio" },
];

const fetchData = async () => {
  await salaryStore().SEARCH_BASE_EXT_SAL();
  baseExtSalList.value = salaryStore().baseExtSalList;
};

const addBaseModal = async () => {
  extSalCode.value = "";
  extSalName.value = "";
  ratio.value = "";
  isDialogBtn.value = false;
  isDialogVisible.value = true;
};

const handleRowClick = async (row) => {
  extSalCode.value = row.item.extSalCode;
  extSalName.value = row.item.extSalName;
  ratio.value = row.item.ratio;
  isDialogBtn.value = true;
  isDialogVisible.value = true;
};

const editBaseExt = async () => {
  const editData = {
    extSalCode: extSalCode.value,
    extSalName: extSalName.value,
    ratio: ratio.value,
  };

  await salaryStore().EDIT_BASE_EXT_SAL(editData);
  await fetchData();
  isDialogVisible.value = false;
  isSuccessFunc.value = true;
};

const addBaseExt = async () => {
  const addData = {
    extSalCode: extSalCode.value,
    extSalName: extSalName.value,
    ratio: ratio.value,
  };

  if (
    ratio.value === "" ||
    ratio.value === null ||
    extSalName.value === "" ||
    extSalName.value === null
  ) {
    isaddBaseExt.value = true;
  } else {
    await salaryStore().ADD_BASE_EXT_SAL(addData);
    await fetchData();
    isDialogVisible.value = false;
    isSuccessFunc.value = true;
  }
};

const deleteBaseExt = async () => {
  await salaryStore().DELETE_BASE_EXT_SAL(extSalCode.value);
  await fetchData();
  isDialogVisible.value = false;
  isSuccessFunc.value = true;
};

onBeforeMount(fetchData);
</script>

<template>
  <h1 class="mb-6">초과수당관리</h1>
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
        <VBtn prepend-icon="tabler-plus" @click="addBaseModal">
          초과 기준 추가
        </VBtn>
      </div>
    </VCardText>
    <VDivider />
    <VDataTable
      :headers="headers"
      :items="baseExtSalList"
      :items-per-page="options.itemsPerPage"
      :page="options.page"
      :options="options"
      @click:row="(_, row) => handleRowClick(row)"
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
                  baseExtSalList.length
                )
              }}
            </p>
            <VPagination
              v-model="options.page"
              :total-visible="$vuetify.display.smAndDown ? 3 : 5"
              :length="Math.ceil(baseExtSalList.length / options.itemsPerPage)"
            />
          </div>
        </VCardText>
      </template>
    </VDataTable>
  </VCard>
  <VDialog v-model="isDialogVisible" max-width="600">
    <!-- Dialog close btn -->
    <DialogCloseBtn @click="isDialogVisible = !isDialogVisible" />

    <!-- Dialog Content -->
    <VCard title="초과 수당 기준 추가">
      <VCardText>
        <VRow v-if="isDialogBtn">
          <VCol cols="12" sm="6">
            <AppTextField
              v-model="extSalCode"
              label="초과 근무 코드"
              disabled
            />
          </VCol>
          <VCol cols="12" sm="6">
            <AppTextField
              v-model="extSalName"
              label="초과 근무 종류"
              disabled
            />
          </VCol>
          <VCol cols="12" sm="6">
            <AppTextField v-model="ratio" label="배율" />
          </VCol>
        </VRow>
        <VRow v-else>
          <VCol cols="12" sm="6">
            <AppTextField v-model="extSalCode" label="초과 근무 코드" />
          </VCol>
          <VCol cols="12" sm="6">
            <AppTextField v-model="extSalName" label="초과 근무 종류" />
          </VCol>
          <VCol cols="12" sm="6">
            <AppTextField v-model="ratio" label="배율" />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3 mt-2">
        <VBtn v-if="isDialogBtn" @click="editBaseExt"> 수정 </VBtn>
        <VBtn v-if="isDialogBtn" @click="deleteBaseExt"> 삭제 </VBtn>
        <VBtn v-else @click="addBaseExt"> 추가 </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
  <VSnackbar
    v-model="isaddBaseExt"
    location="top"
    :timeout="3000"
    color="warning"
  >
    <VAlert
      density="compact"
      type="warning"
      title="warning!"
      text="초과 근무 종류, 배율을 확인해주세요."
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
