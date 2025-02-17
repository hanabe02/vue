import { logiApi } from '@/api'

const All_TRANSPORTATION_URL = '/trans/allTransVehicleList'
const TRANSPORTATION_URL = '/trans/getTransVehicleList' // TYPE별 조회
const ADD_TRANSPORTATION_URL = '/trans/addTransportation'
const UPDATE_TRANSPORTATION_URL = '/trans/updateTransportation'
const DELETE_TRANSPORTATION_URL = '/trans/deleteTransportation'

// 운송수단 전체 조회
function allTransportation() {
  return logiApi.get(`${All_TRANSPORTATION_URL}`)
}

// 운송수단  type별 조회
function getTransportation(type: string) {
  return logiApi.get(`${TRANSPORTATION_URL}/${type}`)
}

// 운송수단 추가
function addTransportation(AddList: object) {
  return logiApi.post(`${ADD_TRANSPORTATION_URL}`, AddList)
}

// 운송수단 수정
function updateTransportation(transList: any) {
  return logiApi.put(`${UPDATE_TRANSPORTATION_URL}`, transList)
}

// 운송수단 삭제
function deleteTransportation(vehicleNumber: string) {
  return logiApi.delete(`${DELETE_TRANSPORTATION_URL}/${vehicleNumber}`)
}

export {
  allTransportation,
  getTransportation,
  addTransportation,
  updateTransportation,
  deleteTransportation,
}
