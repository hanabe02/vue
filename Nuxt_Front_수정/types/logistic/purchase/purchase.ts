export interface BomDeployTO {
    bomNo: string
    bomLevel: number
    parentItemCode: string
    itemCode: string
    itemName: string
    unitOfStock: string
    netAmount: number
    lossRate: string
    necessaryAmount: string
    leadTime: string
    isLeaf: string
    description: string
  }
  
  export interface BomInfoTO {
    itemCode: string
    parentItemCode: string
    no: number
    itemName: string
    itemClassification: string
    itemClassificationName: string
    netAmount: number
    description: string
  }
  
  export interface BomTO {
    itemCode: string
    parentItemCode: string
    no: number
    netAmount: number
    description: string
    status: string
  }
  
  export interface OrderDialogTempTO {
    mrpGatheringNo: string
    itemCode: string
    itemName: string
    unitOfMrp: string
    requiredAmount: string
    stockAmount: string
    calculatedRequiredAmount: string
    standardUnitPrice: string
    sumPrice: string
  }
  
  export interface OrderInfoTO {
    orderNo: string
    orderDate: string
    orderInfoStatus: string
  
    orderSort: string
    itemCode: string
    itemName: string
    orderAmount: string
    inspectionStatus: string
    orderSlipStatus: string
  }
  
  export interface StockLogTO {
    logDate: string
    itemCode: string
    itemName: string
    amount: string
    reason: string
    cause: string
    effect: string
  }
  
  export interface StockTO {
    warehouseCode: string
    itemCode: string
    itemName: string
    unitOfStock: string
    safetyAllowanceAmount: string
    stockAmount: string
    orderAmount: string
    inputAmount: string
    deliveryAmount: string
    totalStockAmount: string
  }
  