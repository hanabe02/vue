<script lang="ts" setup>

const nextPage = () => {
    console.log('하위')
}
</script>

<template>
    <div class="bookmark-wrapper align-items-left d-lg-flex ml-2" style="cursor: pointer;" @click="nextPage">
        <VIcon class="tabler-credit-card" />
        <span class="ml-50">회계</span>
    </div>
</template>
