<script setup lang="ts">
import {computed, ref, watch} from "vue";
import {showToast} from "vant";

const is_start = ref(false);
// const time = ref(1 * 1 * 60 * 1000);
const time = ref(10 * 1000 * 6 * 25);
const totTime = ref(time.value);
const countDown = ref(null);

let timer = null; // 定时器引用
const decreaseTime = () => {
  if (time.value > 0) {
    time.value -= 1000; // 每秒减少 1000 毫秒（1 秒）
  } else {
    time.value = 0; // 确保时间不会变成负值
    clearInterval(timer); // 停止计时器
    onFinish(); // 倒计时结束
  }
};

const isStart = ref(false);
// 启动倒计时和定时器
const start = () => {
  console.log('start');
  countDown.value.start();
  timer = setInterval(decreaseTime, 1000); // 每秒调用一次 decreaseTime
  is_start.value = true;
};

const pause = () => {
  countDown.value.pause();
  is_start.value = false;
};

const onFinish = () => showToast('棒棒哒！');

const currentRate = ref(0);
const rat = computed(() => (1 - time.value / totTime.value) * 100);
// const text = computed(() => rat.value.toFixed(0) + '%');
// 修改 text 的计算方式以显示倒计时时间
const formatTime = (ms) => {
  const totalSeconds = Math.floor(ms / 1000);
  const hours = Math.floor(totalSeconds / 3600);
  const minutes = Math.floor((totalSeconds % 3600) / 60);
  const seconds = totalSeconds % 60;

  // 格式化时间为 HH:mm:ss
  return [
    hours.toString().padStart(2, '0'),
    minutes.toString().padStart(2, '0'),
    seconds.toString().padStart(2, '0'),
  ].join(':');
};

const text = computed(() => formatTime(time.value));

// 渐变色
const gradientColor = {
  '0%': '#3fecff',
  '100%': '#6149f6',
};
</script>

<template>
  <div class="main-container">
    <div class="title">modoro</div>
    <van-circle  class="custom-circle"
        v-model:current-rate="currentRate"
        :rate="rat"
        :speed="100"
        :text="text"
        :color="gradientColor"
        size="240px"
    />
    <van-count-down
        ref="countDown"
        millisecond
        :time="time"
        :auto-start="false"
        format=""
        @finish="onFinish"
    />
    <van-button color="#7232dd" @click="start" v-if="is_start === false" plain>开始记时</van-button>

  </div>

</template>

<style scoped>
.main-container {
  margin-top: 50px;
  display: flex;
  flex-direction: column; /* 垂直排列子元素 */
  align-items: center; /* 水平居中对齐 */
  justify-content: center; /* 垂直居中对齐 */

}
.title {
  font-size: 2rem; /* 标题字体大小 */
  font-weight: bold; /* 粗体 */
  margin-bottom: 20px; /* 标题和其他内容之间的间距 */
}
.van-circle {
  margin: 20px 0; /* 圆形组件上下间距 */
}

.van-grid-item {
  margin-top: 10px; /* 按钮和其他内容之间的间距 */
  color: #fff; /* 按钮文字颜色 */
}


</style>