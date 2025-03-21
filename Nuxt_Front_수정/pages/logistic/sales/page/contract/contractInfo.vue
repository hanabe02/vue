<script setup lang="ts">
import { ref,watch } from 'vue';
import { VDataTable } from 'vuetify/labs/VDataTable'
import axios from 'axios';
import contractInfoModal1 from './contractInfoModal.vue'


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


const headers = [
  { title: '수주일련번호',sortable: false, key: 'contractNo' , width:120},
  { title: '견적일련번호', key: 'estimateNo', width:150 },
  { title: '회사코드', key: 'customerCode', width:150 },
  { title: '수주유형분류', key: 'contractTypeName', width:170 },
  { title: '견적일자', key: 'estimateDate', width:120 },
  { title: '수주일자', key: 'contractDate', width:120 },
  { title: '수주요청자', key: 'contractRequester' , width:150},
  { title: '수주담당자', key: 'empNameInCharge', width:150 },
  { title: '납품완료여부', key: 'deliveryCompletionStatus' , width:150},
  { title: '비고', key: 'description' , width:120},
]

const headers1 = [
  { title: '수주상세일련번호',sortable: false, key: 'contractDetailNo' , width:180},
  { title: '취합발주번호', key: 'estimateNo', width:150 },
  { title: '품목명', key: 'itemName', width:200 },
  { title: '납기일', key: 'dueDateOfContract', width:170 },
  { title: '견적수량', key: 'estimateAmount', width:120 },
  { title: '재고사용량', key: 'stockAmountUse', width:150 },
  { title: '견적단가', key: 'unitPriceOfContract' , width:150},
  { title: '합계액', key: 'sumPriceOfContract', width:180 },
  { title: '납품가능여부', key: 'operationCompletedStatus' , width:200},
  { title: '납품완료여부', key: 'deliveryCompletionStatus' , width:200},
  { title: '작업지시완료여부', key: 'workStatus' , width:200},
  { title: '발주완료여부', key: 'orderStatus' , width:200},
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
    const searchCondition = 'searchByDate';
    const customerCode='';

    // searchOderableList API 호출
    const response = await axios.get('http://localhost:8282/logi/sales/searchContract', {
      params: {
        searchCondition,
        customerCode,
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

const cutomerSave=async()=>{
    //여기서 api를 한번 더 쓸지 아니면 customerCode만 바꿔서 fetchData를 이용해서 api를 사용할지 고민할것.
    // const [startDate, endDate] = dateRange.value.split(' to ')||'';
  console.log("textFieldValue",textFieldValue._rawValue)
  try {
    const startDate=date.value
    const endDate=date1.value
    const searchCondition = 'searchByCustomer';
    const customerCode=textFieldValue._rawValue;

    // searchOderableList API 호출
    const response = await axios.get('http://localhost:8282/logi/sales/searchContract', {
      params: {
        searchCondition,
        customerCode,
        startDate,
        endDate,
      },
    });

    // API 응답에서 gridRowJson 추출
    const  {gridRowJson}  = response.data;

    console.log("gridRowJson:", gridRowJson);
    infodata.value=gridRowJson;
    // return gridRowJson;
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
}


// 버튼 클릭 시 데이터 가져오기
const handleButtonClick = async () => {
  infodata.value = await fetchData();
  console.log("infodata",infodata._rawValue)

}

const dialogClose=()=>{
    isDialogVisible.value=false;
}

const comInfoSave=()=>{
    textFieldValue.value = customerData.value.customerCode; 
    console.log(textFieldValue._rawValue)
    isDialogVisible.value=false;
}

const onSelected=({selected,a})=>{
    console.log("selected", selected);
  console.log("a", a.internalItem.columns)
  customerData.value=a.internalItem.columns
}

const detailRow= async(item, row)=>{
  console.log("row",row.internalItem.columns.contractNo)
  try {
    const contractNo=row.internalItem.columns.contractNo

    // searchOderableList API 호출
    const response = await axios.get('http://localhost:8282/logi/sales/searchContractDetail', {
      params: {
        contractNo
      },
    });

    // API 응답에서 gridRowJson 추출
    const  {gridRowJson}  = response.data;
    infodataDetail.value=gridRowJson;
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
}


const sortedData = async () => {
  infodata.value = await fetchData();
  console.log("selectNum",selectNum)
  console.log("selectNum",selectNum._rawValue.key)
  console.log("textFieldValue1",textFieldValue1.value)
  console.log("infodata",infodata.value)


  if (selectNum._rawValue.key === 'contractRequester' && textFieldValue1.value !== '') {
    const searchedData = infodata.value.filter((item) => {
      return item.contractRequester.includes(textFieldValue1.value);
    });

    console.log("searchedData",searchedData)
    infodata.value = searchedData;}

   if (selectNum._rawValue.key === 'contractNo' && textFieldValue1.value !== '') {
    const searchedData = infodata.value.filter((item) => {
      return item.contractNo.includes(textFieldValue1.value);
    });
     
    console.log("searchedData",searchedData)
    infodata.value = searchedData;}

    if (selectNum._rawValue.key === 'estimateNo' && textFieldValue1.value !== '') {
    const searchedData = infodata.value.filter((item) => {
      return item.estimateNo.includes(textFieldValue1.value);
    });
  
    console.log("searchedData",searchedData)
    infodata.value = searchedData;
  }
};

const cutomerDelete=async()=>{
  textFieldValue.value=''
  infodata.value = await fetchData();
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
  </VCol>
    
    <!-- 두 번째 VRow -->
    <VRow>
      <VCol cols="12" md="12" class="d-flex gap-1">
        <contractInfoModal1 
          :infodata="infodata"
          :textFieldValue="textFieldValue"
          v-model="isDialogVisible"
          @dialogClose="dialogClose"
          @comInfoSave="comInfoSave"
          @selected="onSelected"
          @cutomerSave="cutomerSave"
          @cutomerDelete="cutomerDelete"
        />
      </VCol>
    </VRow>
</VRow>

  <VRow>
    <VCol cols="12" md="2" offset-md="7">
<AppCombobox
v-model="selectNum"
:items="select"
placeholder="Search 조건"
clearable

/></VCol>
<VCol cols="12" md="2">
    <v-text-field
    @input="sortedData"
    v-model="textFieldValue1"
    placeholder="Search..."
    clearable
    ></v-text-field>
  </VCol>
  </VRow>

  <VDataTable
    :headers="headers"
    :items="infodata"
    :items-per-page="10"
    height="400"
    fixed-header
    @click:row="detailRow"
  >
  </VDataTable>
</Vcard>
<Vcard>
  
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