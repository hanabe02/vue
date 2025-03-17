<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable';
import { ref } from 'vue'
import axios from 'axios'

const infodata = ref([])
const insertData = ref([])
const dateRange = ref('')
const editDialog = ref(false)
const editedIndex = ref(-1)

const defaultItem = ref({
  mrpGatheringNo: '',
  orderOrProductionStatus: '',
  itemCode: '',
  itemName: '',
  unitOfMrpGathering: '',
  claimDate: '',
  dueDate: '',
  necessaryAmount: '',
  outsourcAmount: '',
  unitPriceOfOutsourc: '',
  sumPriceOfOutsourc: '',
  actions: '',
})

const editedItem = ref(defaultItem.value)

const headers = [
  { title: '외주번호', key: 'outsourcNo' ,width:120 },
  { title: 'MRP 취합 번호', sortable: false, key: 'mrpGatheringNo' ,width:180 },
  { title: '구매 및 생산여부', key: 'orderOrProductionStatus' ,width:160},
  { title: '품목코드', key: 'itemCode' ,width:120},
  { title: '품목명', key: 'itemName',width:120 },
  { title: '단위', key: 'unitOfMrpGathering' ,width:120},
  { title: '발주/작업지시기한', key: 'claimDate' ,width:180},
  { title: '발주/작업지시완료기한', key: 'dueDate' ,width:200},
  { title: 'Mrp 일련번호', key: 'mrpGatheringSeq',width:150 },
  { title: '필요수량', key: 'necessaryAmount',width:120 },
  { title: '외주지시수량', key: 'outsourcAmount' ,width:150},
  { title: '외주단가', key: 'unitPriceOfOutsourc' ,width:120},
  { title: '합계액', key: 'sumPriceOfOutsourc',width:120 },
  { title: 'ACTIONS', key: 'actions',width:120 },
];

const fetchData = async () => {
  const dateRangeValue = dateRange.value;

// 날짜 선택 여부 확인
if (!dateRangeValue) {
  alert('날짜를 선택해주세요.'); // 날짜를 선택하지 않았을 경우 알림 창 띄우기
  return [];
}

  const [startDate, endDate] = dateRange.value.split(' to ')
  try {
    const searchDateCondition = ''

    const response = await axios.get('http://localhost:8282/logi/outsourc/searchOderableList', {
      params: {
        searchDateCondition,
        startDate,
        endDate,
      },
    });

    const { gridRowJson } = response.data;

    const itemCode = gridRowJson.length > 0 ? gridRowJson[0].itemCode : 'defaultItemCode';

    const unitPriceResponse = await axios.get('http://localhost:8282/logi/outsourc/getStandardUnitPrice', {
      params: {
        itemCode,
      },
    });

    const unitPriceOfOutsourc = unitPriceResponse.data.gridRowJson;
    gridRowJson.forEach(item => {
      item.unitPriceOfOutsourc = unitPriceOfOutsourc;
    });
    console.log('Received Data:', unitPriceOfOutsourc);
    console.log('gridRowJson:', gridRowJson);
    return gridRowJson;
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
};


const handleButtonSearch = async () => {
  infodata.value = await fetchData();
};


const selectRow = (item, row) => {
  console.log("row", row?.internalItem?.columns);
  console.log("rowIndex", row.internalItem.index);
  insertData.value = row?.internalItem?.columns;
  console.log("insertData",insertData._rawValue)
}

const handleButtonInsert = async () => {
  console.log("외주발주진행");
  // console.log("selectData", insertData._rawValue[0].replace(/\[|\]/g, ''));
  console.log("selectData", insertData._rawValue[1].replace(/\[|\]/g, ''));
  const forWardsimulData = insertData._rawValue[1].replace(/\[|\]/g, ''); // 배열로 감싸기
  console.log("forWardsimulData", forWardsimulData);


  const targetObject = infodata._rawValue.find(obj => obj.mrpGatheringNo === forWardsimulData);
  console.log("targetObject",targetObject)

  
  const outOrderData = [targetObject]; // 배열로 감싸기
  console.log("outOrderData", outOrderData);

  if (outOrderData !== null) {
    try {
      const response = await axios.post('http://localhost:8282/logi/outsourc/insertOutsourc', outOrderData, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      // 성공적으로 등록되면 적절한 처리를 추가하세요.
      console.log('Response from server:', response.data);
      alert('외주발주 등록 완료.');
      handleButtonSearch();
    } catch (error) {
      console.error('Error while making the request:', error);
      // 에러 처리 추가
    }
  } else {
    console.error("No row selected");
    // 에러 처리 추가
  }
};

const editItem = (item,row) => {
  editedIndex.value = infodata.value.indexOf(item);
  console.log("editedIndex", editedIndex.value);
  console.log("gridRowJson", infodata);
  // editedItem.value에 item을 복사하고 outsourcAmount를 추가
  editedItem.value = { ...item };
  console.log("editedItem.value", editedItem.value);
  editDialog.value = true;
};


const close = () => {
  editDialog.value = false;
  editedIndex.value = -1;
  editedItem.value = { ...defaultItem.value };
};


const save = () => {
  if (editedIndex.value > -1) {
    const editedRow = infodata.value[editedIndex.value];
    
    if (!editedRow.outsourcAmount) {
      // outsourcAmount가 없을 때만 추가
      editedRow.outsourcAmount = editedItem.value.outsourcAmount;
      
      // sumPriceOfOutsourc 추가
      editedRow.sumPriceOfOutsourc = editedRow.outsourcAmount * editedRow.unitPriceOfOutsourc;
    }
  } else {
    console.log("에러 리발");
  }

  close();
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
      item-key="mrpGatheringNo"
      prepend-icon="tabler-calendar-event"
    />
<div class="vbtn">
        <VBtn 
        color="primary"
          @click="handleButtonSearch"
          class="vb-1"
        >
        외주가능리스트 조회
        </VBtn>
        
        <VBtn
        color="primary"
          @click="handleButtonInsert"
        >
        외주발주 등록
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
    v-model="insertData"
    @click:row="selectRow"
  >
    <template #item.actions="{ item }">
      <div class="d-flex gap-1">
        <IconBtn  @click="() => editItem(item, row)">
          <VIcon icon="tabler-edit" />
        </IconBtn>
      </div>
    </template>
  </VDataTable>

  <VDialog
    v-model="editDialog"
    max-width="600px"
  >
    <VCard>
      <VCardTitle>
        <span class="headline">Edit Item</span>
      </VCardTitle>

      <VCardText>

        <VContainer>
          <VRow>
            <!-- fullName -->
            <VCol
              cols="12"
              sm="6"
              md="4"
            >
              <VTextField
                v-model="editedItem.outsourcAmount"
                label="외주지시수량"
              />
            </VCol>

          </VRow>
        </VContainer>
      </VCardText>

      <VCardActions>
        <VSpacer />

        <VBtn
          color="error"
          variant="outlined"
          @click="close"
        >
          Cancel
        </VBtn>

        <VBtn
          color="success"
          variant="elevated"
          @click="save"
        >
          Save
        </VBtn>
      </VCardActions>
    </VCard>
  </VDialog>
</VCard>

</template>

<style scoped>
/* 기존 스타일 수정 */
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
  margin-right: 1rem;
}

</style>