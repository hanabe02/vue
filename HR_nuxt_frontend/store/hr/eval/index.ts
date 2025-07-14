import { defineStore } from 'pinia'
import {
  addKpiPerf, addKpiRegi, editKpiStatus,
  findEmpEval, findEmpEvalByDept, findEmpEvalByStatus, getKpiPerf,
  getKpiPlan, registerEvalStatus, removeEmpEval, removeKpiPlan, updateEmpEval, updateEmpEvalByDept,
  selEmpEvalList, addEmpEval,
} from '@/api/hr/empeval'

export const evalStore = defineStore('evalStore', {
  state: () => ({
    evalList: [{
      deptName: '',
      disqualification: '',
      durationOfTraining: '',
      empCode: '',
      empName: '',
      grade: '',
      lastSchool: '',
      numberOfCertificate: '',
      numberOfRestDay: '',
      positionName: '',
      remainingHoliday: '',
      score: '',
      searchYear: '',
      status: '',
      attendanceStatus: '',
    }],
    kpiList: [{
      empCode: '',
      targetYear: '',
      perfGoal: '',
      performanceIndicator: '',
      measurementCriteria: '',
      unit: '',
      weight: '',
      target: '',
      calcMethod: '',
      actual: '',
      achievementRate: '',
      status: '',
      score: '',
    }],
  }),
  actions: {
    /**
     * 인사고과 대상자 슬롯창
     */
    async FETCH_EVALLIST() {
      try {
        console.log('인사고과 대상자 슬롯가져오는 중')

        const response = await selEmpEvalList()

        this.evalList = response.data.selEvalList

        console.log('슬롯 값 가져옴', response.data)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사고과 대상자 추가
     */
    async ADD_EMP_EVAL(payload: any) {
      try {
        console.log('대상자 추가중', payload)

        const response = await addEmpEval(payload)

        this.evalList = response.data.selEmpEvalList

        alert("추가완료되었습니다")

        console.log('추가완료', response.data)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사고과 조회
     */
    async SEARCH_EMP_EVAL(payload: any) {
      try {
        console.log('인사고과 조회')

        const response = await findEmpEval(payload)

        this.evalList = response.data.evalList

        console.log(response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사고과 부서별 조회
     */
    async SEARCH_EMP_EVAL_DEPT(payload: any) {
      try {
        console.log('인사고과 부서별 조회')

        const response = await findEmpEvalByDept(payload)

        this.evalList = response.data.evalList
        console.log(response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사고과 승인상태별 조회
     */
    async SEARCH_EMP_EVAL_STATUS(payload: any) {
      try {
        console.log('인사고과 승인여부 조회')

        const response = await findEmpEvalByStatus(payload)

        this.evalList = response.data.evalList
        console.log(response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사고과 갱신
     */
    async UPDATE_EMP_EVAL(payload: any) {
      try {
        console.log('인사고과 갱신')

        const response = await updateEmpEval(payload)
        if (response.data.errorCode !== -1)
          return response.data.errorCode

        else
          alert('고과갱신에 실패하였습니다.')
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사고과 부서별 갱신
     */
    async UPDATE_EMP_EVAL_DEPT(payload: any) {
      try {
        console.log('인사고과 부서별 갱신')

        const response = await updateEmpEvalByDept(payload)
        if (response.data.errorCode !== -1)
          return response.data.errorCode

        else
          alert('부서별 갱신에 실패하였습니다.')
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사고과 삭제
     */
    async DELETE_EMP_EVAL(payload: any) {
      try {
        console.log('인사고과 삭제')

        return await removeEmpEval(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사고과 승인/반려
     */
    async EDIT_EMP_EVAL(payload: any) { // 객체로 넘어오면 {}
      try {
        console.log('인사고과 승인/반려')

        const response = await registerEvalStatus(payload)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 사원 KPI 목표 등록
     */
    async insertEmpKpi(payload: any) { // 객체로 넘어오면 {}
      console.log('KPI 목표 등록 액션', payload)
      try {
        const response = await addKpiRegi(payload)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * KPI 성과 등록
     */
    async insertKpiPerf(payload: any) { // 객체로 넘어오면 {}
      console.log('KPI 성과 등록 액션', payload)
      try {
        const response = await addKpiPerf(payload)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * KPI 성과 조회
     */
    async fetchKpiPerf(payload: any) {
      try {
        console.log('KPI 성과 조회 액션', payload)

        const response = await getKpiPerf(payload)

        this.kpiList = response.data.kpiList
        console.log(response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * KPI 목표 조회
     */
    async fetchKpiPlan(payload: any) {
      try {
        console.log('KPI 목표 조회')

        const response = await getKpiPlan(payload)

        this.kpiList = response.data.kpiList
        console.log(response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * KPI 목표 삭제
     */
    async deleteKpiPlan(payload: any) {
      try {
        console.log('KPI 목표 삭제')

        return await removeKpiPlan(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * KPI 승인/반려
     */
    async updateKpiPlan(payload: any) { // 객체로 넘어오면 {}
      try {
        console.log('KPI 승인/반려')

        const response = await editKpiStatus(payload)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
  },
})
