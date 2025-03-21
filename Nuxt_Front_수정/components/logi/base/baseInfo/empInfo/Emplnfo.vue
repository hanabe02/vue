<script setup lang="ts">
import EmployeeInfo from './EmployeeInfo.vue';

const userTab = ref(null)

const tabs = [
    { icon: 'tabler-user-check', title: '사원 조회' },
]

</script>

<template>
    <div class="text-2xl mb-2">
        <p> 사원 정보</p>
    </div>
    <VRow>
        <VCol>
            <VTabs v-model="userTab" class="v-tabs-pill">
                <VTab v-for="tab in tabs" :key="tab.icon">
                    <VIcon :size="18" :icon="tab.icon" class="me-1" />
                    <span>{{ tab.title }}</span>
                </VTab>
            </VTabs>

            <VWindow v-model="userTab" class="mt-6 disable-tab-transition" :touch="false"></VWindow>
            <VWindow>
                <VWindowItem>
                    <EmployeeInfo />
                </VWindowItem>
            </VWindow>
        </VCol>
    </VRow>
</template>

<style></style>