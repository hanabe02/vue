import { hrApi } from '@/api'

const DAILYLABOR_EMP_URL = '/dailyemp'
const DAILYLABOR_ATTEND_URL = '/dailyattend'
const DAILYLABOR_SALARY_URL = '/dailysal'

function selectDailyLaborEmp() {
  return hrApi.get(`${DAILYLABOR_EMP_URL}/emp`)
}

function getDailyLaborEmpDetail(clickEmp: string) {
  console.log(clickEmp)

  return hrApi.get(`${DAILYLABOR_EMP_URL}/emp/${clickEmp}`)
}

function addDailyLaborEmp(regiEmp: any) {
  console.log(regiEmp)

  return hrApi.post(`${DAILYLABOR_EMP_URL}/emp`, regiEmp)
}

function updateDailyLaborEmp(payload: any) {
  console.log(payload)

  return hrApi.put(`${DAILYLABOR_EMP_URL}/emp`, payload)
}

function deleteDailyLaborEmp(payload: any) {
  console.log(payload)

  return hrApi.put(`${DAILYLABOR_EMP_URL}/deleteemp`, payload)
}

function selectDailyLaborEmpLog() {
  return hrApi.get(`${DAILYLABOR_EMP_URL}/log`)
}

function deleteDailyLaborEmpLog(payload: any) {
  console.log(payload)

  return hrApi.put(`${DAILYLABOR_EMP_URL}/log`, payload)
}

function addDailyLaborAttend(regiAttend: any) {
  console.log(regiAttend)

  return hrApi.post(`${DAILYLABOR_ATTEND_URL}/attned`, regiAttend)
}

function updateDailyLaborAttend(updateAttend: any) {
  console.log(updateAttend)

  return hrApi.put(`${DAILYLABOR_ATTEND_URL}/attned`, updateAttend)
}

function selectDailyLaborAttend(date: any, approval: any) {
  console.log(approval)

  return hrApi.get(`${DAILYLABOR_ATTEND_URL}/attneddate`, {
    params: {
      workDate: date,
      deadlineRequest: approval,
    },
  })
}

function deleteDailyLaborAttend(payload: any) {
  console.log(payload)

  return hrApi.put(`${DAILYLABOR_ATTEND_URL}/attenddate`, payload)
}

function updateDailyLaborAttendApproval(payload: any) {
  console.log(payload)

  return hrApi.put(`${DAILYLABOR_ATTEND_URL}/approval`, payload)
}

function insertSalaryList(payload: any) {
  console.log(payload)

  return hrApi.post(`${DAILYLABOR_SALARY_URL}/salary`, payload)
}

function findSalaryList(payload: any) {
  console.log(payload)

  return hrApi.get(`${DAILYLABOR_SALARY_URL}/salary`, {
    params: {
      date: payload.date,
      deadline: payload.deadline,
      status: payload.status,
    },
  })
}

function updateDailyLaborSalaryList(payload: any) {
  console.log(payload)

  return hrApi.put(`${DAILYLABOR_SALARY_URL}/salary`, payload)
}

function deleteDailyLaborSalary(payload: any) {
  console.log(payload)

  return hrApi.put(`${DAILYLABOR_SALARY_URL}/delete`, payload)
}

function selectSalaryTaxList(payload: any) {
  console.log(payload)

  return hrApi.get(`${DAILYLABOR_SALARY_URL}/salarytax`, {
    params: {
      workDate: payload.workDate,
      deadlineRequest: payload.deadlineRequest,
      status: payload.status,
    },
  })
}

function selectTax(payload: any) {
  console.log(payload)

  return hrApi.get(`${DAILYLABOR_SALARY_URL}/tax`, {
    params: {
      date: payload,
    },
  })
}

function updateSalaryTaxList(payload: any) {
  console.log(payload)

  return hrApi.put(`${DAILYLABOR_SALARY_URL}/salarytax`, payload)
}

function deleteSalaryTax(payload: any) {
  console.log(payload)

  return hrApi.put(`${DAILYLABOR_SALARY_URL}/deletesalarytax`, payload)
}

export {
  addDailyLaborAttend, addDailyLaborEmp, deleteDailyLaborAttend, deleteDailyLaborEmp, deleteDailyLaborEmpLog, deleteDailyLaborSalary, deleteSalaryTax, findSalaryList, getDailyLaborEmpDetail, insertSalaryList,
  selectDailyLaborAttend, selectDailyLaborEmp, selectDailyLaborEmpLog, selectSalaryTaxList, selectTax, updateDailyLaborAttend, updateDailyLaborAttendApproval, updateDailyLaborEmp,
  updateDailyLaborSalaryList, updateSalaryTaxList,
}
