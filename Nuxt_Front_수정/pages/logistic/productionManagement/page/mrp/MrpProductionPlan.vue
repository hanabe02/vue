<script setup lang="ts">
import { VDataTable } from "vuetify/labs/VDataTable";
import MrpGatherResultModal from "./MrpGatherResultModal.vue";
import axios from "axios";

const item = ref([]);
const mpsNoList = ref([]);

const headers = ref([
  { title: "MRP번호", key: "mrpNo" },
  { title: "품목분류", key: "itemClassification" },
  { title: "품목코드", key: "itemCode" },
  { title: "품목명", key: "itemName" },
  { title: "요청일자", key: "orderDate" },
  { title: "소요일자", key: "requiredDate" },
  { title: "필요수량", key: "requiredAmount" },
  { title: "취합 적용상태", key: "ZmrpGatheringStatus" },
]);

const getMrpGatherList = async () => {
  const url =
    "http://localhost:8282/logi/logistics/production/mrpGathering/getMrpList";
  const params = { mrpGatheringStatusCondition: "" };

  const res = await axios.get(url, { params: params });

  console.log(res.data.gridRowJson);
  const data = res.data.gridRowJson;
  const filteredList = data.filter(
    (item) =>
      item.itemClassification === "반제품" ||
      item.itemClassification === "완제품"
  );
  console.log(filteredList);
  item.value = filteredList;
};

getMrpGatherList();

watch([item], () => {
  const list = [];

  for (let i = 0; i < item.value.length; i++) {
    console.log(item.value[i].mrpNo);
    list.push(item.value[i].mrpNo);
    mpsNoList.value = list;
  }

  console.log("mpsNoList.value is : ", toRaw(mpsNoList.value));
});

provide("mrpList", item);
provide("mpsNoList", mpsNoList);
</script>

<template>
  <div>
    <div class="header_wrap">
      <p style="font-weight: bold">
        품목구분이 <span class="span">반제품,완제품</span>인 경우 필요수량은
        <span class="span">생산</span>하여야 합니다.
      </p>
      <MrpGatherResultModal selectedTab="생산" @get-mrp-gather-list="getMrpGatherList" />
      <p></p>
    </div>
    <VDataTable
      :hover="true"
      :headers="headers"
      :items="item"
      :items-per-page="5"
    >
    </VDataTable>
  </div>
</template>

<style scoped>
.span {
  color: green;
  /* text-decoration-color: red; */
}

.header_wrap {
  position: relative;
}
</style>
