<script lang="ts" setup>
import { VDataTable } from "vuetify/labs/VDataTable";
import { ref, watch } from "vue";
import { defineProps, defineEmits } from 'vue';
import axios from 'axios';

const infodata=ref([]);
const isDialogVisible = ref(false);
const selectedRadio = ref('IT-CI');

const propz = defineProps(["isDialogVisible", "radioGroup1"]);
const emits = defineEmits(["searchItem","selectRow","comInfoSave","dialogClose"]);

const headers = [
  { title: '상세코드', sortable: false, key: 'detailCode' , width:120},
  { title: '상세코드이름', key: 'detailCodeName', width:150 },
]

//검색 모달 창 데이터
const searchItem = async() => {
  console.log("작동작동");
  const itemClassificationCondition = selectedRadio.value;
  console.log("bomModal1.itemClassificationCondition:", itemClassificationCondition);
    
  try {
    const response = await axios.get('http://localhost:8282/sys/findPayStepCodeDetailList',{
      params: { itemClassificationCondition }
    });
    
    isDialogVisible.value = true;
    console.log("searchItem", response.data);
    infodata.value= response.data
    
    return infodata;
  
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }

}

//역전개시 검색 가능하게 라디오 버튼 값이 변경되게 값을 가져옴 
watch(() => propz.radioGroup1, () => {
  // 부모 컴포넌트에서 라디오 버튼 값이 변경되면
  // selectedRadio 값을 업데이트하고 검색 수행
  selectedRadio.value = propz.radioGroup1;
  searchItem();
});

const selectRow = (item:any,row:any) => {
  console.log("row",row);
  emits("selectRow", { item, row });
}

const dialogClose = () => {
    emits("dialogClose");
}

const comInfoSave = () => {
    emits("comInfoSave");
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
         @click="searchItem" 
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
              <VBtn @click="dialogClose"> Cancel </VBtn>
              <VBtn @click="comInfoSave"> Save </VBtn>
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
