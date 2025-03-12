<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import data from '@/views/demos/forms/tables/data-table/datatable'
import { outputStore } from '@/store/logi/output'
import RegistModal from '@/components/logi/outputInfo/modal/RegistModal.vue'

const title = ref('출고등록')
const outputData = ref([])
const outputDetailData: Ref<any[]> = ref([])
const contractData = ref([])
const contractDetailData = ref([])
const selectedOutputNumber = ref(null)
const selectedcontractNo = ref(null)

const selectedItem: Ref<any[]> = ref([])
const selecteditem: Ref<any[]> = ref([])

const selectedItem2: Ref<any[]> = ref([])
const selecteditem2: Ref<any[]> = ref([])

const Contractheaders = [
  { title: '수주일련번호', sortable: false, key: 'contractNo', width: 120 },
  { title: '회사코드', key: 'customerCode', width: 150 },
  { title: '수주유형분류', key: 'contractTypeName', width: 170 },
  { title: '수주일자', key: 'contractDate', width: 120 },
  { title: '수주요청자', key: 'contractRequester', width: 150 },
  { title: '수주담당자', key: 'empNameInCharge', width: 150 },
  { title: '납품완료여부', key: 'deliveryCompletionStatus', width: 150 },
  { title: '비고', key: 'description', width: 120 },
]

const ContractDetailheaders = [
  { title: '수주상세일련번호', sortable: false, key: 'contractDetailNo', width: 180 },
  { title: '품목명', key: 'itemName', width: 120 },
  { title: '납기일', key: 'dueDateOfContract', width: 150 },
  { title: '견적수량', key: 'estimateAmount', width: 120 },
  { title: '재고사용량', key: 'stockAmountUse', width: 150 },
  { title: '합계액', key: 'sumPriceOfContract', width: 150 },
  { title: '납품완료여부', key: 'deliveryCompletionStatus', width: 150 },
  { title: '비고', key: 'description', width: 150 },
]

const Registheaders1 = [
  { title: '출고번호', sortable: false, key: 'outputNumber' },
  { title: '수주일련번호', sortable: false, key: 'contractNo', width: 120 },
  { title: '등록일자', key: 'requestDate', width: 120 },
  { title: '거래처명', key: 'customerName', width: 120 },
  { title: '창고', key: 'warehouseCode', width: 120 },
  { title: '출고담당자', key: 'requestPerson', width: 150 },
  { title: '품목명', key: 'itemName', width: 120 },
  { title: '규격', key: 'standard', width: 120 },
  { title: '납기일', key: 'dueDate', width: 120 },
  { title: '입고예정일', key: 'expectedArrivalDate', width: 150 },
  // { title: '단위', key: 'unitOfStock', width: 120 },
  { title: '출고수량', key: 'requestQuantity', width: 120 },
  { title: '검사', key: 'inspection', width: 120 },
]

const searchCondition = 'searchByDate'
const customerCode = ''
const startDate = '2021-01-01'
const endDate = '2024-12-31'

const isContractVisible = ref(true)
const isOutputVisible = ref(false)
const isOutputDetailVisible = ref(false)
const isContractDetailVisible = ref(false)

// 수주 조회
const contract = async () => {
  await outputStore().GET_CONTRACT_INFO(
    searchCondition,
    customerCode,
    startDate,
    endDate)
  contractData.value = outputStore().getContractInfo

  isContractVisible.value = true
  isOutputVisible.value = false
  isOutputDetailVisible.value = false
  title.value = '수주현황'
}

// 출고 조회
const search = async () => {
  await outputStore().GET_OUTPUT_INFO()
  outputData.value = outputStore().getOutputInfo

  isOutputVisible.value = true
  isContractVisible.value = false
  isContractDetailVisible.value = false
  title.value = '출고등록현황'
}

// 한 행 클릭시 수주상세정보 나오게하기
const selectContract = async (code: any, row: { item: { contractNo: any } }) => {
  selectedcontractNo.value = row.item

  const contractNo = row.item.contractNo

  await outputStore().GET_CONTRACT_DETAIL_INFO(contractNo)
  contractDetailData.value = outputStore().getContractDetailInfo

  isContractDetailVisible.value = true
}

// 한 행 클릭시 출고상세정보 나오게하기
const list = async (code: any, row: { item: { outputNumber: any } }) => {
  selectedOutputNumber.value = row.item

  const outputNumber = row.item.outputNumber

  await outputStore().GET_OUTPUT_DETAIL_INFO(outputNumber)
  outputDetailData.value = outputStore().getOutputDetailInfo

  isOutputDetailVisible.value = true
}

// 한 item 클릭 시 itemCode 추출 : 수주현황
watch(selectedItem, async (selectedValue: any) => {
  if (toRaw(selectedValue.length) === 0) {
    selecteditem.value = []
  }
  else {
    selecteditem.value = toRaw(selectedValue[0])

    const contractNo = selecteditem.value.contractNo

    await outputStore().GET_CONTRACT_DETAIL_INFO(contractNo)
    contractDetailData.value = outputStore().getContractDetailInfo
  }
})

// 한 item 클릭 시 itemCode 추출 : 출고등록현황
watch(selectedItem2, (selectedValue2: any) => {
  if (toRaw(selectedValue2.length) === 0)
    selecteditem2.value = []
  else
    selecteditem2.value = toRaw(selectedValue2[0])
})

// 출고 삭제
const deleteOutput = async () => {
  if (!selecteditem2.value.outputNumber) {
    console.error('No workplace selected for deletion')

    return
  }
  const confirmed = window.confirm('정말로 삭제하시겠습니까?')

  if (confirmed) {
    const outputNumber = selecteditem2.value.outputNumber

    try {
      await outputStore().DELETE_OUTPUT_INFO(outputNumber)
      search()
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
  <div>
    <VCol
      sm="12"
      cols="12"
    >
      <VCard>
        <div>
          <VCardItem>
            <div class="d-flex justify-space-between align-center">
              <p class="text-2xl">
                {{ title }}
              </p>
              <div class="d-flex align-center">
                <VBtn
                  class="mr-3"
                  @click="contract"
                >
                  수주현황
                </VBtn>
                <!--
                  <VBtn
                  class="mr-3"
                  @click="add"
                  >
                  출고의뢰등록
                  </VBtn>
                -->
                <RegistModal
                  :selecteditem="selecteditem"
                  :contract-detail-data="contractDetailData"
                  @search="search"
                />
                <VBtn
                  class="mr-3"
                  @click="search"
                >
                  출고현황
                </VBtn>
                <VBtn
                  class="mr-3"
                  @click="deleteOutput"
                >
                  출고등록삭제
                </VBtn>
              </div>
            </div>
          </VCardItem>
        </div>

        <div>
          <!-- 수주 현황 -->
          <VCardText
            v-if="isContractVisible"
            class="d-flex"
          >
            <VDataTable
              v-model="selectedItem"
              :headers="Contractheaders"
              :items="contractData"
              :items-per-page="10"
              show-select
              select-strategy="single"
              return-object
              @click:row="selectContract"
            />
          </VCardText>

          <div>
            <!-- 수주상세정보 -->
            <VCardText v-if="isContractDetailVisible">
              <VDataTable
                :headers="ContractDetailheaders"
                :items="contractDetailData"
                :items-per-page="10"
              />
            </VCardText>
          </div>
        </div>
        <div>
          <!-- 출고 현황 -->
          <VCardText
            v-if="isOutputVisible"
            class="d-flex"
          >
            <VDataTable
              v-model="selectedItem2"
              :headers="Registheaders1"
              :items="outputData"
              :items-per-page="10"
              show-select
              select-strategy="single"
              return-object
              @click:row="list"
            />
          </VCardText>

          <!-- <div> -->
          <!-- 출고상세정보 -->
          <!--
            <VCardText v-if="isOutputDetailVisible">
            <VDataTable
            :headers="Registheaders2"
            :items="outputDetailData"
            :items-per-page="10"
            />
            </VCardText>
            </div>
          -->
        </div>
      </VCard>
    </VCol>
  </div>
</template>
