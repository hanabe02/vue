<script lang="ts" setup>
import { ref } from 'vue';
import { VDataTable } from 'vuetify/labs/VDataTable';
import { productionStore } from '@/store/logi/production'
import { defineProps, defineEmits } from "vue";
import ActualWorkOrderButton from './ActualWorkOrderButton.vue'

const infodata = ref([]);
const isDialogVisible = ref(false);
const showWarningAlert = ref(false);
const workSiteName = ref("");
const workBranch = ref("");

const workOrderSimulationHeaders = [
  { title: '소요량전개번호', key: 'mrpNo' },
  { title: '소요량취합번호', key: 'mrpGatheringNo' },
  { title: '품목분류', key: 'itemClassification' },
  { title: '품목코드', key: 'itemCode' },
  { title: '품목명', key: 'itemName' },
  { title: '단위', key: 'unitOfMrp' },
  { title: '재고량(투입예정재고)', key: 'inputAmount', width:100 },
  { title: '재고소요/제작수량', key: 'requiredAmount', width:100 },
  { title: '재고량(재고소요이후)', key: 'stockAfterWork', width:100 },
  { title: '작업지시기한', key: 'orderDate' },
  { title: '작업완료기한', key: 'requiredDate' }
]


//부모 컴포넌트에서 받아온 데이터 
const propz = defineProps(['selectData']);
const emits = defineEmits([]);

//모의전개 alert, 테이블 데이터
const workOrderDialogClick = async () => {
  if (!propz.selectData || Object.keys(propz.selectData).length === 0) {
    // 행을 선택하지 않았을 때
    showWarningAlert.value = true; // alert 보임 
    isDialogVisible.value = false; // Dialog를 감춤
 
  } else {
    try {
      const { mrpNo, mrpGatheringNo } = propz.selectData;

      await productionStore().FETCH_ADD_WORKORDER_DIALOG(mrpNo, mrpGatheringNo);
      infodata.value = productionStore().AddWorkOrderDialog;

      showWarningAlert.value = false; // alert 감춤  
      isDialogVisible.value = true; // Dialog를 보임 
    
    } catch (error) {
      console.error('데이터 가져오기 에러:', error);
      infodata.value = [];
    }
  }
}


const closeDialog = () => {
  isDialogVisible.value = false;
};


// 작업장구분, 지점구분
// 작업장구분 옵션 (productionProcessCode)
const productionProcessCodeOptions = [
  {title: '라인공정', value: 'PP001'},
  {title: '조립생산공정', value: 'PP002'},
]    

// 지점구분 옵션 (workPlaceCode)
const workPlaceCodeOptions = [
  {title: '(주)세계전자본사', value: 'BRC-01'},
  {title: '(주)세계전자울산지점', value: 'BRC-02'},
]

//실제작업지시 버튼
const workOrderOk = ref({
  productionProcessCode: '',
  workPlaceCode: '',
  mrpGatheringNo: '',
  mrpNo: '',
});


const onWorkSiteNameChange = (value: string) => {
  // 작업장 구분이 변경될 때 해당하는 코드 값을 선택 데이터에 설정
  const selectedOption = productionProcessCodeOptions.find(option => option.title === value);

  if (selectedOption) {
    // 선택된 값을 정확하게 업데이트
    workOrderOk.value.productionProcessCode = selectedOption.value;
  }
}; 

const onWorkBranchChange = (value: string) => {
  // 지점 구분이 변경될 때 해당하는 코드 값을 선택 데이터에 설정
  const selectedOption = workPlaceCodeOptions.find(option => option.title === value);

  if (selectedOption) {
    // 선택된 값을 정확하게 업데이트
    workOrderOk.value.workPlaceCode = selectedOption.value;
  }
};

const selectDialogRow = (item: any, row: any) => {
  workOrderOk.value = {
    mrpNo: row.item.mrpNo,
    mrpGatheringNo: row.item.mrpGatheringNo,
    workPlaceCode: workBranch.value,
    productionProcessCode: workSiteName.value
  };
  console.log("나와라:", row.item.mrpNo);
  console.log("나와라:", row.item.mrpGatheringNo);
  console.log("나와라:", workBranch.value);
  console.log("나와라:", workSiteName.value); 
};

</script>

<template>
  <VDialog v-model="isDialogVisible" width="1400">
    <!-- Activator -->
    <template #activator="{ props }">
      <VBtn 
          v-bind="props" 
          color="primary" 
          @click="workOrderDialogClick"
          > 작업지시 모의전개
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard>
      <VCardTitle>WorkOrder</VCardTitle>

      <VCardText>
        <VRow class="mb-4">
          <VCol cols="12" sm="6" md="2">
            <AppSelect
              v-model="workSiteName"
              :items="productionProcessCodeOptions"
              label="작업장구분"
              @change="onWorkSiteNameChange"
          />
          </VCol>
          <VCol cols="12" sm="6" md="2">
            <AppSelect
              v-model="workBranch"
              :items="workPlaceCodeOptions"
              label="지점구분"
              @change="onWorkBranchChange"
            />
          </VCol>
        </VRow>

      <VDataTable
          :headers="workOrderSimulationHeaders"
          :items="infodata"
          @click:row="selectDialogRow"
        />
      </VCardText>

      <VCardText class="d-flex justify-end">
        <ActualWorkOrderButton 
          :DialogData="workOrderOk" 
          @closeDialog="closeDialog"
        />
      </VCardText>
    </VCard>
  </VDialog>

  <!-- VAlert for warning -->
  <VAlert 
     v-if="showWarningAlert" 
     type="warning" 
     variant="tonal" 
     style="max-width: 400px;"
     >행을 선택해주세요!!
  </VAlert>
</template>
