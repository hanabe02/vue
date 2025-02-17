<script setup lang="ts">
import axios from "axios";
import { VDataTable } from "vuetify/labs/VDataTable";
const isDialogVisible = ref(false);
const item = ref([]);
const selectedDate = ref("");
const productMrpNoAndItemCodeList = ref([]);

// 부모 컴포넌트에서 provide된 데이터를 inject 받는다.
const mpsNoList = inject("mpsNoList");
const mrpList = inject("mrpList");
const { selectedTab } = defineProps(["selectedTab"]);
const emit = defineEmits(["get-mrp-gather-list"]);

const getMrpGatherResultList = async () => {
  const url =
    "http://localhost:8282/logi/logistics/production/mrpGathering/getMrpGatheringList";
  const params = { mpsNoList: `${mpsNoList._rawValue}` };

  const response = await axios.get(url, {
    params: params,
  });
  console.log("response from MrpGatherResultModal", response);
  console.log("response from MrpGatherResultModal", response.data.gridRowJson);
  item.value = response.data.gridRowJson;
};

const setData = () => {
  console.log("mrpList is : ", mrpList._rawValue);

  let list = [];
  // provide된 데이터를 받아서 새로운 객체배열을 생성
  list = mrpList._rawValue.map((item) => ({
    itemCode: item.itemCode,
    mrpNo: item.mrpNo,
  }));
  console.log("mapped list is : ", list);
  //생성된 객체배열을 ref에 할당
  productMrpNoAndItemCodeList.value = list;
};

watch([mpsNoList], () => {
  console.log("selectedTab", selectedTab);
  console.log("injected mpsNoList is : ", mpsNoList._rawValue);
  getMrpGatherResultList();
  console.log("selectedTab from watcher is : ", selectedTab);
  if (selectedTab === "구매") {
    // 서버로 보낼 데이터를 생성하는 함수를 호출
    setData();
  } else if (selectedTab === "생산") {
    // 서버로 보낼 데이터를 생성하는 함수를 호출
    setData();
  }
});

const headers = ref([
  { title: "구매 및 생산여부", key: "orderOrProductionStatus" },
  { title: "품목코드", key: "itemCode" },
  { title: "품목명", key: "itemName" },
  { title: "단위", key: "unitOfMrpGathering" },
  { title: "지시일", key: "claimDate" },
  { title: "납기일", key: "dueDate" },
  { title: "필요수량", key: "necessaryAmount" },
]);

const sendData = async (url, data) => {
  console.log(
    "console from sendData are : ",
    item._rawValue,
    selectedDate._rawValue,
    productMrpNoAndItemCodeList._rawValue
  );

  const response = await axios
    .put(url, data, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .catch((err) => console.log(err));
    // 소요량 취합등록을 하고나서 부모 컴포넌트에서
    // 서버로 요청을 보내는 로직을 수행 
  emit("get-mrp-gather-list");
  console.log(response);
};

const saveMrp = () => {
  console.log(
    item._rawValue,
    selectedDate._rawValue,
    productMrpNoAndItemCodeList._rawValue
  );
  if (selectedDate.value === "") {
    alert("소요량 취합 등록일자를 선택해 주세요.");
    return;
  }

  const url =
    "http://localhost:8282/logi/logistics/production/mrpGathering/registerMrpGathering";
  const data = {
    batchList: item._rawValue,
    mrpGatheringRegisterDate: selectedDate._rawValue,
    mrpNoAndItemCode: productMrpNoAndItemCodeList._rawValue,
  };

  sendData(url, data);
  isDialogVisible.value = false;
};
</script>

<template>
  <VDialog v-model="isDialogVisible" width="90%">
    <!-- Activator -->
    <template #activator="{ props }">
      <VBtn class="mrp_gatehr_result_btn" v-bind="props">
        {{ selectedTab }} 소요량 취합 실행
      </VBtn>
    </template>

    <!-- Dialog close btn -->
    <DialogCloseBtn @click="isDialogVisible = !isDialogVisible" />

    <!-- Dialog Content -->
    <VCard height="80vh" :title="`${selectedTab} 소요량 취합`">
      <VCardText class="d-flex justify-end">
        <div>
          <label class="mrpRegisterDate_label" for="mrpRegisterDate"
            >소요량 취합 등록일자</label
          ><br />
          <input
            class="mrpRigsterDate_input"
            name="mrpRegisterDate"
            type="date"
            v-model="selectedDate"
          />
        </div>
      </VCardText>
      <VCardText>
        <VDataTable
          :hover="true"
          :headers="headers"
          :items="item"
          :items-per-page="5"
        >
        </VDataTable>
      </VCardText>

      <VCardText class="d-flex justify-end"> </VCardText>
      <VBtn width="300" class="save_btn" @click="saveMrp">
        취합 결과 등록
      </VBtn>
    </VCard>
  </VDialog>
</template>

<style scoped>
.mrp_gatehr_result_btn {
  position: absolute;
  right: 0px;
  bottom: 0px;
}

.mrpRigsterDate_input {
  margin-top: 15px;
  border: 1px solid skyblue;
  width: 150px;
  height: 40px;
  border-radius: 3px;
}

.save_btn {
  position: absolute;
  right: 8px;
  bottom: 8px;
}
</style>
