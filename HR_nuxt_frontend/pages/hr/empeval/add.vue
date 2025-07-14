<script setup lang="ts">
import { baseStore } from '@/store/hr/base'
import { evalStore } from '@/store/hr/eval'
import axios from 'axios'
import { ref } from 'vue'
import { VDataTable } from 'vuetify/labs/VDataTable'
import { computed, watch } from 'vue'
// import { useRouter } from "vue-router"; // router 가져오기
// import { inject } from 'vue';

// const router = useRouter();
// const empCode = inject('empCode', ref('')); // 기본값은 빈 문자열
// console.log('현재 로그인된 직원 코드:', empCode.value, empCode)

// // 로그인된 empCode가 'EMP-01'이 아닌 경우 페이지 이동
// if (empCode.value !== 'EMP-01' && empCode.value !== 'EMP-00') {
//   router.push('/hr/confinement/unauthorized');
// }

const store = evalStore()
const searchYear = ref('')
const searchEmp = ref('')
const searchDept = ref('')

// 80기
const empCode = ref('')
const positionName = ref('')
const durationOfTraining = ref('')
const numberOfCertificate = ref('')
const attendanceStatus = ref('')
const numberOfRestDay = ref('')
const remainingHoliday = ref('')
const disqualification = ref('')
const career = ref('')
const score = ref('')
const grade = ref('')
const status = ref('')

const positionNameList: any = ref([])
const durationOfTrainingList: any = ref([])
const numberOfCertificateList: any = ref([])
const attendanceStatusList: any = ref([])
const numberOfRestDayList: any = ref([])
const remainingHolidayList: any = ref([])
const disqualificationList: any = ref([])
const careerList: any = ref([])

const yearList: any = ref([])
const deptList = ref([])
const empList = ref([])

const evalList: any = ref([])

const showTardyModal = ref(false) // 모달 열기 상태
const selectedTardyCount = ref('') // 지각 횟수

const fetchYear = async () => {
  await baseStore().FETCH_YEARLIST()

  const yearListData = baseStore().yearList

  yearList.value = yearListData ? Object.values(yearListData).map(item => item.year) : []
}

const headers = [
  { title: '이름', key: 'empName', width: '150px' },
  { title: '부서', key: 'deptName', width: '150px'},
  { title: '직급', key: 'positionName', width: '150px' },
  { title: '교육', key: 'durationOfTraining', width: '200px' },
  { title: '자격증', key: 'numberOfCertificate', width: '200px' },
  { title: '근태', key: 'attendanceStatus', width: '150px' },
  { title: '남은 휴가', key: 'numberOfRestDay', width: '150px' },
  { title: '휴가', key: 'remainingHoliday', width: '200px' },
  { title: '결격여부', key: 'disqualification', width: 120 },
  { title: '장기근속', key: 'career', width: '160px' },
  { title: '총 점수', key: 'score', width: '150px' },
  { title: '등급', key: 'grade' },
]

// 부서리스트 조회
const fetchDeptList = async () => {
  try {
    const res = await axios.get('http://localhost:8282/hr/base/deptList')

    console.log('vava', res.data)

    const deptData = res.data.deptlist

    deptList.value = deptData.map((e: any) => ({
      title: e.deptName,
      value: e.deptCode,
    }))

    console.log('[deptList]', deptList.value)
  }
  catch (error) {
    console.error(error)
  }
}

// 사원리스트 조회
const fetchEmpList = async () => {
  try {
    const res = await axios.get('http://localhost:8282/hr/affair/emp')

    console.log('gaga', res.data)

    const empData = res.data.empList

    empList.value = empData.map((e: any) => ({
      title: e.empName,
      value: e.empCode,
    }))
    console.log('[empList]', empList.value)
  }
  catch (error) {
    console.error(error)
  }
}

const searchEmpEval = async () => {
  const evalEmpData = {
    searchYear: searchYear.value,
    empCode: searchEmp.value,
  }

  await store.SEARCH_EMP_EVAL(evalEmpData)
  evalList.value = await store.evalList
}

watch([searchEmp, searchDept, positionName, durationOfTraining, attendanceStatus, numberOfCertificate, numberOfRestDay, remainingHoliday, disqualification, career, score, grade, status], () => {
  evalList.value = [{
    empName: searchEmp.value,
    deptName: searchDept.value,
    positionName: positionName.value,
    durationOfTraining: durationOfTraining.value,
    attendanceStatus: attendanceStatus.value,
    numberOfCertificate: numberOfCertificate.value,
    numberOfRestDay: numberOfRestDay.value,
    remainingHoliday: remainingHoliday.value,
    disqualification: disqualification.value,
    career: career.value,
    score: score.value,
    grade: grade.value,
    status: '미승인',
  }]
}, { immediate: true }) // 처음 로드 시에도 바로 실행되게 { immediate: true }

// 80기
const selEmpEval = async () => {
  await evalStore().FETCH_EVALLIST()

  const evalListData = evalStore().evalList

  console.log('값', evalListData)
  positionNameList.value = evalListData ? Object.values(evalListData).map(item => item.positionName) : []
  durationOfTrainingList.value = evalListData ? Object.values(evalListData).map(item => item.durationOfTraining) : []
  numberOfCertificateList.value = evalListData ? Object.values(evalListData).map(item => item.numberOfCertificate) : []
  attendanceStatusList.value = evalListData ? Object.values(evalListData).map(item => item.attendanceStatus) : []
  positionNameList.value = evalListData ? Object.values(evalListData).map(item => item.positionName) : []
  numberOfRestDayList.value = evalListData ? Object.values(evalListData).map(item => item.numberOfRestDay) : []
  remainingHolidayList.value = evalListData ? Object.values(evalListData).map(item => item.remainingHoliday) : []
  disqualificationList.value = evalListData ? Object.values(evalListData).map(item => item.disqualification) : []
  careerList.value = evalListData ? Object.values(evalListData).map(item => item.career) : []
}

const filteredPositionNameList = computed(() => positionNameList.value.filter(item => item !== null && item !== ''))
const filteredDurationOfTrainingList = computed(() => durationOfTrainingList.value.filter(item => item !== null && item !== ''))
const filteredNumberOfCertificateList = computed(() => numberOfCertificateList.value.filter(item => item !== null && item !== ''))
const filteredAttendanceStatusList = computed(() => attendanceStatusList.value.filter(item => item !== null && item !== ''))
const filteredNumberOfRestDayList = computed(() => numberOfRestDayList.value.filter(item => item !== null && item !== ''))
const filteredRemainingHolidayList = computed(() => remainingHolidayList.value.filter(item => item !== null && item !== ''))
const filteredDisqualificationList = computed(() => disqualificationList.value.filter(item => item !== null && item !== ''))
const filteredCareerList = computed(() => careerList.value.filter(item => item !== null && item !== ''))

const btnAddEmpEval = async () => {
  
  if (!searchYear.value || !searchDept.value || !searchEmp.value || !positionName.value || !numberOfCertificate.value || !attendanceStatus.value || !remainingHoliday.value || !disqualification.value || !career.value) {
    alert('모든 슬롯 창을 선택하거나 값을 입력해주세요.')

    return
  }

  // 매핑 로직을 통해 텍스트 필드 값과 슬롯 값을 적절히 반영
  const adjustedPayload = {
    positionName: positionName.value, // 단일 값 사용
    durationOfTraining: durationOfTraining.value,
    numberOfCertificate: numberOfCertificate.value,
    attendanceStatus: attendanceStatus.value,
    numberOfRestDay: numberOfRestDay.value,
    remainingHoliday: remainingHoliday.value,
    disqualification: disqualification.value,
    career: career.value,
  }

  const evalEmpData = {
    searchYear: searchYear.value,
    deptName: searchDept.value,
    empName: searchEmp.value,
    positionName: adjustedPayload.positionName || positionName.value, // 값이 없다면 텍스트 필드 값
    durationOfTraining: adjustedPayload.durationOfTraining || durationOfTraining.value,
    numberOfCertificate: adjustedPayload.numberOfCertificate || numberOfCertificate.value,
    attendanceStatus: adjustedPayload.attendanceStatus || attendanceStatus.value,
    numberOfRestDay: adjustedPayload.numberOfRestDay || numberOfRestDay.value,
    remainingHoliday: adjustedPayload.remainingHoliday || remainingHoliday.value,
    disqualification: adjustedPayload.disqualification || disqualification.value,
    career: adjustedPayload.career || career.value,
  }

  console.log('evalEmpData:', evalEmpData) // evalEmpData 값 로그 확인

  try {
    // store의 ADD_EMP_EVAL 액션 호출
    await store.ADD_EMP_EVAL(evalEmpData)

    // store에서 변경된 evalList 값 가져오기
    evalList.value = store.evalList

    // 성공적으로 저장된 후 업데이트된 evalList 출력
    console.log('Updated evalList:', evalList.value)
  }
  catch (error) {
    console.error('Error in btnAddEmpEval', error)
  }
}

const updateItem = (value: string, field: string) => {
  if (value && !field.includes(value)) {
      field.push(value); // 중복되지 않게 추가
      }
}

watch(attendanceStatus, (newVal) => {
  if (newVal === '자주 지각') {
    showTardyModal.value = true
  }
})

const applyTardy = () => {
  attendanceStatus.value = `자주 지각 (${selectedTardyCount.value})`
  showTardyModal.value = false
}

onMounted(() => {
  fetchYear()
  fetchDeptList()
  fetchEmpList()
  selEmpEval()
})
</script>

<template>
  <div>
    <h1 class="mb-6">인사고과 대상자 추가</h1>
    <VCard class="pa-6 mb-6" elevation="2">
      <!--
        <VCard
        class="mb-6"
        title="인사고과 조회"
        >
      -->
      <VRow>
      <VCol cols="12" md="4">
        <VSelect v-model="searchYear" class="ma-3" label="검색년도" :items="yearList" variant="outlined" />
      </VCol>
      <VCol cols="12" md="4">
        <VSelect v-model="searchDept" class="ma-3" label="부서명" :items="deptList" variant="outlined" />
      </VCol>
      <VCol cols="12" md="4">
        <VSelect v-model="searchEmp" class="ma-3" label="사원명" :items="empList" variant="outlined" />
      </VCol>
    </VRow>
    <VRow>
      <VCol>
        <VSelect v-model="positionName" class="ma-3" label="직급" :items="filteredPositionNameList" variant="outlined" />
      </VCol>
      <VCol>
        <VSelect v-model="durationOfTraining" class="ma-3" label="교육" :items="filteredDurationOfTrainingList" variant="outlined" />
      </VCol>
      <VCol>
        <VSelect v-model="numberOfCertificate" class="ma-3" label="자격증" :items="filteredNumberOfCertificateList" variant="outlined" />
      </VCol>
    </VRow>
    <VRow>
      <VCol>
      <VSelect v-model="attendanceStatus" class="ma-3" label="근태" :items="filteredAttendanceStatusList" variant="outlined" />
    </VCol>
    <VCol>
    <VSelect v-model="numberOfRestDay" class="ma-3" label="남은 휴가" :items="filteredNumberOfRestDayList" variant="outlined" />
    </VCol>
    <VCol>
      <VSelect v-model="remainingHoliday" class="ma-3" label="휴가" :items="filteredRemainingHolidayList" variant="outlined" />
    </VCol>
    </VRow>
    <VRow>
      <VCol cols="12" md="2">
      <VSelect v-model="disqualification" class="ma-3" label="결격여부" :items="filteredDisqualificationList" variant="outlined" />
    </VCol>
    <VCol cols="12" md="2">
      <VSelect v-model="career" class="ma-3" label="근속연수" :items="filteredCareerList" variant="outlined" />
    </VCol>
    </VRow>

    <VRow class="mt-4 justify-end">
      <VBtn class="ml-3" color="primary" @click="btnAddEmpEval">
        사원 추가
      </VBtn>
    </VRow>
    </VCard>

    <VCard class="pa-6" elevation="1">
      <VDataTable class="mt-10" :headers="headers" :items="evalList">
        <template v-slot:[`item.empName`]="{ item }">
          <!-- 사원명 텍스트 필드 -->
          <v-text-field v-model="item.empName" :label="item.empName || '이름'" dense />
        </template>
        <template v-slot:[`item.deptName`]="{ item }">
          <v-text-field v-model="item.deptName" :label="item.deptName || '부서'" dense />
        </template>
        <template v-slot:[`item.positionName`]="{ item }">
          <!-- 직급 텍스트 필드 -->
          <v-text-field v-model="item.positionName" :label="item.positionName || '직급'" dense />
        </template>
        <template v-slot:[`item.durationOfTraining`]="{ item }">
          <v-text-field v-model="item.durationOfTraining" :label="item.durationOfTraining || '교육'" dense />
        </template>
        <template v-slot:[`item.numberOfCertificate`]="{ item }">
          <v-text-field v-model="item.numberOfCertificate" :label="item.numberOfCertificate || '자격증'" dense />
        </template>
        <template v-slot:[`item.attendanceStatus`]="{ item }">
          <v-text-field v-model="item.attendanceStatus" :label="item.attendanceStatus || '근태'" dense />
        </template>
        <template v-slot:[`item.numberOfRestDay`]="{ item }">
          <v-text-field v-model="item.numberOfRestDay" :label="item.numberOfRestDay || '남은 휴가'" dense />
        </template>
        <template v-slot:[`item.remainingHoliday`]="{ item }">
          <v-text-field v-model="item.remainingHoliday" :label="item.remainingHoliday || '휴가'" dense />
        </template>
        <template v-slot:[`item.disqualification`]="{ item }">
          <v-text-field v-model="item.disqualification" :label="item.disqualification || '결격여부'" dense />
        </template>
        <template v-slot:[`item.career`]="{ item }">
          <v-text-field v-model="item.career" :label="item.career || '근속연수'" dense />
        </template>
        <template v-slot:[`item.score`]="{ item }">
          <v-text-field v-model="item.score" :label="item.score || '총 점수'" dense disabled />
        </template>
        <template v-slot:[`item.grade`]="{ item }">
          <v-text-field v-model="item.grade" :label="item.grade || '등급'" dense disabled />
        </template>
      </VDataTable>

    </VCard>
    <br>
    <VCard title="<등급 산정 기준>">
      <VCardText>
        <span>근태</span>
        <p>결근 없음, +20 / 1~2일 결근, +16 / 3~4일 결근, +12 / 5일 이상 결근, +8 / 자주 지각, +10 이하</p>
        <span>직급</span>
        <p>사원, +10 / 대리, +12 / 과장, +15 / 차장, +18 / 부장 이상, +20</p>
        <span>결격(징계 등)여부</span>
        <p>결격 사유 없음, +10 / 결격 사유 있음, +0</p>
        <span>교육이수</span>
        <p>직무 관련 교육 이수 5회 이상, +15 / 직무 관련 교육 이수 3~4회, +10 / 직무 관련 교육 이수 1~2회, +6 / 직무 관련 교육 이수내역 없음, +0</p>
        <span>근속년수</span>
        <p>10년 이상, +10 / 5~9년 근속, +7 / 1~4년 근속, +5 / 1년 미만 근속, +2</p>
        <span>자격증</span>
        <p>직무 관련 자격증 보유, +15 / 일부 관련 자격증 보유, +6 / 자격증 없음, +0</p>
        <span>휴가 사용</span>
        <p>휴가 사용 적절, +10 / 과도한 휴가 사용, +5 / 휴가 사용 적지 않음, +2</p>
        <span>등급산정</span>
        <p>
          90점 이상, S / 90점 미만 ~ 80점 이상, A / 80점 미만 ~ 70점 이상, B / 70점 미만 ~ 60점 이상, C / 60점 미만 ~ 50점 이상, D / 50점 미만, E
        </p>
      </VCardText>
    </VCard>
    <!-- 👇 모달 추가 -->
<v-dialog v-model="showTardyModal" max-width="400px">
  <v-card>
    <v-card-title>지각 횟수를 선택해주세요</v-card-title>
      <v-card-text>
      <v-text-field
        v-model="selectedTardyCount"
        type="number"
        placeholder="숫자를 입력하세요"
        variant="outlined"
       />
</v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn text @click="showTardyModal = false">취소</v-btn>
      <v-btn color="primary" @click="applyTardy">적용</v-btn>
    </v-card-actions>
  </v-card>
</v-dialog>
  </div>
</template>
