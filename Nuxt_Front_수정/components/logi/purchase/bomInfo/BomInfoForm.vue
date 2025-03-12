<script setup lang="ts">
import { ref } from 'vue'
import SearchBomDeployForm from './SearchBomDeployForm.vue'
import SearchBomInfoForm from './SearchBomInfoForm.vue'

const userTab = ref(null)

const tabs = [
  { icon: 'tabler-user-check', title: 'BOM 정전개/역전개' },
  { icon: 'tabler-lock', title: 'BOM 등록/수정' },
]
</script>
    
<!-- BOM 정전개/역전개, BOM 등록/수정 페이지 따로만들어서 한페이지에 넣음 -->
<template>
  <div class="text-2xl mb-2">
    <p> 자재명세서</p>
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
          <SearchBomDeployForm />
        </VWindowItem>

        <VWindowItem>
          <SearchBomInfoForm />
        </VWindowItem>
      </VWindow>
    </VCol>
  </VRow>
</template>