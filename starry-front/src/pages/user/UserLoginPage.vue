<script setup lang="ts">
import {onMounted, ref, toRefs} from "vue";
import {showFailToast, showSuccessToast} from "vant";
import {useRouter} from "vue-router";
import useUserStore from "@/store/modules/user";


const router = useRouter();
const userStore = useUserStore();
const userAccount = ref('');
const userPassword = ref('');

onMounted(async () => {
  if (userStore.user == null) {
    try {
      await userStore.getCurrentUserInfo();
    } catch (error) {

    }
  }
  if (userStore.user) {
    router.push("/user");
  }
})

const onSubmit = async() => {
  try {
    await userStore.userLogin({
      userAccount: userAccount.value,
      userPassword: userPassword.value,
    })
    showSuccessToast("登录成功!");
    await router.push("/user");
    userStore.getCurrentUserInfo();
  } catch (error) {
    showFailToast("登录失败!\n" + error.message);
  }
};

const redirectToRegister =()=> {
  router.replace("/user/register");
}


</script>

<template>
  <van-form @submit="onSubmit">
    <van-cell-group inset>
      <van-field
          v-model="userAccount"
          name="userAccount"
          label="用户名"
          placeholder="用户名"
          :rules="[{ required: true, message: '请填写用户名' }]"
      />
      <van-field
          v-model="userPassword"
          type="password"
          name="userPassword"
          label="密码"
          placeholder="密码"
          :rules="[{ required: true, message: '请填写密码' }]"
      />
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        登录
      </van-button>
    </div>
    <div class="centered-container">
      <van-row type="flex" justify="center" align="center">
        <span>没有账号？</span>
        <van-button type="primary" size="small" @click="redirectToRegister" plain>注册</van-button>
      </van-row>
    </div>
  </van-form>
</template>

<style scoped>
.centered-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

span {
  margin-right: 8px;
}
</style>