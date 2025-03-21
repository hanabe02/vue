<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import { defineEmits, defineProps, ref } from 'vue'

import axios from 'axios'

const propz = defineProps(['isDialogVisible', 'infodata', 'selectRow'])
const emits = defineEmits(['closeModal', 'modifyCom'])
const selectData = ref([])

const select = [
  { title: 'CT-01|수주일반', sortable: false, key: 'CT-01' },
  { title: 'CT-02|긴급수주', key: 'CT-02' },
]

const selectStatus = ref('')

const closeModal = () => {
  emits('closeModal')
}

const openModal = () => {
  // 문제 selectRow 가 객체 형태일 때 오류가 발생한다.
  console.log("✅ openModal 함수 실행됨!");
  console.log('selectRow', propz.selectRow[0]);

  const targetWarehouseCode = propz.selectRow[0];

  console.log('targetWarehouseCode', targetWarehouseCode);
  console.log('infodata', propz.infodata);

  const targetObject = propz.infodata.find(obj => obj.estimateNo === targetWarehouseCode);
  /*
    find() : 배열에서 특정 조건을 만족하는 첫 번째 요소를 반환하는 메서드이다.
    propz.infodata 배열을 순회하면서 obj.estimateNo가 targetWarehouseCode 와 같은 객체를 찾는다.
  */

  console.log('modalselectRow', targetObject);
  selectData.value = targetObject;
}

const modifyCom = () => {
  if (selectStatus.value && selectData.value) {
    const statusTitle = selectStatus.value.title.split('|')[0]; // '|' 문자로 분리하여 첫 번째 부분만 사용
    selectData.value.contractStatus = statusTitle;
    emits('modifyCom');
  } else {
    console.error('selectStatus or selectData is not set correctly');
  }
};

</script>

<template>
  <VDialog
    v-model="propz.isDialogVisible"
    max-width="600"
  >
    <!-- Dialog Activator -->
    <template #activator="{ props }">
      <VBtn
        v-bind="props"
        @click="openModal"
      >
        수주 수정
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="수주 수정">
      <VCardText>
        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="selectData.estimateNo"
              label="견적 일련번호"
              placeholder="창고 코드"
              disabled
            />
          </VCol>
        </VRow>
        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppCombobox
              v-model="selectStatus"
              :items="select"
              label="수주유형"
              clearable
            />
          </VCol>
        </VRow>
        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="selectData.customerCode"
              label="거래처명"
              persistent-hint
              placeholder="거래처명"
              disabled
            />
          </VCol>
        </VRow>
        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <div />
            <AppTextField
              v-model="selectData.contractRequester"
              label="* 수주요청자"
              persistent-hint
              placeholder="수주요청자"
            />
          </VCol>
        </VRow>

        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="selectData.estimateDate"
              label="견적일자"
              persistent-hint
              placeholder="견적일자"
              disabled
            />
          </VCol>
        </VRow>
        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="selectData.effectiveDate"
              label="유효일자"
              persistent-hint
              placeholder="유효일자"
              disabled
            />
          </VCol>
        </VRow>
        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="selectData.estimateRequester"
              label="견적담당자명"
              persistent-hint
              placeholder="견적담당자명"
              disabled
            />
          </VCol>
        </VRow>
        <VRow>
          <VCol
            cols="12"
            sm="6"
            md="4"
          >
            <AppTextField
              v-model="selectData.description"
              label="비고"
              persistent-hint
              placeholder="비고"
            />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3">
        <VBtn
          variant="tonal"
          color="secondary"
          @click="closeModal"
        >
          취소
        </VBtn>
        <VBtn @click="modifyCom">
          수정 완료
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
</template>
