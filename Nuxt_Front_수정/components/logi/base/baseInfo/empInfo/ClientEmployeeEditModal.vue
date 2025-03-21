<script setup lang="ts">
import { employeeStore } from '@/store/logi/employee';
import { computed, watch, ref } from 'vue';

const emits = defineEmits(['closeModal', 'saveClient']);

const props = defineProps({
    selectedEmployee: {
        type: Object,
        required: false, // 초기에는 값이 없을 수도 있음
    },
});

// 반응형 데이터: `computed()`를 활용하여 `selectedEmployee`가 변경될 때 자동 업데이트
// computed() -> 양방형 바인딩 변수, 값을 읽을수도 있고, 변경도 가능하다.
const companyCode = computed({
    get: () => props.selectedEmployee?.companyCode || '',
    set: (val) => {
        if (props.selectedEmployee) props.selectedEmployee.companyCode = val;
    }
});

const empCode = computed({
    get: () => props.selectedEmployee?.empCode || '',
    set: (val) => {
        if (props.selectedEmployee) props.selectedEmployee.empCode = val;
    }
});

const empName = computed(() => props.selectedEmployee?.empName || '');

const empEngName = computed({
    get: () => props.selectedEmployee?.empEngName || '',
    set: (val) => {
        if (props.selectedEmployee) props.selectedEmployee.empEngName = val;
    }
});

const socialSecurityNumber = computed({
    get: () => props.selectedEmployee?.socialSecurityNumber || '',
    set: (val) => {
        if (props.selectedEmployee) props.selectedEmployee.socialSecurityNumber = val;
    }
});

const hireDate = computed({
    get: () => props.selectedEmployee?.hireDate || '',
    set: (val) => {
        if (props.selectedEmployee) props.selectedEmployee.hireDate = val;
    }
});

const retirementDate = computed({
    get: () => props.selectedEmployee?.retirementDate || '',
    set: (val) => {
        if (props.selectedEmployee) props.selectedEmployee.retirementDate = val;
    }
});

const userOrNot = computed({
    get: () => props.selectedEmployee?.userOrNot || 'Y',
    set: (val) => {
        if (props.selectedEmployee) props.selectedEmployee.userOrNot = val;
    }
});

const birthDate = computed({
    get: () => props.selectedEmployee?.birthDate || '',
    set: (val) => {
        if (props.selectedEmployee) props.selectedEmployee.birthDate = val;
    }
});

const gender = computed({
    get: () => props.selectedEmployee?.gender || '',
    set: (val) => {
        if (props.selectedEmployee) props.selectedEmployee.gender = val;
    }
});

const empStore = employeeStore();
const Editdata = ref([]);

// 회사 코드 목록 가져오기
onMounted(async () => {
    console.log("실행됨");
    await empStore.GET_COMPANY_INFO();

    Editdata.value = empStore.compnayInfo.map((emp: { companyCode: any; companyName: any }) => ({
        title: `${emp.companyCode} - ${emp.companyName}`,
        value: emp.companyCode
    }));
});

// 모달 닫기
const closeModal = () => {
    emits('closeModal');
};

// 수정된 데이터 저장
const saveClient = async () => {
    const updatedEmployee = {
        companyCode: companyCode.value,
        empCode: empCode.value,
        empName: empName.value,
        empEngName: empEngName.value,
        socialSecurityNumber: socialSecurityNumber.value,
        hireDate: hireDate.value,
        retirementDate: retirementDate.value,
        userOrNot: userOrNot.value,
        birthDate: birthDate.value,
        gender: gender.value,
    };

    console.log('수정된 사원 정보:', updatedEmployee);
    await empStore.GET_EMP_EDIT(updatedEmployee)
    emits('saveClient', updatedEmployee);
};
</script>

<template>
    <VDialog max-width="600" persistent>
        <VCard title="사원 정보 수정">
            <VCardText>
                <VRow>
                    <VCol cols="12" sm="6" md="4">
                        <AppSelect v-model="companyCode" :items="Editdata" item-text="title" label="회사코드"
                            item-value="value" variant="outlined" />
                    </VCol>
                    <VCol cols="12" sm="6" md="4">
                        <AppTextField v-model="empCode" label="사원코드" />
                    </VCol>
                    <VCol cols="12" sm="6" md="4">
                        <AppTextField v-model="empName" label="사원명" :readonly="true" />
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
                        <AppTextField v-model="userOrNot" label="회원" :readonly="true" />
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
