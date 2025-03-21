<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import { transStore } from '@/store/logi/transportation'
import AddModal from '@/components/logi/transportationInfo/modal/AddModal.vue'
import UpdateModal from '@/components/logi/transportationInfo/modal/UpdateModal.vue'

const selectedItem: Ref<any[]> = ref([])
const selecteditem = ref('')
const selectedType = ref('')

const isAddDialogVisible = ref(false)
const isUpdateDialogVisible = ref(false)

const items = [
  { title: 'ALL', value: 'ALL' },
  { title: '트럭', value: '트럭' },
  { title: '선박', value: '선박' },
  { title: '비행기', value: '비행기' },
  { title: '열차', value: '열차' },
]

const headers = [
  { title: '차량번호', key: 'vehicleNumber', width: 120 },
  { title: '종류', key: 'type', width: 120 },
  { title: '제조사', key: 'manufacturingCompany', width: 120 },
  { title: '제조년도', key: 'yearOfManufacture', width: 120 },
  { title: '적재용량', key: 'loadCapacity', width: 120 },
  { title: '유지보수날짜', key: 'maintenanceDate', width: 130 },
  { title: '현재운전자', key: 'operator', width: 120 },
  { title: '연료타입', key: 'fuelType', width: 120 },
  { title: '운송수단상태', key: 'transportationStatus', width: 130 },
]

const addClose = () => {
  isAddDialogVisible.value = false
}

const updateClose = () => {
  isUpdateDialogVisible.value = false
}

const transData = ref([])

// 전체 조회
const allTrans = async () => {
  try {
    await transStore().All_TRANSPORTATION_URL()
    transData.value = transStore().allTransInfo
  }
  catch (error) {
    console.error('Error while fetching all transportation data:', error)
  }
}

onMounted(async () => {
  allTrans()
})

// type별 조회
const getTrans = async (type: string) => {
  if (type === 'ALL') {
    await allTrans()
  }
  else {
    await transStore().TRANSPORTATION_URL(type)

    const allData = transStore().getTransInfo

    transData.value = allData.filter(item => item.type === type)
  }
}

const selectType = (type: string) => {
  selectedType.value = type
  getTrans(selectedType.value)
}

// 추가
const add = async (AddList: object) => {
  console.log('ruru', AddList)

  await transStore().ADD_TRANSPORTATION_URL(AddList)

  isAddDialogVisible.value = false

  await getTrans(AddList.type)
}

// 한 item 클릭 시 vehicleNumber 추출
watch(selectedItem, (selectedValue: any) => {
  // console.log(selectedValue)
  // console.log(toRaw(selectedValue))
  // console.log(toRaw(selectedValue).length) // 1
  // console.log(toRaw(selectedValue[0]))
  // console.log(toRaw(selectedValue[0]))

  // console.log(toRaw(selectedValue[0].customerCode))
  if (toRaw(selectedValue.length) === 0)
    selecteditem.value = ''
  else
    selecteditem.value = toRaw(selectedValue[0])
  console.log(selecteditem.value)
})

// 수정
const update = async (transList: any) => {
  console.log('qqqq', transList)
  console.log('vehicleNumber', transList.vehicleNumber)

  await transStore().UPDATE_TRANSPORTATION_URL(transList)

  isUpdateDialogVisible.value = false

  await getTrans(transList.type)
}

// 삭제
const deleteItem = async () => {
  console.log('del', selecteditem.value.vehicleNumber)

  if (!selecteditem.value.vehicleNumber) {
    console.error('No workplace selected for deletion')

    return
  }
  const vehicleNumber = selecteditem.value.vehicleNumber

  await transStore().DELETE_TRANSPORTATION_URL(vehicleNumber)

  allTrans()
}
</script>

<template>
  <VCard>
    <div
      class="text-2xl"
      style="margin-left: 20px; margin-top: 20px"
    >
      <p> 운송수단관리</p>

      <div class="d-flex justify-end mt-4 mr-8">
        <VMenu open-on-hover>
          <template #activator="{ props }">
            <VBtn v-bind="props">
              운송수단 조회
            </VBtn>
          </template>

          <VList>
            <VListItem
              v-for="item in items"
              :key="item.value"
              @click="selectType(item.value)"
            >
              <VListItemTitle>{{ item.title }}</VListItemTitle>
            </VListItem>
          </VList>
        </VMenu>

        <!--
          <VBtn
          style="margin-left: 20px;"
          class="button"
          color="primary"
          >
          추가
          </VBtn>
        -->
        <AddModal
          v-model="isAddDialogVisible"
          @close="addClose"
          @add="add"
        />

        <!--
          <VBtn
          style="margin-left: 20px;"
          class="button"
          color="primary"
          >
          수정
          </VBtn>
        -->
        <UpdateModal
          v-model="isUpdateDialogVisible"
          :selecteditem="selecteditem"
          @updateClose="updateClose"
          @update="update"
        />
        <VBtn
          style="margin-left: 20px;"
          class="button"
          color="primary"
          @click="deleteItem"
        >
          운송수단 삭제
        </VBtn>
      </div>
    </div>

    <VCardText>
      <VDataTable
        v-model="selectedItem"
        :headers="headers"
        :items="transData"
        :items-per-page="10"
        show-select
        select-strategy="single"
        return-object
      />
    </VCardText>
  </VCard>
</template>
