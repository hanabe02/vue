# vue    

**vue - nuxt - typescirpt 사용**

vuetify 컴포넌트 사용
  : vue.js 기반의 인기 있는 material Design ui 프레임워크로 다양한 ui 컴포넌트를 제공하여 개발자가 빠르게 웹 애플리케이션을 구축 할 수 있도록 한다.

vue3 버전을 사용하여 vuex -> pinia 를 사용
  pinia 는 상태관리 라이브러리로 
    vue.js 3에서 공식적으로 권장하는 상태 관리 라이브러리이다. 
    + react 에서 redux 같은 느낌이다.
      직관적인 api 를 사용하여 간단하게 상태를 정의 사용이 가능핟.
      타입스크립트 친화적 - 타입스크립트를 쉽게 적용할 수 있다.
      모듈화된 스토어 - 상태를 여러 개의 작은 스토어로 나눠서 관리할 수 있다.
      반응형 지원 vue 의 ref 와 computed 를 내부적으로 활용하여 반응형 상태를 제공한다.
      플러그인 지원 - vue DevTools 와 통합되며, 플러그인을 쉽게 추가 가능하다.

**Vue 앞단**
  pnpm 사용 
  
**Vue 뒷단**
  sql - 프로시저 사용
  
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




