<script setup lang="ts">
import { userStore } from '@/store/logi/user'
import { ref, watch } from 'vue'


const userInfo = ref<any>(null);

// ✅ Pinia에서 가져온 데이터를 `watch`를 통해 반영
watch(() => userStore().userInfo, (newValue) => {
  if (newValue) {
    userInfo.value = newValue;
    console.log("✅ userStore.getUserInfo 업데이트됨:", newValue);
  }
});

const login = () => {
  console.log("버튼 클릭 됨");

  userInfo.value = userStore().userInfo // ✅ `userStore()`가 아니라 `userStore` 사용
  console.log("✅ userStore.getUserInfo:", userStore().userInfo);
  console.log("버튼 클릭 됨");
}
</script>


<template>
  <div class="demo-space-x">
    <button @click="login">버튼</button>
  </div>
  <div v-if="getUserInfo">
      <p>사용자 이름: {{ getUserInfo.name }}</p>
      <p>이메일: {{ getUserInfo.email }}</p>
    </div>
</template>

<style>

</style>