<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable'
import axios from 'axios'
import { baseStore } from '@/store/logi/base'

const companyinfoheaders = [
  { title: '회사코드', sortable: false, key: 'companyCode' },
  { title: '회사명', key: 'companyName' },
  { title: '회사구분', key: 'companyDivision' },
  { title: '사업장등록번호', key: 'businessLicenseNumber' },
  { title: '법인등록번호', key: 'corporationLicenseNumber' },
  { title: '대표자명', key: 'companyCEOName' },
  { title: '업태', key: 'companyBusinessConditions' },
  { title: '종목', key: 'companyBusinessItems' },
]

const companydtailinfoheaders = [
  { title: '우편번호', sortable: false, key: 'companyzipCode' },
  { title: '회사주소', key: 'companyBasicAddress' },
  { title: '세부주소', key: 'companyDetailAddress' },
  { title: '회사전화번호', key: 'companyTelNumber' },
  { title: '회사팩스번호', key: 'companyFAXNumber' },
  { title: '설립일자', key: 'companyEstablishmentDate' },
  { title: '회사개업일', key: 'companyOpenDate' },
  { title: '회사홈페이지', key: 'homepage' },
]

const infodata: Ref<any[]> = ref([])

const fetchData = async () => {
  await baseStore().GET_COMPANY_INFO()
  infodata.value = baseStore().companyInfo;
}

onMounted(async () => {
  fetchData()
})
</script>

<template>
  <p class="text-2xl mb-6">
    회사 정보
  </p>
  <VRow>
    <VCol cols="12">
      <!-- 👉 회사 기본 정보 -->
      <AppCardActions title="회사 기본 정보">
        <VCardText>
          <VDataTable
            :headers="companyinfoheaders"
            :items="infodata"
            :items-per-page="5"
          />
        </VCardText>
      </AppCardActions>
    </VCol>
    <VCol cols="12">
      <!-- 👉 회사 상세 정보 -->
      <AppCardActions title="회사 상세 정보">
        <VCardText>
          <VDataTable
            :headers="companydtailinfoheaders"
            :items="infodata"
            :items-per-page="5"
          />
        </VCardText>
      </AppCardActions>
    </VCol>
  </VRow>
</template>
