import { defineStore } from 'pinia'
import { removeCertificateRequest, updateCertificateRequest } from '@/api/hr/certificate'

export const certStore = defineStore('certStore', {
  state: () => ({
    response: [],
  }),
  actions: {
    // 증명서 신청 삭제
    async REMOVE_CERT_REQUEST(searchOption: any) {
      try {
        const res = await removeCertificateRequest(searchOption)

        console.log(res.data)
        this.response = res.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // 증명서 신청 수정
    async UPDATE_CERT_REQUEST(searchOption: any) {
      try {
        const res = await updateCertificateRequest(searchOption)

        console.log(res.data)
        this.response = res.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
  },
})
