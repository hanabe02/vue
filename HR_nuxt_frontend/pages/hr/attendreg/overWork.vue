<script lang="ts" setup>
import OverWorkReqListTap from '@/views/hr/attendreg/overwork/overWorkReqListTap.vue'
import OverWorkReqTap from '@/views/hr/attendreg/overwork/overWorkReqTap.vue'

const tab = ref('personal-info')
</script>

<template>
  <h1 class="mb-6">초과근무 신청</h1>
  <VCard>
    <VTabs
      v-model="tab"
      height="70"
    >
      <VTab value="personal-info">
        초과근무 신청
      </VTab>
      <VTab value="account-details">
        초과근무 조회
      </VTab>
    </VTabs>
  </VCard>
  <VCard flat>
    <VCardText>
      <VWindow
        v-model="tab"
        class="disable-tab-transition"
      >
        <VWindowItem value="personal-info">
          <OverWorkReqTap />
        </VWindowItem>

        <VWindowItem value="account-details">
          <OverWorkReqListTap />
        </VWindowItem>
      </VWindow>
    </VCardText>
  </VCard>
</template>
