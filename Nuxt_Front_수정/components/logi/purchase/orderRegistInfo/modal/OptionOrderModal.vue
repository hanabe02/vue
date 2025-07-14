<script lang="ts" setup>
import { purchaseStore } from '@/store/logi/purchase'
import { defineProps, defineEmits, ref, toRaw } from "vue";

const isDialogVisible = ref(false);
const itemCode = ref('');
const number = ref('');
const itemName = ref('');
const infodata = ref([]);

//부모 컴포넌트에서 받아온 데이터 
const propz = defineProps(['selectData']);
const emits = defineEmits(['updateLogList']);

// 옵션 다이얼로그가 열릴 때마다 품목 코드 목록을 업데이트
const optionOrderClick = () => {
  const selectData = toRaw(propz.selectData);

  if(!selectData || selectData.length === 0) {
    alert ("임의발주 할 행을 선택하세요!");

    isDialogVisible.value = false;
    return;
  }

    isDialogVisible.value = true;  
    console.log("selectData:", toRaw(propz.selectData));
    // const selectData = toRaw(propz.selectData);

    itemName.value = getItemNameValue();
    itemCode.value = getItemCodeValue();
    // number 초기화
    number.value = '';

    console.log("itemCode:", itemCode);
    console.log("itemName:", itemName);

};

//itemName에 품목명 할당
const getItemNameValue = () => {
  return toRaw(propz.selectData).map((item: { itemName: string }) => item.itemName).join(', ');
}

//itemCode에 품목코드 할당
const getItemCodeValue = () => {
  return toRaw(propz.selectData).map((item: { itemCode: string }) => item.itemCode).join(', ');
}

const handleSubmitClick = async () => {
    try {
      const itemCodeValue = itemCode.value;
      const itemAmountValue = number.value;

      await purchaseStore().FETCH_OPTION_ORDER(itemCodeValue, itemAmountValue);
      infodata.value = purchaseStore().OptionOrder;

      console.log("itemCode:", itemCodeValue);
      console.log("itemAmount:", itemAmountValue);

      isDialogVisible.value = true; // Dialog를 보임
      alert("임의발주가 성공적으로 완료되었습니다!");
        
   } catch (error) {        
    console.error('데이터 가져오기 에러:', error);
    return [];
  }
  close();
};

const close = () => {
  isDialogVisible.value = false; 
}

</script>

<template>
    <VDialog v-model="isDialogVisible" width="500">
        <!-- Activator -->
        <template #activator="{ props }">
            <VBtn 
                v-bind="props"
                class="button" 
                color="primary" 
                @click="optionOrderClick" 
                >임의발주
            </VBtn>
        </template>

        <!-- Dialog Content -->
        <VCard>
            <VCardTitle>임의 발주</VCardTitle>

            <VCardText>
              <VRow>
                <VCol cols="10" sm="6" md="5">
                  <VTextField
                    v-model="itemName"
                    label="품목명"
                    readonly
                  />
                </VCol>
                
                <VCol cols="10" sm="6" md="7">
                  <!-- <AppTextField
                    v-model="number"
                    label="수량"
                    placeholder='발주할 수량 입력해주세요.'
                    items='type'
                  /> -->

                  <VTextField
                    v-model="number"
                    label='임의발주 넣을 수량 입력하세요.'
                  />

                </VCol>
              </VRow>

                <VRow class="justify-end">
                  <div class="d-flex gap-1 pa-3 flex-wrap">
                    <VCol>
                      <VBtn
                          variant="outlined"
                          color="primary" 
                          @click="isDialogVisible = false"
                          >Cancel
                      </VBtn>
                  </VCol>

                  <VCol>
                      <VBtn
                          color="primary" 
                          @click="handleSubmitClick" 
                          >Submit
                      </VBtn>
                    </VCol>
                  </div>
                </VRow>

           </VCardText>
        </VCard>
    </VDialog>
</template>

<style>

</style>