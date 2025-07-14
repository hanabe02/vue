import { hrApi } from '@/api'

const Emp = '/affair/emp'
const appoint = '/affair/appoint'

const getDeptList = '/base/deptList'
const getWorkPlace = '/company/searchWorkplace'

const empPhotoUpdate = '/affairFile/photoUpdate'
const empPhotoRegi = '/affairFile/photoResi'

/// ///////////////직원 조회////////////////////////
// 승우
// 직원 전체 조회
function getAllEmpList() {
  return hrApi.get(`${Emp}`)
}

// 직원 상세 정보 조회
function getEmpDetail(clickEmp: any) {
  console.log(clickEmp)

  return hrApi.get(`${Emp}/${clickEmp}`)
}

// 직원 등록
function addEmp(regiEmp: any) {
  console.log(regiEmp)

  return hrApi.post(`${Emp}`, regiEmp)
}

// 직원 정보 수정
function editEmp(empUpdate: any) {
  console.log(empUpdate)

  return hrApi.put(`${Emp}`, empUpdate)
}

// 직원 삭제
function removeEmp(emp: any) {
  console.log(emp)

  return hrApi.delete(`${Emp}/${emp}`)
}

// 직원 사진 수정
function editPhotoEmp(file: any) {
  console.log(file)

  return hrApi.post(`${empPhotoUpdate}`, file)
}

// 직원 사진 등록
function addImgEmp(file: any) {
  console.log(file)

  return hrApi.post(`${empPhotoRegi}`, file)
}

/// ///////////////직원 조회 끝////////////////////////

// 부서 조회
function getAllDepartmentList() {
  return hrApi.get(`${getDeptList}`)
}

// 근무지 조회
function getWorkPlaceList(COM: any) {
  console.log('근무지 조회', COM)

  return hrApi.get(`${getWorkPlace}`, {
    params: {
      companyCode: COM,
    },
  })
}

/// /////////////////////////직원 발령////////////////////////
// 발령 등록
function addAppointRegi(appRegi: any) {
  console.log('발령정보 등록 API', appRegi)

  return hrApi.post(`${appoint}`, appRegi)
}

// 발령 신청 내역 조회
function getAppList() {
  return hrApi.get(`${appoint}`)
}

// 직원 발령 승인 및 반려
function editApproveAppoint(approveAppoint: any) {
  console.log('발령 승인', approveAppoint)

  return hrApi.put(`${appoint}`, approveAppoint)
}

// 직원 발령 삭제
function removeAppoint(approveAppoint: any) {
  console.log('발령 삭제', approveAppoint)

  return hrApi.delete(`${appoint}`, {
    params: {
      approveAppoint,
    },
  })
}

// 발령 기록 조회
function getLogAppoint() {
  return hrApi.get(`${appoint}/log`)
}

/// /////////////////////////직원 발령 끝////////////////////////

// 부서별 사원 조회

function findDeptEmpList(payload: any) {
  console.log(payload)

  return hrApi.get(`${Emp}/findDeptEmpList`, {
    params: {
      deptCode: payload,
    },
  })
}

export {
  addAppointRegi, addEmp,
  addImgEmp, editApproveAppoint,
  editEmp, editPhotoEmp,
  findDeptEmpList, getAllDepartmentList,
  getAllEmpList, getAppList,
  getEmpDetail, getLogAppoint,
  getWorkPlaceList, removeAppoint,
  removeEmp,
}
