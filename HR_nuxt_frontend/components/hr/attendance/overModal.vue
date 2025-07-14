<script lang="ts" setup>
const isDialogVisible = ref(false)

// 부모로부터 전달받는 데이터
const propz = defineProps(["isDialogVisible", "row"]);

// 부모로 전달하는 데이터
const emits = defineEmits(["closeModal", "updateData", "deleteData"]);

const closeModal = () => {
  emits("closeModal");
};

const updateData = () => {
  const newRow = {
    empCode: propz.row.empCode,
    applyDay: propz.row.applyDay,
    startTime: propz.row.startTime,
    endTime: propz.row.endTime,
    eno: propz.row.eno
  }
  console.log('newRow', newRow)
  emits("updateData", { newRow })
}

const deleteData = () => {
  console.log('delRow', propz.row.eno)
  emits("deleteData", propz.row.eno)
}

</script>
<template>
  <VDialog v-model="isDialogVisible" max-width="600">

    <!-- Dialog close btn -->
    <DialogCloseBtn @click="closeModal" />

    <!-- Dialog Content -->
    <VCard title="초과근무 신청서 수정">
      <VCardText>
        <VRow>
          <VCol cols="12" sm="6" md="6">
            <AppTextField v-model="propz.row.empName" label="직원명" placeholder="직원명" readonly />
          </VCol>
          <VCol cols="12" sm="6" md="6">
            <AppTextField v-model="propz.row.applyDay" type="date" label="신청일" placeholder="신청일" />
          </VCol>
          <VCol cols="12" sm="6" md="6">
            <AppTextField v-model="propz.row.startTime" label="시작시간" placeholder="시작시간" />
          </VCol>
          <VCol cols="12" sm="6" md="6">
            <AppTextField v-model="propz.row.endTime" label="종료시간" placeholder="종료시간" />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3">
        <VBtn @click="updateData">
          수정
        </VBtn>
        <VBtn @click="deleteData">
          삭제
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
</template>
