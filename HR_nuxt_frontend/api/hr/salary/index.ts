import { hrApi } from "@/api";

// Api
const SALARY_URL = "/salary";

/**
 * 기준 급여 조회
 */
function selectBaseSalary(payload: any) {
  return hrApi.get(`${SALARY_URL}/BaseSalaryList`, {
    params: { payload },
  });
}

/**
 * 기준 급여 수정
 */
function modifyBaseSalary(payload: any) {
  console.log(payload);

  return hrApi.put(`${SALARY_URL}/updateBaseSalary`, payload);
}

/**
 * 기준 급여 추가
 */
function registerBaseSalary(payload: any) {
  console.log(payload);

  return hrApi.post(`${SALARY_URL}/registerBaseSalary`, payload);
}

/**
 * 기준 급여 삭제
 */
function deleteBaseSalary(payload: any) {
  console.log(payload.deptCode);

  return hrApi.delete(`${SALARY_URL}/removeBaseSalary`, {
    params: {
      deptCode: payload.deptCode,
      positionCode: payload.positionCode,
    },
  });
}

/**
 * 사회 보험 조회
 */
function selectSocialInsure(payload: any) {
  return hrApi.get(`${SALARY_URL}/SocialInsureList`, {
    params: { year: payload },
  });
}

/**
 * 사회 보험 등록
 */
function registerSocialInsure(payload: any) {
  console.log(payload);

  return hrApi.post(`${SALARY_URL}/registerSocialInsure`, payload);
}

/**
 * 초과 수당 조회
 */
function selectBaseExtSalary() {
  return hrApi.get(`${SALARY_URL}/searchBaseExtSalary`);
}

/**
 * 초과 수당 수정
 */
function modifyBaseExtSalary(payload: any) {
  console.log(payload);

  return hrApi.put(`${SALARY_URL}/updateBaseExtSalary`, payload);
}

/**
 * 초과 수당 추가
 */
function registerBaseExtSal(payload: any) {
  console.log(payload);

  return hrApi.post(`${SALARY_URL}/registerBaseExtSal`, payload);
}

/**
 * 초과 수당 삭제
 */
function deleteBaseExtSal(payload: any) {
  return hrApi.delete(`${SALARY_URL}/removeBaseExtSal`, {
    params: {
      extSalCode: payload,
    },
  });
}

/**
 * 월 급여 초기 조회
 */
function selectSalaryInit(payload: any) {
  return hrApi.get(`${SALARY_URL}/searchSalaryInit`, {
    params: {
      applyYearMonth: payload.applyYearMonth,
      empCode: payload.empCode,
    },
  });
}

/**
 * 월 급여 생성 및 조회
 */
function salaryProcess(payload: any) {
  return hrApi.get(`${SALARY_URL}/salaryProcess`, {
    params: {
      applyYearMonth: payload.applyYearMonth,
      empCode: payload.empCode,
      deptCode: payload.deptCode,
    },
  });
}

/**
 * 월급여 수정 및 마감
 */
function updateMonthSal(payload: any) {
  console.log(payload);

  return hrApi.put(`${SALARY_URL}/updateMonthSal`, payload);
}

/**
 * 월 급여 삭제
 */
function deleteMonthSalay(payload: any) {
  return hrApi.delete(`${SALARY_URL}/removeMonthSalary`, {
    params: {
      empCode: payload.empCode,
      applyYearMonth: payload.applyYearMonth,
    },
  });
}

/**
 * 소득세 조회
 */
function findTax(payload: any) {
  return hrApi.get(`${SALARY_URL}/findMonthDeduction`, {
    params: {
      empCode: payload.empCode,
      applyYearMonth: payload.applyYearMonth,
    },
  });
}

// -------------------------------월급여 조회-------------------------------
/**
 * 월 급여 조회
 */
function findSalary(payload: any) {
  return hrApi.get(`${SALARY_URL}/findSalary`, {
    params: {
      applyYearMonth: payload.applyYearMonth,
      empCode: payload.empCode,
    },
  });
}

/**
 * 상여 및 성과급 조회
 */
function findBonus(payload: any) {
  return hrApi.get(`${SALARY_URL}/findBonus`, {
    params: {
      empCode: payload.empCode,
      applyYearMonth: payload.applyYearMonth,
      detailCodeName: payload.detailCodeName,
      finalizeStatus: payload.finalizeStatus,
    },
  });
}

/**
 * 성과급 추가
 */
function registerBonus(payload: any) {
  console.log(payload);

  return hrApi.post(`${SALARY_URL}/registerBonus`, payload);
}

/**
 * 성과급 삭제
 */
function deleteBonus(payload: any) {
  return hrApi.delete(`${SALARY_URL}/removeBonus`, {
    params: {
      empCode: payload.empCode,
      applyYearMonth: payload.applyYearMonth,
      bonusCode: payload.bonusCode,
    },
  });
}

/**
 * 퇴직금 조회
 */
function findSeverancePay(payload: any) {
  return hrApi.get(`${SALARY_URL}/findSeverancePay`, {
    params: {
      firstDay: payload.firstDay,
      today: payload.today,
    },
  });
}

/**
 * 퇴직금 추가
 */
function registerSeverancePay(payload: any) {
  console.log(payload);

  return hrApi.post(`${SALARY_URL}/registerSeverancePay`, payload);
}

/**
 * 퇴직금 삭제
 */
function deleteSeverance(payload: any) {
  return hrApi.delete(`${SALARY_URL}/removeSeverance`, {
    params: {
      empCode: payload,
    },
  });
}

export {
  deleteBaseExtSal,
  deleteBaseSalary,
  deleteBonus,
  deleteMonthSalay,
  deleteSeverance,
  findBonus,
  findSalary,
  findSeverancePay,
  findTax,
  modifyBaseExtSalary,
  modifyBaseSalary,
  registerBaseExtSal,
  registerBaseSalary,
  registerBonus,
  registerSeverancePay,
  registerSocialInsure,
  salaryProcess,
  selectBaseExtSalary,
  selectBaseSalary,
  selectSalaryInit,
  selectSocialInsure,
  updateMonthSal,
};
