<script setup lang="ts">
import AttenModal from '@/components/hr/attendance/attenModal.vue';
import { attenStore } from '@/store/hr/attendance';
import { baseStore } from '@/store/hr/base';
import type { AttenHeaders, DeptItemProps, EmpItemProps } from '@/types/attendance/types';
import axios from 'axios';
import { VDataTable } from 'vuetify/lib/labs/VDataTable/index.mjs';

const selectedDeptCode = ref('')
const selectedEmpCode = ref('')
const dept_list: any = ref([])
const emp_list_by_dept: any = ref([])
const tarvel_and_education_list = ref([])
const startDate = ref('')
const endDate = ref('')
const isDialogVisible = ref(false)
const row = ref({})
const modalStatus = ref('')

const RestAttdProps: AttenHeaders[] = [
  // { title: "사원명", key: "empName", width: 90 },
  { title: '구분', key: 'restTypeName', width: 120 },
  { title: '신청일자', key: 'requestDate', width: 130 },
  { title: '시작일', key: 'startDate', width: 130 },
  { title: '종료일', key: 'endDate', width: 130 },
  { title: '사유', key: 'cause', width: 100 },
  { title: '시작시간', key: 'startTime', width: 130 },
  { title: '종료시간', key: 'endTime', width: 130 },
  { title: '일수', key: 'numberOfDays', width: 80 },
  { title: '승인상태', key: 'applovalStatus', width: 120 },
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

// 내역 조회
const fetchRestAttdList = async () => {
  await attenStore().SEARCH_TRAVEL_AND_EDUCATION_LIST({ empCode: selectedEmpCode.value, startDate: startDate.value, endDate: endDate.value })
  tarvel_and_education_list.value = attenStore().travelAndEducationList
  console.log(attenStore().restAttdList)
}

// 행 클릭
const onClickRow = (event: any, item: any) => {
  modalStatus.value = 'traEdu'
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
  console.log('수정api전달하자')
  if (newRow.restTypeName === '') {
    alert('출장/교육 유형을 선택해주세요.')
  } else {
    axios.put("http://localhost:8282/hr/attendance/updateVacation", newRow)
      .then(res => {
        console.log(res.data)
        alert('수정이 완료되었습니다. ')
        fetchRestAttdList();
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

  axios.delete("http://localhost:8282/hr/attendance/removeRestAttd", { params: { restAttdCode: delRow } })
    .then(res => {
      console.log(res.data)
      alert('삭제가 완료되었습니다. ')
      fetchRestAttdList();
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
    <VRow>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="startDate" label="시작일" placeholder="시작일" />
      </VCol>
      <VCol cols="12" md="2">
        <AppDateTimePicker v-model="endDate" label="종료일" placeholder="종료일" />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedDeptCode" label="부서조회" placeholder="부서조회" :items="dept_list" clearable
          clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="2">
        <AppSelect v-model="selectedEmpCode" label="사원조회" placeholder="사원조회" :items="emp_list_by_dept" clearable
          clear-icon="tabler-x" />
      </VCol>
      <VCol cols="12" md="2" offset-md="">
        <VBtn class="mt-6 ml-5" height="43" width="152" @click="fetchRestAttdList">
          <VIcon start icon="tabler-search" />내역조회
        </VBtn>
      </VCol>
    </VRow>
  </VContainer>
  <VDataTable :headers="RestAttdProps" :items="tarvel_and_education_list" :items-per-page="10"
    :on-click:row="onClickRow" />
  <AttenModal v-model="isDialogVisible" @closeModal="closeModal" @update-data="updateData" @delete-data="deleteData"
    :row="row" :modalStatus="modalStatus" />
</template>
