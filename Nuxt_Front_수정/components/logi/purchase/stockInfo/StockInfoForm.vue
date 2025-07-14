<script setup lang="ts">
import { ref } from 'vue';
import SearchStockListForm from './SearchStockListForm.vue';
import StockLogList from './StockLogListForm.vue';

const userTab = ref(null);

const tabs = [
  { icon: 'tabler-building-warehouse', title: '창고 입고' },
  { icon: 'tabler-vector-triangle', title: '재고 로그' },
]
</script>

<template>
<p class="text-2xl">
  재고관리
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
          <SearchStockListForm/>
        </VWindowItem>

        <VWindowItem>
          <StockLogList />
        </VWindowItem>

      </VWindow>
    </VCol>
  </VRow>

</template>