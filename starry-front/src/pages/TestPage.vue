<!--<template>-->
<!--  <div>tst</div>-->
<!--  <van-image-->
<!--      width="100"-->
<!--      height="100"-->
<!--      src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"-->
<!--  />-->
<!--  <van-image-->
<!--      round-->
<!--      width="10rem"-->
<!--      height="10rem"-->
<!--      src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"-->
<!--  />-->

<!--</template>-->
<!--<style scoped>-->
<!--</style>-->


<template>
  <div>
    <van-card
        v-for="team in teams"
        :key="team.id"
        :title="team.name"
        :desc="team.description"
        :thumb="team.thumb"
        :price="team.expiry"
    >
      <div>创建者: {{ team.creator }}</div>
      <div>最大人数: {{ team.maxMembers }} / 当前人数: {{ team.currentMembers }}</div>
      <div v-if="team.joined">已加入</div>
      <van-button v-else @click="joinTeam(team.id)">加入</van-button>
    </van-card>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import { Button, Card } from 'vant';

export default defineComponent({
  components: {
    'van-card': Card,
    'van-button': Button
  },
  setup() {

    // 假数据生成
    const teams = [
      {
        id: 1,
        name: '队伍A',
        description: '这是队伍A的描述',
        maxMembers: 10,
        currentMembers: 5,
        expiry: '2024-12-31',
        creator: '用户1',
        joined: false,
        thumb: 'https://via.placeholder.com/150' // 示例缩略图
      },
      {
        id: 2,
        name: '队伍B',
        description: '这是队伍B的描述',
        maxMembers: 20,
        currentMembers: 10,
        expiry: '2024-11-30',
        creator: '用户2',
        joined: true,
        thumb: 'https://via.placeholder.com/150'
      },
      // 你可以添加更多假数据
    ];

    const joinTeam = (teamId) => {
      const team = teams.find(t => t.id === teamId);
      if (team) {
        team.joined = true;
        team.currentMembers++;
      }
    };

    return {
      teams,
      joinTeam
    };
  }
});
</script>

<style>
/* 你可以根据需要添加自定义样式 */
</style>
