<script lang="ts" setup>
import { VDataTable } from "vuetify/labs/VDataTable";
import { ref } from 'vue';
import { returnStore } from '@/store/logi/return';

const isAddDialogVisible = ref(false);
const infodata = ref([]);
const selectData = ref([]);
const returnList = ref([]);
const returnNo = ref('');
const itemName = ref('');
const itemCode = ref('');
const returnQty = ref('');

const props = defineProps([])
const emits = defineEmits(['add'])

const returnInfoHeaders = [
    { title: '반품번호', key: 'returnNo', width:100 }, 
    { title: '배송번호', key: 'deliveryNumber', width:120 }, 
    { title: '품목명', key: 'itemName', width:130 },
    { title: '품목코드', key: 'itemCode', width:100 },
    { title: '수령날짜', key: 'rcvdDate', width:100 },
    { title: '반품사유', key: 'returnReason', width:140 }, 
    { title: '반품수량', key: 'returnQty', width:70 },
    { title: '반품처리자', key: 'returnProcessor', width:110 },
    { title: '반품처리상태', key: 'returnStat', width:100 },
    { title: '비고', key: 'returnNote', width:100 },
]


// 반품요청 상태 ='처리완료'인 데이터만 조회
const addReturnStockClick = async () => {
    try {
        await returnStore().FETCH_GET_RETURN_LIST_FORSTOCK();
        returnList.value = returnStore().returnListForStock;
        console.log("returnList.value:", returnList.value);

        if (Array.isArray(returnList.value.returnListForStock)) {
            // 요청진행 상태인 데이터만 필터링하여 infodata에 업데이트
            infodata.value = returnList.value.returnListForStock.filter((item:any) => item.returnStat === '처리완료');
            console.log("infodata.value:", infodata.value);

            selectData.value = []; //초기화
        } else {
            console.error('Error: returnListData가 배열이 아닙니다.');
        }
    } catch (error) {
        console.error('Error fetching returnListForStock:', error);
    }
};


//변경된 returnNo, itemName, itemCode, returnQty 감지
watch(() => selectData.value, (newSelectData) => {
  const returnNoList = newSelectData.map((item:any) => item.returnNo);
  const itemNameList = newSelectData.map((item:any) => item.itemName);
  const itemCodeList = newSelectData.map((item:any) => item.itemCode);
  const returnQtyList = newSelectData.map((item:any) => item.returnQty);

  returnNo.value = returnNoList.join(',');
  itemName.value = itemNameList.join(',');
  itemCode.value = itemCodeList.join(',');
  returnQty.value = returnQtyList.join(',');

  console.log("returnNoList:", returnNoList);
  console.log("returnNo:", returnNo.value);
  console.log("itemNameList:", itemNameList);
  console.log("itemName:", itemName.value);
  console.log("itemCodeList:", itemCodeList);
  console.log("itemCode:", itemCode.value);
  console.log("returnQtyList:", returnQtyList);
  console.log("returnQty:", returnQty.value);
});

//현재 체크된 배송목록 반품요청 
const returnReqClick = async() => {
    const AddList = {
        returnNo: returnNo.value,
        rtrnItemName: itemName.value,
        rtrnItemCode : itemCode.value,
        returnQty : returnQty.value,
    }
   
    console.log("addModal.AddList:", AddList);
    
    isAddDialogVisible.value = false;

    emits('add', AddList);
}

const addCancel = () => {
    isAddDialogVisible.value = false;
}

</script>

<template>
     <VDialog 
        v-model="isAddDialogVisible" 
        width="1500">
            <!-- Activator -->
        <template #activator="{ props }">
            <VBtn 
                @click="addReturnStockClick" 
                v-bind="props"
                style="margin-right:5px; margin-top:5px"

                >반품재고입고 요청
             </VBtn>
        </template>

        <!-- Dialog Content -->
         <VCard>
            <VCardText>
            <p class="text-2xl" style="font-size: 22px;">
                 반품재고입고 요청
            </p>
                <VRow>
                    <VCol class="d-flex justify-end gap-4" >
                        <VBtn 
                            @click="addCancel"
                            class="mr-2 ml-5 mt-1"
                            variant="tonal"
                             >
                            취소
                        </VBtn>

                        <VBtn 
                            @click="returnReqClick"
                            class="mt-1 mr-13 mb-10"
                            >
                            입고 요청
                        </VBtn>
                    </VCol>  
                </VRow>
                     
                <VDataTable
                    v-model="selectData"
                    :headers="returnInfoHeaders" 
                    :items="infodata" 
                    :items-per-page="5"
                    item-value="returnNo"
                    return-object
                    show-select
                 />
            </VCardText>
        </VCard>
    </VDialog>
</template>

<style scoped>
</style>
