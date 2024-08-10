<script setup lang="ts">

import {onMounted, ref, toRefs} from "vue";
import {getCurrentUser} from "@/apis/user";
import request from '../utils/request';
import {useRouter} from "vue-router";
import {showFailToast} from "vant";
import useUserStore from "@/store/modules/user";


const router = useRouter();
const userList = ref([]);
const userStore = useUserStore(); // 获取 Pinia store
const { user } = toRefs(userStore);

onMounted(async () => {
  if (userStore.user == null) {
    try {
      await userStore.getCurrentUserInfo();
    } catch (error) {
      // 处理获取用户信息失败的情况
      router.push('/user/login');
    }
  }
  if (user.value == null) {
    router.push('/user/login');
  }

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