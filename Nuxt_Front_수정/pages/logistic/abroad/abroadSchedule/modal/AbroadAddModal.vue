<script lang="ts" setup>
const propz = defineProps(['isDialogVisible'])

const emits = defineEmits(['cancelModal', 'saveList'])
const abroadScheduleCode = ref('')
const eventName = ref('')
const eventType = ref('')
const eventLocation = ref('')
const eventDate = ref('')
const eventExplanation = ref('')
const eventTarget = ref('')
const eventHost = ref()
const eventState = ref('')


const cancelModal = () => {
  emits('cancelModal')
}

// 행사 추가하고 저장
const saveList = () => {
  const menudata = {
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

  console.log(menudata)
  emits('saveList', menudata)
}
</script>

<template>
  <VDialog
    v-model="propz.isDialogVisible"
    max-width="600"
  >
    <!-- Dialog Activator -->
    <template #activator="{ props }">
      <VBtn v-bind="props">
        행사 추가
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="행사 추가">
      <VCardText>
        <VRow>
          <VCol cols="12">
            <AppTextField
              v-model="abroadScheduleCode"
              label="행사코드"
              placeholder="abroadScheduleCode"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="eventName"
              label="행사명"
              placeholder="eventName"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="eventType"
              label="행사유형"
              placeholder="eventType"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="eventLocation"
              label="위치"
              placeholder="eventLocation"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="eventDate"
              label="날짜"
              placeholder="eventDate"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="eventExplanation"
              label="설명"
              placeholder="eventExplanation"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="eventTarget"
              label="대상"
              placeholder="eventTarget"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="eventHost"
              label="주최자"
              placeholder="eventHost"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="eventState"
              label="상태"
              placeholder="eventState"
            />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3">
        <VBtn
          variant="tonal"
          color="secondary"
          @click="cancelModal"
        >
          Cancel
        </VBtn>
        <VBtn @click="saveList">
          OK
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
</template>
