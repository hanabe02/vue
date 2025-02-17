<script setup lang="ts">
import { ref } from 'vue'
import ItemInfo from '../itemInfo/ItemInfo.vue'
import ItemDetailInfo from './ItemDetailInfo.vue'

const userTab = ref(null)

const tabs = [
  { icon: 'tabler-user-check', title: '품목 관리' },
  { icon: 'tabler-lock', title: '품목군 관리' },
]
</script>

<!-- 품목관리, 품목군 관리 페이지 따로만들어서 한페이지에 넣음 -->
<template>
  <div class="text-2xl mb-2">
    <p> 품목정보</p>
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
          <ItemInfo />
        </VWindowItem>

        <VWindowItem>
          <ItemDetailInfo />
        </VWindowItem>
      </VWindow>
    </VCol>
  </VRow>
</template>
