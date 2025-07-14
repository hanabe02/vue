<script lang="ts" setup>
import BreakTap from '@/views/hr/attndmgmt/break/breakTap.vue';
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
  <h1 class="mb-6">근태외 관리</h1>
  <VCard>
    <VTabs v-model="tab" height="70">
      <VTab value="personal-info">
        근태외 조회/승인
      </VTab>
    </VTabs>
  </VCard>
  <VCard flat>
    <VCardText>
      <VWindow v-model="tab" class="disable-tab-transition">
        <VWindowItem value="personal-info">
          <BreakTap />
        </VWindowItem>
      </VWindow>
    </VCardText>
  </VCard>
</template>
