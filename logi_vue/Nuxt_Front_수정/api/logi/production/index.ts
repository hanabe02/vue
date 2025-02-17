import { logiApi } from "@/api";

const WORKORDER_MRP_LIST_URL = "/production/getWorkOrderableMrpList";
const ADD_WORKORDER_DIALOG_URL = "/production/showWorkOrderDialog";
const ACTUAL_WORKORDER_BUTTON_URL = "/production/workOrder";
const WORKORDER_LIST_STATUS_URL = "/production/getWorkOrderInfoListStatus";
const EDIT_ACTUAL_AMOUNT_URL = "production/editActualAmount";
const WORKORDER_COMPLETION_URL = "production/workOrderCompletion";
const WORK_PERFORMANCE_MANAGEMENT_URL = "production/getProductionPerformanceInfoList";
const WORKPLACE_URL = "/production/getWorkSiteList";
const WORKPLACE_LOG_URL = "/production/getProductionProcessCode";

//작업지시 필요항목 조회
function getWorkOrderMrpList() {
  return logiApi.get(`${WORKORDER_MRP_LIST_URL}`);
}

//작업지시 모의전개
function getAddWorkOrderDialog(mrpNo:string, mrpGatheringNo:string) {
  return logiApi.get(`${ADD_WORKORDER_DIALOG_URL}`, {
    params: {
      mrpNo,
      mrpGatheringNo,
    },
  });
}

//실제 작업지시
function getActualWorkOrderButton( mrpGatheringNo: string, workPlaceCode: string, productionProcessCode: string ) {
  return logiApi.get(`${ACTUAL_WORKORDER_BUTTON_URL}`, {
    params: {
      mrpGatheringNo, 
      workPlaceCode, 
      productionProcessCode,
    },
  });
}

//작업지시현황 조회 
function getWorkOrderListStatus() {
  return logiApi.get(`${WORKORDER_LIST_STATUS_URL}`);
}

//작업완료된 수량 입력
function putActualAmount(workOrderInfoTO: any) {
  return logiApi.put(`${EDIT_ACTUAL_AMOUNT_URL}`, workOrderInfoTO);
}

//작업완료 등록
function getWorkOrderCompletion(workOrderNo:string, actualCompletionAmount:any ) {
  return logiApi.get(`${WORKORDER_COMPLETION_URL}`, {
    params: {
      workOrderNo, 
      actualCompletionAmount, 
    },
  });
}

//생산실적 조회 
function getWorkPerformanceManagement() {
  return logiApi.get(`${WORK_PERFORMANCE_MANAGEMENT_URL}`);
}

//작업장 조회
function getWorkPlace() {
  return logiApi.get(`${WORKPLACE_URL}`);
}

//작업장로그 조회
function getWorkPlaceLog(productionProcessCode:string, workSiteName:string) {
  return logiApi.get(`${WORKPLACE_LOG_URL}`, {
    params: {
      productionProcessCode,
      workSiteName,
    },
  });
}

export{getWorkPlace , getWorkPlaceLog, getWorkOrderMrpList, getAddWorkOrderDialog, getActualWorkOrderButton, getWorkOrderListStatus, putActualAmount, getWorkOrderCompletion, getWorkPerformanceManagement}