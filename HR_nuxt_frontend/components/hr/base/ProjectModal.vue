<script lang="ts" setup>
import { baseStore } from '@/store/hr/base';
import { defineProps } from 'vue';

const props = defineProps(['fetchData'])

const BaseStore = baseStore()

const isDialogBtn = computed(() => {
  return BaseStore.isDialogBtn
})

const project = computed(() => {
  return BaseStore.project
})

const modifyProject = async () => {
  const projectData = {
    projectID: project.value.projectID,
    projectName: project.value.projectName,
    startDate: project.value.startDate,
    endDate: project.value.endDate,
    projectType: project.value.projectType,
    projectAmount: project.value.projectAmount,
    projectCategory: project.value.projectCategory,
    remarks: project.value.remarks,
    jobDescription: project.value.jobDescription,
  }

  await BaseStore.MODIFY_PROJECT(projectData)
  await props.fetchData()
  await BaseStore.SET_DIALOG(false)
}

const addProject = async () => {
  const projectData = {
    projectID: project.value.projectID,
    projectName: project.value.projectName,
    startDate: project.value.startDate,
    endDate: project.value.endDate,
    projectType: project.value.projectType,
    projectAmount: project.value.projectAmount,
    projectCategory: project.value.projectCategory,
    remarks: project.value.remarks,
    jobDescription: project.value.jobDescription,
  }

  await BaseStore.ADD_PROJECT(projectData)
  await props.fetchData()
  await BaseStore.SET_DIALOG(false)
}
</script>

<template>
  <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
    <VDialog v-model="BaseStore.isDialog" max-width="600">
      <!-- Dialog close btn -->
      <DialogCloseBtn @click="BaseStore.SET_DIALOG(false)" />

      <!-- Dialog Content -->
      <VCard title="프로젝트 추가">
        <VCardText>
          <VRow>
            <VCol v-if="isDialogBtn" cols="12" sm="6">
              <AppTextField v-model="project.projectID" label="코드" disabled />
            </VCol>
            <VCol v-else cols="12" sm="6">
              <AppTextField v-model="project.projectID" label="코드" />
            </VCol>
            <VCol cols="12" sm="6">
              <AppTextField v-model="project.projectName" label="프로젝트 명" />
            </VCol>
            <VCol cols="12" sm="6">
              <AppTextField v-model="project.startDate" label="시작 날짜" type="date" />
            </VCol>
            <VCol cols="12" sm="6">
              <AppTextField v-model="project.endDate" label="종료 날짜" type="date" />
            </VCol>
            <VCol cols="12" sm="6">
              <AppTextField v-model="project.projectType" label="유형" />
            </VCol>
            <VCol cols="12" sm="6">
              <AppTextField v-model="project.projectAmount" label="금액" />
            </VCol>
            <VCol cols="12" sm="6">
              <AppTextField v-model="project.projectCategory" label="분류" />
            </VCol>
            <VCol cols="12" sm="6">
              <AppTextField v-model="project.remarks" label="비고" />
            </VCol>
            <VCol>
              <AppTextarea v-model="project.jobDescription" label="직무내용" placeholder="직무내용을 작성해주세요." />
            </VCol>
          </VRow>
        </VCardText>
        <VCardText class="d-flex justify-end flex-wrap gap-3 mt-2">
          <VBtn v-if="isDialogBtn" @click="modifyProject">
            수정
          </VBtn>
          <VBtn v-else @click="addProject">
            추가
          </VBtn>
          <VBtn @click="BaseStore.SET_DIALOG(false)">
            닫기
          </VBtn>
        </VCardText>
      </VCard>
    </VDialog>
  </div>
  <VCard />
</template>
