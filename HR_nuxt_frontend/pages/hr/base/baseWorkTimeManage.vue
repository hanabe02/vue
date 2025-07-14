<script setup lang="ts">
import axios from 'axios';
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
  { title: '현재년도', key: 'applyYear', width: 110 },
  { title: '점심종료시간', key: 'lunchEndTime', width: 140 },
  { title: '저녁종료시간', key: 'dinnerEndTime', width: 140 },
  { title: '미팅시간', key: 'attendTime', width: 140 },
  { title: '저녁미팅종료', key: 'overEndTime', width: 140 },
  { title: '쉬는시간', key: 'quitTime', width: 140 },
  { title: '점심근무시간', key: 'lunchStartTime', width: 140 },
  { title: '야간근무시간', key: 'nightEndTime', width: 140 },
  { title: '저녁근무시간', key: 'dinnerStartTime', width: 140 },
]

const base_worktime_list = ref([])
const tab = ref('personal-info')

onMounted(() => {
  fetchBaseWorkTime()
})

const fetchBaseWorkTime = async () => {
  try {
    const response = await axios.get('http://localhost:8282/hr/base/baseWorkTimeList')

    base_worktime_list.value = response.data.gridRowJson
    console.log('[baseWorkTime]', base_worktime_list.value)
  }
  catch (e) { console.error(e) }
}
</script>

<template>
  <h1 class="mb-6">기준근무시간관리</h1>
  <VCard>
  <VTabs
      v-model="tab"
      height="70"
    >
      <VTab value="personal-info">
        기준근무시간관리
      </VTab>
    </VTabs>
  </VCard>
  <VDataTable
    :headers="headers"
    :items="base_worktime_list"
    :items-per-page="10"
    fixed-header
  />
</template>
