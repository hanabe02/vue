export interface ContractDetailInMpsAvailableTO {
    contractNo?: string;
    contractType?: string;
    contractDate?: string;
    customerCode?: string;
    contractDetailNo?: string;
    itemCode?: string;
    itemName?: string;
    unitOfContract?: string;
    estimateAmount?: string;
    stockAmountUse?: string;
    productionRequirement?: string;
    dueDateOfContract?: string;
    description?: string;
    planClassification?: string;
    mpsPlanDate?: String;
    homepscheduledEndDate?: String;
  }


export interface InputMaterialsTO {
    inputItemNo?: string;
    productionResultNo?: string;
    description?: string;
    itemCode?: string;
    unitOfInputMaterials?: string;
    warehouseCode?: string;
    itemName?: string;
    inputAmount?: number;
    inputDate?: string;
  }

  export interface MpsTO {
    mpsNo?: string;
    mpsPlanDate?: string;
    contractDetailNo?: string;
    dueDateOfMps?: string;
    salesPlanNo?: string;
    itemCode?: string;
    itemName?: string;
    mpsPlanAmount?: string;
    mrpApplyStatus?: string;
    description?: string;
    unitOfMps?: string;
    mpsPlanClassification?: string;
    scheduledEndDate?: string;
  }

  export interface MrpGatheringTO {
    mrpGatheringNo?: string;
    orderOrProductionStatus?: string;
    itemCode?: string;
    itemName?: string;
    unitOfMrpGathering?: string;
    claimDate?: string;
    dueDate?: string;
    necessaryAmount?: number;
    mrpGatheringSeq?: number;
    requestStatus?: string;
    outsourceStatus?: string;
    mrpTOList?: string;
  }

  export interface MrpTO {
    mrpNo?: string;
    mpsNo?: string;
    mrpGatheringNo?: string;
    itemClassification?: string;
    itemCode?: string;
    itemName?: string;
    unitOfMrp?: string;
    requiredAmount?: number;
    planAmount?: string;
    orderDate?: string;
    requiredDate?: string;
    mrpGatheringStatus?: string;
  }

  export interface OpenMrpTO {
    mpsNo?: string;
    bomNo?: string;
    itemClassification?: string;
    itemCode?: string;
    itemName?: string;
    orderDate?: string;
    requiredDate?: string;
    planAmount?: string;
    totalLossRate?: string;
    caculatedAmount?: string;
    requiredAmount?: number;
    unitOfMrp?: string;
  }

  export interface OpenMrpTO {
    mpsNo?: string;
    bomNo?: string;
    itemClassification?: string;
    itemCode?: string;
    itemName?: string;
    orderDate?: string;
    requiredDate?: string;
    planAmount?: string;
    totalLossRate?: string;
    caculatedAmount?: string;
    requiredAmount?: number;
    unitOfMrp?: string;
  }

  export interface ProductionPerformanceInfoTO {
    workOrderCompletionDate?: string;
    workOrderNo?: string;
    mpsNo?: string;
    contractDetailNo?: string;
    itemClassification?: string;
    itemCode?: string;
    itemName?: string;
    unit?: string;
    workOrderAmount?: string;
    actualCompletionAmount?: string;
    workSuccessRate?: string;
  }

  export interface ProductionResultManagementTO {
    productionResultNo?: string;
    workInstructionNo?: string;
    description?: string;
    productionDate?: string;
    itemCode?: string;
    unitOfProductionResult?: string;
    productionAmount?: string;
    itemName?: string;
  }

  export interface ProductionWorkInstructionTO {
    workInstructionAmount?: number;
    workInstructionNo?: string;
    description?: string;
    itemCode?: string;
    productionStatus?: string;
    instructionDate?: string;
    mrpGatheringNo?: string;
    itemName?: string;
    unitOfWorkInstruction?: string;
  }

  export interface SalesPlanInMpsAvailableTO {
    salesPlanNo?: string;
    planClassification?: string;
    itemCode?: string;
    itemName?: string;
    unitOfSales?: string;
    salesPlanDate?: string;
    mpsPlanDate?: string;
    scheduledEndDate?: string;
    dueDateOfSales?: string;
    salesAmount?: string;
    unitPriceOfSales?: number;
    sumPriceOfSales?: number;
    mpsApplyStatus?: string;
    description?: string; 
  }

  export interface WorkOrderableMrpListTO {
    mrpNo?: string;
    mpsNo?: string;
    mrpGatheringNo?: string;
    itemClassification?: string;
    itemCode?: string;
    itemName?: string;
    unitOfMrp?: string;
    requiredAmount?: number;
    orderDate?: string;
    requiredDate?: string;
  }

  export interface WorkOrderInfoTO {
    workOrderNo?: string;
    mrpNo?: string;
    mpsNo?: string;
    mrpGatheringNo?: string;
    itemClassification?: string;
    itemCode?: string;
    itemName?: string;
    unitOfMrp?: string;
    requiredAmount?: string;
    workSiteCode?: string;
    workSiteName?: string;
    productionProcessCode?: string;
    productionProcessName?: string;
    inspectionStatus?: string;
    productionStatus?: string;
    completionStatus?: string;
    actualCompletionAmount?: string;
  }

  export interface WorkOrderSimulationTO {
    mrpNo?: string;
    mpsNo?: string;
    mrpGatheringNo?: string;
    itemClassification?: string;
    itemCode?: string;
    itemName?: string;
    unitOfMrp?: string;
    requiredAmount?: string;
    stockAfterWork?: string;
    orderDate?: string;
    requiredDate?: string;
  }

  export interface WorkSiteLogTO {
    workOrderNo?: string;
    itemCode?: string;
    itemName?: string;
    progress?: string;
    workSiteName?: string;
    workDate?: string;
    productionProcessCode?: string;
    productionProcessName?: string;
  }

  export interface WorkSiteSimulationTO {
    workOrderNo?: string;
    mrpNo?: string;
    mpsNo?: string;
    workSieteName?: string;
    wdItem?: string;
    parentItemCode?: string;
    parentItemName?: string;
    itemClassIfication?: string;
    itemCode?: string;
    itemName?: string;
    requiredAmount?: string;
  }

  export interface WorkSiteTO {
    productionProcessCode?: string;
    workSiteCode?: string;
    workSiteName?: string;
  }