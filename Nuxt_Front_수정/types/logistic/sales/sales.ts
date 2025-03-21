export interface ContractDetailTO {
    contractDetailNo?: string;
    contractNo?: string;
    itemCode?: string;
    itemName?: string;
    unitOfContract?: string;
    dueDateOfContract?: string;
    estimateAmount?: string;
    stockAmountUse?: string;
    productionRequirement?: string;
    unitPriceOfContract?: string;
    sumPriceOfContract?: string;
    processingStatus?: string;
    operationCompletedStatus?: string;
    deliveryCompletionStatus?: string;
    description?: string;
}

export interface ContractInfoTO {
    contractNo?: string;
    estimateNo?: string;
    contractType?: string;
    contractTypeName?: string;
    customerCode?: string;
    customerName?: string;
    estimateDate?: string;
    contractDate?: string;
    contractRequester?: string;
    personCodeInCharge?: string;
    empNameInCharge?: string;
    description?: string;
    contractDetailTOList: ContractDetailTO[];
    deliveryCompletionStatus?: string;
}

export interface ContractTO {
    contractNo?: string;
    estimateNo?: string;
    contractType?: string;
    customerCode?: string;
    contractDate?: string;
    contractRequester?: string;
    personCodeInCharge?: string;
    description?: string;
    deliveryCompletionStatus?: string;
    contractDetailTOList?: ContractDetailTO[];
}
export interface DeliveryInfoTO {
    deliveryNo?: string;
    estimateNo?: string;
    contractNo?: string;
    contractDetailNo?: string;
    customerCode?: string;
    personCodeInCharge?: string;
    itemCode?: string;
    itemName?: string;
    unitOfDelivery?: string;
    deliveryAmount?: string;
    unitPrice?: string;
    sumPrice?: string;
    deliverydate?: string;
    deliveryPlaceName?: string;
    finalizeStatus?: string;
}

export interface EstimateDetailTO {
    rowId?: string; // React View에서 행 index 값 나타내는 변수
    unitOfEstimate?: string;
    estimateNo?: string;
    unitPriceOfEstimate?: number;
    estimateDetailNo?: string;
    sumPriceOfEstimate?: number;
    description?: string;
    itemCode?: string;
    estimateAmount?: number;
    dueDateOfEstimate?: string;
    itemName?: string;
}

export interface EstimateTO {
    effectiveDate?: string;
    estimateNo?: string;
    estimateRequester?: string;
    description?: string;
    contractStatus?: string;
    customerCode?: string;
    customerName?: string; // React View에서 거래처명도 함께 보냈는데 TO에 추가
    personCodeInCharge?: string;
    personNameCharge?: string;
    estimateDate?: string;
    estimateDetailTOList?: EstimateDetailTO[]; // EstimateDetailTO 리스트

}

export interface logisticExelTO {
    estimateDate?: string;
    estimateNo?: string;
    customerCode?: string;
    customerName?: string;
    businessLicenseNumber?: string;
    estimateAmount?: string;
    unitPriceOfEstimate?: string;
    sumPriceOfEstimate?: string;
    itemName?: string;
    dueDateOfEstimate?: string;
    customerTelNumber?: string;
}

export interface SalesPlanTO {
    unitPriceOfSales?: number;
    salesAmount?: number;
    salesPlanNo?: string;
    description?: string;
    salesPlanDate?: string;
    sumPriceOfSales?: number;
    itemCode?: string;
    dueDateOfSales?: string;
    unitOfSales?: string;
    mpsApplyStatus?: string;
    itemName?: string;
    status?: string;
}

export interface SalesPlane {
    salesPlanNo?: string;
    itemCode?: string;
    itemName?: string;
    unitOfSales?: string;
    salesPlanDate?: string;
    dueDateOfSales?: string;
    salesAmount?: string;
    unitPriceOfSales?: string;
    sumPriceOfSales?: string;
    mpsApplyStatus?: string;
    description?: string;
}