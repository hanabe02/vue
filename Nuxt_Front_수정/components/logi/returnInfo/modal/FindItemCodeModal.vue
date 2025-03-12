<script lang="ts" setup>
import { VDataTable } from "vuetify/labs/VDataTable";
import { ref } from "vue";
import { defineProps, defineEmits } from 'vue';
import axios from 'axios';

const isDialogVisible = ref(false);

const propz = defineProps(["isDialogVisible"]);
const emits = defineEmits(["selectRow","itemSelect","dialogCancel"]);

const headers = [
  { title: '품목코드', sortable: false, key: 'itemCode' , width:120},
  { title: '품목명', key: 'itemName', width:150 },
]

//검색 모달 창 데이터
const infodata = ref([
  { itemCode: 'DK-01', itemName: '디지털 카메라 NO.1' },
  { itemCode: 'DK-02', itemName: '디지털 카메라 NO.2' },
  { itemCode: 'DK-AP01', itemName: '카메라 본체 NO.1' },
  { itemCode: 'DK-AP02', itemName: '카메라 본체 NO.2' },
]);


const selectRow = (item:any,row:any) => {
  console.log("row",row);
  emits("selectRow", { item, row });
}

const dialogCancel = () => {
    emits("dialogCancel");
}

const itemSelect = () => {
    emits("itemSelect");
}

</script>

<template>
  <VDialog
    v-model="propz.isDialogVisible"
    max-width="600"
  >
    <!-- Dialog Activator -->
    <template #activator="{ props }">
      <VBtn 
         v-bind="props"  
         class="button small-button10" 
         > 검색
      </VBtn>
    </template>
    
    <VCard>
      <VCardText class="d-flex justify-end flex-wrap gap-3 pt-5">
        <VCard title="품목 코드">
           <VContainer>
             <VDataTable
              :headers="headers"
              :items="infodata"
              @click:row="selectRow"
            />
          
            <VCardText class="d-flex justify-end gap-4" >
              <VBtn @click="dialogCancel"> Cancel </VBtn>
              <VBtn @click="itemSelect"> Select </VBtn>
            </VCardText>
          </VContainer>
        </VCard>
     </VCardText>
    </VCard>
  
  </VDialog>
</template>

<style scoped>

.scrollable-dialog{
  overflow: visible !important;
}

.small-button10 {
  margin-right: 5px;
  margin-left: -5px;
  font-size: 14px; 
}

</style>
