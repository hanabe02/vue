import axios from 'axios'
import setInterceptors from '@/api/common/interceptors'

// 로그인시 인증 토큰 필요없는 기본 axios
// 기본
function createInstance() {
  return axios.create({
    baseURL: 'http://localhost:8282',
  })
}

// 각각의 모듈에따라 정리한  axios
function createInstanceWithAuth(url: any) {
  const instance = axios.create({
    baseURL: `http://localhost:8282${url}`,
  })

  return setInterceptors(instance) // 인터셉터가 셋업된 인스턴스
}

export const logiApi = createInstanceWithAuth('/logi')
