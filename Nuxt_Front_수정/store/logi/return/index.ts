import { defineStore } from "pinia";
import {
    getReturnOutsList,
    getReturnOutsAllList,
    addReturnOuts,
    completeReturnOuts,
    getReturnList,
    getReturnListForStock,
    updateReturn,
    deleteReturn,
    addReturn,
    searchReturnStockList,
    addReturnStock,
    deleteReturnStock,

} from "@/api/logi/return/index";

export const returnStore = defineStore("returnStore", {
    state: () => ({
      getReturnOutsList: [] as any, //외주반품요청 조회,
      getReturnOutsAllList: [] as any, //외주반품요천 전부 조회,
      addReturnOuts: [] as any, //기타외주반품 추가,
      completeReturnOuts: [] as any, //외주반품요청 처리,
        getReturnList: [] as any, //반품요청 조회
        returnListForStock: [] as any, //반품요청 조회
        updateReturn: [] as any, //반품요청 수정
        deleteReturn: [] as any, //반품요청 취소 
        addReturn: [] as any, //반품요청 등록 
        searchReturnStockList: [] as any, //반품재고입고요청 조회
        addReturnStock: [] as any, //반품재고입고요청 등록
        deleteReturnStock: [] as any, //반품재고입고요청 삭제 
    }),
    actions: {
      
      //외주반품재고 조회
      async FETCH_GET_RETURNOUTSLIST() {
        try {
        
          const response = await getReturnOutsList();      
          this.getReturnOutsList = response.data.gridRowJson;    
          console.log("외주반품요청 조회:", response.data.gridRowJson);
        }
        catch (error) {
          console.error('Error fetching data:', error)
        }
      },

            //외주반품재고 전부 조회
            async FETCH_GET_RETURNOUTSALLLIST() {
              try {
                const response = await getReturnOutsAllList();       
                this.getReturnOutsAllList = response.data.gridRowJson;       
                console.log("외주반품요청 전부 조회:", response.data.gridRowJson);
      
              }
              catch (error) {
                console.error('Error fetching data:', error)
              }
            },

            //기타반품재고 등록
            async FETCH_ADD_RETURNOUTS(returnOuts: any) {
              try {
                const response = await addReturnOuts(returnOuts);
                this.addReturnOuts=response.data;          
                console.log("기타반품요청 등록:", response.data);
              }
              catch (error) {
                console.error('Error fetching data:', error)
              }
            },

            
            //기타반품재고 등록
            async FETCH_COMPLETE_RETURNOUTS(returnCode: any) {
              try {
                const response = await completeReturnOuts(returnCode);          
                this.completeReturnOuts = response.data;
                console.log("반품재고 처리:", response.data);
              }
              catch (error) {
                console.error('Error fetching data:', error)
              }
            },


      //반품요청 날짜별 조회
      async FETCH_GET_RETURNLIST(startDate: any, endDate: any ) {
        try {
          const response = await getReturnList(startDate, endDate)
          this.getReturnList = response.data.gridRowJson;
          console.log("반품요청 날짜별 조회:", response.data.gridRowJson);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //반품요청 조회
      async FETCH_GET_RETURN_LIST_FORSTOCK() {
        try {
          const response = await getReturnListForStock();
          this.returnListForStock = response.data;
          console.log("반품요청 조회:", response.data);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      //반품요청 수정
      async FETCH_UPDATE_RETURN(returnList: any) {
        try {
          const response = await updateReturn(returnList);
          this.updateReturn = response;
          console.log("반품요청 수정:", response.data);
    
        }
        catch (error) {
          console.error('Error fetching data:', error);
        }
      },
    
      //반품요청 취소 
      async FETCH_DELETE_RETURN(returnNo: string) {
        try {
          const response = await deleteReturn(returnNo);          
          console.log("반품요청 취소:", response.data);
  
          // this.deleteItemInfo = res.data.codeList;
        }
        catch (error) {
          console.error('Error fetching data:', error)
        }
      },

      // 반품요청 등록
      async FETCH_ADD_RETURN(params: Object) {
        console.log("params:", params);
        try {
          const response = await addReturn(params);
          console.log("response:", response);
          
          this.addReturn = response.data;
          console.log("반품요청 등록:", response.data);
       
        }
        catch (error) {
          console.error('Error fetching data:', error);
        }
      },

      //반품요청 날짜별 조회
      async FETCH_SEARCH_RETURN_STOCKLIST(itemCode: any) {
        try {
          const response = await searchReturnStockList(itemCode)
          this.searchReturnStockList = response.data;
          console.log("반품재고입고 품목별 조회:", response.data);
          
        } catch (error: any) {
          console.error(error);
        }
      },

      // 반품요청 등록
      async FETCH_ADD_RETURN_STOCK(params: Object) {
        console.log("params:", params);
        try {
          const response = await addReturnStock(params);
          console.log("response:", response);
          
          this.addReturnStock = response.data;
          console.log("반품재고 입고요청 등록:", response.data);
       
        }
        catch (error) {
          console.error('Error fetching data:', error);
        }
      },

      //반품재고 입고요청 삭제
      async FETCH_DELETE_RETURNSTOCK(rtrnRecNo: string) {
        try {
          const response = await deleteReturnStock(rtrnRecNo);          
          console.log("반품재고 입고요청 삭제:", response.data);

        }
        catch (error) {
          console.error('Error fetching data:', error)
        }
      },
     
    }
  })