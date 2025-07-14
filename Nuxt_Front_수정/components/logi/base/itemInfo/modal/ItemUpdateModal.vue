<script lang="ts" setup>
import axios from 'axios'

const props = defineProps(['isUpdateDialogVisible','selecteditem'])
const emits = defineEmits(['update', 'cancel'])

const itemCode = ref('')
const itemName = ref('')
const itemGroupCode = ref('')
const itemClassification = ref('')
const unitOfStock = ref('')
const lossRate = ref('')
const leadTime = ref('')
const standardUnitPrice = ref('')
const description = ref('')

const cancel = () => {
  emits('cancel')
}

const fetchData = async () => {
  console.log('1111111', props.selecteditem)

  itemCode.value = props.selecteditem.itemCode
  itemName.value = props.selecteditem.itemName
  itemGroupCode.value = props.selecteditem.itemGroupCode
  itemClassification.value = props.selecteditem.itemClassification
  unitOfStock.value = props.selecteditem.unitOfStock
  lossRate.value = props.selecteditem.lossRate
  leadTime.value = props.selecteditem.leadTime
  standardUnitPrice.value = props.selecteditem.standardUnitPrice
  description.value = props.selecteditem.description
}

// 코드 추가하고 저장
const update = () => {
  const batchList = {
    itemCode: itemCode.value,
    itemName: itemName.value,
    itemGroupCode: itemGroupCode.value,
    itemClassification: itemClassification.value,
    unitOfStock: unitOfStock.value,
    lossRate: lossRate.value,
    leadTime: leadTime.value,
    standardUnitPrice: standardUnitPrice.value,
    description: description.value,
  }

  console.log(batchList)

  emits('update', batchList)
}
</script>

<template>
  <VDialog
    v-model="props.isUpdateDialogVisible"
    max-width="600"
  >
    <!-- Dialog Activator -->
    <template #activator="{ props }">
      <VBtn
        v-bind="props"
        style="margin-right: 20px;"
        @click="fetchData"
      >
        품목 수정
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="품목 수정">
      <VCardText>
        <VRow>
          <VCol cols="6">
            <AppTextField
              v-model="itemCode"
              label="품목코드"
              items="type"
              disabled
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="itemName"
              label="품목명"
              disabled
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="itemGroupCode"
              label="품목군코드"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="itemClassification"
              label="품목분류"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="unitOfStock"
              label="단위"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="lossRate"
              label="손실율"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="leadTime"
              label="소요일"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="standardUnitPrice"
              label="표준단가"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="description"
              label="비고"
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
          Close
        </VBtn>
        <VBtn @click="update">
          Save
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
</template>
