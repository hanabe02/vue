import { logiApi } from '@/api'

const ITEM_URL = '/base/searchItemList'
const ADD_ITEM_URL = '/base/itemBatchListProcess'
const UPDATE_ITEM_URL = '/base/updateItem'
const DELETE_ITEM_URL = '/base/deleteSelectedItem'
const ITEMGROUP_URL = '/base/searchItemGroupList'
const ADD_ITEMGROUP_URL = '/base/itemGroupBatchListProcess'
const UPDATE_ITEMGROUP_URL = '/base/updateItemGroup'
const DELETE_ITEMGROUP_URL = '/base/deleteSelectedItemGroup'

// 품목 조회
function getItem() {
  return logiApi.get(`${ITEM_URL}`)
}

// 품목 추가
function addItem(batchList: any) {
  return logiApi.post(`${ADD_ITEM_URL}`, batchList)
}

// 품목 수정
function updateItem(batchList: any) {
  return logiApi.put(`${UPDATE_ITEM_URL}`, batchList)
}

// 품목 삭제
function deleteItem(itemCode: string) {
  return logiApi.delete(`${DELETE_ITEM_URL}/${itemCode}`)
}

////////////////////////////////////////

// 품목군 조회
function getItemGroup() {
  return logiApi.get(`${ITEMGROUP_URL}`)
}

// 품목군 추가
function addItemGroup(batchList: any) {
  return logiApi.post(`${ADD_ITEMGROUP_URL}`, batchList)
}

// 품목군 수정
function updateItemGroup(batchList: any) {
  return logiApi.put(`${UPDATE_ITEMGROUP_URL}`, batchList)
}

// 품목군 삭제
function deleteItemGroup(itemCode: string) {
  return logiApi.delete(`${DELETE_ITEMGROUP_URL}/${itemCode}`)
}

export {
  getItem,
  addItem,
  updateItem,
  deleteItem,
  getItemGroup,
  addItemGroup,
  updateItemGroup,
  deleteItemGroup,
}
