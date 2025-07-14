<script lang="ts" setup>
import type { AttenProps, ItemPropsTypes } from "@/types/attendance/types"; // `type` 키워드를 사용해 타입만 가져옵니다.
import { defineEmits, defineProps } from "vue";
// leave

const isDialogVisible = ref(false)
const selectedRest = ref('')
const selectedRestCode = ref('')
const selectedRestName = ref('')


const leaveProps: AttenProps = [
  { text: '공가', value: 'ASC004' },
  { text: '연차', value: 'ASC005' },
  { text: '오전반차', value: 'ASC006' },
  { text: '오후반차', value: 'ASC007' },
]

const eduProps: AttenProps = [
  { text: '출장', value: 'ASC002' },
  { text: '교육', value: 'ASC003' },
]

// select component에 사용되는 함수
const itemProps = (item: ItemPropsTypes) => {
  return {
    title: item.text,
    value: item.value,
  }
}

const restProps: AttenProps = [
  { text: '조퇴', value: 'DAC004' },
  { text: '외출', value: 'ADC006' },
]

// leaveProps, eduProps, restProps가 변할때마다 text, value를 담음
const handleSelectedRestChange = (selectedProps: any) => (newValue: any, oldValue: any) => {
  const selectedItem = selectedProps.find((item: any) => item.value === newValue)
  if (selectedItem) {
    selectedRestName.value = selectedItem.text
    selectedRestCode.value = selectedItem.value
    console.log('Selected Title:', selectedRestName.value)
    console.log('Selected value:', selectedRestCode.value)
  }
}

watch(selectedRest, handleSelectedRestChange(leaveProps))
watch(selectedRest, handleSelectedRestChange(eduProps))
watch(selectedRest, handleSelectedRestChange(restProps))

// 부모로부터 전달받는 데이터
const propz = defineProps(["isDialogVisible", "row", "modalStatus"]);

// 부모로 전달하는 데이터
const emits = defineEmits(["closeModal", "updateData", "deleteData"]);

const closeModal = () => {
  emits("closeModal");
};

const updateData = () => {
  const newRow = {
    requestDate: propz.row.requestDate,
    restTypeName: selectedRestName.value,
    restTypeCode: selectedRestCode.value,
    startDate: propz.row.startDate,
    endDate: propz.row.endDate,
    startTime: propz.row.startTime,
    endTime: propz.row.endTime,
    applovalStatus: propz.row.applovalStatus,
    numberOfDays: numberOfDays.value,
    cause: propz.row.cause,
    empCode: propz.row.empCode,
    restAttdCode: propz.row.restAttdCode
  }
  console.log('newRow', newRow)
  emits("updateData", { newRow })
}

const deleteData = () => {
  console.log('newRow', propz.row.restAttdCode)
  emits("deleteData", propz.row.restAttdCode)
}



// computed를 사용하여 numberOfDays 계산
const numberOfDays = computed(() => {
  if (propz.row.endDate && propz.row.startDate) {
    const diffDate = new Date(propz.row.endDate).getTime() - new Date(propz.row.startDate).getTime()
    const dayDiff = diffDate / (1000 * 60 * 60 * 24) + 1

    if (propz.row.endDate === propz.row.startDate)
      return 0.5
    else
      return dayDiff
  }
  else {
    return 0
  }
})

</script>

<template>
  <VDialog v-model="isDialogVisible" max-width="600">

    <!-- Dialog close btn -->
    <DialogCloseBtn @click="closeModal" />

    <!-- Dialog Content -->
    <VCard :title="`${modalStatus === 'traEdu' ? '출장/교육' : modalStatus === 'leave' ? '휴가' : '근태외'} 신청서 수정/삭제`">
      <VCardText>
        <VRow>
          <VCol cols="12" sm="6">
            <AppTextField v-model="propz.row.requestDate" label="신청일" placeholder="신청일" readonly />
          </VCol>
          <VCol cols="12" sm="6">
            <AppSelect v-model="selectedRest" label="신청유형" :placeholder=propz.row.restTypeName :items="leaveProps"
              :item-props="itemProps" clearable clear-icon="tabler-x" v-if="modalStatus === 'leave'" />
            <AppSelect v-model="selectedRest" label="신청유형" :placeholder=propz.row.restTypeName :items="eduProps"
              :item-props="itemProps" clearable clear-icon="tabler-x" v-if="modalStatus === 'traEdu'" />
            <AppSelect v-model="selectedRest" label="신청유형" :placeholder=propz.row.restTypeName :items="restProps"
              :item-props="itemProps" clearable clear-icon="tabler-x" v-if="modalStatus === 'rest'" />
          </VCol>
          <VCol cols="12" sm="6">
            <AppTextField v-model="propz.row.startDate" type="date" label="시작일" placeholder="시작일" />
          </VCol>
          <VCol cols="12" sm="6">
            <AppTextField v-model="propz.row.endDate" type="date" label="종료일" placeholder="종료일" />
          </VCol>
          <VCol cols="12" sm="6">
            <AppTextField v-model="propz.row.startTime" label="시작시간" placeholder="시작시간" />
          </VCol>
          <VCol cols="12" sm="6">
            <AppTextField v-model="propz.row.endTime" label="종료시간" placeholder="종료시간" />
          </VCol>
          <VCol cols="12" sm="6">
            <AppTextField v-model="propz.row.applovalStatus" label="결제상태" placeholder="결제상태" readonly />
          </VCol>
          <VCol cols="12" sm="6">
            <AppTextField v-model="numberOfDays" label="일수" type="number" placeholder="일수" readonly />
          </VCol>
          <VCol cols="12" sm="">
            <AppTextarea v-model="propz.row.cause" label="사유" placeholder="사유를 입력해주세요." />
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
