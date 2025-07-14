<script setup lang="ts">
import axios from "axios";
import { computed, onMounted, ref } from "vue";
import { VDataTable } from "vuetify/labs/VDataTable";
import { useRouter } from "vue-router";

// 내가 추가한 로직
const router = useRouter(); // vue Router 사용
const isLoggedIn = ref(false);

// const checkLogin = () => {
//   const token = localStorage.getItem("authToken");
//   if(!token) {
//     isLoggedIn.value = false;
//     router.push("login");
//   }else{
//     isLoggedIn.value = true;
//   }
// };

// onMounted(() => {
//   checkLogin();
//   if(isLoggedIn.value){
//     fetchData();
//   }
// });

const headers = [
  // { title: '사번', key: 'empCode' },
  { title: "직원명", key: "empName" },
  { title: "입사일", key: "hireDate" },
  { title: "생년월일", key: "birthDate" },
  { title: "성별", key: "gender" },
  { title: "부서번호", key: "deptCode" },
  { title: "부서이름", key: "deptName" },
  { title: "고용상태", key: "retirementDate" },
];

const empList = ref([]);
const search = ref("");
const loading = ref(false);

// 직원 리스트 조회
const fetchData = async () => {
  try {
    loading.value = false;

    const response = await axios.get("http://localhost:8282/hr/affair/emp");

    empList.value = response.data.empList;
    console.log("[emp_list]", empList.value);
  } catch (error) {
    console.error(error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchData();
});

// 직원 검색
const filteredEmpList = computed(() => {
  return empList.value.filter((emp: any) => {
    return Object.values(emp).some((field) => {
      if (typeof field === "string")
        return field.toLowerCase().includes(search.value.toLowerCase());

      return false;
    });
  });
});
</script>

<template>
  <h1 class="mb-6">직원정보 조회</h1>
  <VApp>
    <VCard>
      <!-- 검색창 -->
      <VCardText>
        <VRow>
          <VCol cols="12" offset-md="" md="12">
            <AppTextField
              v-model="search"
              density="compact"
              placeholder="Search"
              append-inner-icon="tabler-search"
              single-line
              hide-details
              dense
              outlined
            />
          </VCol>
        </VRow>

        <!-- 테이블 -->
        <VDataTable
          :headers="headers"
          :items="filteredEmpList"
          :items-per-page="10"
        />
      </VCardText>
    </VCard>
  </VApp>
</template>
