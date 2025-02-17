<script setup lang="ts">
import { ref } from 'vue';
import { VDataTable } from 'vuetify/labs/VDataTable';
import { productionStore } from '@/store/logi/production'
import UpdateActualCmplAmtButton from './modal/UpdateActualCmplAmtButton.vue'; 

const infodata = ref([]);
const isUpdateDialogVisible = ref(false);

interface SelectData {
  workOrderNo: any;
  actualCompletionAmount: any;
}
    
// selectData를 정의한 타입으로 변경합니다.
const selectData: globalThis.Ref<SelectData> = ref({
  workOrderNo: null, 
  actualCompletionAmount: null, 
});

const workOrderStatusListHeaders = [
  { title: '작업지시일련번호', key: 'workOrderNo' },
  { title: '소요량취합번호', key: 'mrpGatheringNo' },
  { title: '품목분류', key: 'itemClassification' },
  { title: '품목코드', key: 'itemCode' },
  { title: '품목명', key: 'itemName' },
  { title: '단위', key: 'unitOfMrp' },
  { title: '지시수량', key: 'requiredAmount' },
  { title: '작업장코드', key: 'workSiteCode' },
  { title: '작업장명', key: 'workSiteName' },
  { title: '생산공정코드', key: 'productionProcessCode' },
  { title: '생산공정명', key: 'productionProcessName' },
  { title: '원재료검사', key: 'inspectionStatus' },
  { title: '제품상태', key: 'productionStatus' },
  { title: '완료상태', key: 'completionStatus' },
  { title: '작업완료된수량', key: 'actualCompletionAmount' }
];

const searchWorkOrderStatusClick = async () => {
  try {
    await productionStore().FETCH_WORKORDER_LIST_STATUS();
    infodata.value = productionStore().WorkOrderListStatus;
  
  } catch (error) {
  
    console.error('Error fetching data:', error);
  
  }
};


//작업완료등록
const cancel = async () => {
  isUpdateDialogVisible.value = false
}

const update = async () => {
  searchWorkOrderStatusClick();
}

const selectRow = (item: any, row: any) => {
  selectData.value = toRaw(row.item);
  console.log("selectData.value:", selectData.value);
};


</script>

<template>
  <!-- 👉 작업지시 현황 리스트 -->
  <AppCardActions>
    <template #title>
      <div style="font-size: 22px;">작업지시현황</div>
    </template>

    <!-- 버튼 -->
    <VCol>
      <div class="demo-space-x">
        <VBtn 
            class="ml-3"
            color="primary" 
            @click="searchWorkOrderStatusClick"
            >
            작업지시 현황조회
        </VBtn>

        <!--작업완료등록 버튼-->
        <UpdateActualCmplAmtButton 
            v-model="isUpdateDialogVisible"
            :selectData="selectData"
            @update="update"
            @cancel="cancel"
          />

        <div margin-bottom="10px" />
      </div>
    </VCol>

    <!-- 테이블 내용 -->
    <VCardText>
      <VDataTable
          :headers="workOrderStatusListHeaders"
          :items="infodata"
          :items-per-page="5"
          @click:row="selectRow"
        >

      </VDataTable>
     </VCardText>
    </AppCardActions>

</template>

