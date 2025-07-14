import { defineStore } from 'pinia'
import axios from 'axios'
import { hrApi } from '@/api';
import {
  addDayAttd,
  addRequestVacation, addRestAttd,
  batchDayAttdMgt,
  deleteDayAttdmgtApplyStatus,
  editDayAttd,
  editTravelAndEducationList,
  executeVacationCreate,
  getAnnualVactionMgtList,
  getDayAttdList,
  getEmpListByDept,
  getRestAttdListByAnnulVacation,
  getRestAttdListByEmpCodeAndYM,
  getTravelAndEducationList,
  removeDayAttd,
  removeRestAttd,
  removeTravelAndEducationList,
  selectDayAttdMgtListByStatus,
  selectDayAttdMgtListByStatusAndEmpCode,
  updateDayAttdmgtApplyStatus,
  updateVacationApplyStatus,
  runDailyAggregation,
  runMonthlyAggregation,
  fetchMonthAttendanceList,
  closeMonthAttendance,
} from '@/api/hr/attendance'

// export const useAttenStore = defineStore('attenStore', {
//   state: () => ({
//     dayAttdList: [],
//     empList: [],
//     restAttdList: [],
//     monthAttdList: [] as any[],
//   }),

//   actions: {
    
//   }
// });

export const attenStore = defineStore('attenStore', {
  state: () => ({
    isDialogBtn: false,
    dayAttdList: [],
    empList: [],
    annualVacationMgtList: [],
    restAttdList: [],
    travelAndEducationList: [],
    dayAttdMgtList: [],
    dayRegiselect: [],
    monthAttdList: [] as any[],
  }),
  actions: {
    SET_DIALOG_BTN(payload: any) {
      try {
        this.isDialogBtn = payload
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async SEARCH_DAY_ATTD_BY_PARMS(payload: any) {
      try {
        const res = await getDayAttdList(payload)

        console.log('[dayAttdList]', res.data.dayAttdList)
        this.dayAttdList = res.data.dayAttdList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async FETCH_EMPLIST_BY_DEPT(payload: any) {
      try {
        const response = await getEmpListByDept(payload)

        console.log('[empList]', response.data.empList)
        this.empList = response.data.empList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async INSERT_DAY_ATTD(payload: any) {
      try {
        const response = await addDayAttd(payload)
        if (response.data.errorCode === -1) {
          alert('저장에 실패 하였습니다.')
          console.log(response.data.errorMsg)
        }
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async DELETE_DAT_ATTD(payload: any) {
      try {
        const response = await removeDayAttd(payload)
        if (response.data.errorCode === -1) {
          alert('삭제에 실패 하였습니다.')
          console.log(response.data.errorMsg)
        }

        return response
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async UPDATE_DAYATTD(payload: any) {
      try {
        const response = await editDayAttd(payload)
        if (response.data.errorCode === -1) {
          alert('수정에 실패 하였습니다.')
          console.log(response.data.errorMsg)
        }

        return response
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async SEARCH_ANNUAL_VACTION_MGTLIST(payload: any) {
      try {
        const response = await getAnnualVactionMgtList(payload)

        this.annualVacationMgtList = response.data.annualVacationMgtList
        if (response.data.errorCode === -1) {
          alert('수정에 실패 하였습니다.')
          console.log(response.data.errorMsg)
        }

        return response.data.annualVacationMgtList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async FETCH_REST_ATTD_LIST_BY_EMP_CODE_AND_YM(payload: any) {
      try {
        const response = await getRestAttdListByEmpCodeAndYM(payload)

        this.restAttdList = response.data.restAttdList
        console.log(response)
        if (response.data.errorCode === -1) {
          alert(`근태 조회에 실패하였습니다.${response.data.errorMsg}`)
          console.log(response.data.errorMsg)
        }
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async FETCH_REST_ATTD_BY_ANNUAL_VACTION(payload: any) {
      try {
        const response = await getRestAttdListByAnnulVacation(payload)

        this.restAttdList = response.data.restAttdList
        console.log(response)
        if (response.data.errorCode === -1) {
          alert(`근태 조회에 실패하였습니다.${response.data.errorMsg}`)
          console.log(response.data.errorMsg)
        }
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    ADD_REST_ATTD(payload: any) {
      try {
        addRestAttd(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    DELTE_REST_ATTD(payload: any) {
      try {
        removeRestAttd(payload)
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async INSERT_REQUEST_REST_ATTD(payload: any) {
      try {
        console.log('여기는 액션입니다', payload)

        const response = await addRequestVacation(payload)
        if (response.data.errorCode === -1) {
          alert('수정에 실패 하였습니다.')
          console.log(response.data.errorMsg)
        }

        return response
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async SEARCH_TRAVEL_AND_EDUCATION_LIST(payload: any) {
      try {
        const response = await getTravelAndEducationList(payload)

        this.travelAndEducationList = response.data.travelAndEducationList
        console.log(response)
        if (response.data.errorCode === -1) {
          alert('수정에 실패 하였습니다.')
          console.log(response.data.errorMsg)
        }

        return response.data.travelAndEducationList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async UPDATE_TRAVEL_AND_EDUCATION_LIST(payload: any) {
      try {
        const response = await editTravelAndEducationList(payload)
        if (response.data.errorCode === -1) {
          alert('수정에 실패 하였습니다.')
          console.log(response.data.errorMsg)
        }

        return response
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async DELETE_TRAVEL_AND_EDUCATION_LIST(payload: any) {
      try {
        const response = await removeTravelAndEducationList(payload)
        if (response.data.errorCode === -1) {
          alert('수정에 실패 하였습니다.')
          console.log(response.data.errorMsg)
        }

        return response
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // 일근태 관리 목록 조회
    async SEARCH_DAYATTDMGT_BY_STATUS(payload: any) {
      try {
        const response = await selectDayAttdMgtListByStatus(payload)

        console.log('[dayAttdMgtList]', response.data.dayAttdMgtList)
        this.dayAttdMgtList = response.data.dayAttdMgtList
        if (response.data.errorCode === -1)
          console.log(response.data.errorMsg)

        return response.data.dayAttdMgtList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async SEARCH_DAYATTDMGT_BY_STATUS_BY_EMP_CODE(payload: any) {
      try {
        const response = await selectDayAttdMgtListByStatusAndEmpCode(payload)

        console.log('[dayAttdMgtList]', response.data.dayAttdMgtList)
        this.dayAttdMgtList = response.data.dayAttdMgtList
        if (response.data.errorCode === -1)
          console.log(response.data.errorMsg)

        return response.data.dayAttdMgtList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    async BATCH_DAY_ATTDMGT(payload: any) {
      try {
        const response = await batchDayAttdMgt(payload)

        console.log('[dayAttdMgtList]', response.data.dayAttdMgtList)
        this.dayAttdMgtList = response.data.dayAttdMgtList
        if (response.data.errorCode === -1)
          console.log(response.data.errorMsg)

        return response.data.dayAttdMgtList
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
    //일집계
    
    // 일집계
   async runDailyAggregation(applyDay: string) {
    try {
      console.log("runDailyAggregation api 접속")
      const response = await runDailyAggregation(applyDay); // 여기서 POST 한번만 호출
      console.log(response);
      if (response.data.errorCode === 0) {
        this.dayAttdMgtList = response.data.dayAttdMgtList; // 결과 저장
      } else {
        alert("집계 실패: " + response.data.errorMsg);
      }
    } catch (err: any) {
      console.error("집계 API 호출 실패", err);
      alert("집계 요청 중 오류 발생");
    }
  },

  // 월집계


  
    async aggregateMonth(applyYearMonth: string) {
      console.log("aggregateMonth 정상적으로 실행 됨");
      const { data } = await runMonthlyAggregation(applyYearMonth);
      console.log(data);
      if (data.errorCode === 0) {
        this.monthAttdList = data.monthAttdMgtList;
      } else {
        alert('집계 실패: ' + data.errorMsg);
      }
    },

    async loadMonthList(applyYearMonth: string) {
      const { data } = await fetchMonthAttendanceList(applyYearMonth);
      if (data.errorCode === 0) {
        this.monthAttdList = data.monthAttdMgtList;
      } else {
        alert('조회 실패: ' + data.errorMsg);
      }
    },

    async finalizeMonth() {
      const { data } = await closeMonthAttendance(this.monthAttdList);
      if (data.errorCode === 0) {
        alert('마감 성공!');
      } else {
        alert('마감 실패: ' + data.errorMsg);
      }
    },
   

    // 연차 마감 관리
    async BETCH_VACATION_CREATE(payload: any) {
      try {
        const response = executeVacationCreate(payload)

        console.log(response)

        return response
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // 연차 승인 관리
    async UPDATE_VACATION_APPLYSTATUS(payload: any) {
      try {
        const response = updateVacationApplyStatus(payload)

        console.log(response)

        return response
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    // 일근태 관리 - 근태 승인, 승인 취소
    async UPDATE_DAY_ATTDMGT_APPLYSTATUS(payload: any) {
      try {
        const response = await updateDayAttdmgtApplyStatus(payload)

        console.log(response.data)
        if (response.data.errorCode === -1) {
          alert('수정에 실패 하였습니다.')
          console.log(response.data.errorMsg)
        }

        return response
      }
      catch (err: any) {
        throw new Error(err)
      }
    },

    async DELETE_DAY_ATTDMGT_APPLYSTATUS(payload: any) {
      try {
        const response = await deleteDayAttdmgtApplyStatus(payload)

        console.log(response)
        if (response.data.errorCode === -1)
          console.log(response.data.errorMsg)

        return response
      }
      catch (err: any) {
        throw new Error(err)
      }
    },
  },
},
)
