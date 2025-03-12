import { logiApi } from '@/api'

const CODE_URL = '/base/searchCodeList'
const ADD_CODE_URL = '/base/batchListProcess'
const UPDATE_CODE_URL = '/base/updateCode'
const DELETE_CODE_URL = '/base/deleteCode'

// 코드 조회
function getCode() {
  return logiApi.get(`${CODE_URL}`)
}

// 코드 추가
function addCode(codelist: any) {
  return logiApi.post(`${ADD_CODE_URL}`, codelist)
}

// 코드 수정
function updateCode(codeData: any) {
  return logiApi.put(`${UPDATE_CODE_URL}`, codeData)
}

// 코드 삭제
function deleteCode(divisionCodeNo: string) {
  return logiApi.delete(`${DELETE_CODE_URL}/${divisionCodeNo}`)
}

export { getCode, addCode, updateCode, deleteCode }
