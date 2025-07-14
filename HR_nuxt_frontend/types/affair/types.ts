export interface AssignEmpTO {
  empAssignCode: string
  empCode: string
  assignType: string
  empName: string
  assignDate: string
  empPosition: string
  currentDivision: string
  assignDivision: string
  empAssign: string
  assignCode: string
}

export interface BestEmpTO {
  companyCode: string
  empName: string
  empCode: string
  img: string
  deptCode: string
  deptName: string
  weekdayWorkDays: string
  applyYearMonth: string
}

export interface CareerInfoTO {
  empCode: string
  careerCode: string
  companyName: string
  occupation: string
  assignmentTask: string
  exHiredate: string
  exRetirementDat: string
}

export interface CertificateTO {
  empCode: string
  empName: string
  deptName: string
  requestDate: string
  useDate: string
  usageCode: string
  usageName: string
  etc: string
  approvalStatus: string
  rejectCause: string
}

export interface EducationInfoTO {
  empCode: string
  educationCode: string
  schoolName: string
  major: string
  entranceDate: string
  graduateDate: string
  grade: string
}

export interface EmpInfoTO {
  socialSecurityNumber: string
  empEngName: string
  deptName: string
  updateHistory: string
  updateDate: string
  zipCode: string
  positionCode: string
  userId: string
  levelOfEducation: string
  userOrNot: string
  email: string
  empName: string
  image: string
  hireDate: string
  retirementDate: string
  workplaceCode: string
  companyCode: string
  birthDate: string
  gender: string
  deptCode: string
  empCode: string
  positionName: string
  detailAddress: string
  workplaceName: string
  basicAddress: string
  phoneNumber: string
  authorityCode: string
  seq: number
  empDetailTOList: []
  empSecretTOList: []
}

export interface EmployeeBasicTO {
  companyCode: string
  empCode: string
  empName: string
  empEngName: string
  socialSecurityNumber: string
  hireDate: string
  retirementDate: string
  userOrNot: string
  birthDate: string
  gender: string
  deptCode: string
  deptName: string
  zipCode: string
  basicAddress: string
  image: string
  positionCode: string
  positionName: string
  updateDate: string
  email: string
  levelOfEducation: string
  workInfoList: []
  careerInfoList: []
  educationInfoList: []
  familyInfoList: []
}

export interface EmployeeDetailTO {
  companyCode: string
  empCode: string
  seq: number
  updateHistory: string
  updateDate: string
  workplaceCode: string
  deptCode: string
  positionCode: string
  userId: string
  phoneNumber: string
  email: string
  zipCode: string
  basicAddress: string
  detailAddress: string
  levelOfEducation: string
  image: string
  empName: string
  deptName: string
  gender: string
  hireDate: string
  birthDate: string
  workplaceName: string
  positionName: string
}

export interface EmployeeSecretTO {
  companyCode: string
  empCode: string
  seq: number
  userPassword: string
}

export interface EmpTO {
  empCode: string
  empName: string
  birthDate: string
  hireDate: string
  gender: string
  mobileNumber: string
  address: string
  companyName: string
  detailAddress: string
  email: string
  workPlaceName: string
  workPlaceCode: string
  positionName: string
  positionCode: string
  deptName: string
  deptCode: string
  detailCodeName: string
}

export interface EmpUpdateTO {
  socialSecurityNumber: string
  empEngName: string
  deptName: string
  updateHistory: string
  updateDate: string
  zipCode: string
  positionCode: string
  userId: string
  levelOfEducation: string
  userOrNot: string
  email: string
  empName: string
  image: string
  hireDate: string
  retirementDate: string
  workplaceCode: string
  companyCode: string
  birthDate: string
  gender: string
  deptCode: string
  empCode: string
  positionName: string
  detailAddress: string
  workplaceName: string
  basicAddress: string
  phoneNumber: string
  authorityCode: string
  seq: number
}

export interface FamilyInfoTO {
  empCode: string
  familyCode: string
  familyName: string
  relation: string
  birthdate: string
  liveTogether: string
}

export interface LicenseInfoTO {
  empCode: string
  licenseCode: string
  licenseName: string
  getDate: string
  expireDate: string
  licenseLevel: string
  licenseCenter: string
  issueNumber: string
}

export interface RegiAppTO {
  appointDept: string
  appointDate: string
  appointLevel: string
  appointType: string
  appointWP: string
  deptCode: string
  deptName: string
  empCode: string
  empName: string
  positionCode: string
  positionName: string
  status: string
  workPlaceCode: string
  workPlaceName: string
}

export interface RegiEmpTO {
  userPassword: string
  companyCode: string
  updateDate: string
  workplaceCode: string
  workplaceName: string
  deptCode: string
  deptName: string
  positionCode: string
  positionName: string
  userId: string
  phoneNumber: string
  email: string
  zipCode: string
  basicAddress: string
  detailAddress: string
  levelOfEducation: string
  image: string
  empName: string
  empCode: string
  empEngName: string
  socialSecurityNumber: string
  hireDate: string
  retirementDate: string
  userOrNot: string
  birthDate: string
  gender: string
  authorityCode: string
  accessCode: string
}

export interface RegistEMPTO {
  companyCode: string
  userPassword: string
  updateDate: string
  workplaceCode: string
  deptCode: string
  positionCode: string
  userId: string
  phoneNumber: string
  email: string
  zipCode: string
  basicAddress: string
  detailAddress: string
  levelOfEducation: string
  image: string
  empName: string
  empEngName: string
  socialSecurityNumber: string
  hireDate: string
  retirementDate: string
  userOrNot: string
  birthDate: string
  gender: string
  authorityCode: string
  accessCode: string
}

export interface TempAppointmentTO {
  appointmentNo: string
  empCode: string
  appointmentHistory: string
  currentInformation: string
  preAppointmentInformation: string
}

export interface UpdateEMPTO {
  empCode: string
  email: string
  zipCode: string
  basicAddress: string
  levelOfEducation: string
  empName: string
  birthDate: string
  gender: string
}

export interface WorkInfoTO {
  empCode: string
  empName: string
  workInfoDays: string
  hiredate: string
  retireDate: string
  userOrNot: string
  occupation: string
  employmentType: string
  hobong: string
  position: string
  deptName: string
}
