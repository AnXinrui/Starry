<script setup lang="ts">

import {computed, onMounted, ref} from "vue";
import {getCurrentUser} from "@/apis/user";
import {useRouter} from "vue-router";

const router = useRouter();
const user = ref();

const genderLabel = computed(() => {
  // console.log(user.value.gender);
  return user.value.gender === 0 ? '男' : '女';
});

onMounted(async () => {
  const res = await getCurrentUser();
  console.log(res, 'user');
  if (!res) {
    // 如果获取当前用户失败或用户不存在，重定向到登录页面
    router.push('/user/login');
  }
  user.value = res.data;
})

const toEdit = (editKey: string, editName: string, currentValue: string) => {
  router.push({
    path: '/user/update/edit',
    query: {
      editKey: editKey,
      editName: editName,
      currentValue: currentValue
    }
  })
}
</script>

<template>
  <template v-if="user">
    <van-cell-group>
      <van-cell title="用户名" :value="user.username" @click="toEdit('username', '用户名', user.username)"/>
      <van-cell title="个性签名" :label="user.profile" @click="toEdit('profile', '个性签名', user.profile)"/>
      <van-cell title="性别" :label="genderLabel" @click="toEdit('gender', '性别', user.gender)"/>
      <van-cell title="电话" :value="user.phone" @click="toEdit('phone', '电话', user.phone)"/>
      <van-cell title="邮箱" :label="user.email" @click="toEdit('email', '邮箱', user.email)"/>
      <van-cell title="头像" :label="user.avatarUrl" @click="toEdit('avatarUrl', '头像链接', user.avatarUrl)"/>
    </van-cell-group>
  </template>

</template>

<style scoped>

</style>