<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { showSuccessToast, showFailToast } from 'vant';
import request from '@/utils/request.ts';
import {getCurrentUser} from "@/apis/user.ts"; // 假设你有一个封装好的请求函数

const activeIds = ref<string[]>([]); // 用于存储选择的标签
const activeIndex = ref(0);
const router = useRouter();

const items = [
  {
    text: '学生生涯',
    children: [
      {text: '大一', id: '大一'},
      {text: '大二', id: '大二'},
      {text: '大三', id: '大三'},
      {text: '大四', id: '大四'},
      {text: '研究生', id: '研究生'},
      {text: '博士', id: '博士'},
      {text: '小学', id: '小学'},
      {text: '初中', id: '初中'},
      {text: '高中', id: '高中'},
      {text: '学前班', id: 'x', disabled: true},
    ],
  },
  {
    text: '编程语言',
    children: [
      {text: 'C++', id: 'C++'},
      {text: 'Python', id: 'Python'},
      {text: 'Java', id: 'Java'},
      {text: 'Go', id: 'Go'},
      {text: 'JavaScript', id: 'JavaScript'},
      {text: 'Ruby', id: 'Ruby'},
      {text: 'Swift', id: 'Swift'},
      {text: 'Kotlin', id: 'Kotlin'},
      {text: 'PHP', id: 'PHP'},
      {text: 'TypeScript', id: 'TypeScript'},
    ],
  },
  {
    text: "业余爱好",
    children: [
      { text: "爬山", id: "爬山" },
      { text: "游泳", id: "游泳" },
      { text: "跑步", id: "跑步" },
      { text: "读书", id: "读书" },
      { text: "摄影", id: "摄影" },
      { text: "旅行", id: "旅行" },
      { text: "音乐", id: "音乐" },
      { text: "绘画", id: "绘画" },
      { text: "烹饪", id: "烹饪" },
      { text: "编程", id: "编程" },
      { text: "钓鱼", id: "钓鱼" },
      { text: "打篮球", id: "打篮球" },
      { text: "骑自行车", id: "骑自行车" },
      { text: "瑜伽", id: "瑜伽" },
      { text: "看电影", id: "看电影" }
    ]
  },
  {text: '待补充', disabled: true},
];

const onSubmit = async () => {
  const tags = JSON.stringify(activeIds.value);
  const currentUser = await getCurrentUser();
  // alert(tags)
  try {
    const res = await request.post('/user/update', {
      id: currentUser?.data.id,
      tags: tags
    });
    // @ts-ignore
    if (res.code === 0) {
      showSuccessToast('标签更新成功！');
      router.push('/user'); // 跳转到其他页面
    } else {
      showFailToast('标签更新失败！');
    }
  } catch (error) {
    showFailToast('请求失败，请稍后再试！');
  }
};
</script>

<template>
  <h4>请选择您感兴趣的内容~</h4>
  <van-tree-select
      v-model:active-id="activeIds"
      v-model:main-active-index="activeIndex"
      :items="items"
  />

  <van-button  type="primary" block @click="onSubmit"
               style="position: fixed;bottom: 60px;">我选好了</van-button>



</template>

<style scoped>
 .button-container {
   position: fixed;
   bottom: 60px;
 }
</style>