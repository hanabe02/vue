<script lang="ts" setup>
import axios from 'axios'
import { codeStore } from '@/store/logi/code'

const props = defineProps(['isUpdateDialogVisible', 'selecteditem', 'search'])
const emits = defineEmits(['update', 'cancel'])

const divisionCodeNo = ref('')
const codeType = ref('')
const divisionCodeName = ref('')
const codeChangeAvailable = ref('')
const description = ref('')

const type = ref(['공통', '회계', '구매', '영업'])
const items = ref(['변경가능', '변경불가능'])

const cancel = () => {
  emits('cancel')
}

const fetchData = async () => {
  divisionCodeNo.value = props.selecteditem.divisionCodeNo
  codeType.value = props.selecteditem.codeType
  divisionCodeName.value = props.selecteditem.divisionCodeName
  codeChangeAvailable.value = props.selecteditem.codeChangeAvailable
  description.value = props.selecteditem.description
}

// 코드 수정하고 저장
const update = async () => {
  const codeData = {
    divisionCodeNo: divisionCodeNo.value,
    codeType: codeType.value,
    divisionCodeName: divisionCodeName.value,
    codeChangeAvailable: codeChangeAvailable.value,
    description: description.value,
  }

  console.log('tqttqtqtqt', codeData)

  await codeStore().UPDATE_CODE_INFO(codeData)

  emits('update')

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
        @click="fetchData"
      >
        코드 수정
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="코드수정">
      <VCardText>
        <VRow>
          <VCol cols="12">
            <AppTextField
              v-model="divisionCodeNo"
              label="코드번호"
              items="type"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppAutocomplete
              v-model="codeType"
              label="코드타입"
              :items="type"
              placeholder="공통"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="divisionCodeName"
              label="코드이름"
              persistent-hint
            />
          </VCol>
          <VCol cols="12">
            <AppAutocomplete
              v-model="codeChangeAvailable"
              label="변경가능여부"
              :items="items"
              placeholder="변경가능"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="description"
              label="설명"
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
