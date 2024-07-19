<script setup lang="ts">
import {ref} from "vue";
import {getCurrentUser, loginAPI} from "@/apis/user";
// import request from '@/utils/http';
import {showFailToast, showSuccessToast} from "vant";
import {BaseResponse} from "@/modules/BaseResponse"
import {useRouter} from "vue-router";


const router = useRouter();
const userAccount = ref('');
const userPassword = ref('');
const onSubmit = async() => {
  //@ts-ignore
  const res:BaseResponse = await loginAPI({
    userAccount: userAccount.value,
    userPassword: userPassword.value,
  })

  if (res && res.code == 0 && res.data) {
    showSuccessToast("登录成功!");
    router.push("/user");
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
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        提交
      </van-button>
    </div>
  </van-form>
</template>

<style scoped>

</style>