export interface DailyLaborEmp {
  employeeId: string
  employeeName: string
  residentialNumber: string
  nationality: string
  department: string
  project: string
  workStartDate: string
  workEndDate: string
  employmentInsuranceEligibility: string | boolean
  normalPayType: string
  normalPay: string | number
  overtimePayRate: string | number
  nightPayRate: string | number
  bankName: string
  accountNumber: string
  accountHolder: string
  phoneNumber: string
  email: string
  status: string
}

export interface DailyManageRowType {
  internalItem: {
    columns: {
      employeeId: string
      employeeName: string
      residentialNumber: string
    }
    index: number
    key: any
    raw: DailyLaborEmp
    selectable: boolean
    type: string
    value: any
  }
  item: DailyLaborEmp
}

export interface LogData {
  employeeName: string
  residentialNumber: string
}

export interface DailyLaborEmpLog {
  employeeName: string
  residentialNumber: string
  nationality: string
  department: string
  project: string
  workStartDate: string
  workEndDate: string
  employmentInsuranceEligibility: string
  normalPayType: string
  normalPay: number
  overtimePayRate: number
  nightPayRate: number
  bankName: string
  accountNumber: string
  accountHolder: string
  phoneNumber: string
  email: string
}

export interface Headers {
  title: string
  key: string
  align: string
}

export interface Header {
  title: string
  key: string
}

export interface AttendDataList {
  employeeId: string
  employeeName: string
  project: string
  workDate: string
  workStartTime: string
  workType: string
  deadlineRequest: string
}

export interface AttendData {
  employeeId: string
  workDate: string
  workEndTime: string
}

export interface EmployeeInfo {
  accountHolder: string
  accountNumber: string
  bankName: string
  department: string
  email: string
  employeeId: string
  employeeName: string
  employmentInsuranceEligibility: string
  nationality: string
  nightPayRate: number
  normalPay: number
  normalPayType: string
  overtimePayRate: number
  phoneNumber: string
  project: string
  residentialNumber: string
  status: string
  workDate: string
  workEndDate: string
  workStartDate: string
  workTime: string
}

export interface BatchAttendData {
  employeeId: string
  workDate: string
}

export interface Select {
  value: string
  title: string
}

export interface DailyLaborSalary {
  employeeId: string
  employeeName: string
  workDate: string
  totalPay: string
  normalPay: string
  overtimePay: string
  nightPayRate: string
  workTax: string
  deadlineRequest: string
  status: string
}

export interface SalaryData {
  employeeId: string
  workDate: string
}

export interface DailyLaborTax {
  attributionYear: string
  healthInsureRates: string
  longTermCareRate: string
  nationPensionRates: string
  empInsureRates: string
  wrkInsureRates: string
  incomeTaxRates: string
  localIncomeTaxRates: string
}

export interface DailyLaborSalaryTax {
  employeeId: string
  employeeName: string
  workDate: string
  totalPay: string
  normalPay: string
  overtimePay: string
  nightPayRate: string
  workTax: string
  deadlineRequest: string
  status: string
  totalTax: string
  nationTax: string
  healthTax: string
  longTermTax: string
  empInsureTax: string
  industInsureTax: string
  incomeTax: string
  localIncomeTax: string
}

export interface DailySalarySearchTapRow {
  internalItem: {
    columns: {
      employeeId: string
      employeeName: string
      nightPayRate: string
      normalPay: string
      overtimePay: string
      totalPay: string
      totalTax: string
    }
    index: number
    key: string
    raw: {
      deadlineRequest: string
      empInsureTax: string
      healthTax: string
      incomeTax: string
      industInsureTax: string
      localIncomeTax: string
      longTermTax: string
      nationTax: string
      status: string
      workDate: string
      workTax: string
    }
    selectable: boolean
    type: string
    value: {
      deadlineRequest: string
      empInsureTax: string
      employeeId: string
      employeeName: string
      healthTax: string
      incomeTax: string
      industInsureTax: string
      localIncomeTax: string
      longTermTax: string
      nationTax: string
      nightPayRate: string
      normalPay: string
      overtimePay: string
      status: string
      totalPay: string
      totalTax: string
      workDate: string
      workTax: string
    }
  }
  item: {
    deadlineRequest: string
    empInsureTax: string
    employeeId: string
    employeeName: string
    healthTax: string
    incomeTax: string
    industInsureTax: string
    localIncomeTax: string
    longTermTax: string
    nationTax: string
    nightPayRate: string
    normalPay: string
    overtimePay: string
    status: string
    totalPay: string
    totalTax: string
    workDate: string
    workTax: string
  }
}

export interface salaryTax {
  standard: string
  empPercent: string
  workPercent: string
  note: string
}
