<script setup lang="ts">
import FlatPickr from 'vue-flatpickr-component'
import { useTheme } from 'vuetify'

// @ts-expect-error There won't be declaration file for it
import { VField, filterFieldProps, makeVFieldProps } from 'vuetify/lib/components/VField/VField'

// @ts-expect-error There won't be declaration file for it
import { VInput, makeVInputProps } from 'vuetify/lib/components/VInput/VInput'

// @ts-expect-error There won't be declaration file for it
import { filterInputAttrs } from 'vuetify/lib/util/helpers'
import { useConfigStore } from '@core/stores/config'

// inherit Attribute make false
defineOptions({
  inheritAttrs: false,
})
// 부모에서 자식 컴포넌트로 전달된 속성을 자동으로 적용하지 않도록 설정하는 역할
// 기본적으로 vue 는 부모의 속성을 자식 컴포넌트의 최상위 요소에 자동 추가된다.


const props = defineProps({
  // 부모 컴포넌트에서 전달할 props 속성들의 타입을 지정하는 역할
  // 즉, 자식 컴포넌트에서 사용할 props 의 타입과 기본값을 정의하는 코드

  // 부모에서 전달하는 속성은 5개에서 6개 정도인데.
  // defindeProps() 에는 이렇게 많은 속성들의 정의되어 있는 이유?

  /*
    확정성 때문, appdateTimepicker 는 단순히 v-mode, label, placeholder, readonly 같은 기본 속성만 처리하는게 아니라
      추가적인 기능을 제공하고 Vuetify 와 연동하기 위해서 다양한 속성을 미리 정의한다.
  */
  autofocus: Boolean,
  counter: [Boolean, Number, String] as PropType<true | number | string>,
    // counter 는 true, 1, "hi" 이런 값을 받을 수 있다.
    // propType 부분에 true 인유는 bool 값을 true 만 적용
  counterValue: Function as PropType<(value: any) => number>,
    // 함수 타입의 속성
    // as PropType<(value: any) => number>를 사용하여 반환 타입 number 로 강제
    // 즉! (value: any) => number 이런 형태의 함수를 받아야 한다.
  prefix: String,
  placeholder: String,
  persistentPlaceholder: Boolean,
  persistentCounter: Boolean,
  suffix: String,
  type: {
    type: String,
    default: 'text',
  },
  modelModifiers: Object as PropType<Record<string, boolean>>,
  // 객체 타입, record 를 사용하여 속성은 key : string, 값 : boolean
  ...makeVInputProps({
    // makeVInputProps()는 Vuetify의 VInput에서 사용하는 속성들을 포함하는 함수
    // density: compact -> 입력 필드의 높이를 줄여서 조밀하게 표시
    density: 'compact',
    // hideDetails : auto -> 에러 메시지나 도움말을 자동으로 숨김
    hideDetails: 'auto',
  }),
  // 즉! appdateTimePicker 컴포넌트가 vInput 의 모든 속성을 사용할 수 있도록 설정
  ...makeVFieldProps({
    variant: 'outlined',
    color: 'primary',
  }),
  // 마찬가지 vfield 에서 지원하는 속성을 추가하여 appdatetimepicker 에서 활용 가능하게 한다.
})

const emit = defineEmits<Emit>()
// emit 은 vue 에서 자식 컴포넌트가 -> 부모 컴포넌트로 이벤트를 발생시키는 역할을 한다.

interface Emit {
  (e: 'click:control', val: MouseEvent): true
  // click:control 이벤트 명, mouseEvent 전달할 값, 반환값 : true
  (e: 'mousedown:control', val: MouseEvent): true
  (e: 'update:focused', val: MouseEvent): true
  (e: 'update:modelValue', val: string): void
  (e: 'click:clear', el: MouseEvent): void
}
// 이벤트 이름과 함께 전달할 값의 타입을 정의, 
// 이벤트가 발생할 때 무슨 데이터를 전달할지 typescript 가 체크할 수 있도록 설정

const configStore = useConfigStore()
// Pinia 상태 관리 라이브러리에서 사용하는 함수
// configstore를 사용하면 애플리케이션의 전역 설정을 가져오고 수정할 수 있음.
const attrs = useAttrs()
// attrs 는 부모가 전달한 props 외의 속성을 저장한 객체

const [rootAttrs, compAttrs] = filterInputAttrs(attrs)
// attrs 부모에서 전달된 속성을 -> 두개의 그룹으로 분리 역할
// filterInputAttrs() : attrs 속성들을 분류 rrotAttrs, compAttrs 로 나누는 역할을 한다.
// rootAttrs : 최상위 요소에 전달할 속성, 
// compAttrs : 내부의 input 또는 flatPickr 같은 특정 요소에 전달할 속성

/*
  나누는 이유
    rootAttrs
      class, style, id, data
    compAttrs
      placeholder
      disabled
      readonly
      autocomplete
      maxlength
*/


// eslint-disable-next-line @typescript-eslint/no-unused-vars
const [{ modelValue: _, ...inputProps }] = VInput.filterProps(props)
// filterProps를 호출하여 props 에서 특정 속성만 필터링한다.
// 결과는 배열로 반환되며, 첫 번째 요소를 구조 분해 할당으로 받는다.
// modelValue를 제외한 나머지 props를 inputProps로 관리

const [fieldProps] = filterFieldProps(props)
// filterFieldProps를 호출하여 props에서 특정 필드 관련 속성만 필터링
// 반환된 배열의 첫 번째 요소를 fieldProps 로 저장
// props 에서 필드 관련 속성만 따로 추출하여 fieldProps 에 저장

// 여기서 filterFieldProps 함수는 특정 Field 관련 속성만 걸러내는 역할을 한다.
// 여기서 Field 는 보통 입력 필드 input field 와 관련된 속성을 의미한다.

const refFlatPicker = ref()
// ref() 함수를 사용하여 FlatPicker 날짜 선택기 참조를 생성한다.
// refFlatePicker.value 로 FlatPicker 컴포넌트를 직접 조작할 수 있다.

const { focused } = useFocus(refFlatPicker)
// useFocus(refFlatPicker) 호출 flatPicker 의 포커스 상태를 감지
// 날짜 선택기가 현재 포커스 상태인지 확인하고, ui 동작을 제어할 수 있도록 한다.
const isCalendarOpen = ref(false)
// 기본값은 false -> true 인 경우 달력이 열려 있는 상태를 의미
const isInlinePicker = ref(false)
// 기본값은 false -> true가 되면 인라인(즉, 팝업이 아닌 고정된 형태) 날짜 선택기를 렌더링 할 수 있다.

// flat picker prop manipulation

if (compAttrs.config && compAttrs.config.inline) {
  console.log("디버깅포인트 : " + compAttrs.config);
  console.log("디버깅포인트 : " + compAttrs.config.inline);
  isInlinePicker.value = compAttrs.config.inline
  // 반응형 변수 isInlinePicker의 값을 config.inline 값과 동일하게 설정
  Object.assign(compAttrs, { altInputClass: 'inlinePicker' })
  // compAttrs 객체에 altInputClass 속성을 추가
  // 즉, compAttrs.altInputClass 와 동일한 동작을 하게 만든다.
}

// v-field clear prop
const onClear = (el: MouseEvent) => {
  el.stopPropagation()
  // MouseEvent 객체 e1 이 상위 요소까지 전파되는 것을 막는다.
  // 즉, 클릭 이벤트가 부모 요소까지 전달되지 않도록 한다.

  nextTick(() => {
    // nextTick vue()를 사용하여 Dom 업데이트가 끝난 후 코드 실행을 보장한다.
    // 여기서는 모덹밧을 업데이트하는 코드가 실행되지 전에, vue의 반응형 시스템이 모든 업데이트를 반영할 수 있도록 기다린다.
    emit('update:modelValue', '')
    // vue v-model을 업데이트 하는 역할
    // modelvalue를 '' 로 설정하여 입력 필드의 값을 비운다.

    emit('click:clear', el)
    // click:Clear 이벤트를 발생시켜 부모 컴포넌트에서 초기화 버튼 클릭 이벤트를 감지할 수 있도록 한다.
    // 예를들어 부모 컴포넌트에서 @click:clear+handleClear를 설정 handleClear() 함수가 실행될 것이다.
  })
}

const vuetifyTheme = useTheme()
// vue3 에서 제공하는 훅으로, 현재 사용중인 테마 정보를 가져오는 역할
// vuetify는 다크모드, 라이트모드, 커스텀 테마 등의 테마를 지원
// useTheme() 를 사용시 테마 관련 정보를 쉽게 가져올 수 있다.

const vuetifyThemesName = Object.keys(vuetifyTheme.themes.value)
// vuetifyTheme.themes.value는 테마 객체 전체를 반환합니다.
// Object.keys(vuetifyTheme.themes.value)를 호출하면, 테마 이름들의 배열이 반환

// Themes class added to flat-picker component for light and dark support
const updateThemeClassInCalendar = () => {
  // ℹ️ Flatpickr don't render it's instance in mobile and device simulator
  if (!refFlatPicker.value.fp.calendarContainer)
    return

  vuetifyThemesName.forEach(t => {
    refFlatPicker.value.fp.calendarContainer.classList.remove(`v-theme--${t}`)
  })
  // 모든 기존 테마 클래스 제거
  refFlatPicker.value.fp.calendarContainer.classList.add(`v-theme--${vuetifyTheme.global.name.value}`)
  // 현재 vuefity 테마를 캘린더에 적용
}
// Vuetify 테마를 flatpickr 캘린더에 적용하는 역할

watch(() => configStore.theme, updateThemeClassInCalendar)
// configstore.theme 이 변경 될 때마다 updateThemeClassInCalendar 실행

/*
watch(function(){
  return configstore.themel}, upd..);
})
*/

onMounted(() => {
  updateThemeClassInCalendar()
})

const emitModelValue = (val: string) => {
  emit('update:modelValue', val)
}
// update:modelvalue v-model 과 연결된 특별한 이벤트 이름
// 부모에서 v-model 로 연결된 변수의 값이 자동으로  value 로 변경됨

// 즉, emit() 을 실행하면 보모의 esDate=''가 자동으로 실행된다.

const elementId = computed(() => {
  const _elementIdToken = fieldProps.id || fieldProps.label

  return _elementIdToken ? `app-picker-field-${_elementIdToken}-${Math.random().toString(36).slice(2, 7)}` : undefined
  // elementIdToken 값이 존재하면 고유한 id를 생성
  // elementIdToken 값이 없으면 undefined 반환 (즉 id 부여하지 않음)

  // Math.random().toString(36).slice(2, 7);
    // math.random() : 0과1 사이의 랜덤 숫자를 생성
    // .tostring 랜덤 숫자를 영문 숫자 조합의 문자열로 변환
    // .slice 앞의 0. 을 제거하고 5자리의 랜덤 문자열만 남김
})

// elementId 를 동적으로 생성하는 역할
/*
  각 요소 field 가 고유한 id를 가질 수 있도록 Math.random()을 사용하여 고유한 문자열을 추가한다.

  computed() : vue 에서 반응형 데이터를 생성하는 함수
  fieldProps.id 또는 fieldProps.label 이 변경되면 elementId도 자동으로 다시 계산
*/
</script>

<template>
  <div class="app-picker-field">
    <!-- v-input -->
    <VLabel
      v-if="fieldProps.label"
      class="mb-1 text-body-2 text-high-emphasis"
      :for="elementId"
      :text="fieldProps.label"
    />
    <!--
      label 값이 있을 때만 표시
      :for 입력 필드와 연결된 id 설정
      :text 라벨 텍스트
    -->

    <VInput
      v-bind="{ ...inputProps, ...rootAttrs }"
      :model-value="modelValue"
      :hide-details="props.hideDetails"
      :class="[{
        'v-text-field--prefixed': props.prefix,
        'v-text-field--suffixed': props.suffix,
        'v-text-field--flush-details': ['plain', 'underlined'].includes(props.variant),
      }, props.class]"
      class="position-relative v-text-field"
      :style="props.style"
    >
      <template #default="{ id, isDirty, isValid, isDisabled, isReadonly }">
        <!-- v-field -->
        <VField
          v-bind="{ ...fieldProps, label: undefined }"
          :id="id.value"
          role="textbox"
          :active="focused || isDirty.value || isCalendarOpen"
          :focused="focused || isCalendarOpen"
          :dirty="isDirty.value || props.dirty"
          :error="isValid.value === false"
          :disabled="isDisabled.value"
          @click:clear="onClear"
        >
          <template #default="{ props: vFieldProps }">
            <div v-bind="vFieldProps">
              <!-- flat-picker  -->
              <FlatPickr
                v-if="!isInlinePicker"
                v-bind="compAttrs"
                ref="refFlatPicker"
                :model-value="modelValue"
                :placeholder="props.placeholder"
                :readonly="isReadonly.value"
                class="flat-picker-custom-style"
                :disabled="isReadonly.value"
                @on-open="isCalendarOpen = true"
                @on-close="isCalendarOpen = false"
                @update:model-value="emitModelValue"
              />

              <!-- simple input for inline prop -->
              <input
                v-if="isInlinePicker"
                :value="modelValue"
                :placeholder="props.placeholder"
                :readonly="isReadonly.value"
                class="flat-picker-custom-style"
                type="text"
              >
            </div>
          </template>
        </VField>
      </template>
    </VInput>

    <!-- flat picker for inline props -->
    <FlatPickr
      v-if="isInlinePicker"
      v-bind="compAttrs"
      ref="refFlatPicker"
      :model-value="modelValue"
      @update:model-value="emitModelValue"
      @on-open="isCalendarOpen = true"
      @on-close="isCalendarOpen = false"
    />
  </div>
</template>

<style lang="scss">
/* stylelint-disable no-descending-specificity */
@use "flatpickr/dist/flatpickr.css";
@use "@core/scss/base/mixins";

.flat-picker-custom-style {
  position: absolute;
  color: inherit;
  inline-size: 100%;
  inset: 0;
  outline: none;
  padding-block: 0;
  padding-inline: var(--v-field-padding-start);
}

$heading-color: rgba(var(--v-theme-on-background), var(--v-high-emphasis-opacity));
$body-color: rgba(var(--v-theme-on-background), var(--v-medium-emphasis-opacity));
$disabled-color: rgba(var(--v-theme-on-background), var(--v-disabled-opacity));

// hide the input when your picker is inline
input[altinputclass="inlinePicker"] {
  display: none;
}

.flatpickr-time input.flatpickr-hour {
  font-weight: 400;
}

.flatpickr-calendar {
  @include mixins.elevation(6);

  background-color: rgb(var(--v-theme-surface));
  inline-size: 16.625rem;
  margin-block-start: 0.1875rem;

  .flatpickr-day:focus{
    border-color: rgba(var(--v-border-color),var(--v-border-opacity));
    background:  rgba(var(--v-border-color),var(--v-border-opacity));
  }

  .flatpickr-rContainer {
    .flatpickr-weekdays {
      block-size: 2.125rem;
      padding-inline: 0.875rem;
    }

    .flatpickr-days {
      min-inline-size: 16.625rem;

      .dayContainer {
        justify-content: center !important;
        inline-size: 16.625rem;
        min-inline-size: 16.625rem;
        padding-block-end: 0.75rem;
        padding-block-start: 0;

        .flatpickr-day {
          block-size: 2.125rem;
          font-size: 0.9375rem;
          line-height: 2.125rem;
          margin-block-start: 0 !important;
          max-inline-size: 2.125rem;
        }
      }
    }
  }

  .flatpickr-day {
    color: $body-color;

    &.today {
      border-color: rgb(var(--v-theme-primary));

      &:hover {
        border-color: rgb(var(--v-theme-primary));
        background: transparent;
        color: $body-color;
      }
    }

    &.selected,
    &.selected:hover {
      border-color: rgb(var(--v-theme-primary));
      background: rgb(var(--v-theme-primary));
      color: rgb(var(--v-theme-on-primary));

      @include mixins.elevation(2);
    }

    &.inRange,
    &.inRange:hover {
      border: none;
      background: rgba(var(--v-theme-primary), var(--v-activated-opacity)) !important;
      box-shadow: none !important;
      color: rgb(var(--v-theme-primary));
    }

    &.startRange {
      @include mixins.elevation(2);
    }

    &.endRange {
      @include mixins.elevation(2);
    }

    &.startRange,
    &.endRange,
    &.startRange:hover,
    &.endRange:hover {
      border-color: rgb(var(--v-theme-primary));
      background: rgb(var(--v-theme-primary));
      color: rgb(var(--v-theme-on-primary));
    }

    &.selected.startRange + .endRange:not(:nth-child(7n + 1)),
    &.startRange.startRange + .endRange:not(:nth-child(7n + 1)),
    &.endRange.startRange + .endRange:not(:nth-child(7n + 1)) {
      box-shadow: -10px 0 0 rgb(var(--v-theme-primary));
    }

    &.flatpickr-disabled,
    &.prevMonthDay:not(.startRange,.inRange),
    &.nextMonthDay:not(.endRange,.inRange) {
      opacity: var(--v-disabled-opacity);
    }

    &:hover {
      border-color: transparent;
      background: rgba(var(--v-theme-on-surface), 0.08);
    }
  }

  .flatpickr-weekday {
    color: $heading-color;
    font-size: 0.8125rem;
    font-weight: 500;
  }

  .flatpickr-days {
    inline-size: 16.625rem;
  }

  &::after,
  &::before {
    display: none;
  }

  .flatpickr-months {
    border-block-end: 1px solid rgba(var(--v-border-color), var(--v-border-opacity));

    .flatpickr-prev-month,
    .flatpickr-next-month {
      fill: $body-color;

      &:hover i,
      &:hover svg {
        fill: $body-color;
      }
    }
  }

  .flatpickr-current-month span.cur-month {
    font-weight: 300;
  }

  &.open {
    // Open calendar above overlay
    z-index: 2401;
  }

  &.hasTime.open {
    .flatpickr-time {
      border-color: rgba(var(--v-border-color), var(--v-border-opacity));
      block-size: auto;
    }

    .flatpickr-hour,
    .flatpickr-minute,
    .flatpickr-am-pm {
      font-size: 0.9375rem;
    }
  }
}

.v-theme--dark .flatpickr-calendar {
  box-shadow: 0 3px 14px 0 rgb(15 20 34 / 38%);
}

// Time picker hover & focus bg color
.flatpickr-time input:hover,
.flatpickr-time .flatpickr-am-pm:hover,
.flatpickr-time input:focus,
.flatpickr-time .flatpickr-am-pm:focus {
  background: transparent;
}

// Time picker
.flatpickr-time {
  .flatpickr-am-pm,
  .flatpickr-time-separator,
  input {
    color: $body-color;
  }

  .numInputWrapper {
    span {
      &.arrowUp {
        &::after {
          border-block-end-color: rgb(var(--v-border-color));
        }
      }

      &.arrowDown {
        &::after {
          border-block-start-color: rgb(var(--v-border-color));
        }
      }
    }
  }
}

//  Added bg color for flatpickr input only as it has default readonly attribute
.flatpickr-input[readonly],
.flatpickr-input ~ .form-control[readonly],
.flatpickr-human-friendly[readonly] {
  background-color: inherit;
}

// week sections
.flatpickr-weekdays {
  margin-block: 12px;
}

// Month and year section
.flatpickr-current-month {
  .flatpickr-monthDropdown-months {
    appearance: none;
  }

  .flatpickr-monthDropdown-months,
  .numInputWrapper {
    padding: 2px;
    border-radius: 4px;
    color: $heading-color;
    font-size: 0.9375rem;
    font-weight: 500;
    transition: all 0.15s ease-out;

    span {
      display: none;
    }

    .flatpickr-monthDropdown-month {
      background-color: rgb(var(--v-theme-surface));
    }

    .numInput.cur-year {
      font-weight: 500;
    }
  }
}

.flatpickr-day.flatpickr-disabled,
.flatpickr-day.flatpickr-disabled:hover {
  color: $body-color;
}

.flatpickr-months {
  padding-block: 0.75rem;
  padding-inline: 1rem;

  .flatpickr-prev-month,
  .flatpickr-next-month {
    display: flex;
    align-items: center;
    border-radius: 5rem;
    background: rgba(var(--v-theme-surface-variant), var(--v-selected-opacity));
    block-size: 1.75rem;
    inline-size: 1.75rem;
    inset-block-start: 0.75rem !important;
    margin-block: 0.1875rem;
    padding-block: 0.25rem;
    padding-inline: 0.4375rem;
  }

  .flatpickr-next-month {
    inset-inline-end: 1.05rem !important;
  }

  .flatpickr-prev-month {
    /* stylelint-disable-next-line liberty/use-logical-spec */
    right: 3.8rem;
    left: unset !important;
  }

  .flatpickr-month {
    display: flex;
    align-items: center;
    block-size: 2.125rem;

    .flatpickr-current-month {
      display: flex;
      align-items: center;
      padding: 0;
      block-size: 1.75rem;
      inset-inline-start: 0;
      text-align: start;
    }
  }
}

// Update hour font-weight
.flatpickr-time input.flatpickr-hour {
  font-weight: 400;
}
</style>
