import { defineStore } from "pinia";
import {
    getWorkOrderMrpList,
    getAddWorkOrderDialog,
    getActualWorkOrderButton,
    getWorkOrderListStatus, 
    putActualAmount,
    getWorkOrderCompletion,
    getWorkPerformanceManagement,
    getWorkPlace,
    getWorkPlaceLog,
} from "@/api/logi/production/index";

export const productionStore = defineStore("productionStore", {
  state: () => ({
    WorkOrderMrpList: [] as any, //작업지시 필요항목 조회 
    AddWorkOrderDialog : [] as any, //작업지시 모의전개 
    ActualWorkOrderButton: [] as any, //실제 작업지시
    WorkOrderListStatus: [] as any, //작업지시현황 조회 
    ActualAmount: [] as any, //작업완료된 수량 입력
    WorkOrderCompletion: [] as any, //작업완료 등록
    WorkPerformanceManagement: [] as any, //생산실적 조회
    WorkSiteList: [] as any, // 작업장 리스트
    WorkSiteLogList: [] as any, // 작업장로그 리스트

  }),
  actions: {
    // 작업지시 필요항목 조회
    async FETCH_WORKORDER_MRP_LIST() {
      try {
        const response = await getWorkOrderMrpList();
        this.WorkOrderMrpList = response.data.gridRowJson;
      
      } catch (error: any) {
        console.error(error);
      }
    },
    
    // 작업지시 모의전개 
    async FETCH_ADD_WORKORDER_DIALOG(mrpNo:string, mrpGatheringNo:string) {
      try {
        const response = await getAddWorkOrderDialog(mrpNo, mrpGatheringNo);
        this.AddWorkOrderDialog = response.data.result;
        console.log("모의전개:", response.data.result);
      
      } catch (error: any) {
        console.error(error);
      }
    },

    // 실제 작업지시
    async FETCH_ACTUAL_WORKORDER_BUTTON(mrpGatheringNo: string, workPlaceCode: string, productionProcessCode: string ) {
      try {
        const response = await getActualWorkOrderButton(mrpGatheringNo, workPlaceCode, productionProcessCode );
        this.ActualWorkOrderButton = response.data;
        console.log("작업지시:", response.data );

      } catch (error: any) {
        console.error(error);
      }
    },

    //작업지시현황 조회 
    async FETCH_WORKORDER_LIST_STATUS() {
      try {
        const response = await getWorkOrderListStatus();
        this.WorkOrderListStatus = response.data;
        console.log("작업지시현황조회:", response.data );
      
      } catch (error: any) {
        console.error(error);
      }
    },  

    //작업완료된 수량 입력  
    async FETCH_EDIT_ACTUAL_AMOUNT({workOrderNo, actualCompletionAmount}: any) {
      try {
        const responsePut = await putActualAmount({workOrderNo, actualCompletionAmount});
        console.log("작업완료된수량:", responsePut.data );

        if (responsePut.data.errorCode !== 0) {
          console.log("작업완료된 수량에서 에러. 에러코드: " + responsePut.data.errorCode);
          return;  // 데이터 업데이트 중지
        }
        
        this.ActualAmount = responsePut.data;
      } catch (error: any) {
        console.error(error);
        // 에러가 발생한 경우에는 여기에서 함수를 종료시키고 데이터 업데이트를 하지 않도록 수정
        return;
      }
    },

    //작업완료 등록  
    async FETCH_WORKORDER_COMPLETION(workOrderNo:any, actualCompletionAmount:any) {
      try {
        const responseGet = await getWorkOrderCompletion(workOrderNo, actualCompletionAmount);
        console.log("작업완료등록:", responseGet.data );

        if (responseGet.data.errorCode !== 0) {
          console.log("작업완료 등록에서 에러. 에러코드: " + responseGet.data.errorCode);
          return;  // 데이터 업데이트 중지
        }

        this.WorkOrderCompletion = responseGet.data;
      } catch (error: any) {
        console.error(error);
        // 에러가 발생한 경우에는 여기에서 함수를 종료시키고 데이터 업데이트를 하지 않도록 수정
        return;
      }
    }, 

    //작업지시현황 조회 
    async FETCH_WORK_PERFORMANCE_MANAGEMENT() {
      try {
        const response = await getWorkPerformanceManagement();
        this.WorkPerformanceManagement = response.data;
        console.log("생산실적조회:", response.data );
      
      } catch (error: any) {
        console.error(error);
      }
    }, 

    // 작업장 조회
    async FETCH_WORKPLACE() {
      try {
        const response = await getWorkPlace();
        this.WorkSiteList = response.data.WorkSiteList;
      
      } catch (error: any) {
        console.error(error);
      }
    },

    // 작업장로그 조회
    async FETCH_WORKPLACE_LOG(productionProcessCode:string, workSiteName:string) {
      try {
        const response = await getWorkPlaceLog(productionProcessCode, workSiteName);
        this.WorkSiteLogList = response.data;
      
      } catch (error: any) {
        console.error(error);
      }
    },
  }
})