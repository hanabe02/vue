<script setup lang="ts">
import MrpRegister from "./MrpRegister.vue";
import MrpGather from "./MrpGather.vue";
const currentTab = ref("MRP 등록");

// watch(currentTab, () => {
//   console.log("current tab is : ", currentTab.value);
// });
</script>

<template>
  <div class="gap"></div>

  <div class="main_wrap">
    <div class="tab_wrap">
      <VTabs v-model="currentTab">
        <VTab value="MRP 등록">MRP 등록</VTab>
        <VTab value="품목별 조달계획">품목별 조달계획</VTab>
      </VTabs>
    </div>
    <div class="gap"></div>
    <MrpRegister v-if="currentTab === 'MRP 등록'" />
    <MrpGather v-if="currentTab === '품목별 조달계획'" />
  </div>
</template>

<style scoped>
.main_wrap {
  /* border: 1px solid red; */
}

.tab_wrap {
  /* border: 1px solid orange; */
  height: 50px;
  width: auto;
}

.main {
  margin: auto;
  text-align: center;
}

.gap {
  height: 50px;
}
</style>
