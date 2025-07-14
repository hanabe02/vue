<script lang="ts" setup>
defineOptions({
  name: 'AppSelect',
  inheritAttrs: false,
  // inherifAttrs: false 부모의 모든 속성이 자동으로 적용되는 걸 막고, 
  // 우리가 직접 $attrs를 통해 전달하도록 설정하는 것이다.
})
// defineOptions() : 
  // 컴포넌트 이름을 설정하고, 부모에서 넘어온 속성 처리를 어떻게 
  // 할지 정하는 부분이다.

const elementId = computed(() => {
  const attrs = useAttrs()
  const _elementIdToken = attrs.id || attrs.label

  return _elementIdToken ? `app-select-${_elementIdToken}-${Math.random().toString(36).slice(2, 7)}` : undefined
})

const label = computed(() => useAttrs().label as string | undefined)
// useAttrs() 는 부모가 appSelect 에 전달한 모든 속성을 가져오는 함수이다.
// useAttrs().label 을 사용하여 label 값을 가져온다. label 값이 computed 속성에 저장된다.
// 이후 VLabel 에서 사용됨
</script>

<template>
  <div
    class="app-select flex-grow-1"
    :class="$attrs.class"
  >
    <VLabel
      v-if="label"
      :for="elementId"
      class="mb-1 text-body-2 text-high-emphasis"
      :text="label"
    />
    <VSelect
      v-bind="{
        ...$attrs,
        class: null,
        label: undefined,
        variant: 'outlined',
        id: elementId,
        menuProps: { contentClass: ['app-inner-list', 'app-select__content', 'v-select__content', $attrs.multiple !== undefined ? 'v-list-select-multiple' : ''] },
      }"
    >
    <!-- 
      $attrs : 부모에서 저달된 모든 속성을 그대로 VSelect에 전달한다. 
      // 13번 줄 확인
      v-select 는 드롭다운 선택 박스 역할을 하는 Vuetify 컴포넌트
        -> 사용자가 클릭하면 옵션 목록이 나타남
        드롭다운 기능 -> 내부적으로 input 요소나 button 이 아니라 
        컴포넌트 자체 클릭이 가능
         -> 클릭시 옵션 리스트 메뉴가 나타난다.
      menuProps란?
        vuetify의 vselect 컴포넌트에서 드롭다운 메뉴에 대한 추가 속성을 설정하는 옵션이다.

        multiple 속성이 있을 경우 v-list select multiple 클래스를 추가 다중 선택 스타일을 반영한다.
     -->
      <template
        v-for="(_, name) in $slots"
        #[name]="slotProps"
      >
      <!--
        $slots 부모 컴포넌트에서 appselect 에 전달한 모든 슬롯을 포함하는 객체이다.
        v-for 를 사용하여 $slots의 각 슬롯을 반복하면서 name을 가져온다.

        이때 부모에서는 slot 을 전달하지 않기 때문에, 내부에서 slot 처리
        
      -->
        <slot
          :name="name"
          v-bind="slotProps || {}"
        />
      </template>
    </VSelect>
  </div>
</template>
