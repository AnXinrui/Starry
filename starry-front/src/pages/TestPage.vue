<template>
  <div class="loading-container" v-if="!toName">
    <van-loading size="160" type="spinner" color="#1989fa" vertical>
      <p style="font-size: 20px;">匹配中...</p>
    </van-loading>
  </div>
  <div v-else class="chat-container">
    <van-list
        :data="dialog"
        class="chat-list"
    >
      <van-cell v-for="(message, index) in dialog" :key="index" class="chat-item">
        <div :class="['chat-content', !message.isReply ? 'chat-content-me' : 'chat-content-other']">
          {{ message.message }}
        </div>
      </van-cell>
    </van-list>

    <div class="sumbit-container">
      <van-cell-group inset >
        <van-field
            v-model="msg"
            center
            clearable
            label=""
            placeholder="请输入内容"
        >
          <template #button>
            <van-button size="small" type="primary" @click="sendMessage">发送</van-button>
          </template>
        </van-field>
      </van-cell-group>
    </div>
  </div>
</template>

<style scoped>
.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60vh; /* 确保容器占满整个视口高度 */
  margin-top: 3vh; /* 上边距可选：根据需要调整 */
}

.chat-container {
  display: flex;
  flex-direction: column;
  height: 60vh;
}

.chat-content {
  padding: 10px;
  border-radius: 15px;
  max-width: 70%;
}

.chat-content-me {
  background-color: #0084ff;
  color: white;
  margin-left: auto;
}

.chat-content-other {
  background-color: #f1f1f1;
  color: black;
}

.submit-container {
  position: sticky;
  bottom: 0;
  background-color: #fff; /* Ensure the background is solid */
  padding: 10px 0; /* Adjust padding as necessary */
  box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1); /* Optional: Add shadow for effect */
}
</style>

<script setup lang="ts">
import {ref} from "vue";
import {showFailToast, showToast} from "vant";

const msg = ref('');
const toName = ref('');
console.log(!toName.value);
let dialog = ref<messageType[]>([]);

let ws = new WebSocket("ws://localhost:8080/chat");

ws.onopen = () => {
  console.log('连接成功...');
}

const sendMessage =() => {
  if (msg.value == null || msg.value.length < 1) {
    showFailToast('内容不能为空!!!')
    return;
  }
  let message = {
    toName: toName.value,
    message: msg.value,
  }
  // console.log('message', message)
  ws.send(JSON.stringify(message));
  msg.value = '';
}


ws.onmessage = (e) => {
  const str: string = e.data;
  const res: messageType = JSON.parse(e.data);
  if (res.isSystem === -1) {
    showToast(res.message);
    return;
  }
  if (res.isSystem === 0) {
    toName.value=res.message;
    console.log(toName.value)
  } else {
      console.log(res);
      dialog.value.push(res);
      console.log(dialog.value);
  }
}

ws.onclose = () => {
  console.log('断开链接...')
}

interface messageType {
  isSystem: number;
  isReply: boolean;
  message: string;
}
</script>