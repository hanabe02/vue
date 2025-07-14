<script lang="ts" setup>
import { baseStore } from "@/store/hr/base";
import { salaryStore } from "@/store/hr/salary";
import { VDataTable } from "vuetify/labs/VDataTable";

const baseInsureList = ref(salaryStore().baseInsureList);
const selectedYear = ref(baseStore().selectYear);

const majorheaders = [
  { title: "비고", key: "note", sortable: false },
  { title: "연금보험료(전체)(%)", key: "nationalPension", sortable: false },
  { title: "근로자(%)", key: "empNation", sortable: false },
  { title: "사업주(%)", key: "workNation", sortable: false },
];

const nationalheaders = [
  { title: "구분", key: "note", sortable: false },
  { title: "기준액", key: "standard", sortable: false },
  { title: "보험료율", key: "insureRate", sortable: false },
  { title: "근로자(%)", key: "empHealth", sortable: false },
  { title: "사업주(%)", key: "workHealth", sortable: false },
];

const Healthheaders = [
  { title: "구분", key: "note", sortable: false },
  { title: "보험료율(%)", key: "standard", sortable: false },
  { title: "근로자(%)", key: "empInsure", sortable: false },
  { title: "사업자(%)", key: "workInsure", sortable: false },
];

const preexistingheaders = [
  { title: "구분", key: "note", sortable: false },
  { title: "요율(%)", key: "industRate", sortable: false },
];

const newNationPension = computed(() => [
  {
    note: "기준 소득월액",
    nationalPension: baseInsureList.value.nationPensionRates,
    empNation: baseInsureList.value.nationPensionRates / 2,
    workNation: baseInsureList.value.nationPensionRates / 2,
  },
]);

const newHealthInsure = computed(() => [
  {
    note: "건강보험료",
    standard: "보수월액",
    insureRate: baseInsureList.value.healthInsureRates,
    empHealth: baseInsureList.value.healthInsureRates / 2,
    workHealth: baseInsureList.value.healthInsureRates / 2,
  },
  {
    note: "장기요양보험료",
    standard: "건강보험료",
    insureRate: baseInsureList.value.longTermCareRate,
    empHealth: baseInsureList.value.longTermCareRate / 2,
    workHealth: baseInsureList.value.longTermCareRate / 2,
  },
]);

const newEmpInsure = computed(() => [
  {
    note: "실업급여",
    standard: baseInsureList.value.wrkInsureRates,
    empInsure: baseInsureList.value.wrkInsureRates / 2,
    workInsure: baseInsureList.value.wrkInsureRates / 2,
  },
  {
    note: "고용안정, 직업능력 개발사업(150인미만)",
    standard: baseInsureList.value.jobstabilRates,
    empInsure: "-",
    workInsure: baseInsureList.value.jobstabilRates,
  },
]);

const newIndustInsure = computed(() => [
  {
    note: "업종요율(전기기계기구·정밀기구·전자제품 제조업)",
    industRate: baseInsureList.value.industInsureRates,
  },
  {
    note: "출퇴근재해요율",
    industRate: baseInsureList.value.industInsureCharRates,
  },
]);

const addNationalPension = async () => {
  const addSocialInsure = {
    nationpensionRates: newNationPension.value[0].nationalPension,
    attributionYear: selectedYear.value,
  };

  console.log(addSocialInsure);

  await salaryStore().ADD_SOCIAL_INSURE(addSocialInsure);
};

const addHealthInsure = () => {
  const addSocialInsure = {
    healthinsureRates: newHealthInsure.value[0].insureRate,
    longtermcareRate: newHealthInsure.value[1].insureRate,
    attributionYear: selectedYear.value,
  };

  salaryStore().ADD_SOCIAL_INSURE(addSocialInsure);
};

const addEmpInsure = () => {
  const addSocialInsure = {
    wrkinsureRates: newEmpInsure.value[0].standard,
    jobstabilRates: newEmpInsure.value[1].standard,
    attributionYear: selectedYear.value,
  };

  salaryStore().ADD_SOCIAL_INSURE(addSocialInsure);
};

const addIndustInsure = () => {
  const addSocialInsure = {
    industinsureRates: newIndustInsure.value[0].industRate,
    industinsurecharRates: newIndustInsure.value[1].industRate,
    attributionYear: selectedYear.value,
  };

  salaryStore().ADD_SOCIAL_INSURE(addSocialInsure);
};

watch(
  () => baseStore().selectYear,
  (newValue) => {
    selectedYear.value = newValue;
  }
);

watch(
  () => salaryStore().baseInsureList,
  (newValue) => {
    baseInsureList.value = newValue;
  }
);
</script>

<template>
  <VContainer class="mb-6">
    <VCardText class="d-flex flex-wrap py-4 gap-4">
      <h1 class="text-h3">국민연금</h1>
      <VSpacer />
      <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
        <VBtn @click="addNationalPension"> 국민연금 기준 등록 </VBtn>
      </div>
    </VCardText>
    <VDivider />
    <VDataTable class="mb-12" :headers="majorheaders" :items="newNationPension">
      <template #item.nationalPension="{ item }">
        <AppTextField v-model="item.nationalPension" />
      </template>
      <template #item.empNation="{ item }">
        <AppTextField v-model="item.empNation" disabled />
      </template>
      <template #item.workNation="{ item }">
        <AppTextField v-model="item.workNation" disabled />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
    <VCardText class="d-flex flex-wrap py-4 gap-4">
      <h1 class="text-h3">건강보험</h1>
      <VSpacer />
      <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
        <VBtn @click="addHealthInsure"> 건강보험 기준 등록 </VBtn>
      </div>
    </VCardText>
    <VDivider />
    <VDataTable
      class="mb-12"
      :headers="nationalheaders"
      :items="newHealthInsure"
    >
      <template #item.insureRate="{ item }">
        <AppTextField v-model="item.insureRate" />
      </template>
      <template #item.empHealth="{ item }">
        <AppTextField v-model="item.empHealth" disabled />
      </template>
      <template #item.workHealth="{ item }">
        <AppTextField v-model="item.workHealth" disabled />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
    <VCardText class="d-flex flex-wrap py-4 gap-4">
      <h1 class="text-h3">고용보험</h1>
      <VSpacer />
      <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
        <VBtn @click="addEmpInsure"> 고용보험 기준 등록 </VBtn>
      </div>
    </VCardText>
    <VDivider />
    <VDataTable class="mb-12" :headers="Healthheaders" :items="newEmpInsure">
      <template #item.standard="{ item }">
        <AppTextField v-model="item.standard" />
      </template>
      <template #item.empInsure="{ item }">
        <AppTextField v-model="item.empInsure" disabled />
      </template>
      <template #item.workInsure="{ item }">
        <AppTextField v-model="item.workInsure" disabled />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
    <VCardText class="d-flex flex-wrap py-4 gap-4">
      <h1 class="text-h3">산재보험</h1>
      <VSpacer />
      <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
        <VBtn @click="addIndustInsure"> 산재보험 기준 등록 </VBtn>
      </div>
    </VCardText>
    <VDivider />
    <VDataTable
      class="mb-12"
      :headers="preexistingheaders"
      :items="newIndustInsure"
    >
      <template #item.industRate="{ item }">
        <AppTextField v-model="item.industRate" />
      </template>
      <template #bottom>
        <VDivider />
      </template>
    </VDataTable>
  </VContainer>
</template>
