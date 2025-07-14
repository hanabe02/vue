<template>
  <VRow>
    <!-- 견적조회 -->
    <VCol md="12">
      <VCard class="mb-6" title="견적 조회">
        <VCardText>
          <VRow align="center">
            <VCol cols="3">
              <v-btn class="ml-3" @click="searchDate">견적 조회</v-btn>
            </VCol>
            <VCol cols="3" style="align-self: flex-end">
              <v-select
                v-model="selectedItem"
                class="mb-3"
                label="선택"
                :items="items"
                item-title="text"
                item-value="value"
                @input="updateSelectedItem"
              ></v-select>
            </VCol>
            <VCol cols="3">
              <AppDateTimePicker
                class="mb-3"
                v-model="startDate"
                label="시작일"
                placeholder="YYYY-MM-DD"
              />
            </VCol>
            <VCol cols="3">
              <AppDateTimePicker
                class="mb-3"
                v-model="endDate"
                label="종료일"
                placeholder="YYYY-MM-DD"
              />
            </VCol>
          </VRow>
        </VCardText>
        <VCardText>
          <VDataTable
            :headers="headers"
            :items="infodata"
            :items-per-page="5"
          />
        </VCardText>
      </VCard>
    </VCol>
  </VRow>

  <!-- 견적 상세 -->
  <VRow>
    <VCol md="12">
      <VCard class="mb-6" title="견적 상세">
        <VCardText>
          <VDataTable
            :headers="detailheaders"
            :items="infodata1"
            :items-per-page="5"
          />
        </VCardText>
      </VCard>
    </VCol>
  </VRow>
</template>

<script setup lang="ts">
import axios from "axios";
import { ref, onMounted, computed } from "vue";
import { VDataTable } from "vuetify/labs/VDataTable";

const infodata = ref([]);
const infodata1 = ref([]);

const selectedItem = ref(null);
const startDate = ref("");
const endDate = ref("");
const dateSearchCondition = ref([]);

onMounted(async () => {
  infodata.value = await fetchData();
  infodata1.value = await fetchData1();
});

const updateSelectedItem = (value: any) => {
  selectedItem.value = value;
  console.log("select", selectedItem);
};

const headers = [
  { title: "견적일련번호", key: "estimateNo", align: "center" },
  { title: "거래처코드", key: "customerCode", align: "center" },
  { title: "견적일자", key: "estimateDate", align: "center" },
  { title: "수주여부", key: "contractStatus", align: "center" },
  { title: "견적요청자", key: "estimateRequester", align: "center" },
  { title: "유효일자", key: "effectiveDate", align: "center" },
  { title: "견적담당자코드", key: "personCodeInChange", align: "center" },
  { title: "비고", key: "description", align: "center" },
];

const detailheaders = [
  { title: "견적상세일련번호", key: "estimateDetailNo", align: "center" },
  { title: "품목코드", key: "itemCode", align: "center" },
  { title: "품목명", key: "itemName", align: "center" },
  { title: "단위", key: "unitOfEstimate", align: "center" },
  { title: "납기일", key: "dueDateOfEstimate", align: "center" },
  { title: "견적수량", key: "estimateAmount", align: "center" },
  { title: "견적단가", key: "unitPriceOfEstimate", align: "center" },
  { title: "합계액", key: "sumPriceOfEstimate", align: "center" },
  { title: "비고", key: "description", align: "center" },
  { title: "견적일련번호", key: "estimateNo", align: "center" },
];

const items = [
  { value: "estimateDate", text: "견적일자" },
  { value: "effectiveDate", text: "유효일자" },
];

const searchDate = async () => {
  // 시작일, 종료일, 드롭다운 모두 선택하지 않은 경우
  if (!startDate.value && !endDate.value && !selectedItem.value) {
    alert("시작일과 종료일을 선택하고, 선택도 해주세요!");
    return;
  }

  // 시작일과 종료일 모두 선택하지 않은 경우
  if (!startDate.value || !endDate.value) {
    alert("시작일과 종료일을 선택해주세요!");
    return;
  }

  // 드롭다운 선택을 하지 않은 경우
  if (!selectedItem.value) {
    alert("선택을 해주세요!");
    return;
  }

  try {
    const response = await fetchData(
      startDate.value,
      endDate.value,
      selectedItem.value
    );
    infodata.value = response;

    const detailResponse = await fetchData1(
      startDate.value,
      endDate.value,
      selectedItem.value
    );
    infodata1.value = detailResponse;
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

const fetchData = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8282/logi/sales/searchEstimates",
      {
        params: {
          startDate: startDate.value,
          endDate: endDate.value,
          dateSearchCondition: selectedItem.value,
        },
      }
    );
    console.log("estimate", response.data.gridRowJson);
    return response.data.gridRowJson;
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

const fetchData1 = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8282/logi/sales/searchEstimates",
      {
        params: {
          startDate: startDate.value,
          endDate: endDate.value,
          dateSearchCondition: selectedItem.value,
        },
      }
    );

    // 가져온 데이터 중에서 estimateDetailTOList를 infodata1에 할당합니다.
    const detailList = response.data.gridRowJson
      .map((item: any) => item.estimateDetailTOList)
      .flat();
    console.log("estimatedetail", detailList);
    infodata1.value = detailList;

    return detailList;
  } catch (error) {
    console.error("Error fetching data:", error);
    return [];
  }
};
</script>