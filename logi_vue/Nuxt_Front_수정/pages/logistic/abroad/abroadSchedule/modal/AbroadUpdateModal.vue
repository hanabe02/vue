<script lang="ts" setup>
import axios from 'axios'
import { defineProps, ref, watch } from 'vue'
import { abroadStore } from '@/store/logi/abroad'

const props = defineProps(['selectedAbroadSchedule'])
const emit = defineEmits(['update'])

const isUpdateDialogVisible = ref(false)

const abroadScheduleCode = ref('')
const eventName = ref('')
const eventType = ref('')
const eventLocation = ref('')
const eventDate = ref('')
const eventExplanation = ref('')
const eventTarget = ref('')
const eventHost = ref() 
const eventState = ref('')


const updateClick = async () => {
  console.log('eeee', props.selectedAbroadSchedule);
    abroadScheduleCode.value = props.selectedAbroadSchedule.abroadScheduleCode
    eventName.value = props.selectedAbroadSchedule.eventName
    eventType.value = props.selectedAbroadSchedule.eventType
    eventLocation.value = props.selectedAbroadSchedule.eventLocation
    eventDate.value = props.selectedAbroadSchedule.eventDate
    eventExplanation.value = props.selectedAbroadSchedule.eventExplanation
    eventTarget.value = props.selectedAbroadSchedule.eventTarget
    eventHost.value = props.selectedAbroadSchedule.eventHost
    eventState.value = props.selectedAbroadSchedule.eventState
  }

// 수정하고 추가 로직
const update = async () => {
  const updateDataList
    = {
      abroadScheduleCode: abroadScheduleCode.value,
      eventName: eventName.value,
      eventType: eventType.value,
      eventLocation: eventLocation.value,
      eventDate: eventDate.value,
      eventExplanation: eventExplanation.value,
      eventTarget: eventTarget.value,
      eventHost: eventHost.value,
      eventState: eventState.value,
    }

  console.log("updateDataList:", updateDataList);

  await abroadStore().UPDATE_ABROADSCHEDULE_URL(updateDataList);

  isUpdateDialogVisible.value = false
  
  emit('update');
}

</script>

<template>
  <VDialog
    v-model="isUpdateDialogVisible"
    max-width="600"
  >
    <!-- Dialog Activator -->
    <template #activator="{ props }">
      <VBtn v-bind="props"
      @click="updateClick">
        행사 수정
      </VBtn>
    </template>

    <!-- Dialog close btn -->
    <DialogCloseBtn @click="isUpdateDialogVisible = !isUpdateDialogVisible" />

    <!-- Dialog Content -->
    <VCard title="행사 수정">
      <VCardText>
        <VRow>
          <VCol cols="6">
            <AppTextField
              v-model="abroadScheduleCode"
              label="행사코드"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="eventName"
              label="행사명"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="eventType"
              label="행사유형"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="eventLocation"
              label="위치"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="eventDate"
              label="날짜"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="eventExplanation"
              label="설명"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="eventTarget"
              label="대상"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="eventHost"
              label="주최자"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="eventState"
              label="상태"
            />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3">
        <VBtn
          variant="tonal"
          color="secondary"
          @click="isUpdateDialogVisible = false"
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

<style scoped>
.address-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}
</style>
