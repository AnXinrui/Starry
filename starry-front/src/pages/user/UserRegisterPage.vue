<script setup lang="ts">
import {onMounted, ref} from "vue";
import {getCurrentUser, loginAPI, registerAPI} from "@/apis/user";
// import request from '@/utils/http';
import {showFailToast, showSuccessToast} from "vant";
import {BaseResponse} from "@/modules/BaseResponse"
import {useRouter} from "vue-router";


const router = useRouter();
const userAccount = ref('');
const userPassword = ref('');
const confirmPassword = ref('');

const onSubmit = async(value) => {
  // console.log(value)
  //@ts-ignore
  const res:BaseResponse = await registerAPI({
    userAccount: userAccount.value,
    userPassword: userPassword.value,
    confirmPassword: confirmPassword.value,
  })

  if (res && res.code == 0 && res.data) {
    showSuccessToast("注册成功!");
    await router.push("/user/login");
  } else {
    if (res.description) {
      showFailToast(res.description)
    } else {
      showFailToast("登录失败!!!")
    }
  }
};


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
      <van-field
          v-model="confirmPassword"
          type="password"
          name="confirmPassword"
          label="确认密码"
          placeholder="密码"
          :rules="[{ required: true, message: '请确认密码' }]"
      />
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        注册
      </van-button>
    </div>
  </van-form>
</template>

<style scoped>

</style>le>