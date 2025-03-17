<script setup lang="ts">
// Composition API를 사용한 setup 방식
// 다른 컴포넌트에서 사용할 수 있게 모듈화 or 캡슐화 한 코드이다.

interface Props {
  icon?: string
  iconSize?: string
}
// Props : 인터페이스를 정의, 이 컴포넌트에서 받을 입력값의 타입 지정

const props = withDefaults(defineProps<Props>(), {
  icon: 'tabler-x',
  iconSize: '20',
})
// defineProps 인터페이스를 기반으로 props를 받도록 설정
// withDefaults(...) -> props가 전달되지 않을 경우 기본값을 설정
// icon 기본값 : tabler-x
// iconSize 기본값 : 20
</script>

<template>
  <IconBtn
    variant="elevated"
    class="v-dialog-close-btn"
  >
  <!--
    iconBtn : vuetify의 버튼 컴포넌트
      variant=elevated 버튼을 입체적으로 보이게 하는 스타일
      css -> 스타일을 추가할 수 있도록 클래스 지정
  -->
    <VIcon
      :icon="props.icon"
      :size="props.iconSize"
    />
    <!-- 
      vIcon : vuetify의 아이콘 컴포넌트 
        아이콘을 props.icon 값으로 설정
        아이콘 크기를 props.iconSize 값으로 설정
    -->
  </IconBtn>
</template>
