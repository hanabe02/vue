<script lang="ts" setup>
const props = defineProps(['isAddDialogVisible'])
const emits = defineEmits(['save', 'close'])

const itemGroupCode = ref('')
const itemGroupName = ref('')
const description = ref('')

const close = () => {
  emits('close')
}

// 코드 추가하고 저장
const save = () => {
  const batchList = {
    itemGroupCode: itemGroupCode.value,
    itemGroupName: itemGroupName.value,
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
        style="margin-right: 20px; margin-left: 20px"
      >
        품목군 추가
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="품목군 추가">
      <VCardText>
        <VRow>
          <VCol cols="6">
            <AppTextField
              v-model="itemGroupCode"
              label="품목군코드"
              items="type"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="itemGroupName"
              label="품목군명"
              persistent-hint
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
