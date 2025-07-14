<script setup lang="ts">
import { VDataTableServer } from 'vuetify/labs/VDataTable'
import { paginationMeta } from '@api-utils/paginationMeta'

// Data table options
const itemsPerPage = ref(5)
const page = ref(1)
const sortBy = ref()
const orderBy = ref()

// Update data table options
const updateOptions = (options: any) => {
  page.value = options.page
  sortBy.value = options.sortBy[0]?.key
  orderBy.value = options.sortBy[0]?.order
}

const vehiclesData = {
  totalVehicles: 1,
  vehicles: [
    {
      location: 'DN20231235',
      startRoute: '예일창고',
      endRoute: '33, Jinjuyeok-ro 73beon-gil',
      itemName: '카메라 본체 NO.2',
      progress: 100,
      startCity: '예일창고',
      endCity: '33, Jinjuyeok-ro 73beon-gil',
    },
    {
      location: 'DN20231237',
      startRoute: '예일창고',
      endRoute: 'Sangnam-dong, Changwon-si',
      itemName: '디지털카메라 NO.1',
      progress: 70,
      startCity: '일산창고',
      endCity: 'Sangnam-dong, Changwon-si',
    },
    {
      location: 'DN20231229',
      startRoute: '예일창고',
      endRoute: 'Gimhae Jinyoung',
      itemName: '디지털카메라 NO.2',
      progress: 55,
      startCity: '성진창고',
      endCity: 'Gimhae Jinyoung',
    },
  ],
}

const vehicles = computed(() => vehiclesData.vehicles)
const totalVehicles = computed(() => vehiclesData.totalVehicles)

const test = () => {
  console.log('111', vehicles)
  console.log('222', vehicles.value)
}

const headers = [
  { title: '배송번호', key: 'location' },
  { title: '출발지', key: 'startRoute' },
  { title: '도착지', key: 'endRoute' },
  { title: '상품명', key: 'itemName' },
  { title: 'PROGRESS', key: 'progress' },
]

const resolveChipColor = (warning: string) => {
  if (warning === 'No Warnings')
    return 'success'
  if (warning === 'fuel problems')
    return 'primary'
  if (warning === 'Temperature Not Optimal')
    return 'warning'
  if (warning === 'Ecu Not Responding')
    return 'error'
  if (warning === 'Oil Leakage')
    return 'info'
}
</script>

<template>
  <VCard>
    <VCardItem title="배송상세">
      <template #append>
        <!-- <VBtn @click="test">
          버튼
        </VBtn> -->
        <MoreBtn />
      </template>
    </VCardItem>

    <VDivider />
    <VDataTableServer
      v-model:items-per-page="itemsPerPage"
      v-model:page="page"
      :items-per-page-options="[
        { value: 5, title: '5' },
        { value: 10, title: '10' },
        { value: 20, title: '20' },
        { value: -1, title: '$vuetify.dataFooter.itemsPerPageAll' },
      ]"
      :items-length="totalVehicles"
      :items="vehicles"
      :headers="headers"
      show-select
      class="text-no-wrap"
      @update:options="updateOptions"
    >
      <template #item.location="{ item }">
        <VAvatar
          variant="tonal"
          class="me-2"
          size="40"
        >
          <VIcon
            icon="tabler-truck"
            size="24"
          />
        </VAvatar>
        <NuxtLink
          class="text-body-2 text-high-emphasis font-weight-medium"
          :to="{ name: 'apps-logistics-fleet' }"
        >
          DN{{ item.location }}
        </NuxtLink>
      </template>

      <template #item.startRoute="{ item }">
        {{ item.startCity }}, {{ item.startCountry }}
      </template>

      <template #item.endRoute="{ item }">
        {{ item.endCity }}, {{ item.endCountry }}
      </template>

      <template #item.warnings="{ item }">
        <VChip
          :color="resolveChipColor(item.warnings)"
          label
        >
          {{ item.warnings }}
        </VChip>
      </template>

      <template #item.progress="{ item }">
        <div
          class="d-flex align-center gap-x-4"
          style="min-inline-size: 240px;"
        >
          <div class="w-100">
            <VProgressLinear
              :model-value="item.progress"
              rounded
              color="primary"
              :height="8"
            />
          </div>
          <div>
            {{ item.progress }}%
          </div>
        </div>
      </template>

      <!-- pagination -->
      <template #bottom>
        <VDivider />

        <div class="d-flex align-center justify-sm-space-between justify-center flex-wrap gap-3 pa-5 pt-3">
          <p class="text-sm text-disabled mb-0">
            {{ paginationMeta({ page, itemsPerPage }, totalVehicles) }}
          </p>

          <VPagination
            v-model="page"
            :length="Math.ceil(totalVehicles / itemsPerPage)"
            :total-visible="$vuetify.display.xs ? 1 : Math.min(Math.ceil(totalVehicles / itemsPerPage), 5)"
          >
            <template #prev="slotProps">
              <VBtn
                variant="tonal"
                color="default"
                v-bind="slotProps"
                :icon="false"
              >
                Previous
              </VBtn>
            </template>

            <template #next="slotProps">
              <VBtn
                variant="tonal"
                color="default"
                v-bind="slotProps"
                :icon="false"
              >
                Next
              </VBtn>
            </template>
          </VPagination>
        </div>
      </template>
    </VDataTableServer>
  </VCard>
</template>
