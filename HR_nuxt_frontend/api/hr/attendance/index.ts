import { hrApi } from '@/api'

const ATTD_URL = '/attendance'

function getEmpListByDept(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.get(`${ATTD_URL}/findEmpListByDept`, {
    params: { deptCode: payload },
  })
}

/**
 * 승우
 */
function getDayAttdList(payload: any) {
  console.log('API를 호출했습니다.', payload)

  const { applyDay, empCode } = payload

  console.log(applyDay)

  return hrApi.get(`${ATTD_URL}/dayAttendance`, {
    params: {
      applyDay,
      empCode,
    },
  })
}

function addDayAttd(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.post(`${ATTD_URL}/dayAttendance`, payload)
}

function editDayAttd(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.put(`${ATTD_URL}/dayAttendanceUpdate`, payload)
}

function removeDayAttd(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.delete(`${ATTD_URL}/dayAttendance`, {
    params: {
      empCode: payload.empCode,
      dayAttdCode: payload.dayAttdCode,
    },
  })
}

function getAnnualVactionMgtList(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.get(`${ATTD_URL}/findAnnualVactionMgtListByYear`, {
    params: {
      empCode: payload.empCode,
      year: payload.year,
    },
  })
}

function getRestAttdListByEmpCodeAndYM(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.get(`${ATTD_URL}/findRestAttdListByEmpCodeAndYM`, {
    params: {
      empCode: payload.empCode,
      yearMonth: payload.yearMonth,
    },
  })
}
function getRestAttdListByAnnulVacation(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.get(`${ATTD_URL}/findRestAttdListByAnnulVacation`, {
    params: {
      empCode: payload.empCode,
      yearMonth: payload.yearMonth,
    },
  })
}

function addRequestVacation(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.post(`${ATTD_URL}/registeRequestVacation`, payload)
}

function addRestAttd(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.post(`${ATTD_URL}/registRestAttd`, payload)
}

function removeRestAttd(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.delete(`${ATTD_URL}/removeRestAttd`, {
    params: {
      restAttdCode: payload,
    },
  })
}

function getTravelAndEducationList(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.get(`${ATTD_URL}/findTravelAndEducationList`, {
    params: {
      empCode: payload.empCode,
      startDate: payload.startDate,
      endDate: payload.endDate,
    },
  })
}

// 근태등록자가 수정을 하면 안 될 것 같아서, 수정기능을 사용하지 않기로 함.
function editTravelAndEducationList(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.put(`${ATTD_URL}/modifyTravelAndEducationList`, payload)
}

function removeTravelAndEducationList(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.delete(`${ATTD_URL}/removeTravelAndEducationList`, {
    params: {
      empCode: payload.empCode,
      restAttdCode: payload.restAttdCode,
    },
  })
}

function selectDayAttdMgtListByStatus(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.get(`${ATTD_URL}/findDayAttdMgtListCategory`, {
    params: {
      startDate: payload.startDate,
      endDate: payload.endDate,
      applyStatus: payload.applyStatus,
      empCode: payload.empCode,
    },
  })
}

function batchDayAttdMgt(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.get(`${ATTD_URL}/dayAttendanceManage`, {
    params: {
      applyDay: payload,
    },
  })
}
//일집계
function runDailyAggregation(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.post(`${ATTD_URL}/batchDayAttd`,  { applyDay: payload });
}

function runMonthlyAggregation(applyYearMonth: string,) {
  console.log('API를 호출했습니다.', applyYearMonth)

  return hrApi.post(`${ATTD_URL}/batchMonthAttd`, {
    applyYearMonth,
  });
  
}

function fetchMonthAttendanceList(applyYearMonth: string,) {
  console.log('API를 호출했습니다.', applyYearMonth)

  return hrApi.post('/hr/attendance/monthAttendanceManage', {
    params: { applyYearMonth },
  });
}


function closeMonthAttendance(list: any[],) {
  console.log('API를 호출했습니다.', list)

  return hrApi.post('/hr/attendance/monthAttendanceClose', {
    monthAttdMgt: list,
  });
}


// 연차 마감 관리
function executeVacationCreate(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.get(`${ATTD_URL}/exeAnnualVacationProcess`, {
    params: {
      applyYearMonth: payload,
    },
  })
}

// 연차 승인관리
function updateVacationApplyStatus(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.post(`${ATTD_URL}/modifyAnnualVacationMgtList`, payload)
}

// 일근태 관리 - 근태승인, 근태취소
function updateDayAttdmgtApplyStatus(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.put(`${ATTD_URL}/dayAttendanceManageUpdate`, payload)
}

// 없음
function deleteDayAttdmgtApplyStatus(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.delete(`${ATTD_URL}/dayAttendanceManageRemove`, {
    params: {
      dayAttdMgtList: payload,
    },
  })
}

// 사월별 일근태 관리 조회
function selectDayAttdMgtListByStatusAndEmpCode(payload: any) {
  console.log('API를 호출했습니다.', payload)

  return hrApi.get(`${ATTD_URL}/findDayAttdMgtListCategoryByEmpCode`, {
    params: {
      startDate: payload.startDate,
      endDate: payload.endDate,
      applyStatus: payload.applyStatus,
      empCode: payload.empCode,
    },
  })
}

export {
  addDayAttd, addRequestVacation,
  addRestAttd, batchDayAttdMgt,
  deleteDayAttdmgtApplyStatus, editDayAttd,
  editTravelAndEducationList, executeVacationCreate,
  getAnnualVactionMgtList, getDayAttdList,
  getEmpListByDept, getRestAttdListByAnnulVacation,
  getRestAttdListByEmpCodeAndYM, getTravelAndEducationList,
  removeDayAttd, removeRestAttd,
  removeTravelAndEducationList, selectDayAttdMgtListByStatus,
  selectDayAttdMgtListByStatusAndEmpCode, updateDayAttdmgtApplyStatus, updateVacationApplyStatus
  , runDailyAggregation, runMonthlyAggregation, fetchMonthAttendanceList, closeMonthAttendance
}

