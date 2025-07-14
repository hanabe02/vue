<script setup lang="ts">
import { useTheme } from 'vuetify'
import ScrollToTop from '@core/components/ScrollToTop.vue'
import initCore from '@core/initCore'
import { initConfigStore, useConfigStore } from '@core/stores/config'
import { hexToRgb } from '@layouts/utils'
import { watch, ref, provide } from 'vue'

const { global } = useTheme()

// ℹ️ Sync current theme with initial loader theme
initCore()
initConfigStore()

// const empCode = ref("EMP-01"); // 예시로 "EMP-01"을 설정
// provide('empCode', empCode);
// 🔹 empCode 전역 상태 생성
const empCode = ref("")
provide('empCode', empCode) // 전역 제공

watch(empCode, (newVal) => {
  console.log('empCode 변경됨:', newVal) // 값이 변경될 때마다 출력
})
const configStore = useConfigStore()
const { isMobile } = useDevice()
if (isMobile)
  configStore.appContentLayoutNav = 'vertical'
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
