// store/hr/emp.ts
import { defineStore } from 'pinia'

export const useEmpStore = defineStore('emp', {
  state: () => ({
    empCode: '',
  }),
  actions: {
    // localStorage에서 불러오기
    loadEmpCode() {
      if (process.client) {
        this.empCode = localStorage.getItem('empCode') || ''
      }
    },
    // empCode 설정 + localStorage에 저장
    setEmpCode(code: string) {
      this.empCode = code
      if (process.client) {
        localStorage.setItem('empCode', code)
      }
    },
    // 로그아웃 등 초기화용
    clearEmpCode() {
      this.empCode = ''
      if (process.client) {
        localStorage.removeItem('empCode')
      }
    },
  },
})
