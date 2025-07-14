<script setup lang="ts">
import { employeeStore } from '@/store/logi/employee'

const emits = defineEmits(['closeModal', 'saveClient'])

const companyCode = ref('');
const empCode = ref('');
const empName = ref('');
const empEngName = ref('');
const socialSecurityNumber = ref('');
const userOrNot = ref('');
const gender = ref('');
const empStore = employeeStore();

const infodata = ref([]);

onMounted(async () => {
    console.log("실행됨");
    await empStore.GET_COMPANY_INFO();

    // infodata.value = empStore.employeeInfo.value[0]?.companyCode

    infodata.value = empStore.compnayInfo.map((emp: { companyCode: any; companyName: any }) => ({
        title: `${emp.companyCode} - ${emp.companyName}`,
        value: emp.companyCode
    }));
})

const getEndDate = () => {
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const lastday = new Date(year, month, 0).getDate();
    return `${year}-${month}-${lastday}`;
};

const getTodayDate = () => {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const day = String(today.getDate()).padStart(2, "0");
    return `${year}-${month}-${day}`;
};

const hireDate = ref(getTodayDate());
const retirementDate = ref(getEndDate());
const birthDate = ref(getEndDate());

const closeModal = () => {
    emits('closeModal')
}
const resetData = () => {
    companyCode.value = ''
    empCode.value = ''
    empName.value = ''
    empEngName.value = ''
    socialSecurityNumber.value = ''
    hireDate.value = ''
    retirementDate.value = ''
    userOrNot.value = ''
    birthDate.value = ''
    gender.value = ''
}

const saveClient = async () => {
    const clientdata = {
        companyCode: companyCode.value,
        empCode: empCode.value,
        empName: empName.value,
        empEngName: empEngName.value,
        socialSecurityNumber: socialSecurityNumber.value,
        hireDate: hireDate.value,
        retirementDate: retirementDate.value,
        userOrNot: 'Y',
        birthDate: birthDate.value,
        gender: gender.value,
    }
    await empStore.GET_EMP_ADD(clientdata);
    console.log(clientdata)
    emits('saveClient', clientdata)
    resetData()
}


</script>


<template>
    <VDialog max-width="600" persistent>
        <!-- Dialog Content -->
        <VCard title="사원 정보 추가">
            <VCardText>
                <VRow>
                    <VCol cols="12" sm="6" md="4">
                        <AppSelect v-model="companyCode" :items="infodata" item-text="title" label="회사코드"
                            item-value="value" variant="outlined" />
                    </VCol>
                    <VCol cols="12" sm="6" md="4">
                        <AppTextField v-model="empCode" label="사원코드" />
                    </VCol>
                    <VCol cols="12" sm="6" md="4">
                        <AppTextField v-model="empName" label="사원명" persistent-hint />
                    </VCol>
                    <VCol cols="12">
                        <AppTextField v-model="empEngName" label="사원 영문명" />
                    </VCol>
                    <VCol cols="12">
                        <AppTextField v-model="socialSecurityNumber" label="사회보장번호" />
                    </VCol>
                    <VCol cols="12" sm="6">
                        <AppDateTimePicker class="mb-3" v-model="hireDate" label="입사일" placeholder="YYYY-MM-DD"
                            :readonly="true" />
                    </VCol>
                    <VCol cols="12" sm="6">
                        <AppDateTimePicker class="mb-3" v-model="retirementDate" label="퇴직일" placeholder="YYYY-MM-DD" />
                    </VCol>
                    <VCol cols="12">
                        <AppTextField v-model="userOrNot" label="회원" value="Y" :readonly="true" />
                    </VCol>
                    <VCol cols="12">
                        <AppDateTimePicker class="mb-3" v-model="birthDate" label="생년월일" placeholder="YYYY-MM-DD" />
                    </VCol>
                    <VCol cols="12" sm="6">
                        <AppSelect v-model="gender" :items="['남', '여']" label="성별" />
                    </VCol>
                </VRow>
            </VCardText>

            <VCardText class="d-flex justify-end flex-wrap gap-3">
                <VBtn variant="tonal" color="secondary" @click="closeModal">
                    Close
                </VBtn>
                <VBtn @click="saveClient">
                    Save
                </VBtn>
            </VCardText>
        </VCard>
    </VDialog>
</template>