<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import FindDetailCodeListModal from './modal/FindDetailCodeListModal.vue'
import { purchaseStore } from '@/store/logi/purchase'
import { ref } from 'vue'

const radioGroup = ref("forward");
const radioGroup1 = ref("IT-CI");
const isDialogVisible = ref(false);
const textFieldValue = ref('');
const infodata = ref([]);
const rowData = ref('');
const itemCode = ref('');


const clientheaders = [
  { title: 'BOM번호', sortable: false, key: 'bomNo', width: 120 },
  { title: 'BOM레벨', key: 'bomLevel', width: 80 },
  { title: '상위품목코드', key: 'parentItemCode', width: 150 },
  { title: '품목코드', key: 'itemCode', width: 150 },
  { title: '품목명', key: 'itemName', width: 180 },
  { title: '단위', key: 'unitOfStock', width: 80 },
  { title: '정미수량', key: 'netAmount', width: 80 },
  { title: '손실율', key: 'lossRate', width: 80 },
  { title: '필요수량', key: 'necessaryAmount', width: 80 },
  { title: '리드타임', key: 'leadTime', width: 80 },
  { title: 'ISLEAF', key: 'isLeaf', width: 100 },
  { title: '비고', key: 'description', width: 100 },
]

//BOM 조회 버튼 데이터
const searchBom = async() => {
  console.log("radioGroup은?:",radioGroup);
  const deployCondition=radioGroup.value;
  const itemClassificationCondition=radioGroup1.value;

  console.log("deployCondition은?:", deployCondition);
  console.log("itemClassificationCondition은?:", itemClassificationCondition);
  
  await purchaseStore().FETCH_SEARCH_BOM_DEPLOY(deployCondition, itemClassificationCondition, textFieldValue.value);
  infodata.value = purchaseStore().SearchBomDeploy;
  }


  const selectRow=({item,row}:any)=>{
  console.log("row1",row.item.detailCode);
  console.log("item",item);
  rowData.value=row.item.detailCode;
}

  const comInfoSave=()=>{
    console.log("rowData",rowData);
    textFieldValue.value=rowData.value;
    isDialogVisible.value=false;
  }

  const dialogClose=()=>{
    isDialogVisible.value=false;
  }

</script>

<template>
  <VCard>

    <VCol> 
      <p class="text-2xl">
        [품목분류]
      </p>
      <div class="">
        <VRadioGroup v-model="radioGroup1" inline>
          <VRadio label="완제품" value="IT-CI" />
          <VRadio label="반제품" value="IT-SI" />
          <VRadio label="원재료" value="IT-MA" />
        </VRadioGroup>
      </div>
    </VCol>

    <div style="margin-bottom: 15px;"></div>

    <VCol>
      <p class="text-2xl">
        [BOM검색조건]
      </p>
    
      <div class="">
          <VRadioGroup v-model="radioGroup" inline>
            <VRadio label="정전개" value="forward" />
            <VRadio label="역전개" value="reverse" />
          </VRadioGroup>
        </div>
    
      <div style="margin-bottom: 20px;"></div>

      <VRow>
        <VCol col="12" md="2">
            <AppTextField
                placeholder="품목코드를 검색하세요."
                v-model="textFieldValue"
                :value="textFieldValue"
                @input="textFieldValue=$event.target.value"
              />
        </VCol>
        
        <!-- <VBtn class="button small-button" color="primary">검색</VBtn> -->
        <VCol col="12" md="1" >
          <FindDetailCodeListModal
              v-model = "isDialogVisible"
              @selectRow = "selectRow"
              @comInfoSave = "comInfoSave"
              @dialogClose = "dialogClose"
              :radioGroup1 = "radioGroup1"
              />
        </VCol>
     
        <VCol col="12" md="2" >
            <VBtn
              class="button"
              color="primary"
              @click="searchBom"
              style="margin-left: -35px;"
              >
               BOM조회
             </VBtn>
         </VCol>  
      </VRow>
    </VCol>
      
    <VCardText>
      <VDataTable
        :headers="clientheaders"
        :items="infodata"
        :items-per-page="5"
      />
    </VCardText>
  </VCard>
</template>

<style scoped>
.button {
  margin-right: 20px;
  font-size: 14;
}
</style>