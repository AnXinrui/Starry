<script setup lang="ts">
import {showFailToast, showToast} from "vant";
import {onMounted, ref} from "vue";
import request from '../utils/http';
import {useRouter} from "vue-router";
import TeamCardList from "@/components/TeamCardList.vue";

const router = useRouter();

const teamList = ref([])

onMounted(async ()=> {
  const res = await request.get('/team/list/my-join');
  if (res) {
    // @ts-ignore
    if (res.code === 0) {
      teamList.value = res.data;
      // @ts-ignore
    } else if (res.code === 40100) {
      router.push('/user/login')
    } else {
      showFailToast('系统错误');
    }
  }
})




</script>

<template>
  <div class="container">
    <team-card-list :team-list="teamList" />
  </div>
</template>

<style scoped>
.container {
  padding-bottom: 60px; /* 增加内容区域的下边距 */
}
</style>