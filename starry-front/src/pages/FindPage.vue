<script setup lang="ts">
import {ref} from "vue";
import {showToast} from "vant";


const active = ref(0);
const onClickTab = ({ title }) => showToast(title);
</script>

<template>
  <van-tabs v-model:active="active" @click-tab="onClickTab">
    <van-tab to="/find" title="伙伴推荐" />
    <van-tab to='/find/team-find' title="学习小队" />
  </van-tabs>

  <RouterView/>
</template>

<style scoped>

</style>