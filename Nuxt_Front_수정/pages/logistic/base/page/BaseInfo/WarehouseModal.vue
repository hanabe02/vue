<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import { defineEmits, defineProps, ref } from 'vue'

import axios from 'axios'

const propz = defineProps(['isDialogVisible'])
const emits = defineEmits(['closeModal', 'insertWare', 'warehouseCode1'])
const warehouseCode1 = ref('')
const warehouseName1 = ref('')
const warehouseUseOrNot1 = ref(false)
const description1 = ref('')

const closeModal = () => {
  emits('closeModal')
}

const insertWare = () => {
  const warehouseCode = warehouseCode1.value
  const warehouseName = warehouseName1.value
  const warehouseUseOrNot = warehouseUseOrNot1.value
  const description = description1.value

  emits('insertWare', { warehouseCode, warehouseName, warehouseUseOrNot, description })
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
        창고 추가
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="User Profile">
      <VCardText>
        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="warehouseCode1"
              label="창고 코드"
              placeholder="창고 코드"
              disabled
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <VRadioGroup v-model="warehouseUseOrNot1">
              <VRadio
                label="예"
                value="Y"
              />
              <VRadio
                label="아니오"
                value="N"
              />
            </VRadioGroup>
          </VCol>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="description1"
              label="창고명"
              placeholder="창고명"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="warehouseName1"
              label="창고 설명"
              persistent-hint
              placeholder="창고 설명"
            />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3">
        <VBtn
          variant="tonal"
          color="secondary"
          @click="closeModal"
        >
          Close
        </VBtn>
        <VBtn @click="insertWare">
          Save
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
</template>
