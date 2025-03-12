<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable'
import AbroadAddModal from '@/pages/logistic/abroad/abroadSchedule/modal/AbroadAddModal.vue' 
import AbroadUpdateModal from '@/pages/logistic/abroad/abroadSchedule/modal/AbroadUpdateModal.vue' 
import { abroadStore } from '@/store/logi/abroad'

const isDialogVisible = ref(false)
const abroadScheduleInfodata: Ref<any[]> = ref([])
const selectedAbroadSchedule = ref([])
const isUpdateDialogVisible = ref(false);

const eventDetaillistheaders = [
  { title: '행사코드', key: 'abroadScheduleCode' },
  { title: '행사명', key: 'eventName' },
  { title: '행사유형', key: 'eventType' },
  { title: '위치', key: 'eventLocation' },
  { title: '날짜', key: 'eventDate' },
  { title: '설명', key: 'eventExplanation' },
  { title: '대상', key: 'eventTarget' },
  { title: '주최자', key: 'eventHost' },
  { title: '상태', key: 'eventState' },
]

// 행사 조회
const fetchData = async () => {
  await abroadStore().GET_ABROADSCHEDULE_INFO()
  abroadScheduleInfodata.value = abroadStore().abroadScheduleInfo
}

onMounted(async () => {
  fetchData()
})

const cancelModal = () => {
  isDialogVisible.value = false
}

// 추가하고 저장하기
const saveList = async (menudata: any) => {
  try {
    console.log('ruru', menudata)

    await abroadStore().ADD_ABROADSCHEDULE_INFO(menudata)

    abroadScheduleInfodata.value = abroadStore().addAbroadScheduleInfo

    isDialogVisible.value = false

    fetchData()
  }
  catch (error) {
    console.error('Error fetching data:', error)

    return []
  }
  isDialogVisible.value = false
}

// 한 행 클릭시 사업장정보 나오게하기
const list = async (code: any, row: { item: { abroadScheduleCode: any } }) => {
  console.log('row', row.item)
  try {
    selectedAbroadSchedule.value = row.item

    const abroadScheduleCode = row.item.abroadScheduleCode

    console.log('row', abroadScheduleCode)

  }
  catch (error) {
    console.error('Error fetching data:', error)

    return []
  }
}

// 한 행 클릭 시 삭제
const deleteSelectedAbroadSchedule = async () => {
  if (!selectedAbroadSchedule.value) {
    console.error('No workplace selected for deletion')

    return
  }

  const confirmDelete = window.confirm('행사를 삭제하시겠습니까?')

  if (confirmDelete) {
    const abroadScheduleCode = selectedAbroadSchedule.value.abroadScheduleCode

    try {
      await abroadStore().DELETE_ABROADSCHEDULE_INFO(abroadScheduleCode)
      fetchData()
      alert('행사가 성공적으로 삭제되었습니다.')
    }
    catch (error) {
      console.error('Error deleting workplace:', error)
      alert('행사 삭제에 실패했습니다.')
    }
  }
}

const saveButton = () => {
  isUpdateDialogVisible.value = false;
  alert('행사가 성공적으로 수정되었습니다.');
  fetchData();

}
</script>

<template>
  <p class="text-2xl">
    행사 정보
  </p>
  <div class="demo-space-x">
    
      <!-- <VBtn color="primary">
      행사 추가
      </VBtn> -->
   
    <AbroadAddModal
      v-model="isDialogVisible"
      @saveList="saveList"
      @cancelModal="cancelModal"
    />

      <!-- <VBtn color="primary">
      행사 수정
      </VBtn> -->
   
    <AbroadUpdateModal
      v-model="isUpdateDialogVisible"
      :selectedAbroadSchedule="selectedAbroadSchedule"
      @update="saveButton"
    />

    <VBtn
      color="primary"
      @click="deleteSelectedAbroadSchedule"
    >
      행사 삭제
    </VBtn>
    <div style="margin-bottom: 70px" />

    <VRow>
      <VCol cols="12">
        <!-- 👉 행사 목록 -->
        <AppCardActions title="행사 목록">
          <VCardText>
            <VDataTable
              :headers="eventDetaillistheaders"
              :items="abroadScheduleInfodata"
              :items-per-page="5"
              @click:row="list"
            />
          </VCardText>
        </AppCardActions>
      </VCol>
    </VRow>
  </div>
</template>
