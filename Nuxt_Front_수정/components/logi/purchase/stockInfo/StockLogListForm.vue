<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import { ref } from 'vue';
import { purchaseStore } from '@/store/logi/purchase';

const startDate = ref(null);
const endDate = ref(null);
const infodata = ref([]);

const stockLogListHeaders = [
    { title: '기록 날짜', key: 'logDate' }, 
    { title: '품목코드', key: 'itemCode' },
    { title: '품목명', key: 'itemName' },
    { title: '재고량', key: 'amount' },
    { title: '사유', key: 'reason' }, 
    { title: '원인', key: 'cause' },
    { title: '결과', key: 'effect' },
]


//재고로그 조회   
const stockLogListClick = async () => {
  try {
    // 날짜가 입력되지 않았을 경우
    if (!startDate.value || !endDate.value) {
    alert('시작일과 종료일을 입력해주세요.');
    return;
    }

    await purchaseStore().FETCH_SEARCH_STOCK_LOG_LIST( startDate.value , endDate.value );    
    infodata.value = purchaseStore().SearchStockLogList;    

  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

</script>

<template>
  <VCard>
    <VCardText>
      <div class="date-picker-container7">
          <VCol cols="3">
              <div class="date-picker-wrapper8">
                  <AppDateTimePicker 
                      v-model="startDate" 
                      class="mb-5" 
                      label="시작일" 
                      placeholder="YYYY-MM-DD" 
                    />
               </div>
          </VCol>
            
                  <VCol cols="3">
                    <div class="date-picker-wrapper9">
                      <AppDateTimePicker 
                          v-model="endDate" 
                          class="mb-5"  
                          label="종료일" 
                          placeholder="YYYY-MM-DD"
                       />
                    </div>
                  </VCol>
         
                  <!-- 재고로그 조회 -->
                  <VBtn 
                      class="button10"
                      color="primary" 
                      @click="stockLogListClick"
                      >재고로그 조회 
                  </VBtn>
                </div>

              <VDataTable
                :headers="stockLogListHeaders"
                :items="infodata"
                :items-per-page="5"
              />
        </VCardText>
    </VCard>
</template>

<style>
.date-picker-container7 {
  display: flex;
}

.date-picker-wrapper8 {
  width:150px; 
  margin-right:30px; 
  margin-left:-10px;
}

.date-picker-wrapper9 {
  width:150px; 
  margin-right:300px; 
  margin-left:-180px;
}

.button10{
  margin-top:35px;
  margin-right:890px; 
  margin-left:-330px; 
}
</style>