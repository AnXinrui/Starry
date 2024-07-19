<script setup lang="ts">
import { showSuccessToast } from 'vant';

import {useRouter} from "vue-router";

const router = useRouter();
const onClickLeft = () => router.back();
const onClickRight = () => showSuccessToast('按钮');
</script>

<template>
  <van-nav-bar
      title="标题"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight">
    <template #right>
      <van-icon name="search" size="18" />
    </template>
  </van-nav-bar>

  <router-view />

  <van-tabbar route>
<!--    <van-tabbar-item replace to="/login" icon="home-o">标签</van-tabbar-item>-->
<!--    <van-tabbar-item replace to="/search" icon="search">标签</van-tabbar-item>-->
    <van-tabbar-item icon="home-o">标签</van-tabbar-item>
    <van-tabbar-item icon="search">标签</van-tabbar-item>
    <van-tabbar-item icon="friends-o">标签</van-tabbar-item>
    <van-tabbar-item replace to="/user" icon="user-o">我</van-tabbar-item>
  </van-tabbar>
</template>

<style scoped>

</style>