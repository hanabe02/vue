<script setup lang="ts">
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


const headers = [
  { title: '비고', key: 'remark' },
  { title: '권한코드번호', key: 'authority_code_number' },
  { title: '권한코드이름', key: 'authority_code_name' },
  { title: '권한레벨', key: 'authority_level' },
]

const items = [
  { remark: '관리자 권한', authority_code_number: 'MA0001', authority_code_name: 'MASTER', authority_level: '1' },
  { remark: '인사 권한', authority_code_number: 'HR0001', authority_code_name: 'HUMAN_RESOURCE', authority_level: '1' },
  { remark: '회계 권한', authority_code_number: 'AC0001', authority_code_name: 'ACCOUNT', authority_level: '1' },
  { remark: '물류 권한', authority_code_number: 'LOGI0001', authority_code_name: 'LOGISTICS', authority_level: '1' },
]

const tab = ref('personal-info')

</script>

<template>
  <h1 class="mb-6">권한관리</h1>
  <VCard>
    <VTabs
      v-model="tab"
      height="70"
    >
      <VTab value="personal-info">
       권한관리
      </VTab>
    </VTabs>
  </VCard>
  <VDataTable
    :headers="headers"
    :items="items"
    :items-per-page="5"
  />
</template>
