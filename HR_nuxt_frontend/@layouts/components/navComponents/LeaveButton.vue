<script lang="ts" setup>
import axios from 'axios';
// 오늘 날짜 가져오기
const today = new Date().toISOString().slice(0, 10)

const handleLeave = async () => {
  try {
    await axios.post("http://localhost:8282/api/attendance/leave", {
        empCode: 'EMP-01', // 로그인한 사번 또는 사용자 코드
        workDate: today,
    })
    alert('퇴장 처리 완료!')
  } catch (error) {
    console.error('퇴장 실패', error)
    alert('퇴장 처리 실패')
  }
}
</script>

<template>
  <v-btn color="primary" @click="handleLeave">
    퇴장
  </v-btn>
</template>

<style scoped>
/* 필요하면 버튼 스타일 추가 */
</style>
