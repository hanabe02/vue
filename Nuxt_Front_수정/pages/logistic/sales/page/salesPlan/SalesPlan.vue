<script setup lang="ts">
import { VDataTable } from "vuetify/labs/VDataTable";
import axios from "axios";

// Dialog
const isestimateDialogVisible = ref(false);
const isestimatedetailDialogVisible = ref(false);

const infodata = ref([]);
const selectData = ref([]);

const salesPlanNo = ref("");
const itemCode = ref("");
const itemName = ref("");
const unitOfSales = ref("");
const salesPlanDate = ref("");
const dueDateOfSales = ref("");
const salesAmount = ref("");
const unitPriceOfSales = ref("");
const sumPriceOfSales = ref("");
const mpsApplyStatus = ref("");
const description = ref("");

const headers = [
  { title: "판매계획일련번호", sortable: false, key: "salesPlanNo", align: "center" },
  { title: "품목코드", key: "itemCode", align: "center" },
  { title: "품목명", key: "itemName", align: "center" },
  { title: "단위", key: "unitOfSales", align: "center" },
  { title: "판매계획일", key: "salesPlanDate", align: "center" },
  { title: "계획마감일", key: "dueDateOfSales", align: "center" },
  { title: "계획수량", key: "salesAmount", align: "center" },
  { title: "계획단가", key: "unitPriceOfSales", align: "center" },
  { title: "합계액", key: "sumPriceOfSales", align: "center" },
  { title: "MPS적용상태", key: "mpsApplyStatus", align: "center" },
  { title: "설명", key: "description", align: "center" },
];

const fetchData = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8282/logi/sales/jpasalesplan"
    );
    console.log("salesPlan", response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching data:", error);
    return [];
  }
};

const fetchaddData = async () => {
  try {
    // 입력 데이터가 하나라도 존재하는지 확인
    if (
      salesPlanNo.value ||
      itemCode.value ||
      itemName.value ||
      unitOfSales.value ||
      salesPlanDate.value ||
      dueDateOfSales.value ||
      salesAmount.value ||
      unitPriceOfSales.value ||
      sumPriceOfSales.value ||
      mpsApplyStatus.value ||
      description.value
    ) {
      const adddata = {
        salesPlanNo: salesPlanNo.value,
        itemCode: itemCode.value,
        itemName: itemName.value,
        unitOfSales: unitOfSales.value,
        salesPlanDate: salesPlanDate.value,
        dueDateOfSales: dueDateOfSales.value,
        salesAmount: salesAmount.value,
        unitPriceOfSales: unitPriceOfSales.value,
        sumPriceOfSales: sumPriceOfSales.value,
        mpsApplyStatus: mpsApplyStatus.value,
        description: description.value,
      };

      const response = await axios.post(
        "http://localhost:8282/logi/sales/jpasalesplansave",
        adddata
      );

      if (response.status === 200) {
        alert("추가 완료되었습니다!");
        isestimateDialogVisible.value = false;
        infodata.value = await fetchData();
      }

      return response.data;
    } else {
      alert("데이터를 입력해주세요!"); // 아무 데이터도 입력하지 않으면 알림 표시
    }
  } catch (error) {
    alert("추가 실패하였습니다. 다시 확인해주세요!");
    console.error("Error fetching data:", error);
    return [];
  }
};

const fetchupdateData = async () => {
  try {
    const updatedata = {
      salesPlanNo: salesPlanNo.value,
      itemCode: itemCode.value,
      itemName: itemName.value,
      unitOfSales: unitOfSales.value,
      salesPlanDate: salesPlanDate.value,
      dueDateOfSales: dueDateOfSales.value,
      salesAmount: salesAmount.value,
      unitPriceOfSales: unitPriceOfSales.value,
      sumPriceOfSales: sumPriceOfSales.value,
      mpsApplyStatus: mpsApplyStatus.value,
      description: description.value,
    };
    const response = await axios.post(
      "http://localhost:8282/logi/sales/jpaupdatesalesplan",updatedata
    );
    console.log("updatesalesplan", response.data);
    // 데이터 추가가 성공하면 알림 창을 띄우고 다이얼로그를 닫음
    if (response.status === 200) {
      alert("수정 완료되었습니다!");
      isestimatedetailDialogVisible.value = false; // 추가 다이얼로그 닫기
      // 추가된 데이터 다시 불러오기
      infodata.value = await fetchData();
    }

    return response.data;
  } catch (error) {
    alert("수정 실패하였습니다. 다시 확인해주세요!");
    console.error("Error fetching data:", error);
    return [];
  }
};

const fetchadeleteData = async () => {
  try {
    if (!selectData.value || !selectData.value.salesPlanNo) {
      alert("삭제할 데이터를 선택해주세요!");
      return;
    }
    
    const response = await axios.delete(
      "http://localhost:8282/logi/sales/deletesalesplan",
      {
        params: { SalesPlanNo: selectData.value.salesPlanNo },
      }
    );
    console.log("deletesalesplan", response.data);

    // 삭제가 성공하면 알림 창을 띄우고 데이터를 다시 불러옴
    if (response.status === 200) {
      alert("삭제 완료되었습니다!");
      infodata.value = await fetchData();
    }

    return response.data;
  } catch (error) {
    console.error("Error fetching data:", error);
    return [];
  }
};

onMounted(async () => {
  infodata.value = await fetchData();
});

const closeModal = () => {
  isestimateDialogVisible.value = false;
};

const closeModal1 = () => {
  isestimatedetailDialogVisible.value = false;
};

const selectRow = (item: any, row: any) => {
  console.log("row", row?.internalItem?.columns);
  console.log("rowIndex", row.internalItem.index);
  selectData.value = row?.internalItem?.columns;
  console.log("selectData",selectData._rawValue)

  // 행을 선택할 때마다 데이터를 업데이트합니다.
  updateDialogData();
};

const updateDialogData = () => {
  // 선택한 행의 데이터가 있을 경우
  if (selectData.value) {
    // 선택한 데이터를 수정 다이얼로그에 채우기
    salesPlanNo.value = selectData.value.salesPlanNo || "";
    itemCode.value = selectData.value.itemCode || "";
    itemName.value = selectData.value.itemName || "";
    unitOfSales.value = selectData.value.unitOfSales || "";
    salesPlanDate.value = selectData.value.salesPlanDate || "";
    dueDateOfSales.value = selectData.value.dueDateOfSales || "";
    salesAmount.value = selectData.value.salesAmount || "";
    unitPriceOfSales.value = selectData.value.unitPriceOfSales || "";
    sumPriceOfSales.value = selectData.value.sumPriceOfSales || "";
    mpsApplyStatus.value = selectData.value.mpsApplyStatus || "";
    description.value = selectData.value.description || "";
  }
};
</script>

<template>
  <VContainer>
    <VForm @submit.prevent="() => {}">
      <VRow align="center" class="my-4">
        <!-- 👉 판매계획추가 -->
        <VCol cols="auto">
          <VDialog v-model="isestimateDialogVisible" width="500" persistent>
            <!-- 판매계획추가 -->
            <template #activator="{ props }">
              <VBtn v-bind="props"> 추가 </VBtn>
            </template>

            <!-- 다이얼로그 닫기 버튼 -->
            <DialogCloseBtn
              @click="isestimateDialogVisible = !isestimateDialogVisible"
            />

            <!-- 판매계획추가 다이얼로그 테이블 -->
            <VCard title="판매 계획 추가">
              <VContainer>
                <VCol>
                  <AppTextField
                    v-model="salesPlanNo"
                    label="판매계획일련번호"
                    placeholder="판매계획일련번호"
                  />
                  <AppTextField
                    v-model="itemCode"
                    label="품목코드"
                    placeholder="품목코드"
                  />
                  <AppTextField
                    v-model="itemName"
                    label="품목명"
                    placeholder="품목명"
                  />
                  <AppTextField
                    v-model="unitOfSales"
                    label="단위"
                    placeholder="ex) EA"
                  />
                  <AppDateTimePicker
                    class="mb-3"
                    v-model="salesPlanDate"
                    label="판매계획일"
                    placeholder="판매계획일"
                  />
                  <AppDateTimePicker
                    class="mb-3"
                    v-model="dueDateOfSales"
                    label="계획마감일"
                    placeholder="계획마감일"
                  />
                  <AppTextField
                    v-model="salesAmount"
                    label="계획수량"
                    placeholder="계획수량"
                  />
                  <AppTextField
                    v-model="unitPriceOfSales"
                    label="계획단가"
                    placeholder="계획단가"
                  />
                  <AppTextField
                    v-model="sumPriceOfSales"
                    label="합계액"
                    placeholder="합계액"
                  />
                  <AppTextField
                    v-model="mpsApplyStatus"
                    label="MPS적용상태"
                    placeholder="MPS적용상태"
                  />
                  <AppTextField
                    v-model="description"
                    label="설명"
                    placeholder="설명"
                  />
                </VCol>
              </VContainer>

              <VCardText class="d-flex justify-end flex-wrap gap-3">
                <VBtn variant="tonal" color="secondary" @click="closeModal">
                  취소
                </VBtn>
                <VBtn @click="fetchaddData"> 입력 </VBtn>
              </VCardText>
            </VCard>
          </VDialog>
        </VCol>
        <!-- 👉 판매계획수정 -->
        <VCol cols="auto">
          <VDialog
            v-model="isestimatedetailDialogVisible"
            width="500"
            persistent
          >
            <!-- 판매계획수정 버튼 -->
            <template #activator="{ props }">
              <VBtn v-bind="props"> 수정 </VBtn>
            </template>

            <!-- 다이얼로그 닫기 버튼 -->
            <DialogCloseBtn
              @click="
                isestimatedetailDialogVisible = !isestimatedetailDialogVisible
              "
            />

            <!-- 판매계획수정 다이얼로그 테이블 -->
            <VCard title="판매 계획 수정">
              <VContainer>
                <VCol>
                  <AppTextField
                    v-model="salesPlanNo"
                    label="판매계획일련번호"
                  />
                  <AppTextField v-model="itemCode" label="품목코드" />
                  <AppTextField v-model="itemName" label="품목명" />
                  <AppTextField v-model="unitOfSales" label="단위" />
                  <AppDateTimePicker
                    v-model="salesPlanDate"
                    label="판매계획일"
                  />
                  <AppDateTimePicker
                    v-model="dueDateOfSales"
                    label="계획마감일"
                  />
                  <AppTextField
                    v-model="salesAmount"
                    label="계획수량"
                  />
                  <AppTextField
                    v-model="unitPriceOfSales"
                    label="계획단가"
                  />
                  <AppTextField
                    v-model="sumPriceOfSales"
                    label="합계액"
                  />
                  <AppTextField
                    v-model="mpsApplyStatus"
                    label="MPS적용상태"
                  />
                  <AppTextField v-model="description" label="설명" />
                </VCol>
              </VContainer>

              <VCardText class="d-flex justify-end flex-wrap gap-3">
                <VBtn variant="tonal" color="secondary" @click="closeModal1">
                  취소
                </VBtn>
                <VBtn @click="fetchupdateData"> 입력 </VBtn>
              </VCardText>
            </VCard>
          </VDialog>
        </VCol>
        <VCol cols="auto">
          <VBtn @click="fetchadeleteData">삭제</VBtn>
        </VCol>
      </VRow>
    </VForm>
  </VContainer>
  <VRow>
    <VDataTable
      :headers="headers"
      :items="infodata"
      :pagination="false"
      @click:row="selectRow"
    />
  </VRow>
</template>