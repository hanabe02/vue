<script setup lang="ts">
import { ref } from 'vue'
import { VDataTable } from 'vuetify/labs/VDataTable'
import { productionStore } from '@/store/logi/production'

const infodata = ref([]);

const workPerformanceHeaders = [
  { title: '생산완료날짜', key: 'workOrderCompletionDate' },
  { title: '작업지시일련번호', key: 'workOrderNo' },
  { title: '수주상세일련번호', key: 'contractDetailNo' },
  { title: '품목구분', key: 'itemClassification' },
  { title: '품목코드', key: 'itemCode' },
  { title: '품목명', key: 'itemName' },
  { title: '단위', key: 'unit' },
  { title: '작업지시수량', key: 'workOrderAmount' },
  { title: '실제제작수량', key: 'actualCompletionAmount' },
  { title: '공정성공율', key: 'workSuccessRate' }
];

//생산실적 조회 
const WorkPerformanceClick = async () => {
  try {
    await productionStore().FETCH_WORK_PERFORMANCE_MANAGEMENT();
    infodata.value = productionStore().WorkPerformanceManagement;

  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

</script>

<template>
  <!-- 👉 생산실적관리 -->
    <AppCardActions>
      <template #title>
        <div style="font-size: 22px;">생산실적관리</div>
      </template>   
          
        <!-- 버튼 -->
          <VCol>
            <div>
              <VBtn  
                color="primary"  
                @click="WorkPerformanceClick"
                > 생산실적조회 
              </VBtn>
            </div>
          </VCol>

          <!-- 테이블 내용 -->
          <VCardText>
            <VDataTable 
                :headers="workPerformanceHeaders" 
                :items="infodata" 
                :items-per-page="5"
            />
          </VCardText>
      </AppCardActions>
</template>
  

  