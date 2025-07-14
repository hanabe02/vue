import { hrApi } from '@/api';

const conExp = 'conexpense/conexp'

// 전체 조회
function getReqAllList() {
  return hrApi.get(`${conExp}`)
}

// 검색
function findConList(payload: { startDate?: string; endDate?: string; searchEmpCode?: string }) {
  console.log(payload)

  return hrApi.get(`${conExp}/${payload.startDate}/${payload.endDate}/${payload.searchEmpCode}`)
}

// 등록(신규)
function addConRegi(conRegi: any) {
  console.log('경조비 신청 API', conRegi)

  return hrApi.post(`${conExp}`, conRegi)
}

// 수정
function editConExp(conUpdate: any) {
  console.log('내역 수정', conUpdate)

  return hrApi.put(`${conExp}`, conUpdate)
}

// 삭제
function removeConExp(reqNum: string) {
  console.log('내역 삭제', reqNum)

  return hrApi.delete(`${conExp}`, {
    params: {
      reqNum,
    },
  })
}

// 승인
function approveConExp(reqNumList: string[]) {
  console.log('승인 요청', reqNumList)

  return hrApi.put(`${conExp}/status`, {
    reqNumList,
    status: '승인',
  });
}

// 반려
function rejectConExp(reqNumList: string[]) {
  console.log('반려 요청', reqNumList)

  return hrApi.put(`${conExp}/status`, {
    reqNumList,
    status: '반려',
  });
}


export {
  addConRegi, editConExp, findConList, getReqAllList, removeConExp, approveConExp, rejectConExp
}
