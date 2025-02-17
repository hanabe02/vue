<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable';
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

const infodata = ref([]);
const selectData = ref([]);

const headers = [
  { title: '검사코드', key: 'inspectionId', width: 120 },
  { title: 'MRP 취합 번호', sortable: false, key: 'mrpGatheringNo', width: 180 },
  { title: '품목코드', key: 'itemCode', width: 120 },
  { title: '품목명', key: 'itemName', width: 120 },
  { title: '단위', key: 'unitOfMrpGathering', width: 120 },
  { title: '외주지시수량', key: 'instructionQuantity', width: 150 },
  { title: '합격수량', key: 'passQuantity', width: 150 },
  { title: '불합격수량', key: 'failQuantity', width: 150 },
  { title: '검사자', key: 'inspector', width: 120 },
  { title: '검사날짜', key: 'inspectionDate', width: 120 },
  { title: '비고', key: 'comments', width: 120 },
  { title: '반품 등록 유무', key: 'registReturn', width: 160 },
];

const fetchData = async () => {
  const response = await axios.get('http://localhost:8282/logi/inspection/showInspectionList');
  infodata.value = response.data.gridRowJson.map(item => {
    item.unitOfMrpGathering = 'EA';
    return item;
  });
};

onMounted(async () => {
  fetchData();
});

const handleButtonClick = async () => {
  console.log(selectData);
  console.log(selectData.value);

  try {
    if (selectData.value.length === 0) {
      alert('선택된 행이 없습니다.');
      return;
    }

      // 선택된 항목의 inspectionId를 가져옵니다.
      const selectedId = selectData.value;

      // infodata에서 해당 항목을 찾습니다.
      const selectedItem = infodata.value.find(item => item.inspectionId === selectedId);

      // 선택된 항목의 registReturn 값을 확인합니다.
      if (selectedItem && selectedItem.registReturn === 'Y') {
        alert('이미 반품 등록된 항목입니다.');
        return;
      }

    const response = await axios.post('http://localhost:8282/logi/inspection/addReturnOuts', selectData.value);
    alert('반품 요청을 등록했습니다.');
    fetchData();
  } catch (error) {
    console.error('Error submitting data:', error);
    alert('반품 요청 중 오류가 발생했습니다.');
  }
};

const handleSelect = (selectedItems) => {
  if (selectedItems.length > 0) {
    selectData.value = selectedItems[0];  // 첫 번째 선택된 항목으로 설정
  } else {
    selectData.value = null;  // 선택된 항목이 없을 경우 null로 설정
  }
};
</script>

<template>
  <VCard class="dd-flex">
    <div class="d-flex">
      <VCol class="d-flex gap-1">
        <VBtn @click="handleButtonClick">
          반품 요청
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
      item-value= "inspectionId"
      v-model:selected-items="selectData"
      @update:model-value="handleSelect"
    />
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
