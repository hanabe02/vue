<script setup lang="ts">
import { VDataTable } from 'vuetify/labs/VDataTable';
import { employeeStore } from '@/store/logi/employee';
import ClientEmployeeAddModal from './ClientEmployeeAddModal.vue';
import ClientEmployeeEditModal from './ClientEmployeeEditModal.vue';

const empStore = employeeStore();
const selectedEmp: Ref<any[]> = ref([])
const empheaders = [
    { title: '회사코드', key: 'companyCode', width: 150 },
    { title: '사원코드', key: 'empCode', width: 150 },
    { title: '사원명', key: 'empName', width: 150 },
    { title: '입사일', key: 'hireDate', width: 150 },
    { title: '회원', key: 'userOrNot', width: 150 },
]
const generalEmpdata = ref([])
const empAdd = ref(false)
const empEdit = ref(false)

const empSearch = async () => {
    await empStore.GET_EMP_INFO();
    generalEmpdata.value = empStore.employeeInfo;
}

// const empEdit = async () => {
//   const empCode = selectedEmp.value[0]?.empCode;

//   // await empStore.GET_EMP_EDIT();
// }

const empDelete = async () => {
    console.log(selectedEmp.value[0]?.empCode);
    // const empCode = {
    //   empCode: selectedEmp.value[0]?.empCode
    // }
    const empCode = selectedEmp.value[0]?.empCode;
    await empStore.GET_EMP_DELETE(empCode);

    await empSearch()
    alert('사원의 정보가 삭제되었습니다.')
}

const closeAddModal = () => {
    empAdd.value = false
}

const closeEditModal = () => {
    empEdit.value = false
}

const saveAddClient = async () => {
    try {
        closeAddModal()

        empSearch()

        alert('사원의 정보가 추가되었습니다.')
    }
    catch (error) {
        console.error('Error saving client:', error)
        alert('거래처 추가에 실패했습니다.')
    }
}

const saveEditClient = async () => {
    try {
        closeEditModal()

        empSearch()

        alert('사원의 정보가 수정되었습니다.')
    }
    catch (error) {
        console.error('Error saving client:', error)
        alert('거래처 추가에 실패했습니다.')
    }
}


</script>

<template>
    <VCard>
        <div style="margin-bottom: 20px" />

        <div style="margin-left: 20px">
            <VBtn class="button" color="primary" @click="empSearch">
                사원 조회
            </VBtn>
            <VBtn class="button" color="primary" @click="empAdd = !empAdd">
                사원 추가
            </VBtn>
            <VBtn class="button" color="primary" @click="empEdit = !empEdit">
                사원 수정
            </VBtn>
            <VBtn class="button" color="primary" @click="empDelete">
                사원 삭제
            </VBtn>
        </div>
        <VCardText>
            <VDataTable v-model="selectedEmp" :headers="empheaders" :items="generalEmpdata" :items-per-page="10"
                show-select select-strategy="single" return-object />
        </VCardText>
    </VCard>

    <ClientEmployeeAddModal v-model="empAdd" :is-dialog-visible="empAdd" @closeModal="closeAddModal"
        @saveClient="saveAddClient" />
    <ClientEmployeeEditModal v-model="empEdit" :is-dialog-visible="empEdit" :selectedEmployee="selectedEmp[0]"
        @closeModal="closeEditModal" @saveClient="saveEditClient" />


</template>

<style>
.button {
    margin-right: 20px;
}
</style>
