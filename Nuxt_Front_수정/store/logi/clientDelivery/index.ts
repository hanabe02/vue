import { defineStore } from 'pinia'
import {
  addClientDelivery,
  deleteClientDelivery,
  getClientDeliveryList,
  getOutputList,
  updateClientDelivery,
} from '@/api/logi/clientdelivery'

export const clientDeliveryStore = defineStore('clientDeliveryStore', {
  state: () => ({
    getClientDeliveryInfo: [] as any,
    addClientDeliveryInfo: [] as any,
    updateClientDeliveryInfo: [] as any,
    deleteClientDeliveryInfo: [] as any,
    getOutputInfo: [] as any,
  }),

  actions: {
    // 츨고 조회
    async GET_OUTPUT_INFO() {
      try {
        const res = await getOutputList()

        console.log('H', res)
        console.log('H', res.data)

        this.getOutputInfo = res.data.outputList
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 배송 조회
    async GET_CLIENT_DELIVERY_URL() {
      try {
        const res = await getClientDeliveryList()

        console.log('g', res)
        console.log('g', res.data)
        console.log('g', res.data.clientDeliveryList[0])

        this.getClientDeliveryInfo = res.data.clientDeliveryList

        console.log('dld?', this.getClientDeliveryInfo)
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 배송 추가
    async ADD_CLIENT_DELIVERY_URL(addData: any) {
      try {
        console.log('ㅁㅇㅇ', addData)

        const res = await addClientDelivery(addData)

        console.log('123', res)

        this.addClientDeliveryInfo = res
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 배송 수정
    async UPDATE_CLIENT_DELIVERY_URL(batchList: any) {
      try {
        const res = await updateClientDelivery(batchList)

        console.log('pp', res)
        console.log('pp', res.data)

        this.updateClientDeliveryInfo = res
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 배송 삭제
    async DELETE_CLIENT_DELIVERY_URL(outputNumber: string) {
      try {
        const res = await deleteClientDelivery(outputNumber)

        console.log('d', res)
        console.log('d', res.data)

        // this.deleteClientDeliveryInfo = res.data.codeList;
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },
  },
})
