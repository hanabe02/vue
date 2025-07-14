<script lang="ts" setup>
import Annual from "@/views/hr/attndmgmt/annual/annual.vue";
import AnnualBatch from "@/views/hr/attndmgmt/annual/annualBatch.vue";
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



const tab = ref("personal-info");

const monthProps = [{ title: "2023-01" }];
</script>

<template>
  <h1 class="mb-6">휴가 관리</h1>
  <VCard>
    <VTabs v-model="tab" height="70">
      <VTab value="personal-info"> 연차 조회/승인 </VTab>
      <VTab value="account-details"> 연차 마감 </VTab>
    </VTabs>
  </VCard>
  <VCard flat>
    <VCardText>
      <VWindow v-model="tab" class="disable-tab-transition">
        <VWindowItem value="personal-info">
          <Annual />
        </VWindowItem>
        <VWindowItem value="account-details">
          <AnnualBatch />
        </VWindowItem>
      </VWindow>
    </VCardText>
  </VCard>
</template>
