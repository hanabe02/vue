import { defineStore } from 'pinia'
import {
  addCode,
  deleteCode,
  getCode,
  updateCode,
} from '@/api/logi/code'

export const codeStore = defineStore("codeStore", {
  state: () => ({
    getCodeInfo: [] as any, // 코드정보
    addCodeInfo: [] as any, // 코드정보
    updateCodeInfo: [] as any, // 코드정보
    deleteCodeInfo: [] as any, // 코드정보
  }),
  actions: {
    // 코드 조회
    async GET_CODE_INFO() {
      try {
        const res = await getCode();

        console.log("g", res);
        console.log("g", res.data);

        this.getCodeInfo = res.data.codeList;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },

    // 코드 추가
    async ADD_CODE_INFO(codelist: any) {
      try {
        const res = await addCode(codelist);

        console.log("123", res);
        console.log("123", res.data);

        this.addCodeInfo = res.config.data;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },

    // 코드 수정
    async UPDATE_CODE_INFO(codeData: any) {
      try {
        const res = await updateCode(codeData);

        console.log("pp", res);
        console.log("pp", res.data);

        this.updateCodeInfo = res;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },

    // 코드 삭제
    async DELETE_CODE_INFO(divisionCodeNo: string) {
      try {
        const res = await deleteCode(divisionCodeNo);

        console.log("d", res);
        console.log("d", res.data);

        this.deleteCodeInfo = res.data.codeList;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },
  },
});
