<script setup lang="ts">
import {computed, onMounted, ref, toRefs} from 'vue';
import {getCurrentUser} from '@/apis/user';
import {UserType} from "@/apis/user/type";
import {Cell, CellGroup, Image as VanImage, showFailToast, showSuccessToast} from 'vant';
import request from '@/utils/request';
import {useRouter} from "vue-router";
import {BaseResponse} from "@/modules/BaseResponse";
import useUserStore from "@/store/modules/user";

const router = useRouter();
const userStore = useUserStore();
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
});

const genderLabel = computed(() => {
  if (user.value && user.value.gender !== undefined) {
    return user.value.gender === 0 ? '男' : '女';
  }
  return '';
});


const tags = computed(() => {
  if (user.value && user.value.tags) {
    return JSON.parse(user.value.tags);
  }
  return [];
});

// 创建 logout 函数
const doUserLogout = async () => {
  try {
    await userStore.userLogout();
    showSuccessToast('退出登录');
    router.push('/user/login');
  } catch (error: any) {
    showFailToast(`登录失败!\n${error.message}`);
  }
};

const changeInfo = () => {
  router.push('/user/update');
}
</script>

<template>
  <div class="user-profile" v-if="user">
    <van-cell-group>
      <van-cell>
        <template #title>
          <div class="user-info">
            <div class="user-header">
              <span class="username">{{ user.username }}</span>
              <van-image
                  :src="user.avatarUrl"
                  width="64"
                  height="64"
                  fit="cover"
                  round
              />
            </div>
            <div class="signature">{{ user.profile }}</div>
          </div>
        </template>
      </van-cell>
      <van-cell title="用户账号" :value="user.userAccount"/>
      <van-cell title="性别" :value="genderLabel"/>
      <van-cell title="朋友圈"  is-link to="/user/blog"/>
      <van-cell title="我的队伍" is-link to="/team/user-team" />
      <van-cell title="积分" :value="user.rating"/>
      <van-cell title="星星⭐签" class="tag-cell" is-link value="重新选择" to="/user/tags" />
      <div class="tag-container">
        <van-tag
            v-for="tag in tags"
            :key="tag"
            type="primary"
            size="large"
        >
          {{ tag }}
        </van-tag>
      </div>
    </van-cell-group>
  </div>

  <div v-else>
    <van-loading vertical>
      <template #icon>
        <van-icon name="star-o" size="30"/>
      </template>
      加载中...
    </van-loading>
  </div>
  <div class="button-container">
    <van-space size="2rem">
      <van-button type="primary" @click="changeInfo">修改信息</van-button>
      <van-button type="danger" @click="doUserLogout">退出登陆</van-button>
    </van-space>
  </div>


</template>

<style scoped>
.user-profile {
  padding: 16px;
}

.user-info {
  display: flex;
  flex-direction: column;
}

.user-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
}

.username {
  font-weight: bold;
}

.signature {
  font-size: 12px;
  color: #888;
}

.button-container {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  position: fixed;
  bottom: 60px;
  left: 0;
  right: 0;
  padding: 0 16px; /* 可根据需要调整左右边距 */
}


.tag-container {
  display: flex;
  flex-wrap: wrap;
  gap: 8px; /* 标签之间的间距 */
  margin-top: 8px; /* 标签与标题之间的间距 */
}
</style>