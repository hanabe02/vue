<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import data from '@/views/demos/forms/tables/data-table/datatable'

const selectedRadio = ref('primary')
const colorsRadio = ['Primary', 'Secondary', 'Success', 'Info', 'Warning', 'Error']

const Orderheaders1 = [
  { title: '출고번호', sortable: false, key: 'shipmentNumber' },
  { title: '출고일자', key: 'deliveryDate' },
  { title: '거래처명', key: 'customerName' },
  { title: '규격', key: 'standard' },
  { title: '단위', key: 'unitOfStock' },
  { title: '출고', key: 'release' },
  { title: '표준단가', key: 'standardUnitPrice' },
  { title: '공급가', key: 'supplyPrice' },
  { title: '부가세', key: 'VAT' },
  { title: '합계액', key: 'totalAmount' },
]

const Orderheaders2 = [
  { title: 'No', sortable: false, key: 'id' },
  { title: '품목코드', key: 'fullName' },
  { title: '품명', key: 'startDate' },
  { title: '규격', key: 'email' },
  { title: '주문단위수량', key: 'experience' },
  { title: '규격', key: 'age' },
  { title: '단위', key: 'age' },
  { title: '출고', key: 'age' },
  { title: '단가', key: 'age' },
  { title: '공급가', key: 'age' },
  { title: '부가세', key: 'age' },
  { title: '합계액', key: 'age' },
]
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
                출고처리(국내수주)
              </p>
              <div class="d-flex align-center">
                <VBtn
                  class="mr-3"
                  @click="sendSMS"
                >
                  SMS발송
                </VBtn>
                <VBtn
                  class="mr-3"
                  @click="applyRequest"
                >
                  출고등록
                </VBtn>
                <VBtn
                  class="mr-3"
                  @click="applyInspection"
                >
                  출고현황
                </VBtn>
                <VBtn
                  class="mr-3"
                  @click="printEmailSettings"
                >
                  인쇄/E-MAIL 설정
                </VBtn>
              </div>
            </div>
          </VCardItem>
        </div>

        <div>
          <VCardText>
            <VCardTitle>
              주문출고
            </VCardTitle>
            <VCardText class="d-flex">
              <VDataTable
                v-model="selectedItem1"
                :headers="Orderheaders1"
                :items="orderData1"
                :items-per-page="5"
                show-select
                select-strategy="single"
                return-object
              />
            </VCardText>
          </VCardText>
        </div>

        <div>
          <VCardText>
            <VCardTitle>
              상세정보
            </VCardTitle>
            <VDataTable
              v-model="selectedItem2"
              :headers="Orderheaders2"
              :items="orderData2"
              density="compact"
              :items-per-page="5"
              show-select
              select-strategy="single"
              return-object
            />
          </VCardText>
        </div>
      </VCard>
    </VCol>
  </div>
</template>
