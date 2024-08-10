<script setup lang="ts">

import {computed, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {getCurrentUser} from "@/apis/user";
import {showFailToast, showSuccessToast} from "vant";
import request from '@/utils/request.ts';

const route = useRoute();
const router = useRouter();
const editUser = ref({
      editKey: String(route.query.editKey),
      editName: String(route.query.editName),
      currentValue: String(route.query.currentValue),
    }
)

// 计算属性，用于转换显示值
const displayValue = computed({
  get() {
    if (editUser.value.editKey === 'gender') {
      return editUser.value.currentValue === '1' ? '女' : '男';
    }
    return editUser.value.currentValue;
  },
  set(newValue) {
    if (editUser.value.editKey === 'gender') {
      editUser.value.currentValue = newValue === '女' ? '1' : '0';
    } else {
      editUser.value.currentValue = newValue;
    }
  }
});

const onSubmit = async () => {
  const currentUser = await getCurrentUser();
  console.log(currentUser,'currentUser');
  if (!currentUser) {
    showFailToast('未登录');
    router.replace('/user/login');
    return
  }

  const res = await request.post('/user/update', {
    'id': currentUser.data.id,
    [editUser.value.editKey]: editUser.value.currentValue,
  })
  // @ts-ignore
  if (res.code === 0 && res.data > 0) {
    showSuccessToast('修改成功！');
    router.back();
  } else {
    showFailToast('更新失败');
  }
};
</script>


<template>
  <van-form @submit="onSubmit">

    <van-field
        v-model="displayValue"
        :name="editUser.editKey"
        :label="editUser.editName"
        :placeholder="`请输入${editUser.editName}`"
    />
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        提交
      </van-button>
    </div>
  </van-form>
</template>

<style scoped>

</style>

<style scoped>

</style>