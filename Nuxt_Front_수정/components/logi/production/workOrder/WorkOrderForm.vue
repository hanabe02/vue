<script setup lang="ts">
import WorkOrderListForm from './WorkOrderListForm.vue';
import WorkOrderStatusForm from './WorkOrderStatusForm.vue';
import WorkPerformanceManagementForm from './WorkPerformanceManagementForm.vue';

const userTab = ref(null)

const tabs = [
  { icon: 'tabler-click', title: '작업지시' },
  { icon: 'tabler-file-text', title: '작업지시현황' },
  { icon: 'tabler-database-edit', title: '생산실적관리' }
]
</script>

<template>
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
          <WorkOrderListForm/>
        </VWindowItem>

        <VWindowItem>
          <WorkOrderStatusForm />
        </VWindowItem>

        <VWindowItem>
          <WorkPerformanceManagementForm/>
        </VWindowItem>

      </VWindow>
    </VCol>
  </VRow>

</template>