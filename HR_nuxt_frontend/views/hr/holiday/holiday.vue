<script setup lang="ts">
import { baseStore } from '@/store/hr/base'

const selectedYear = ref('')
const selectedMonth = ref('')
const month_list: Ref<string[]> = ref([])
const year_list: Ref<string[]> = ref([])

onMounted(() => {
  fetchYearList()
  fetchMonthList()
})

const fetchYearList = async () => {
  await baseStore().FETCH_YEARLIST()
  year_list.value = Object.values(baseStore().yearList).map((item: any) => item.year)
}

// 월 조회
const fetchMonthList = async () => {
  await baseStore().FETCH_MONTHLIST()
  month_list.value = Object.values(baseStore().monthList).map((item: any) => item.month)
}
</script>
