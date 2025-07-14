import { logiApi } from "@/api";

const OUTPUT_URL = "/sales/output";
const OUTPUT_DETAIL_URL = "/sales/outputInfo";
const ADD_OUTPUT_URL= "/sales/addOutput";
const DELETE_OUTPUT_URL= "/sales/deleteOutput";
const CONSTRACT_URL = "/sales/searchContractOutput";
const CONSTRACT_DETAIL_URL = "/sales/searchContractOutputDetail";

// 출고 조회
function getOutputList() {
  return logiApi.get(`${OUTPUT_URL}`);
}

// 한 행 클릭 시 출고상세정보 조회
function getOutputDetail(outputNumber: string) {
  return logiApi.get(`${OUTPUT_DETAIL_URL}`, {
    params: {
      outputNumber,
    },
  });
}

// 출고 등록
function addOutput(OutputList: any) {
  return logiApi.post(`${ADD_OUTPUT_URL}`, OutputList);
}

// 출고 삭제
function deleteOutput(outputNumber: string) {
  return logiApi.delete(`${DELETE_OUTPUT_URL}/${outputNumber}`);
}

// 출고를 위한 수주 조회
function getContract(
  searchCondition: any,
  customerCode: any,
  startDate: any,
  endDate: any
) {
  return logiApi.get(`${CONSTRACT_URL}`, {
    params: {
      searchCondition,
      customerCode,
      startDate,
      endDate,
    },
  });
}

// 한 행 클릭 시 수주상세정보 조회
function getContractDetail(contractNo: string) {
  return logiApi.get(`${CONSTRACT_DETAIL_URL}`, {
    params: {
      contractNo,
    },
  });
}

export {
  getOutputList,
  getOutputDetail,
  addOutput,
  deleteOutput,
  getContract,
  getContractDetail,
}; 