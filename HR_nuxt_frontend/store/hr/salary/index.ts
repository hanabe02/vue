import {
deleteBaseExtSal, deleteBaseSalary, deleteBonus, deleteMonthSalay,
deleteSeverance, findBonus, findSalary,
findSeverancePay, findTax, modifyBaseExtSalary,
modifyBaseSalary, registerBaseExtSal, registerBaseSalary, registerBonus, registerSeverancePay,
registerSocialInsure,
salaryProcess, selectBaseExtSalary, selectBaseSalary,
selectSalaryInit, selectSocialInsure, updateMonthSal,
} from '@/api/hr/salary'
import { defineStore } from 'pinia'

export const salaryStore = defineStore('salaryStore', {
  state: () => ({
    baseSalaryList: [],
    baseInsureList: [{
      attributionYear: '',
      empinsureRates: '',
      healthinsureRates: '', // 건강보험
      industinsureRates: '', // 산재보험
      industinsurecharRates: '', // 출퇴근
      jobstabilRates: '', // 고용안정
      longtermcareRate: '', // 장기요양보험
      nationpensionRates: '', // 국민연금
      teachpenisionRates: '', //
      vocacompetencyRates: '',
      wrkinsureRates: '', // 실업급여
    }],
    baseExtSalList: [],
    initSalaryList: [],
    salaryList: [],
    monthDeductionList: [],
    bonusList: [],
    severanceList: [],
    isDialogVisible: false,
    isDialogBtn: false,
    selectedrow: '',
    rowList: [],
    deptList: [],
    posList: [],
  }),

  actions: {
  // -------------------------------기준급여-------------------------------
    /**
     * 기준 급여 조회
     * @param commit
     */
    async FETCH_BASE_SALARY(payload: any) {
      try {
        const response = await selectBaseSalary(payload)

        this.baseSalaryList = response.data.baseSalaryList

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 기준 급여 수정
     */
    async EDIT_BASE_SALARY(payload: any) { // 객체로 넘어오면 {}
      try {
        console.log(payload)

        const response = await modifyBaseSalary(payload)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 기준 급여 등록
     */
    async ADD_BASE_SALARY(payload: any) {
      try {
        const response = await registerBaseSalary(payload)
        if (response.data.errorCode !== -1)
          return response.data.errorCode

        else
          alert('등록에 실패하였습니다.')
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 기준 급여 삭제
     */
    async DELETE_BASE_SALARY(payload: any) {
      try {
        return await deleteBaseSalary(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // -------------------------------사회보험-------------------------------
    /**
     * 보험률 조회
     */
    async SEARCH_SOCIAL_INSURE(payload: any) {
      try {
        const response = await selectSocialInsure(payload)

        this.baseInsureList = response.data.baseInsureList[0]
        console.log(response)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 사회 보험 등록/수정
     * @param {}
     * @param payload
     * @returns {Promise<*>}
     * @constructor
     */
    async ADD_SOCIAL_INSURE(payload: any) {
      try {
        console.log(payload)

        // 진승우 # 원래 코드 이거였음 => const response = await registerSocialInsure(payload)
        await registerSocialInsure(payload)

        // if(response.data.errorCode !== -1){
        //   return response.data.errorCode
        // }else{
        //   alert("등록에 실패하였습니다.")
        // }
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // -------------------------------초과수당-------------------------------
    /**
     * 초과 수당 조회
     */
    async SEARCH_BASE_EXT_SAL() {
      try {
        const response = await selectBaseExtSalary()

        this.baseExtSalList = response.data.baseExtSalList

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 초과 수당 수정
     */
    async EDIT_BASE_EXT_SAL(payload: any) { // 객체로 넘어오면 {}
      try {
        console.log(payload)

        const response = await modifyBaseExtSalary(payload)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 초과 수당 등록
     */
    async ADD_BASE_EXT_SAL(payload: any) {
      try {
        const response = await registerBaseExtSal(payload)
        if (response.data.errorCode !== -1)
          return response.data.errorCode

        // else {
        //   Swal.fire({
        //     title: 'Error!',
        //     text: '데이터가 존재하지 않습니다',
        //     icon: 'error',
        //     confirmButtonText: '확인',
        //   })
        // }
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 초과 수당 삭제
     */
    async DELETE_BASE_EXT_SAL(payload: any) {
      try {
        return await deleteBaseExtSal(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // -------------------------------월급여 관리-------------------------------
    /**
     * 월 급여 초기 조회
     */
    async SEARCH_SALARY_INIT(payload: any) {
      try {
        const response = await selectSalaryInit(payload)

        this.initSalaryList = response.data.initSalaryList

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 월 급여 생성 및 조회
     */
    async SALARY_PROCESS(payload: any) {
  try {
    console.log(payload )

    const response = await salaryProcess(payload)

    // 🔐 방어코드 추가
    const list = response.data.salaryList
      console.log("[📥SALARY_PROCESS 응답 salaryList]", list)
      
    if (!Array.isArray(list)) {
      console.warn("salaryList가 undefined이거나 배열이 아님", list)
      this.salaryList = []
      return
    }
      
    const responseData = list.find(item => item && item.empCode === payload.empCode)
    this.salaryList = responseData ?? {}
    return response.data
  } catch (err: any) {
    console.error("SALARY_PROCESS 실패:", err)
    throw new Error(err)
  }
},

    /**
     * 월 급여 승인 수정
     */
    async MONTH_SAL_FINAL(payload: any) { // 객체로 넘어오면 {}
      try {
        const response = await updateMonthSal(payload)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 월 급여 삭제
     */
    async DELETE_MONTH_SALARY(payload: any) {
      try {
        return await deleteMonthSalay(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 소득세 조회
     */
    async FIND_TAX(payload: any) {
      try {
        const response = await findTax(payload)

        this.monthDeductionList = response.data.monthDeductionList
        console.log('이야아', response.data.monthDeductionList)

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

//승인 처리 후 해당 사원의 급여 데이터 재조회
async FETCH_SALARY_DETAIL(payload: any) {
  try {
    const response = await findSalary(payload)
  console.log('✅ salaryList.value:', salaryList.value)
    const detail = response.data.salaryList.find((item: any) => item.empCode === payload.empCode)
    this.salaryList = detail ?? {}
    return detail.data
  } catch (err: any) {
    console.log('✅ salaryList.value1:', salaryList.value)
    console.log('Fetch_SALARY_DETAIL 실패: ' , err)
    throw new Error(err)
  }
},

    // -------------------------------월급여 조회-------------------------------
    /**
     * 월 급여 조회
     */
  async SEARCH_SALARY(payload: any) {
      try {
        const response = await findSalary(payload)

        console.log(response)
        this.salaryList = response.data.salaryList

        // if (response.data.salaryList.length === 0) {
        //   Swal.fire({
        //     title: 'Error!',
        //     text: '데이터가 존재하지 않습니다',
        //     icon: 'error',
        //     confirmButtonText: '확인',
        //   })
        // }

        return response.data
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // -------------------------------상여 및 성과급-------------------------------
    /**
     * 상여 및 성과급 조회
     */
    async FIND_BONUS(payload: any) {
      try {
        const response = await findBonus(payload)

        this.bonusList = response.data.bonusList

        // if (response.data.bonusList == '') {
        //   Swal.fire({
        //     title: 'Error!',
        //     text: '데이터가 존재하지 않습니다',
        //     icon: 'error',
        //     confirmButtonText: '확인',
        //   })
        // }
        // else {
        //   return response.data
        // }
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 성과급 등록
     */
    async ADD_BONUS(payload: any) {
      try {
        const response = await registerBonus(payload)
        if (response.data.errorCode !== -1)
          return response.data.errorCode

        else
          alert('등록에 실패하였습니다.')
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 성과급 삭제
     */
    async DELETE_BONUS(payload: any) {
      try {
        return await deleteBonus(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // -------------------------------퇴직금-------------------------------
    /**
     * 퇴직금 조회
     */
    async FIND_SEVERANCE_PAY(payload: any) {
      try {
        const response = await findSeverancePay(payload)

        console.log(response.data.severanceList)
        this.severanceList = response.data.severanceList

        // if (response.data.severanceList == '') {
        //   Swal.fire({
        //     title: 'Error!',
        //     text: '데이터가 존재하지 않습니다',
        //     icon: 'error',
        //     confirmButtonText: '확인',
        //   })
        // }
        // else {
        //   return response.data
        // }
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 퇴직금 등록 및 조회
     */
    async ADD_SEVERANCE_PAY(payload: any) {
      try {
        const response = await registerSeverancePay(payload)

        console.log(response.data.severanceList)
        this.severanceList = response.data.severanceList

        // if (response.data.errorCode === '-1' || response.data.errorCode === -2) {
        //   Swal.fire({
        //     title: 'Error!',
        //     text: response.data.errorMsg,
        //     icon: 'error',
        //     confirmButtonText: '확인',
        //   })
        // }
        // else {
        //   return response.data
        // }
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    /**
     * 퇴직금 삭제
     */
    async DELETE_SEVERANCE(payload: any) {
      try {
        return await deleteSeverance(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    SET_DIALOG(payload: any) {
      try {
        this.isDialogVisible = payload
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

    SET_ROW_LIST(payload: any) {
      try {
        this.rowList = payload

        return payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    SET_SELECTED_ROW(payload: any) {
      try {
        this.selectedrow = payload

        return payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    SET_POS_LIST(payload: any) {
      try {
        this.posList = payload

        return payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    SET_DEPT_LIST(payload: any) {
      try {
        this.deptList = payload

        return payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
  },
})
