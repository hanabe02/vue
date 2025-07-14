<script setup lang="ts">
import ProjectModal from '@/components/hr/base/ProjectModal.vue';
import { baseStore } from '@/store/hr/base';
import { VDataTable } from 'vuetify/labs/VDataTable';
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

const initialProjectArray = ref([])
const BaseStore = baseStore()
const projectList = ref([])
const selectedRows = ref([])

const headers = [
  { title: '프로젝트 코드', key: 'projectID', align: 'center' },
  { title: '프로젝트 명', key: 'projectName', align: 'center' },
  { title: '시작 날짜', key: 'startDate', align: 'center' },
  { title: '종료 날짜', key: 'endDate', align: 'center' },
  { title: '유형', key: 'projectType', align: 'center' },
  { title: '금액', key: 'projectAmount', align: 'center' },
  { title: '분류', key: 'projectCategory', align: 'center' },
]

const fetchData = async () => {
  await BaseStore.FETCH_PROJECT()
  projectList.value = await BaseStore.projectList
}

const addProjectBtn = () => {
  BaseStore.SET_DIALOG_BTN(false)
  BaseStore.SET_PROJECT_ROW(initialProjectArray.value)
  BaseStore.SET_DIALOG(true)
}

const removeProject = async () => {
  for (const key in selectedRows.value) {
    if (selectedRows.value.hasOwnProperty(key)) {
      const value = selectedRows.value[key]

      await BaseStore.REMOVE_PROJECT(value)
    }
  }
  await fetchData()
}

const handleRowClick = row => {
  BaseStore.SET_DIALOG_BTN(true)
  BaseStore.SET_PROJECT_ROW(row.item)
  BaseStore.SET_DIALOG(true)
}

onMounted(fetchData)
</script>

<template>
  <h1 class="mb-6">
    프로젝트 관리
  </h1>
  <VCard>
    <VContainer>
      <VCardText class="d-flex flex-wrap py-4 gap-4">
        <div style="inline-size: 10rem;">
          <AppTextField placeholder="Search" density="compact" />
        </div>
        <VSpacer />

        <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
          <VBtn prepend-icon="tabler-plus" @click="addProjectBtn">
            프로젝트 추가
          </VBtn>
          <VBtn prepend-icon="tabler-minus" @click="removeProject">
            프로젝트 삭제
          </VBtn>
        </div>
      </VCardText>
      <VDivider />
      <VDataTable v-model="selectedRows" :headers="headers" :items="projectList" item-value="projectID" show-select
        @click:row="(_, row) => handleRowClick(row)" />
    </VContainer>
  </VCard>
  <ProjectModal :fetch-data="fetchData" />
</template>
