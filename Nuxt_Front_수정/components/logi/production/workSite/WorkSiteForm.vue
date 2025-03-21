<script setup lang="ts">
import { ref } from 'vue'
import { VDataTable } from 'vuetify/labs/VDataTable'
import { productionStore } from '@/store/logi/production'

const infodata = ref([]);
const infodataProduct = ref([]);

const workSiteHeaders = [
  { title: '생산공정 코드', key: 'productionProcessCode' },
  { title: '작업장 코드', key: 'workSiteCode' },
  { title: '작업장명', key: 'workSiteName' }
]

const workPlaceDetailColumns = [
  { title: "작업지시번호", key: "workOrderNo", width: 90},
  { title: "품목코드", key: "itemCode", width: 80},
  { title: "품목명", key: "itemName", width: 80},
  { title: "상황", key: "progress", width: 250},
  { title: "생산공정코드", key: "productionProcessCode", width: 60},
  { title: "생산공정명", key: "productionProcessName", width: 60},  
  { title: "작업장명", key: "workSiteName", width: 100},
  { title: "날짜", key: "workDate", width: 150},
]


//작업장 조회
const workSiteListClick = async () => {
  try {
    await productionStore().FETCH_WORKPLACE();
    infodata.value = productionStore().WorkSiteList;
  
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};


//작업장 로그 조회 
const selectRow = async (code: any, row: { item: { productionProcessCode: string, workSiteName: string }, }) => {
  try {
    const { productionProcessCode, workSiteName } = row.item; 
  
    console.log('나와1', productionProcessCode);
    console.log('나와2', workSiteName);

    await productionStore().FETCH_WORKPLACE_LOG(productionProcessCode, workSiteName);
    infodataProduct.value = productionStore().WorkSiteLogList;

  }
  catch (error) {
    console.error('Error fetching data:', error)
    return []
  }
};

</script>

<template>
  <VRow>
    <VCol cols="12">
      <!-- 👉 작업장 조회 -->
        <AppCardActions>
           <template #title>
             <div style="font-size: 22px;">작업장 조회</div>
           </template>
          
                <!-- 조회 버튼 -->
            <VCardText>
              <VRow class="mb-4">
                <VCol>
                  <div class="demo-space-x">
                  <VBtn color="primary" @click="workSiteListClick"> 
                      작업장 조회
                  </VBtn>
                  </div>
                </VCol>
              </VRow>
              
              <!-- 테이블 내용 -->
              <VDataTable
                :headers="workSiteHeaders"
                :items="infodata"
                @click:row="selectRow"
              />
            </VCardText>
        </AppCardActions>
    </VCol>

    <VCol cols="12">
      <!-- 👉 작업장 로그 조회 -->
      <AppCardActions>
        <template #title>
          <div style="font-size: 22px;">작업장 로그 조회</div>
        </template>
       
        <VCardText> 
          <VDataTable
            :headers="workPlaceDetailColumns"
            :items="infodataProduct"
          />
        </VCardText>
      </AppCardActions>
    </VCol>
  </VRow>
</template>