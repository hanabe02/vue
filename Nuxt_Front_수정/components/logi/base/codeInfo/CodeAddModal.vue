<script lang="ts" setup>
const props = defineProps(['isAddDialogVisible'])
const emits = defineEmits(['save', 'close'])

const divisionCodeNo = ref('')
const codeType = ref('')
const divisionCodeName = ref('')
const codeChangeAvailable = ref('')
const description = ref('')

const type = ref(['공통', '회계', '구매', '영업'])
const items = ref(['변경가능', '변경불가능'])

const close = () => {
  emits('close')
}

// 코드 추가하고 저장
const save = () => {
  const codelist = {
    divisionCodeNo: divisionCodeNo.value,
    codeType: codeType.value,
    divisionCodeName: divisionCodeName.value,
    codeChangeAvailable: codeChangeAvailable.value,
    description: description.value,
    status: 'INSERT',
  }

  console.log(codelist)

  emits('save', codelist)
}
</script>

<template>
  <VDialog
    v-model="props.isDialogVisible"
    max-width="600"
  >
    <!-- Dialog Activator -->
    <template #activator="{ props }">
      <VBtn
        v-bind="props"
        style="margin-right: 20px; margin-left: 20px"
      >
        코드 추가
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="코드추가">
      <VCardText>
        <VRow>
          <VCol cols="12">
            <AppTextField
              v-model="divisionCodeNo"
              label="코드번호"
              items="type"
              placeholder="NEW"
              disabled
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
