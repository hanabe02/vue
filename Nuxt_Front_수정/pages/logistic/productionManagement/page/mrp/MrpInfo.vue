<script lang="ts" setup>
import axios from "axios";
import { VDataTable } from "vuetify/labs/VDataTable";
const startDate = ref("");
const endDate = ref("");
const selectedOption = ref("");
const item = ref([]);

const headers = [
  { key: "mrpGatheringNo", title: "소요량취합번호" },
  { key: "orderOrProductionStatus", title: "구매 및 생산여부" },
  { key: "itemCode", title: "품목코드" },
  { key: "itemName", title: "품목명" },
  { key: "necessaryAmount", title: "필요수량" },
  { key: "claimDate", title: "지시일" },
  { key: "dueDate", title: "납기일" },
];

watch(selectedOption, () => {
  console.log("selectedOption is : ", selectedOption.value);
});

const isValid = () => {
  console.log(startDate.value, endDate.value, selectedOption.value);
  if (
    startDate.value === "" ||
    endDate.value === "" ||
    selectedOption.value === ""
  ) {
    alert("날짜 혹은 조회 기준을 선택해 주세요");
    return false;
  }
  return true;
};
const getData = async () => {
  if (!isValid()) return;
  console.log(startDate.value, endDate.value, selectedOption.value);

  const url =
    "http://localhost:8282/logi/logistics/production/mrpGathering/searchMrpGathering";
  const data = {
    params: {
      startDate: startDate.value,
      endDate: endDate.value,
      searchDateCondition: selectedOption.value,
    },
  };

  const response = await axios
    .get(url, data)
    .catch((err) => console.log("error occured at getData() err is :", err));
  console.log(response.data);
  item.value = response.data;
};
</script>

<template>
  <div class="gap"></div>
  <div class="main_wrap">
    <div class="header_wrap">
      <input class="date" type="date" v-model="startDate" />
      <input class="date" type="date" v-model="endDate" />

      <select
        class="select"
        v-model="selectedOption"
      >
        <option class="option" value="claimDate">지시일</option>
        <option class="option" value="dueDate">납기일</option>
      </select>

      <v-btn class="btn_search" @click="getData">소요량 취합 결과 조회</v-btn>
    </div>
    <div class="gap"></div>
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
.main_wrap {
}
.header_wrap {
  position: relative;
  width: auto;
  height: fit-content;
  border-radius: 5px;
}

.date {
  border: none;
  width: 150px;
  height: 40px;
  border-radius: 3px;
  margin-right: 20px;
  border: 1px solid skyblue;
}

.select {
  width: 200px;
  height: 40px;
  border: none;
  margin-right: 20px;
  text-align: center;
  border: 1px solid skyblue;
  border-radius: 3px;
  background-color: inherit;
  color: skyblue;
}

.option {
  color: black;
  background-color: inherit;
  text-align: center;
}
.btn_search {
  border: none;
  width: 180px;
  height: 40px;
  border-radius: 3px;
}

.gap {
  height: 50px;
}
</style>
