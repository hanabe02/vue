<script lang="ts" setup>
import { attenStore } from '@/store/hr/attendance';
import { baseStore } from '@/store/hr/base';
import axios from 'axios';

const dept_list2: any = ref([])
const emp_list_by_dept2: any = ref([])

const newSelectedDeptCode = ref('')
const newSelectedEmpCode = ref('')
const newRequestDate = ref('')
const newCategory = ref('')
const newUsageName = ref('')
const newEtc = ref('')

const items = ['재직증명서', '경력증명서', '급여명세서']

const use = [
  { title: '금융기관 제출용', value: 'ELM001' },
  { title: '관공서 제출용', value: 'ELM002' },
  { title: '회사 제출용', value: 'ELM003' },
  { title: '기타 제출용', value: 'ELM004' },
]

// 페이지가 로드되자마자 실행
onMounted(() => {
  fetchDeptList2()
})

watch(newSelectedDeptCode, () => {
  fetchEmpList2()
})

// 부서리스트 조회2
const fetchDeptList2 = async () => {
  await baseStore().FETCH_DEPTLIST()
  dept_list2.value = Object.values(baseStore().deptList).map((item: DeptItemProps) => {
    return { title: item.deptName, value: item.deptCode }
  })
}

// 부서의 사원 조회2
const fetchEmpList2 = async () => {
  await attenStore().FETCH_EMPLIST_BY_DEPT(newSelectedDeptCode.value)
  emp_list_by_dept2.value = Object.values(attenStore().empList).map((item: EmpItemProps) => {
    return { title: item.empName, value: item.empCode }
  })
}

const newCert = async () => {
  const newCert = {
    empCode: newSelectedEmpCode.value,
    requestDate: newRequestDate.value,
    category: newCategory.value,
    usageCode: newUsageName.value,
    etc: newEtc.value,
  }

  console.log(newCert)
  await axios.post('http://localhost:8282/hr/certificate/insertCertificateRequest', newCert,
  ).then(res => {
    console.log(res.data)
    alert('신규 신청이 등록되었습니다.')
    attenStore().SET_DIALOG_BTN(false)
  }).catch(e => console.error(e))
}
</script>

<template>
  <!-- 다이얼로그 -->
  <div class="text-center">
    <VDialog v-model="attenStore().isDialogBtn" width="300">
      <VCard>
        <VCardText>
          신청 정보를 입력해주세요.
        </VCardText>
        <VContainer>
          <AppSelect v-model="newSelectedDeptCode" label="부서명" :items="dept_list2" clearable clear-icon="tabler-x" />
          <AppSelect v-model="newSelectedEmpCode" label="사원명" :items="emp_list_by_dept2" clearable
            clear-icon="tabler-x" />
          <AppTextField v-model="newRequestDate" type="date" label="신청일" placeholder="신청일" />
          <AppSelect v-model="newCategory" label="증명서구분" :items="items" append-inner-icon="tabler-search" />
          <AppSelect v-model="newUsageName" label="용도" :items="use" />
          <VTextarea v-model="newEtc" clearable label="신청사유" variant="outlined" />
        </VContainer>
        <VCardActions>
          <VBtn color="primary" @click="newCert">
            신청
          </VBtn>
          <VBtn color="primary" @click="attenStore().SET_DIALOG_BTN(false)">
            닫기
          </VBtn>
        </VCardActions>
      </VCard>
    </VDialog>
  </div>
</template>
