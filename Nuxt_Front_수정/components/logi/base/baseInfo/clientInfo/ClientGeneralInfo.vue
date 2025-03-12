<script lang="ts" setup>
import axios from 'axios'
import { VDataTable } from 'vuetify/labs/VDataTable'
import ClientGeneralAddModal from '../modal/ClientGeneralAddModal.vue'
import ClientGeneralUpdateModal from '../modal/ClientGeneralUpdateModal.vue'
import { baseStore } from '@/store/logi/base'

const isAddDialogVisible = ref(false)
const isEditDialogVisible = ref(false)
const selectedClient = ref(null)

const selectedItem: Ref<any[]> = ref([])
const selectedCustomerCode = ref('')

const clientheaders = [
  { title: '일반거래처코드', key: 'customerCode', width: 150 },
  { title: '사업장코드', key: 'workplaceCode', width: 150 },
  { title: '거래처명', key: 'customerName', width: 150 },
  { title: '거래처유형', key: 'customerType', width: 150 },
  { title: '거래처대표자명', key: 'customerCeo', width: 150 },
  { title: 'status', key: 'status', width: 150 },
  { title: '사업자등록번호', key: 'businessLicenseNumber', width: 150 },
  { title: '개인거래처 주민등록번호', key: 'socialSecurityNumber', width: 210 },
  { title: '업태', key: 'customerBusinessConditions', width: 150 },
  { title: '종목', key: 'customerBusinessItems', width: 150 },
  { title: '우편번호', key: 'customerZipCode', width: 150 },
  { title: '기본주소', key: 'customerBasicAddress', width: 150 },
  { title: '세부주소', key: 'customerDetailAddress', width: 150 },
  { title: '전화번호', key: 'customerTelNumber', width: 150 },
  { title: '팩스번호', key: 'customerFaxNumber', width: 150 },
  { title: '비고', key: 'customerNote', width: 150 },
]

const generalClientdata = ref([])

// 일반거래처 조회
const search = async () => {
  await baseStore().GET_GENERAL_CLIENT_INFO()
  generalClientdata.value = baseStore().generalClientInfo
}

const closeAddModal = () => {
  isAddDialogVisible.value = false
}

const closeEditModal = () => {
  selectedCustomerCode.value = ''
  isEditDialogVisible.value = false
}

// const openUpdateModal = selected => {
//   selectedClient.value = selected
//   isDialogVisible.value = true
// }

// 거래처 추가
const saveClient = async (clientdata: any) => {
  try {
    console.log('ruru', clientdata)

    await baseStore().ADD_GENERAL_CLIENT_INFO(clientdata)

    generalClientdata.value = baseStore().addGeneralClientInfo

    closeAddModal()

    search()

    alert('거래처가 성공적으로 추가되었습니다.')
  }
  catch (error) {
    console.error('Error saving client:', error)
    alert('거래처 추가에 실패했습니다.')
  }
}

watch(selectedItem, (selectedValue: any) => {
  // console.log(selectedValue)
  // console.log(toRaw(selectedValue).length) // 1
  // console.log(toRaw(selectedValue[0]))
  // console.log(toRaw(selectedValue[0]))
  // console.log(toRaw(selectedValue[0].customerCode))
  if (toRaw(selectedValue.length) === 0)
    selectedCustomerCode.value = ''
  else
    selectedCustomerCode.value = toRaw(selectedValue[0].customerCode)
  console.log(selectedCustomerCode.value)
})

// 수정하고 저장
watch(() => isEditDialogVisible, isVisible => {
  if (!isVisible)
    search()
})

// 삭제
const deleterow = async () => {
  try {
    if (!selectedCustomerCode.value) {
      console.warn('삭제할 항목을 선택하세요.')

      return
    }

    const userConfirmed = window.confirm('삭제하시겠습니까?')

    if (userConfirmed) {
      const customerCode = selectedCustomerCode.value

      console.log('customerCode', customerCode)

      await baseStore().DELETE_GENERAL_CLIENT_INFO(customerCode)

      search()

      alert('거래처가 성공적으로 삭제되었습니다.')
    }
  }
  catch (error) {
    console.error('데이터 삭제 오류:', error)
    alert('거래처 삭제에 실패했습니다.')
  }
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
        일반거래처 조회
      </VBtn>
      <VBtn
        class="button"
        color="primary"
        @click="isAddDialogVisible = !isAddDialogVisible"
      >
        일반거래처 추가
      </VBtn>
      <VBtn
        class="button"
        color="primary"
        @click="isEditDialogVisible = !isEditDialogVisible"
      >
        일반거래처 수정
      </VBtn>
      <VBtn
        class="button"
        color="primary"
        @click="deleterow"
      >
        일반거래처 삭제
      </VBtn>
    </div>
    <VCardText>
      <VDataTable
        v-model="selectedItem"
        :headers="clientheaders"
        :items="generalClientdata"
        :items-per-page="5"
        show-select
        select-strategy="single"
        return-object
      />
    </VCardText>
  </VCard>

  <ClientGeneralAddModal
    v-model="isAddDialogVisible"
    :is-dialog-visible="isAddDialogVisible"
    @closeModal="closeAddModal"
    @saveClient="saveClient"
  />

  <ClientGeneralUpdateModal
    v-model="isEditDialogVisible"
    :is-dialog-visible="isEditDialogVisible"
    :selected-customer-code="selectedCustomerCode"
    :search="search"
    @closeModal="closeEditModal"
    @saveUpdateClient="saveUpdateClient"
  />
</template>

<style>
.button {
  margin-right: 20px;
}
</style>
