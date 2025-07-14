<script setup lang="ts">
import OverModal from '@/components/hr/attendance/overModal.vue'
import { attenStore } from '@/store/hr/attendance'
import { baseStore } from '@/store/hr/base'
import type { AttenHeaders, DeptItemProps, EmpItemProps } from '@/types/attendance/types'
import axios from 'axios'
import { VDataTable } from 'vuetify/lib/labs/VDataTable/index.mjs'

const startDate = ref('')
const endDate = ref('')
const selectedEmpCode = ref('')
const elastic_list = ref([])
const selectedDeptCode = ref('')
const dept_list: any = ref([])
const emp_list_by_dept: any = ref([])
const isDialogVisible = ref(false)
const row = ref({})

const elasticProps: AttenHeaders[] = [
  { title: '직원코드', key: 'empCode' },
  { title: '직원명', key: 'empName' },
  { title: '신청일', key: 'applyDay' },
  { title: '시작시간', key: 'startTime' },
  { title: '종료시간', key: 'endTime' },
]

// 페이지가 로드되자마자 실행
onMounted(() => {
  fetchDeptList()
})

// 부서코드가 변경될때마다 함수 실행
watch(selectedDeptCode, async () => {
  await fetchEmpList()
})

// 부서리스트 조회
const fetchDeptList = async () => {
  await baseStore().FETCH_DEPTLIST()
  dept_list.value = Object.values(baseStore().deptList).map((item: DeptItemProps) => {
    return { title: item.deptName, value: item.deptCode }
  })
}

// 부서의 사원 조회
const fetchEmpList = async () => {
  await attenStore().FETCH_EMPLIST_BY_DEPT(selectedDeptCode.value)
  emp_list_by_dept.value = Object.values(attenStore().empList).map((item: EmpItemProps) => {
    return { title: item.empName, value: item.empCode }
  })
}

// 조회 버튼
const fetchElastic = async () => {
  await axios.get('http://localhost:8282/hr/attendance/findElastic', {
    params: {
      empCode: selectedEmpCode.value, startDate: startDate.value, endDate: endDate.value,
    },
  }).then(
    res => {
      console.log('[elasticList]', res.data)
      elastic_list.value = res.data.elasticList
    },
  ).catch(e => { console.error(e) })
}

// 행 클릭
const onClickRow = (event: any, item: any) => {
  isDialogVisible.value = !isDialogVisible.value
  console.log('isDialogVisible.value', isDialogVisible.value)
  console.log('item', item)
  row.value = item.item
}

//============================================================================================

// 모달 함수들
const closeModal = () => {
  isDialogVisible.value = false
}

const updateData = ({ newRow }: any) => {
  console.log('newRow부모', newRow)
  if (newRow.applyDay === '' || newRow.startDate === '' || newRow.endDate === '') {
    alert('모든 항목을 작성해주세요.')
  } else {
    axios.post("http://localhost:8282/hr/attendance/updateElastic", newRow)
      .then(res => {
        console.log(res.data)
        alert('수정이 완료되었습니다. ')
        fetchElastic();
        isDialogVisible.value = !isDialogVisible.value
      }).catch(e => {
        console.error(e)
        alert('다시 시도해 주세요. ')
        isDialogVisible.value = !isDialogVisible.value
      })
  }
}

const deleteData = (delRow: any) => {
  console.log('delRow부모', delRow)

  axios.delete("http://localhost:8282/hr/attendance/deleteElasticOne", { params: { eno: delRow } })
    .then(res => {
      console.log(res.data)
      alert('삭제가 완료되었습니다. ')
      fetchElastic();
      isDialogVisible.value = !isDialogVisible.value
    }).catch(e => {
      console.error(e)
      alert('다시 시도해 주세요. ')
      isDialogVisible.value = !isDialogVisible.value
    })
}
</script>

<template>
  <VContainer class="mb-6">
    <VForm>
      <VRow>
        <VCol cols="12" md="2" offset-md="10">
          <VBtn class="mt-6" height="43" width="152" @click="fetchElastic">
            <VIcon start icon="tabler-search" />조회
          </VBtn>
        </VCol>
      </VRow>
      <VRow>
        <VCol cols="12" md="3">
          <AppSelect v-model="selectedDeptCode" label="부서조회" placeholder="부서조회" :items="dept_list" clearable
            clear-icon="tabler-x" />
        </VCol>
        <VCol cols="12" md="3">
          <AppSelect v-model="selectedEmpCode" label="사원조회" placeholder="사원조회" :items="emp_list_by_dept" clearable
            clear-icon="tabler-x" />
        </VCol>
        <VCol cols="12" md="3">
          <AppDateTimePicker v-model="startDate" label="시작일" placeholder="시작일" />
        </VCol>
        <VCol cols="12" md="3">
          <AppDateTimePicker v-model="endDate" label="종료일" placeholder="종료일" />
        </VCol>
      </VRow>
    </VForm>
    <VRow class="mt-3">
      <VCol>
        <VDataTable :headers="elasticProps" :items="elastic_list" :items-per-page="10" :on-click:row="onClickRow" />
      </VCol>
    </VRow>
  </VContainer>
  <OverModal v-model="isDialogVisible" @closeModal="closeModal" @update-data="updateData" @delete-data="deleteData"
    :row="row" />
</template>
