<script lang="ts" setup>
import DailyBatchTap from '@/views/hr/attndmgmt/daily/dailyBatchTap.vue';
import DailySearchTap from '@/views/hr/attndmgmt/daily/dailySearchTap.vue';
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


const tab = ref('personal-info')
</script>

<template>
  <h1 class="mb-6">일근태관리</h1>
  <VCard>
    <VTabs
      v-model="tab"
      height="70"
    >
      <VTab value="personal-info">
        일근태 마감관리
      </VTab>
      <VTab value="account-details">
        일근태 조회/승인
      </VTab>
    </VTabs>
  </VCard>
  <VCard flat>
    <VCardText>
      <VWindow
        v-model="tab"
        class="disable-tab-transition"
      >
        <!-- 마감관리 -->
        <VWindowItem value="personal-info">
          <DailyBatchTap />
        </VWindowItem>

        <!-- 근태 조회/승인 -->
        <VWindowItem value="account-details">
          <DailySearchTap />
        </VWindowItem>
      </VWindow>
    </VCardText>
  </VCard>
</template>
