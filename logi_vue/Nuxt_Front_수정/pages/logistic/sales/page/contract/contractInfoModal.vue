<script lang="ts" setup>
import { VDataTable } from "vuetify/labs/VDataTable";
import { ref } from "vue";
import { defineProps, defineEmits } from "vue";
import axios from 'axios';
const infodata=ref([])

const propz = defineProps(["infodata","textFieldValue","isDialogVisible"]);
const emits = defineEmits(["selected","comInfoSave","dialogClose","cutomerSave","cutomerDelete"]);

const headers = [
  { title: '회사코드',sortable: false, key: 'customerCode' , width:120},
  { title: '회사명', key: 'customerName', width:150 },
]

const fetchData = async () => {
  try {
  const searchCondition='ALL';
  const workplaceCode=''
    const response = await axios.get('http://localhost:8282/hr/company/searchCustomer',{
      params: {
        searchCondition,workplaceCode
      },
    });
    console.log("searchCustomerList", response.data.gridRowJson);
    return response.data.gridRowJson; // companyInfo 배열을 반환
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
};


onMounted(async () => {
  infodata.value = await fetchData();
});

const dialogClose = () => {
  emits("dialogClose");
};

const comInfoSave = () => {
  emits("comInfoSave");
};


const cutomerSave = () => {
  emits("cutomerSave");
};

const cutomerDelete=()=>{
  emits("cutomerDelete");
}

const onRowSelected = (selected, a) => {
  
  console.log(selected);
  console.log(a.item);
  emits("selected", { selected, a });
};



</script>

<template>
  <VDialog
   v-model="propz.isDialogVisible" 
    scrollable
    max-width="350"
    content-class="scrollable-dialog"
  >

    <!-- Dialog Activator -->
    <template #activator="{ props }">
  <VRow>
  <VCol cols="12" md="3" offset-md="1">
    <v-text-field   
      v-bind="props"
      style="width: 150px"
      placeholder="회사코드"
      class="custom-text-field"
      :value="textFieldValue" @input="textFieldValue=$event.target.value"
      clearable
    ></v-text-field>
  </VCol>
  <VCol cols="12" md="3">
    <VBtn @click="cutomerSave">
      회사코드로 검색
    </VBtn>
  </VCol>
  <VCol cols="12" md="3">
    <VBtn @click="cutomerDelete">
      회사코드 삭제
    </VBtn>
  </VCol>
</VRow>
    </template>

    <VCard>

      <VCardText class="d-flex justify-end flex-wrap gap-3 pt-5">

        <VCard title="회계코드">
      <VContainer>
        <VDataTable
          :headers="headers"
          :items="infodata"
          :items-per-page="10"
          height="400"
          fixed-header 
          :key="(row) => row.id"
          @click:row="onRowSelected"
           class="custom-text-field"
        />
        <VCardText class="d-flex justify-end">
          <VBtn @click="dialogClose"> Cancle </VBtn>
          <VBtn @click="comInfoSave"> Save </VBtn>
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
