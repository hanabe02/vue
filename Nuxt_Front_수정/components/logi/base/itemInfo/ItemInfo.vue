<script lang="ts" setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import ItemAddModal from './modal/ItemAddModal.vue'
import ItemUpdateModal from './modal/ItemUpdateModal.vue'
import { itemStore } from '@/store/logi/item'

const isAddDialogVisible = ref(false)
const isUpdateDialogVisible = ref(false)

const selectedItem: Ref<any[]> = ref([])
const selecteditem = ref('')

const codeheaders = [
  { title: '품목코드', key: 'itemCode' },
  { title: '품목명', key: 'itemName' },
  { title: '품목군코드', key: 'itemGroupCode' },
  { title: '품목분류', key: 'itemClassification' },
  { title: '단위', key: 'unitOfStock' },
  { title: '손실율', key: 'lossRate' },
  { title: '소요일', key: 'leadTime' },
  { title: '표준단가', key: 'standardUnitPrice' },
  { title: '비고', key: 'description' },
]

const itemData = ref([])

const close = async () => {
  isAddDialogVisible.value = false
}

const cancel = async () => {
  isUpdateDialogVisible.value = false
}

// 조회
const search = async () => {
  await itemStore().GET_ITEM_INFO()
  itemData.value = itemStore().getItemInfo
}

// 품목 추가
const save = async (batchList: object) => {
  console.log('ruru', batchList)

  await itemStore().ADD_ITEM_INFO(batchList)
  itemData.value = itemStore().addItemInfo

  // console.log('lala', response.config.data)

  close()

  search()
}

// 한 item 클릭 시 itemCode 추출
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

// 품목 수정
const update = async (batchList: any) => {
  console.log('qqqq', batchList)

  await itemStore().UPDATE_ITEM_INFO(batchList)

  cancel()

  search()
}

// 품목 삭제
const deleteItem = async () => {
  console.log('ㅅㅅㅅㅅ', selecteditem.value)
  console.log('xzzzz', selecteditem.value.itemCode)

  if (!selecteditem.value.itemCode) {
    console.error('No workplace selected for deletion')

    return
  }
  const userConfirmed = window.confirm('정말로 품목을 삭제하시겠습니까?')

  if (userConfirmed) {
    const itemCode = selecteditem.value.itemCode

    // 삭제 API 호출
    await itemStore().DELETE_ITEM_INFO(itemCode)

    // 삭제 후 다시 검색 등 필요한 작업 수행
    search()
  }
  else {
    console.log('품목 삭제 취소됨')
  }
}
</script>

<template>
  <VCard>
    <div
      class="text-2xl"
      style="margin-left: 20px; margin-top: 20px"
    >
      <p> 품목 관리</p>
    </div>

    <div style="margin-left: 20px; margin-top: 20px">
      <VBtn
        class="button"
        color="primary"
        @click="search"
      >
        품목 조회
      </VBtn>
      <!--
        <VBtn
        class="button"
        color="primary"
        >
        추가
        </VBtn>
      -->
      <ItemAddModal
        v-model="isAddDialogVisible"
        @close="close"
        @save="save"
      />
      <!--
        <VBtn
        class="button"
        color="primary"
        >
        수정
        </VBtn>
      -->
      <ItemUpdateModal
        v-model="isUpdateDialogVisible"
        :selecteditem="selecteditem"
        @cancel="cancel"
        @update="update"
      />
      <VBtn
        class="button"
        color="primary"
        @click="deleteItem"
      >
        품목 삭제
      </VBtn>
    </div>

    <VCardText>
      <VDataTable
        v-model="selectedItem"
        :headers="codeheaders"
        :items="itemData"
        :items-per-page="5"
        show-select
        select-strategy="single"
        return-object
      />
    </VCardText>
  </VCard>
</template>
