<script lang="ts" setup>
import { VDataTable } from "vuetify/labs/VDataTable";
import { ref, watch } from 'vue';
import { clientDeliveryStore } from '@/store/logi/clientDelivery'

const isAddDialogVisible = ref(false);
const infodata = ref([]);
const selectData = ref([]);
const clientDeliveryData = ref([]);
const deliveryNumber = ref('');
const itemName = ref('');

const props = defineProps([])
const emits = defineEmits(['add'])

const deliveryInfoHeaders = [
  { title: '배송번호', key: 'deliveryNumber', width: 120 },
  { title: '출고번호', key: 'outputNumber', width: 120 },
  { title: '상품명', key: 'itemName', width: 120 },
  { title: '이름', key: 'name', width: 120 },
  { title: '연락처', key: 'tel', width: 120 },
  { title: '배송일자', key: 'deliveryDate', width: 120 },
  { title: '출발지', key: 'warehouseCode', width: 120 },
  { title: '도착지', key: 'endingRoute', width: 120 },
  { title: '배송상태', key: 'deliveryStatus', width: 120 }
]


// 반품 요청
const addReturnInfoClick = async () => {

    await clientDeliveryStore().GET_CLIENT_DELIVERY_URL();
    clientDeliveryData.value = clientDeliveryStore().getClientDeliveryInfo;

    console.log('clientDeliveryData.value:', clientDeliveryData.value);

    //배송완료인 데이터만 가져오기
    if (clientDeliveryData.value && Array.isArray(clientDeliveryData.value)) {
            const completedDeliveries = clientDeliveryData.value.filter(
                  (delivery:any) => delivery.deliveryStatus === '배송완료'
            );

        infodata.value = completedDeliveries;
        console.log('infodata.value:', infodata.value);

        selectData.value = []; //초기화

    } else {
        console.error('Error: Unable to retrieve valid clientDeliveryData.clientDeliveryList');
    }

}

//변경된 deliveryNumber, itemName 감지
watch(() => selectData.value, (newSelectData) => {
  const deliveryNumberList = newSelectData.map((item:any) => item.deliveryNumber);
  const itemNameList = newSelectData.map((item:any) => item.itemName);

  deliveryNumber.value = deliveryNumberList.join(',');
  itemName.value = itemNameList.join(',');

  console.log("deliveryNumberList:", deliveryNumberList);
  console.log("itemNameList:", itemNameList);
  console.log("deliveryNumber:", deliveryNumber.value);
  console.log("itemName:", itemName.value);
});

//현재 체크된 배송목록 반품요청 
const returnReqClick = async() => {
    const AddList = {
        deliveryNumber: deliveryNumber.value,
        itemName: itemName.value,
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
        width="1300">
            <!-- Activator -->
        <template #activator="{ props }">
            <VBtn 
                @click="addReturnInfoClick" 
                v-bind="props"
                style="margin-right: 20px; margin-top: 35px"

                >반품 요청
             </VBtn>
        </template>

        <!-- Dialog Content -->
         <VCard>
            <VCardText>
            <p class="text-2xl" style="font-size: 22px;">
                 수령된물품 반품요청
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
                            현재 체크된 배송물품 반품요청
                        </VBtn>
                    </VCol>  
                </VRow>
                     
                <VDataTable
                    v-model="selectData"
                    :headers="deliveryInfoHeaders" 
                    :items="infodata" 
                    :items-per-page="5"
                    item-value="deliveryNumber"
                    return-object
                    show-select
                 />
            </VCardText>
        </VCard>
    </VDialog>
</template>

<style scoped>
</style>
