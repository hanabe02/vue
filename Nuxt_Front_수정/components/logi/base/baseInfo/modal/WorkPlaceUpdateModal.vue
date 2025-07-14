<script lang="ts" setup>
import axios from 'axios'
import { defineProps, ref, watch } from 'vue'
import { baseStore } from '@/store/logi/base'

const props = defineProps(['selectedWorkplace'])
const emit = defineEmits(['update'])

const isDialogVisible = ref(false)

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

watch(() => props.selectedWorkplace, newSelectedWorkplace => {
  if (newSelectedWorkplace) {
    workplaceCode.value = newSelectedWorkplace.workplaceCode
    companyCode.value = newSelectedWorkplace.companyCode
    workplaceName.value = newSelectedWorkplace.workplaceName
    businessLicenseNumber.value = newSelectedWorkplace.businessLicenseNumber
    corporationLicenseNumber.value = newSelectedWorkplace.corporationLicenseNumber
    workplaceCeoName.value = newSelectedWorkplace.workplaceCeoName
    workplaceBusinessConditions.value = newSelectedWorkplace.workplaceBusinessConditions
    workplaceBusinessItems.value = newSelectedWorkplace.workplaceBusinessItems
    workplaceZipCode.value = newSelectedWorkplace.workplaceZipCode
    workplaceBasicAddress.value = newSelectedWorkplace.workplaceBasicAddress
    workplaceDetailAddress.value = newSelectedWorkplace.workplaceDetailAddress
    workplaceTelNumber.value = newSelectedWorkplace.workplaceTelNumber
    workplaceFaxNumber.value = newSelectedWorkplace.workplaceFaxNumber
    workplaceEstablishDate.value = newSelectedWorkplace.workplaceEstablishDate
    workplaceOpenDate.value = newSelectedWorkplace.workplaceOpenDate
    workplaceCloseDate.value = newSelectedWorkplace.workplaceCloseDate
    approvalStatus.value = newSelectedWorkplace.approvalStatus
  }
})

// 수정하고 추가 로직
const update = async () => {
  const updateDataList
    = {
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

  console.log(updateDataList)
  console.log(updateDataList[0])

  await baseStore().UPDATE_WORKPLACE_INFO(updateDataList)

  emit('update')

  isDialogVisible.value = false
}

const openDaumPostcodeModal = () => {
  new daum.Postcode({
    oncomplete: data => {
      let addr = ''
      let extraAddr = ''

      if (data.userSelectedType === 'R')
        addr = data.roadAddress
      else addr = data.jibunAddress

      if (data.userSelectedType === 'R') {
        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname))
          extraAddr += data.bname

        if (data.buildingName !== '' && data.apartment === 'Y') {
          extraAddr
            += extraAddr !== '' ? `, ${data.buildingName}` : data.buildingName
        }

        if (extraAddr !== '')
          extraAddr = ` (${extraAddr})`

        workplaceZipCode.value = data.zonecode
        workplaceBasicAddress.value = addr
        workplaceDetailAddress.value = extraAddr

        // 주소를 입력하는 부분의 ID를 사용하여 자동으로 값 설정
        const zipcodeElement = document.getElementById('sample6Postcode')
        if (zipcodeElement)
          zipcodeElement.value = data.zonecode

        document.getElementById('sample6Address').value = addr
        document.getElementById('sample6DetailAddress').focus()
      }
      else {
        document.getElementById('sample6ExtraAddress').value = ''

        workplaceZipCode.value = data.zonecode
        workplaceBasicAddress.value = addr
        workplaceDetailAddress.value = ''
      }
    },
  }).open()
}

onMounted(() => {
  const script = document.createElement('script')

  script.src = '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js'
  document.head.appendChild(script)
})
</script>

<template>
  <VDialog
    v-model="isDialogVisible"
    max-width="600"
  >
    <!-- Dialog Activator -->
    <template #activator="{ props }">
      <VBtn v-bind="props">
        사업장 수정
      </VBtn>
    </template>

    <!-- Dialog close btn -->
    <DialogCloseBtn @click="isDialogVisible = !isDialogVisible" />

    <!-- Dialog Content -->
    <VCard title="사업장 수정">
      <VCardText>
        <VRow>
          <VCol cols="6">
            <AppTextField
              v-model="workplaceCode"
              label="사업장 코드"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="companyCode"
              label="회사코드"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="workplaceName"
              label="사업장명"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="businessLicenseNumber"
              label="사업장등록번호"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="corporationLicenseNumber"
              label="법인등록번호"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="workplaceCeoName"
              label="대표자명"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="workplaceBusinessConditions"
              label="업태"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="workplaceBusinessItems"
              label="종목"
            />
          </VCol>
          <VCol cols="3">
            <AppTextField
              v-model="workplaceZipCode"
              label="사업장우편번호"
            />
          </VCol>
          <div class="address-container">
            <VCol cols="12">
              <AppTextField
                v-model="workplaceBasicAddress"
                label="사업장기본주소"
              />
            </VCol>
            <VCol cols="12">
              <VBtn
                style="margin-top: 27px;"
                @click="openDaumPostcodeModal"
              >
                우편번호 검색
              </VBtn>
            </VCol>
          </div>

          <VCol cols="6">
            <AppTextField
              v-model="workplaceDetailAddress"
              label="사업장세부주소"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="workplaceTelNumber"
              label="사업장전화번호"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="workplaceFaxNumber"
              label="사업장팩스번호"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="workplaceEstablishDate"
              label="사업장개업년월일"
              type="date"
              @change="handleDateSelection"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="workplaceOpenDate"
              label="사업장설립년월일"
              type="date"
              @change="handleDateSelection"
            />
          </VCol>
          <VCol cols="6">
            <AppTextField
              v-model="workplaceCloseDate"
              label="사업장폐업년월일"
              type="date"
              @change="handleDateSelection"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="approvalStatus"
              label="본점여부"
            />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3">
        <VBtn
          variant="tonal"
          color="secondary"
          @click="isDialogVisible = false"
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
