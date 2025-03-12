<script lang="ts" setup>
import { VDataTable } from "vuetify/labs/VDataTable";
import { ref, onMounted, watch, toRaw } from 'vue';
import { purchaseStore } from '@/store/logi/purchase';

const isDialogVisible = ref(false);
const infodata = ref([]);
const number = ref('');
const selectData = ref([]);
const orderNoList = ref('');

const orderInfoListOnDeliveryHeaders = [
  { title: '발주번호', sortable: false, key: 'orderNo', width: 170 },
  { title: '발주날짜', key: 'orderDate', width: 100 },
  { title: '상태', key: 'orderInfoStatus', width: 100 },
  { title: '발주구분', key: 'orderSort', width: 100 },
  { title: '품목코드', key: 'itemCode', width: 100 },
  { title: '품목명', key: 'itemName', width: 150 },
  { title: '수량', key: 'orderAmount', width: 100 },
  { title: "원재료검사", key: "inspectionStatus", width: 80 },
]

//오늘날짜
onMounted (async () => {
    number.value = getCurrentDate();
  }
);

const getCurrentDate = () => {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
    const day = currentDate.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
};

// 오늘날짜로 입고  
const stockClick = async () => {
  try {
    await purchaseStore().FETCH_SEARCH_ORDER_LIST_ONDLVRY();
    infodata.value = purchaseStore().OrderListOnDlvry;

  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

//선택한 행에서 orderNo 추출하기
watch(() => selectData.value, (newSelectData) => {
  const orderNoArr = toRaw(newSelectData).map((item: { orderNo: string }) => item.orderNo);
  orderNoList.value = orderNoArr.join(',');
  console.log("orderNoList:", orderNoList.value);
});

//현재 체크된 발주품목 원재료 검사   
const inspectionClick = async () => {
  console.log("orderNo:", orderNoList.value);
  
  try {
    await purchaseStore().FETCH_ORDERLIST_INSPECTION(orderNoList.value);
    infodata.value = purchaseStore().OrderListInspection;
    
    await stockClick();
    
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};


//현재 체크된 발주품목 입고    
const orderListClick = async () => {
  try {
    await purchaseStore().FETCH_ORDERLIST_WAREHOUSING(orderNoList.value);
    infodata.value = purchaseStore().OrderListWarehousing;

    alert("입고가 성공적으로 완료되었습니다!");
    await stockClick();
    isDialogVisible.value = false; 
    
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

</script>

<template>
    <div class="button-container">
        <VCol cols="5">           
            <VTextField 
                v-model="number" 
                label="오늘날짜" 
                readonly
                style="width:120px;"
            />
        </VCol>

        <VDialog v-model="isDialogVisible" width="1300">
            <!-- Activator -->
            <template #activator="{ props }">
                <VBtn 
                    class="button" 
                    color="primary" 
                    @click="stockClick" 
                    v-bind="props"
                    >입고
                </VBtn>
            </template>

            <!-- Dialog Content -->
            <VCard>
                <VCardTitle>발주품목 입고</VCardTitle>
                    <VRow>
                        <VCol class="d-flex gap-4" >
                            <VBtn 
                                @click="inspectionClick"
                                class="mr-2 ml-5 mt-5"
                                variant="tonal"
                                >
                                현재 체크된 발주품목 원재료 검사 
                            </VBtn>

                            <VBtn 
                                @click="orderListClick"
                                class="mt-5"
                                >
                                현재 체크된 발주품목 입고
                            </VBtn>
                        </VCol>  
                    </VRow>
                    
                    <VCardText>
                        <VDataTable
                        v-model="selectData"
                        :headers="orderInfoListOnDeliveryHeaders" 
                        :items="infodata" 
                        :items-per-page="10"
                        item-value="orderNo"
                        return-object
                        show-select
                     />
                </VCardText>
            </VCard>
        </VDialog>
    </div>
</template>

<style scoped>
.button{
    margin-left:-410px;
    margin-right:1500px;
}
</style>
