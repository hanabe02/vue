// import { canNavigate } from '@layouts/plugins/casl'

// export default defineNuxtRouteMiddleware(to => {
//   /*
//      * If it's a public route, continue navigation. This kind of pages are allowed to visited by login & non-login users. Basically, without any restrictions.
//      * Examples of public routes are, 404, under maintenance, etc.
//      */
//   if (to.meta.public)
//     return

//   const { status } = useAuth()
//   const isLoggedIn = status.value === 'authenticated'

//   /*
//       If user is logged in and is trying to access login like page, redirect to home
//       else allow visiting the page
//       (WARN: Don't allow executing further by return statement because next code will check for permissions)
//      */
//   if (to.meta.unauthenticatedOnly) {
//     if (isLoggedIn)
//       return navigateTo('/')
//     else
//       return undefined
//   }

//   if (!canNavigate(to)) {
//     /* eslint-disable indent */
//     return navigateTo(isLoggedIn
//       ? { name: 'not-authorized' }
//       : {
//           name: 'login',
//           query: {
//             ...to.query,
//             to: to.fullPath !== '/' ? to.path : undefined,
//           },
//         })
//     /* eslint-enable indent */
//   }
// })


import { canNavigate } from '@layouts/plugins/casl'

export default defineNuxtRouteMiddleware(to => {
  // ✅ 현재 로그인 상태 확인 (undefined 방지)
  const authToken = useCookie('authToken').value || false;
  const isLoggedIn = !!authToken; // `authToken`이 있으면 true, 없으면 false

  console.log("🔍 현재 로그인 상태:", isLoggedIn, "| 이동할 페이지:", to.name);

  // ✅ 1. 로그인된 상태에서 `/login` 페이지 접근 시 `/`로 이동 (불필요한 이동 방지)
  if (isLoggedIn && to.name === 'login') {
    console.log("✅ 로그인한 상태에서 로그인 페이지 접근 차단 → 홈으로 이동");
    return navigateTo('/');
  }

  // ✅ 2. `canNavigate(to)` 검사 (로그인 페이지에서는 검사 X)
  let canAccess = true;
  if (to.name !== 'login') {  // 로그인 페이지는 항상 접근 가능
    try {
      canAccess = canNavigate(to);
    } catch (error) {
      console.error("❌ canNavigate() 실행 중 오류 발생:", error);
      canAccess = false;
    }
  }

  console.log("✅ canNavigate 결과:", canAccess);

  // ✅ 3. `canNavigate()`가 false이면 권한 없음 → `/not-authorized` 또는 `/login`
  if (!canAccess) {
    console.log("❌ 접근 권한 없음:", to.name);

    // 🔥 **현재 페이지가 `login` 또는 `not-authorized`가 아닐 때만 `navigateTo()` 실행**
    if (to.name !== 'login' && to.name !== 'not-authorized') {
      return navigateTo(isLoggedIn ? { name: 'not-authorized' } : { name: 'login' });
    }
  }

  // ✅ 4. 모든 검사를 통과하면 그대로 진행 (무한 루프 방지)
  return;
});
