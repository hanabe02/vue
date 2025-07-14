import { defineStore } from 'pinia'
import {
  addAppointRegi,
  addEmp,
  addImgEmp,
  editApproveAppoint,
  editEmp,
  editPhotoEmp,
  findDeptEmpList,
  getAllDepartmentList,
  getAllEmpList,
  getAppList,
  getEmpDetail,
  getLogAppoint,
  getWorkPlaceList,
  removeAppoint,
  removeEmp,
} from '@/api/hr/affair'

export const affairStore = defineStore('affairStore', {
  state: () => ({
    all_emp_list: [],
    selected_emp_detail: [],
    all_dept_list: [],
    all_work_place_list: [],
    all_appointments_list: [],
    all_appointments_log: [],
    selected_dept_Emp_List: [],
  }),
  actions: {
  /**
   * 직원전체조회 O
   */
    async FETCH_ALL_EMP() { // 객체로 넘어오면 {}
      try {
        const response = await getAllEmpList()

        this.all_emp_list = response.data.empList

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 부서전체조회 O
     */

    async FETCH_ALL_DEPARTMENT() { // 객체로 넘어오면 {}
      try {
        const response = await getAllDepartmentList()

        this.all_dept_list = response.data.deptlist

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 근무지 조회
     */

    async FETCH_WORKPLACE(COM: any) { // 객체로 넘어오면 {}
      try {
        console.log('근무지 조회', COM)

        const response = await getWorkPlaceList(COM)

        console.log('근무지 조회', response)
        this.all_work_place_list = response.data.gridRowJson

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // 겹침
    async FETCH_EMP_DETAIL(clickEmp: any) {
      try {
        console.log(clickEmp)

        const response = await getEmpDetail(clickEmp)

        console.log('직원 상세 조회', response.data.empDetailInfo)
        this.selected_emp_detail = response.data.empDetailInfo[0]

        return Promise.resolve(response)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async FETCH_EMP_LIST_BY_DEPT(clickEmp: any) {
      try {
        console.log(clickEmp)

        const response = await getEmpDetail(clickEmp)

        console.log('직원 상세 조회', response.data.empDetailInfo)
        this.selected_emp_detail = response.data.empDetailInfo

        return Promise.resolve(response)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 직원 정보 수정
     */
    async UPDATE_EMP_INFO(empUpdate: any) { // 객체로 넘어오면 {}
      console.log(empUpdate)
      try {
        const response = await editEmp(empUpdate)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 직원 사진 수정
     */
    async UPDATE_PHOTO_FILE(empPhoto: any) { // 객체로 넘어오면 {}
      console.log(empPhoto)
      try {
        const response = await editPhotoEmp(empPhoto)

        console.log(response)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 직원 사진 등록
     */
    async INSERT_IMG(empPhoto: any) { // 객체로 넘어오면 {}
      console.log(empPhoto)
      try {
        const response = await addImgEmp(empPhoto)

        console.log(response)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 직원 등록
     */
    async INSERT_EMP(empregi: any) { // 객체로 넘어오면 {}
      console.log(empregi)
      try {
        const response = await addEmp(empregi)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 직원 삭제
     */
    async DELETE_SELECTED_EMP(emp: any) { // 객체로 넘어오면 {}
      console.log(emp)
      try {
        const response = await removeEmp(emp)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 발령 등록
     */
    async INSERT_APPOINT_EMP(empApp: any) { // 객체로 넘어오면 {}
      console.log('발령 정보 액션', empApp)
      try {
        const response = await addAppointRegi(empApp)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 발령 신청 내역 조회
     */
    async FETCH_APPOINT_LIST() {
      try {
        const response = await getAppList()

        console.log('발령 내역 조회 회신', response)
        this.all_appointments_list = response.data.appoint

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 발령 승인 반려
     */
    async UPDATE_APPOINT_APPROVE_EMP(approveAppoint: any) {
      try {
        console.log('발령 내역 조회 액션~~~~~~', approveAppoint)

        const response = await editApproveAppoint(approveAppoint)

        console.log('발령 내역 조회 회신', response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 발령 삭제
     */
    async DELETE_APPOINT_EMP(approveAppoint: any) {
      try {
        console.log('발령 삭제 액션~~~~~~', approveAppoint)

        const response = await removeAppoint(approveAppoint)

        console.log('발령 삭제 회신', response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 발령 승인 기록 조회
     */
    async FETCH_APPOINT_LOG() {
      try {
        const response = await getLogAppoint()

        console.log('발령 삭제 회신', response)
        this.all_appointments_log = response.data.appoint

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async FETCH_DEPT_EMP_LIST(payload: any) {
      try {
        const response = await findDeptEmpList(payload)

        this.selected_dept_Emp_List = response.data.deptEmpList
        console.log(response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

  },
})
