<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable';
import { ref, onMounted } from 'vue';
import ForwardModal1 from './fowardModal.vue'
import axios from 'axios';

const infodata = ref([]);
const dateRange = ref('');
const selectData = ref([]);
const handlesimulClick=ref(false);
const isDialogVisible=ref(false)
const spreadData=ref([]);



const headers = [
  { title: '외주번호', key: 'outsourcNo' , width:120},
  { title: 'MRP 취합 번호',sortable: false, key: 'mrpGatheringNo', width:200 },
  { title: '구매 및 생산여부', key: 'orderOrProductionStatus', width:170 },
  { title: '품목코드', key: 'itemCode', width:150 },
  { title: '품목명', key: 'itemName', width:120 },
  { title: '단위', key: 'unitOfMrpGathering' , width:100},
  { title: '발주/작업지시기한', key: 'claimDate', width:180 },
  { title: '발주/작업지시완료기한', key: 'dueDate' , width:200},
  { title: 'Mrp 일련번호', key: 'mrpGatheringSeq' , width:200},
  { title: '필요수량', key: 'necessaryAmount', width:120 },
  { title: '외주지시수량', key: 'outsourcAmount', width:150 },
  { title: '외주단가', key: 'unitPriceOfOutsourc' , width:120},
  { title: '합계액', key: 'sumPriceOfOutsourc', width:150 },
]



const fetchData = async () => {
  const dateRangeValue = dateRange.value;

  // 날짜 선택 여부 확인
  if (!dateRangeValue) {
    alert('날짜를 선택해주세요.'); // 날짜를 선택하지 않았을 경우 알림 창 띄우기
    return [];
  }

  const [startDate, endDate] = dateRange.value.split(' to ');
  try {
    const searchDateCondition = '';

    // searchOderableList API 호출
    const response = await axios.get('http://localhost:8282/logi/outsourc/searchForwardableList', {
      params: {
        searchDateCondition,
        startDate,
        endDate,
      },
    });

    // API 응답에서 gridRowJson 추출
    const { gridRowJson } = response.data;

    // 첫 번째 행의 itemCode 값을 사용 (실제로는 특정 로직으로 가져와야 함)
    const itemCode = gridRowJson.length > 0 ? gridRowJson[0].itemCode : 'defaultItemCode';

    // getStandardUnitPrice API 호출
    const unitPriceResponse = await axios.get('http://localhost:8282/logi/outsourc/getStandardUnitPrice', {
      params: {
        itemCode,
      },
    });
    
    const unitPriceOfOutsourc = unitPriceResponse.data.gridRowJson;
    gridRowJson.forEach(item => {
      item.unitPriceOfOutsourc = unitPriceOfOutsourc;
      });
    console.log("Received Data:", unitPriceOfOutsourc);
    console.log("gridRowJson:", gridRowJson);
    return gridRowJson;
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
};

const selectRow = (item:any, row:any) => {
  console.log("row", row?.internalItem?.columns);
  console.log("rowIndex", row.internalItem.index);
  selectData.value = row?.internalItem?.columns;
  console.log("selectData",selectData._rawValue)
}


// 버튼 클릭 시 데이터 가져오기
const handleButtonClick = async () => {
  console.log("infodata",infodata)
  infodata.value = await fetchData();
};

const handleReleaseClick=async()=>{
  console.log("selectData", selectData._rawValue[0].replace(/\[|\]/g, ''));
  const forWardsimulData = selectData._rawValue[0].replace(/\[|\]/g, ''); // 배열로 감싸기
  console.log("forWardsimulData", forWardsimulData);


  const targetObject = infodata._rawValue.find(obj => obj.mrpGatheringNo === forWardsimulData);
  console.log("targetObject",targetObject)


  if (targetObject !== null) {
    try {
      const response = await axios.post('http://localhost:8282/logi/outsourc/updateForwardStatus', targetObject);

      // 성공적으로 등록되면 적절한 처리를 추가하세요.
      console.log('Response from server:', response.data);
      alert('외주자재 출고처리 완료.');
      handleButtonClick();
    } catch (error) {
      console.error('Error while making the request:', error);
      // 에러 처리 추가
    }
}
}

const openModal=async()=>{
  isDialogVisible.value=true;
  console.log("ModalselectData",selectData.value[0])
  const targetWarehouseCode=selectData.value[0]

  const targetObject = infodata.value.find(obj => obj.mrpGatheringNo === targetWarehouseCode);
  console.log("targetObject",targetObject)
  const mrpGatheringNo=targetObject.mrpGatheringNo
  const id=targetObject.itemCode
  const seq=targetObject.mrpGatheringSeq
  console.log("mrpGatheringNo",mrpGatheringNo)


  if(targetObject !== null){
    try{
      const response = await axios.get('http://localhost:8282/logi/outsourc/showReleaseDialog', {
        params:{
          mrpGatheringNo,
          id,
          seq
        }
      });
      console.log('Response from server:', response);
      spreadData.value=response.data.result
    }catch(error){
      console.error('Error while making the request:', error);
    }
  }
}

const closeModal=()=>{
  isDialogVisible.value=false
}


</script>

<template>
  <VCard >
  <div class="d-flex">
    <VCol 
        class="d-flex gap-1"
      >
    <AppDateTimePicker
      v-model="dateRange"
      placeholder="시작일&종료일"
      :config="{ mode: 'range' }"
      class="dateTime"
      style="width: 300px"
      prepend-icon="tabler-calendar-event"
    />
    <div class="vbtn">
    <VBtn
    class="vb-1"
      color="primary"
          @click="handleButtonClick"
        >
        외주자재 발주 필요목록 조회
        </VBtn>
        <ForwardModal1
        :isDialogVisible="isDialogVisible"
        :selectData="selectData"
        :spreadData="spreadData"
        @openModal="openModal"
        @closeModal="closeModal"
        />
        <VBtn
          @click="handleReleaseClick"
            class="ml-2"
        >
        외주자재 출고 처리
        </VBtn>
  </div>
</VCol>
</div>

  <VDataTable class="dataTable"
    :headers="headers"
    :items="infodata"
    :pagination="false"
    show-select
    item-value="mrpGatheringNo"
    v-model="selectData"
    @click:row="selectRow"
  />
</VCard>
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



</style>