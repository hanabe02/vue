<script setup lang="ts">
import MpsRegisteredByContract from "./MpsRegisteredByContract.vue";
import MpsRegisteredBySalesPlan from "./MpsRegisteredBySalesPlan.vue";

const selectedOption = ref("a");

// const selectedComponent = ref("수주로 MPS 등록 건");

watch(selectedOption, (newValue, oldValue) => {
  // console.log("newValue is : ", newValue);
  if (newValue === "수주로 MPS 등록 건") {
    console.log("수주");
  } else if (newValue === "판매계획으로 MPS 등록 건") {
    console.log("판매계획");
  }
});

// const radioChange = (e) => {
//   const radio = e.target.value;
//   console.log(radio);
//   if (radio === "수주로 MPS 등록 건") {
//     console.log("수주");
//   } else if (radio === "판매계획으로 MPS 등록 건") {
//     console.log("판매계획");
//   }

// };

// function handleChange() {
//   if (selectedOption.value === "a") {
//     selectedComponent.value = '<MpsRegisteredByContract />';
//   } else if (selectedOption.value === "b") {
//     selectedComponent.value = '<MpsRegisteredBySalesPlan />';
//   }
// }
</script>

<template>
  <div class="page_wrapper">
    <div class="header_wrap">
      <div class="radio_wrap">
        <div>
          <input
            type="radio"
            id="radioA"
            name="radio"
            value="a"
            v-model="selectedOption"
          />
          <label for="radioA" style="font-size: 15px; margin-right: 10px"
            >&nbsp; 수주로 MPS 등록 건</label
          >
          <input
            type="radio"
            id="radioB"
            name="radio"
            value="b"
            v-model="selectedOption"
          />
          <label for="radioB" style="font-size: 15px"
            >&nbsp; 판매계획으로 MPS 등록 건</label
          >
        </div>
      </div>
    </div>
      <MpsRegisteredByContract v-if="selectedOption === 'a'" />
      <MpsRegisteredBySalesPlan v-if="selectedOption === 'b'" />
  </div>
</template>

<style scoped>
.page_wrapper {
  /* border: 3px solid grey; */
}

.header_wrap {
  position: relative;
  width: auto;
  /* border: 3px solid orange; */
  height: 70px;
  border-radius: 5px;
}
.radio_wrap {
  margin-bottom: 20px;
  /* border: 1px solid red; */
}

.date {
  border: none;
  width: 150px;
  height: 40px;
  border-radius: 3px;
  margin-right: 20px;
  border: 1px solid skyblue;
}

.startDate_wrap {
  display: inline-block;
}

.endDate {
  margin-left: 50px;
}
.btn_search {
  border: none;
  width: 100px;
  height: 40px;
  border-radius: 3px;
}

.btn_register {
  border: none;
  width: 100px;
  height: 40px;
  border-radius: 3px;
}

.btn_estimate {
  position: absolute;
  right: 0px;

  border: none;
  width: 120px;
  height: 40px;
  border-radius: 3px;
}

.gap {
  height: 50px;
}
</style>
