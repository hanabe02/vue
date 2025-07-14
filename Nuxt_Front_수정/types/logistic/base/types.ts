export interface CompanyInfoTO {
  companyCode?: string
  companyName?: string
  companyDivision?: string
  businessLicenseNumber?: string
  corporationLicenseNumber?: string
  companyCEOName?: string
  companyBusinessCondition?: string
  companyBusinessItem?: string
  companyZIPCode?: string
  companyBasicAddress?: string
  companyDetailAddress?: string
  companyTelNumber?: string
  companyFAXNumber?: string
  companyEstablishmentDate?: string
  companyOpenDate?: string
  homepage?: string
}

export interface DeptInfoTO {
  workplaceCode?: string
  deptCode?: string
  deptName?: string
  workplaceName?: string
  companyCode?: string
  deptStartDate?: string
  deptEndDate?: string
}

export interface FinancialTO {
  accountAssociatesCode?: string
  workplaceCode?: string
  accountAssociatesName?: string
  accountAssociatesType?: string
  accountNumber?: string
  accountOpenPlace?: string
  cardNumber?: string
  cardType?: string
  cardMemberName?: string
  cardOpenPlace?: string
  businessLicenseNumber?: string
  companfinancialInstituteCodeyTelNumber?: string
  financialInstituteName?: string
  divisionCodeNo?: string
}

export interface ItemGroupTO {
  itemGroupCode?: string
  description?: string
  itemGroupName?: string
}

export interface ItemInfoTO {
  itemCode?: string
  itemName?: string
  itemGroupCode?: string
  itemClassification?: string
  unitOfStock?: string
  lossRate?: string
  leadTime?: string
  standardUnitPrice?: number
  codeUseCheck?: string
  description?: string
}

export interface ItemTO {
  itemGroupCode?: string
  leadTime?: string
  unitOfStock?: string
  standardUnitPrice?: number
  description?: string
  itemCode?: string
  itemClassification?: string
  lossRate?: string
  itemName?: string
}

export interface LogiCodeDetailTO {
  divisionCodeNo?: string
  detailCode?: string
  detailCodeName?: string
  codeUseCheck?: string
  description?: string
}

export interface LogiCodeTO {
  divisionCodeNo?: string
  codeType?: string
  divisionCodeName?: string
  codeChangeAvailable?: string
  description?: string
  codeDetailTOList?: []
}

export interface WarehouseTO {
  warehouseCode?: string
  warehouseName?: string
  warehouseUseOrNot?: string
  description?: string
}

export interface WorkplaceInfoTO {
  workplaceCode?: string
  companyCode?: string
  workplaceName?: string
  businessLicenseNumber?: string
  corporationLicenseNumber?: string
  workplaceCeoName?: string
  workplaceBusinessConditions?: string
  workplaceBusinessItems?: string
  workplaceZipCode?: string
  workplaceBasicAddress?: string
  workplaceDetailAddress?: string
  workplaceTelNumber?: string
  workplaceFaxNumber?: string
  workplaceEstablishmentDate?: string
  workplaceOpenDate?: string
  workplaceCloseDate?: string
  isMainOffice?: string
  approvalStatus?: string
}
