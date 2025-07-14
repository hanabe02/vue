<script lang="ts" setup>
const props = defineProps(['isUpdateDialogVisible', 'selecteditem'])
const emits = defineEmits(['update', 'updateClose'])

const vehicleNumber = ref('')
const type = ref('')
const loadCapacity = ref('')
const maintenanceDate = ref('')
const operator = ref('')
const fuelType = ref('')
const transportationStatus = ref('')

const updateClose = () => {
  emits('updateClose')
}

const fetchData = async () => {
  console.log('hey', props.selecteditem)

  vehicleNumber.value = props.selecteditem.vehicleNumber
  type.value = props.selecteditem.type
  loadCapacity.value = props.selecteditem.loadCapacity
  maintenanceDate.value = props.selecteditem.maintenanceDate
  operator.value = props.selecteditem.operator
  fuelType.value = props.selecteditem.fuelType
  transportationStatus.value = props.selecteditem.transportationStatus
}

// 운송수단 수정하고 저장
const update = () => {
  const transList = {
    vehicleNumber: vehicleNumber.value,
    type: type.value,
    loadCapacity: loadCapacity.value,
    maintenanceDate: maintenanceDate.value,
    operator: operator.value,
    fuelType: fuelType.value,
    transportationStatus: transportationStatus.value,
  }

  console.log(transList)

  emits('update', transList)
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
        운송수단 수정
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="운송수단 상세수정">
      <VCardText>
        <VRow>
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
          @click="updateClose"
        >
          Close
        </VBtn>
        <VBtn @click="update">
          Update
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
</template>
