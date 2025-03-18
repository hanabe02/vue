# 🌟 Vue 프로젝트 (Vue 3 + Nuxt + TypeScript)

이 프로젝트는 **Vue 3, Nuxt, TypeScript** 기반의 웹 애플리케이션입니다.  
UI 라이브러리로 **Vuetify**를 사용하여 **Material Design** 스타일의 UI를 제공합니다.

---

## 📌 기술 스택

### 🖥️ **Frontend (Vue)**
- **Vue 3** + **Nuxt**
- **TypeScript 적용**
- **Pinia** (상태 관리)  
  - Vuex 대신 **Pinia** 사용 (Vue 3 공식 권장 상태 관리 라이브러리)
  - **Redux와 유사한 역할**
  - **타입스크립트 친화적**
  - **모듈화된 스토어 관리**
  - **반응형 지원** (`ref` & `computed` 활용)
  - **Vue DevTools 지원**

- **UI Framework:** Vuetify  
  - **Material Design 기반 UI 프레임워크**
  - 다양한 UI 컴포넌트 제공 → 개발 속도 향상

- **패키지 매니저:** `pnpm` 사용

---

### 🔥 **Backend**
- **SQL (Oracle DB)**
- **Stored Procedures (프로시저) 활용**

---

## 🔄 데이터 덤프(Dump) 작업 방법

### ✅ **덤프 가져오기 (Import)**

**dump imp 하는 방법**
  1. sqlplus/ nolog
  3. conn /as sysdba
  5. create user [아이디] identified by [비밀번호] default tablespace system;
  7. grant connect, resource, dba to [아이디]
  9. conn [아이디/비번]
  11. exit
  13. imp userid=[아이디]/[비번] file='"[덤프 경로]"' full=y;
  
**dump exp 하는 방법**
  1. lsnrctl services
  2. exp 아이디/비번@[localhost:포트번호/xe : 덤프가 설치되어 있는경우 -> 이 경우는 오라클 db 의 경로] file=[dmp 설치할 경로]

---

## **Bitbucket**
  - git 저장소를 관리하는 저장소

---

### **클라우드**
  - 가상 공간이라는 개념 -> 인터넷에서 데이터를 저장하고 실행하는 공간이다.
  
  **클라우드의 특징**
  1. 어디서든 접근 가능
  2. 물리적 서버 불필요
  3. 공유 및 협업 용이

  **클라우드 기반 Bitbucket** : bitbucket cloud 를 사용하면 코드를 로컬 에 저장하는 게 아니라 bitbucket 서버에 저장하게 된다.

---

### **sourceTree**
  - git 을 편리하게 사용할 수 있게 도와주는 툴 

---

### **추가 구현 : 로그인, 2025년 3월 14일 ~ 2025년 3월 17일** 
      - 로그인 : 하드 코딩 부분 -> **Google OAuth 2.0 로그인 적용**
       📦 프로젝트 루트
          ├── 📂 frontend (Vue.js)
          │   ├── login.vue  # Google 로그인 버튼
          │   ├── store.js  # 상태 관리 (Pinia)
          │   ├── api.js  # API 요청 관리
          │   └── main.js  # Vue 초기 설정
          │
          ├── 📂 backend (Spring Boot)
          │   ├── SecurityConfig.java  # Spring Security 설정
          │   ├── CustomOAuth2UserService.java  # OAuth2 로그인 처리
          │   ├── OAuthAttributes.java  # OAuth2 사용자 정보 변환
          │   ├── SessionUser.java  # 세션 관리
          │   ├── Users.java  # 사용자 엔티티
          │   ├── UserRepository.java  # JPA 레포지토리
          │   └── application.properties  # 환경 설정
          └── README.md

### **추가 구현 : 백에서 프론트 url 요청, db 데이터 가져오기 2025년 3월 18일 **
      - 로그인 DB 정보, 백단에서 가져오기 : CORS(cross Origin Resource Sharing) 기술 사용
        cors? -> 웹 브라우저에서 다른 도메인(origin)의 리소스를 요청할 때 보안 문제를 해결하기 위한 메커니즘

        cors 를 사용하는 경우 : 
          1. 사용자 로그인 버튼 클릭 http://localhost:8282/oauth2/authorization/google : OAuth 로그인
          2. 구글 OAuth 인증 성공 후 http://localhost:3000/auth-callback 리디렉션 
          3. 앞단에서 fetch() 사용 http://localhost:8282/auth/user 에 사용자 정보 요청
          4. cors 정책으로 인해 브라우저가 http://localhost:8282/auth/user 요청 차단
            -> 프론트엔드 도메인(localhost:3000)과 백엔드(localhost:8282)이 다르기 때문👍
            이 경우, cors 설정이 없으면 보안 정책(same-origin policy, sop) 때문에 요청이 차단됨!

        cors 가 필요없는 경우 :
          1. 백엔드 내부에서 실행된 코드
          2. 서버가 백엔드 내부에서 데이터베이스 DB 를 조회하고 그 결과를 반환하는 것
          3. cors는 브라우저에서 발생하는 요청에 대한 보안 정책이기 때문에, 이 경우에는 영향을 받지 않음
            -> 즉 여기서는 요청이 백엔드 내부에서 이루어지기 때문에 브라우저의 CORS 정책이 적용되지 않았던 것

        SecurityCode.java 코드 일부
          @Bean
            public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS 설정 추가
                        .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                        .authorizeRequests(auth -> auth
                                .antMatchers("/", "/auth/**", "/oauth2/**").permitAll()
                                .anyRequest().authenticated()
                        )
                        .oauth2Login(oauth2 -> oauth2
                                .successHandler((request, response, authentication) -> {
                                    // ✅ 세션에서 사용자 정보 확인
                                    SessionUser user = (SessionUser) request.getSession().getAttribute("user");
        
                                    if (user != null) {
                                        System.out.println("✅ 로그인 성공: 세션에 사용자 정보 저장됨 → " + user);
                                    } else {
                                        System.out.println("🚨 로그인 성공했지만 세션에 사용자 정보 없음!");
                                    }
        
                                    // ✅ 프론트엔드의 `/auth-callback` 페이지로 리디렉션
                                    response.sendRedirect("http://localhost:3000/auth-callback");
                                })
                                .userInfoEndpoint(userInfo -> userInfo
                                        .userService(customOAuth2UserService)
                                )
                        )
                        .formLogin(form -> form.disable()) // 기본 로그인 폼 비활성화
                        .httpBasic(httpBasic -> httpBasic.disable()); // Basic Auth 비활성화
        
                return http.build();
            }
        
            // ✅ CORS 설정 추가
            @Bean
            public CorsConfigurationSource corsConfigurationSource() {
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                CorsConfiguration config = new CorsConfiguration();
        
                config.setAllowCredentials(true);
                config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // ✅ Vue 프론트엔드 허용
                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        
                source.registerCorsConfiguration("/**", config);
                return source;
            }
        }



