<script lang="ts" setup>
const propz = defineProps(['isDialogVisible'])

const emits = defineEmits(['cancelModal', 'saveList'])
const workplaceCode = ref('')
const companyCode = ref('')
const workplaceName = ref('')
const businessLicenseNumber = ref('')
const corporationLicenseNumber = ref('')
const workplaceCeoName = ref('')
const workplaceBusinessConditions = ref()
const workplaceBusinessItems = ref('')
const workplaceZipCode = ref('')
const workplaceBasicAddress = ref('')
const workplaceDetailAddress = ref('')
const workplaceTelNumber = ref('')
const workplaceFaxNumber = ref('')
const workplaceEstablishDate = ref('')
const workplaceOpenDate = ref('')
const workplaceCloseDate = ref('')
const approvalStatus = ref('')

const cancelModal = () => {
  emits('cancelModal')
}

// 사업장 추가하고 저장
const saveList = () => {
  const menudata = {
    workplaceCode: workplaceCode.value,
    companyCode: companyCode.value,
    workplaceName: workplaceName.value,
    businessLicenseNumber: businessLicenseNumber.value,
    corporationLicenseNumber: corporationLicenseNumber.value,
    workplaceCeoName: workplaceCeoName.value,
    workplaceBusinessConditions: workplaceBusinessConditions.value,
    workplaceBusinessItems: workplaceBusinessItems.value,
    workplaceZipCode: workplaceZipCode.value,
    workplaceBasicAddress: workplaceBasicAddress.value,
    workplaceDetailAddress: workplaceDetailAddress.value,
    workplaceTelNumber: workplaceTelNumber.value,
    workplaceFaxNumber: workplaceFaxNumber.value,
    workplaceEstablishDate: workplaceEstablishDate.value,
    workplaceOpenDate: workplaceOpenDate.value,
    workplaceCloseDate: workplaceCloseDate.value,
    approvalStatus: approvalStatus.value,
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
        사업장 추가
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="사업장 추가">
      <VCardText>
        <VRow>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceCode"
              label="사업장 코드"
              placeholder="workplaceCode"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="companyCode"
              label="회사코드"
              placeholder="companyCode"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceName"
              label="사업장명"
              placeholder="workplaceName"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="businessLicenseNumber"
              label="사업장등록번호"
              placeholder="johndoe@email.com"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="corporationLicenseNumber"
              label="법인등록번호"
              placeholder="corporationLicenseNumber"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceCeoName"
              label="대표자명"
              placeholder="workplaceCeoName"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceBusinessConditions"
              label="업태"
              placeholder="workplaceBusinessConditions"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceBusinessItems"
              label="종목"
              placeholder="workplaceBusinessItems"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceZipCode"
              label="사업장우편번호"
              placeholder="workplaceZipCode"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceBasicAddress"
              label="사업장기본주소"
              placeholder="workplaceBasicAddress"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceDetailAddress"
              label="사업장세부주소"
              placeholder="workplaceDetailAddress"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceTelNumber"
              label="사업장전화번호"
              placeholder="workplaceTelNumber"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceFaxNumber"
              label="사업장팩스번호"
              placeholder="workplaceFaxNumber"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceEstablishDate"
              label="사업장개업년월일"
              type="date"
              placeholder="workplaceEstablishDate date"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceOpenDate"
              label="사업장설립년월일"
              type="date"
              placeholder="workplaceOpenDate"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="workplaceCloseDate"
              label="사업장폐업년월일"
              type="date"
              placeholder="workplaceCloseDate"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="approvalStatus"
              label="본점여부"
              placeholder="approvalStatus"
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
