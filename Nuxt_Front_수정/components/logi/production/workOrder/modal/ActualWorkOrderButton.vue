<script lang="ts" setup>
import { productionStore } from '@/store/logi/production'
import { ref, defineProps, defineEmits } from 'vue';

const actualWorkData = ref([]);
const isWarnDialogVisible = ref(false);

const propz = defineProps(['DialogData']);
const emits = defineEmits(['closeDialog']);

//실제작업 지시 
const actualWorkOrderClick = async () => {
  
  if (!propz.DialogData || Object.values(propz.DialogData).some((value) => value === '')) {
    isWarnDialogVisible.value = true; //작업을 지시할 행을 선택해주세요! Dialog창 띄우기
  
  } else {
    try {
      const { mrpGatheringNo, workPlaceCode, productionProcessCode } = propz.DialogData;
      await productionStore().FETCH_ACTUAL_WORKORDER_BUTTON(mrpGatheringNo, workPlaceCode, productionProcessCode );
      actualWorkData.value = productionStore().ActualWorkOrderButton;
      
      isWarnDialogVisible.value=false; 
      alert("작업지시가 성공적으로 처리되었습니다.");
    
    } catch (error) {
      console.error('데이터 가져오기 에러:', error);
      actualWorkData.value = [];
      
    }
    isWarnDialogVisible.value = false;
  }
};

const close=()=>{
  emits('closeDialog');
}


</script>

<template>
  <VDialog v-model="isWarnDialogVisible" width="400">
    <!-- Activator -->
    <template #activator="{ props }">
      <VBtn 
        @click="close"
        style="margin-right:15px"
        variant="tonal"
        >
        Close
      </VBtn>

      <VBtn 
          @click="actualWorkOrderClick" 
          v-bind="props"
          > 실제 작업지시
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <!-- <VCard>
      <VCardTitle>실제 작업지시</VCardTitle>

      <div class="demo-space-x">
        <VCardText>
          작업을 지시할 행을 선택해주세요!
        </VCardText>

        <VCol col="5">
          <VBtn @click="isWarnDialogVisible = false">
            OK
          </VBtn>
        </VCol>
      </div>

    </VCard> -->
  </VDialog>
</template>
