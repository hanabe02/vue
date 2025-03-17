<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable';
import { ref, computed, watch } from 'vue';
import axios from 'axios';

const infodata = ref([]);
const selectData = ref([]);  // 단일 선택을 위해 null 초기값 설정
const dialog = ref(false);     // dialog 변수를 정의합니다.
const inspectionResults = ref({ passQty: 0, failQty: 0, inspector: '', comments: '' }); // 추가한 데이터 필드 초기화

const selectedRow = computed(() => {
  return infodata.value.find(item => item.mrpGatheringNo === selectData.value);
});

const headers = [
  { title: '외주번호', key: 'outsourcNo', width: 120 },
  { title: 'MRP 취합 번호', sortable: false, key: 'mrpGatheringNo', width: 180 },
  { title: '구매 및 생산여부', key: 'orderOrProductionStatus', width: 180 },
  { title: '품목코드', key: 'itemCode', width: 120 },
  { title: '품목명', key: 'itemName', width: 120 },
  { title: '단위', key: 'unitOfMrpGathering', width: 120 },
  { title: '발주/작업지시기한', key: 'claimDate', width: 180 },
  { title: '발주/작업지시완료기한', key: 'dueDate', width: 200 },
  { title: 'Mrp 일련번호', key: 'mrpGatheringSeq', width: 150 },
  { title: '필요수량', key: 'necessaryAmount', width: 120 },
  { title: '외주지시수량', key: 'outSourcAmount', width: 150 },
  { title: '외주단가', key: 'unitPriceOfOutsourc', width: 120 },
  { title: '합계액', key: 'sumPriceOfOutsourc', width: 120 },
];

const fetchData = async () => {
  try {
    const response = await axios.get('http://localhost:8282/logi/outsourc/outInspectionInfoList');
    const { gridRowJson } = response.data;

    if (gridRowJson.length === 0) return [];

    const itemCode = gridRowJson[0].itemCode;
    const unitPriceResponse = await axios.get('http://localhost:8282/logi/outsourc/getStandardUnitPrice', {
      params: { itemCode },
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

const handleComplete = async () => {
  try {
    if (!selectData.value) {
      alert('선택된 행이 없습니다.');
      return;
    }

    dialog.value = true;
  } catch (error) {
    console.error('요청 중 오류 발생:', error);
    alert('외주자재 검사 완료 승인 중 오류가 발생했습니다.');
  }
};

const handleButtonClick = async () => {
  infodata.value = await fetchData();
};

const handleSelect = (selectedItems) => {
  if (selectedItems.length > 0) {
    selectData.value = selectedItems[0];  // 첫 번째 선택된 항목으로 설정
  } else {
    selectData.value = null;  // 선택된 항목이 없을 경우 null로 설정
  }
};

watch(selectData, () => {
  if (selectedRow.value) {
    inspectionResults.value.passQty = selectedRow.value.outSourcAmount || 0;
    inspectionResults.value.failQty = 0;
    inspectionResults.value.inspector = '';
    inspectionResults.value.comments = '';
  }
});

const adjustQuantities = (field) => {
  const outSourcAmount = parseInt(selectedRow.value?.outSourcAmount, 10) || 0;
  if (field === 'passQty') {
    inspectionResults.value.failQty = outSourcAmount - inspectionResults.value.passQty;
  } else if (field === 'failQty') {
    inspectionResults.value.passQty = outSourcAmount - inspectionResults.value.failQty;
  }
};

const closeDialog = () => {
  dialog.value = false;
};

const submitResults = async () => {
  const outSourcAmount = parseInt(selectedRow.value?.outSourcAmount, 10) || 0;
  const passQty = parseInt(inspectionResults.value?.passQty, 10) || 0;
  const failQty = parseInt(inspectionResults.value?.failQty, 10) || 0;

  if (passQty + failQty !== outSourcAmount) {
    console.log(passQty);
    console.log(failQty);
    console.log(outSourcAmount);
    alert('합격량과 불합격량의 합이 지시수량과 같아야 합니다.');
    return;
  }

  if (passQty < 0 || failQty < 0) {
    alert('정확한 값을 입력해주세요.');
    return;
  }

  if (!inspectionResults.value.inspector) {
    alert('검사자명을 입력해야 합니다.');
    return;
  }

  const dataList = {
    mrpGatheringNo: selectedRow.value?.mrpGatheringNo || '',
    itemCode: selectedRow.value?.itemCode || '',
    outSourcAmount: parseInt(selectedRow.value?.outSourcAmount)||0,
    passQty: parseInt(inspectionResults.value.passQty)||0,
    failQty: parseInt(inspectionResults.value.failQty)||0,
    inspector: inspectionResults.value.inspector,
    comments: inspectionResults.value.comments
  };

  try {
    const response = await axios.post('http://localhost:8282/logi/inspection/registResult', dataList);
    console.log('Response:', response.data);
    alert('검사 완료했습니다.');
    infodata.value = await fetchData();
  } catch (error) {
    console.error('Error submitting data:', error);
    alert('데이터 제출 중 오류가 발생했습니다.');
  }

  dialog.value = false;
};

</script>

<template>
  <VCard class="dd-flex">
    <div class="d-flex">
      <VCol class="d-flex gap-1">
        <VBtn @click="handleButtonClick">
          외주발주현황 조회
        </VBtn>
        <VBtn @click="handleComplete">
          외주자재 검사 결과
        </VBtn>
      </VCol>
    </div>

    <VDataTable
      class="dataTable"
      :headers="headers"
      :items="infodata"
      :pagination="false"
      show-select
      select-strategy="single"
      item-value="mrpGatheringNo"
      v-model="selectData"
      @update:model-value="handleSelect"
    />

    <VDialog v-model="dialog" max-width="600">
  <VCard>
    <VCardTitle>외주 검사 결과</VCardTitle>
    <VCardText>
      <VForm ref="form">
        <VRow>
          <VCol cols="12">
            <label>MRP 취합번호</label>
            <VTextField
              :value="selectedRow?.mrpGatheringNo"
              readonly
            />
          </VCol>
          <VCol cols="12">
            <label>품목코드</label>
            <VTextField
              :value="selectedRow?.itemCode"
              readonly
            />
          </VCol>
          <VCol cols="12">
            <label>외주지시수량</label>
            <VTextField
              :value="selectedRow?.outSourcAmount"
              readonly
            />
          </VCol>
          <VCol cols="6">
            <label>합격량</label>
            <VTextField
              v-model="inspectionResults.passQty"
              type="number"
              @input="() => adjustQuantities('passQty')"
            />
          </VCol>
          <VCol cols="6">
            <label>불합격량</label>
            <VTextField
              v-model="inspectionResults.failQty"
              type="number"
              @input="() => adjustQuantities('failQty')"
            />
          </VCol>
          <VCol cols="12">
            <label>검사자</label>
            <VTextField
              v-model="inspectionResults.inspector"
            />
          </VCol>
          <VCol cols="12">
            <label>추가 코멘트</label>
            <VTextField
              v-model="inspectionResults.comments"
            />
          </VCol>
        </VRow>
      </VForm>
    </VCardText>
    <VCardActions>
      <VSpacer />
      <VBtn color="blue darken-1" text @click="closeDialog">취소</VBtn>
      <VBtn color="blue darken-1" text @click="submitResults">입력</VBtn>
    </VCardActions>
  </VCard>
</VDialog>
  </VCard>
</template>

<style scoped>
.d-flex {
  display: flex;
  align-items: center;
}

.v-data-table {
  margin-top: 50px;
}

.vbtn {
  margin-left: auto;
}

.dd-flex {
  margin-top: 30px;
}

label {
  margin-bottom: 4px;
  display: block;
}
</style>
