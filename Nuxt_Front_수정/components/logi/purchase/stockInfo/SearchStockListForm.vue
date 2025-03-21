<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable';
import {ref, onMounted} from 'vue';
import { purchaseStore } from '@/store/logi/purchase';
import OrderInfoListModal from './modal/OrderInfoListModal.vue'

const infodata = ref([]);
const selectData = ref([]);

const stockListHeaders = [
  { title: '창고코드', sortable: false, key: 'warehouseCode' },
  { title: '품목코드', key: 'itemCode' },
  { title: '품목명', key: 'itemName' },
  { title: '단위', key: 'unitOfStock' },
  { title: '안전재고량', key: 'safetyAllowanceAmount' },
  { title: '재고량', key: 'stockAmount' },
  { title: '입고예정재고량', key: 'orderAmount' },
  { title: '투입예정재고량', key: 'inputAmount' },
  { title: '납품예정재고량', key: 'deliveryAmount' },
]

const searchStockList = async () => {
  try {
    await purchaseStore().FETCH_SEARCH_STOCK_LIST();
    infodata.value = purchaseStore().SearchStockList;
 
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
};

const selectRow=(item:any, row:any)=>{
  console.log("row",row)
  selectData.value=row.item;
}

onMounted (async () => {
   await searchStockList();
  }
);


</script>

<template>
  <VCard>
      <VCardText>
      <!-- 오늘날짜 입고 버튼 -->
        <OrderInfoListModal />

        <VDataTable
          :headers="stockListHeaders"
          :items="infodata"
          :pagination="false"
          @click:row="selectRow"
        />
      </VCardText>
    </VCard>
</template>

<style scoped>
/* 버튼 스타일 */
.button {
  color: #ffffff;
  background-color: #1976D2;
  margin-left: -260px; 
  margin-right: 60px;
  /* 추가적인 스타일 속성들 */
}

</style>
