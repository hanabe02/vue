import { hrApi } from '@/api'

// Api
const EDU_URL = '/edu'

/**
 * 교육 관리 및 승인요청
 */
function approReqEdu(payload: any) {
  console.log('payload', payload)

  return hrApi.post(`${EDU_URL}/approReqEdu`, payload)
}

/**
 * 교육 관리 승인상태 조회
 */
function findEduListByStatus(payload: any) {
  console.log('payload', payload)

  return hrApi.get(`${EDU_URL}/searchEduListByStatus`, {
    params: payload,
  })
}

/**
 * 교육 관리 내역 삭제
 */
function removeEduList(payload: any) {
  return hrApi.delete(`${EDU_URL}/removeEduList`, {
    params: {
      searchYear: payload.searchYear,
      eduCode: payload.eduCode,
      empCode: payload.empCode,
      status: payload.status,
    },
  })
}

export {
  approReqEdu, findEduListByStatus, removeEduList,
}