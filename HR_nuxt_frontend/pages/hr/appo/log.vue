<script setup lang="ts">
import { affairStore } from '@/store/hr/affair';
import { onMounted, ref } from 'vue';
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

const store = affairStore()
const searchQuery = ref('') // searchQuery를 ref로 선언

const headers = [
  { title: '발령번호', key: 'appointNo' },
  { title: '사번', key: 'empCode' },
  { title: '직원명', key: 'empName' },
  { title: '부서', key: 'deptName' },
  { title: '발령부서', key: 'appointDept' },
  { title: '직급', key: 'positionName' },
  { title: '발령직급', key: 'appointLevel' },
  { title: '근무지', key: 'workPlaceName' },
  { title: '발령근무지', key: 'appointWP' },
  { title: '승인상태', key: 'status' },
]

const appointmentsLog = ref([])

onMounted(async () => {
  try {
    await store.FETCH_APPOINT_LOG()
    appointmentsLog.value = store.all_appointments_log
  }
  catch (error) {
    console.error('Failed to fetch appointments log', error)
  }
})
// 검색 이벤트 처리
const handleSearch = () => {
  // 검색 필터링 로직 추가 가능
  console.log('Searching:', searchQuery.value);
};
</script>

<template>
  <div>
    <h1 class="mb-6">인사발령 이력</h1>
    <VCard class="mt-3">
      <!-- 직원 정보 검색 -->
      <VTextField
        v-model="searchQuery"
        density="compact"
        placeholder="직원 정보 검색"
        append-inner-icon="tabler-search"
        single-line
        hide-details
        dense
        outlined
        @input="handleSearch"
      />
      <VDataTable
        v-model="selectedRows"
        :headers="headers"
        :items="appointmentsLog"
        :items-per-page="5"
        :footer-props="{
          'items-per-page-options': [5, 10, 20],
          'show-current-page': true,
          'show-first-last-page': true,
        }"
        :search="searchQuery"
        align="center"
        show-select
        item-value="appointNo"
        return-object
      >
        <template #item.empCode="{ item }">
          {{ item.empCode }}
        </template>
        <template #item.empName="{ item }">
          {{ item.empName }}
        </template>
        <template #item.deptName="{ item }">
          {{ item.deptName }}
        </template>
        <template #item.appointDept="{ item }">
          {{ item.appointDept }}
        </template>
        <template #item.appointWP="{ item }">
          {{ item.appointWP }}
        </template>
        <template #item.appointLevel="{ item }">
          {{ item.appointLevel }}
        </template>
        <template #item.status="{ item }">
          {{ item.status }}
        </template>
      </VDataTable>
    </VCard>
  </div>
</template>