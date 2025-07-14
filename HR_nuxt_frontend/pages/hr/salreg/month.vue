<script setup lang="ts">
import SalregMonthModal from '@/components/hr/salary/SalregMonthModal.vue'
import { paginationMeta } from '@/server/utils/paginationMeta'
import { baseStore } from '@/store/hr/base'
import { salaryStore } from '@/store/hr/salary'
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



const options = ref({ page: 1, itemsPerPage: 5, sortBy: [''], sortDesc: [false] })


const selectedRow = ref(null)
const search = ref('')
const selectedYear = ref('')
const selectedMonth = ref('')
const initSalaryList: any = ref([])
const monthList: any = ref([])
const yearList: any = ref([])

const headers = [
  { title: '해당 년월', key: 'applyYearMonth', align: 'center' },
  { title: '사원 번호', key: 'empCode', align: 'center' },
  { title: '사원 명', key: 'empName', align: 'center' },
  { title: '실 지급액', key: 'realSalary', align: 'center' },
  { title: '승인상태', key: 'finalizeStatus', align: 'center' },
   { title: '작업', key: 'actions', align: 'center' },
]

const fetchData = async () => {
  await baseStore().FETCH_MONTHLIST()
  await baseStore().FETCH_YEARLIST()

  const monthListData = baseStore().monthList
  const yearListData = baseStore().yearList

  monthList.value = monthListData ? Object.values(monthListData).map(item => item.month) : []
  yearList.value = yearListData ? Object.values(yearListData).map(item => item.year) : []
}

const fetchData2 = async () => {
  const applyYear = `${selectedYear.value}-${selectedMonth.value.trim()}`

  const initData = {
    applyYearMonth: applyYear,
    empCode: '%',
  }

  await salaryStore().SEARCH_SALARY_INIT(initData)
  initSalaryList.value = salaryStore().initSalaryList
}

// 프로시져 문제

const handleRowClick = async row => {
  const salaryData = {
    empCode: row.item.empCode,
    deptCode: row.item.deptCode,
    applyYearMonth: `${selectedYear.value}-${selectedMonth.value.trim()}`,
  }

  const monthDeductionData = {
    empCode: row.item.empCode,
    applyYearMonth: `${selectedYear.value}-${selectedMonth.value.trim()}`,
  }

  console.log(monthDeductionData)
  if (row.item.finalizeStatus === 'N' || row.item.finalizeStatus === null) {
    await salaryStore().SALARY_PROCESS(salaryData)

    await salaryStore().FIND_TAX(monthDeductionData)
    salaryStore().SET_DIALOG_BTN(true)
  }

  else {
    await salaryStore().SALARY_PROCESS(salaryData)

    await salaryStore().FIND_TAX(monthDeductionData)
    salaryStore().SET_DIALOG_BTN(false)
  }
  await salaryStore().SET_DIALOG(true)
}

const filteredData = computed(() => {
  return initSalaryList.value.filter((emp: any) => {
    return Object.values(emp).some(field => {
      if (typeof field === 'string')
        return field.toLowerCase().includes(search.value.toLowerCase())

      return false
    })
  })
})



const handleSalaryCreate = async (item: any) => {
  const yearMonth = `${selectedYear.value}-${selectedMonth.value.trim().padStart(2, '0')}`

  const rowData = {
    empCode: item.empCode,
    deptCode: item.deptCode,
    applyYearMonth: yearMonth,
  }

  const monthDeductionData = {
    empCode: item.empCode,
    applyYearMonth: yearMonth,
  }

  try {

     salaryStore().SET_SELECTED_ROW(rowData)
       console.log("📌 SET_SELECTED_ROW rowData:", rowData)
    // ✅ 먼저 rowList 설정 (모달에서 보여줄 기본급, 상여 등 포함)
    await salaryStore().SET_ROW_LIST(rowData)

    // ✅ 공제 항목도 미리 불러오기
    await salaryStore().FIND_TAX(monthDeductionData)

    // ✅ 모드 구분용 버튼 상태 설정
    salaryStore().SET_DIALOG_BTN('create')

    // ✅ 모달 열기
    salaryStore().SET_DIALOG(true)

  } catch (err) {
    console.error("급여 생성 준비 실패", err)
    alert("급여 생성 중 오류가 발생했습니다.")
  }
}



onBeforeMount(fetchData)
watch([selectedYear, selectedMonth], fetchData2, { immediate: true })
</script>

<template>
  <h1 class="mb-6">
    월 급여 등록
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
      </VRow>
    </VCardText>
  </VCard>
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
        <template #[`item.actions`]="{ item }">
    <VBtn size="small" @click="handleSalaryCreate(item)">급여 생성</VBtn>
  </template>
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
 <SalregMonthModal :fetch-data2="fetchData2" />

</template>
