<template>
  <img class="background"
       src="https://cn.bing.com//th?id=OHR.GimignanoTuscany_ZH-CN8059318824_1920x1080.jpg&rf=LaDigue_1920x1080.jpg"
       style="padding-bottom:10px;" alt=""/>

  <div v-for="blog in blogList" :key="blog.id" style="padding-bottom:10px;">
    <van-row>
      <van-col span="2" offset="1">
        <van-image
            width="3rem"
            height="3rem"
            fit="contain"
            :src="blog.userAvatar"
        />
      </van-col>
      <van-col span="17" offset="2">
        <div style="font-weight: 700;color: dodgerblue;">
          {{ blog.username }}
        </div>
        {{ blog.content }}
        <div v-if="blog.image && blog.image.length > 0">
          <img class="blog-image" :src="blog.image" alt=""/>
        </div>
      </van-col>
      <van-col offset="12" span="3">
        <div @click="doLike">
          <van-icon name="good-job-o" v-if="!isLike"/>
          <van-icon name="good-job" color="#1989fa" v-else/>
          <span>{{ num }}</span>
        </div>
      </van-col>
      <van-col offset="0" span="3">
        <div >
          <van-icon name="chat-o" />
          <span>3</span>
        </div>
      </van-col>
      <van-col offset="5" span="17">
        <div style="background-color: #DDDDDD">
          <span style="font-weight: 700;color: dodgerblue;">用户名: </span>
          <span> 所有HTML元素可以看作盒子，在CSS中，"box model"这一术语是用来设计和布局时使用。 CSS盒模型本质上是一个盒子，封装周围的HTML元素，它包括：边距，边框，填充，和实际内容。 盒模型允许我们在其它元素和周围元素边框之
      </span>
        </div>

      </van-col>
    </van-row>
    <van-divider/>
  </div>
  <van-floating-bubble
      axis="xy"
      icon="add"
      magnetic="x"
      v-model:offset="offset"
      @click="addBlog"
  />
</template>

<style scoped>
img {
  max-width: 100%;
  height: auto;
}

.blog-image {
  width: auto;
  height: 120px;
}
</style>

<script setup lang="ts">
import {ref} from "vue";

interface blogType {
  id: number;
  username: string;
  userAvatar: string;
  content: string;
  image?: string;
}

const blogList = ref<blogType[]>([
  {
    id: 1,
    userAvatar: 'https://cdn.acwing.com/media/user/profile/photo/367929_lg_bcb8531e6c.jpg',
    username: 'nini0913',
    content: '今晚国乒对战思密达，祝国乒好运，加油！',
    image: 'https://cdn.acwing.com/media/user/profile/photo/410386_lg_fa8db1824c.png'
  },
  {
    id: 2,
    username: 'nini0913',
    userAvatar: 'https://cdn.acwing.com/media/user/profile/photo/457383_lg_77bf0c1843.jpg',
    content: '今天的天气真好，适合出去走走。',
    image: 'https://www4.bing.com//th?id=OHR.YellowstoneGeyser_ZH-CN3441008468_1920x1080.jpg'
  },
  {
    id: 3,
    username: 'nini0913',
    userAvatar: 'https://cdn.acwing.com/media/user/profile/photo/417629_lg_f0e64a55d7.jpg',
    content: '最近在研究Vue 3的Composition API，感觉非常强大。',
    image: 'https://www.runoob.com/wp-content/uploads/2017/01/vue.png'
  },
  {
    id: 4,
    username: 'nini0913',
    userAvatar: 'https://cdn.acwing.com/media/user/profile/photo/459283_lg_4d2f7d424b.jpg',
    content: '发现了一家新开的咖啡店，环境和咖啡都很不错。',
  },
  {
    id: 5,
    username: 'nini0913',
    userAvatar: 'https://cdn.acwing.com/media/user/profile/photo/386314_lg_613da38f49.png',
    content: '周末计划去爬山，希望天气能给力。',
    image: ''
  }
]);

let isLike = ref(true);
const doLike = () => {
  if (isLike.value) {
    num.value--;
  } else {
    num.value++;
  }
  isLike.value = !isLike.value;
}

const num = ref(20);

// 悬浮球
const offset = ref({ x: 303, y: 552 });
const addBlog = () => {
  console.log("add blog")
}

</script>