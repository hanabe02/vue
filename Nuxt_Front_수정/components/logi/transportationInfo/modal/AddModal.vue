<script lang="ts" setup>
const props = defineProps(['isAddDialogVisible'])
const emits = defineEmits(['add', 'close'])

const vehicleNumber = ref('')
const type = ref('')
const option = ref(['트럭', '선박', '비행기', '열차'])
const manufacturingCompany = ref('')
const yearOfManufacture = ref('')
const loadCapacity = ref('')
const maintenanceDate = ref('')
const operator = ref('')
const fuelType = ref('')
const transportationStatus = ref('')

const resetState = () => {
  vehicleNumber.value = ''
  type.value = ''
  manufacturingCompany.value = ''
  yearOfManufacture.value = ''
  loadCapacity.value = ''
  maintenanceDate.value = ''
  operator.value = ''
  fuelType.value = ''
  transportationStatus.value = ''
}

const close = () => {
  resetState()
  emits('close')
}

// 운송수단 추가하고 저장
const add = () => {
  const AddList = {
    vehicleNumber: vehicleNumber.value,
    type: type.value,
    manufacturingCompany: manufacturingCompany.value,
    yearOfManufacture: yearOfManufacture.value,
    loadCapacity: loadCapacity.value,
    maintenanceDate: maintenanceDate.value,
    operator: operator.value,
    fuelType: fuelType.value,
    transportationStatus: transportationStatus.value,
  }

  console.log(AddList)

  emits('add', AddList)
  resetState()
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
        style="margin-left: 20px; "
      >
        운송수단 추가
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="운송수단 추가">
      <VCardText>
        <VRow>
          <VCol cols="6">
            <AppTextField
              v-model="vehicleNumber"
              label="차량번호"
              placeholder="NEW"
              disabled
            />
          </VCol>
          <VCol cols="6">
            <AppAutocomplete
              v-model="type"
              label="종류"
              :items="option"
              placeholder="Select"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="manufacturingCompany"
              label="제조사"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="yearOfManufacture"
              label="제조년도"
              type="date"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="loadCapacity"
              label="적재용량"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="maintenanceDate"
              label="유지보수날짜"
              type="date"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="operator"
              label="현재운전자"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="fuelType"
              label="연료타입"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="transportationStatus"
              label="운송수단상태"
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
        <VBtn @click="add">
          Add
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
</template>
