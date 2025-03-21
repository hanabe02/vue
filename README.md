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

### SecurityCode.java 코드 일부
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

### **추가 구현 : 소셜 로그인 로그아웃 구현 : 백단 -> session 초기화, localStorage 초기화, 2025년 3월 19일 **
    - error 발생 : google 로그인 시 -> /login 창이 한 번 더 뜨는 문제가 발생, 2025년 3월 19일 09:30 ~ 2025년 3월 19일 15:07분 해결
      acl.global.ts 가 auth-callback 라우터 주소 인식을 못하는 문제 때문에 login 주소를 호출하는 문제가 발생
      
      acl.global.ts 란?
        😁 nuxt -> 라우트 변경(페이지 이동) 전에 [acl.global.ts] 가 항상 먼저 실행된다.

    - error 발생 : cors 오류 때문에 앞단 -> 백단으로 url 요청이 안됨 localhost:3000, localhost:8282 보안 오류 계속 발생 - 2025년 3월 21일 14:53 분 ~ 2025년 3월 21일 15:19 분
      : 🫤 CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
      
      cors 오류 발생 => 백엔드가 인증을 요구하고 있다는 뜻, 
                       - 하지만 나는 인증이 필요 없는 데이터를 요청 중 ex) 견적 관련 데이터
                       - spring security가 모든 요청을 기본적으로 authenticated() 로 처리하고 있음, 
                       - cors 전에 인증부터 시도하고 있는 상황이 발생 
                       + spring security 유지하면서, 로그인/로그아웃만 보호, 나머지는 인증 없이 자유롭게 사용하기
                       + sprincSecurity 코드 일부 수정
                         .authorizeRequests(auth -> auth
                            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // options (preflight) 요청 허용
                            .antMatchers("/**").permitAll() // 모든 api 인증 없이 허용 (우선 전체 열어두기)
                            // 만약 특정 api 만 인증이 필요하면 
                            // .anMatchers("/auth/logout", "/auth/me").authenticated()
                            // 그 외는 막기 .anyMatchers().authenticated() <-- 전 코드에서는 이 코드 한 줄 😠💢 때문에 cors error 가 났다.
      Tip
        vue 에서 백단으로 후출을 해야할 경우
          인증이 필요 없는 경우 : axios.get('...')
          인증이 필요한 경우 : axios.get('...', 
            witchCredentials:true // witchCredentials 는 쿠키(세션)을 함꼐 보내겠다는 의미이다.
            -> 사용자가 로그인을 하면 spring boot 에 세션쿠키를 브라우저에 줘, 이 세션이 로그인 상태를 유지해주는 한마디로 "인증 토큰" 역할을 한다.
          )

>>>>>>> ca29fbabd8aaefef9858554a7e135bc15bc78f43
### **acl.global.ts 코드 일부**
        console.log("🔍 현재 로그인 상태:", isLoggedIn, "| 이동할 페이지:", to.name);
        
        // 로그인 버튼 클릭시 백단에서 auth-callback 라우터를 호출하는데 밑에 있는 login 검증 코드에서 걸려서 /login 창을 계속 띄우는 문제가 발생
        // 때문에 auth-callback 라우터 주소를 허용 함으로 써 db 에서 user 의 정보를 가져올 수 있게 해줌
        if (to.name === 'auth-callback') {
          console.log("✅ `auth-callback` 접근 허용됨");
          return;
        }

        // 프로젝트 실행시 로그인이 필요하기 때문에 -> /login 주소로 이동
        // autu-callback 을 실행하면 locatstorage 에 로그인의 상태가 적용되기 때문에 isLoggedIn 의 결과값이 : true 가 된다.
        if (!isLoggedIn && to.name !== 'login' && process.client) {
          console.log("🚨 로그인 필요! `/login`으로 이동");
          return router.push('/login');
        }
  ### logout 백단 logout 컨트롤러 추가
      AuthController.java
          @PostMapping("/logout")
          public ResponseEntity<?> logout(HttpSession session) {
              System.out.println("로그아웃 요청 받음!");
              session.invalidate(); // 세션 무효화 -> 서버에 저장되어 있는 쿠키를 제거하는 역할을 한다.
              System.out.println("세션 삭제 완료!");
              return ResponseEntity.ok().body("Logout successful");
          }
  ### logout 앞단 
      userProfile.vue : 버튼 클릭시 백단의 /logout 실행 + localStorage.removeItem 을 통해 로컬 스토리지 정보 초기화
        const logout = async () => {
          console.log("로그아웃 실행 됨");
          // 1️⃣ 로컬 스토리지에서 userInfo 삭제
          localStorage.removeItem("userInfo");
        
          await fetch("http://localhost:8282/auth/logout",{
            method: "POST",
            credentials: "include",
          });
        
          window.location.href = "https://accounts.google.com/logout";
        
          // ✅ 4️⃣ 로그아웃 후 로그인 페이지로 이동
          setTimeout(() => {
            window.location.href = "/login";
          }, 1000);
        }

### **추가 구현 : 회원정보 (CLUD) 구현 : 2025년 3월 19일 09:48 ~ 진행중

# 📌 추가 구현: 회원정보 (CRUD)  
📅 **구현 기간**: 2025년 3월 19일 18:48 ~ 3월 21일 15:30  

---

## 🚀 사원정보 CRUD 구현 

### 🔍 사원 조회 (Select)  
        [front] 전달한 정보 없음, ex) 조건을 줘야 할 경우 String 형태로 값을 전달, params 형태로 넣어서 전달 가능
        [back] String -> @RequestParam String 값
### 🔍 사원 추가 (insert)  
        [front] select 조회된 정보를 params:{} 에 담아서 -> [back] 전달
### 🔍 사원 수정 (update)  
        [front] update 정보 Get 방식 params:{} 형태로 전달 -> [back] @RequestParam Map<String, String> params 형태로 받는다.
### 🔍 사원 삭제 (delete)  
        [front] select 정보를 선택 params:{} 에 담아서 -> [back] 전달

## 🚀 Front, Back 코드 
        [front 구조]
          employee.vue : 컴포넌트를 관리하는 최상단 컴포넌트
          empInfo.vue : 사원정보 + 사원 추가, 수정, 사원조회, 삭제 등의 컴포넌트를 관리하는 중단 컴포넌트
          employeeInfo.vue : 사원조회, 추가, 수정, 삭제 버튼이 동작하는 함수를 저으이 한 컴포넌트
          ClientEmployeeAddModal : 사원 추가 관련 모달창 + 함수 실행
          ClientEmployeeEditModal : 사원 수정 관련 모달창 + 함수 실행
          + pinia 를 통해 정보를 관리, select 조회를 통해 나온 결과 pinia의 스토어에 저장, 저장된 정보를 
            활용해 사원수정, 사원삭제 사용
          + api 컴포넌트를 활용 api 주소를 메번 사용하는 것이 아닌 따로 만들어 모듈화를 진행 import 해서 사용할 수 있게 추가
        
        [back 구조]
          employeeController
            조회, 추가, 수정, 삭제 관련 메서드를 만들어 
            @RequestMapping(value = "/...", method=RequestMethod.Get) 을 통해 fornt 의 url 요청을 받을 수 있게 구현
            
          employeeServiceFacade 퍼사드 인터페이스
            퍼사드(Facade) 패턴 -> 복잡한 시스템을 단순하게 사용할 수 있도록 하는 인터페이스
              = 즉, 여러개의 서비스 클래스를 하나의 진입점에서 관리하는 구조이다.
              
          employeeSerivceFacadeImpl 퍼사드 클래스
            여러 서비스 로직을 묶어서 제공하는 클래스 
              mybatis, jpa 를 활용한 db 연결이 여기서 일어난다.

          EmployeeDAO
            mybatis를 사용한 DAO(Data Access Object) 인터페이스
            테이터베이스와 직접 소통하는 역할
            , sql을 실행하는 함수들을 정의한 곳

          sqlMapEmployee.xml
            sql 쿼리가 실행되는 부분

