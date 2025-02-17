<script setup lang="ts">
import Order1 from './order1.vue';
import Order from './order.vue';



const userTab = ref(null)

const tabs = [
  { icon: 'tabler-user-check', title: '외주발주 등록' },
  { icon: 'tabler-lock', title: '외주발주조회' },
]

</script>

<template>
  <VRow >
    <VCol
    >

    </VCol>

    <VCol

    >
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
          <Order1/>
        </VWindowItem>

        <VWindowItem>
          <Order />
        </VWindowItem>

      </VWindow>
    </VCol>
  </VRow>

</template>
