import { hrApi } from '@/api'

// Api
const EVAL_URL = '/eval'

/**
 * 인사고과 대상자 슬롯 가져오기
 */
function selEmpEvalList() {
  return hrApi.get(`${EVAL_URL}/selEmpEvalList`)
}

/**
 * 인사고과 대상자 추가
 */

function addEmpEval(payload: any) {
  return hrApi.post(`${EVAL_URL}/addEmpEval`, payload, {
    headers: {
      'Content-Type': 'application/json',
    }
  })
}

/**
 * 인사고과 조회
 */
function findEmpEval(payload: any) {
  return hrApi.get(`${EVAL_URL}/SearchEmpEval`, {
    params: payload,
  })
}

/**
 * 인사고과 부서별 조회
 */
function findEmpEvalByDept(payload: any) {
  return hrApi.get(`${EVAL_URL}/searchEmpEvalByDept`, {
    params: payload,
  })
}

/**
 * 인사고과 승인상태 조회
 */
function findEmpEvalByStatus(payload: any) {
  console.log(payload)

  return hrApi.get(`${EVAL_URL}/searchEmpEvalByStatus`, {
    params: payload,
  })
}

/**
 * 인사고과 갱신(조회 전 실행됨)
 */
function updateEmpEval(payload: any) {
  console.log(payload)

  return hrApi.post(`${EVAL_URL}/updateEmpEval`, payload)
}

/**
 * 인사고과 부서 전체 갱신(조회 전 실행됨)
 */
function updateEmpEvalByDept(payload: any) {
  console.log(payload)

  return hrApi.post(`${EVAL_URL}/updateEmpEvalByDept`, payload)
}

/**
 * 인사고과 삭제
 */
function removeEmpEval(payload: any) {
  return hrApi.delete(`${EVAL_URL}/removeEmpEval`, {
    params: {
      searchYear: payload.searchYear,
      empCode: payload.empCode,
    },
  })
}

/**
 * 인사고과 승인/반려
 */
function registerEvalStatus(payload: any) {
  console.log(payload)

  return hrApi.post(`${EVAL_URL}/registerEvalStatus`, payload)
}

// KPI 등록
function addKpiRegi(payload: any) {
  console.log(payload)

  return hrApi.post(`${EVAL_URL}/addKpiRegi`, payload)
}

// KPI 성과 등록

function addKpiPerf(payload: any) {
  console.log(payload)

  return hrApi.post(`${EVAL_URL}/addKpiPerf`, payload)
}

// KPI 성과 조회

function getKpiPerf(payload: any) {
  return hrApi.get(`${EVAL_URL}/getKpiPerf`, {
    params: payload,
  })
}

// KPI 목표 조회
function getKpiPlan(payload: any) {
  return hrApi.get(`${EVAL_URL}/getKpiPlan`, {
    params: payload,
  })
}

// KPI 목표 삭제
function removeKpiPlan(payload: any) {
  return hrApi.delete(`${EVAL_URL}/removeKpiPlan`, {
    params: {
      empCode: payload.empCode,
      targetYear: payload.targetYear,
      performanceIndicator: payload.performanceIndicator,
      status: payload.status,
    },
  })
}

// KPI 승인/반려
function editKpiStatus(payload: any) {
  console.log(payload)

  return hrApi.post(`${EVAL_URL}/editKpiStatus`, payload)
}

export {
  addKpiPerf, addKpiRegi,
  editKpiStatus, findEmpEval,
  findEmpEvalByDept, findEmpEvalByStatus,
  getKpiPerf, getKpiPlan,
  registerEvalStatus, removeEmpEval,
  removeKpiPlan, updateEmpEval,
  updateEmpEvalByDept, selEmpEvalList, addEmpEval,
}
