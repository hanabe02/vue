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
  { title: '코드분할번호', key: 'division_code_no' },
  { title: '코드타입', key: 'code_type' },
  { title: '분할코드명', key: 'division_code_name' },
  { title: '코드변경사항', key: 'code_change_available' },
  { title: '정규분포', key: 'description' },
]

const items = [
  { division_code_no: 'CO-02', code_type: '공통', division_code_name: '사업장', code_change_available: '변경불가능', description: 'null' },
  { division_code_no: 'CO-03', code_type: '공통', division_code_name: '부서', code_change_available: '변경불가능', description: 'null' },
  { division_code_no: 'CO-04', code_type: '공통', division_code_name: '창고', code_change_available: '변경불가능', description: 'null' },
  { division_code_no: 'HR-01', code_type: '공통', division_code_name: '직급', code_change_available: '변경가능', description: 'null' },
  { division_code_no: 'HR-02', code_type: '공통', division_code_name: '사원', code_change_available: '변경불가능', description: 'null' },
  { division_code_no: 'CL-01', code_type: '공통', division_code_name: '거래처', code_change_available: '변경불가능', description: 'null' },
  { division_code_no: 'CL-02', code_type: '회계', division_code_name: '금융거래처', code_change_available: '변경불가능', description: 'null' },
  { division_code_no: 'IT', code_type: '구매', division_code_name: '품목구분', code_change_available: '변경가능', description: 'null' },
  { division_code_no: 'IT-CI', code_type: '구매', division_code_name: '완제품', code_change_available: '변경불가능', description: 'null' },
  { division_code_no: 'IT-SI', code_type: '구매', division_code_name: '반제품', code_change_available: '변경불가능', description: 'null' },
  { division_code_no: 'IT-MA', code_type: '구매', division_code_name: '원재료', code_change_available: '변경불가능', description: 'null' },
  { division_code_no: 'CT', code_type: '영업', division_code_name: '수주 유형 분류', code_change_available: '변경가능', description: 'null' },
  { division_code_no: 'DT', code_type: '영업', division_code_name: '납품 여부 분류', code_change_available: '변경가능', description: 'null' },
  { division_code_no: 'IT-GROUP', code_type: '구매', division_code_name: '품목군 구분', code_change_available: '변경불가능', description: 'null' },
]

const tab = ref('personal-info')

</script>

<template>
  <h1 class="mb-6">코드조회</h1>
  <VCard>
    <VTabs
      v-model="tab"
      height="70"
    >
      <VTab value="personal-info">
       코드조회
      </VTab>
    </VTabs>
  </VCard>
  <VDataTable
    :headers="headers"
    :items="items"
    :items-per-page="5"
  />
</template>
