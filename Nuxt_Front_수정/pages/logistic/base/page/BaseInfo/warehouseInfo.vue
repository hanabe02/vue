<script setup lang="ts">
import { ref,watch } from 'vue';
import { VDataTable } from 'vuetify/labs/VDataTable'
import axios from 'axios';
import NewwarehouseModal from './WarehouseModal.vue'


const wareInfo=ref([]);
const wareInfodetail=ref([]);
const selectRow=ref([]);
const isDialogVisible = ref(false);



const headers = [
  { title: '창고 코드', sortable: true,key: 'warehouseCode' , width:150},
  { title: '창고명', key: 'warehouseName', width:150 },
  { title: '창고 사용여부', key: 'warehouseUseOrNot', width:150 },
  { title: '비고', key: 'description', width:150 },
  // { title: 'STATUS', key: 'status', width:150 },

]

const headers1 = [
  { title: '창고코드',sortable: true, key: 'warehouseCode' , width:120},
  { title: '자재코드', key: 'itemCode', width:150 },
  { title: '자재명', key: 'itemName', width:180 },
  { title: '단위', key: 'unitOfStock', width:100 },
  { title: '안전재고량', key: 'safetyAllowanceAmount', width:120 },
  { title: '가용재고량', key: 'stockAmount', width:120 },
  // { title: '전체재고량', key: 'totalStockAmount' , width:120},
]


const wareInfoSearch=async()=>{
    try {
    const response = await axios.get('http://localhost:8282/logi/base/warehouseInfo');
    console.log("getcompanyInfo", response.data);
    wareInfo.value= response.data.gridRowJson; // companyInfo 배열을 반환
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }

}

const insertWare=async({warehouseCode,warehouseName,warehouseUseOrNot,description}:any)=>{
    console.log("warehouseCode.value",warehouseCode,warehouseName,warehouseUseOrNot,description)
    const batchList={
        description:description,status:"INSERT",warehouseCode:"",warehouseName:warehouseName,warehouseUseOrNot:warehouseUseOrNot
    }
    console.log("batchList",batchList)
    try {
    const response = await axios.post('http://localhost:8282/logi/base/warehousebatchListProcess',[batchList]);
        console.log("response",response.data)
        alert("등록완료.");
        wareInfoSearch();
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
    isDialogVisible.value=false;
}

const closeModal=()=>{
    isDialogVisible.value=false;
}

const wareInfoDelete = async () => {
  // 사용자에게 확인을 묻는 다이얼로그
  console.log("select",selectRow._rawValue[0].replace(/\[|\]/g, ''))
  console.log("wareInfo",wareInfo)
  const targetWarehouseCode=selectRow._rawValue[0].replace(/\[|\]/g, '')
  const userConfirmed = confirm("삭제하시겠습니까?");
  const targetObject = wareInfo._rawValue.find(obj => obj.warehouseCode === targetWarehouseCode);
  console.log("targetObject",targetObject)
  if (userConfirmed) {
    const selectedRow = selectRow.value;

// status를 'delete'로 변경
targetObject.status = 'DELETE';

// deleteList에 배열로 감싸서 할당
const deleteList = [targetObject];

console.log('Selected Row:', selectedRow);
console.log('deleteList:', deleteList);


    try {
      const response = await axios.post(
        'http://localhost:8282/logi/base/warehousebatchListProcess', deleteList 
      );

      console.log("getcompanyInfo", response.data);
      alert("삭제완료.");
      wareInfoSearch();
      selectRow.value=[]
    } catch (error) {
      console.error('Error fetching data:', error);
      return [];
    }
  }
};


// const detailRow=async(item:any,row:any)=>{
//     console.log("row",row.item.warehouseCode)
//     const warehouseCode=row.item.warehouseCode
//     try {
//     const response = await axios.get('http://localhost:8282/logi/purchase/warehouseStockList',{
//         params:{warehouseCode}});
//         console.log("response",response.data)
//         wareInfodetail.value=response.data.gridRowJson
//         selectRow.value=row.item
//   } catch (error) {
//     console.error('Error fetching data:', error);
//     return [];
//   }

// }

const wareInfoDetailSearch=async()=>{
  console.log("select",selectRow._rawValue)
  const warehouseCode = selectRow._rawValue[0].replace(/\[|\]/g, '');
    try {
    const response = await axios.get('http://localhost:8282/logi/purchase/warehouseStockList',{
        params:{warehouseCode}});
        console.log("response",response.data)
        wareInfodetail.value=response.data.gridRowJson
        // selectRow.value=row.item
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
}


</script>

<template>
    <Vcard  class="dd-flex" title="창고 조회">

    <VRow> <VCol cols="12" md="2" offset-md="5"></VCol>
      <VCol cols="12" md="5" class="d-flex gap-3" >
    <VBtn @click="wareInfoSearch">
      창고 조회
    </VBtn>

  
    <VBtn @click="wareInfoDelete">
      창고 삭제
    </VBtn>

    <VBtn @click="wareInfoDetailSearch">
      창고 상세 조회
    </VBtn>
        <NewwarehouseModal
        v-model="isDialogVisible"
        @insertWare="insertWare"
        @closeModal="closeModal"
        />
      </VCol>
    </VRow>


  <VDataTable
    :headers="headers"
    :items="wareInfo"
    :items-per-page="10"
    v-model="selectRow"
    height="250"
    show-select
    item-value="warehouseCode"
    @click:row="detailRow"
  >

  </VDataTable>
</Vcard>
<Vcard>
  
<VDataTable
    :headers="headers1"
    :items="wareInfodetail"
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