<script lang="ts" setup>
import axios from "axios";

const today = new Date().toISOString().slice(0,10)

const handleEnter = async () => {
  try {
    await axios.post("http://localhost:8282/api/attendance/enter", {
  empCode: 'EMP-01',
  workDate: today,
})
    alert('입장처리 완료')
}
catch(error){
    console.log('입장실패',error)
    alert('입장 처리 실패')
}
}

</script>

<template>
      <v-btn color="primary" @click="handleEnter">
    입장
  </v-btn>
</template>

<style scoped>
/* 필요하면 버튼 스타일 추가 가능 */
</style>
