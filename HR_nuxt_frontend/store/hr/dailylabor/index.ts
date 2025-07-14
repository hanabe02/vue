import { defineStore } from 'pinia'
import {
  addDailyLaborAttend, addDailyLaborEmp, deleteDailyLaborAttend, deleteDailyLaborEmp,
  deleteDailyLaborEmpLog,
  deleteDailyLaborSalary, deleteSalaryTax,
  findSalaryList, getDailyLaborEmpDetail, insertSalaryList,
  selectDailyLaborAttend, selectDailyLaborEmp, selectDailyLaborEmpLog, selectSalaryTaxList,
  selectTax, updateDailyLaborAttend,
  updateDailyLaborAttendApproval, updateDailyLaborEmp, updateDailyLaborSalaryList, updateSalaryTaxList,
} from '@/api/hr/dailylabor'

export const dailyLaborStore = defineStore('dailyLaborStore', {
  state: () => ({
    dailyLaborEmpList: [],
    dailyLaborEmpDetailInfo: [],
    dailyLaborAttendList: [],
    dailyLaborSalaryList: [],
    dailyLaborSalaryTaxList: [],
    dailyLaborTaxList: [],
    isModal: false,
    detailSalaryTax: [],
    empLogList: [],
  }),
  actions: {
    async SEARCH_EMP_LIST() {
      try {
        const res = await selectDailyLaborEmp()

        console.log(res.data.dailyLaborEmpList)
        this.dailyLaborEmpList = res.data.dailyLaborEmpList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async FETCH_EMP_DETAIL(clickEmp: string) {
      try {
        const res = await getDailyLaborEmpDetail(clickEmp)

        console.log(res.data.dailyLaborEmpDetailInfo[0])
        this.dailyLaborEmpDetailInfo = res.data.dailyLaborEmpDetailInfo[0]
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async INSERT_EMP(regiEmp: any) {
      try {
        await addDailyLaborEmp(regiEmp)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async UPDATE_EMP(payload: any) {
      try {
        await updateDailyLaborEmp(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async DELETE_EMP(payload: any) {
      try {
        await deleteDailyLaborEmp(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async FETCH_EMP_LOG() {
      try {
        const res = await selectDailyLaborEmpLog()

        this.empLogList = res.data.dailyLaborEmpLogList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async DELETE_EMP_LOG(payload: any) {
      try {
        await deleteDailyLaborEmpLog(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async INSERT_ATTEND(regiAttend: any) {
      try {
        await addDailyLaborAttend(regiAttend)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async UPDATE_WORK_ATTEND(updateAttend: any) {
      try {
        await updateDailyLaborAttend(updateAttend)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async FETCH_ATTEND(date: any, approval: any) {
      try {
        const res = await selectDailyLaborAttend(date, approval)

        this.dailyLaborAttendList = res.data.dailyLaborAttendList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async DELETE_ATTEND(payload: any) {
      try {
        await deleteDailyLaborAttend(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async UPDATE_ATTEND_APPROVAL(payload: any) {
      try {
        await updateDailyLaborAttendApproval(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async INSERT_SALARY_LIST(payload: any) {
      try {
        await insertSalaryList(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async FIND_SALARY_LIST(payload: any) {
      try {
        const res = await findSalaryList(payload)

        this.dailyLaborSalaryList = res.data.dailyLaborSalaryList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async UPDATE_SALARY_LIST(payload: any) {
      try {
        await updateDailyLaborSalaryList(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async DELETE_SALARY_LIST(payload: any) {
      try {
        await deleteDailyLaborSalary(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async FETCH_SALARY_TAX(payload: any) {
      try {
        const res = await selectSalaryTaxList(payload)

        this.dailyLaborSalaryTaxList = res.data.dailyLaborSalaryTaxList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async FETCH_TAX(payload: any) {
      try {
        const res = await selectTax(payload)

        this.dailyLaborTaxList = res.data.dailyLaborTaxList[0]
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async MODAL_SET(payload: any) {
      try {
        this.isModal = payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async DETAIL_SALARY_TAX(payload: any) {
      try {
        this.detailSalaryTax = payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async UPDATE_SALARY_TAX(payload: any) {
      try {
        await updateSalaryTaxList(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async DELETE_SALARY_TAX(payload: any) {
      try {
        await deleteSalaryTax(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
  },
})
