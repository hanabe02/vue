import { logiApi } from '@/api'

const OUTPUT_URL = '/sales/getOutputList'
const GET_CLIENT_DELIVERY_URL = '/sales/getClientDelivery'
const ADD_CLIENT_DELIVERY_URL = '/sales/addClientDelivery'
const UPDATE_CLIENT_DELIVERY_URL = '/sales/updateClientDelivery'
const DELETE_CLIENT_DELIVERY_URL = '/sales/deleteClientDelivery'

// 배송 조회
function getClientDeliveryList() {
  return logiApi.get(`${GET_CLIENT_DELIVERY_URL}`);
}

// 배송 등록
function addClientDelivery(addData: any) {
  return logiApi.post(`${ADD_CLIENT_DELIVERY_URL}`, addData)
}

// 배송 수정
function updateClientDelivery(addData: any) {
  return logiApi.put(`${UPDATE_CLIENT_DELIVERY_URL}`, addData);
}

// 배송 삭제
function deleteClientDelivery(deliveryNumber: string) {
  return logiApi.delete(`${DELETE_CLIENT_DELIVERY_URL}/${deliveryNumber}`)
}

// 배송을 위한 출고 조회
function getOutputList() {
  return logiApi.get(`${OUTPUT_URL}`)
}

export {
  getClientDeliveryList,
  addClientDelivery,
  updateClientDelivery,
  deleteClientDelivery,
  getOutputList,
};
