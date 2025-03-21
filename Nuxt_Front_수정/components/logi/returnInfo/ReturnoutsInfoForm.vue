<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import { ref, toRaw, watch} from 'vue';
import UpdateReturnInfoModal from './modal/UpdateReturnInfoModal.vue'
import AddReturnModal from './modal/AddReturnModal.vue'
import { returnStore } from '@/store/logi/return';

const infodata = ref([]);
const selectedItem: Ref<any[]> = ref([]);
const AddList = ref({});
const isUpdateDialogVisible = ref(false);
const isReturnDialogVisible = ref(false);

// const selectedRow = computed(() => {
//   return infodata.value.find(item => item.inspectionCode === selectData.value);
// });
const selectedRow = ref(null);


const returnInfoHeaders = [
    { title: '반품번호', key: 'returnCode', width: 100, align: 'center' },
    { title: '검사번호', key: 'inspectionCode', width: 180 , align: 'center' },
    { title: '품목명', key: 'itemName', width: 100 , align: 'center' },
    { title: '품목코드', key: 'itemCode', width: 100, align: 'center' },
    { title: '반품수량', key: 'returnAmount', width: 100, align: 'center' },
    { title: '반품요청 날짜', key: 'returnOrderDate', width: 160, align: 'center' },
    { title: '반품처리 날짜', key: 'returnDate', width: 160, align: 'center' },
    { title: '반품사유', key: 'comments', width: 160, align: 'center' },
    { title: '진행상황', key: 'returnSituation', width: 110, align: 'center' },
];

//반품요청 조회 
const returnoutsListClick = async () => {
    try {
    await returnStore().FETCH_GET_RETURNOUTSLIST();    
    infodata.value = returnStore().getReturnOutsList;  
   
   } catch (error) {
     console.error('Error fetching data:', error);
   }
};

//반품요청전부 조회 
const returnOutsAllListClick = async () => {
    try {
    await returnStore().FETCH_GET_RETURNOUTSALLLIST( );    
    infodata.value = returnStore().getReturnOutsAllList;  
   
   } catch (error) { 
     console.error('Error fetching data:', error);
   }
};

//기타반품요청 등록
const addReturnOutsClick = async () => {
  if (!AddList.value.inspectionCode || !AddList.value.itemName || !AddList.value.itemCode || !AddList.value.returnAmount || !AddList.value.comments) {
    alert("모든 필드를 입력해주세요.");
    return;
  }
  try {
    console.log("입력값:", toRaw(AddList.value));  // 수정: AddList.value로 접근
    await returnStore().FETCH_ADD_RETURNOUTS(AddList.value);  // 수정: AddList.value로 접근
    console.log(returnStore().addReturnOuts);
    alert("기타반품요청이 등록되었습니다.");
    returnoutsListClick();
    cancel(); // 추가: 입력 후 모달창 닫기
   } catch (error) {
     console.error('Error fetching data:', error);
   }
};


//반품 처리
const completeReturnOutsClick = async () => {
  if (!(selectedItem.value.length > 0 && selectedItem.value[0])) {
    alert("선택된 항목이 없습니다.");
    return;
  }
  const returnCode = selectedItem.value[0].returnCode;
  console.log("선택된 반품 코드:", returnCode);

  if (!returnCode) {
    alert("반품 코드가 없습니다.");
    return;
  }

  try {
    await returnStore().FETCH_COMPLETE_RETURNOUTS(returnCode);
    console.log("반품 처리 완료:", returnStore().completeReturnOuts);
    alert("반품 처리되었습니다.");
    returnoutsListClick();
  } catch (error) {
    console.error('Error fetching data:', error);
  }  
};

// dialog 창 오픈
const dialogOpen = () => {
  isReturnDialogVisible.value = true;
};


// 한 item 클릭 시 returnNo 추출
watch(selectedItem, (selectedValue: any) => {
  // console.log(toRaw(selectedValue[0].returnNo))
  // if (toRaw(selectedValue.length) === 0)
  //   selectedRow.value = ''
  // else
  // selectedRow.value = toRaw(selectedValue[0])
  // console.log(selectedRow.value)
  if (selectedValue.length > 0 && selectedValue[0]) {
    console.log(toRaw(selectedValue[0].returnNo));
    selectedRow.value = toRaw(selectedValue[0]);
  } else {
    selectedRow.value = null;
  }
  console.log(selectedRow.value);
})

const resetAddList = () => {
  AddList.value = {
    inspectionCode: '',
    itemName: '',
    itemCode: '',
    returnAmount: '',
    comments: '',
  };
};

 //모달창 닫기
const cancel = async () => {
  isUpdateDialogVisible.value = false;
  isReturnDialogVisible.value = false;
  resetAddList(); // 입력값 초기화
}
</script>

<template>
  <div class="text-2xl mb-2">
    <p>반품요청 관리</p>

    <VCard>
      <VCardText>
        <div class="date-picker-container">
          <!-- 왼쪽 영역 -->
          <div class="left-buttons">
            <VBtn 
              class="return-search"
              color="primary" 
              @click="returnoutsListClick"
            >반품요청 조회</VBtn>

            <VBtn 
              class="return-search"
              color="primary" 
              @click="returnOutsAllListClick"
            >전부 조회</VBtn>

          </div>

          <!-- 오른쪽 영역 -->
          <div class="right-buttons app-user-search-filter flex-wrap gap-3">
            <VBtn 
              class="return-button"
              color="primary"
              @click="dialogOpen"
            >기타반품 요청</VBtn>
            <VBtn 
              class="return-button"
              color="primary"
              @click="completeReturnOutsClick"
            >반품 처리</VBtn>
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

  <v-dialog v-model="isReturnDialogVisible" max-width="600px">
  <v-card>
    <v-card-title>
      <span class="headline">기타 반품 요청</span>
    </v-card-title>
    <v-card-text>
      <v-form ref="form">
        <v-container>
          <v-row>
            <v-col cols="12">
              <v-text-field
                v-model="AddList.inspectionCode"
                label="검사 번호"
                required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="6">
              <v-text-field
                v-model="AddList.itemName"
                label="품목명"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="6">
              <v-text-field
                v-model="AddList.itemCode"
                label="품목코드"
                required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12">
              <v-text-field
                v-model="AddList.returnAmount"
                label="반품 수량"
                type="number"
                required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12">
              <v-text-field
                v-model="AddList.comments"
                label="반품 사유"
                required
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" text @click="cancel">취소</v-btn>
      <v-btn color="blue darken-1" text @click="addReturnOutsClick">등록</v-btn>
    </v-card-actions>
  </v-card>
</v-dialog>
</template>

<style>
.date-picker-container {
  display: flex;
  justify-content: space-between;
}

.left-buttons, .right-buttons {
  display: flex;
  gap: 20px; /* 버튼 사이의 간격을 동일하게 설정 */
}

.date-picker-wrapper {
  width: 150px; 
  margin-right: 20px; 
}

.return-search {
  margin-top: 35px;
}

.small-input {
  width: 200px;
}
  
.return-button {
  font-size: 15px;
  margin-top: 35px;
}
</style>