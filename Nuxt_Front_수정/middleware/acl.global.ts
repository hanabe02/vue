// // import { canNavigate } from '@layouts/plugins/casl'

// export default defineNuxtRouteMiddleware((to) => {
//   console.log("🔍 라우트 미들웨어 실행 중...");

//   const router = useRouter();
//   const isLoggedIn = ref(false);

//   // ✅ 1️⃣ localStorage에서 로그인 상태 확인 (`process.client` 체크 필수)
//   if (process.client) {
//     const storedUser = localStorage.getItem("userInfo");
//     isLoggedIn.value = !!storedUser;
//   }

//   console.log("🔍 현재 로그인 상태:", isLoggedIn, "| 이동할 페이지:", to.name);

//   console.log("🔍 현재 로그인 상태:", isLoggedIn.value, "| 이동할 페이지:", to.name);

//   // ✅ 2️⃣ `watchEffect()`를 사용하여 로그인 상태 변경 감지 후 이동
//   watchEffect(async () => {
//     await nextTick(); // ✅ 상태가 반영된 후 실행

//     if (!isLoggedIn.value && to.name !== 'login' && process.client) {
//       console.log("🚨 로그인 필요! `/login`으로 이동");
//       router.push('/login');
//     }

//     if (isLoggedIn.value && to.name === 'login' && process.client) {
//       console.log("✅ 로그인한 상태에서 `/login` 접근 차단 → 홈으로 이동");
//       router.push('/');
//     }
//   });

//   return;
// });

export default defineNuxtRouteMiddleware((to) => {
  console.log("🔍 라우트 미들웨어 실행 중...");

  const router = useRouter();
  let storedUser = null;
  if (process.client) {
    storedUser = localStorage.getItem("userInfo");
  }
  const isLoggedIn = !!storedUser;

  console.log("🔍 현재 로그인 상태:", isLoggedIn, "| 이동할 페이지:", to.name);

  // ✅ `auth-callback`은 로그인 체크 없이 그냥 실행되도록 예외 처리
  if (to.name === 'auth-callback') {
    console.log("✅ `auth-callback` 접근 허용됨");
    return;
  }

  if (!isLoggedIn && to.name !== 'login' && process.client) {
    console.log("🚨 로그인 필요! `/login`으로 이동");
    return router.push('/login');
  }

  return;
});