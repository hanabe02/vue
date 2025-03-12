<script setup lang="ts">
import { VDataTable } from "vuetify/labs/VDataTable";

import axios from "axios";

const startDate = ref("");
const endDate = ref("");
const item = ref([]);

const headers = ref([
  { title: "판매계획번호", key: "salesPlanNo" },
  { title: "품목코드", key: "itemCode" },
  { title: "품목명", key: "itemName" },
  { title: "판매계획수량", key: "salesAmount" },
  { title: "계획단가 ", key: "unitPriceOfSales" },
  { title: "합계액", key: "sumPriceOfSales" },
  { title: "판매계획일", key: "salesPlanDate" },
  { title: "판매계획마감일", key: "dueDateOfSales" },
  { title: "MPS 계획일", key: "mpsPlanDate" },
  { title: "출하예정일", key: "scheduledEndDate" },
  { title: "MPS 적용", key: "mpsApplyStatus" },
  { title: "비고", key: "description" },
]);

watch([startDate, endDate], () => {
  console.log(startDate.value, endDate.value);
});

const getSalesPlanData = () => {
  if (startDate.value === "" || endDate.value === "") {
    alert("시작일 혹은 종료일을 선택해 주세요");
    return;
  }

  // 서버로 요청을 보내는 로직
};


const saveSalesPlanMps = () => {
  const bool = confirm('등록하시겠습니까?')
  if(!bool)return;
  
  
  
};
const selectedRow = () => {};

console.log("it is work at selling_plan mps");
</script>

<template>
  <div class="gap"></div>
  <div class="page_wrapper">
    <div class="header_wrap">
      <input class="date" type="date" v-model="startDate" />
      <input class="date" type="date" v-model="endDate" />
      <v-btn class="btn_search" @click="getSalesPlanData"
        >MPS 등록가능 판매계획 조회</v-btn
      >
      <v-btn class="btn_insert" @click="saveSalesPlanMps">MPS 등록</v-btn>
    </div>

    <div class="gap"></div>
    <VDataTable
      :headers="headers"
      :items="item"
      :items-per-page="5"
      @click:row="selectedRow"
    >
      <template #item.mpsPlanDate="{ item }">
        <AppDateTimePicker
          v-model="item.mpsPlanDate"
          placeholder="MPS 계획일"
        />
      </template>

      <template #item.scheduledEndDate="{ item }">
        <AppDateTimePicker
          v-model="item.scheduledEndDate"
          placeholder="출하예정일"
        />
      </template>
    </VDataTable>
  </div>
</template>

<style scoped>
.page_wrapper {
  /* border: 1px solid red; */
}

.header_wrap {
  position: relative;
  width: auto;
  /* border: 3px solid orange; */
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

.btn_search {
  border: none;
  width: 210px;
  height: 40px;
  border-radius: 3px;
}

.btn_insert {
  position: absolute;
  margin-left: 400px;
  right: 0px;
  border: none;
  width: 180px;
  height: 40px;
  border-radius: 3px;
}

.gap {
  height: 50px;
}
</style>
