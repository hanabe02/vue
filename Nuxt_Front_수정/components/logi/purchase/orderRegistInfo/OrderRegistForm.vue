<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import { ref } from 'vue';
import { purchaseStore } from '@/store/logi/purchase'
import OptionOrderModal from './modal/OptionOrderModal.vue'
import GetOrderDialogModal from './modal/GetOrderDialogModal.vue'

const startDate = ref(null);
const endDate = ref(null);
const infodata = ref([]);
const selectData = ref([]);


// 주석 부분을 추가하여 병합시 충돌이 일어나는지 확인하기 위한 주석 on // 
// 주석 부분을 추가하여 병합시 충돌이 일어나는지 확인하기 위한 주석 off // 


const getOrderListHeaders = [
  { title: '소요량취합번호', sortable: false, key: 'mrpGatheringNo', width: 180 },
  { title: '품목코드', key: 'itemCode', width: 100 },
  { title: '품목명', key: 'itemName', width: 150 },
  { title: '단위', key: 'unitOfMrp', width: 100 },
  { title: '필요수량', key: 'requiredAmount', width: 100 },
  { title: '현재고량', key: 'stockAmount', width: 100 },
  { title: '발주기한', key: 'orderDate', width: 150 },
  { title: '입고기한', key: 'requiredDate', width: 150 },
]


//재고처리 및 발주조회      
const getOrderListClick = async () => {
  try {
    // 날짜가 입력되지 않았을 경우
    if (!startDate.value || !endDate.value) {
    alert('시작일과 종료일을 입력해주세요.');
    return;
    }

    await purchaseStore().FETCH_SEARCH_ORDERLIST( startDate.value , endDate.value );    
    infodata.value = purchaseStore().SearchOrderList;
  
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

</script>

<template>
  <VCard>
      <VCardText>
        <div class="button-container">
            <!-- 모의재고처리 및 취합발주 버튼  -->
            <GetOrderDialogModal 
             :selectData = "selectData"
            />
      
            <!-- 임의발주 버튼 -->
            <OptionOrderModal 
              :selectData = "selectData"
              />

            <div class="date-picker-container">
              <VCol cols="4">
                <div class="date-picker-wrapper1">
                <AppDateTimePicker 
                    v-model="startDate" 
                    class="mb-5" 
                    label="시작일" 
                    placeholder="YYYY-MM-DD" 
                  />
                </div>
              </VCol>
            
              <VCol cols="3">
                <div class="date-picker-wrapper2">
                    <AppDateTimePicker 
                      v-model="endDate" 
                      class="mb-5"  
                      label="종료일" 
                      placeholder="YYYY-MM-DD"
                    />
                </div>
              </VCol>
         
        
              <!-- 재고처리/발주필요 목록조회 -->
              <VBtn 
                  class="button2"
                  color="primary" 
                  @click="getOrderListClick"
                  >재고/발주목록 조회 
              </VBtn>
          </div>
        </div>
    
        
          <VDataTable
              v-model="selectData"
              :headers="getOrderListHeaders" 
              :items="infodata" 
              :items-per-page="10"
              item-value="mrpGatheringNo"
              return-object
              show-select 
          />
      </VCardText>
  </VCard>
</template>

<style>
/* 🔹 날짜 선택 및 조회 버튼 컨테이너 */
.date-picker-container {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 15px;
  margin-bottom: 20px;
  width: 100%;
}

/* 🔹 날짜 입력 필드 크기 조정 */
.date-picker-wrapper1, 
.date-picker-wrapper2 {
  width: 250px !important; /* 크기 강제 조정 */
  min-width: 200px;
  display: flex;
  justify-content: center;
}

/* 🔹 Vuetify Date Picker 내부 스타일 수정 (날짜 필드가 안 보이는 문제 해결) */
.date-picker-wrapper1 .v-input, 
.date-picker-wrapper2 .v-input {
  width: 250px !important;
  min-width: 200px !important;
  display: block !important;
}

.date-picker-wrapper1 .v-field__input, 
.date-picker-wrapper2 .v-field__input {
  width: 100% !important;
  min-width: 180px;
  font-size: 16px;
  padding: 8px 12px;
}

/* 🔹 "재고/발주목록 조회" 버튼 위치 조정 */
.date-picker-wrapper3 {
  width: 180px;
  display: flex;
  justify-content: center;
  margin-left: 20px;
}

/* 🔹 버튼 스타일 */
.button2 {
  min-width: 150px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  white-space: nowrap;
}

</style>