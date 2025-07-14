<script setup lang="ts">
import axios from 'axios'
import { VDataTable } from 'vuetify/labs/VDataTable'
import CodeAddModal from './CodeAddModal.vue'
import CodeUpdateModal from './CodeUpdateModal.vue'
import { codeStore } from '@/store/logi/code'

const isAddDialogVisible = ref(false)
const isUpdateDialogVisible = ref(false)

const selectedItem: Ref<any[]> = ref([])
const selecteditem = ref('')

const codeheaders = [
  { title: '코드번호', key: 'divisionCodeNo' },
  { title: '코드타입', key: 'codeType' },
  { title: '코드이름', key: 'divisionCodeName' },
  { title: '변경가능여부', key: 'codeChangeAvailable' },
  { title: '설명', key: 'description' },
]

const close = async () => {
  isAddDialogVisible.value = false
}

const cancel = async () => {
  isUpdateDialogVisible.value = false
}

const codeData = ref([])

// 코드 조회
const search = async () => {
  await codeStore().GET_CODE_INFO()
  codeData.value = codeStore().getCodeInfo
}

// 코드 추가
const save = async (codelist: object) => {
  console.log('ruru', codelist)

  await codeStore().ADD_CODE_INFO(codelist)

  codeData.value = codeStore().addCodeInfo

  // console.log('lala', response.config.data)

  close()

  search()
}

// 한 item 클릭 시 코드번호 추출
watch(selectedItem, (selectedValue: any) => {
  console.log(selectedValue)
  console.log(toRaw(selectedValue))
  console.log(toRaw(selectedValue).length) // 1
  console.log(toRaw(selectedValue[0]))
  console.log(toRaw(selectedValue[0]))

  // console.log(toRaw(selectedValue[0].customerCode))
  if (toRaw(selectedValue.length) === 0)
    selecteditem.value = ''
  else
    selecteditem.value = toRaw(selectedValue[0])
  console.log(selecteditem.value)
})

const update = async () => {
  search()
}

// 삭제
const deleteCode = async () => {
  console.log('ㅅㅅㅅㅅ', selecteditem.value)
  console.log('xzzzz', selecteditem.value.divisionCodeNo)

  if (!selecteditem.value.divisionCodeNo) {
    console.error('No workplace selected for deletion')

    return
  }
  const divisionCodeNo = selecteditem.value.divisionCodeNo

  await codeStore().DELETE_CODE_INFO(divisionCodeNo)

  search()
}
</script>

<template>
  <div>
    <div class="text-2xl mb-2">
      <p> 코드관리</p>
    </div>

    <VCard>
      <div
        class="text-2xl mb-6"
        style="margin-left: 20px; margin-top: 20px"
      >
        <VBtn
          class="button"
          color="primary"
          @click="search"
        >
          코드 조회
        </VBtn>
        <!--
          <VBtn
          style="margin-left: 10px;"
          class="button"
          color="primary"
          >
          추가
          </VBtn>
        -->
        <CodeAddModal
          v-model="isAddDialogVisible"
          @save="save"
          @close="close"
        />
        <!--
          <VBtn
          style="margin-left: 10px;"
          class="button"
          color="primary"
          >
          수정
          </VBtn>
        -->
        <CodeUpdateModal
          v-model="isUpdateDialogVisible"
          :selecteditem="selecteditem"
          @update="update"
          @cancel="cancel"
        />
        <VBtn
          style="margin-right: 20px;"
          class="button"
          color="primary"
          @click="deleteCode"
        >
          코드 삭제
        </VBtn>
      </div>
  
      <VCardText>
        <VDataTable
          v-model="selectedItem"
          :headers="codeheaders"
          :items="codeData"
          :items-per-page="5"
          show-select
          select-strategy="single"
          return-object
        />
      </VCardText>
    </VCard>
  </div>
</template>
