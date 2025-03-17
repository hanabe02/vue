<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable'
import { baseStore } from '@/store/logi/base'

const headers = [
  { title: '사업장 코드', key: 'workplaceCode' },
  { title: '부서코드', key: 'deptCode' },
  { title: '부서명', key: 'deptName' },
  { title: '사업장명', key: 'deptName' },
  { title: '회사코드', key: 'companyCode' },
  { title: '부서신설일', key: 'deptStartDate' },
  { title: '부서해체일', key: 'deptEndDate' },
]

const deptInfodata: Ref<any[]> = ref([])

const fetchData = async () => {
  await baseStore().GET_DEPT_INFO()
  deptInfodata.value = baseStore().deptInfo
}

   
onMounted(async () => {
  fetchData()
})
</script>

<template>
  <p class="text-2xl mb-6">
    부서 정보
  </p>
  <VRow>
    <VCol cols="12">
      <!-- 👉 부서 정보  -->
      <AppCardActions title="부서 정보">
        <VCardText>
          <VDataTable
            :headers="headers"
            :items="deptInfodata"
            s
            :items-per-page="5"
          />
        </VCardText>
      </AppCardActions>
    </VCol>
  </vrow>
</template>
