<script setup lang="ts">
import { baseStore } from '@/store/hr/base'
import type { DeptItemProps } from '@/types/attendance/types'
import axios from 'axios'
import { ref } from 'vue'
import { VDataTable } from 'vuetify/labs/VDataTable'
import * as xlsx from 'xlsx'
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

const tab = ref('personal-info')
const deptName = ref('')
const deptName_list: any = ref([])
const certificateList = ref([])
const startDate = ref('')
const endDate = ref('')
const selectedRows = ref([])
const certificateNo = ref('')
const dialog = ref(false)
const rejectCause = ref('')
const printCertificate = ref([])

const headers = [
  { title: '신청번호', key: 'certificateNo' },
  { title: '신청일자', key: 'requestDate' },
  { title: '사원명', key: 'empName' },
  { title: '증명서구분', key: 'category' },
  { title: '증명서용도', key: 'usageName' },
  { title: '출력일자', key: 'useDate' },
  { title: '승인상태', key: 'approvalStatus' },
]

// 페이지가 로드되자마자 실행
onMounted(() => {
  fetchDeptList()
})

// 부서리스트 조회
const fetchDeptList = async () => {
  await baseStore().FETCH_DEPTLIST()
  deptName_list.value = Object.values(baseStore().deptList).map((item: DeptItemProps) => {
    return { title: item.deptName, value: item.deptName }
  })
}

const fetchData = async () => {
  if (deptName.value === '' || startDate.value === '' || endDate.value === '') {
    alert('모든 항목을 선택해주세요.')

    return
  }

  await axios.get('http://localhost:8282/hr/certificate/findCertificateListByDept', {
    params: { startDate: startDate.value, endDate: endDate.value, deptName: deptName.value },
  }).then(res => {
    certificateList.value = res.data.certificateList
    console.log('certificateList', certificateList.value)
  }).catch(e => console.error(e))
}

// 승인 상태에서 승인버튼을 클릭하면 이미 승인된 건입니다. 출력
// 승인 상태에서 승인취소버튼을 클릭하면 승인 취소되었습니다. 출력
// 승인 취소상태에서는 조회되지 않음

// 증명서 승인/취소
const updateStatus = async (title: string) => {
  if (selectedRows.value.length === 0) {
    alert('항목을 선택해주세요.')

    return
  }

  if (title === '승인') {
    if (selectedRows.value[0].approvalStatus === '승인') {
      alert('이미 승인된 건입니다.')

      return
    }
  }
  if (title === '승인취소') {
    if (selectedRows.value[0].approvalStatus === '승인대기') {
      alert('승인대기건은 승인취소할 수 없습니다.')

      return
    }
  }

  const approvalStatus = title === '승인' ? 'approval' : 'cancel'
  const message = title === '승인' ? '승인 완료되었습니다.' : '승인 취소 완료되었습니다.'

  const updatedRowsData = selectedRows.value.map((row: any) => ({ ...row, approvalStatus }))

  console.log('updatedRows', updatedRowsData)

  try {
    await axios.post('http://localhost:8282/hr/certificate//approvalCertificateList', updatedRowsData)
    alert(message)
  }
  catch (error) {
    console.error(error)
  }

  selectedRows.value = []
  fetchData()
}

// 승인반려클릭
const openModal = () => {
  if (selectedRows.value.length === 0) {
    alert('항목을 선택해주세요.')

    return
  }
  if (selectedRows.value[0]?.approvalStatus === '승인') {
    alert('승인된 건은 반려할 수 없습니다.'); selectedRows.value = []

    return
  }
  dialog.value = true
}

// 승인 반려
const rejectCert = async () => {

  const updatedRowsData = selectedRows.value.map((row: any) => ({ ...row, approvalStatus: 'refer', rejectCause: rejectCause.value }))

  console.log('updatedRows', updatedRowsData)
  await axios.post('http://localhost:8282/hr/certificate/approvalCertificateList', updatedRowsData)
  dialog.value = false
  alert('승인 반려 완료되었습니다. ')
  selectedRows.value = []
  fetchData()
}

// 증명서 출력
const excelDown = async () => {
  if (selectedRows.value.length === 0) {
    alert('출력할 증명서를 선택해주세요.')

    return
  }
  if (selectedRows.value.length > 1) {
    alert('1개의 증명서만 선택해주세요.')

    return
  }
  if (selectedRows.value[0].approvalStatus != '승인') {
    alert('승인된 증명서만 출력가능합니다.')

    return
  }
  if (selectedRows.value[0].useDate != null) {
    alert('이미 출력된 건입니다.')

    return
  }

  certificateNo.value = selectedRows.value[0]?.certificateNo
  console.log(certificateNo.value)
  await axios.patch(`http://localhost:8282/hr/certificate/updateUseDate/${certificateNo.value}`).then(res => {
    console.log('updateUseDate', res.data)
  }).catch(e => console.error(e))
  await axios.get('http://localhost:8282/hr/certificate/printCertificate', {
    params: { certificateNo: certificateNo.value },
  }).then(res => {
    printCertificate.value = res.data.printCertificate
    console.log('printCertificate.value', printCertificate.value)
    console.log('selectedRows.value', selectedRows.value)

    const certWS = xlsx.utils.json_to_sheet([printCertificate.value])

    console.log('certWS', certWS)

    const wb = xlsx.utils.book_new()

    xlsx.utils.book_append_sheet(wb, certWS, 'certificate')
    xlsx.writeFile(wb, `${new Date()}_${selectedRows.value[0].empName}_${selectedRows.value[0].category}.xlsx`)
  }).catch(e => console.error(e))
  selectedRows.value = []
  fetchData()
}
</script>

<template>
  <VCard>
    <VTabs v-model="tab" height="70">
      <VTab value="personal-info">
        증명서 관리
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
                <VCol cols="12" sm="3">
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
    <VDataTable v-model="selectedRows" :headers="headers" :items="certificateList" :items-per-page="10" show-select
      return-object item-value="certificateNo" />
    <VRow class="text-right mr-3 mb-2 mt-3">
      <VCol>
        <VBtn class="mr-2" color="primary" @click="updateStatus('승인')">
          승인
        </VBtn>
        <VBtn class="mr-2" color="primary" @click="updateStatus('승인취소')">
          승인취소
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
</template>