<!-- ❗Errors in the form are set on line 60 -->
<script setup lang="ts">
import type { NuxtError } from 'nuxt/app'
import type { User } from 'next-auth'

import { VForm } from 'vuetify/components/VForm'
import AuthProvider from '@/views/pages/authentication/AuthProvider.vue'
import { useGenerateImageVariant } from '@core/composable/useGenerateImageVariant'
import authV2LoginIllustrationBorderedDark from '@images/pages/auth-v2-login-illustration-bordered-dark.png'
import authV2LoginIllustrationBorderedLight from '@images/pages/auth-v2-login-illustration-bordered-light.png'
import authV2LoginIllustrationDark from '@images/pages/auth-v2-login-illustration-dark.png'
import authV2LoginIllustrationLight from '@images/pages/auth-v2-login-illustration-light.png'
import authV2MaskDark from '@images/pages/misc-mask-dark.png'
import authV2MaskLight from '@images/pages/misc-mask-light.png'
import { VNodeRenderer } from '@layouts/components/VNodeRenderer'
import { themeConfig } from '@themeConfig'
import { ref } from "vue";
import { userStore } from '@/store/logi/user'

const { signIn, data: sessionData } = useAuth()
// useAuth() 함수 Vue 의 composition api 에서 사용되는 composable 함수
// useAuth()는 인증 관련 기능을 제공하는 함수이다.
// { signIn, data: sessionData } → useAuth() 
// signIn 함수와 data 세션 정보를 구조 분해 할당하여 가져온다.

// 즉, useAuth() 는 인증 관련 메서드와 세션 데이터를

const authThemeImg = useGenerateImageVariant(authV2LoginIllustrationLight, authV2LoginIllustrationDark, authV2LoginIllustrationBorderedLight, authV2LoginIllustrationBorderedDark, true)

const authThemeMask = useGenerateImageVariant(authV2MaskLight, authV2MaskDark)

definePageMeta({
  layout: 'blank',
  unauthenticatedOnly: true,

})

const isPasswordVisible = ref(false)

const route = useRoute()

const ability = useAbility()

const errors = ref<Record<string, string | undefined>>({
  email: undefined,
  password: undefined,
})

const refVForm = ref<VForm>()

const credentials = ref({
  email: 'admin@demo.com',
  password: 'admin',
})

const rememberMe = ref(false)

async function login() {
  console.log("login 함수가 실행 됨");
  const response = await signIn('credentials', {
    callbackUrl: '/',
    redirect: false,
    ...credentials.value,
  })
  console.log(response);


  // If error is not null => Error is occurred
   if (response && response.error) {
    const apiStringifiedError = response.error
    const apiError: NuxtError = JSON.parse(apiStringifiedError)

    errors.value = apiError.data

    // If err => Don't execute further
    return
  }

  // Reset error on successful login
  errors.value = {}

  // Update user abilities
  const { user } = sessionData.value!

  useCookie<Partial<User>>('userData').value = user

  // Save user abilities in cookie so we can retrieve it back on refresh
  useCookie<User['abilityRules']>('userAbilityRules').value = user.abilityRules

  ability.update(user.abilityRules ?? [])

  navigateTo(route.query.to ? String(route.query.to) : '/', { replace: true })
}

const onSubmit = () => {
  console.log("버튼이 클릭 됨");
  refVForm.value?.validate()
    .then(({ valid: isValid }) => {
      if (isValid)
        login()
    })
}

const loginWithGoogle = async () => {
  await userStore().GET_USER_INFO()
}

// const loginWithGoogle = () => {
//     window.location.href = "http://localhost:8282/oauth2/authorization/google";

    // setTimeout(async () => {
    //   try {
    //     const response = await fetch("http://localhost:8282/auth/user", {
    //       method: "GET",
    //       credentials: "include",
    //     });
    //     if (!response.ok) throw new Error("사용자 정보를 가져올 수 없음");
    //     const userData = await response.json();
    //     console.log("✅ 로그인된 사용자:", userData);

    //     if (userData && userData.token) {
    //       // JWT 토큰을 저장 (로컬 스토리지 or 쿠키)
    //       useCookie('authToken').value = userData.token;
    //       console.log("✅ JWT 저장 완료:", userData.token);

    //       window.location.href = "http://localhost:3000";
    //     } else {
    //       console.error("❌ JWT 토큰이 없습니다.");
    //     }
    //   } catch (error) {
    //     console.error("❌ 사용자 정보 가져오기 실패:", error);
    //   }
    // }, 3000); // 로그인 후 3초 후 실행 (OAuth 리디렉션 고려)
// }

</script>

<template>
  <VRow
    no-gutters
    class="auth-wrapper bg-surface"
  >
    <VCol
      lg="8"
      class="d-none d-lg-flex"
    >
      <div class="position-relative bg-background rounded-lg w-100 ma-8 me-0">
        <div class="d-flex align-center justify-center w-100 h-100">
          <VImg
            max-width="505"
            :src="authThemeImg"
            class="auth-illustration mt-16 mb-2"
          />
        </div>

        <VImg
          :src="authThemeMask"
          class="auth-footer-mask"
        />
      </div>
    </VCol>

    <VCol
      cols="12"
      lg="4"
      class="auth-card-v2 d-flex align-center justify-center"
    >
      <VCard
        flat
        :max-width="500"
        class="mt-12 mt-sm-0 pa-4"
      >
        <VCardText>
          <VNodeRenderer
            :nodes="themeConfig.app.logo"
            class="mb-6"
          />

          <h4 class="text-h4 mb-1">
            Welcome to <span class="text-capitalize"> {{ themeConfig.app.title }} </span>! 👋🏻
          </h4>
          <p class="mb-0">
            Please sign-in to your account and start the adventure
          </p>
        </VCardText>
        <VCardText>
          <VAlert
            color="primary"
            variant="tonal"
          >
            <p class="text-sm mb-2">
              Admin Email: <strong>admin@demo.com</strong> / Pass: <strong>admin</strong>
            </p>
            <p class="text-sm mb-0">
              Client Email: <strong>client@demo.com</strong> / Pass: <strong>client</strong>
            </p>
          </VAlert>
        </VCardText>
        <VCardText>
          <VForm
            ref="refVForm"
            @submit.prevent="onSubmit"
          >
            <VRow>
              <!-- email -->
              <VCol cols="12">
                <AppTextField
                  v-model="credentials.email"
                  label="Email"
                  placeholder="johndoe@email.com"
                  type="email"
                  autofocus
                  :rules="[requiredValidator, emailValidator]"
                  :error-messages="errors.email"
                />
              </VCol>

              <!-- password -->
              <VCol cols="12">
                <AppTextField
                  v-model="credentials.password"
                  label="Password"
                  placeholder="············"
                  :rules="[requiredValidator]"
                  :type="isPasswordVisible ? 'text' : 'password'"
                  :error-messages="errors.password"
                  :append-inner-icon="isPasswordVisible ? 'tabler-eye-off' : 'tabler-eye'"
                  @click:append-inner="isPasswordVisible = !isPasswordVisible"
                />

                <div class="d-flex align-center flex-wrap justify-space-between mt-1 mb-4">
                  <VCheckbox
                    v-model="rememberMe"
                    label="Remember me"
                  />
                  <NuxtLink
                    class="text-primary ms-2 mb-1"
                    :to="{ name: 'forgot-password' }"
                  >
                    Forgot Password?
                  </NuxtLink>
                </div>

                <VBtn
                  block
                  type="submit"  style="margin-bottom: 10px;"
                >
                  Login
                </VBtn>
                <!-- 
                  type : 속성은 버튼의 기본 동작을 정의하는 역할
                  특히, form 태그 안에서 사용될 때 버튼의 동작을 결정하는 중요한 속성이다.
                -->
                <div>
                  <h1>Hello Spring Boot</h1>
                  <button @click="loginWithGoogle" class="btn btn-success">Google Login</button>
                </div>
    


              </VCol>

              <!-- create account -->
              <VCol
                cols="12"
                class="text-center"
              >
                <span>New on our platform?</span>
                <NuxtLink
                  class="text-primary ms-2"
                  :to="{ name: 'register' }"
                >
                  Create an account
                </NuxtLink>
              </VCol>
              <VCol
                cols="12"
                class="d-flex align-center"
              >
                <VDivider />
                <span class="mx-4">or</span>
                <VDivider />
              </VCol>

              <!-- auth providers -->
              <VCol
                cols="12"
                class="text-center"
              >
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
