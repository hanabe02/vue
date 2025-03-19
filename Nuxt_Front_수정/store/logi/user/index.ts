import {defineStore} from 'pinia'

export const userStore = defineStore('userStore', {
    state: () => ({
        userInfo: [] as any
    }),
    actions: {
        async GET_USER_INFO(){
            try{
                window.location.href = "http://localhost:8282/oauth2/authorization/google";
            }catch (error){
                console.error('Error fetching data:', error)
            }
        },
        async FETCH_USER_INFO() {
            try {
                console.log("✅ FETCH_USER_INFO() 실행 시작");
                const response = await fetch("http://localhost:8282/auth/user", {
                    method: "GET",
                    credentials: "include", // ✅ 세션 쿠키 포함
                    mode: "cors",
                });

                const userData = await response.json();
                if (!response.ok) throw new Error("사용자 정보를 가져올 수 없음");

                console.log("✅ 로그인된 사용자:", userData);

                // ✅ 사용자 정보를 localStorage에도 저장 (새 창에서도 유지되도록)
                localStorage.setItem("userInfo", JSON.stringify(userData));

                // ✅ 디버깅: 데이터 확인
                console.log("🔥 기존 상태 값:", this.userInfo);
                console.log("🔥 새로운 데이터 값:", userData);

                // ✅ 반응형 업데이트
                this.userInfo = { ...userData };

                await nextTick();

                // ✅ 사용자 정보를 Pinia Store에 저장
                console.log("✅ 최종 상태 값:", this.userInfo);
            } catch (error) {
                console.error("❌ 사용자 정보 가져오기 실패:", error);
            }
        }
    }
})

