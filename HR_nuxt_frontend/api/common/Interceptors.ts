import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'

export default function setInterceptors(instance: AxiosInstance): AxiosInstance {
  instance.interceptors.request.use(
    (config: AxiosRequestConfig) => config,
    error => Promise.reject(error),
  )

  instance.interceptors.response.use(
    (response: AxiosResponse) => response,
    error => Promise.reject(error),
  )

  return instance
}
