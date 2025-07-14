/** - 사진 나오게하기, 사진 수정, 저장 구현하기 - 주소 수정 시 다음 api 받아와서
주소 저장하도록 구현하기 */

<script setup lang="ts">
import axios from "axios";
import { computed, onMounted, ref } from "vue";
import { VDataTable } from "vuetify/labs/VDataTable";
import { useRouter } from "vue-router"; // router 가져오기
import { inject } from 'vue';

const router = useRouter();
// 기존 inject 제거
// const empCode = inject('empCode', ref(''))

import { useEmpStore } from '@/store/hr/emp'
import { VBtn, VCard } from "vuetify/lib/components/index.mjs";

const empStore = useEmpStore()
empStore.loadEmpCode()

console.log('현재 로그인된 직원 코드:', empStore.empCode)

// 로그인된 empCode가 'EMP-01'이 아닌 경우 페이지 이동
if (empStore.empCode !== 'EMP-01' && empStore.empCode !== 'EMP-00') {
  router.push('/hr/confinement/unauthorized');
}

// 사원 컬럼
const headers = [
  { title: "사번", key: "empCode" },
  { title: "직원명", key: "empName" },
  { title: "부서이름", key: "deptName" },
];

// 최종학력 select 컬럼
const educations = [
  { value: "중졸이하", text: "중졸이하" },
  { value: "중졸", text: "중졸" },
  { value: "고졸", text: "고졸" },
  { value: "대졸", text: "학사" },
  { value: "석사", text: "석사" },
  { value: "박사", text: "박사" },
];

// 직급 select 컬럼
const positions = [
  { value: "POS-01", text: "사장" },
  { value: "POS-02", text: "이사" },
  { value: "POS-03", text: "부장" },
  { value: "POS-04", text: "차장" },
  { value: "POS-05", text: "과장" },
  { value: "POS-06", text: "대리" },
  { value: "POS-07", text: "사원" },
  { value: "POS-08", text: "인턴" },
  { value: "POS-09", text: "계약직" },
];

const accountData = {
  avatarImg: "/_nuxt/assets/images/empImages/EMP-09.jpeg",
};

const formImg = ref<null | File>(null);

const isSuccessFunc = ref(false);
const iseditFunc = ref(false);
const isdeleteFunc = ref(false);

// 성별 select 컬럼
const gender = [
  { value: "male", text: "남성" },
  { value: "female", text: "여성" },
];

// select component에 사용되는 함수
const itemProps = (item: any) => {
  return {
    title: item.text,
    value: item.value,
  };
};

// DateTimePicker 관련 변수
const now = new Date();
const formattedDate = now.toLocaleString("default", {
  year: "numeric",
  month: "2-digit",
});

const empList = ref([]);
const search = ref("");
const workplaceList = ref([]);
const deptList = ref([]);
const loading = ref(false);

const refInputEl = ref<HTMLElement>();
const accountDataLocal = ref(structuredClone(accountData));

const empDetail = ref({
  empCode: "",
  workplaceCode: "",
  deptCode: "",
  positionCode: "",
  phoneNumber: "",
  email: "",
  zipCode: "",
  basicAddress: "",
  detailAddress: "",
  levelOfEducation: "",
  image: "",
  empName: "",
  deptName: "",
  gender: "",
  hireDate: "",
  birthDate: "",
});

// const empImage = ref('')

// const photo = ref({
//   file: [],
// })

// 직원리스트 조회
const fetchData = async () => {
  try {
    loading.value = true;

    const response = await axios.get("http://localhost:8282/hr/affair/emp");

    empList.value = response.data.empList;
    console.log("[empList]", response.data.empList);
  } catch (error) {
    console.error(error);
  } finally {
    loading.value = false;
  }
};

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

// 직원 행 클릭
const onRowClick = async (event: any, item: any) => {
  console.log("[clickEmp]", item.item.empCode);
  try {
    formImg.value = null;

    const clickEmp = item.item.empCode;
    const res = await axios.get(
      `http://localhost:8282/hr/affair/emp/${clickEmp}`
    );

    console.log("[empDetailInfo]", res.data.empDetailInfo[0]);
    empDetail.value = res.data.empDetailInfo[0];
    console.log(empDetail.value);
    console.log("positionCode", empDetail.value.positionCode);
    console.log("[clickEmp]", clickEmp);
    accountDataLocal.value.avatarImg = `/_nuxt/assets/images/empImages/${empDetail.value.image}`;
  } catch (e) {
    console.error(e);
  }
};

// 수정버튼
const editFunc = async () => {
  if (empDetail.value.empCode === "") {
    iseditFunc.value = true;
  } else {
    console.log("수정버튼 클릭!!!");
    try {
      const empUpdate = empDetail.value;
      const formData = new FormData();

      const res = await axios.put(
        "http://localhost:8282/hr/affair/emp",
        empUpdate,
        {
          headers: {
            "Cache-Control": "no-cache",
            Pragma: "no-cache",
          },
        }
      );

      if (formImg.value) {
        formData.append("file", formImg.value);

        await axios.post(
          "http://localhost:8282/hr/affairFile/photoUpdate",
          formData,
          {
            headers: {
              "Cache-Control": "no-cache",
              Pragma: "no-cache",
            },
          }
        );
      }

      console.log("[res.empUpdate]", res.data); // sucess
      await fetchData();
      isSuccessFunc.value = true;
    } catch (e) {
      console.error(e); // error.mesagge
    }
  }
};

//연차생성
const vacationcreate = async(parms: any) => {
  console.log("연차생성버튼클릭");
try{
  const res = await axios.post("http://localhost:8282/hr/base/vacationcreate");
  if(res.status===200){
    console.log("연차 생성 성공",res.status);
    alert("연차가 성공적으로 생성되었습니다!");
  }
  else{
    alert("연차생성 결과를 확인 할 수 없습니다.");
  }
  }catch(e:any){
        console.error("연차생성 실패", e);
        
    alert("연차 생성 실패: " + (e.response?.data?.message || e.message));
    }
  };



// 삭제버튼
const deleteFunc = async (params: any) => {
  if (empDetail.value.empCode === "") {
    isdeleteFunc.value = true;
  } else {
    try {
      params = empDetail.value.empCode;
      console.log("[delEmpCode]", params);

      const res = await axios.delete(
        `http://localhost:8282/hr/affair/emp/${params}`
      );

      console.log("[res.empUpdate]", res.data); // sucess
      await fetchData();
      isSuccessFunc.value = true;
    } catch (e) {
      console.error(e); // error.mesagge
    }
  }
};

// 부서리스트 조회
const fetchDeptList = async () => {
  try {
    const res = await axios.get("http://localhost:8282/hr/base/deptList");
    const deptData = res.data.deptlist;

    deptList.value = deptData.map((e: any) => ({
      value: e.deptCode,
      text: e.deptName,
    }));
    console.log("[deptList]", deptList.value);
  } catch (error) {
    console.error(error);
  }
};

// 근무지리스트 조회
const fetchWorkPlaceList = async () => {
  try {
    const res = await axios.get("http://localhost:8282/hr/base/workplaceList");
    const workplaceData = res.data.workplaceList;

    workplaceList.value = workplaceData.map((e: any) => ({
      value: e.workplaceCode,
      text: e.workplaceName,
    }));
    console.log("[workplaceList]", workplaceList.value);
  } catch (error) {
    console.error(error);
  }
};

// 우편번호 모달 오픈, 구현중
const searchPost = () => {
  alert("우편번호 검색 api모달 출력!!! ");
  console.log("우편번호 검색!!!");
};

// changeAvatar function
const changeAvatar = (file: Event) => {
  const fileReader = new FileReader();
  const { files } = file.target as HTMLInputElement;

  if (files && files.length > 0) formImg.value = files[0];

  if (files && files.length) {
    fileReader.readAsDataURL(files[0]);
    fileReader.onload = () => {
      if (typeof fileReader.result === "string")
        accountDataLocal.value.avatarImg = fileReader.result;
    };
  }
};

// 페이지가 로드되자마자 실행
onMounted(() => {
  fetchData();
  fetchWorkPlaceList();
  fetchDeptList();
});
</script>

<template>
  <h1 class="mb-6">직원정보 관리</h1> 
  <VBtn class="ml-3" @click="vacationcreate">연차생성</VBtn>
  <VRow>
    <!-- 직원정보조회 -->
    <VCol md="4">
      <VCard class="mb-6" height="725" title="직원정보 조회">
        <VCardText>
          <VRow>
            <VCol cols="12" offset-md="" md="">
              <AppTextField
                v-model="search"
                density="compact"
                placeholder="검색"
                append-inner-icon="tabler-search"
                single-line
                hide-details
                dense
                outlined
              />
            </VCol>
          </VRow>
          
          <VDataTable
            :headers="headers"
            :items="filteredEmpList"
            :items-per-page="10"
            @click:row="onRowClick"
          />
        </VCardText>
      </VCard>
    </VCol>

    <!-- 직원기초정보 -->
    <VCol md="8">
      <VCard class="mb-6" height="400">
        <VContainer>
          <VCardTitle class="mt-3">
            직원기초정보
            <VBtn class="ml-3" @click="editFunc">
              <VIcon start icon="tabler-edit" />수정
            </VBtn>
            <VBtn color="error" class="ml-3" @click="deleteFunc">
              <VIcon start icon="tabler-trash" />삭제
            </VBtn>
          </VCardTitle>
          <VCardText class="d-flex">
            <!-- 👉 Avatar -->
            <VAvatar
              rounded
              size="100"
              class="me-6"
              :image="accountDataLocal.avatarImg"
            />

            <!-- 👉 Upload Photo -->
            <form class="d-flex flex-column justify-center gap-4">
              <div class="d-flex flex-wrap gap-2">
                <VBtn variant="outlined" @click="refInputEl?.click()">
                  <VIcon icon="tabler-cloud-upload" class="d-sm-none" />
                  <span class="d-none d-sm-block">Upload new photo</span>
                </VBtn>

                <input
                  ref="refInputEl"
                  type="file"
                  name="file"
                  accept=".jpg, .jpeg, .png"
                  hidden
                  @input="changeAvatar"
                />
              </div>

              <p class="text-body-1 mb-0">Allowed JPG, JPEG or PNG.</p>
            </form>
          </VCardText>

          <VRow>
            <VCol cols="12" md="3">
              <VTextField
                v-model="empDetail.empName"
                class="mb-3"
                label="사원명"
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <VTextField
                v-model="empDetail.empCode"
                class="mb-3"
                label="사번"
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <AppDateTimePicker
                v-model="empDetail.birthDate"
                class="mb-3"
                placeholder="생년월일"
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <VSelect
                v-model="empDetail.gender"
                class="mb-3"
                label="성별"
                :items="gender"
                :item-props="itemProps"
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <VSelect
                v-model="empDetail.deptCode"
                class="mb-3"
                label="부서"
                :items="deptList"
                :item-props="itemProps"
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <VTextField
                v-model="empDetail.deptCode"
                class="mb-3"
                label="부서번호"
                read-only
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <AppDateTimePicker
                v-model="empDetail.hireDate"
                class="mb-3"
                placeholder="입사일"
              />
            </VCol>
          </VRow>
        </VContainer>
      </VCard>

      <!-- 직원기초정보 -->
      <VCard title="직원세부정보" height="300">
        <VContainer>
          <VRow>
            <VCol cols="12" md="3">
              <VSelect
                v-model="empDetail.positionCode"
                class="mb-3"
                label="직급"
                :items="positions"
                :item-props="itemProps"
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <VTextField
                v-model="empDetail.phoneNumber"
                class="mb-3"
                label="전화번호"
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <VTextField
                v-model="empDetail.email"
                class="mb-3"
                label="이메일"
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <VSelect
                v-model="empDetail.workplaceCode"
                class="mb-3"
                label="근무지"
                :items="workplaceList"
                :item-props="itemProps"
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <VSelect
                v-model="empDetail.levelOfEducation"
                class="mb-3"
                label="최종학력"
                :items="educations"
                :item-props="itemProps"
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <VTextField
                v-model="empDetail.zipCode"
                class="mb-3"
                label="우편번호(클릭)"
                variant="outlined"
                @click="searchPost"
              />
            </VCol>
            <VCol cols="12" md="3">
              <VTextField
                v-model="empDetail.basicAddress"
                class="mb-3"
                label="주소"
                variant="outlined"
              />
            </VCol>
            <VCol cols="12" md="3">
              <VTextField
                v-model="empDetail.detailAddress"
                class="mb-3"
                label="상세주소"
                variant="outlined"
              />
            </VCol>
          </VRow>
        </VContainer>
      </VCard>
    </VCol>
  </VRow>
  <VSnackbar
    v-model="iseditFunc"
    location="top"
    :timeout="3000"
    color="warning"
  >
    <VAlert
      density="compact"
      type="warning"
      title="warning!"
      text="빈칸이 없는지 확인해주세요."
    />
  </VSnackbar>
  <VSnackbar
    v-model="isdeleteFunc"
    location="top"
    :timeout="3000"
    color="warning"
  >
    <VAlert
      density="compact"
      type="warning"
      title="warning!"
      text="사번을 기입해주세요."
    />
  </VSnackbar>
  <VSnackbar
    v-model="isSuccessFunc"
    location="top"
    :timeout="3000"
    color="success"
  >
    <VAlert
      density="compact"
      type="success"
      title="success!"
      text="완료되었습니다."
    />
  </VSnackbar>
</template>
