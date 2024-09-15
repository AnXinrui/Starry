<script setup lang="ts">
import {showFailToast, showToast} from "vant";
import {onMounted, ref} from "vue";
import request from '../utils/request.ts';
import {useRouter} from "vue-router";
import TeamCardList from "@/components/TeamCardList.vue";

const router = useRouter();

const teamList = ref([])

onMounted(async ()=> {
  const res = await request.get('/team/list/my-create');
  if (res) {
    // @ts-ignore
    if (res.code === 0) {
      teamList.value = res.data;
      // @ts-ignore
    } else if (res.code === 40100) {
      await router.push('/user/login')
    } else {
      showFailToast('系统错误');
    }
  }
})



const addTeam = () => {
  router.push('/team/add');
}
</script>

<template>
  <div class="container">
    <team-card-list :team-list="teamList" />

    <van-sticky :offset-bottom="50" position="bottom">
      <van-button type="primary" @click="addTeam" block>创建队伍</van-button>
    </van-sticky>
  </div>
</template>

<style scoped>
.container {
  padding-bottom: 80px; /* 增加内容区域的下边距 */
}

.team-card {
  margin-bottom: 20px;
}

.van-sticky {
  z-index: 1000; /* 确保按钮在最前面 */
}
</style>