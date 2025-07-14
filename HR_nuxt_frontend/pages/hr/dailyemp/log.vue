<script setup lang="ts">
import { dailyLaborStore } from '@/store/hr/dailylabor';
import type { DailyLaborEmpLog, Headers, LogData } from '@/types/dailylabor/types';
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



const search = ref('')
const DailyLaborStore = dailyLaborStore()
const empLogList = ref<DailyLaborEmpLog[]>([])
const selectedRows = ref<DailyLaborEmpLog[]>([])

const headers: Headers[] = [
  { title: '성명', key: 'employeeName', align: 'center' },
  { title: '주민등록번호', key: 'residentialNumber', align: 'center' },
  { title: '프로젝트', key: 'project', align: 'center' },
  { title: '부서', key: 'department', align: 'center' },
  { title: '시작날짜', key: 'workStartDate', align: 'center' },
  { title: '종료날짜', key: 'workEndDate', align: 'center' },
  { title: '핸드폰', key: 'phoneNumber', align: 'center' },
]

const fetchData = async () => {
  await DailyLaborStore.FETCH_EMP_LOG()
  empLogList.value = await DailyLaborStore.empLogList
}

const removelog = async () => {
  const payload: LogData[] = []

  selectedRows.value.forEach(element => {
    const logData = {
      employeeName: element.employeeName,
      residentialNumber: element.residentialNumber,
    }

    payload.push(logData)
  })

  await DailyLaborStore.DELETE_EMP_LOG(payload)
  await fetchData()
}

const filteredData = computed(() => {
  return empLogList.value.filter((emp: DailyLaborEmpLog) => {
    return Object.values(emp).some(field => {
      if (typeof field === 'string')
        return field.toLowerCase().includes(search.value.toLowerCase())

      return false
    })
  })
})

onMounted(fetchData)
</script>

<template>
  <h1 class="mb-6">
    일용근로 직원이력 관리
  </h1>
  <VCard>
    <VContainer>
      <VCardText class="d-flex flex-wrap py-4 gap-4">
        <div style="inline-size: 10rem;">
          <AppTextField v-model="search" placeholder="Search" density="compact" />
        </div>
        <VSpacer />

        <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
          <VBtn prepend-icon="tabler-minus" @click="removelog">
            이력 삭제
          </VBtn>
        </div>
      </VCardText>
      <VDivider />
      <VDataTable v-model="selectedRows" :headers="headers" :items="filteredData" item-value="residentialNumber"
        return-object show-select />
    </VContainer>
  </VCard>
</template>
