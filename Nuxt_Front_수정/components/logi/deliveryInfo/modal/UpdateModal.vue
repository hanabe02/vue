<script lang="ts" setup>
const props = defineProps(['isUpdateDialogVisible', 'selecteditem'])
const emits = defineEmits(['updateClose', 'update'])
const outputNumber = ref('')
const deliveryNumber = ref('')
const itemName = ref('')
const name = ref('')
const tel = ref('')
const deliveryDate = ref('')
const warehouseCode = ref('')
const endingRoute = ref('')
const deliveryStatus = ref('')
const option = ref(['배송준비중', '배송중', '배송완료'])

const resetState = () => {
  outputNumber.value = ''
  deliveryNumber.value = ''
  itemName.value = ''
  name.value = ''
  tel.value = ''
  deliveryDate.value = ''
  warehouseCode.value = ''
  endingRoute.value = ''
  deliveryStatus.value = ''
}

const updateClose = () => {
  resetState()
  emits('updateClose')
}

const fetchData = async () => {
  console.log('hey', props.selecteditem)

  outputNumber.value = props.selecteditem.outputNumber
  deliveryNumber.value = props.selecteditem.deliveryNumber
  itemName.value = props.selecteditem.itemName
  name.value = props.selecteditem.name
  tel.value = props.selecteditem.tel
  deliveryDate.value = props.selecteditem.deliveryDate
  warehouseCode.value = props.selecteditem.warehouseCode
  endingRoute.value = props.selecteditem.endingRoute
  deliveryStatus.value = props.selecteditem.deliveryStatus
}

const update = async () => {
  const addData = {
    outputNumber: outputNumber.value,
    deliveryNumber: deliveryNumber.value,
    itemName: itemName.value,
    name: name.value,
    tel: tel.value,
    deliveryDate: deliveryDate.value,
    warehouseCode: warehouseCode.value,
    endingRoute: endingRoute.value,
    deliveryStatus: deliveryStatus.value,
  }

  console.log('ttttt', deliveryNumber.value)

  console.log('addData', addData)

  emits('update', addData)

  resetState()
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
        style="margin-left: 20px; "
        @click="fetchData"
      >
        배송상세수정
      </VBtn>
    </template>

    <!-- Dialog close btn -->
    <DialogCloseBtn @click="updateClose" />

    <!-- Dialog Content -->
    <VCard title="배송등록">
      <VCardText>
        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="outputNumber"
              label="출고번호"
              disabled
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="deliveryNumber"
              label="배송번호"
              placeholder="NEW"
              disabled
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="itemName"
              label="상품명"
              persistent-hint
              disabled
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="name"
              label="이름"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="tel"
              label="연락처"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="deliveryDate"
              label="배송일자"
              type="date"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="warehouseCode"
              label="출발지"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="endingRoute"
              label="도착지"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppAutocomplete
              v-model="deliveryStatus"
              label="배송상태"
              :items="option"
            />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3">
        <VBtn
          variant="tonal"
          color="secondary"
          @click="updateClose"
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

<style>
#myButton {
    display: none
}
</style>
