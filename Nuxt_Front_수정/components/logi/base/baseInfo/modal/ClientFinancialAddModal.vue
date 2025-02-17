<script lang="ts" setup>
const props = defineProps(['isAddDialogVisible'])
const emits = defineEmits(['closeAddModal', 'saveAddClient'])

const accountAssociatesCode = ref('')
const workplaceCode = ref('')
const accountAssociatesName = ref('')
const accountAssociatesType = ref('')
const accountNumber = ref('')
const accountOpenPlace = ref('')
const cardNumber = ref('')
const cardType = ref('')
const cardMemberName = ref('')
const cardOpenPlace = ref('')
const businessLicenseNumber = ref('')
const financialInstituteCode = ref('')
const financialInstituteName = ref('')
const divisionCodeNo = ref('')

const closeAddModal = () => {
  emits('closeAddModal')
}

const saveAddClient = () => {
  const clientdata = {
    accountAssociatesCode: accountAssociatesCode.value,
    workplaceCode: workplaceCode.value,
    accountAssociatesName: accountAssociatesName.value,
    accountNumber: accountNumber.value,
    accountOpenPlace: accountOpenPlace.value,
    cardNumber: cardNumber.value,
    cardType: cardType.value,
    cardMemberName: cardMemberName.value,
    cardOpenPlace: cardOpenPlace.value,
    businessLicenseNumber: businessLicenseNumber.value,
    financialInstituteCode: financialInstituteCode.value,
    financialInstituteName: financialInstituteName.value,
    divisionCodeNo: divisionCodeNo.value,
  }

  emits('saveAddClient', clientdata)
}
</script>

<template>
  <VDialog
    v-model="props.isAddDialogVisible"
    max-width="600"
  >
    <template #activator="{ props }">
      <VBtn
        v-bind="props"
        style="margin-right: 20px;"
      >
        금융거래처 추가
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="금융거래처 추가">
      <VCardText>
        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="accountAssociatesCode"
              label="금융거래처코드"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="workplaceCode"
              label="사업장코드"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="accountAssociatesName"
              label="금융거래처명"
              persistent-hint
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="accountAssociatesType"
              label="금융거래처유형"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="accountNumber"
              label="계좌번호"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="accountOpenPlace"
              label="계좌개설점"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="cardNumber"
              label="카드번호"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="cardType"
              label="카드구분"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="cardMemberName"
              label="카드회원명"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="cardOpenPlace"
              label="카드가맹점번호"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="businessLicenseNumber"
              label="사업자등록번호"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="financialInstituteCode"
              label="금융기관코드"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="financialInstituteName"
              label="금융기관명"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="divisionCodeNo"
              label="구분"
            />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3">
        <VBtn
          variant="tonal"
          color="secondary"
          @click="closeAddModal"
        >
          Close
        </VBtn>
        <VBtn @click="saveAddClient">
          Save
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
</template>
