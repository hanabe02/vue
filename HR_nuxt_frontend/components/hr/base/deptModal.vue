<script lang="ts" setup>
import { baseStore } from '@/store/hr/base';
import { defineProps } from 'vue';
import { VDataTable } from 'vuetify/labs/VDataTable';

const props = defineProps(['fetchData'])

const Headers = [
  { title: '사원코드', key: 'empCode' },
  { title: '사원명', key: 'empName' },
  { title: '고용일', key: 'hireDate' },
  { title: '직급', key: 'positionName' },
]

const deptList = computed(() => {
  return baseStore().deptRow
})

const editDept = async () => {
  const editDeptData = {
    deptName: deptList.value.deptName,
    deptCode: deptList.value.deptCode,
    workplaceCode: deptList.value.workplaceCode,
  }

  console.log(editDeptData)
  await baseStore().MODIFY_DEPT(editDeptData)
  await props.fetchData()
  baseStore().SET_DIALOG(false)
}

const deleteDept = async () => {
  try {
    const payload = {
      deptCode: deptList.value.deptCode,
      workplaceCode: deptList.value.workplaceCode,
    }

    console.log(payload)
    await baseStore().REMOVE_DEPT(payload)
    await props.fetchData()
    baseStore().SET_DIALOG(false)
  }
  catch (err) {
    console.error(err)
    alert('부서 삭제 중 오류가 발생했습니다.')
  }
}

const empList = ''
</script>

<template>
  <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
    <VDialog v-model="baseStore().isDialog" max-width="600">
      <!-- Dialog close btn -->
      <DialogCloseBtn @click="baseStore().SET_DIALOG(false)" />

      <!-- Dialog Content -->
      <VCard title="부서정보">
        <VCardText>
          <VRow>
            <VCol cols="12" sm="6">
              <AppTextField v-model="deptList.deptCode" label="부서 코드" disabled />
            </VCol>
            <VCol cols="12" sm="6">
              <AppTextField v-model="deptList.deptName" label="부서명" />
            </VCol>
          </VRow>
        </VCardText>

        <VDataTable :headers="Headers" :items="deptList.empList" />

        <VCardText class="d-flex justify-end flex-wrap gap-3 mt-2">
          <VBtn @click="editDept">
            수정
          </VBtn>
          <VBtn @click="deleteDept">
            삭제
          </VBtn>
        </VCardText>
      </VCard>
    </VDialog>
  </div>
  <VCard />
</template>
