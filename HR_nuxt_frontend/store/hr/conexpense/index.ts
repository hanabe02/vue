import { defineStore } from 'pinia'
import { addConRegi, editConExp, findConList, getReqAllList, removeConExp, approveConExp, rejectConExp } from '@/api/hr/conexpense'


export const conExpStore = defineStore('conExpStore', {
  state: () => ({
    conExpList: [{
      reqNum: '',
      empCode: '',
      empName: '',
      reqDate: '',
      conType: '',
      trgt: '',
      conTermStart: '',
      conTermEnd: '',
      conAmt: '',
      rel: '',
      conPlace: '',
      bank: '',
      accNum: '',
      accHolder: '',
      note: '',
      approvalStatus: '',
    }],
    allReqList: [],
    conExpenseList: [] as any,
  }),
  actions: {
    // 전체 내역 조회
    async FETCH_ALL_REQ() { // 객체로 넘어오면 {}
      try {
        const response = await getReqAllList()

        this.allReqList = response.data.conExpList

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // 검색 조회
    async FETCH_REQ_DETAIL(payload: { startDate?: string; endDate?: string; searchEmpCode?: string }) {
      try {
        console.log(payload)

        const response = await findConList(payload)

        console.log('검색1', response)
        console.log('검색2', response.data)
        console.log('검색3', response.data.conExpenseList)
        this.conExpenseList = response.data.conExpenseList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // 등록(신규)
    async REGI_CONEXP(conRegi: any) {
      console.log(conRegi)
      try {
        const response = await addConRegi(conRegi)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // 수정
    async UPDATE_CONEXP(conUpdate: any) { // 객체로 넘어오면 {}
      console.log(conUpdate)
      try {
        const response = await editConExp(conUpdate)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // 삭제
    async DELETE_CONEXP(reqNum: string) {
      try {
        console.log('삭제~~~~~', reqNum)

        const response = await removeConExp(reqNum)

        console.log('삭제 회신', response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // 승인
    async APPROVE_CONEXP(reqNumList: string[]) {
      try {
        const response = await approveConExp(reqNumList);
        console.log('승인 완료', response.data);
      } catch (err: any) {
        console.error('승인 오류', err);
        throw new Error(err);
      }
    },

    // 반려
    async REJECT_CONEXP(reqNumList: string[]) {
      try {
        const response = await rejectConExp(reqNumList);
        console.log('반려 완료', response.data);
      } catch (err: any) {
        console.error('반려 오류', err);
        throw new Error(err);
      }
    },


  },
})
