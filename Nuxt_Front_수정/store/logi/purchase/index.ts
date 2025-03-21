import { defineStore } from "pinia";
import {
    getSearchBomDeploy,
    getSearchBomInfo,
    addBatchBomListProcess,
    deleteBomdataBatch,

    getSearchOrderList,
    getShowOrderDialog,
    getOrderAndInv,
    getOptionOrder,
    getShowOrderInfo,
    getSearchStockList,
    getOrderListOnDlvry,
    putOrderListInspection,
    getOrderListWarehousing,
    getSearchStockLogList,
} from "@/api/logi/purchase/index";

export const purchaseStore = defineStore("purchaseStore", {
    state: () => ({
        SearchBomDeploy: [] as any, //BOM 조회 
        SearchBomInfo: [] as any, //BOM 등록/수정 조회 
        BatchBomListProcess: [] as any, //Bom 새로운 항목 추가 
        BomdataBatch: [] as any, //BOM 항목 삭제
        SearchOrderList: [] as any, //재고처리 및 발주조회
        ShowOrderDialog: [] as any, //모의재고처리 및 취합발주
        OrderAndInv: [] as any, //현재 전개된 결과 발주 및 재고처리
        OptionOrder: [] as any, //임의 발주 
        ShowOrderInfo: [] as any, //발주현황 조회
        SearchStockList: [] as any, //재고목록 조회 
        OrderListOnDlvry: [] as any, //오늘날짜로 입고
        OrderListInspection: [] as any, //현재 체크된 발주품목 원재료 검사 
        OrderListWarehousing: [] as any, //현재 체크된 발주품목 입고 
        SearchStockLogList: [] as any, //재고로그목록 조회 
    }),
    actions: {
      
      //BOM 조회
      async FETCH_SEARCH_BOM_DEPLOY(deployCondition: string, itemClassificationCondition: string, itemCode: string) {
        try {
          const response = await getSearchBomDeploy(deployCondition, itemClassificationCondition, itemCode);
          this.SearchBomDeploy = response.data;
          console.log("BOM조회1:", response.data);
          console.log("BOM조회2:", response.data.SearchBomDeploy);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //BOM 등록/수정 조회 
      async FETCH_SEARCH_BOM_INFO(parentItemCode:any ) {
        try {
          const response = await getSearchBomInfo(parentItemCode);
          this.SearchBomInfo = response.data;
          console.log("BOM등록/수정 조회:", response.data);
          
        } catch (error: any) {
          console.error(error);
        }
      },

       //Bom 새로운 항목 추가 
      async FETCH_BATCH_BOMLIST_PROCESS(batchBomList: any) {
        try {
          const response = await addBatchBomListProcess(batchBomList);
          console.log("새로운 항목 추가:", response.data);
  
          this.BatchBomListProcess = response.data;
        }
        catch (error) {
          console.error('Error fetching data:', error);
        }
      },

      //Bom 새로운 항목 추가 
      async FETCH_BOMDATA_BATCH(batchBomList: any) {
        try {
          const response = await deleteBomdataBatch(batchBomList);
          console.log("항목 삭제:", response.data);
  
          this.BomdataBatch = response.data;
        }
        catch (error) {
          console.error('Error fetching data:', error);
        }
      },

    
      //재고/발주목록 조회 
      async FETCH_SEARCH_ORDERLIST(startDate: any, endDate: any) {
        try {
          const response = await getSearchOrderList(startDate, endDate);
          this.SearchOrderList = response.data.gridRowJson;
          console.log("재고처리 및 발주조회:", response.data.gridRowJson);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //모의재고처리 및 취합발주
      async FETCH_SHOW_ORDER_DIALOG(mrpNoList:string) {
        try {
          const response = await getShowOrderDialog(mrpNoList);
          this.ShowOrderDialog = response.data.result;
          console.log("모의재고처리 및 취합발주:", response.data.result);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //현재 전개된 결과 발주 및 재고처리
      async FETCH_ORDER_AND_INV(mrpGatheringNoList:string) {
        try {
          const response = await getOrderAndInv(mrpGatheringNoList);
          this.OrderAndInv = response.data;
          console.log("현재전개된결과 발주 및 재고처리:", response.data);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //임의 발주
      async FETCH_OPTION_ORDER( itemCode:any, itemAmount:any ) {
        try {
          const response = await getOptionOrder(itemCode, itemAmount);
          this.OptionOrder = response.data;
          console.log("임의 발주:", response.data);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //발주현황 조회 
      async FETCH_SHOW_ORDER_INFO(startDate: any, endDate: any) {
        try {
          const response = await getShowOrderInfo(startDate, endDate);
          this.ShowOrderInfo = response.data.gridRowJson;
          console.log("발주현황 조회:", response.data.gridRowJson);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //재고목록 조회 
      async FETCH_SEARCH_STOCK_LIST() {
        try {
          const response = await getSearchStockList();
          this.SearchStockList = response.data.gridRowJson;
          console.log("재고목록 조회:", response.data.gridRowJson);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //오늘날짜로 입고품목 조회  
      async FETCH_SEARCH_ORDER_LIST_ONDLVRY() {
        try {
          const response = await getOrderListOnDlvry();
          this.OrderListOnDlvry = response.data.gridRowJson;
          console.log("오늘날짜로 입고품목 조회:", response.data.gridRowJson);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //현재 체크된 발주품목 원재료 검사
      async FETCH_ORDERLIST_INSPECTION(sendData:string) {
        try {
          const response = await putOrderListInspection(sendData);
          this.OrderListInspection = response.data;
          console.log("현재 체크된 발주품목 원재료 검사:", response.data);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //현재 체크된 발주품목 입고
      async FETCH_ORDERLIST_WAREHOUSING(orderNoList:string) {
        try {
          const response = await getOrderListWarehousing(orderNoList);
          this.OrderListWarehousing = response.data;
          console.log("현재 체크된 발주품목 입고:", response.data);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //재고로그목록 조회
      async FETCH_SEARCH_STOCK_LOG_LIST(startDate: any, endDate: any) {
        try {
          const response = await getSearchStockLogList(startDate, endDate);
          this.SearchStockLogList = response.data.gridRowJson;
          console.log("재고로그목록 조회:", response.data.gridRowJson);
          
        } catch (error: any) {
          console.error(error);
        }
      },
    }
  })