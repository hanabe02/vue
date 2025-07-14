<script setup lang="ts">
import AuthProvider from "@/views/pages/AuthProvider.vue";
import { useGenerateImageVariant } from "@core/composable/useGenerateImageVariant";
import authV2LoginIllustrationBorderedDark from "@images/pages/auth-v2-login-illustration-bordered-dark.png";
import authV2LoginIllustrationBorderedLight from "@images/pages/auth-v2-login-illustration-bordered-light.png";
import authV2LoginIllustrationDark from "@images/pages/auth-v2-login-illustration-dark.png";
import authV2LoginIllustrationLight from "@images/pages/auth-v2-login-illustration-light.png";
import authV2MaskDark from "@images/pages/misc-mask-dark.png";
import authV2MaskLight from "@images/pages/misc-mask-light.png";
import { VNodeRenderer } from "@layouts/components/VNodeRenderer";
import { themeConfig } from "@themeConfig";
import { ref, inject } from 'vue'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { useEmpStore } from '@/store/hr/emp'

const router = useRouter();

definePageMeta({
  layout: "blank",
});

const form = ref({
  empCode: "EMP-01",
  password: "1208125847",
  remember: false,
});

// 비밀번호 표시 여부
const isPasswordVisible = ref(false);

// const linkbtn = () => {
//   router.push("/");
// };
// 🔹 inject로 empCode 가져오기 (초기값: 빈 문자열)
const empCode = inject('empCode', ref(''))

// 로그인 함수
const login = async () => {
  try {
    // 서버로 로그인 요청 보내기
    const response = await axios.post('http://localhost:8282/sys/login', {
      empCode: form.value.empCode,
      password: form.value.password,
      companyCode: 'COM-01',
      workplaceCode: 'BRC-01',
    });
    console.log('정보:', response.data);
    console.log('정보:', response.data.empInfo.empCode);
    // 로그인 성공 여부 확인
    if (response.data.errorCode === 0) {
      // 로그인 성공 처리
      alert('로그인 성공!');
      const empStore = useEmpStore()   // ✅ Pinia 스토어 인스턴스 호출
      empStore.setEmpCode(response.data.empInfo.empCode)  // ✅ 전역 상태 + localStorage 저장
      empCode.value = response.data.empInfo.empCode; // empCode 업데이트
      provide('empCode', empCode.value); // empCode 제공 
      console.log('정보1:', empCode);
      router.push("/"); // 기본 페이지로 리디렉션
      } else {
      // 로그인 실패 처리
      alert(response.data.errorMsg || 'Invalid ID or password'); // 서버에서 제공하는 오류 메시지 출력
    }
  } catch (error) {
    console.error('로그인 실패:', error);
    alert('로그인 중 에러 발생');
  }
};

const authThemeMask = useGenerateImageVariant(authV2MaskLight, authV2MaskDark);
</script>

<template>
  <VRow no-gutters class="auth-wrapper bg-surface">
    <VCol md="8" class="d-none d-md-flex">
      <div class="position-relative bg-background rounded-lg w-100 ma-8 me-0">
        <div class="d-flex align-center justify-center w-100 h-100">
          <VImg
            max-width="505"
            :src="authThemeImg"
            class="auth-illustration mt-16 mb-2"
          />
        </div>

        <VImg class="auth-footer-mask" :src="authThemeMask" />
      </div>
    </VCol>

    <VCol
      cols="12"
      md="4"
      class="auth-card-v2 d-flex align-center justify-center"
    >
      <VCard flat :max-width="500" class="mt-12 mt-sm-0 pa-4">
        <VCardText>
          <VNodeRenderer :nodes="themeConfig.app.logo" class="mb-6" />
          <h4 class="text-h4 mb-1">
            Welcome to
            <span class="text-capitalize">{{ themeConfig.app.title }}</span>! 👋🏻
          </h4>
          <p class="mb-0">
            Please sign-in to your account and start the adventure
          </p>
        </VCardText>
        <VCardText>
          <VForm @submit.prevent="login">
            <VRow>
              <!-- email -->
              <VCol cols="12">
                <AppTextField
                  v-model="form.empCode"
                  autofocus
                  label="Email"
                  type="empCode"
                  placeholder="johndoe@email.com"
                />
              </VCol>

              <!-- password -->
              <VCol cols="12">
                <AppTextField
                  v-model="form.password"
                  label="Password"
                  placeholder="············"
                  :type="isPasswordVisible ? 'text' : 'password'"
                  :append-inner-icon="isPasswordVisible ? 'tabler-eye-off' : 'tabler-eye'"
                  @click:append-inner="isPasswordVisible = !isPasswordVisible"
                />

                <div
                  class="d-flex align-center flex-wrap justify-space-between mt-2 mb-4"
                >
                  <VCheckbox v-model="form.remember" label="Remember me" />
                  <a class="text-primary ms-2 mb-1" href="/">
                    Forgot Password?
                  </a>
                </div>
                <VBtn block type="submit"> Login </VBtn>
              </VCol>

              <!-- create account -->
              <VCol cols="12" class="text-center text-base">
                <span>New on our platform?</span>

                <a class="text-primary ms-2" href="#"> Create an account </a>
              </VCol>

              <VCol cols="12" class="d-flex align-center">
                <VDivider />

                <span class="mx-4">or</span>

                <VDivider />
              </VCol>

              <!-- auth providers -->
              <VCol cols="12" class="text-center">
                <AuthProvider />
              </VCol>
            </VRow>
          </VForm>
        </VCardText>
      </VCard>
    </VCol>
  </VRow>
</template>

<style lang="scss">
@use "@core/scss/template/pages/page-auth.scss";
</style>
