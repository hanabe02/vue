<script setup lang="ts">
import { VDataTable } from "vuetify/labs/VDataTable";
import MrpSimulationModal from "./MrpSimulationModal.vue";
import MpsModifyModal from "./MpsModifyModal.vue";
import axios from "axios";

//for test commit

const startDate = ref("");
const endDate = ref("");
const item = ref([]);
const selectedItem = ref("");

const contractInfo = ref("");

const headers = reactive([
  { title: "주생산계획번호", key: "mpsNo" },
  { title: "수주상세일련번호", key: "contractDetailNo" },
  { title: "계획구분", key: "mpsPlanClassification" },
  { title: "품목코드", key: "itemCode" },
  { title: "품목명", key: "itemName" },
  { title: "MPS 계획수량", key: "mpsPlanAmount" },
  { title: "MPS 계획일자", key: "mpsPlanDate" },
  { title: "출하예정일", key: "scheduledEndDate" },
  { title: "납기일", key: "dueDateOfMps" },
  { title: "MRP 적용상태", key: "mrpApplyStatus" },
  { title: "비고", key: "description" },
]);

// 서버로 요청을 보내는 로직
const getMpsData = async () => {
  // 시작일과 종료일이 선택이 되지 않으면은 요청을 보낼수 없게 한다.
  if (startDate.value === "" || endDate.value === "") {
    alert("시작일 혹은 종료일을 선택해 주세요.");
    return;
  }

  console.log(startDate.value, endDate.value);
  const url = "http://localhost:8282/logi/logistics/production/searchMpsInfo";
  const params = { startDate: startDate.value, endDate: endDate.value };

  const response = await axios
    .get(url, {
      params: params,
    })
    .catch((err) => console.log("err at getMpsData() is :", err));
  item.value = response.data.result;

  // 응답을 받고 나서selectedItem ref를 초기화
  selectedItem.value = "";
  console.log(response.data.result);
};

// promise안에 값이 들어있는 객체가있다.
const selectedRow = (value, item) => {
  console.log(value, item);
  console.log("toRawed value is :", toRaw(item.item));
  selectedItem.value = item.item;
};

watch(selectedItem, () => {
  const data = selectedItem._rawValue;
  console.log("toRawed data at selectedItem is ", data);
  contractInfo.value = data;
});

// 선택된 열을 자식 컴포넌트들에 provide
provide("info", selectedItem);
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
      <!-- MPS 수정 모달 버튼 -->
      <MpsModifyModal contract="contract" @get-mps-data="getMpsData" />
      <!-- <v-btn class="btn_estimate" @click="modalOpen">MRP 모의전개</v-btn> -->

      <MrpSimulationModal contract="contract" @get-mps-data="getMpsData" />
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
  /* border: 1px solid blue; */
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
  /* border: 1px solid red; */
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
