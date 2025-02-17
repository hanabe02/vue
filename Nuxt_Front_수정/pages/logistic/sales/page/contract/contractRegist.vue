<script setup lang="ts">
import { ref,watch } from 'vue';
import { VDataTable } from 'vuetify/labs/VDataTable'
import axios from 'axios';
import registModal from './contractRegistModal.vue'

const infodata = ref([]);
const dateRange = ref('');
const isDialogVisible = ref(false);
const customerData=ref('');
const textFieldValue=ref('');
const date = ref('');
const date1 = ref('');
const infodataDetail=ref([]);
const selectNum=ref('');
const textFieldValue1=ref('');
const selectRow=ref([]);



const headers = [
  { title: '견적일련번호', key: 'estimateNo', width:150 },
  { title: '수주유형', key: 'contractStatus', width:170 },
  { title: '거래처명', key: 'customerCode', width:170 },
  { title: '수주요청자', key: 'contractRequester' , width:150},
  { title: '견적일자', key: 'estimateDate', width:120 },
  { title: '유효일자', key: 'effectiveDate', width:120 },
  { title: '견적담당자명', key: 'estimateRequester', width:150 },
  { title: '비고', key: 'description' , width:120},
]

const headers1 = [
  { title: '견적상세일련번호',sortable: false, key: 'estimateDetailNo' , width:180},
  { title: '품목코드', key: 'itemCode', width:150 },
  { title: '품목명', key: 'itemName', width:200 },
  { title: '단위', key: 'unitOfEstimate' , width:120},
  { title: '납기일', key: 'dueDateOfEstimate', width:170 },
  { title: '견적수량', key: 'estimateAmount', width:120 },
  { title: '견적단가', key: 'unitPriceOfEstimate' , width:150},
  { title: '합계액', key: 'sumPriceOfEstimate', width:180 },
  { title: '비고', key: 'description' , width:120},
]

const select=[
{ title: '수주일련번호',sortable: false, key: 'contractNo' , width:120},
  { title: '견적일련번호', key: 'estimateNo', width:150 },
  { title: '수주요청자', key: 'contractRequester', width:150 },
]

const fetchData = async () => {
  // const [startDate, endDate] = dateRange.value.split(' to ');
  console.log("textFieldValue",textFieldValue._rawValue)
  try {
    const startDate=date.value
    const endDate=date1.value

    // searchOderableList API 호출
    const response = await axios.get('http://localhost:8282/logi/sales/searchEstimateInContractAvailable', {
      params: {

        startDate,
        endDate,
      },
    });

    // API 응답에서 gridRowJson 추출
    const  {gridRowJson}  = response.data;

    console.log("gridRowJson:", gridRowJson);
    return gridRowJson;
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
};


// 버튼 클릭 시 데이터 가져오기
const handleButtonClick = async () => {
  infodata.value = await fetchData();
  console.log("infodata",infodata._rawValue)

}



const detailRow= async(item, row)=>{
  console.log("row",row)
  selectRow.value=row.item
    infodataDetail.value=row.item.estimateDetailTOList;

}



const registClick=async()=>{
    console.log("registClickRow",selectRow._rawValue)

  try {
    const paramList=selectRow._rawValue[0]
    const contractDate = new Date().toISOString().split('T')[0];
    const targetObject = infodata._rawValue.find(obj => obj.estimateNo === paramList);
    console.log("targetObject",targetObject)
    const batchList={contractRequester:targetObject.contractRequester,contractType:targetObject.contractStatus,customerCode:targetObject.customerCode,
        description:targetObject.description,estimateNo:targetObject.estimateNo
    }

    console.log(contractDate);
    console.log("contractRequester",contractDate,batchList,targetObject.personCodeInCharge,targetObject.estimateDetailTOList)
    // searchOderableList API 호출
    const response = await axios.post('http://localhost:8282/logi/sales/addNewContract', 
    {
    batchList: batchList,
    contractDate:contractDate,
    personCodeInCharge: "EMP-01",
    estimateDetail: targetObject.estimateDetailTOList,
  });

      console.log('Response from server:', response.data);
      alert('수주 등록 완료.');
      selectRow.value=[];
      handleButtonClick();
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
}


const closeModal=()=>{
    isDialogVisible.value=false
}

const modifyCom=()=>{
    if(selectRow.contractRequester===''){
        alert("수주요청자를 입력하세요")
    }
    else{
        isDialogVisible.value=false
    }
}
</script>

<template>
    <Vcard>
  <VRow>
  <VCol cols="12" md="6" class="d-flex gap-1">
    <AppDateTimePicker
      v-model="date"
      placeholder="YYYY-MM-DD"
      class="mr-2"
      style="width: 250px"
      prepend-icon="tabler-calendar-event"
    />
    <div class="divider">~</div>
    <AppDateTimePicker
      v-model="date1"
      placeholder="YYYY-MM-DD"
      class="mr-2"
      style="width: 200px"
    />  
    <VBtn
      color="primary"
      @click="handleButtonClick"
    >
      검색
    </VBtn>
    <registModal
        v-model="isDialogVisible"
        :infodata="infodata"
        :selectRow="selectRow"
        @closeModal="closeModal"
        @modifyCom="modifyCom"
        />
    <VBtn
      color="primary"
      @click="registClick"
    >
      등록
    </VBtn>
  </VCol>
</VRow>

  <VDataTable
    :headers="headers"
    :items="infodata"
    :items-per-page="10"
    height="400"
    fixed-header
    show-select
    item-value="estimateNo"
    v-model="selectRow"
    @click:row="detailRow"
  >
  </VDataTable>
</Vcard>


<Vcard 
title="수주상세"
>
<VDataTable
    :headers="headers1"
    :items="infodataDetail"
    :items-per-page="10"
    height="200"
    fixed-header
  >
  </VDataTable>
</Vcard>
</template>


<style scoped>
/* 추가된 스타일 */
.d-flex {
  display: flex;
  align-items: center;
}

/* 테이블 스타일 추가 */
.v-data-table {
  margin-top: 50px; /* Add margin to the top of the table */
}

.vbtn{
  margin-left: auto;
}

.dd-flex{
  margin-top: 30px;
}

.vb-1{
  margin-right: 0.5rem;
}

.divider {
  font-size: 50px; /* 원하는 크기로 조절 */
  color: primary;  /* 원하는 색상으로 조절 */
  margin: 0 10px; /* 원하는 여백으로 조절 */
}

</style>