import { defineStore } from 'pinia'
import {
  approReqEdu, findEduListByStatus, removeEduList,
} from '@/api/hr/edu'

export const eduStore = defineStore('eduStore', {
  state: () => ({
    eduList: [{
      deptName: '',
      durationOfTraining: '',
      empCode: '',
      empName: '',
      searchYear: '',
      status: '',
    }],
    selEduList: [{
      eduCode: '',
      eduName: '',
      empCode: '',
      empName: '',
      deptCode: '',
      deptName: '',
      eduHistory: '',
      searchYear: '',
      status: '',
    }],
  }),
  actions: {
  /**
   * 교육 관리 및 승인요청
   */
    async APPRO_EDU_LIST(payload: any) {
      try {
        console.log('교육 승인요청중')

        const response = await approReqEdu(payload)

        this.eduList = response.data.eduList

        console.log('response.data', response.data)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 교육 관리 승인상태별 조회
     */
    async SEARCH_EDU_LIST_STATUS(payload: any) {
      try {
        console.log('교육 관리 승인여부 조회')

        const response = await findEduListByStatus(payload)

        this.selEduList = response.data
        console.log('response', response.data)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 교육 관리 내역 삭제
     */
    async DELETE_EDU_LIST(payload: any) {
      try {
        console.log('교육 관리 내역 삭제')

        return await removeEduList(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
  },
})
