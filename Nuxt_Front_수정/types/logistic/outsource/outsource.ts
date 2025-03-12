export interface ForwardTO{
    mrpGatheringNo?:string;
    itemClassification?:string;
    itemCode?:string;
    itemName?:string;
    unitOfMrp?:string;
    inputAmount?:string;
    requiredAmount?:string;
    stockAfterWork?:string;
    orderDate?:string;
    requiredDate?:string;
}

export interface OutInspectionTO{
    claimDate?:string;
    dueDate?:string;
    itemCode?:string;
    itemName?:string;
    mrpGatheringNo?:string;
    mrpGatheringSeq?:string;
    necessaryAmount?:string;
    orderOrProductionStatus?:string;
    outSourcAmount?:string;
    sumPriceOfOutsourc?:string;
    unitOfMrpGathering?:string;
    unitPriceOfOutsourc?:string;
    outsourcNo?:string;
    forwardStatus?:string;
    completionStatus?:string;
    actualCompletionAmount?:string;
}


export interface OutsourcTO extends BaseTO{
    claimDate?:string;
    dueDate?:string;
    itemCode?:string;
    itemName?:string;
    mrpGatheringNo?:string;
    mrpGatheringSeq?:Number;
    necessaryAmount?:Number;
    orderOrProductionStatus?:string;
    outsourcAmount?:Number;
    sumPriceOfOutsourc?:Number;
    unitOfMrpGathering?:string;
    unitPriceOfOutsourc?:string;
    outsourcNo?:string;
    forwardStatus?:string;
}

export interface BaseTO{
    errorCode?:Number;
    errorMsg?:string;
    status?:string;
    chk?:string;
}





