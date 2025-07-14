<script setup lang="ts">
import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRouter } from "vue-router"; // router 가져오기
import { inject } from 'vue';

const router = useRouter();
import { useEmpStore } from '@/store/hr/emp'

const empStore = useEmpStore()
empStore.loadEmpCode()

console.log("현재 로그인된 직원 코드:", empStore.empCode)

// 로그인된 empCode가 'EMP-01'이 아닌 경우 페이지 이동
if (empStore.empCode !== 'EMP-01' && empStore.empCode !== 'EMP-00') {
  router.push('/hr/confinement/unauthorized')
}

const accountData = {
  avatarImg: "/_nuxt/assets/images/empImages/EMP-09.jpeg",
}

const isSuccessFunc = ref(false)
const issaveFunc = ref(false)
const selectedDept = ref('')
const accountDataLocal = ref(structuredClone(accountData))
const refInputEl = ref<HTMLElement>()

const formImg = ref<null | File>(null)

const itemProps = (item: any) => {
  return {
    title: item.text,
    value: item.value,
  }
}

// 성별 select 컬럼
const gender = [
  { value: 'male', text: '남성' },
  { value: 'female', text: '여성' },
]

// 직급 select 컬럼
const positions = [
  { value: '사장', text: '사장' },
  { value: '이사', text: '이사' },
  { value: '부장', text: '부장' },
  { value: '차장', text: '차장' },
  { value: '과장', text: '과장' },
  { value: '대리', text: '대리' },
  { value: '사원', text: '사원' },
  { value: '인턴', text: '인턴' },
  { value: '계약직', text: '계약직' },
]

// 최종학력 select 컬럼
const educations = [
  { value: '중졸이하', text: '중졸이하' },
  { value: '중졸', text: '중졸' },
  { value: '고졸', text: '고졸' },
  { value: '대졸', text: '학사' },
  { value: '석사', text: '석사' },
  { value: '박사', text: '박사' },
]

const empList = ref([])
const deptList = ref([])
const workplaceList = ref([])

const empDetail = ref({
  empCode: '',
  workplaceName: '',
  positionName: '',
  phoneNumber: '',
  email: '',
  zipCode: '',
  basicAddress: '',
  detailAddress: '',
  levelOfEducation: '',
  empName: '',
  deptName: '',
  gender: '',
  birthDate: '',
  socialSecurityNumber: '',
  hireDate: '',
})

const fetchData = async () => {
  try {
    const response = await axios.get('http://localhost:8282/hr/affair/emp')

    empList.value = response.data.empList
    console.log('[empList]', empList.value)
  }
  catch (error) {
    console.error(error)
  }
}

// 부서리스트 조회
const fetchDeptList = async () => {
  try {
    const res = await axios.get('http://localhost:8282/hr/base/deptList')

    console.log('vava', res.data)

    const deptData = res.data.deptlist

    deptList.value = deptData.map((e: any) => ({
      title: e.deptName,
      value: { deptName: e.deptName, deptCode: e.deptCode },
    }))
    console.log('[deptList]', deptList.value)
  }
  catch (error) {
    console.error(error)
  }
}

// 근무지리스트 조회
const fetchWorkPlaceList = async () => {
  try {
    const res = await axios.get('http://localhost:8282/hr/base/workplaceList')
    const workplaceData = res.data.workplaceList

    workplaceList.value = workplaceData.map((e: any) => ({
      value: e.workplaceName,
      text: e.workplaceName,
    }))
    console.log('[workplaceList]', workplaceList.value)
  }
  catch (error) {
    console.error(error)
  }
}

// 등록 버튼
const saveFunc = async () => {
  if (empDetail.value.empCode === '') {
    issaveFunc.value = true
  }
  else {
    try {
      empDetail.value.deptName = selectedDept.value.deptName
      console.log(empDetail.value)

      const postData = empDetail.value
      const formData = new FormData()

      const res = await axios.post(
        'http://localhost:8282/hr/affair/emp',
        postData,
      )

      console.log('[res.postData]', res.data)

      if (formImg.value) {
        formData.append('file', formImg.value)

        await axios.post(
          'http://localhost:8282/hr/affairFile/photoUpdate',
          formData,
        )
      }

      console.log('[postData]', postData)
      await fetchData()
      Object.keys(empDetail.value).forEach(key => {
        empDetail.value[key] = ''
      })
      isSuccessFunc.value = true
    }
    catch (error) {
      console.log('등록 오류', error)
      alert('직원정보 등록 중 오류가 발생했습니다.')
    }
  }
}

// 이미지
const onImageChange = (file: Event) => {
  const fileReader = new FileReader()
  const { files } = file.target as HTMLInputElement

  if (files && files.length > 0)
    formImg.value = files[0]

  if (files && files.length) {
    fileReader.readAsDataURL(files[0])
    fileReader.onload = () => {
      if (typeof fileReader.result === 'string')
        accountDataLocal.value.avatarImg = fileReader.result
    }
  }
}

// reset 버튼
const resetAvatar = () => {
  accountDataLocal.value.avatarImg = accountData.avatarImg
}

// 우편번호
const sample6ExecDaumPostcode = () => {
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

        empDetail.value.zipCode = data.zonecode
        empDetail.value.basicAddress = addr
        empDetail.value.detailAddress = extraAddr

        const zipcodeElement = document.getElementById('sample6Postcode')
        if (zipcodeElement)
          zipcodeElement.value = data.zonecode
        else console.error('zipcodeElement is null or not found')

        document.getElementById('sample6Address').value = addr
        document.getElementById('sample6DetailAddress').focus()
      }
      else {
        document.getElementById('sample6ExtraAddress').value = ''

        empDetail.value.zipCode = data.zonecode
        empDetail.value.basicAddress = addr
        empDetail.value.detailAddress = ''
      }
    },
  }).open()
}

onMounted(() => {
  fetchData()
  fetchDeptList()
  fetchWorkPlaceList()

  const script = document.createElement('script')

  script.src = '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js'
  document.head.appendChild(script)
})
</script>

<template>
  <h1 class="mb-2">직원정보 등록</h1>
  <VContainer>
    <VRow>
      <!-- 직원기초정보 -->
      <VCol md="12">
        <VCard>
          <VCardTitle class="d-flex justify-space-between">
            직원기초정보
            <VBtn color="primary" @click="saveFunc">
              등록
            </VBtn>
          </VCardTitle>
          <VCardText class="d-flex align-center">
            <!-- 👉 Avatar -->
            <VAvatar rounded size="100" class="me-6" :image="accountDataLocal.avatarImg" />
            <!-- 👉 Upload Photo -->
            <form class="d-flex flex-column justify-center gap-4">
              <div class="d-flex flex-wrap gap-2">
                <VBtn variant="outlined" @click="refInputEl?.click()">
                  <span class="d-none d-sm-block">Upload photo</span>
                </VBtn>
                <input ref="refInputEl" type="file" accept="image/jpeg, image/jpg, image/png" hidden
                  @change="onImageChange">
                <VBtn type="reset" color="secondary" variant="outlined" @click="resetAvatar">
                  <span class="d-none d-sm-block">Reset</span>
                </VBtn>
              </div>
              <p class="text-body-1 mb-0">
                Allowed JPG, JPEG or PNG.
              </p>
            </form>
          </VCardText>
          <VCardText class="d-flex">
            <VRow>
              <VCol cols="12" md="3">
                <VTextField v-model="empDetail.empName" label="사원명" variant="outlined" />
              </VCol>
              <VCol cols="12" md="3">
                <VTextField v-model="empDetail.empCode" label="사번" variant="outlined" />
              </VCol>
              <VCol cols="12" md="3">
                <AppDateTimePicker v-model="empDetail.birthDate" placeholder="생년월일" variant="outlined" />
              </VCol>
              <VCol cols="12" md="3">
                <VTextField v-model="empDetail.socialSecurityNumber" label="주민번호" variant="outlined" />
              </VCol>
              <VCol cols="12" md="3">
                <VSelect v-model="selectedDept" label="부서" variant="outlined" :items="deptList" />
              </VCol>
              <VCol cols="12" md="3">
                <VTextField v-model="selectedDept.deptCode" label="부서번호" variant="outlined" readonly />
              </VCol>
              <VCol cols="12" md="3">
                <AppDateTimePicker v-model="empDetail.hireDate" placeholder="입사일" variant="outlined" />
              </VCol>
              <VCol cols="12" md="3">
                <VSelect v-model="empDetail.gender" label="성별" variant="outlined" :items="gender"
                  :item-props="itemProps" />
              </VCol>
            </VRow>
          </VCardText>
        </VCard>
        <br>
        <!-- 직원기초정보 -->
        <VCard title="직원세부정보">
          <VSelect v-model="empDetail.positionName" class="ma-4" label="직급" variant="outlined" :items="positions"
            :item-props="itemProps" />
          <VTextField v-model="empDetail.phoneNumber" class="ma-4" label="전화번호" variant="outlined" />
          <VTextField v-model="empDetail.email" class="ma-4" label="이메일" variant="outlined" />
          <VSelect v-model="empDetail.workplaceName" class="ma-4" label="근무지" variant="outlined" :items="workplaceList"
            :item-props="itemProps" />
          <VSelect v-model="empDetail.levelOfEducation" class="ma-4" label="최종학력" variant="outlined" :items="educations"
            :item-props="itemProps" />
          <VTextField v-model="empDetail.zipCode" class="ma-4" label="우편번호" variant="outlined"
            @click="sample6ExecDaumPostcode" />
          <VTextField v-model="empDetail.basicAddress" class="ma-4" label="주소" variant="outlined" />
          <VTextField v-model="empDetail.detailAddress" class="ma-4" label="상세주소" variant="outlined" />
        </VCard>
      </VCol>
    </VRow>
  </VContainer>
  <VSnackbar v-model="issaveFunc" location="top" :timeout="3000" color="warning">
    <VAlert density="compact" type="warning" title="warning!" text="빈칸이 없는지 확인해주세요." />
  </VSnackbar>
  <VSnackbar v-model="isSuccessFunc" location="top" :timeout="3000" color="success">
    <VAlert density="compact" type="success" title="success!" text="완료되었습니다." />
  </VSnackbar>
</template>
