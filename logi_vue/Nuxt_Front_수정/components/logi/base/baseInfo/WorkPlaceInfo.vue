<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable'
import WorkPlaceAddModal from './modal/WorkPlaceAddModal.vue'
import WorkPlaceUpdateModal from './modal/WorkPlaceUpdateModal.vue'
import { baseStore } from '@/store/logi/base'

const isDialogVisible = ref(false)
const workplaceInfodata: Ref<any[]> = ref([])
const workplaceinfodatadetail: Ref<any[]> = ref([])
const selectedWorkplace = ref(null)

const workplacelistheaders = [
  { title: '사업장 코드', key: 'workplaceCode' },
  { title: '회사코드', key: 'companyCode' },
  { title: '사업장명', key: 'workplaceName' },
  { title: '사업장등록번호', key: 'businessLicenseNumber' },
  { title: '법인등록번호', key: 'corporationLicenseNumber' },
  { title: '대표자명', key: 'workplaceCeoName' },
  { title: '업태', key: 'workplaceBusinessConditions' },
  { title: '종목', key: 'workplaceBusinessItems' },
]

const workplacedetaillistheaders = [
  { title: '사업장우편번호', sortable: false, key: 'workplaceZipCode' },
  { title: '사업장기본주소', key: 'workplaceBasicAddress' },
  { title: '사업장세부주소', key: 'workplaceDetailAddress' },
  { title: '사업장전화번호', key: 'workplaceTelNumber' },
  { title: '사업장팩스번호', key: 'workplaceFaxNumber' },
  { title: '사업장개업년월일', key: 'workplaceEstablishDate' },
  { title: '사업장설립년월일', key: 'workplaceOpenDate' },
  { title: '사업장폐업년월일', key: 'workplaceCloseDate' },
  { title: '본점여부', key: 'approvalStatus' },
]

// 사업장 조회
const fetchData = async () => {
  await baseStore().GET_WORKPLACE_INFO()
  workplaceInfodata.value = baseStore().workplaceInfo
}

onMounted(async () => {
  fetchData()
})

// 한 행 클릭시 사업장정보 나오게하기
const list = async (code: any, row: { item: { workplaceCode: any } }) => {
  console.log('row', row.item)
  try {
    selectedWorkplace.value = row.item

    const workplaceCode = row.item.workplaceCode

    console.log('row', workplaceCode)

    await baseStore().GET_WORKPLACE_DETAIL_INFO(workplaceCode)

    workplaceinfodatadetail.value = baseStore().workplaceDetailInfo

    console.log(workplaceinfodatadetail.value)
  }
  catch (error) {
    console.error('Error fetching data:', error)

    return []
  }
}

const cancelModal = () => {
  isDialogVisible.value = false
}

// 추가하고 저장하기
const saveList = async (menudata: any) => {
  try {
    console.log('ruru', menudata)

    await baseStore().ADD_WORKPLACE_INFO(menudata)

    workplaceInfodata.value = baseStore().addWorkplaceInfo

    isDialogVisible.value = false

    fetchData()
  }
  catch (error) {
    console.error('Error fetching data:', error)

    return []
  }
  isDialogVisible.value = false
}

// 한 행 클릭 시 삭제
const deleteSelectedWorkplace = async () => {
  if (!selectedWorkplace.value) {
    console.error('No workplace selected for deletion')

    return
  }

  const confirmDelete = window.confirm('사업장을 삭제하시겠습니까?')

  if (confirmDelete) {
    const workplaceCode = selectedWorkplace.value.workplaceCode

    try {
      await baseStore().DELETE_WORKPLACE_INFO(workplaceCode)
      fetchData()
      alert('사업장이 성공적으로 삭제되었습니다.')
    }
    catch (error) {
      console.error('Error deleting workplace:', error)
      alert('사업장 삭제에 실패했습니다.')
    }
  }
}
</script>

<template>
  <p class="text-2xl">
    사업장 정보
  </p>
  <div class="demo-space-x">
    <!--
      <VBtn color="primary">
      사업장 추가
      </VBtn>
    -->
    <WorkPlaceAddModal
      v-model="isDialogVisible"
      @saveList="saveList"
      @cancelModal="cancelModal"
    />

    <!--
      <VBtn color="primary">
      사업장 수정
      </VBtn>
    -->
    <WorkPlaceUpdateModal
      :selected-workplace="selectedWorkplace"
      @update="fetchData"
    />

    <VBtn
      color="primary"
      @click="deleteSelectedWorkplace"
    >
      사업장 삭제
    </VBtn>
    <div style="margin-bottom: 70px" />

    <VRow>
      <VCol cols="12">
        <!-- 👉 사업장 목록 -->
        <AppCardActions title="사업장 목록">
          <VCardText>
            <VDataTable
              :headers="workplacelistheaders"
              :items="workplaceInfodata"
              :items-per-page="5"
              @click:row="list"
            />
          </VCardText>
        </AppCardActions>
      </VCol>

      <VCol cols="12">
        <!-- 👉 사업장 정보 -->
        <AppCardActions title="사업장 정보">
          <VCardText>
            <VDataTable
              :headers="workplacedetaillistheaders"
              :items="workplaceinfodatadetail"
              :items-per-page="5"
            />
          </VCardText>
        </AppCardActions>
      </VCol>
    </VRow>
  </div>
</template>
