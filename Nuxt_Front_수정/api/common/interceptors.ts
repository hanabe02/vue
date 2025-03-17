export default function setInterceptors(instance: any) {
  instance.interceptors.request.use(
    (config: any) => config,
    (error: any) => Promise.reject(error)
  );

  instance.interceptors.response.use(
    (response: any) => response,
    (error: any) => Promise.reject(error)
  );

  return instance; // 추가필요
}
