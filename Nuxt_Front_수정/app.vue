<script setup lang="ts">
import { useTheme } from 'vuetify'
import ScrollToTop from '@core/components/ScrollToTop.vue'
import initCore from '@core/initCore'
import { initConfigStore, useConfigStore } from '@core/stores/config'
import { hexToRgb } from '@layouts/utils'

import { ref, onMounted } from 'vue' // ✅ Vue 함수 추가
import { userStore } from '@/store/logi/user';// ✅ UserStore 추가
// import { useDevice } from '@vueuse/core' // ✅ useDevice 추가

const { global } = useTheme()

// ℹ️ Sync current theme with initial loader theme
initCore()
initConfigStore()

const configStore = useConfigStore()
const { isMobile } = useDevice()
if (isMobile)
  configStore.appContentLayoutNav = 'vertical'

// ✅ 로그인 상태 유지 로직
const store = userStore();

onMounted(() => {
  console.log("✅ App.vue 실행됨 - 로그인 상태 확인 중...")

  const storedUser = localStorage.getItem("userInfo")
  console.log(storedUser);

  if (storedUser) {
    store.userInfo = JSON.parse(storedUser)
    console.log("✅ 기존 로그인 정보 유지:", store.userInfo)
  } else {
    console.log("🚨 기존 정보 없음, 서버에서 다시 불러오기")
    // store.FETCH_USER_INFO()
  }
})
</script>

<template>
  <VLocaleProvider :rtl="configStore.isAppRTL">
    <!-- ℹ️ This is required to set the background color of active nav link based on currently active global theme's primary -->
    <VApp :style="`--v-global-theme-primary: ${hexToRgb(global.current.value.colors.primary)}`">
      <NuxtLayout>
        <NuxtPage />
        <NuxtLoadingIndicator color="rgb(var(--v-theme-primary))" />
      </NuxtLayout>
      <ScrollToTop />
    </VApp>
  </VLocaleProvider>
</template>
