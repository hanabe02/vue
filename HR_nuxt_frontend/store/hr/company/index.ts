import { defineStore } from 'pinia'
import { searchCustomerList } from '@/api/hr/company'

export const companyStore = defineStore('companyStore', {
  state: () => ({
    companyCodeList: [],
  }),
  actions: {
    async SEARCH_CUSTOMER_LIST(searchOption: any) {
      try {
        const res = await searchCustomerList(searchOption)

        console.log(res)
        this.companyCodeList = res.data.gridRowJson
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
  },
})
