<script lang="ts" setup>
import axios from 'axios'
import { itemStore } from '@/store/logi/item'

const props = defineProps(['isUpdateDialogVisible', 'selecteditem', 'search'])
const emits = defineEmits(['update', 'cancel', 'clear'])

const itemGroupCode = ref('')
const itemGroupName = ref('')
const description = ref('')

const cancel = () => {
  emits('cancel')
}

const setting = () => {
  itemGroupCode.value = props.selecteditem.itemGroupCode
  itemGroupName.value = props.selecteditem.itemGroupName
  description.value = props.selecteditem.description
}

// 코드 추가하고 저장
const update = async () => {
  const batchList = {
    itemGroupCode: itemGroupCode.value,
    itemGroupName: itemGroupName.value,
    description: description.value,
  }

  console.log('수정했니?', batchList)

  await itemStore().UPDATE_ITEMGROUP_INFO(batchList)

  emits('clear')

  cancel()
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
        @click="setting"
      >
        품목군 수정
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="품목군 수정">
      <VCardText>
        <VRow>
          <VCol cols="6">
            <AppTextField
              v-model="itemGroupCode"
              label="품목군코드"
              items="type"
              disabled
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
