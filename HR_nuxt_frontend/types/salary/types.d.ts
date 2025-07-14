// d
export interface BaseDeduction {
  deductionCode: string;
  deductionName: string;
  ratio: string;
  status: string;
}

export interface BaseExtSal {
  extSalCode: string;
  extSalName: string;
  ratio: string;
  status: string;
}

export interface BaseSalary {
  positionCode: string;
  positionName: string;
  baseSalary: string;
  hobongRatio: string;
  deptName: string;
  deptCode: string;
  workPlaceCode: string;
  status: string;
}

export interface Bonus {
  empCode: string;
  applyYearMonth: string;
  bonus: string;
  bonusCode: string;
  finalizeStatus: string;
  empName: string;
  detailCodeName: string;
}

export interface MonthDeduction {
  empCode: string;
  applyYearMonth: string;
  deductionCode: string;
  deductionName: string;
  price: string;
}

export interface MonthExtSal {
  monthExtSalCode: string;
  applyYearMonth: string;
  empCode: string;
  extSalCode: string;
  extSalName: string;
  price: string;
}

export interface MonthSalaryPKcombine {
  empCode: string;
  applyYearMonth: string;
}

export interface MonthSalary {
  deptCode: string;
  salary: string;
  totalExtSal: string;
  totalPayment: string;
  totalDeduction: string;
  realSalary: string;
  bonus: string;
  finalizeStatus: string;
  mealAllowance: string;
  ownerCarSub: string;
  totalTaxSal: string;
  empName: string;
}

export interface Severance {
  empCode: string;
  empName: string;
  hireDate: string;
  retireDate: string;
  applyDate: string;
  severanceType: string;
  workDays: string;
  severancePay: string;
  severanceDeduction: string;
  realSeverancePay: string;
  firstDay: string;
  today: string;
}

export interface SocialInsure {
  attributionYear: string;
  healthinsureRates: string;
  longtermcareRate: string;
  nationpensionRates: string;
  teachpenisionRates: string;
  empinsureRates: string;
  wrkinsureRates: string;
  jobstabilRates: string;
  vocacompetencyRates: string;
  industinsureRates: string;
  industinsurecharRates: string;
}

// v
export interface SalaryHeader {
  title: string;
  key: string;
  align: string;
}

export interface YearList {
  year: string;
}

export interface MonthList {
  month: string;
}
