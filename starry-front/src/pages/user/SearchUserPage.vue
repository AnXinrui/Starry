<script setup lang="ts">
import {ref} from 'vue'
import {useRouter} from "vue-router";
import request from '@/utils/http';

const router = useRouter();
const value = ref('');
const userList = ref([]);
const onSearch = async () => {
  const response = await request.get('/user/search', {
    params: {
      keyword: value.value
    }
  });
  userList.value = response.data;
  // console.log(userList.value);
}
const onCancel = () => router.back();
</script>

<template>
  <form action="/">
    <van-search
        v-model="value"
        show-action
        placeholder="请输入搜索关键词"
    >
      <template #action>
        <div @click="onSearch">搜索</div>
      </template>
    </van-search>
  </form>

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