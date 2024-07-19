<template>
  <div class="user-profile">
    <van-cell-group>
      <van-cell>
        <template #title>
          <div class="user-info">
            <div class="user-header">
              <span class="username">{{ user.username }}</span>
              <van-image
                  round
                  :src="user.avatar"
                  width="64"
                  height="64"
                  fit="cover"
              />
            </div>
            <div class="signature">{{ user.signature }}</div>
          </div>
        </template>
      </van-cell>
      <van-cell title="用户账号" :value="user.userAccount" />
      <van-cell title="性别" :value="user.gender" />
      <van-cell title="积分" :value="user.points" />
    </van-cell-group>
  </div>
  <div class="image-container">
    <van-image
        round
        width="10rem"
        height="10rem"
        :src="user.avatar"
    />
  </div>
  <div class="button-container">
    <van-space size="2rem">
      <van-button type="primary">修改信息</van-button>
      <van-button type="danger">退出登陆</van-button>
    </van-space>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getCurrentUser } from '@/apis/user'; // 根据你的项目结构调整路径

interface User {
  username: string;
  avatar: string;
  signature: string;
  userAccount: string;
  gender: string;
  points: number;
}

const user = ref<User>({
  username: '',
  avatar: '',
  signature: '',
  userAccount: '',
  gender: '',
  points: 0,
});

onMounted(async () => {
  const res = await getCurrentUser();
  if (res && res.data) {
    user.value = res.data;
  }
});
</script>

<style scoped>
.user-profile {
  margin: 1rem;
}

.user-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.username {
  font-size: 1.2rem;
  font-weight: bold;
}

.signature {
  margin-top: 0.5rem;
  color: #888;
}

.image-container {
  display: flex;
  justify-content: center;
  margin-top: 1rem;
}

.button-container {
  text-align: center;
  margin-top: 2rem;
}
</style>
