import axios from 'axios';

export const fetchData = async (batchList: any) => {
console.log('1111111', batchList);

const selectedValue = {
batchList: batchList.value,
status: "INSERT",
};

const res = await axios.post("http://localhost:8282/logi/base/itemBatchListProcess", batchList);

console.log('제발', res);

console.log('rara', res.data.itemTo);

//   itemCode.value = res.data.itemTo.itemCode
//   itemName.value = res.data.itemTo.itemName
//   itemGroupCode.value = res.data.itemTo.itemGroupCode
//   itemClassification.value = res.data.itemTo.itemClassification
//   unitOfStock.value = res.data.itemTo.unitOfStock
//   lossRate.value = res.data.itemTo.lossRate
//   leadTime.value = res.data.itemTo.leadTime
//   standardUnitPrice.value = res.data.itemTo.standardUnitPrice
//   description.value = res.data.itemTo.description
};
