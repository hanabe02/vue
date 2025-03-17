<script lang="ts" setup>
import axios from "axios";
const isDialogVisible = ref(false);
const selectedRadio = ref("");
const { contract, salesPlan } = defineProps(["contract", "salesPlan"]);
const emit = defineEmits(["get-mps-data"]);

// 부모 컴포넌트에서 provide한 데이터를 받아온다.
// ---> provide된 데이터가 변경될 때마다 변경된 데이터를 inject받는다.
//       provide된 데이터는 변경할수는 있지만 예측불가 하므로
//       권장되지는 않는다.
const data = inject("info");
const mpsPlanAmount = ref("");
// 백단으로 보낼 데이터를 담을 reactive
const modifiedData = reactive({
  mpsNo: "",
  contractDetailNo: "",
  mpsPlanClassification: "",
  itemCode: "",
  itemName: "",
  mpsPlanAmount: "",
  mpsPlanDate: "",
  dueDateOfMps: "",
  scheduledEndDate: "",
  mrpApplyStatus: "",
  description: "",
});

// 모달창이 새로 열릴 때마다 mpsPlanAmount에 의 ref값을 변경
// 여기에서 서버로 보낼 수정된 데이터에 mpsPlanAmount 칼럼을 제외한 모든 값들을 할당해준다.
watch([isDialogVisible], () => {
  mpsPlanAmount.value = data.value.mpsPlanAmount;
  console.log(data.value.mpsPlanAmount);

  modifiedData.mpsNo = data.value.mpsNo;
  modifiedData.contractDetailNo = data.value.contractDetailNo;
  modifiedData.mpsPlanClassification = data.value.mpsPlanClassification;
  modifiedData.itemCode = data.value.itemCode;
  modifiedData.itemName = data.value.itemName;
  modifiedData.mpsPlanAmount = data.value.mpsPlanAmount;
  modifiedData.mpsPlanDate = data.value.mpsPlanDate;
  modifiedData.dueDateOfMps = data.value.dueDateOfMps;
  modifiedData.scheduledEndDate = data.value.scheduledEndDate;
  modifiedData.mrpApplyStatus = data.value.mrpApplyStatus;
  modifiedData.description = data.value.description;
});

// 특정 탭이 열릴때 한번만 실행되면 되기때문에 watch같은 것을 사용하지 않음
if (contract) {
  selectedRadio.value = "contract";
} else if (salesPlan) {
  selectedRadio.value = "salesPlan";
}

watch(mpsPlanAmount, () => {
  console.log("changed mpsPlanAmount is : ", mpsPlanAmount.value);
  modifiedData.mpsPlanAmount = mpsPlanAmount.value;
  // 서버로 보내기 위한 데이터

  const data = toRaw(modifiedData);
  console.log("data at variable is : ", data);

  console.log("modifiedData is : ", toRaw(modifiedData));
});

const saveModifiedMps = async (url, obj) => {
  console.log(url, obj);
  const response = await axios.post(url, obj);

  console.log(response);
  // mps정보가 수정되고 나면은 부모 컴포넌트에서
  // mps데이터를 가지고 오는 로직을 실행 시킨다.
  emit("get-mps-data");
};

const saveMrp = () => {
  // toRaw()는 프록시에 있는 데이터를 
  // 가독성이 높은 객체 형태로 변환
  // 해주는 메서드이다. 
  const data = toRaw(modifiedData);
  const tab = selectedRadio.value;
  if (tab === "contract") {
    console.log("this is contract tab");
    const url = "http://localhost:8282/logi/logistics/production/updateMps";
    const obj = data;

    saveModifiedMps(url, obj);
    isDialogVisible.value = false;

    // 판매계획 서비스는 아직 구현되지 않았음
  } else if (tab === "salesPlan") {
    console.log("this is salesPlan tab");
    const url = "";
    const obj = {};
  }
};
</script>
<template>
  <VDialog v-model="isDialogVisible" :width="'25%'">
    <!-- Activator -->
    <template #activator="{ props }">
      <VBtn class="mrp_simulation_btn" v-bind="props"> MPS수정 </VBtn>
    </template>

    <!-- Dialog close btn -->
    <DialogCloseBtn @click="isDialogVisible = !isDialogVisible" />

    <!-- Dialog Content -->
    <VCard height="100vh" title="MPS 수정">
      {{ contract }}
      {{ salesPlan }}

      <label class="label" for="주생산계획번호">주생산계획번호</label>

      <input
        name="주생산계획번호"
        class="input"
        type="text"
        :value="data.mpsNo"
        readonly
      />
      <label class="label" for="수주상세일련번호">수주상세일련번호</label>
      <input
        name="수주상세일련번호"
        class="input"
        type="text"
        :value="data.contractDetailNo"
        readonly
      />
      <label class="label" for="계획구분">계획구분</label>
      <input
        name="계획구분"
        class="input"
        type="text"
        :value="data.mpsPlanClassification"
        readonly
      />
      <label class="label" for="품목코드">품목코드</label>
      <input
        name="품목코드"
        class="input"
        type="text"
        :value="data.itemCode"
        readonly
      />
      <label class="label" for="품목명">품목명</label>
      <input
        name="품목명"
        class="input"
        type="text"
        :value="data.itemName"
        readonly
      />
      <label class="label" for="MPS 계획수량">MPS 계획수량</label>
      <input
        name="MPS 계획수량"
        class="input"
        type="text"
        v-model="mpsPlanAmount"
      />
      <label class="label" for="MPS 계획일자">MPS 계획일자</label>
      <input
        name="MPS 계획일자"
        class="input"
        type="text"
        :value="data.mpsPlanDate"
        readonly
      />
      <label class="label" for="납기일">납기일</label>
      <input
        name="납기일"
        class="input"
        type="text"
        :value="data.dueDateOfMps"
        readonly
      />
      <label class="label" for="출하예정일">출하예정일</label>
      <input
        name="출하예정일"
        class="input"
        type="text"
        :value="data.scheduledEndDate"
        readonly
      />
      <label class="label" for="MRP 적용상태">MRP 적용상태</label>
      <input
        name="MRP 적용상태"
        class="input"
        type="text"
        :value="data.mrpApplyStatus ? data.mrpApplyStatus : 'N'"
        readonly
      />
      <label class="label" for="비고">비고</label>
      <input
        name="비고"
        class="input"
        type="text"
        :value="data.description"
        readonly
      />

      <VCardText class="d-flex justify-end"> </VCardText>
    </VCard>
    <VBtn width="180" class="save_btn" @click="saveMrp"> MPS 수정 </VBtn>
  </VDialog>
</template>

<style scoped>
.save_btn {
  position: absolute;
  right: 8px;
  bottom: 8px;
}

.content_wrap {
  height: 260px;
}

.input {
  border: 1px solid skyblue;
  margin-top: 5px;
  margin-bottom: 5px;
  height: 50px;
  text-align: center;
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 3px;
}

.label {
  text-align: center;
}
</style>
