import { defineStore } from 'pinia'
import {
  addFinanceClient,
  addGeneralClient,
  addWorkplace,
  deleteFinanceClient,
  deleteGeneralClient,
  deleteWorkplace,
  getCompanyInfo,
  getDeptInfo,
  getFinanceClient,
  getGeneralClient,
  getWorkplaceDetail,
  getWorkplaceList,
  updateFinanceClient,
  updateGeneralClient,
  updateWorkplace,
} from '@/api/logi/base'

export const baseStore = defineStore('baseStore', {
  state: () => ({
    companyInfo: [] as any, // 회사정보
    workplaceInfo: [] as any, // 사업장정보
    workplaceDetailInfo: [] as any, // 사업장 상세정보
    addWorkplaceInfo: [] as any, // 사업장 추가
    updateWorkplaceInfo: [] as any, // 사업장 수정
    deleteWorkplaceInfo: [] as any[], // 사업장 식제
    deptInfo: [] as any, // 부서 정보
    generalClientInfo: [] as any, // 일반거래처 조회
    addGeneralClientInfo: [] as any, // 일반거래처 추가
    updateGeneralClientInfo: [] as any, // 일반거래처 수정
    deleteGeneralClientInfo: [] as any, // 일반거래처 삭제
    financeClientInfo: [] as any, // 금융거래처 조회
    addfinanceClientInfo: [] as any, // 금융거래처 추가
    updatefinanceClientInfo: [] as any, // 금융거래처 수정
    deletefinanceClientInfo: [] as any, // 금융거래처 삭제
  }),
  actions: {
    // 회사정보 조회
    async GET_COMPANY_INFO() {
      try {
        const res = await getCompanyInfo()

        this.companyInfo = res.data.companyInfo
      }
      catch (error: any) {
        console.error('Error fetching data:', error)
      }
    },

    /////////////////////////////////////////

    // 사업장정보 조회
    async GET_WORKPLACE_INFO() {
      try {
        const res = await getWorkplaceList()

        console.log('ddd', res)
        console.log('ddd', res.data)

        this.workplaceInfo = res.data.workplaceList
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 한 행클릭시 사업장 상세정보 조회
    async GET_WORKPLACE_DETAIL_INFO(workplaceCode: string) {
      try {
        const res = await getWorkplaceDetail(workplaceCode)

        console.log('vv', res)
        console.log('vv', res.data)

        this.workplaceDetailInfo = res.data.workplaceInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 사업장 추가
    async ADD_WORKPLACE_INFO(menudata: any) {
      try {
        const res = await addWorkplace(menudata)

        console.log('vv', res)
        console.log('vv', res.data)

        this.addWorkplaceInfo = res.data.workplaceInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 사업장 수정
    async UPDATE_WORKPLACE_INFO(updateDataList: any) {
      try {
        const res = await updateWorkplace(updateDataList)

        console.log('tt', res)
        console.log('tt', res.data)

        this.updateWorkplaceInfo = res.data.workplaceInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 사업장 삭제
    async DELETE_WORKPLACE_INFO(workplaceCode: string) {
      try {
        const res = await deleteWorkplace(workplaceCode)

        console.log('tt', res)
        console.log('tt', res.data)

        this.deleteWorkplaceInfo = res.data.workplaceCode
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    ////////////////////////////////////

    // 부서정보 조회
    async GET_DEPT_INFO() {
      try {
        const res = await getDeptInfo()

        console.log('ccc', res)
        console.log('ccc', res.data)

        this.deptInfo = res.data.deptInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    /////////////////////////////////////

    // 일반거래처 조회
    async GET_GENERAL_CLIENT_INFO() {
      try {
        const res = await getGeneralClient()

        console.log('general', res)
        console.log('general', res.data)

        this.generalClientInfo = res.data.clientInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 일반거래처 추가
    async ADD_GENERAL_CLIENT_INFO(clientdata: any) {
      try {
        const res = await addGeneralClient(clientdata)

        console.log('ㅁㅇㅇ', res)
        console.log('ㅁㅇㅇ', res.data)

        this.addGeneralClientInfo = res.data.clientInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 일반거래처 수정
    async UPDATE_GENERAL_CLIENT_INFO(updateDataList: any) {
      try {
        const res = await updateGeneralClient(updateDataList)

        console.log('pp', res)
        console.log('pp', res.data)

        this.updateGeneralClientInfo = res.data.clientInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 일반거래처 삭제
    async DELETE_GENERAL_CLIENT_INFO(customerCode: string) {
      try {
        const res = await deleteGeneralClient(customerCode)

        console.log('d', res)
        console.log('d', res.data)

        this.deleteGeneralClientInfo = res.data.clientInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    ////////////////////////////////////////

    // 금융거래처 조회
    async GET_FINANCE_CLIENT_INFO() {
      try {
        const res = await getFinanceClient()

        console.log('f', res)
        console.log('f', res.data)

        this.getFinanceClient = res.data.financeInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 금융거래처 추가
    async ADD_FINANCE_CLIENT_INFO(clientdata: any) {
      try {
        const res = await addFinanceClient(clientdata)

        console.log('ㅁㅇㅇ', res)
        console.log('ㅁㅇㅇ', res.data)

        this.addfinanceClientInfo = res.data.financeInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 금융거래처 수정
    async UPDATE_FINANCE_CLIENT_INFO(clientdata: any) {
      try {
        const res = await updateFinanceClient(clientdata)

        console.log('pp', res)
        console.log('pp', res.data)

        this.updateFinanceClient = res.data.financeInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 금융거래처 삭제
    async DELETE_FINANCE_CLIENT_INFO(accountAssociatesCode: string) {
      try {
        const res = await deleteFinanceClient(accountAssociatesCode)

        console.log('d', res)
        console.log('d', res.data)

        this.deletefinanceClientInfo = res.data.financeInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },
  },
})
