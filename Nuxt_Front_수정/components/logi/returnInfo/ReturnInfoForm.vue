<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import { ref, toRaw, watch } from 'vue';
import UpdateReturnInfoModal from './modal/UpdateReturnInfoModal.vue'
import AddReturnModal from './modal/AddReturnModal.vue'
import { returnStore } from '@/store/logi/return';

const startDate = ref(null);
const endDate = ref(null);
const infodata = ref([]);
const selectedItem: Ref<any[]> = ref([]);
const selectedRow: Ref<any> = ref();
const AddList = ref({});
const isUpdateDialogVisible = ref(false);

const returnInfoHeaders = [
    { title: '반품번호', key: 'returnNo', width:100 }, 
    { title: '배송번호', key: 'deliveryNumber', width:120 }, 
    { title: '품목명', key: 'itemName', width:130 },
    { title: '품목코드', key: 'itemCode', width:100 },
    { title: '수령날짜', key: 'rcvdDate', width:100 },
    { title: '반품사유', key: 'returnReason', width:140 }, 
    { title: '반품수량', key: 'returnQty', width:70 },
    { title: '반품처리자', key: 'returnProcessor', width:110 },
    { title: '반품처리상태', key: 'returnStat', width:100 },
    { title: '비고', key: 'returnNote', width:100 },
]

//반품요청 조회 
 const returnListClick = async () => {
    try {
    //날짜가 입력되지 않았을 경우
    if (!startDate.value || !endDate.value) {
    alert('시작일과 종료일을 입력해주세요.');
    return;
    }

    await returnStore().FETCH_GET_RETURNLIST( startDate.value , endDate.value );    
    infodata.value = returnStore().getReturnList;  
   
   } catch (error) {
     console.error('Error fetching data:', error);
   }
 };


//반품 요청
const returnReqClick = (AddListFromChild: any) => {
  AddList.value = AddListFromChild;
  isUpdateDialogVisible.value = true;
};


//요청 등록
const updateReturnInfo = async (updateList: Object) => {
  console.log("등록Data:", updateList);

    await returnStore().FETCH_ADD_RETURN(updateList);

    cancel();
 };


 //요청 취소 
 const deleteReturnInfoClick = async () => {
  console.log('나와?', selectedRow.value.returnNo);

  // 값이 숫자가 아니거나 변환할 수 없는 경우
  if (!selectedRow.value.returnNo) {
    console.error('유효하지 않은 값입니다.');

    return;
  }

  const returnNo = selectedRow.value.returnNo;

  await returnStore().FETCH_DELETE_RETURN(returnNo);

  returnListClick();
}




// 한 item 클릭 시 returnNo 추출
watch(selectedItem, (selectedValue: any) => {
//   console.log(selectedValue)
//   console.log(toRaw(selectedValue))
//   console.log(toRaw(selectedValue).length) // 1
//   console.log(toRaw(selectedValue[0]))
//   console.log(toRaw(selectedValue[0]))

  console.log(toRaw(selectedValue[0].returnNo))
  if (toRaw(selectedValue.length) === 0)
    selectedRow.value = ''
  else
  selectedRow.value = toRaw(selectedValue[0])
  console.log(selectedRow.value)
})

 //모달창 닫기
 const cancel = async () => {
  isUpdateDialogVisible.value = false;
}



</script>

<template>
  <div class="text-2xl mb-2">
    <p>반품요청 관리</p>

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
         
          <!-- 반품요청 조회 -->
          <VBtn 
            class="return-search"
            color="primary" 
             @click="returnListClick"
             >반품요청 조회 
           </VBtn>

            <div class="app-user-search-filter flex-wrap gap-3">
              
            <!-- <VBtn 
                class="return-button" 
                color="primary"
                @click="addReturnInfoClick"
                >요청 등록
            </VBtn> -->
            <AddReturnModal
              @add="returnReqClick"
              />

            <UpdateReturnInfoModal
              class="return-button"
              v-model="isUpdateDialogVisible"
              :AddList = "AddList"
              @cancel="cancel"
              @update="updateReturnInfo"
            />
              <!-- <VBtn 
                class="return-button"
                color="primary"
                >반품요청 수정
            </VBtn> -->
                
            <VBtn 
                class="return-button" 
                color="primary"
                @click="deleteReturnInfoClick"
                >요청 취소
            </VBtn>
         </div>  
      </div>

        <VDataTable
           v-model="selectedItem"
           :headers="returnInfoHeaders"
           :items="infodata"
           :items-per-page="5"
           show-select
           select-strategy="single"
           return-object
        />
        </VCardText>
    </VCard>
    </div>
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

.return-search{
  margin-top:35px;
  margin-right:450px; 
  margin-left:-330px; 
}

.small-input {
  width: 200px;
}
  
.return-button {
  font-size: 15px;
  margin-top: 35px;
  margin-right: 20px; 
}
</style>