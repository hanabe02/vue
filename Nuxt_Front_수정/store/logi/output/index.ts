import { defineStore } from "pinia";
import {
  getOutputList,
  getOutputDetail,
  addOutput,
  deleteOutput,
  getContract,
  getContractDetail,
} from "@/api/logi/output";

export const outputStore = defineStore("outputStore", {
  state: () => ({
    getOutputInfo: [] as any, // 출고정보
    getOutputDetailInfo: [] as any, //상세정보
    addOutputInfo: [] as any, // 출고등록
    deleteOutputInfo: [] as any, // 출고삭제
    getContractInfo: [] as any, // 수주현황
    getContractDetailInfo: [] as any, // 수주상세정보
  }),

  actions: {
    // 츨고 조회
    async GET_OUTPUT_INFO() {
      try {
        const res = await getOutputList();

        console.log("g", res);
        console.log("g", res.data);

        this.getOutputInfo = res.data.outputList;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },

    // 한 행클릭시 출고상세정보 조회
    async GET_OUTPUT_DETAIL_INFO(outputNumber: string) {
      try {
        const res = await getOutputDetail(outputNumber);

        console.log("vv3", res.data.outputList[0]);
        console.log("vv4", res.data.outputInfo[0]);

        this.getOutputDetailInfo = res.data.outputList[0];

        console.log(this.getOutputDetailInfo);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },

    // 츨고 추가
    async ADD_OUTPUT_INFO(addData: any) {
      try {
        console.log("addDataAAAA", addData);

        const res = await addOutput(addData);

        console.log("123", res);

        this.addOutputInfo = res;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },

    // 츨고 삭제
    async DELETE_OUTPUT_INFO(outputNumber: string) {
      try {
        const res = await deleteOutput(outputNumber);

        console.log("d", res);
        console.log("d", res.data);

        // this.deleteOutputInfo = res.data.codeList;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },

    // 수주 조회
    async GET_CONTRACT_INFO(
      searchCondition: any,
      customerCode: any,
      startDate: any,
      endDate: any
    ) {
      try {
        const res = await getContract(
          searchCondition,
          customerCode,
          startDate,
          endDate
        );

        console.log("contract", res.data.gridRowJson);

        this.getContractInfo = res.data.gridRowJson;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },                                                                                                                                                         
    // 한 행클릭시 수주상세정보 조회
    async GET_CONTRACT_DETAIL_INFO(contractNo: string) {
      try {
        console.log('여기')
        const res = await getContractDetail(contractNo);

        console.log("vv", res);
        console.log("vv2", res.data.gridRowJson);

        this.getContractDetailInfo = res.data.gridRowJson;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },
  },
});