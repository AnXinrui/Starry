<script setup lang="ts">
import {showToast} from "vant";
import {onMounted, ref} from "vue";
import request from '../utils/request.ts';
import {useRouter} from "vue-router";
import TeamCardList from "@/components/TeamCardList.vue";

const router = useRouter();

const value = ref('');
const onClickButton = () => showToast(value.value);

const teamList = ref([])

onMounted(async ()=> {
  const res = await request.get('/team/list');
  if (res) {
    // @ts-ignore
    if (res.code === 0) {
      teamList.value = res.data;
      // @ts-ignore
    } else if (res.code === 40100) {
      router.push('/user/login')
    }
  }
})

// const team = {
//   id: 1,
//   name: '神里凌华小队',
//   description: '雪霁银妆素，桔高映琼枝',
//   user: '神里凌华',
//   maxMembers: 10,
//   currentMembers: 5,
//   expiry: '2024-12-31',
//   creator: '神里凌华',
//   joined: false,
// }

const addTeam = () => {
  router.push('/team/add');
}
</script>

<template>
  <div class="container">
    <form action="/">
      <van-search
          v-model="value"
          show-action
          placeholder="请输入搜索关键词"
      >
        <template #action>
          <div @click="onClickButton">搜索</div>
        </template>
      </van-search>
    </form>
    <team-card-list :team-list="teamList"/>
<!--    <div v-for="team in teamList" :key="team.id" class="team-card">-->

<!--      <van-card-->
<!--          :desc="team.description"-->
<!--          :title="team.name"-->
<!--          thumb="https://upload-bbs.miyoushe.com/upload/2022/11/28/17949827/45041f6e7ebdd54b3e7589c18da6a07c_6575916404804375892.jpg?x-oss-process=image//resize,s_600/quality,q_80/auto-orient,0/interlace,1/format,jpg"-->
<!--      >-->
<!--        <template #tags>-->
<!--          <div>队长： {{team.username}}</div>-->
<!--          <div>已加入人数： {{team.hasJoinNum}} / {{team.maxNum}}</div>-->
<!--          <div v-if="team.expireTime">过期时间： {{team.expireTime}}</div>-->
<!--        </template>-->
<!--        <template #footer>-->
<!--          <van-button size="mini" v-if="team.hasJoin">退出</van-button>-->
<!--          <van-button size="mini" v-else>加入</van-button>-->
<!--        </template>-->

<!--      </van-card>-->
<!--    </div>-->

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