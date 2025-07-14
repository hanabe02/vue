import axios from 'axios'
import setInterceptors from './common/Interceptors'

function createInstanceWithAuth(url: any) {
  const instance = axios.create({
    // baseURL: `${process.env.NUXT_PUBLIC_API_BASE_URL}${url}`,
    baseURL: `http://localhost:8282${url}`,
  })

  return setInterceptors(instance)
}

export const hrApi = createInstanceWithAuth('/hr')
export const sys = createInstanceWithAuth('/sys')
