import { defineStore } from 'pinia'
import {
  addItem,
  addItemGroup,
  deleteItem,
  deleteItemGroup,
  getItem,
  getItemGroup,
  updateItem,
  updateItemGroup,
} from '@/api/logi/item'

export const itemStore = defineStore('itemStore', {
  state: () => ({
    getItemInfo: [] as any, // 품목정보
    addItemInfo: [] as any,
    updateItemInfo: [] as any,
    deleteItemInfo: [] as any,
    getItemGroupInfo: [] as any, // 품목군정보
    addItemGroupInfo: [] as any,
    updateItemGroupInfo: [] as any,
    deleteItemGroupInfo: [] as any,
  }),

  actions: {
    // 품목 조회
    async GET_ITEM_INFO() {
      try {
        const res = await getItem()

        console.log('g', res)
        console.log('g', res.data)

        this.getItemInfo = res.data.gridRowJson
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 품목 추가
    async ADD_ITEM_INFO(batchList: any) {
      try {
        const res = await addItem(batchList)

        console.log('123', res)
        console.log('123', res.data)

        this.addItemInfo = res.config.data
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 품목 수정
    async UPDATE_ITEM_INFO(batchList: any) {
      try {
        const res = await updateItem(batchList)

        console.log('pp', res)
        console.log('pp', res.data)

        this.updateItemInfo = res
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 품목 삭제
    async DELETE_ITEM_INFO(itemCode: string) {
      try {
        const res = await deleteItem(itemCode)

        console.log('d', res)
        console.log('d', res.data)

        // this.deleteItemInfo = res.data.codeList;
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    //////////////////////////////////

    // 품목군 조회
    async GET_ITEMGROUP_INFO() {
      try {
        const res = await getItemGroup()

        console.log('g', res)
        console.log('g', res.data)

        this.getItemGroupInfo = res.data.gridRowJson
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 품목군 추가
    async ADD_ITEMGROUP_INFO(batchList: any) {
      try {
        const res = await addItemGroup(batchList)

        console.log('123', res)
        console.log('123', res.data)

        this.addItemGroupInfo = res.config.data
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 품목군 수정
    async UPDATE_ITEMGROUP_INFO(batchList: any) {
      try {
        const res = await updateItemGroup(batchList)

        console.log('pp', res)
        console.log('pp', res.data)

        this.updateItemGroupInfo = res
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },

    // 품목군 삭제
    async DELETE_ITEMGROUP_INFO(itemCode: string) {
      try {
        const res = await deleteItemGroup(itemCode)

        console.log('d', res)
        console.log('d', res.data)

      // this.deleteItemGroupInfo = res.data.codeList;
      }
      catch (error) {
        console.error('Error fetching data:', error)
      }
    },
  },
})
