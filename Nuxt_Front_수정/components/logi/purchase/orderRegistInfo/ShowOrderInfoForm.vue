<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import { ref } from 'vue';
import { purchaseStore } from '@/store/logi/purchase'

const startDate = ref(null);
const endDate = ref(null);
const infodata = ref([]);

const showOrderInfoHeaders = [
  { title: '발주번호', sortable: false, key: 'orderNo', width: 170 },
  { title: '발주날짜', key: 'orderDate', width: 100 },
  { title: '현재상태', key: 'orderInfoStatus', width: 100 },
  { title: '발주유형', key: 'orderSort', width: 100 },
  { title: '품목코드', key: 'itemCode', width: 100 },
  { title: '품목명', key: 'itemName', width: 150 },
  { title: '발주수량', key: 'orderAmount', width: 100 },
  // { title: "결재승인여부", key: "orderSlipStatus", width: 90 },
  { title: "원재료검사", key: "inspectionStatus", width: 100 },
]


//발주현황조회
const showOrderInfoClick = async () => {
  try {
    // 날짜가 입력되지 않았을 경우
    if (!startDate.value || !endDate.value) {
    alert('시작일과 종료일을 입력해주세요.');
    return;
    }
  
    await purchaseStore().FETCH_SHOW_ORDER_INFO( startDate.value , endDate.value );    
    infodata.value = purchaseStore().ShowOrderInfo;

  } catch (error) {
    console.error('Error fetching data:', error);
  }
};


</script>

<template>
  <VCard>

      <div class="date-picker-container2">
        <VCol>
          <div class="date-picker-wrapper3">
            <AppDateTimePicker 
                v-model="startDate" 
                label="시작일" 
                placeholder="YYYY-MM-DD" 
            />
          </div>
        </VCol>
        
        <VCol>
           <div class="date-picker-wrapper4">
              <AppDateTimePicker 
                 v-model="endDate" 
                  label="종료일" 
                  placeholder="YYYY-MM-DD"
              />
           </div>
        </VCol>
      
        
        <!-- 발주현황조회 -->
        <VBtn 
           class="button5"
           color="primary" 
           @click="showOrderInfoClick"
          >발주현황조회
        </VBtn>
      </div>
      
      <VCardText>
        <VDataTable
            :headers="showOrderInfoHeaders" 
            :items="infodata" 
            :items-per-page="10"
         />
      </VCardText> 
    </VCard>
</template>

<style>

.date-picker-container2 {
  display: flex;
  margin-top:20px;
}

.date-picker-wrapper3 {
  width:150px; 
  margin-right:300px; 
  margin-left:15px;
}

.date-picker-wrapper4 {
  width:150px; 
  margin-right:200px; 
  margin-left:-310px;
}

.button5{
  margin-right:890px; 
  margin-left:-520px; 
  margin-top:35px;
}
</style>