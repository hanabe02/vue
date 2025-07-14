<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import { clientDeliveryStore } from '@/store/logi/clientDelivery'
import { outputStore } from '@/store/logi/output'
import AddModal from '@/components/logi/deliveryInfo/modal/AddModal.vue'
import UpdateModal from '@/components/logi/deliveryInfo/modal/UpdateModal.vue'

const title = ref('배송서비스')
const selectedItem: Ref<any[]> = ref([])
const selecteditem = ref('')
const selectedOutputNumber = ref(null)

const isAddModalVisible = ref(false)
const isUpdateDialogVisible = ref(false)
const isOutputVisible = ref(true)
const isclientDeliveryVisible = ref(false)

const headers = [
  { title: '배송번호', key: 'deliveryNumber', width: 120 },
  { title: '출고번호', key: 'outputNumber', width: 120 },
  { title: '상품명', key: 'itemName', width: 120 },
  { title: '이름', key: 'name', width: 120 },
  { title: '연락처', key: 'tel', width: 120 },
  { title: '배송일자', key: 'deliveryDate', width: 120 },
  { title: '출발지', key: 'warehouseCode', width: 120 },
  { title: '도착지', key: 'endingRoute', width: 120 },
  { title: '배송상태', key: 'deliveryStatus', width: 120 },
]

const outputheaders = [
  { title: '출고번호', sortable: false, key: 'outputNumber',width: 120 },
  { title: '수주일련번호', sortable: false, key: 'contractNo', width: 120 },
  { title: '등록일자', key: 'requestDate', width: 120 },
  { title: '거래처명', key: 'customerName', width: 120 },
  { title: '창고', key: 'warehouseCode', width: 120 },
  { title: '출고담당자', key: 'requestPerson', width: 150 },
  { title: '품목명', key: 'itemName', width: 120 },
  { title: '규격', key: 'standard', width: 120 },
  { title: '납기일', key: 'dueDate', width: 120 },
  { title: '입고예정일', key: 'expectedArrivalDate', width: 150 },
  { title: '단위', key: 'unitOfStock', width: 120 },
  { title: '출고수량', key: 'requestQuantity', width: 120 },
  { title: '검사', key: 'inspection', width: 120 },
]
        
const addClose = () => {
  isAddModalVisible.value = false
  console.log('닫기', isAddModalVisible.value)
}

const updateClose = () => {
  isUpdateDialogVisible.value = false
}

const clientDeliveryData = ref([])
const outputData = ref([])

// 출고현황조회
const outputList = async () => {
  await outputStore().GET_OUTPUT_INFO()
  outputData.value = outputStore().getOutputInfo

  isOutputVisible.value = true
  isclientDeliveryVisible.value = false
  title.value = '출고등록현황'
}

// 배송 조회
const allclientDelivery = async () => {
  try {
    await clientDeliveryStore().GET_CLIENT_DELIVERY_URL()
    clientDeliveryData.value = clientDeliveryStore().getClientDeliveryInfo
    isclientDeliveryVisible.value = true
    isOutputVisible.value = false
    title.value = '배송서비스 조회'
  }
  catch (error) {
    console.error('Error while fetching all transportation data:', error)
  }
}

const list = async (code: any, row: { item: { outputNumber: any; itemName: any; warehouseCode: any } }) => {
  console.log('row', row.item)
  console.log('row', row.item.outputNumber)
  console.log('row갲', row.item.itemName)

  selectedOutputNumber.value = {
    outputNumber: row.item.outputNumber,
    itemName: row.item.itemName,
    warehouseCode: row.item.warehouseCode,
  }

  const outputNumber = row.item.outputNumber

  console.log('rowrow', outputNumber)

  isAddModalVisible.value = true
}

// 추가
const add = async (addData: object) => {
  console.log('ruru', addData)

  await clientDeliveryStore().ADD_CLIENT_DELIVERY_URL(addData)

  addClose()

  await allclientDelivery()
}

watch(selectedItem, (selectedValue: any) => {
  // console.log(selectedValue)
  // console.log(toRaw(selectedValue))
  // console.log(toRaw(selectedValue).length) // 1
  // console.log(toRaw(selectedValue[0]))
  // console.log(toRaw(selectedValue[0]))

  // console.log(toRaw(selectedValue[0].customerCode))
  if (toRaw(selectedValue.length) === 0)
    selecteditem.value = ''
  else
    selecteditem.value = toRaw(selectedValue[0])
  console.log('뭐선택', selecteditem.value)
})

// 수정
const update = async (addData: any) => {
  console.log('qqqq', addData)

  await clientDeliveryStore().UPDATE_CLIENT_DELIVERY_URL(addData)

  isUpdateDialogVisible.value = false

  await allclientDelivery()
}

// 삭제
const deleteItem = async () => {
  if (!selecteditem.value.deliveryNumber) {
    console.error('No workplace selected for deletion')

    return
  }
  const confirmed = window.confirm('정말로 삭제하시겠습니까?')

  if (confirmed) {
    const deliveryNumber = selecteditem.value.deliveryNumber

    try {
      await clientDeliveryStore().DELETE_CLIENT_DELIVERY_URL(deliveryNumber)
      allclientDelivery()
      alert('성공적으로 삭제되었습니다.')
    }
    catch (error) {
      console.error('Error deleting workplace:', error)
      alert('삭제에 실패했습니다.')
    }
  }
}
</script>

<template>
  <VCard>
    <div
      class="text-2xl"
      style="margin-left: 20px; margin-top: 20px"
    >
      <p
        class="text-2xl"
        style="margin-left: 10px;"
      >
        {{ title }}
      </p>

      <div class="d-flex justify-end mt-4 mr-8">
        <VBtn
          style="margin-left: 20px;"
          class="button"
          color="primary"
          @click="outputList"
        >
          출고현황
        </VBtn>
        <VBtn
          style="margin-left: 20px;"
          class="button"
          color="primary"
          @click="allclientDelivery"
        >
          배송조회
        </VBtn>
        <UpdateModal
          v-model="isUpdateDialogVisible"
          :selecteditem="selecteditem"
          @updateClose="updateClose"
          @update="update"
        />
        <VBtn
          style="margin-left: 20px;"
          class="button"
          color="primary"
          @click="deleteItem"
        >
          배송목록삭제
        </VBtn>
      </div>
    </div>

    <!-- 배송서비스 -->
    <VCardText
      v-if="isclientDeliveryVisible"
      class="d-flex"
    >
      <VDataTable
        v-model="selectedItem"
        :headers="headers"
        :items="clientDeliveryData"
        :items-per-page="10"
        show-select
        select-strategy="single"
        return-object
      />
    </VCardText>

    <div>
      <!-- 출고 현황 -->
      <VCardText
        v-if="isOutputVisible"
        class="d-flex"
      >
        <VDataTable
          v-model="selectedItem2"
          :headers="outputheaders"
          :items="outputData"
          :items-per-page="10"
          @click:row="list"
        />
      </VCardText>
    </div>
  </VCard>

  <AddModal
    :is-add-modal-visible="isAddModalVisible"
    :selected-output-number="selectedOutputNumber"
    @add-close="addClose"
    @add="add"
  />
</template>
