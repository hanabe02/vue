<script lang="ts" setup>
import { VDataTable } from "vuetify/labs/VDataTable";
import { ref } from "vue";
import { defineProps, defineEmits } from "vue";
import axios from 'axios';


const propz = defineProps(["selectData","isDialogVisible","spreadData"]);
const emits = defineEmits(["openModal","closeModal",]);

const headers = [
{ title: '품목명', key: 'itemName', width:150 },
{ title: '품목코드', key: 'itemCode', width:150 },
  { title: '투입량',sortable: false, key: 'inputAmount' , width:120},
  { title: '품목분류', key: 'itemClassification', width:150 },
  { title: 'MRP 취합 번호', key: 'mrpGatheringNo', width:180 },
  { title: '발주/작업지시기한', key: 'orderDate', width:180 },
  { title: '발주/작업지시기한', key: 'requiredDate', width:180 },
  { title: '재고량', key: 'stockAfterWork', width:120 },
  { title: '단위', key: 'unitOfMrp', width:100 },
  { title: '주문수량', key: 'requiredAmount', width:150 },

]




const openModal = () => {
  emits("openModal");
};

const closeModal=()=>{
    emits("closeModal");
}
</script>

<template>
  <VDialog
   v-model="propz.isDialogVisible" 
    scrollable
    max-width="800"
    content-class="scrollable-dialog"
  >

  <template #activator="{ props }">
      <VBtn v-bind="props" @click="openModal">
        외주자재 출고 모의 전개
      </VBtn>
    </template>

    <VCard>

      <VCardText class="d-flex justify-end flex-wrap gap-3 pt-5">

        <VCard title="해당품목 모의전개 결과">
      <VContainer>
        <VDataTable
          :headers="headers"
          :items="spreadData"
        />
        <VCardText class="d-flex justify-end">
          <VBtn @click="closeModal"> Ok </VBtn>
        </VCardText>
      </VContainer>
    </VCard>

      </VCardText>
    </VCard>
  </VDialog>
</template>

<style lang="scss">
.scrollable-dialog{
  overflow: visible !important;
}


</style>
