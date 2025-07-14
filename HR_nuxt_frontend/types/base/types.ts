export interface TimeData {
  applyYear: string
  lunchEndTime: string
  dinnerEndTime: string
  attendTime: string
  overEndTime: string
  quitTime: string
  lunchStartTime: string
  nightEndTime: string
  dinnerStartTime: string
}

export interface EmployeData {
  workplaceCode: string
  deptCode: string
  deptName: string
  workplaceName: string
  companyCode: string
  deptStartDate: string
  deptEndDate: string
  status: string
  empName: string
  positionName: string
  count: number
  head: string
  deptTel: string
}

export interface HolidayData {
  applyDay: string
  holidayName: string
  holidayType: string
  holidayCode: string
  status: string
  dateName: string
  locdate: string
}

export interface CodeData {
  codeNumber: string
  codeName: string
  modifiable: string
}

export interface DetailCodeData {
  detailCodeNumber: string
  codeNumber: string
  detailCodeName: string
  detailCodeNameusing: string
}

export interface EmployeeData {
  empName: string
  hiredate: string
  occupation: string
  employmentType: string
  position: string
  address: string
  detailAddress: string
  deptName: string
}

export interface DateData {
  year: string
  month: string
}

export interface DeptList {
  title: string
  value: string
}

export interface ProjectList {
  title: string
  value: string
}

export interface ProjectInfo {
  endDate: string
  jobDescription: string
  projectAmount: string
  projectCategory: string
  projectID: string
  projectName: string
  projectType: string
  remarks: string
  startDate: string
  status: string
}
