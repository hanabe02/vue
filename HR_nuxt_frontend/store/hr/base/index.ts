import { defineStore } from 'pinia'
import {
  addDept, addHoliday,
  addProject,
  addPublicHoliday, findDeptManageList, modifyDept,
  modifyHoliday,
  modifyProject, removeDept,
  removeHoliday, removeProject, selectCompanyList, selectDeptList,
  selectDeptListDetail, selectHolidayList, selectHolidayType, selectMonthList, selectPositionList, selectProjectList, selectWorkplaceList, selectYearList,
} from '@/api/hr/base'

export const baseStore = defineStore('baseStore', {
  state: () => ({
    deptList: [],
    deptlistDetail: [],
    deptManageList: [],
    posList: [],
    yearList: [],
    companyList: [],
    workplaceList: [],
    monthList: [],
    holidayList: [],
    holidayTypeList: [],
    selectYear: '',
    isDialog: false,
    deptRow: [],
    holidayRow: [],
    projectList: [],
    project: [],
    isDialogBtn: false,
  }),
  actions: {
    async FETCH_DEPTLIST() {
      try {
        const response = await selectDeptList()

        console.log('[deptList]', response.data.deptlist)
        this.deptList = response.data.deptlist
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * deptList-직원 있는 부서만
     */
    async FETCH_DEPTLIST_DETAIL() {
      try {
        const response = await selectDeptListDetail()

        this.deptlistDetail = response.data.deptlist

        return response.data.deptlistDetail
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * deptManageList
     */
    async FIND_DEPT_MANAGE_LIST() {
      try {
        const response = await findDeptManageList()

        console.log(response.data)
        this.deptManageList = response.data.deptManageList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * positionList
     */
    async FETCH_POSLIST() {
      try {
        const response = await selectPositionList()

        this.posList = response.data.positionList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * yearList
     * @param commit
     * @return {Promise<*>}
     * @constructor
     */
    async FETCH_YEARLIST() {
      try {
        const response = await selectYearList()

        console.log(response.data.yearList)
        this.yearList = response.data.yearList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * monthList
     */
    async FETCH_MONTHLIST() {
      try {
        const response = await selectMonthList()

        console.log(response.data.monthList)
        this.monthList = response.data.monthList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * companyList
     */
    async FETCH_COMPANYLIST() {
      try {
        const response = await selectCompanyList()

        this.companyList = response.data.companyList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * workplaceList
     */
    async FETCH_WORKPLACELIST() {
      try {
        const response = await selectWorkplaceList()

        this.workplaceList = response.data.workplaceList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 년도 or 년도-월에 맞는 휴일 찾기
     * allHolidayList
     */
    async FETCH_HOLIDAYLIST(payload: any) {
      try {
        const response = await selectHolidayList(payload)

        this.holidayList = response.data.holidayList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 휴일 종류 찾기
     * holidayType
     */
    async FETCH_HOLIDAYTYPE() {
      try {
        const response = await selectHolidayType()

        this.holidayTypeList = response.data.holidayTypeList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 휴일 저장
     * insertHoliday
     */
    async ADD_HOLIDAY(payload: any) {
      console.log(payload)
      try {
        const response = await addHoliday(payload)

        console.log(response)
      }
      catch (err) {
        alert('중복된 휴일을 입력하셨습니다.')
      }
    },

    /**
     * 휴일 수정
     * modifyHoliday
     */
    async MODIFY_HOLIDAY(payload: any) {
      console.log(payload)
      try {
        const response = await modifyHoliday(payload)

        console.log(response)
      }
      catch (err) {
        alert('중복된 휴일을 입력하셨습니다.')
      }
    },

    /**
     * 휴일 삭제
     * removeHoliday
     */
    async REMOVE_HOLIDAY(payload: any) {
      console.log(payload)
      try {
        const response = await removeHoliday(payload)

        console.log(response)
      }
      catch (err) {
        alert('중복된 휴일을 입력하셨습니다.')
      }
    },

    // /**
    //  * PA 공휴일 찾기
    //  * holiday
    //  * @param commit
    //  * @return {Promise<*>}
    //  * @constructor
    //  */
    // async FETCH_PA_HOLIDAY({commit},payload){
    //     console.log(payload)
    //     try{
    //         const response = await findPAHoliday(payload)
    //         console.log(response)
    //         commit('GET_PA_HOLIDAY', response.data)
    //     }catch(err){
    //         throw new Error(err)
    //     }
    // },
    /**
     * 법정 공휴일 저장
     * insertPublicHoliday
     */
    async ADD_PUBLIC_HOLIDAY(payload: any) {
      console.log(payload)
      try {
        const response = await addPublicHoliday(payload)

        console.log(response)
      }
      catch (err) {
        alert('오류입니다.')
      }
    },

    /**
     * 부서 저장
     * addDept
     */
    async ADD_DEPT(payload: any) {
      console.log(payload)
      try {
        const response = await addDept(payload)

        console.log(response.data)
        if (response.data.errorCode === 0) {
          // Swal.fire({
          //   title: 'info',
          //   text: '기준 급여 관리에서 기준 급여 및 호봉 인상률을 작성해주세요',
          //   icon: 'info',
          //   confirmButtonText: '확인',
          // })
        }
      }
      catch (err) {
        alert('중복된 부서를 입력하셨습니다.')
      }
    },

    /**
     * 부서 수정
     * modifyDept
     */
    async MODIFY_DEPT(payload: any) {
      console.log(payload)
      try {
        const response = await modifyDept(payload)

        console.log(response)
      }
      catch (err) {
        alert('오류입니다.')
      }
    },

    /**
     * 부서 삭제
     * removeDept
     */
    async REMOVE_DEPT(payload: any) {
      console.log(payload)
      try {
        const response = await removeDept(payload)

        console.log(response)
      }
      catch (err) {
        alert('오류입니다.')
      }
    },
    SET_SELECT_YEAR(payload: any) {
      try {
        console.log('year')
        this.selectYear = payload
        console.log(payload)

        return payload
      }
      catch (err) {
        alert('오류입니다.')
      }
    },
    SET_DIALOG(payload: any) {
      try {
        this.isDialog = payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    SET_DEPT_ROW(payload: any) {
      try {
        this.deptRow = payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    SET_HOLIDAY_ROW(payload: any) {
      try {
        this.holidayRow = payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async FETCH_PROJECT() {
      try {
        const response = await selectProjectList()

        this.projectList = response.data.projectList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async ADD_PROJECT(payload: any) {
      try {
        await addProject(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async MODIFY_PROJECT(payload: any) {
      try {
        await modifyProject(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async REMOVE_PROJECT(payload: any) {
      try {
        await removeProject(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    SET_PROJECT_ROW(payload: any) {
      try {
        this.project = payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    SET_DIALOG_BTN(payload: any) {
      try {
        this.isDialogBtn = payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
  },
})
