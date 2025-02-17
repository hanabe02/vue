<script setup lang="ts">
import { VDataTable } from "vuetify/labs/VDataTable";
import MrpSimulationModal from "./MrpSimulationModal.vue";
import MpsModifyModal from "./MpsModifyModal.vue";
import axios from "axios";

const startDate = ref("");
const endDate = ref("");
const item = ref([]);
const selectedItem = ref([]);
const salesPlanInfo = reactive({ type: "salesPlanInfo" });

const headers = reactive([
  { title: "주생산계획번호", key: "mpsNo" },
  { title: "판매계획번호", key: "salesPlanNo" },
  { title: "계획구분", key: "mpsPlanClassification" },
  { title: "품목코드", key: "itemCode" },
  { title: "품목명", key: "itemName" },
  { title: "MPS 계획수량", key: "mpsPlanAmount" },
  { title: "MPS 계획일자", key: "mpsPlanDate" },
  { title: "납기일", key: "dueDateOfMps" },
  { title: "출하예정일", key: "scheduledEndDate" },
  { title: "MRP 적용상태", key: "mrpApplyStatus" },
  { title: "비고", key: "description" },
]);

provide("info", selectedItem);
// 서버로 요청을 보내는 로직
const getMpsData = () => {
  if (startDate.value === "" || endDate.value === "") {
    alert("시작일 혹은 종료일을 선택해 주세요.");
    return;
  }
};

// promise안에 값이 들어있는 객체가있다.
const selectedRow = (value, item) => {
  console.log(value, item.item);
  selectedItem.value = item.item;
};
</script>

<template>
  <div class="page_wrapper">
    <div>
      <label class="startDate">MPS 계획일자 선택</label>
      <label class="endDate">MPS 종료일자 선택</label>
    </div>
    <div class="header_wrap">
      <div class="startDate_wrap">
        <input name="startDate" class="date" type="date" v-model="startDate" />
      </div>
      <input class="date" type="date" v-model="endDate" />
      <v-btn class="btn_search" @click="getMpsData">수주조회</v-btn>
      <!-- MPS 수정 모달 버튼  -->
      <MpsModifyModal sales-plan="salesPlan" />

      <!-- <v-btn class="btn_register">수주수정</v-btn> -->
      <!-- <v-btn class="btn_estimate" @click="modalOpen">MRP 모의전개</v-btn> -->

      <!-- props로 현재 페이지의 정보를 넘겨줘서 modal에서 서버로 보내는 요청이     -->
      <!-- 다르게 해야겠다. -->
      <MrpSimulationModal sales-plan="salesPlan" />
      <!-- <v-btn class="btn_estimate" @click="modalOpen">MRP 모의전개</v-btn> -->
    </div>

    <div class="gap"></div>
    <VDataTable
      :hover="true"
      :headers="headers"
      :items="item"
      :items-per-page="5"
      :select-strategy="'single'"
      @click:row="selectedRow"
    >
    </VDataTable>
  </div>
</template>

<style scoped>
.page_wrapper {
  /* border: 1px solid green; */
}

.header_wrap {
  position: relative;
  width: auto;
  /* border: 3px solid orange; */
  height: 70px;
  border-radius: 5px;
}
.radio_wrap {
  margin-bottom: 20px;
  /* border: 1px solid blue; */
}

.date {
  border: none;
  width: 150px;
  height: 40px;
  border-radius: 3px;
  margin-right: 20px;
  border: 1px solid skyblue;
}

.startDate_wrap {
  display: inline-block;
}

.endDate {
  margin-left: 50px;
}
.btn_search {
  border: none;
  width: 100px;
  height: 40px;
  border-radius: 3px;
}

.btn_register {
  border: none;
  width: 100px;
  height: 40px;
  border-radius: 3px;
}

.btn_estimate {
  position: absolute;
  right: 0px;

  border: none;
  width: 120px;
  height: 40px;
  border-radius: 3px;
}

.gap {
  height: 50px;
}
</style>
