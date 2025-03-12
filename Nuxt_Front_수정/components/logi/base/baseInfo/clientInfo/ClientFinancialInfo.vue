<script lang="ts" setup>
import axios from 'axios'
import { VDataTable } from 'vuetify/labs/VDataTable'
import ClientFinancialAddModal from '../modal/ClientFinancialAddModal.vue'
import ClientFinancialUpdateModal from '../modal/ClientFinancialUpdateModal.vue'
import { baseStore } from '@/store/logi/base'

const Financialclientheaders = [
  { title: '금융거래처코드', key: 'accountAssociatesCode', width: 150 },
  { title: '사업장코드', key: 'workplaceCode', width: 150 },
  { title: '금융거래처명', key: 'accountAssociatesName', width: 150 },
  { title: '금융거래처유형', key: 'accountAssociatesType', width: 150 },
  { title: '계좌번호', key: 'accountNumber', width: 150 },
  { title: '계좌개설점', key: 'accountOpenPlace', width: 150 },
  { title: '카드번호', key: 'cardNumber', width: 150 },
  { title: '카드구분', key: 'cardType', width: 210 },
  { title: '카드회원명', key: 'cardMemberName', width: 150 },
  { title: '카드가맹점번호', key: 'cardOpenPlace', width: 150 },
  { title: '사업자등록번호', key: 'businessLicenseNumber', width: 150 },
  { title: '금융기관코드', key: 'financialInstituteCode', width: 150 },
  { title: '금융기관명', key: 'financialInstituteName', width: 150 },
  { title: '구분', key: 'divisionCodeNo', width: 150 },
]

const Financedata = ref([])
const isAddDialogVisible = ref(false)
const isUpdateDialogVisible = ref(false)
const selectedItem: Ref<any[]> = ref([])
const selecteditem = ref('')

const closeAddModal = async () => {
  isAddDialogVisible.value = false
}

const closeUpdateModal = async () => {
  isUpdateDialogVisible.value = false
}

// 금융거래처 조회
const search = async () => {
  await baseStore().GET_FINANCE_CLIENT_INFO()

  Financedata.value = baseStore().getFinanceClient
}

// 금융거래처 추가
const saveAddClient = async (clientdata: string) => {
  console.log('ruru', clientdata)

  await baseStore().ADD_FINANCE_CLIENT_INFO(clientdata)

  Financedata.value = baseStore().addfinanceClientInfo

  closeAddModal()

  search()
}

watch(selectedItem, (selectedValue: any) => {
  // console.log(selectedValue)
  // console.log(toRaw(selectedValue).length) // 1
  // console.log(toRaw(selectedValue[0]))
  // console.log(toRaw(selectedValue[0]))
  // console.log(toRaw(selectedValue[0].customerCode))
  if (toRaw(selectedValue.length) === 0)
    selecteditem.value = ''
  else
    selecteditem.value = toRaw(selectedValue[0].accountAssociatesCode)
  console.log(selecteditem.value)
})

// 수정하고 저장
watch(() => isUpdateDialogVisible, isVisible => {
  if (!isVisible)
    search()
})

// 삭제
const deleterow = async () => {
  if (selectedItem.value.length === 0) {
    console.warn('삭제할 항목을 선택하세요.')

    return
  }

  const accountAssociatesCode = selecteditem.value

  console.log('nana',accountAssociatesCode)

  await baseStore().DELETE_FINANCE_CLIENT_INFO(accountAssociatesCode)

  search()
}
</script>

<template>
  <VCard>
    <div style="margin-bottom: 20px" />

    <div style="margin-left: 20px">
      <VBtn
        class="button"
        color="primary"
        @click="search"
      >
        금융거래처 조회
      </VBtn>
      <!--
        <VBtn
        class="button"
        color="primary"
        >
        추가
        </VBtn>
      -->
      <ClientFinancialAddModal
        v-model="isAddDialogVisible"
        @closeAddModal="closeAddModal"
        @saveAddClient="saveAddClient"
      />
      <!--
        <VBtn
        class="button"
        color="primary"
        >
        수정
        </VBtn>
      -->
      <ClientFinancialUpdateModal
        v-model="isUpdateDialogVisible"
        :selecteditem="selecteditem"
        :search="search"
        @closeUpdateModal="closeUpdateModal"
        @saveUpdateClient="saveUpdateClient"
      />
      <VBtn
        class="button"
        color="primary"
        @click="deleterow"
      >
        금융거래처 삭제
      </VBtn>
    </div>

    <VCardText>
      <VDataTable
        v-model="selectedItem"
        :headers="Financialclientheaders"
        :items="Financedata"
        :items-per-page="5"
        show-select
        select-strategy="single"
        return-object
      />
    </VCardText>
  </VCard>
</template>

<style>
.button {
  margin-right: 20px;
}
</style>
