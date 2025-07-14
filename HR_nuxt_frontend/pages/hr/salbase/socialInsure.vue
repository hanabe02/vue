<script lang="ts" setup>
import { baseStore } from '@/store/hr/base'
import { salaryStore } from '@/store/hr/salary'
import EmploymentTap from '@/views/hr/salbase/socialinsure/employmentTap.vue'
import HealthTap from '@/views/hr/salbase/socialinsure/healthTap.vue'
import MajorTap from '@/views/hr/salbase/socialinsure/majorTap.vue'
import NationalTap from '@/views/hr/salbase/socialinsure/nationalTap.vue'
import PreexistingTap from '@/views/hr/salbase/socialinsure/preexistingTap.vue'
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
const selectedYear = ref('')
const yearList: any = ref([])

const fechData = async () => {
  await baseStore().FETCH_YEARLIST()

  const yearListData = await baseStore().yearList

  yearList.value = yearListData ? Object.values(yearListData).map(item => item.year) : []
}

const searchYear = async () => {
  if (selectedYear.value === '' || selectedYear.value === null) {
    console.log('Year값 없음')
  }
  else {
    await salaryStore().SEARCH_SOCIAL_INSURE(selectedYear.value)
    await baseStore().SET_SELECT_YEAR(selectedYear.value)
  }
}

onBeforeMount(fechData)
watch([selectedYear], searchYear, { immediate: true })
</script>

<template>
  <h1 class="mb-6">사회보험관리</h1>
  <VCard class="mb-6">
    <VCardText>
      <VRow>
        <VCol cols="12" sm="4">
          <AppSelect v-model="selectedYear" label="적용년도" :items="yearList" single-line />
        </VCol>
      </VRow>
    </VCardText>
  </VCard>
  <VCard>
    <VTabs v-model="tab" height="70">
      <VTab value="major-insurance">
        4대보험
      </VTab>
      <VTab value="national-pension">
        국민연금
      </VTab>
      <VTab value="Health-Insurance">
        건강보험
      </VTab>
      <VTab value="Employment-Insurance">
        고용보험
      </VTab>
      <VTab value="pre-existing-insurance">
        산재보험
      </VTab>
    </VTabs>
  </VCard>
  <VCard flat>
    <VCardText>
      <VWindow v-model="tab" class="disable-tab-transition">
        <VWindowItem value="major-insurance">
          <MajorTap />
        </VWindowItem>

        <VWindowItem value="national-pension">
          <NationalTap />
        </VWindowItem>

        <VWindowItem value="Health-Insurance">
          <HealthTap />
        </VWindowItem>

        <VWindowItem value="Employment-Insurance">
          <EmploymentTap />
        </VWindowItem>

        <VWindowItem value="pre-existing-insurance">
          <PreexistingTap />
        </VWindowItem>
      </VWindow>
    </VCardText>
  </VCard>
</template>
