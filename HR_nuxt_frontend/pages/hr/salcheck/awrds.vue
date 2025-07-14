<script setup lang="ts">
import { paginationMeta } from '@/server/utils/paginationMeta'
import { affairStore } from '@/store/hr/affair'
import { baseStore } from '@/store/hr/base'
import { salaryStore } from '@/store/hr/salary'
import type { SalaryHeader } from '@/types/salary/types'
import { VDataTable } from 'vuetify/labs/VDataTable'
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
const selectedYear = ref('')
const selectedMonth = ref('')
const selectedEmp = ref('')
const bonusList: any = ref([])
const monthList: any = ref([])
const yearList: any = ref([])
const empList: any = ref([])

const headers: SalaryHeader[] = [
  { title: '해당 년월', key: 'applyYearMonth', align: 'center' },
  { title: '사원 명', key: 'empName', align: 'center' },
  { title: '지급액', key: 'bonus', align: 'center' },
  { title: '분류', key: 'detailCodeName', align: 'center' },
  { title: '승인상태', key: 'finalizeStatus', align: 'center' },
]

const fetchData = async () => {
  await useAsyncData(async () => {
    await affairStore().FETCH_ALL_EMP()
    await baseStore().FETCH_MONTHLIST()
    await baseStore().FETCH_YEARLIST()
  })

  const monthListData = baseStore().monthList
  const yearListData = baseStore().yearList
  const empListData = affairStore().all_emp_list

  monthList.value = monthListData ? Object.values(monthListData).map(item => item.month) : []
  yearList.value = yearListData ? Object.values(yearListData).map(item => item.year) : []
  empList.value = empListData
    ? Object.values(empListData).map(item => {
      return { title: item.empName, value: item.empCode }
    })
    : []
}

onBeforeMount(fetchData)

const options = ref({ page: 1, itemsPerPage: 5, sortBy: [''], sortDesc: [false] })

const fetchData2 = async () => {
  const month = !selectedMonth.value ? '%' : selectedMonth.value
  const emp = !selectedEmp.value ? '%' : selectedEmp.value

  const findSalaryData = {
    applyYearMonth: `${selectedYear.value}-${month.trim()}`,
    empCode: emp,
    detailCodeName: '%',
    finalizeStatus: 'Y',
  }

  await salaryStore().FIND_BONUS(findSalaryData)

  bonusList.value = (salaryStore().bonusList)
}

const filteredData = computed(() => {
  return bonusList.value.filter((emp: any) => {
    return Object.values(emp).some(field => {
      if (typeof field === 'string')
        return field.toLowerCase().includes(search.value.toLowerCase())

      return false
    })
  })
})

watch([selectedYear, selectedMonth, selectedEmp], fetchData2, { immediate: true })
</script>

<template>
  <h1 class="mb-6">
    상여 및 성과금 조회
  </h1>
  <VCard class="mb-6">
    <VCardText>
      <VRow>
        <VCol cols="12" sm="4">
          <AppSelect v-model="selectedYear" label="해당 연도" placeholder="해당 연도" :items="yearList" clearable
            clear-icon="tabler-x" />
        </VCol>

        <VCol cols="12" sm="4">
          <AppSelect v-model="selectedMonth" label="해당 월" placeholder="해당 월" :items="monthList" clearable
            clear-icon="tabler-x" />
        </VCol>

        <VCol cols="12" sm="4">
          <AppSelect v-model="selectedEmp" label="해당 사원" placeholder="해당 사원" :items="empList" clearable
            clear-icon="tabler-x" />
        </VCol>
      </VRow>
    </VCardText>
  </Vcard>
  <VCard>
    <VCardText class="d-flex flex-wrap py-4 gap-4">
      <div class="me-3 d-flex gap-3">
        <AppSelect :model-value="options.itemsPerPage" :items="[
          { value: 5, title: '5' },
          { value: 10, title: '10' },
          { value: 25, title: '25' },
          { value: 50, title: '50' },
          { value: -1, title: 'All' },
        ]" style="inline-size: 6.25rem;" @update:model-value="options.itemsPerPage = parseInt($event, 10)" />
      </div>
      <VSpacer />

      <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
        <!-- 👉 Search  -->
        <div style="inline-size: 10rem;">
          <AppTextField v-model="search" placeholder="Search" density="compact" />
        </div>
      </div>
    </VCardText>
    <VDivider />
    <VDataTable :headers="headers" :items="filteredData" :items-per-page="options.itemsPerPage" :page="options.page"
      :options="options">
      <template #bottom>
        <VDivider />
        <VCardText class="pt-2">
          <div class="d-flex flex-wrap justify-center justify-sm-space-between gap-y-2 mt-2">
            <p class="text-sm text-disabled mb-0">
              {{ paginationMeta({ page: options.page, itemsPerPage: options.itemsPerPage }, filteredData.length) }}
            </p>

            <VPagination v-model="options.page" :total-visible="$vuetify.display.smAndDown ? 3 : 5"
              :length="Math.ceil(filteredData.length / options.itemsPerPage)" />
          </div>
        </VCardText>
      </template>
    </VDataTable>
  </VCard>
</template>
