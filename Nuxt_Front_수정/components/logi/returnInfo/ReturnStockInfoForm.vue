<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import { ref } from 'vue';
import { returnStore } from '@/store/logi/return';
import FindItemCodeModal from './modal/FindItemCodeModal.vue'
import AddReturnStockModal from './modal/AddReturnStockModal.vue'

const infodata = ref([]);
const isDialogVisible = ref(false);
const selectData = ref([]);
const textFieldValue = ref([]);
const selectedItem: Ref<any[]> = ref([]);
const selectedRow: Ref<any> = ref();
const AddList = ref({});
const isUpdateDialogVisible = ref(false);
const itemCode = ref([]);

const returnStockInfoHeaders = [
    { title: '반품재고입고번호', key: 'rtrnRecNo' }, 
    { title: '반품 번호', key: 'returnNo' }, 
    { title: '반품 품목코드', key: 'rtrnItemCode' },
    { title: '반품 품목명', key: 'rtrnItemName' },
    { title: '반품 수량', key: 'returnQty' },
    { title: '창고 코드', key: 'recWrhsCode' },
    { title: '창고명', key: 'recWrhsName' },
    { title: '입고일자', key: 'recDate' }
]


//반품재고 조회    
const returnStockListClick = async () => {
  console.log("selectData1:",selectData);
  itemCode.value = selectData.value;
 
  try {
    await returnStore().FETCH_SEARCH_RETURN_STOCKLIST(itemCode.value);    
    infodata.value = returnStore().searchReturnStockList;    

  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

const selectRow=({item,row}:any)=>{
  console.log("row:",row);
  selectData.value=row.item.itemCode;
}

const itemSelect=()=>{
  console.log("selectData2:",selectData);
  textFieldValue.value=selectData.value;
  isDialogVisible.value=false;
}

const dialogCancel=()=>{
  isDialogVisible.value=false;
}

//반품재고입고 요청
const returnReqClick = (AddListFromChild: any) => {
  AddList.value = AddListFromChild;
  isUpdateDialogVisible.value = true;
};


// //요청 등록
const updateReturnInfo = async (updateList: Object) => {
  console.log("등록Data:", updateList);

    await returnStore().FETCH_ADD_RETURN_STOCK(updateList);

    cancel();
 };

  //모달창 닫기
const cancel = async () => {
  isUpdateDialogVisible.value = false;
}

// 한 item 클릭 시 rtrnRecNo 추출
watch(selectedItem, (selectedValue: any) => {
//   console.log(selectedValue)
//   console.log(toRaw(selectedValue))
//   console.log(toRaw(selectedValue).length) // 1
//   console.log(toRaw(selectedValue[0]))
//   console.log(toRaw(selectedValue[0]))

  console.log(toRaw(selectedValue[0].rtrnRecNo))
  if (toRaw(selectedValue.length) === 0)
    selectedRow.value = ''
  else
  selectedRow.value = toRaw(selectedValue[0])
  console.log(selectedRow.value)
})

 //요청 취소 
 const deleteReturnStock = async () => {
  console.log('나와?', selectedRow.value.rtrnRecNo);

  // 값이 숫자가 아니거나 변환할 수 없는 경우
  if (!selectedRow.value.rtrnRecNo) {
    console.error('유효하지 않은 값입니다.');

    return;
  }

  const rtrnRecNo = selectedRow.value.rtrnRecNo;

  await returnStore().FETCH_DELETE_RETURNSTOCK(rtrnRecNo);

  returnStockListClick();
}

</script>

<template>
  <p class="text-2xl">
      반품재고입고 요청관리
  </p>
  
  <div style="margin-bottom: 30px;"></div>

    <VCard>
      <VCardText>
        
          <div class="app-user-search-filter d-flex align-center flex-wrap gap-3">
          <VCol col="10" md="2" >
            <AppTextField 
                class="small-input3" 
                placeholder="품목코드를 검색하세요." 
                v-model="textFieldValue"
                :value="textFieldValue"
                @input="textFieldValue=$event.target.value"
                />
          </VCol>

            <!-- <VBtn class="button small-button" color="primary">검색</VBtn> -->
            <FindItemCodeModal
                class="find-button"
                v-model="isDialogVisible"
                @selectRow="selectRow"
                @itemSelect="itemSelect"
                @dialogCancel="dialogCancel"
            />
        
            <VBtn 
                class="returnStock-search" 
                color="primary" 
                @click="returnStockListClick"
                >반품재고목록 조회
            </VBtn>
         
            <AddReturnStockModal
            class="return-button2"
            @add="returnReqClick"
            />
              <!-- <VBtn 
                  class="return-button2" 
                  color="primary"
                  >반품재고입고 등록
              </VBtn> -->
            
            <UpdateReturnStockModal
              class="return-button3"
              v-model="isUpdateDialogVisible"
              :AddList = "AddList"
              @cancel="cancel"
              @update="updateReturnInfo"/>
              <!-- <VBtn 
                  class="return-button3" 
                  color="primary"
                  >수정
              </VBtn> -->
        
              <VBtn 
                  class="return-button3" 
                  color="primary"
                  @click="deleteReturnStock"
                  >삭제
              </VBtn>
            </div>

      <div style="margin-bottom:30px;"></div>

      <VDataTable
        v-model="selectedItem"
        :headers="returnStockInfoHeaders" 
        :items="infodata" 
        :items-per-page="5"
        show-select
        select-strategy="single"
        return-object
      />

    </VCardText>
  </VCard>
</template>
  
<style scoped>  
.small-input3 {
  width: 200px;
  margin-left:-5px;
}

.returnStock-search{
  margin-right:450px;
  margin-left:5px;
}

.return-button2 {
  margin-right: 0px;
  margin-left: 30px;
}
.return-button3 {
  margin-left:1px;
  margin-top:3px;
}
</style>