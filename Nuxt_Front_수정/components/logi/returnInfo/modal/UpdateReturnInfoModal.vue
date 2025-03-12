<script lang="ts" setup>
import { ref, defineProps, defineEmits, watch } from 'vue';

const props = defineProps(['isUpdateDialogVisible','AddList'])
const emits = defineEmits(['update', 'cancel'])

const returnNo = ref('');
const deliveryNumber = ref('');
const itemName = ref('');
const itemCode = ref('');
const itemCodeOptions = ref(['DK-01', 'DK-02', 'DK-AP01', 'DK-AP02'])
const rcvdDate = ref('');
const returnReason = ref('');
const reasonOptions = ref(['상품불량 또는 파손', '잘못된 상품 수령', '고객변심 또는 착오주문']);
const returnQty = ref('');
const returnProcessor = ref('');
const returnStat = ref('');
const statusOptions = ref(['요청진행', '요청취소', '처리완료']); 
const returnNote = ref('');

const resetState = () => {
    returnNo.value = ''
    deliveryNumber.value = ''
    itemName.value = ''
    itemCode.value = ''
    rcvdDate.value = ''
    returnReason.value = ''
    returnQty.value = ''
    returnProcessor.value = ''
    returnStat.value = ''
    returnNote.value = ''
}

const cancel = () => {
  resetState();
  emits('cancel');
}

// AddList가 변경될 때마다 실행되는 로직
watch(() => props.AddList, (newValue) => {
  if (newValue) {
    deliveryNumber.value = newValue.deliveryNumber;
    itemName.value = newValue.itemName;

    console.log("deliveryNumber.value", deliveryNumber.value);
    console.log("itemName.value", itemName.value);
  }
});


// 반품 요청 추가하고 저장 
const update = () => {
  const updateList = {
    returnNo: returnNo.value,
    deliveryNumber: deliveryNumber.value,
    itemName: itemName.value,
    itemCode: itemCode.value,
    rcvdDate: rcvdDate.value,
    returnReason: returnReason.value,
    returnQty: returnQty.value,
    returnProcessor: returnProcessor.value,
    returnStat: returnStat.value,
    returnNote: returnNote.value,
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
        style="margin-right: 20px; margin-top: 35px"
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
                v-model="returnNo"
                label="반품번호"
                placeholder="NEW"
                disabled
              />
            </VCol>

            <VCol cols="6">
              <AppTextField
                v-model="deliveryNumber"
                label="배송번호"
                disabled
              />
            </VCol>

            <VCol cols="6">
              <AppTextField
                v-model="itemName"
                label="품목명"
              />
            </VCol>

            <VCol cols="6">
              <AppAutocomplete
                v-model="itemCode"
                label="품목코드"
                :items="itemCodeOptions"
                placeholder="품목코드"
              />
            </VCol>

            <VCol cols="6">
              <AppTextField
                v-model="rcvdDate"
                label="수령날짜"
                type="date"
              />
            </VCol>

            <VCol cols="6">
              <AppAutocomplete
                v-model="returnReason"
                label="반품사유"
                :items="reasonOptions"
                placeholder="반품사유를 선택해주세요"
              />
            </VCol>

            <VCol cols="6">
              <AppTextField
                v-model="returnQty"
                label="반품수량"
              />
            </VCol>

            <VCol cols="6">
              <AppTextField
                v-model="returnProcessor"
                label="반품처리자"
              />
            </VCol>

            <VCol cols="6">
              <AppAutocomplete
                v-model="returnStat"
                label="반품처리상태"
                :items="statusOptions"
                placeholder="반품처리상태 선택"
              />
            </VCol>

            <VCol cols="6">
              <AppTextField
                v-model="returnNote"
                label="비고"
                placeholder="추가 참고사항"
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
