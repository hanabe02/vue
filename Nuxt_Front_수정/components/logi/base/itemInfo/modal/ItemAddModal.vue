<script lang="ts" setup>
const props = defineProps(['isAddDialogVisible'])
const emits = defineEmits(['save', 'close'])

const itemCode = ref('')
const itemName = ref('')
const itemGroupCode = ref('')
const itemClassification = ref('')
const unitOfStock = ref('')
const lossRate = ref('')
const leadTime = ref('')
const standardUnitPrice = ref('')
const description = ref('')

const close = () => {
  emits('close')
}

// 코드 추가하고 저장
const save = () => {
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
    status: 'INSERT',
  }

  console.log(batchList)

  emits('save', batchList)
}
</script>

<template>
  <VDialog
    v-model="props.isAddDialogVisible"
    max-width="600"
  >
    <!-- Dialog Activator -->
    <template #activator="{ props }">
      <VBtn
        v-bind="props"
        style="margin-right: 20px; margin-left: 20px;"
      >
        품목 추가
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="품목 추가">
      <VCardText>
        <VRow>
          <VCol cols="6">
            <AppTextField
              v-model="itemCode"
              label="품목코드"
              items="type"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="itemName"
              label="품목명"
              persistent-hint
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
          @click="close"
        >
          Close
        </VBtn>
        <VBtn @click="save">
          Save
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
</template>
