<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable'
import AddWorkOrderDialog from './modal/AddWorkOrderModal.vue'
import { productionStore } from '@/store/logi/production'

const selectData = ref([]);
const infodata: Ref<any[]> = ref([]);

const workOrderListHeaders = [
  { title: '소요량전개번호', key: 'mrpNo' },
  { title: '소요량취합번호', key: 'mrpGatheringNo' },
  { title: '품목분류', key: 'itemClassification' },
  { title: '품목코드', key: 'itemCode' },
  { title: '품목명', key: 'itemName' },
  { title: '단위', key: 'unitOfMrp' },
  { title: '필요수량', key: 'requiredAmount' },
  { title: '작업지시기한', key: 'orderDate' },
  { title: '작업완료기한', key: 'requiredDate' }
]

//작업지시 필요항목 조회 클릭 
const WorkOrderListDataClick = async () => {
  try {
    await productionStore().FETCH_WORKORDER_MRP_LIST();
    infodata.value = productionStore().WorkOrderMrpList;
  
    } catch (error) {
      console.error('Error fetching data:', error);
    }
};



//모의전개로 보내는 데이터
const selectRow=(item:any, row:any)=>{
  console.log("row",row)
  selectData.value = row.item;
}

</script>

<template>
  <!-- 👉 작업지시 필요 리스트 -->
   <AppCardActions>
      <template #title>
         <div style="font-size: 22px;">작업지시 필요리스트 (MRP 취합 기반)</div>
      </template>

        <!-- 버튼 -->
        <VCol>
          <div class="demo-space-x">
            
            <VBtn 
              class="ml-3"
              color="primary"   
              @click="WorkOrderListDataClick"
              > 
              작업지시 필요항목 조회
            </VBtn>
              
            <!-- 🪄 모의전개 버튼  -->
            <AddWorkOrderDialog 
               :selectData="selectData" />
          </div>
        </VCol>

        <!-- 테이블 내용 -->
        <VCardText>
          <VDataTable
            :headers="workOrderListHeaders"
            :items="infodata"
            :items-per-page="5"
            @click:row="selectRow"
          />
      </VCardText>
  </AppCardActions>
</template>
