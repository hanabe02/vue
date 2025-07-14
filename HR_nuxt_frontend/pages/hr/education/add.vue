<script setup lang="ts">
import { affairStore } from '@/store/hr/affair'
import { useEmpStore } from '@/store/hr/emp'
import axios from 'axios'
import { ref } from "vue"
import { useRouter } from 'vue-router'

const router = useRouter()

const empStore = useEmpStore()

empStore.loadEmpCode()

console.log('현재 로그인된 직원 코드:', empStore.empCode)

// 로그인된 empCode가 'EMP-01'이 아닌 경우 페이지 이동
if (empStore.empCode !== 'EMP-01' && empStore.empCode !== 'EMP-00') {
  router.push('/hr/confinement/unauthorized')
}

const eduList = ref<any[]>([
  {
    empCode: 'EMP-01',
    empName: '홍길동',
    deptCode: 'D001',
    deptName: '인사팀',
    status: '',
    eduHistory: '',
    searchYear: '2025',
  }
])

const eduCode = ref('')
const eduName = ref('')
const startDate = ref('')
const endDate = ref('')
const description = ref('')
const empName = ref('')
const deptCode = ref('')
const deptName = ref('')
const eduHisotry = ref('')
const empCode1 = ref('')
const openDate = ref('')

const empName_list: any = ref([])
const dialog = ref(false)
const eduNameList = ref<any[]>([])

const showSnackbar = ref(false)
const snackbarMessage = ref('')
const showSnackbar2 = ref(false)
const snackbarMessage2 = ref('')

// 직원리스트 조회
const fetchEmpList = async () => {
  await affairStore().FETCH_ALL_EMP()
  empName_list.value = Object.values(affairStore().all_emp_list).map((item: EmpItemProps) => {
    return { title: item.empName, value: item.empCode }
  })
}

const requestEducation = async () => {
  const payload = {
    eduCode: eduCode.value,
    eduName: eduName.value,
    empCode: empCode1.value,
    deptCode: deptCode.value,
    deptName: deptName.value,
    eduHistory: eduHisotry.value,
    searchYear: startDate.value,
    openDate: openDate.value,
  }

  console.log('최종 전송 데이터:', payload)

  try {
    await axios.post('http://localhost:8282/hr/edu/requestEduList', payload)
  }
  catch (error) {
    console.error('교육 등록 실패:', error)
    alert('교육 등록에 실패했습니다. 다시 시도해주세요.')
  }
  snackbarMessage2.value = '신청되었습니다.'
  showSnackbar2.value = true
  return;
}

const registerEducation = async () => {
  if (empStore.empCode !== 'EMP-00') { 
    snackbarMessage.value = ' ❌ 권한이 없습니다.'
    showSnackbar.value = true
    return;
  }
  const payload = {
    eduCode: eduCode.value,
    eduName: eduName.value,
    empCode: empCode1.value,
    deptCode: deptCode.value,
    deptName: deptName.value,
    eduHistory: eduHisotry.value,
    startDate: startDate.value,
    endDate: endDate.value,
    openDate: openDate.value,
    description: description.value,
  }

  console.log('최종 전송 데이터:', payload)

  try {
    await axios.post('http://localhost:8282/hr/edu/registerEduList', payload)
    // ✅ 입력값 초기화
    eduCode.value = ''
    eduName.value = ''
    empCode1.value = ''
    deptCode.value = ''
    deptName.value = ''
    eduHisotry.value = ''
    startDate.value = ''
    endDate.value =  '',
    openDate.value =  '',
    description.value = ''

    alert('교육이 성공적으로 등록되었습니다!')
  }
  catch (error) {
    console.error('교육 등록 실패:', error)
    alert('교육 등록에 실패했습니다. 다시 시도해주세요.')
  }
}
 

const openModal = () => {
  dialog.value = true
  selectEduList() // 모달 열면서 교육명 목록도 조회
}

const selectEduList = async () => {
  try {
    const res = await axios.post('http://localhost:8282/hr/edu/selectEduNameList')

    eduNameList.value = res.data
    console.log('출력값 확인', eduNameList.value)
  }
  catch (error) {
    console.error('교육명 조회 실패:', error)
    alert('교육명을 불러오는 데 실패했습니다.')
  }
}

const selectEdu = (edu: any) => {
  eduName.value = edu.eduName
  dialog.value = false
}

onMounted(() => {
  fetchEmpList()
})
</script>

<template>
  <h1 class="mb-6">📋 교육 등록 및 신청</h1>
  <VCard>
    <VContainer>
      <VRow>
        <VCol cols="12" md="6">
          <AppTextField
            v-model="eduName"
            label="교육명"
            placeholder="예: 직무 향상 교육"
            required
            @click="openModal"
          />
        </VCol>
        <VCol cols="12" md="3">
          <AppDateTimePicker
            v-model="startDate"
            label="시작일"
            placeholder="YYYY-MM-DD"
          />
        </VCol>
        <VCol cols="12" md="3">
          <AppDateTimePicker
            v-model="endDate"
            label="종료일"
            placeholder="YYYY-MM-DD"
          />
        </VCol>
        <VCol cols="12" sm="2">
         <AppSelect v-model="empCode1" label="직원명" :items="empName_list" clearable clear-icon="tabler-x" />
         </VCol>
        <VCol cols="12">
          <AppTextField
            v-model="description"
            label="교육 설명"
            placeholder="교육에 대한 간단한 설명"
            clearable
          />
        </VCol>
        <VCol cols="12" class="d-flex justify-end" style="gap: 8px;">
          <VBtn color="success" @click="requestEducation">교육과정 신청</VBtn>
          <VBtn color="primary" @click="registerEducation">등록</VBtn>
        </VCol>
      </VRow>
    </VContainer>
  </VCard>

  <!-- 교육명 선택 모달 -->
  <VDialog v-model="dialog" max-width="600">
    <VCard>
      <VCardTitle>교육명 선택</VCardTitle>
      <VCardText>
        <VList>
          <VListItem
            v-for="(edu, index) in eduNameList"
            :key="index"
            @click="selectEdu(edu)"
            style="cursor: pointer"
          >
            <VListItemTitle>{{ edu.eduName }}</VListItemTitle>
          </VListItem>
        </VList>
      </VCardText>
      <VCardActions>
        <VSpacer />
        <VBtn text @click="dialog = false">닫기</VBtn>
      </VCardActions>
    </VCard>
  </VDialog>
  <v-snackbar v-model="showSnackbar" timeout="3000" color="error"  location="top center">
  {{ snackbarMessage }}
</v-snackbar>
<v-snackbar v-model="showSnackbar2" timeout="3000" color="success"  location="top center">
  {{ snackbarMessage2 }}
</v-snackbar>
</template>
<!-- background: string;
surface: string;
primary: string;
secondary: string;
success: string;
warning: string;
error: string;
info: string; 라이브러리 안에 다 있음 찾아보셈 80기 존잘-->

