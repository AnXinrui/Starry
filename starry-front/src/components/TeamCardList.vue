<script setup lang="ts">
import request from "@/utils/http"
import {useUserStore} from "@/stores/userStore";
import {onMounted, ref} from "vue";
import {Team} from "@/modules/team.d";
import {UserType} from "@/modules/user.d";
import {showFailToast, showSuccessToast} from "vant";



const userStore = useUserStore();
const user = ref<UserType>();

onMounted(async () => {
  await userStore.getCurrentUser();
  user.value = userStore.user;
});

const props = defineProps<{
  teamList: Team[];
}>();

const deleteTeam = async (teamId: number) => {
  const response = await request({
    url: '/team/delete',
    method: 'POST',
    params: {teamId},
  })
  console.log(response);
  //@ts-ignore
  if (response.code === 0) {
    showSuccessToast('删除成功！')
  } else {//@ts-ignore
    showFailToast('删除失败!\n' + response.description);
  }
}
</script>

<template>
  <div v-if="user">
    <div v-for="team in props.teamList" :key="team.id" class="team-card">

      <van-card
          :desc="team.description"
          :title="team.name"
          thumb="https://upload-bbs.miyoushe.com/upload/2022/11/28/17949827/45041f6e7ebdd54b3e7589c18da6a07c_6575916404804375892.jpg?x-oss-process=image//resize,s_600/quality,q_80/auto-orient,0/interlace,1/format,jpg"
      >
        <template #tags>
          <div>队长： {{team.username}}</div>
          <div>已加入人数： {{team.hasJoinNum}} / {{team.maxNum}}</div>
          <div v-if="team.expireTime">过期时间： {{team.expireTime}}</div>
        </template>
        <template #footer>
          <div v-if="team.userId === user.id">
            <van-button size="mini" type="primary">修改</van-button>
            <van-button size="mini" type="danger" @click="deleteTeam(team.id)">删除</van-button>
          </div>
          <van-button size="mini" v-else-if="team.hasJoin">退出</van-button>
          <van-button size="mini" v-else type="success">加入</van-button>
        </template>

      </van-card>
    </div>
  </div>
</template>

<style scoped>

</style>