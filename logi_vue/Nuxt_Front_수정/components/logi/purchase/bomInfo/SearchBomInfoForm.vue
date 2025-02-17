<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import { purchaseStore } from '@/store/logi/purchase'
import { ref } from 'vue'
import BomAddModal from './modal/BomAddModal.vue'
import FindDetailCodeListModal from './modal/FindDetailCodeListModal.vue'


const radioGroup1 = ref("IT-CI");
const isDialogVisible = ref(false);
const selectData2 = ref([]);
const textFieldValue = ref([]);
const infodata = ref([]);
const isAddDialogVisible = ref(false);
const selectData = ref([]);

const searchBomInfoheaders = [
  { title: 'NO', sortable: false, key: 'no', width: 60 },
  { title: '상위품목코드', key: 'parentItemCode', width: 150 },
  { title: '품목코드', key: 'itemCode', width: 150 },
  { title: '품목분류', key: 'itemClassificationName', width: 150 },
  { title: '품목명', key: 'itemName', width: 150 },
  { title: '정미수량', key: 'netAmount', width: 150 },
  { title: 'STATUS', key: 'status', width: 150 },
  { title: '설명', key: 'description', width: 60 },
]

//Bom 조회
const selectRow=({item,row}:any)=>{
  console.log("row2",row);
  selectData2.value=row.item.detailCode;
}

const comInfoSave=()=>{
  console.log("selectData2",selectData2)
  textFieldValue.value=selectData2.value;
  isDialogVisible.value=false;
}

const dialogClose=()=>{
  isDialogVisible.value=false;
}

const searchBomRegist=async()=>{
  console.log("radioGroup1", radioGroup1.value);
  const deployCondition = radioGroup1.value;
  console.log("deplayCondition:", deployCondition);

  await purchaseStore().FETCH_SEARCH_BOM_INFO(textFieldValue.value);
  infodata.value = purchaseStore().SearchBomInfo;
  console.log("뭐가 나와?", infodata.value);
}

//새로운 항목 추가
 const close = () => {
  isAddDialogVisible.value = false;
 }

 const save = async (batchBomList: any) => {
  console.log("부모.batchBomList", batchBomList);
  
  try {
    await purchaseStore().FETCH_BATCH_BOMLIST_PROCESS([batchBomList]);
    infodata.value = purchaseStore().BatchBomListProcess;

  } catch (error) {
    console.error('Error fetching data:', error);
  }
  close();
  alert("새로운 항목이 추가 되었습니다.");

  searchBomRegist();
};

//삭제
const deleteClick = async () => {
  // 선택된 행의 정보를 담을 배열
  const batchBomListArray: { no: number, netAmount: number, parentItemCode: string, itemCode: string, description: string, status: string }[] = [];

  // selectData.value 배열에 여러 행이 선택된 경우를 대비하여 forEach를 사용
  selectData.value.forEach((selectedRow) => {
    const { no, netAmount, parentItemCode, itemCode, description } = selectedRow;
    
    // 추출한 값들을 활용하여 객체 생성 후 배열에 추가
    const batchBomList = {
      no: no,
      netAmount: netAmount,
      itemCode: itemCode,
      description: description,
      parentItemCode: parentItemCode,
      status: "DELETE",
    };

    batchBomListArray.push(batchBomList);
  });

  console.log("Batch Bom List Array:", batchBomListArray);

  try {
    await purchaseStore().FETCH_BOMDATA_BATCH(batchBomListArray);
    infodata.value = purchaseStore().BomdataBatch;
    console.log("infodata.value", infodata.value);
    
    if(infodata.value){
      alert("삭제가 완료되었습니다.");
    }
 
  } catch (error) {
    console.error('Error fetching data:', error);
  }
  searchBomRegist();
};


//저장
const saveClick = async () => {
  // 선택된 행의 정보를 담을 배열
  const batchBomListArray: { no: number, netAmount: number, parentItemCode: string, itemCode: string, description: string, status: string }[] = [];

  // selectData.value 배열에 여러 행이 선택된 경우를 대비하여 forEach를 사용
  selectData.value.forEach((selectedRow) => {
    const { no, netAmount, parentItemCode, itemCode, description } = selectedRow;
    
    const batchBomList = {
      no: no,
      netAmount: netAmount,
      itemCode: itemCode,
      description: description,
      parentItemCode: parentItemCode,
      status: "UPDATE",
    };

    batchBomListArray.push(batchBomList);
  });

  console.log("Batch Bom List Array:", batchBomListArray);

  try {
    await purchaseStore().FETCH_BOMDATA_BATCH(batchBomListArray);
    infodata.value = purchaseStore().BomdataBatch;
    console.log("infodata.value", infodata.value);
    
    if (infodata.value) {
      alert("선택한 항목이 저장 되었습니다.");
    }
 
  } catch (error) {
    console.error('Error fetching data:', error);
  }
  searchBomRegist();
};
</script>

<template>
    <VCard>
      <VCol>
      
        <p class="text-2xl">
          품목분류
        </p>
          
          <div class="">
            <VRadioGroup v-model="radioGroup1" inline>
              <VRadio label="완제품" value="IT-CI" />
              <VRadio label="반제품" value="IT-SI" />
            </VRadioGroup>
          </div>
      
          <div style="margin-bottom: 20px;"></div>
  
          
          <VRow>
            <div class="app-user-search-filter d-flex align-center flex-wrap gap-3">
            <VCol col="12" md="3" >
              <AppTextField 
                  class="small-input" 
                  placeholder="품목코드를 검색하세요." 
                  v-model="textFieldValue"
                  :value="textFieldValue"
                  @input="textFieldValue=$event.target.value"
                  />
            </VCol>
    
              <!-- <VBtn class="button small-button" color="primary">검색</VBtn> -->
              <FindDetailCodeListModal
                  v-model="isDialogVisible"
                  @selectRow="selectRow"
                  @comInfoSave="comInfoSave"
                  @dialogClose="dialogClose"
                  :radioGroup1 = "radioGroup1"
              />
          
              <VBtn 
                  class="button small-button11" 
                  color="primary" 
                  @click="searchBomRegist"
                  > BOM조회
              </VBtn>
    
              <!-- <VBtn 
                  class="button small-button11" 
                  color="primary"
                  >새로운 항목 추가
              </VBtn> -->
              <BomAddModal
              v-model="isAddDialogVisible"
              @close="close"
              @save="save"
              :textFieldValue="textFieldValue"
              />
  
              <VBtn 
                  class="button small-button11" 
                  color="primary"
                  @click="deleteClick"
                  >삭제
              </VBtn>
        
              <VBtn 
                  class="button small-button11" 
                  color="primary"
                  @click="saveClick"
                  >일괄저장
              </VBtn>
            </div>
          </VRow>
      </VCol>
  
      <VCardText>
        <VDataTable 
          v-model="selectData"
          :headers="searchBomInfoheaders" 
          :items="infodata" 
          :items-per-page="5"
          item-value="description"
          return-object
          show-select 
        />
      </VCardText>
    
    </VCard>
  </template>
  
  <style scoped>  
  .small-input {
    width: 200px;
    margin-right: 80px;
  }
  
  .small-button11 {
    margin-right: 10px;
    font-size: 14px;
  }
  </style>