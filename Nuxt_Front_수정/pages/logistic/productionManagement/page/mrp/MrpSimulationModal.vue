<script lang="ts" setup>
import { VDataTable } from "vuetify/labs/VDataTable";
import axios from "axios";

const { contract, salesPlan } = defineProps(["contract", "salesPlan"]);
const emit = defineEmits(["get-mps-data"]);
// 부모 컴포넌트로부터 provide받은 데이터를 inject한다.
const data = inject("info");
const selectedRadio = ref("");
const selectedDate = ref("");
const item = ref([]);
const isDialogVisible = ref(false);

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

// 부모 컴포넌트로부터 provide되어 들어온 데이러를 inject로 받아서 사용

const searchMrpList = async (mpsNo) => {
  console.log("data is : ", data);
  console.log("mpsNo is : ", mpsNo);
  const url = "http://localhost:8282/logi/logistics/production/openMrp";
  const params = {
    mpsNo: mpsNo,
  };

  const response = await axios.get(url, {
    params: params,
  });

  console.log(response.data.result);
  item.value = response.data.result;
};

// 데이터를 inject 받아서 서버로 요청을 보내는 로직을 작성
// 데이터를 선택하면은 선택된 데이터가 provide가 되고,
// inject된 새로운 데이터를 watch가 감지한다.
// inject된 새로운 데이터에서 mpsNo를 추출해서 백단으로 넘겨준다.
// ---> watch는 초기값을 감지하지는 않는거 같다.
watch([data, selectedDate], () => {
  // console.log("data from watch is SimulationModal is : ", data._rawValue.mpsNo);
  // console.log("selectedDate.value is : ", selectedDate.value);
  const mpsNo = data._rawValue.mpsNo;
  if (contract) {
    console.log("this is for contract");
    // 넘어온 mpsNo가 유효하지 않을때는 서버로 요청을 보내지 않는다.
    if (mpsNo === "" || mpsNo === undefined)
      return console.log(`${'mpsNo is undefined or "" or null'} `);
    searchMrpList(mpsNo);
  } else if (salesPlan) {
    // 넘어온 mpsNo가 유효하지 않을때는 서버로 요청을 보내지 않는다.
    if (mpsNo === "" || mpsNo === undefined)
      return console.log(`${'mpsNo is undefined or "" or null'} `);
    searchMrpList(mpsNo);
    console.log("this is for salesPlan");
  }
});

if (contract) {
  selectedRadio.value = "contract";
} else if (salesPlan) {
  selectedRadio.value = "salesPlan";
}

const saveMrp = async () => {
  console.log("item.value is : ", toRaw(item.value));
  //mps 등록일자를 선택해 주지 않으면은 서버로 요청을 보낼수 없게한다
  if (selectedDate.value === "") {
    alert("mrp 등록일자를 선택해 주세요.");
    return;
  }

  const toRawedData = toRaw(item.value);
  console.log(toRawedData, selectedDate.value);

  const url = "http://localhost:8282/logi/logistics/production/registerMrp";
  const body = { mrpRegisterDate: selectedDate.value, batchList: toRawedData };
  const response = await axios
    .put(url, body)
    .catch((err) => console.log("err is : ", err));
  console.log("response is : ", response);
  isDialogVisible.value = false;
  // mrp 등록후 부모 컴포넌트에 있는 getData() 함수를 호출
  emit("get-mps-data");
};
</script>

<template>
  <VDialog v-model="isDialogVisible" width="90%">
    <!-- Activator -->
    <template #activator="{ props }">
      <VBtn class="mrp_simulation_btn" v-bind="props"> MRP 모의전개 </VBtn>
    </template>

    <!-- Dialog close btn -->
    <DialogCloseBtn @click="isDialogVisible = !isDialogVisible" />

    <!-- Dialog Content -->
    <VCard height="80vh" title="MRP 모의전개">
      <VCardText class="d-flex justify-end">
        <div>
          <label class="mrpRegisterDate_label" for="mrpRegisterDate"
            >MRP 등록일자</label
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

      <VBtn width="300" class="save_btn" @click="saveMrp"> MRP 등록 </VBtn>
    </VCard>
  </VDialog>
</template>

<style scoped>
.save_btn {
  position: absolute;
  right: 8px;
  bottom: 8px;
}

.mrp_simulation_btn {
  position: absolute;
  right: 0px;
}

.mrpRegisterDate_label {
  text-align: center;
}

.mrpRigsterDate_input {
  margin-top: 15px;
  border: 1px solid skyblue;
  width: 150px;
  height: 40px;
  border-radius: 3px;
}
</style>
