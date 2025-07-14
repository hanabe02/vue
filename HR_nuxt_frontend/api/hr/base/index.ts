import { hrApi, sys } from "@/api";

const BASE_URL = "/base";

/**
 * deptList
 */
function selectDeptList() {
  return hrApi.get(`${BASE_URL}/deptList`);
}

/**
 * deptListDetail
 */
function selectDeptListDetail() {
  return hrApi.get(`${BASE_URL}/deptList`);
}

/**
 * deptManageList
 */
function findDeptManageList() {
  return hrApi.get(`${BASE_URL}/findDeptManageList`);
}

/**
 * posList
 */
function selectPositionList() {
  return hrApi.get(`${BASE_URL}/positionList`);
}

/**
 * 승우
 * yearList
 */
function selectYearList() {
  return hrApi.get(`${BASE_URL}/yearList`);
}

/*
 * 승우
 * monthList
 */
function selectMonthList() {
  return hrApi.get(`${BASE_URL}/monthList`);
}

/**
 * companyList
 */
function selectCompanyList() {
  return hrApi.get(`${BASE_URL}/companyList`);
}

/**
 * workplaceList
 */
function selectWorkplaceList() {
  return hrApi.get(`${BASE_URL}/workplaceList`);
}

/**
 * allHolidayList
 */
function selectHolidayList(payload: any) {
  return hrApi.get(`${BASE_URL}/holidayList`, {
    params: {
      applyDay: payload,
    },
  });
}

/**
 * holidayType
 */
function selectHolidayType() {
  return sys.get("/findHolidayType");
}

/**
 * insertholiday
 */
function addHoliday(payload: any) {
  return hrApi.post(`${BASE_URL}/addHoliday`, payload);
}

/**
 * modifyHoliday
 */
function modifyHoliday(payload: any) {
  return hrApi.put(`${BASE_URL}/modifyHoliday`, payload);
}

/**
 * removeHoliday
 */
function removeHoliday(payload: any) {
  return hrApi.delete(`${BASE_URL}/removeHoliday`, {
    params: {
      applyDay: payload,
    },
  });
}

function addPublicHoliday(payload: any) {
  return hrApi.post(`${BASE_URL}/addPublicHoliday`, payload);
}

/**
 * addDept
 */
function addDept(payload: any) {
  return hrApi.post(`${BASE_URL}/addDept`, payload);
}

/**
 * modifyDept
 */
function modifyDept(payload: any) {
  return hrApi.put(`${BASE_URL}/modifyDept`, payload);
}

/**
 * removeDept
 */
function removeDept(payload: any) {
  return hrApi.delete(`${BASE_URL}/removeDept`, {
    params: {
      deptCode: payload.deptCode,
      workplaceCode: payload.workplaceCode,
    },
  });
}

/**
 * 프로젝트 조회
 */
function selectProjectList() {
  return hrApi.get(`${BASE_URL}/project`);
}

/**
 * 프로젝트 등록
 */
function addProject(payload: any) {
  return hrApi.post(`${BASE_URL}/project`, payload);
}

/**
 * 프로젝트 수정
 */
function modifyProject(payload: any) {
  return hrApi.put(`${BASE_URL}/project`, payload);
}

/**
 * 프로젝트 삭제
 */
function removeProject(payload: any) {
  return hrApi.delete(`${BASE_URL}/project`, {
    params: {
      projectID: payload,
    },
  });
}

export {
  addDept,
  addHoliday,
  addProject,
  addPublicHoliday,
  findDeptManageList,
  modifyDept,
  modifyHoliday,
  modifyProject,
  removeDept,
  removeHoliday,
  removeProject,
  selectCompanyList,
  selectDeptList,
  selectDeptListDetail,
  selectHolidayList,
  selectHolidayType,
  selectMonthList,
  selectPositionList,
  selectProjectList,
  selectWorkplaceList,
  selectYearList,
};
