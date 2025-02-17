<script setup lang="ts">
import { ref } from 'vue'
import ClientGeneralInfo from '../clientInfo/ClientGeneralInfo.vue'
import ClientFinancialInfo from '../clientInfo/ClientFinancialInfo.vue'

const userTab = ref(null)

const tabs = [
  { icon: 'tabler-user-check', title: '일반 거래처' },
  { icon: 'tabler-lock', title: '금융 거래처' },
]
</script>

<!-- 품목관리, 품목군 관리 페이지 따로만들어서 한페이지에 넣음 -->
<template>
  <div class="text-2xl mb-2">
    <p> 거래처 정보</p>
  </div>
  <VRow>
    <VCol>
      <VTabs
        v-model="userTab"
        class="v-tabs-pill"
      >
        <VTab
          v-for="tab in tabs"
          :key="tab.icon"
        >
          <VIcon
            :size="18"
            :icon="tab.icon"
            class="me-1"
          />
          <span>{{ tab.title }}</span>
        </VTab>
      </VTabs>

      <VWindow
        v-model="userTab"
        class="mt-6 disable-tab-transition"
        :touch="false"
      >
        <VWindowItem>
          <ClientGeneralInfo />
        </VWindowItem>

        <VWindowItem>
          <ClientFinancialInfo />
        </VWindowItem>
      </VWindow>
    </VCol>
  </VRow>
</template>
