<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable';
import { ref, onMounted } from 'vue';
import axios from 'axios';
const infodata = ref([]);
const dateRange = ref('')

const headers = [
  { title: '외주번호', key: 'outsourcNo' ,width:120  },
  { title: 'MRP 취합 번호',sortable: false, key: 'mrpGatheringNo' ,width:150 },
  { title: '구매 및 생산여부', key: 'orderOrProductionStatus',width:180 },
  { title: '품목코드', key: 'itemCode' ,width:120},
  { title: '품목명', key: 'itemName',width:120 },
  { title: '단위', key: 'unitOfMrpGathering' ,width:120},
  { title: '발주/작업지시기한', key: 'claimDate' ,width:180},
  { title: '발주/작업지시완료기한', key: 'dueDate' ,width:200},
  { title: 'Mrp 일련번호', key: 'mrpGatheringSeq' ,width:150},
  { title: '필요수량', key: 'necessaryAmount' ,width:120},
  { title: '외주지시수량', key: 'outsourcAmount' ,width:150},
  { title: '외주단가', key: 'unitPriceOfOutsourc' ,width:120},
  { title: '합계액', key: 'sumPriceOfOutsourc' ,width:120},
]


const fetchData = async () => {
  const [startDate, endDate] = dateRange.value.split(' to ');
  if (!dateRange) {
    alert('날짜를 선택해주세요.'); // 날짜를 선택하지 않았을 경우 알림 창 띄우기
    return [];
  }
  try {
    const searchDateCondition = '';

    // searchOderableList API 호출
    const response = await axios.get('http://localhost:8282/logi/outsourc/searchOutsourcInfoList', {
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


// 버튼 클릭 시 데이터 가져오기
const handleButtonClick = async () => {
  infodata.value = await fetchData();
};

</script>

<template>
   <VCard class="dd-flex">
  <div class="d-flex">
    <VCol 
        class="d-flex gap-1"
      >
    <AppDateTimePicker
      v-model="dateRange"
      placeholder="시작일&종료일"
      :config="{ mode: 'range' }"
      class="mr-2"
      style="width: 300px"
      prepend-icon="tabler-calendar-event"
    />
    <div class="vbtn">
    <VBtn
    color="primary"
          @click="handleButtonClick"
        >
        외주발주 조회
        </VBtn>
  </div>
    </VCol>
</div>

  <VDataTable class="dataTable"
    :headers="headers"
    :items="infodata"
    :pagination="false"
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

.vb-1{
  margin-right: 0.5rem;
}

</style>