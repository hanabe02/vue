<script setup lang="ts">
import { onMounted, watchEffect, ref } from 'vue';
import { useRouter } from 'vue-router';
import { userStore } from '@/store/logi/user';

const store = userStore();  // ✅ store를 변수로 고정
const userInfo = ref<any>(null);
const router = useRouter();

onMounted(async () => {
  try {
    console.log("FETCH_USER_INFO 코드 실행");
    await store.FETCH_USER_INFO(); // ✅ 사용자 정보 가져오기

    // ✅ 2️⃣ 상태가 반영될 때까지 대기 (nextTick 사용)
    await nextTick();

    // ✅ userInfo가 업데이트될 때 자동 반영
    // watchEffect(() => {
    //   userInfo.value = store.userInfo;
    // });

    console.log("✅ 로그인 완료, 사용자 정보:", userInfo.name);
    router.push('/');
  } catch (error) {
    console.error('❌ 로그인 후 사용자 정보 가져오기 실패:', error);
    router.push('/login'); // 실패 시 로그인 페이지로 이동
  }
});
</script>

<template>
  <div>로그인 처리 중...</div>
</template>
