<script lang="ts" setup>
import { ref } from 'vue';
import { defineProps, defineEmits } from "vue";
import { productionStore } from '@/store/logi/production'

const isUpdateDialogVisible = ref(false);
const completionAmount = ref(null);

const propz = defineProps(['selectData', 'isUpdateDialogVisible', 'searchWorkOrderStatusClick']);
const emits = defineEmits(['update', 'cancel']);


//작업완료등록 버튼 클릭
const workOrderCompletionClick = () => {
  if (!propz.selectData || Object.keys(propz.selectData).length === 0) {
    // 행을 선택하지 않았을 때
    alert("행을 선택해주세요!");
    isUpdateDialogVisible.value = false; // Dialog를 감춤
  
  } else {
  
    completionAmount.value = propz.selectData.actualCompletionAmount;
    isUpdateDialogVisible.value = true; // Dialog를 보임
  }
}

const update = async () => {
   
  try {
      const workOrderNo = propz.selectData.workOrderNo;
      const actualCompletionAmount = completionAmount.value;

      console.log("Work Order No:", workOrderNo);
      console.log("Actual Completion Amount:", actualCompletionAmount);

    //작업완료된 수량 입력 
    await productionStore().FETCH_EDIT_ACTUAL_AMOUNT({workOrderNo, actualCompletionAmount});  

    //작업완료 등록 
    await productionStore().FETCH_WORKORDER_COMPLETION(workOrderNo, actualCompletionAmount);
     
    if (productionStore().WorkOrderCompletion.errorCode !== 0) {
      alert("작업이 실패하였습니다. 품목을 확인해주세요!"); 
      cancel();
      return;  // 데이터 업데이트 중지
    }

     alert("작업이 성공적으로 완료되었습니다.");

     } catch (error) {
      console.error('데이터 가져오기 에러:', error);
    }

    emits('update');
  cancel();
}


const cancel = () => {
  emits('cancel')
}

</script>


<template>
  <VDialog v-model="isUpdateDialogVisible" width="500">
    
    <!-- Activator -->
    <template #activator="{ props }">
      <VBtn v-bind="props" color="primary" @click="workOrderCompletionClick">
        작업완료 등록
      </VBtn>
    </template>

    <!-- Dialog Content -->
    <VCard title="작업완료된수량">
      <VCardText>
        <VRow>
          <VCol cols="12">
            <AppTextField
              v-model="completionAmount"
              label="지시수량과 동일하게 입력해주세요!"
              :placeholder="'현재 지시수량: ' + propz.selectData.requiredAmount"
              items="type"
            />
          </VCol>
        </VRow>
      </VCardText>

      <VCardText class="d-flex justify-end flex-wrap gap-3">
        <VBtn
          variant="tonal"
          color="primary"
          @click="cancel"
        >
          Cancel
        </VBtn>

        <VBtn @click="update">
          Save
        </VBtn>
      
      </VCardText>
    </VCard>
  </VDialog>

</template>