// url적는곳
import { logiApi } from '@/api'

const ABROAD_SCHEDULE_URL = '/abroad/abroadScheduleList'
const ADD_ABROAD_SCHEDULE_URL = '/abroad/addAbroadSchedule'
const UPDATE_ABROAD_SCHEDULE_URL = '/abroad/updateAbroadSchedule'
const DELETE_ABROAD_SCHEDULE_URL = '/abroad/abroadScheduleCode'

// 행사 조회
function getAbroadScheduleList() {
  return logiApi.get(`${ABROAD_SCHEDULE_URL}`)
}

// 행사 추가
function addAbroadSchedule(menudata: any) {
  return logiApi.post(`${ADD_ABROAD_SCHEDULE_URL}`, menudata)
}

// 행사 수정
function updateAbroadSchedule(updateDataList: any) {
  return logiApi.put(`${UPDATE_ABROAD_SCHEDULE_URL}`, updateDataList)
}

// 행사 삭제
function deleteAbroadSchedule(abroadScheduleCode: string) {
  return logiApi.delete(`${DELETE_ABROAD_SCHEDULE_URL}`, {
    params: {
      abroadScheduleCode,
    },
  })
}


export {
  getAbroadScheduleList,
  addAbroadSchedule,
  updateAbroadSchedule,
  deleteAbroadSchedule,
}
