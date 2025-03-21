<script setup lang="ts">
import MrpProductionPlan from "./MrpProductionPlan.vue";
import MrpPurchasePlan from "./MrpPurchasePlan.vue";

const headers = ref([
  { title: "주생산계획번호", key: "mpsNo" },
  { title: "BOM번호", key: "bomNo" },
  { title: "품목구분", key: "itemClassification" },
  { title: "품목코드", key: "itemCode" },
  { title: "품목명", key: "itemName" },
  { title: "발주/작업지시 기한", key: "orderDate" },
  { title: "발주/작업지시 완료기한", key: "requiredDate" },
  { title: "계획수량", key: "planAmount" },
  { title: "누적손실율", key: "totalLossRate" },
  { title: "계산수량", key: "caculatedAmount" },
  { title: "필요수량", key: "requiredAmount" },
  { title: "단위", key: "unitOfMrp" },
]);

const currentTab = ref("구매");
watch(currentTab, () => {
  console.log(currentTab.value);
});
</script>

<template>
  <div class="main_wrap">
    <div class="header">
      <div class="tab_wrap">
        <h2>조달 구분</h2>
        <div class="gap"></div>
        <VTabs v-model="currentTab">
          <VTab value="구매">구매</VTab>
          <VTab value="생산">생산</VTab>
        </VTabs>
      </div>
    </div>
    <div class="gap_2"></div>

    <MrpPurchasePlan v-if="currentTab === '구매'" />
    <MrpProductionPlan v-if="currentTab === '생산'" />
  </div>
</template>

<style scoped>
.main_wrap {
  /* border: 1px solid orange; */
}

.tab_wrap {
  position: relative;
  display: inline-block;
  text-align: center;
}

.header {
  position: relative;
  /* border: 1px solid red; */
}

.gap {
  height: 15px;
}

.gap_2 {
  height: 50px;
}
</style>
