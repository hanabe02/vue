<script setup lang ="ts">
import { baseStore } from '@/store/hr/base'
import { affairStore } from '@/store/hr/affair'
import type { EmpItemProps, DeptItemProps } from '@/types/attendance/types'
import axios from 'axios'
import { ref } from 'vue'
import { VDataTable } from 'vuetify/labs/VDataTable'
import * as xlsx from 'xlsx'
import { useEmpStore } from '@/store/hr/emp'


const router = useRouter();
const empStore = useEmpStore()
empStore.loadEmpCode()

console.log("현재 로그인된 직원 코드:", empStore.empCode)

// 로그인된 empCode가 'EMP-01'이 아닌 경우 페이지 이동
if (empStore.empCode !== 'EMP-01' && empStore.empCode !== 'EMP-00') {
  router.push('/hr/confinement/unauthorized')
}


const tab = ref('personal-info')
const empCode1 = ref('')
const empName = ref('')
const empName_list: any = ref([])
const deptName = ref('')
const deptName_list: any = ref([])
const startDate = ref('')
const endDate = ref('')
const selectedRows = ref([])
const eduCode = ref('')
const dialog = ref(false)
const rejectCause = ref('')
const printCertificate = ref([])

const isSuccessFunc = ref(false)
const snackbarColor = ref("success");  // 스낵바 색상 (기본값: 성공)
const snackbarType = ref("success");  // 스낵바 타입 (기본값: success)
const snackbarText = ref("완료되었습니다.");  // 스낵바 텍스트 (기본값: 완료 메시지)

const eduList = ref([])

const headers = [
  { title: '교육코드', key: 'eduCode', width: 120 },
  { title: '교육명', key: 'eduName', width: '150px' },
  { title: '직원코드', key: 'empCode', width: 120 },
  { title: '직원명', key: 'empName', width: 100 },
  { title: '부서코드', key: 'deptCode', width: 120 },
  { title: '부서명', key: 'deptName', width: 100 },
  { title: '교육 이수 내역', key: 'eduHistory', width: '150px' },
  { title: '승인상태', key: 'status', width: 120 },
  { title: '검색년도', key: 'searchYear', width: '150px' },
  { title: '반려사유', key: 'rejectCause', witdh: '300px' },
  { title: '비고', key: 'etc', width: '300px' },
  { title: '증명서구분', key: 'category', witdh: 120 },
]

onMounted(() => {
  fetchDeptList()
  fetchEmpList()
})

// 부서리스트 조회
const fetchDeptList = async () => {
  await baseStore().FETCH_DEPTLIST()
  deptName_list.value = Object.values(baseStore().deptList).map((item: DeptItemProps) => {
    return { title: item.deptName, value: item.deptName }
  })
}

// 직원리스트 조회
const fetchEmpList = async () => {
  await affairStore().FETCH_ALL_EMP()
  empName_list.value = Object.values(affairStore().all_emp_list).map((item: EmpItemProps) => {
    return { title: item.empName, value: item.empCode }
  })
}

const fetchData = async () => {
  if (deptName.value === '' || startDate.value === '' || endDate.value === '' || empCode1.value === '') {
    alert('모든 항목을 선택해주세요.')

    return
  }

  await axios.get('http://localhost:8282/hr/edu/findEduList', {
    params: { startDate: startDate.value, endDate: endDate.value, empCode: empCode1.value, deptName: deptName.value },
  }).then(res => {
    eduList.value = res.data.eduList
    console.log('eduList', eduList.value)
  }).catch(e => console.error(e))
}

// 승인
const updateStatus = async (title: string) => {
  if (empStore.empCode !== 'EMP-00') {
    snackbarColor.value = 'error';
    snackbarText.value = '⛔ 권한이 없습니다. 관리자에게 문의해주세요.';
    snackbarType.value = 'error';
    isSuccessFunc.value = true;
    return;
  }

  if (selectedRows.value.length === 0) {
    alert('항목을 선택해주세요.')

    return
  }

  if (title === '승인') {
    if (selectedRows.value[0].status === '승인') {
      alert('이미 승인된 건입니다.')

      return
    }
  }

  const status = title === '승인' ? 'approval' : 'cancel'
  const message = title === '승인' ? '승인 완료되었습니다.' : '승인 취소 완료되었습니다.'

  const updatedRowsData = selectedRows.value.map((row: any) => ({ ...row, status }))

  console.log('updatedRows', updatedRowsData)

  try {
    await axios.post('http://localhost:8282/hr/edu/approEduList', updatedRowsData)
    alert(message)
  }
  catch (error) {
    console.error(error)
  }

  selectedRows.value = []
  fetchData()
}

// 승인반려
const openModal = () => {
  if (selectedRows.value.length === 0) {
    alert('항목을 선택해주세요.')

    return
  }
  if (selectedRows.value[0]?.status === '승인') {
    alert('승인된 건은 반려할 수 없습니다.'); selectedRows.value = []

    return
  }
  dialog.value = true
}


const excelDown = async () => {
//   if (selectedRows.value.length === 0) {
//     alert('출력할 증명서를 선택해주세요.')

//     return
//   }
//   if (selectedRows.value.length > 1) {
//     alert('1개의 증명서만 선택해주세요.')

//     return
//   }
//   if (selectedRows.value[0].status != '승인') {
//     alert('승인된 증명서만 출력가능합니다.')

//     return
//   }
//   if (selectedRows.value[0].useDate != null) {
//     alert('이미 출력된 건입니다.')

//     return
//   }

  eduCode.value = selectedRows.value[0]?.eduCode
  console.log(eduCode.value)
  await axios.patch(`http://localhost:8282/hr/edu/updateUseDate/${eduCode.value}`).then(res => {
    console.log('updateUseDate', res.data)
  }).catch(e => console.error(e))
  await axios.get('http://localhost:8282/hr/edu/printEduCertificate', {
    params: { eduCode: eduCode.value },
  }).then(res => {
    printCertificate.value = res.data.printCertificate
    console.log('printCertificate.value', printCertificate.value)
    console.log('selectedRows.value', selectedRows.value)

    const certWS = xlsx.utils.json_to_sheet([printCertificate.value])

    console.log('certWS', certWS)

    const wb = xlsx.utils.book_new()

    xlsx.utils.book_append_sheet(wb, certWS, 'certificate')
    xlsx.writeFile(wb, `${new Date()}_${selectedRows.value[0].eduName}_${selectedRows.value[0].empName}_${selectedRows.value[0].etc}.xlsx`)
  }).catch(e => console.error(e))
  selectedRows.value = []
  fetchData()
}

// 승인 반려
const rejectCert = async () => {
  const updatedRowsData = selectedRows.value.map((row: any) => ({ ...row, status: 'refer', rejectCause: rejectCause.value }))

  console.log('updatedRows', updatedRowsData)
  await axios.post('http://localhost:8282/hr/edu/approEduList', updatedRowsData)
  dialog.value = false
  alert('승인 반려 완료되었습니다. ')
  selectedRows.value = []
  fetchData()
}
</script>

<template>
      <VCard>
    <VTabs v-model="tab" height="70">
      <VTab value="personal-info">
        📋 교육 승인 및 반려
      </VTab>
    </VTabs>
  </VCard>
  <VCard flat>
    <VCardText>
      <VWindow v-model="tab" class="disable-tab-transition">
        <VWindowItem value="personal-info">
          <VCard class="mb-6">
            <VCardText>
              <VRow>
                <VCol cols="12" sm="2">
                  <AppSelect v-model="empCode1" label="직원명" :items="empName_list" clearable clear-icon="tabler-x" />
                </VCol>
                <VCol cols="12" sm="2">
                  <AppSelect v-model="deptName" label="부서명" :items="deptName_list" clearable clear-icon="tabler-x" />
                </VCol>
                <VCol cols="12" sm="3" mt="4">
                  <AppTextField v-model="startDate" type="date" label="시작일" placeholder="시작일" />
                </VCol>
                <VCol cols="12" sm="3" mt="4">
                  <AppTextField v-model="endDate" type="date" label="종료일" placeholder="종료일" />
                </VCol>
                <VBtn class="mt-10" @click="fetchData">
                  조회
                </VBtn>
              </VRow>
            </VCardText>
          </VCard>
        </VWindowItem>
      </VWindow>
    </VCardText>
  </VCard>
  <VCard class="mt-6">
    <!-- 증명서 발급 승인 -->
    <VCardText class="mt-3 mb-3">
      증명서승인내역
    </VCardText>
    <VDataTable v-model="selectedRows" :headers="headers" :items="eduList" :items-per-page="10" show-select
      return-object item-value="certificateNo" />
    <VRow class="text-right mr-3 mb-2 mt-3">
      <VCol>
        <VBtn class="mr-2" color="primary" @click="updateStatus('승인')">
          승인
        </VBtn>
        <VBtn color="primary" class="mr-2" @click="openModal">
          승인반려
        </VBtn>
        <VBtn color="primary" @click="excelDown">
          출력
        </VBtn>
      </VCol>
    </VRow>
  </VCard>
   <!-- 다이얼로그 -->
   <div class="text-center">
    <VDialog v-model="dialog" width="300">
      <VCard>
        <VCardText>
          반려사유를 입력해주세요.
        </VCardText>
        <VContainer>
          <VTextarea v-model="rejectCause" clearable label="반려사유" variant="outlined" />
        </VContainer>
        <VCardActions>
          <VBtn color="primary" @click="rejectCert">
            반려
          </VBtn>
          <VBtn color="primary" @click="dialog = false">
            닫기
          </VBtn>
        </VCardActions>
      </VCard>
    </VDialog>
  </div>
  <VSnackbar
    v-model="isSuccessFunc"
    location="top"
    :timeout="3000"
    :color="snackbarColor"
  >
    <VAlert
      density="compact"
      :type="snackbarType"
      title="알림"
      :text="snackbarText"
    />
  </VSnackbar>
</template>