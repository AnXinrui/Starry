<script setup lang="ts">
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const activeTab = ref(0);
const router = useRouter();
const route = useRoute();

// Watch the route to update the active tab index
watch(
    () => route.path,
    (newPath) => {
      switch (newPath) {
        case '/team/created-teams':
          activeTab.value = 0; // 将路径 '/team/created-teams' 对应到第二个 tab
          break;
        case '/team/joined-teams':
          activeTab.value = 1; // 将路径 '/team/joined-teams' 对应到第一个 tab
          break;
        default:
          activeTab.value = 0;
      }
    },
    { immediate: true }
);

const navigateTo = (path: string) => {
  alert(path)
  router.push(path);
};
</script>

<template>
  <van-tabs v-model:active="activeTab">
    <van-tab title="我创建的队伍" to="/team/created-teams"></van-tab>
    <van-tab title="我加入的队伍" to="/team/joined-teams"></van-tab>
  </van-tabs>

  <router-view></router-view>
</template>

<style scoped>

</style>