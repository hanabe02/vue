<script setup lang="ts">
import { ref } from 'vue'
import { attenStore } from '@/store/hr/attendance'


const applyYearMonth = ref('2025-06') // 기본값 예시

// ✅ 집계 실행
const handleAggregate = async () => {
  if (!applyYearMonth.value) return alert('📌 집계 대상 연월을 입력하세요!')
  await attenStore().aggregateMonth(applyYearMonth.value)
}

// ✅ 목록 조회
const handleSearch = async () => {
  if (!applyYearMonth.value) return alert('📌 조회할 연월을 입력하세요!')
  await attenStore().loadMonthList(applyYearMonth.value)
}

// ✅ 마감 처리
const handleClose = async () => {
  if (attenStore().monthAttdList.length === 0) return alert('⚠️ 마감할 데이터가 없습니다!')
  await attenStore().finalizeMonth()
}
</script>

<template>
  <div class="p-6 space-y-4">
    <h1 class="text-2xl font-bold">📅 월근태 집계</h1>

    <div class="flex items-center gap-4">
      <input
        v-model="applyYearMonth"
        placeholder="예: 2025-06"
        class="border rounded px-3 py-2"
      />
      <v-btn @click="handleAggregate">
        집계 실행
      </v-btn>
      <v-btn @click="handleSearch" >
        목록 조회
      </v-btn>
      <v-btn @click="handleClose">
      마감 처리
      </v-btn>
      
    </div>

    <!-- ✅ 월근태 테이블 -->
    <table class="w-full mt-6 border">
      <thead class="bg-gray-100">
        <tr>
          <th class="border px-2 py-1">사번</th>
          <th class="border px-2 py-1">연월</th>
          <th class="border px-2 py-1">총 근무시간</th>
          <th class="border px-2 py-1">지각일수</th>
          <th class="border px-2 py-1">마감여부</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in attenStore().monthAttdList" :key="row.empCode + row.applyYearMonth">
          <td class="border px-2 py-1">{{ row.empCode }}</td>
          <td class="border px-2 py-1">{{ row.applyYearMonth }}</td>
          <td class="border px-2 py-1">{{ row.workHour }}</td>
          <td class="border px-2 py-1">{{ row.lateDays }}</td>
          <td class="border px-2 py-1">
            {{ row.finalizeStatus === 'Y' ? '✅ 마감' : '⏳ 진행중' }}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
input {
  width: 200px;
}
</style>
