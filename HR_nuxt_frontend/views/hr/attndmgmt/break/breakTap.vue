<script setup lang="ts">
import { VDataTable } from 'vuetify/lib/labs/VDataTable/index.mjs';
import axios from 'axios';




</script>

<template>
  <VContainer>
    <VRow>
      <VCol cols="12" md="2" offset-md="6">
        <VBtn class="mt-6" height="43" width="150" @click="searchByType">
          <VIcon start icon="tabler-search" />현황 조회
        </VBtn>
      </VCol>
      <VCol cols="12" md="2">
        <VBtn class="mt-6" height="43" width="150" @click="updateStatus('근태승인')">
          <VIcon start icon="tabler-square-key" />근태외 승인
        </VBtn>
      </VCol>
      <VCol cols="12" md="2">
        <VBtn class="mt-6" height="43" width="150" @click="updateStatus('승인취소')">
          <VIcon start icon="tabler-square-x" />승인 취소
        </VBtn>
      </VCol>
    </VRow>
    <VRow class="mt-6">
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedSearchType" :items="searchType" label="조회선택타입" placeholder="조회선택타입" clearable
          clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedDeptCode" :items="dept_list" label="부서조회" placeholder="부서조회" clearable
          :disabled="selectedSearchType === '전체사원조회'" clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedEmpCode" :items="emp_list_by_dept" label="사원조회" placeholder="사원조회" clearable
          :disabled="selectedSearchType === '전체사원조회'" clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="startDate" label="시작일" placeholder="시작일" />
      </VCol>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="endDate" label="종료일" placeholder="종료일" />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedStatus" :items="applyStatusType" label="승인상태" placeholder="승인상태" clearable
          clear-icon="tabler-x" />
      </VCol>
    </VRow>
    <VRow class="mt-6">
      <VCol>
        <VDataTable :headers="headers" :items="day_attd_list" :items-per-page="10" @click:row="getRowData" />
      </VCol>
    </VRow>
  </VContainer>
</template>
