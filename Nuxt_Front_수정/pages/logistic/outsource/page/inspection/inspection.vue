<script setup lang="ts">
import InspectionResult from './inspectionResult.vue';
import registReturn from './registReturn.vue';

const userTab = ref(null)

const tabs = [
  { icon: 'tabler-truck-delivery', title: '외주자재 조회/검사' },
  { icon: 'tabler-zoom-in', title: '불량품목 반품등록' },
]
</script>

<template>
<p class="text-2xl">
  외주발주검사
</p>

  <VRow>
    <VCol class="mt-5">
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
          <InspectionResult/>
        </VWindowItem>

        <VWindowItem>
          <registReturn/>
        </VWindowItem>

      </VWindow>
    </VCol>
  </VRow>

</template>