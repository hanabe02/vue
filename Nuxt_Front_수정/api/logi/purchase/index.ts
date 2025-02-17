import { logiApi } from "@/api";

const SEARCH_BOM_DEPLOY_URL = "/purchase/searchBomDeploy";
const SEARCH_BOM_INFO_URL = "/purchase/searchBomInfo";
const BATCH_BOMLIST_PROCESS_URL = "/purchase/batchBomListProcess";
const BOMDATA_BATCH_URL = "/purchase/bomdata/batch";

const SEARCH_ORDERLIST_URL = "/purchase/getOrderList";
const SHOW_ORDER_DIALOG_URL = "/purchase/showOrderDialog";
const ORDER_AND_INV_URL = "/purchase/order";
const OPTION_ORDER_URL = "/purchase/optionOrder";
const SHOW_ORDER_INFO_URL = "/purchase/showOrderInfo";
const SEARCH_STOCK_LIST_URL = "/purchase/searchStockList";
const SEARCH_ORDER_LIST_ONDLVRY_URL = "/purchase/searchOrderInfoListOnDelivery";
const ORDERLIST_INSPECTION_URL = "/purchase/inspection";
const ORDERLIST_WAREHOUSING_URL = "/purchase/warehousing";
const SEARCH_STOCK_LOG_LIST_URL = "/purchase/searchStockLogList";

//BOM 조회
function getSearchBomDeploy(deployCondition: string, itemClassificationCondition: string, itemCode: string ) {
  return logiApi.get(`${SEARCH_BOM_DEPLOY_URL}`, {
    params: {
        deployCondition,
        itemClassificationCondition,
        itemCode,
    },
  });
}

//BOM 등록/수정 조회
function getSearchBomInfo(parentItemCode: any ) {
  return logiApi.get(`${SEARCH_BOM_INFO_URL}`, {
    params: {
      parentItemCode
    },
  });
}

//BOM 새로운 항목 추가
function addBatchBomListProcess(batchBomList: any[]) {
  return logiApi.post(`${BATCH_BOMLIST_PROCESS_URL}`, batchBomList)
}

//BOM 항목 삭제
function deleteBomdataBatch(batchBomList: any[]) {
  return logiApi.post(`${BOMDATA_BATCH_URL}`, batchBomList)
}

//재고/발주목록 조회 
function getSearchOrderList( startDate: any, endDate: any ) {
  return logiApi.get(`${SEARCH_ORDERLIST_URL}`, {
    params: {
      startDate, 
      endDate
    },
  });
}

//모의재고처리 및 취합발주
function getShowOrderDialog( mrpNoList:string ) {
  return logiApi.get(`${SHOW_ORDER_DIALOG_URL}`, {
    params: {
      mrpNoList
    },
  });
}

//현재 전개된 결과 발주 및 재고처리
function getOrderAndInv( mrpGatheringNoList:string ) {
  return logiApi.get(`${ORDER_AND_INV_URL}`, {
    params: {
      mrpGatheringNoList
    },
  });
}

//임의 발주
function getOptionOrder( itemCode:any, itemAmount:any ) {
  return logiApi.get(`${OPTION_ORDER_URL}`, {
    params: {
      itemCode,
      itemAmount,
    },
  });
}

//발주현황 조회 
function getShowOrderInfo( startDate: any, endDate: any ) {
  return logiApi.get(`${SHOW_ORDER_INFO_URL}`, {
    params: {
      startDate, 
      endDate
    },
  });
}

//재고목록 조회  
function getSearchStockList() {
  return logiApi.get(`${SEARCH_STOCK_LIST_URL}`);
}

//오늘날짜로 입고품목 조회 
function getOrderListOnDlvry() {
  return logiApi.get(`${SEARCH_ORDER_LIST_ONDLVRY_URL}`);
}

//현재 체크된 발주품목 원재료 검사 
function putOrderListInspection(sendData:string) {
  return logiApi.put(`${ORDERLIST_INSPECTION_URL}`, {sendData});
}

//현재 체크된 발주품목 입고 
function getOrderListWarehousing(orderNoList:string) {
  return logiApi.get(`${ORDERLIST_WAREHOUSING_URL}`, {
    params: {
      orderNoList,
    },
  });
}

//재고로그목록 조회 
function getSearchStockLogList( startDate: any, endDate: any ) {
  return logiApi.get(`${SEARCH_STOCK_LOG_LIST_URL}`, {
    params: {
      startDate, 
      endDate
    },
  });
}

export{ getSearchBomDeploy, getSearchBomInfo, addBatchBomListProcess, deleteBomdataBatch, getSearchOrderList, getShowOrderDialog, getOrderAndInv, getOptionOrder, getShowOrderInfo, getSearchStockList, getOrderListOnDlvry, putOrderListInspection, getOrderListWarehousing, getSearchStockLogList }