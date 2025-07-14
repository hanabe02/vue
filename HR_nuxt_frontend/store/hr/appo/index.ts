import { defineStore } from 'pinia'
import {
  approveAppointment,
  editAppoRequest,
  findAllAppoLog,
  findAppoLogByYear,
  findAppoRequest,
  registerAppointment,
  removeAppoLog,
} from '@/api/hr/appo'

export const appoStore = defineStore('appoStore', {
  state: () => ({
    appoList: [],
    logList: [],
  }),

  actions: {
    /**
     * 인사발령 요청 조회
     */
    async SEARCH_APPO_REQUEST() {
      try {
        console.log('인사발령 요청 조회')

        const response = await findAppoRequest()

        this.appoList = response.data.appoList
        console.log(response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사발령 요청
     */
    async ADD_APPO_REQUEST(payload: any) {
      try {
        console.log('인사발령 요청')

        const response = await registerAppointment(payload)
        if (response.data.errorCode !== -1)
          return response.data.errorCode

        else
          alert('발령요청에 실패하였습니다.')
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사발령 요청 수정
     */
    async EDIT_APPO_REQUEST(payload: any) { // 객체로 넘어오면 {}
      try {
        console.log('인사발령 요청 수정')

        const response = await editAppoRequest(payload)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사발령 승인/반려
     */
    async APPROVE_OR_REFER_APPO(payload: any) { // 객체로 넘어오면 {}
      try {
        console.log('인사발령 승인/반려')

        const response = await approveAppointment(payload)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사발령 이력 조회
     */
    async SEARCH_APPO_LOG() {
      try {
        console.log('인사발령 이력 조회')

        const response = await findAllAppoLog()

        this.logList = response.data.logList
        console.log(response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사발령 이력 연도별 조회
     */
    async SEARCH_APPO_LOG_BY_YEAR(searchYear: any) {
      console.log(searchYear)
      try {
        console.log('인사발령 이력 연도별 조회')

        const response = await findAppoLogByYear(searchYear)

        this.logList = response.data.logList
        console.log(response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 인사발령 이력 삭제
     */
    async DELETE_APPO_LOG(payload: any) {
      try {
        console.log('인사발령 이력 삭제')

        return await removeAppoLog(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
  },
})
