import { hrApi } from '@/api'

// Api
const APPO_URL = '/appo'

/**
 * 인사발령 요청 조회
 */
function findAppoRequest() {
  return hrApi.get(`${APPO_URL}/searchAppoRequest`, {
  })
}

/**
 * 인사발령 요청
 */
function registerAppointment(payload: any) {
  return hrApi.post(`${APPO_URL}/registerAppo`, payload)
}

/**
 * 인사발령 요청 수정
 */
function editAppoRequest(payload: any) {
  return hrApi.post(`${APPO_URL}/updateAppo`, payload)
}

/**
 * 인사발령 승인/반려
 */
function approveAppointment(payload: any) {
  return hrApi.post(`${APPO_URL}/approveAppo`, payload)
}

/**
 * 인사발령 이력 조회
 */
function findAllAppoLog() {
  return hrApi.get(`${APPO_URL}/searchAllAppoLog`)
}

/**
 * 인사발령 이력 년도별 조회
 */
function findAppoLogByYear(searchYear: any) {
  return hrApi.get(`${APPO_URL}/searchAppoLogByYear`, {
    params: { searchYear },
  })
}

/**
 * 인사발령 이력 삭제
 */
function removeAppoLog(payload: any) {
  return hrApi.delete(`${APPO_URL}/deleteAppoLog`, {
    params: {
      logSeq: payload.logSeq,
    },
  })
}

export { approveAppointment, editAppoRequest, findAllAppoLog, findAppoLogByYear, findAppoRequest, registerAppointment, removeAppoLog }
