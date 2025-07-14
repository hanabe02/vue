<script lang="ts" setup>
const nextPage = () => {
    console.log('여기입니다')
}
</script>

<template>
    <div class="bookmark-wrapper align-items-left d-lg-flex ml-2" style="cursor: pointer;" @click="nextPage">
        <VIcon class="tabler-users" />
        <span class="ml-50">인사</span>
    </div>
</template>
