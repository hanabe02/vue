import { defineStore } from 'pinia'
import {
  addTransportation,
  allTransportation,
  deleteTransportation,
  getTransportation,
  updateTransportation,
} from '@/api/logi/transportation'

export const transStore = defineStore('transStore', {
  state: () => ({
    allTransInfo: [] as any,
    getTransInfo: [] as any,
    addTransInfo: [] as any,
    updateTransInfo: [] as any,
    deleteTransInfo: [] as any,
  }),

  actions: {
    // 운송수단 전체 조회
    async All_TRANSPORTATION_URL() {
      try {
        const res = await allTransportation()

        // console.log("all", res);
        // console.log("all", res.data);

        this.allTransInfo = res.data.vehicleList
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 운송수단 type별 조회
    async TRANSPORTATION_URL(type: string) {
      try {
        const res = await getTransportation(type)

        // console.log("trans", res);
        // console.log("trans", res.data);

        this.getTransInfo = res.data.transVehicleList
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 운송수단 추가
    async ADD_TRANSPORTATION_URL(AddList: object) {
      try {
        console.log('ㅁ', AddList)

        const res = await addTransportation(AddList)

        console.log('으악', res)

        this.addTransInfo = res

        console.log('으악2', this.addTransInfo)
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 운송수단 수정
    async UPDATE_TRANSPORTATION_URL(transList: any) {
      try {
        const res = await updateTransportation(transList)

        console.log('pp', res.config.data)
        console.log('pp', res.data)

        this.updateTransInfo = res
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 운송수단 삭제
    async DELETE_TRANSPORTATION_URL(vehicleNumber: string) {
      try {
        await deleteTransportation(vehicleNumber)
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },
  },
})
