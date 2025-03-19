<script setup lang="ts">
import { userStore } from '@/store/logi/user'
import { ref, watch } from 'vue'


const userInfo = ref<any>(null);


// ✅ 페이지 로드될 때 세션에서 사용자 정보 불러오기
onMounted(async () => {
  console.log("✅ userProfile.vue 실행됨 - 로그인 상태 확인 중...");
  
  const storedUser = localStorage.getItem("userInfo");
  if (storedUser) {
    userInfo.value = JSON.parse(storedUser);
    console.log("✅ 기존 로그인 정보 유지:", userInfo.value);
  } else {
    console.log("🚨 기존 정보 없음, 서버에서 다시 불러오기");
    userInfo.FETCH_USER_INFO(); // ✅ 필요 시 서버에서 데이터 가져오기
  }
});

// ✅ Pinia에서 가져온 데이터를 `watch`를 통해 반영
watchEffect(() => {
  if (userInfo.userInfo) {
    userInfo.value = userInfo.userInfo;
    console.log("✅ watchEffect - userInfo 업데이트됨:", userInfo.value);
  }
});

// const login = () => {
//   console.log("버튼 클릭 됨");

//   userInfo.value = userStore().userInfo // ✅ `userStore()`가 아니라 `userStore` 사용
//   console.log("✅ userStore.getUserInfo:", userStore().userInfo);
//   console.log("버튼 클릭 됨");
// }

const logout = async () => {
  console.log("로그아웃 실행 됨");
  // 1️⃣ 로컬 스토리지에서 userInfo 삭제
  localStorage.removeItem("userInfo");

  await fetch("http://localhost:8282/auth/logout",{
    method: "POST",
    credentials: "include",
  });

  window.location.href = "https://accounts.google.com/logout";

  // ✅ 4️⃣ 로그아웃 후 로그인 페이지로 이동
  setTimeout(() => {
    window.location.href = "/login";
  }, 1000);
}


</script>
<template>
  <p class="text-2xl">
    회원 정보
  </p>
  <div class="demo-space-x">
    <div v-if="userInfo">
      <img :src="userInfo.picture" alt="사용자 이미지" class="user-image"/>
      <p class="user-name">사용자 이름: {{ userInfo.name }}</p>
      <p class="user-email">이메일: {{ userInfo.email }}</p>
      
      <div class="account-container">
        <button class="user-gmail">Google 계정 관리</button>
        <div class="button-group">
          <button class="user-add">
            <span class="icon">➕</span> 계정 추가
          </button>
          <button class="user-logout" @click="logout">
            <span class="icon">↩</span> 로그아웃
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.text-2xl{
  padding: 20px;
}

.account-container {
  text-align: center;
  padding-left: 20px;
  background-color: #f8f9fa;
  border-radius: 12px;
  width: 300px;
  margin: auto;
}
.user-image {
  width: 100px; /* 이미지 크기 설정 */
  height: 100px;
  margin: 0 20px;
  border-radius: 50%; /* 원형으로 만들기 */
  object-fit: cover; /* 이미지 비율 맞추기 */
  margin-bottom: 15px; /* 이미지와 텍스트 사이에 간격을 추가 */
}

.user-name {
  margin: 0 20px;
  font-size: 16px; /* 글자 크기 */
  font-weight: bold; /* 글자 두껍게 */
  margin-top: 10px; /* 텍스트 위쪽에 간격 추가 */
  margin-bottom: 10px;
}

.user-email{
  margin: 0 20px;
  margin-bottom: 10px;
}

.user-gmail {
  background-color: transparent;
  border: 1px solid #888;
  color: #1a73e8;
  padding: 10px 20px;
  border-radius: 20px;
  cursor: pointer;
  font-weight: bold;
}

.button-group {
  display: flex;
  justify-content: center;
  gap: 2px;
  margin-top: 10px;
}

button {
  flex: 1;
  padding: 12px;
  border: none;
  background-color: white;
  border-radius: 10px;
  cursor: pointer;
  font-size: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  font-weight: bold;
}

.user-add {
  border: 1px solid #ddd;
}

.user-logout {
  border: 1px solid #ddd;
}

button:hover {
  background-color: #e8eaed;
}
</style>