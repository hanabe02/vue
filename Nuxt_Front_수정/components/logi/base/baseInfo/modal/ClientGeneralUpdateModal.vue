<script lang="ts" setup>
import axios from 'axios'
import { baseStore } from '@/store/logi/base'

const props = defineProps(['isDialogVisible', 'updateClient', 'selectedCustomerCode', 'search'])
const emits = defineEmits(['closeModal', 'updateClient'])

const customerCode = ref('')
const workplaceCode = ref('')
const customerName = ref('')
const customerType = ref('')
const customerCeo = ref('')
const businessLicenseNumber = ref('')
const socialSecurityNumber = ref('')
const customerBusinessConditions = ref('')
const customerBusinessItems = ref('')
const customerZipCode = ref('')
const customerBasicAddress = ref('')
const customerDetailAddress = ref('')
const customerTelNumber = ref('')
const customerFaxNumber = ref('')
const customerNote = ref('')
const items = ['일반', '해외']

// const getClientInfoData: Ref<any[]> = ref([])

const closeModal = () => {
  emits('closeModal')
}

const fetchData = async (customerCodes: string) => {
  console.log('1111111', customerCodes)

  const res = await axios.get('http://localhost:8282/logi/base/searchClientDetailList', {
    params: {
      customerCodes,
    },
  })

  console.log('rara', res.data.clientDetailInfo[0])

  customerCode.value = res.data.clientDetailInfo[0].customerCode
  workplaceCode.value = res.data.clientDetailInfo[0].workplaceCode
  customerName.value = res.data.clientDetailInfo[0].customerName
  customerType.value = res.data.clientDetailInfo[0].customerType
  customerCeo.value = res.data.clientDetailInfo[0].customerCeo
  businessLicenseNumber.value = res.data.clientDetailInfo[0].businessLicenseNumber
  socialSecurityNumber.value = res.data.clientDetailInfo[0].socialSecurityNumber
  customerBusinessConditions.value = res.data.clientDetailInfo[0].customerBusinessConditions
  customerBusinessItems.value = res.data.clientDetailInfo[0].customerBusinessItems
  customerZipCode.value = res.data.clientDetailInfo[0].customerZipCode
  customerBasicAddress.value = res.data.clientDetailInfo[0].customerBasicAddress
  customerDetailAddress.value = res.data.clientDetailInfo[0].customerDetailAddress
  customerTelNumber.value = res.data.clientDetailInfo[0].customerTelNumber
  customerFaxNumber.value = res.data.clientDetailInfo[0].customerFaxNumber
  customerNote.value = res.data.clientDetailInfo[0].customerNote
}

watch(() => props.selectedCustomerCode, async () => {
  console.log('ttttttttttttttttttttttt')
  await fetchData(props.selectedCustomerCode)
},
)

const updateData = async () => {
  try {
    const userConfirmed = window.confirm('수정하시겠습니까?')

    if (userConfirmed) {
      const updateDataList
    = {
      customerCode: customerCode.value,
      workplaceCode: workplaceCode.value,
      customerName: customerName.value,
      customerType: customerType.value,
      customerCeo: customerCeo.value,
      businessLicenseNumber: businessLicenseNumber.value,
      socialSecurityNumber: socialSecurityNumber.value,
      customerBusinessConditions: customerBusinessConditions.value,
      customerBusinessItems: customerBusinessItems.value,
      customerZipCode: customerZipCode.value,
      customerBasicAddress: customerBasicAddress.value,
      customerDetailAddress: customerDetailAddress.value,
      customerTelNumber: customerTelNumber.value,
      customerFaxNumber: customerFaxNumber.value,
      customerNote: customerNote.value,
    }

      console.log('123', updateDataList)

      await baseStore().UPDATE_GENERAL_CLIENT_INFO(updateDataList)

      alert('거래처가 성공적으로 수정되었습니다.')

      props.search()

      // emits('updateClient')
      closeModal()
    }
  }
  catch (error) {
    console.error('데이터 수정 오류:', error)

    // 수정 실패 메시지
    alert('거래처 수정에 실패했습니다.')
  }
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

        customerZipCode.value = data.zonecode
        customerBasicAddress.value = addr
        customerDetailAddress.value = extraAddr

        // 주소를 입력하는 부분의 ID를 사용하여 자동으로 값 설정
        const zipcodeElement = document.getElementById('sample6Postcode')
        if (zipcodeElement)
          zipcodeElement.value = data.zonecode

        document.getElementById('sample6Address').value = addr
        document.getElementById('sample6DetailAddress').focus()
      }
      else {
        document.getElementById('sample6ExtraAddress').value = ''

        customerZipCode.value = data.zonecode
        customerBasicAddress.value = addr
        customerDetailAddress.value = ''
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
  <VDialog max-width="600">
    <!-- Dialog Content -->
    <VCard title="거래처 수정">
      <VCardText>
        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="customerCode"
              label="거래처코드"
              disabled
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
              v-model="customerName"
              label="거래처명"
              persistent-hint
            />
          </VCol>
          <VCol cols="12">
            <AppAutocomplete
              v-model="customerType"
              label="customerType"
              :items="items"
              placeholder="일반"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="customerCeo"
              label="대표자명"
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
              v-model="socialSecurityNumber"
              label="주민등록번호"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="customerBusinessConditions"
              label="업태"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="customerBusinessItems"
              label="종목"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="customerZipCode"
              label="우편번호"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <VBtn
              style="margin-top: 27px; margin-left: 20px;"
              @click="openDaumPostcodeModal"
            >
              우편번호 검색
            </VBtn>
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="customerBasicAddress"
              label="기본주소"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="customerDetailAddress"
              label="세부주소"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="customerTelNumber"
              label="전화번호"
            />
          </VCol>
          <VCol
            cols="12"
            sm="6"
          >
            <AppTextField
              v-model="customerFaxNumber"
              label="팩스번호"
            />
          </VCol>
          <VCol cols="12">
            <AppTextField
              v-model="customerNote"
              label="비고"
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
        <VBtn @click="updateData">
          Save
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
</template>
