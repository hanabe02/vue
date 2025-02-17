<script lang="ts" setup>
import FindDetailCodeListModal from './FindDetailCodeListModal.vue'
import { purchaseStore } from '@/store/logi/purchase'
import { ref, toRaw } from 'vue'

const radioGroup1 = ref("IT-CI");
const isDialogVisible = ref(false);
const rowData1 = ref('');
const rowData2 = ref('');
const itemCode = ref('');
const itemName = ref('');
const netAmount = ref('');
const description = ref('');
const no = ref('');
const parentItemCode = ref('');
const isAddDialogVisible = ref(false);
const resultData = ref([]);
const itemList = ref();
const noList = ref([]);

const propz = defineProps([]);
const emits = defineEmits(['save', 'close'])


//새로운 항목 추가
const addNewItem = async() => {
  // 새로운 항목 추가 버튼을 누를 때 상태 초기화
  radioGroup1.value = "IT-CI";
  rowData1.value = '';
  rowData2.value = '';
  itemCode.value = '';
  itemName.value = '';
  netAmount.value = '';
  description.value = '';
  parentItemCode.value = '';
  isAddDialogVisible.value = true;
}

//검색모달창 
const selectRow=({item,row}:any)=>{
  rowData1.value=row.item.detailCode;
  rowData2.value=row.item.detailCodeName;
  console.log("detailCode:",row.item.detailCode);
  console.log("detailCodeName:",row.item.detailCodeName);

  itemCode.value = row.item.detailCode;
  itemName.value = row.item.detailCodeName;
}

const comInfoSave=async()=>{
    parentItemCode.value=rowData1.value;
    console.log("검색 parentItemCode:",parentItemCode.value);
    
    itemName.value=rowData2.value;
    console.log("itemName.value",itemName.value);
    isDialogVisible.value=false;

    //조회 데이터 가져오기
    await purchaseStore().FETCH_SEARCH_BOM_INFO(parentItemCode.value);
    resultData.value = purchaseStore().SearchBomInfo;

    console.log("resultData:", resultData.value); //조회 결과 데이터 

    // itemCodeList 추출
    const codeList = toRaw(resultData.value).map((item: { itemCode: string, no: number }) => ({
      itemCode: item.itemCode,
      no: item.no,
    }));
    console.log("codeList:", codeList);

    const itemCodeList = codeList.map(item => item.itemCode).join(',');
    console.log("itemCodeList:", itemCodeList);

    // itemList 초기화
    itemList.value = [];

    const uniqueItemCodes = Array.from(new Set(itemCodeList.split(',')));
    itemList.value = uniqueItemCodes.map(itemCode => ({
      value: itemCode,
      no: codeList.find(item => item.itemCode === itemCode)?.no // itemCode에 대응하는 no를 연결
    }));
    console.log("uniqueItemCodes", uniqueItemCodes);
    console.log("itemList.value", itemList.value);

    noList.value = itemList.value;
    console.log("noList.value", noList.value);

    // itemList에 문자열 배열 할당
    itemList.value = uniqueItemCodes;
    
    itemCode.value = '';
};


const dialogClose=()=>{
    isDialogVisible.value=false;
  }

//새로운항목추가 OK 버튼 
const okButton = () => {
    try {
      const selectedNo = noList.value.find((item: { value: string, no?: number }) => item.value === itemCode.value.toString())!.no;
      console.log("selectedNo:", selectedNo);
      console.log("itemList.value22222:", itemList.value);

      const batchBomList = {
          no: selectedNo,
          netAmount: parseFloat(netAmount.value),
          itemCode: itemCode.value,
          description: description.value,
          parentItemCode: parentItemCode.value,
          status: "INSERT",
        };
        console.log("save.batchBomList:", batchBomList);
        emits('save', batchBomList);
    } catch (error) {
        console.error('Error in save function:', error);
    }
};

  const close = () => {
  emits('close')
}

</script>

<template>
  <VDialog
    v-model="isAddDialogVisible"
    max-width="500"
  >
    <!-- Dialog Activator -->
    <template #activator="{ props }">
      <VBtn 
        v-bind="props"
        @click="addNewItem">
        새로운 항목 추가
      </VBtn>
    </template>

    <VCard>
      <VCardText>
        <p class="text-2xl" style="font-size: 20px;">
          [새로운 항목 추가]
        </p>
        
        <VCol>
            <VRow>
              <VCol col="15"> 
              <p class="text-2xl" style="font-size: 15px;">
                품목구분
              </p>
              <div style="margin-top: -10px;">
                <VRadioGroup v-model="radioGroup1" inline>
                  <VRadio label="완제품" value="IT-CI" />
                  <VRadio label="반제품" value="IT-SI" />
                </VRadioGroup>
              </div>
            </VCol>
          </VRow>
        </VCol>

        <div style="margin-top: 15px;"/>

          <VRow>
            <VCol cols="7">
              <VTextField
                v-model="parentItemCode"
                label="상위품목코드"
                placeholder="상위품목코드를 검색하세요"
                style="width:240px; margin-left:10px;"
              />
            </VCol>

            <VCol col="6">
              <FindDetailCodeListModal
                  v-model = "isDialogVisible"
                  @selectRow = "selectRow"
                  @comInfoSave = "comInfoSave"
                  @dialogClose = "dialogClose"
                  :radioGroup1 = "radioGroup1"
                  />
            </VCol>
          </VRow>
          
          <div style="margin-top: 10px;"/>

            <VCol cols="7">
              <AppSelect
              v-model="itemCode" 
              :items="itemList"
              label="품목코드"
              item-text="value"  
              item-value="no" 
            />
            </VCol>

            <VCol cols="12">
              <AppTextField
                placeholder="수량을 입력하세요"
                v-model="netAmount"
                  label="정미수량"
              />
            </VCol>

            <VCol cols="12">
              <AppTextField
                v-model="description"
                label="설명"
                placeholder="설명을 입력하세요"
              />
            </VCol>
      </VCardText>

        <VCardText class="d-flex justify-end gap-4">
              <VBtn @click="close">
                Cancel
              </VBtn>
              
              <VBtn @click="okButton">
                OK
              </VBtn>
        </VCardText>
    </VCard>
  </VDialog>
</template>

<style lang="scss">
.scrollable-dialog{
  overflow: visible !important;
}
</style>