<script setup lang="ts">
import request from "@/utils/request"
import {onMounted, ref, toRefs} from "vue";
import useUserStore from "@/store/modules/user";
import {UserType} from "@/modules/user.d";
import {Dialog, showFailToast, showSuccessToast, showToast} from "vant";
import {Team} from "@/modules/team.d";
import {useRouter} from "vue-router";


const router = useRouter();

const userStore = useUserStore();
const { user } = toRefs(userStore);

onMounted(async () => {
  if (userStore.user == null) {
    try {
      await userStore.getCurrentUserInfo();
    } catch (error) {
      // 处理获取用户信息失败的情况
      // console.log(error)
      router.push('/user/login');
    }
  }
  if (user.value == null) {
    router.push('/user/login');
  }
});

const props = defineProps<{
  teamList: Team[];
}>();

const password = ref('');
const joinTeam = async (team: Team) => {
  if (team.status === 1) {
    const response = await request({
      method: 'post',
      url: '/team/join',
      data: {
        teamId: team.id,
        password: password.value,
      }
    });//@ts-ignore
    if (response.code === 0) {
      showSuccessToast('加入成功');
      location.reload();
    } else {//@ts-ignore
      showFailToast('失败！\n'+ response.description);
    }
  } else {
    const response = await request({
      method: 'post',
      url: '/team/join',
      data: {
        teamId: team.id,
      }
    });//@ts-ignore
    if (response.code === 0) {
      showSuccessToast('加入成功');
      location.reload();
    } else {//@ts-ignore
      console.log(response)
      showFailToast('失败！\n'+ response.description);
    }
  }
};


const deleteTeam = async (teamId: number) => {
  const response = await request({
    url: '/team/delete',
    method: 'POST',
    params: {teamId},
  })
  // console.log(response);
  //@ts-ignore
  if (response.code === 0) {
    showSuccessToast('删除成功！')
  } else {//@ts-ignore
    showFailToast('删除失败!\n' + response.description);
  }
}

const quitTeam = async (teamId: number) => {
  const response = await request({
    url: '/team/quit',
    method: 'POST',
    params: {teamId},
  })
  //@ts-ignore
  if (response.code === 0) {
    showSuccessToast('退出成功！')
    location.reload();
  } else {//@ts-ignore
    showFailToast('退出失败!\n' + response.description);
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
          <van-button size="mini" v-else-if="team.hasJoin" @click="quitTeam(team.id)">退出</van-button>
          <div v-else>
            <van-field
                v-model="password"
                center
                clearable
                label=""
                placeholder="请输入队伍密码" v-if="team.status === 1"
            >
              <template #button>
                <van-button size="mini"  type="success" @click="joinTeam(team)" >加入</van-button>
              </template>
            </van-field>
            <van-button size="mini"  type="success" @click="joinTeam(team)" v-else>加入</van-button>
          </div>
        </template>
      </van-card>
    </div>
  </div>
</template>

<style scoped>

</style>