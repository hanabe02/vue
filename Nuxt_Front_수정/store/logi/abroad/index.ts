import { defineStore } from 'pinia'
import {
  addAbroadSchedule,
  deleteAbroadSchedule,
  getAbroadScheduleList,
  updateAbroadSchedule,
} from '@/api/logi/abroad'

export const abroadStore = defineStore('abroadStore', {
  state: () => ({
    abroadScheduleInfo: [] as any, // 행사정보
    addAbroadScheduleInfo: [] as any, // 행사 추가
    updateAbroadScheduleInfo: [] as any, // 행사 수정
    deleteAbroadScheduleInfo: [] as any[], // 행사 식제
  }),
  actions: {
    // 행사정보 조회
    async GET_ABROADSCHEDULE_INFO() {
      try {
        const res = await getAbroadScheduleList()

        console.log('ddd', res)
        console.log('ddd', res.data)

        this.abroadScheduleInfo = res.data.abroadScheduleList
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 행사 추가
    async ADD_ABROADSCHEDULE_INFO(menudata: any) {
      try {
        const res = await addAbroadSchedule(menudata)

        console.log('vv', res)
        console.log('vv', res.data)

        this.addAbroadScheduleInfo = res.data.abroadScheduleInfo
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 행사 수정
    async UPDATE_ABROADSCHEDULE_URL(updateDataList: any) {
      try {
        const res = await updateAbroadSchedule(updateDataList)

        console.log('tt', res)
        console.log('tt', res.data)

        this.updateAbroadScheduleInfo = res.data
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 행사 삭제
    async DELETE_ABROADSCHEDULE_INFO(abroadScheduleCode: string) {
      try {
        const res = await deleteAbroadSchedule(abroadScheduleCode)

        console.log('tt', res)
        console.log('tt', res.data)

        this.deleteAbroadScheduleInfo = res.data.abroadScheduleCode
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },
  },
})
