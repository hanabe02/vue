<script lang="ts" setup>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps(['isUpdateDialogVisible','AddList'])
const emits = defineEmits(['update', 'cancel'])

const rtrnRecNo = ref('');
const returnNo = ref('');
const rtrnItemCode = ref('');
const rtrnItemName = ref('');
const returnQty = ref('');
const wrhsCode = ref('');
const wrhsName = ref('');
const recDate = ref('');
const wrhsCodeOptions = ref(['WHS-01', 'WHS-02', 'WHS-03', 'WHS-04']);
const wrhsNameOptions = ref(['서울 창고', '진주 창고', '사천 창고', '경산 창고']);

const resetState = () => {
    rtrnRecNo.value = ''
    returnNo.value = ''
    rtrnItemCode.value = ''
    rtrnItemName.value = ''
    returnQty.value = ''
    wrhsCode.value = ''
    wrhsName.value = ''
    recDate.value = ''
}

const cancel = () => {
  resetState();
  emits('cancel');
}

// AddList가 변경될 때마다 실행되는 로직
watch(() => props.AddList, (newValue) => {
  if (newValue) {
    returnNo.value = newValue.returnNo;
    rtrnItemName.value = newValue.rtrnItemName;
    rtrnItemCode.value = newValue.rtrnItemCode;
    returnQty.value = newValue.returnQty;

    console.log("returnNo.value", returnNo.value);
    console.log("rtrnItemName.value", rtrnItemName.value);
    console.log("rtrnItemCode.value", rtrnItemCode.value);
    console.log("returnQty.value", returnQty.value);
  }
});


// 반품 요청 추가하고 저장 
const update = () => {
  const updateList = {
    rtrnRecNo: rtrnRecNo.value,
    returnNo: returnNo.value,
    rtrnItemCode: rtrnItemCode.value,
    rtrnItemName: rtrnItemName.value,
    returnQty: Number(returnQty.value),
    recWrhsCode: wrhsCode.value,
    recWrhsName: wrhsName.value,
    recDate: recDate.value,
  }
  console.log("updateModal.updateList:", updateList);

  emits('update', updateList);
  resetState();
}

</script>

<template>
  <VDialog
    v-model="props.isUpdateDialogVisible"
    max-width="700"
  >
    <!-- Dialog Activator -->
    <template #activator="{ props }">
      <VBtn
        v-bind="props"
        style="margin-right:5px; margin-top:5px"
      >
       요청 등록
      </VBtn>
    </template>

      <!-- Dialog Content -->
      <VCard title="반품요청 등록">
        <VCardText>
          <VRow>
            <VCol cols="6">
              <AppTextField
                v-model="rtrnRecNo"
                label="반품재고입고번호"
                placeholder="NEW"
                disabled
              />
            </VCol>

            <VCol cols="6">
              <AppTextField
                v-model="returnNo"
                label="반품 번호"
                disabled
              />
            </VCol>

            <VCol cols="6">
              <AppTextField
                v-model="rtrnItemCode"
                label="반품 품목코드"
              />
            </VCol>

            <VCol cols="6">
              <AppTextField
                v-model="rtrnItemName"
                label="반품 품목명"
              />
            </VCol>

            <VCol cols="6">
              <AppAutocomplete
                v-model="wrhsCode"
                label="창고코드"
                :items="wrhsCodeOptions"
                placeholder="창고코드 선택"
              />
            </VCol>

            <VCol cols="6">
              <AppAutocomplete
                v-model="wrhsName"
                label="창고명"
                :items="wrhsNameOptions"
                placeholder="창고명 선택"
              />
            </VCol>

            <VCol cols="6">
              <AppTextField
                v-model="returnQty"
                label="반품 수량"
              />
            </VCol>

            <VCol cols="6">
              <AppTextField
                v-model="recDate"
                label="입고일자"
                type="date"
              />
            </VCol>

          </VRow>
        </VCardText>
  
        <VCardText class="d-flex justify-end flex-wrap gap-3">
          <VBtn
            variant="tonal"
            color="secondary"
            @click="cancel"
          >
            Cancel
          </VBtn>
  
          <VBtn @click="update">
            등록
          </VBtn>
        </VCardText>
      </VCard>
    </VDialog>
  </template>
