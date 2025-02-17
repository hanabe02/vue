<script setup lang="ts">
import OrderRegistForm from './OrderRegistForm.vue';
import ShowOrderInfoForm from './ShowOrderInfoForm.vue';

const userTab = ref(null)

const tabs = [
  { icon: 'tabler-truck-delivery', title: '발주' },
  { icon: 'tabler-zoom-in', title: '발주현황' },
]
</script>

<template>
<p class="text-2xl">
  발주관리
</p>

  <VRow >
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
          <OrderRegistForm/>
        </VWindowItem>

        <VWindowItem>
          <ShowOrderInfoForm />
        </VWindowItem>

      </VWindow>
    </VCol>
  </VRow>

</template>