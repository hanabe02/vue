import axios from 'axios';
import { selectedItem, selecteditem, search } from './ClientFinancialInfo.vue';

// 삭제
export const deleterow = async () => {
try {
if (selectedItem.value.length === 0) {
console.warn('삭제할 항목을 선택하세요.');

return;
}

const accountAssociatesCodeToDelete = selecteditem.value;

const res = await axios.delete(`http://localhost:8282/logi/base/deleteFinance/${accountAssociatesCode.value}`);

console.log('삭제 성공:', res.data);

search();
}
catch (error) {
console.error('데이터 삭제 오류:', error);
}
};
