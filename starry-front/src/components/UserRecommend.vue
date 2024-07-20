<script setup lang="ts">

import {onMounted, ref} from "vue";
import {getCurrentUser} from "@/apis/user";
import request from '../utils/http';
import {useRouter} from "vue-router";
import {showFailToast} from "vant";
import {useUserStore} from "@/stores/userStore";


const router = useRouter();
const user = ref();
const userList = ref([]);
const userStore = useUserStore(); // 获取 Pinia store

onMounted(async () => {
  // 获取当前用户
  await userStore.getCurrentUser();

  // 检查用户是否存在
  if (!userStore.user) {
    router.push('/user/login');
    return;
  }

  // 将用户信息赋值给本地变量
  user.value = userStore.user;

  // 获取推荐用户列表
  try {
    const res2 = await request({
      url: '/user/recommend',
      method: 'GET'
    });
    // @ts-ignore
    if (res2.code === 0) {
      userList.value = res2.data;
    } else {
      showFailToast("系统错误，请重试！");
    }
  } catch (error) {
    showFailToast("系统错误，请重试！");
  }
});

// onMounted(async () => {
//   const res = await getCurrentUser();
//   if (!res) {
//     router.push('/user/login');
//   }
//   if (res && res.data) {
//     user.value = res.data;
//   }
//
//   const res2 = await request({
//     url: '/user/recommend',
//     method: 'GET'
//   })
//   // @ts-ignore
//   if (res2 && res2.code === 0) {
//     userList.value = res2.data;
//   } else {
//     showFailToast("系统错误，请重试！");
//   }
// })
</script>

<template>
  <div v-for="user in userList" :key="user.id">
    <van-card
        :desc="user.profile"
        :title="user.username"
        :thumb=user.avatarUrl
    >
      <template #tags>
        <van-tag plain type="primary" v-for="tag in JSON.parse(user.tags)">{{tag}}</van-tag>
      </template>
    </van-card>
  </div>
</template>

<style scoped>

</style>