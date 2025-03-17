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

**Bitbucket **
  git 저장소를 관리하는 저장소

**클라우드**
  가상 공간이라는 개념 -> 인터넷에서 데이터를 저장하고 실행하는 공간이다.
  
  클라우드의 특징
  1. 어디서든 접근 가능
  2. 물리적 서버 불필요
  3. 공유 및 협업 용이

  클라우드 기반 Bitbucket : bitbucket cloud 를 사용하면 코드를 로컬 에 저장하는 게 아니라 bitbucket 서버에 저장하게 된다.
  

**sourceTree **
  git 을 편리하게 사용할 수 있게 도와주는 툴 

**추가 구현 : 로그인 부분 + 로그아웃 코드 구현, 계획 날짜 2025년 3월 14일** 
    - 로그인 부분은 -> 하드 코딩 되어 있고
    - 로그아웃 부분은 -> 구현 x




