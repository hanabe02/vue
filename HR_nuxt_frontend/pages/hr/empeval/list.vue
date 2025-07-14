<!-- 
 2025.04.25 어진욱 인사고과 조회 필터링 작업

 최초 데이터 바인딩은 onMounted에서 가져옴
  -->

<script setup lang="ts">
import { baseStore } from '@/store/hr/base'
import { evalStore } from '@/store/hr/eval'
import axios from 'axios'
import { ref } from 'vue'
import { VDataTable } from 'vuetify/labs/VDataTable'
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


const store = evalStore()
const searchYear = ref('')
const searchEmp = ref('')
const searchDept = ref('')

const yearList: any = ref([])
const deptList = ref([])
const empList = ref([])

const evalList: any = ref([])

const fetchYear = async () => {
  await baseStore().FETCH_YEARLIST()

  const yearListData = baseStore().yearList

  yearList.value = yearListData ? Object.values(yearListData).map(item => item.year) : []
}

const headers = [
  { title: '이름', key: 'empName' },
  { title: '부서', key: 'deptName' },
  { title: '직급', key: 'positionName' },
  { title: '교육', key: 'durationOfTraining' },
  { title: '자격증', key: 'numberOfCertificate' },
  { title: '근태', key: 'numberOfRestDay' },
  { title: '휴가', key: 'remainingHoliday' },
  { title: '결격여부', key: 'disqualification' },
  { title: '장기근속', key: 'career' },
  { title: '총 점수', key: 'score' },
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

const searchDeptEval = async () => {
  const empEvalDeptData = {
    searchYear: searchYear.value,
    deptCode: searchDept.value,
  }

  console.log(empEvalDeptData)
  await store.SEARCH_EMP_EVAL_DEPT(empEvalDeptData)
  evalList.value = await store.evalList
}

onMounted(() => {
  fetchYear()
  fetchDeptList()
  fetchEmpList()
})
</script>

<template>
  <div>
    <h1 class="mb-6">인사고과 조회</h1>
    <VCard>
      <!--
        <VCard
        class="mb-6"
        title="인사고과 조회"
        >
      -->
      <VSelect v-model="searchEmp" class="ma-3" label="사원명" :items="empList" variant="outlined" />
      <!-- <VSelect v-model="searchDept" class="ma-3" label="부서명" :items="deptList" variant="outlined" /> -->
      <VSelect v-model="searchYear" class="ma-3" label="검색년도" :items="yearList" variant="outlined" />
      <VBtn class="ml-3" color="primary" @click="searchEmpEval">
        사원 조회
      </VBtn>
      <VBtn class="ml-3" variant="tonal" @click="searchDeptEval">
        부서 조회
      </VBtn>
      <VDataTable class="mt-10" :headers="headers" :items="evalList" />
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
    <!-- <VCard title="<등급 산정 기준>">
      <VCardText>
        <span>근태</span>
        <p>
          지각 4회는 결근 1번으로 간주하며, 결근 1회 이하 / +10, 2~4회 +7 /
          4~6회 +4
        </p>
        <span>학력</span>
        <p>박사, +10 / 석사, +7 / 학사, +4</p>
        <span>결격(징계 등)여부</span>
        <p>유 = -30</p>
        <span>교육이수</span>
        <p>4회 이상, +10 / 3회, +7 / 1~2회, +4</p>
        <span>근속년수</span>
        <p>10년 이상, +10 / 7~9년, +7 / 3~6, +4</p>
        <span>자격증</span>
        <p>5개 이상, +10 / 4개, +8 / 3개, +6 / 2개, +4 / 1개, +2</p>
        <span>휴가 사용</span>
        <p>5일 미만, +10 / 5~10일 , +7 / 11~15일, +4</p>
        <span>등급산정</span>
        <p>
          55점 이상, S / 45~54, A / 35~44, B / 25~34, C / 15~24, D / 0~14, E
        </p>
      </VCardText>
    </VCard> -->
  </div>
</template>
