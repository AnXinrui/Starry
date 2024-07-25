<script setup lang="ts">
import {ref} from "vue";
import request from '@/utils/http';
import {useRouter} from "vue-router";
import {showFailToast, showSuccessToast} from "vant";

const router = useRouter();

const expireTimeChecked= ref(false);
const passwordChecked= ref(false);

const form = ref({
  name: '',
  description: '',
  maxNum: 2,
  expireTime: null,
  status: 0,
  password: '',
});


// 日期选择
const dateResult = ref('');
const showDatePicker = ref(false);
const minDate = new Date();
const maxDate = new Date();
maxDate.setMonth(maxDate.getMonth() + 6);
const onDateConfirm = ({ selectedValues }) => {
  dateResult.value = selectedValues.join('-');
  showDatePicker.value = false;
  console.log(dateResult.value);
};
let formDate = '';
let formTime = '';
// 时间选择
const timeResult = ref();
const showTimePicker = ref(false);

const formatter = (type, option) => {
  if (type === 'hour') {
    option.text += '时';
  }
  if (type === 'minute') {
    option.text += '分';
  }
  return option;
};
const onTimeConfirm = ({ selectedValues }) => {
  timeResult.value = selectedValues.join(':');
  showTimePicker.value = false;
  console.log(timeResult.value)
};

const initializeDateTime = () => {
  const now = new Date();

  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');

  now.setHours(now.getHours() + 3);
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');

  dateResult.value = `${year}-${month}-${day}`;
  timeResult.value = `${hours}:${minutes}`;
};

initializeDateTime();


const onSubmit = async () => {
  if (!expireTimeChecked) {
    form.value.expireTime = null;
  }
  if (expireTimeChecked && dateResult.value && timeResult.value) {
    form.value.expireTime = dateResult.value + ' ' + timeResult.value;
  }

  if (passwordChecked.value === true) {
    form.value.status = 1;
  }
  // console.log(form.value,'form');
  // const response = await request.post('/team/add', form.value);
  const response = await request({
    url: '/team/add',
    method: 'POST',
    data: form.value
  })
  // console.log('onSubmit', response);
  //@ts-ignore
  if (response.code === 0) {
    showSuccessToast('创建成功');
    router.back();
  } else {
    console.log(response);
    //@ts-ignore
    showFailToast("创建失败！\n" + response.description);
  }
}

</script>

<template>
  <van-form @submit="onSubmit">
    <van-cell-group inset>
      <van-field
          v-model="form.name"
          name="队伍名称"
          label="队伍名称"
          placeholder="请填写队伍名称"
          :rules="[{ required: true, message: '请填写队伍名称' }]"
          maxlength="25"
          show-word-limit
      />
      <van-field
          v-model="form.description"
          rows="2"
          autosize
          label="队伍描述"
          type="textarea"
          maxlength="120"
          placeholder="请输入队伍描述"
          show-word-limit
      />
      <van-field name="stepper" label="上限人数">
        <template #input>
          <van-stepper v-model="form.maxNum" min="2" max="100" />
        </template>
      </van-field>

<!--      <van-field name="checkbox" label="过期时间">-->
<!--        <template #input>-->
<!--          <van-checkbox v-model="statusCheck" shape="square" />-->
<!--        </template>-->
<!--      </van-field>-->
      <van-field name="checkbox" label="过期时间">
        <template #input>
          <van-checkbox v-model="expireTimeChecked" shape="square">永久有效</van-checkbox>
        </template>
      </van-field>

      <div v-if="expireTimeChecked != true">
        <van-field
            v-model="dateResult"
            is-link
            readonly
            name="datePicker"
            label="截止日期"
            placeholder="点击确认截止日期"
            @click="showDatePicker = true"
        />
        <van-popup v-model:show="showDatePicker" position="bottom">
          <van-date-picker @confirm="onDateConfirm" @cancel="showDatePicker = false"
                           :min-date="minDate"  :max-date="maxDate"/>
        </van-popup>

        <van-field
            v-model="timeResult"
            is-link
            readonly
            name="datePicker"
            label="截止日期"
            placeholder="点击确认截止时间"
            @click="showTimePicker = true"
        />
        <van-popup v-model:show="showTimePicker" position="bottom">
<!--          <van-date-picker @confirm="onConfirm" @cancel="showPicker = false"-->
<!--                           :min-date="minDate"  :max-date="maxDate"/>-->
          <van-time-picker
              @confirm="onTimeConfirm"
              @cancel="showTimePicker = false"
              title="选择时间"
              :formatter="formatter"
          />
        </van-popup>
      </div>


      <van-field name="checkbox" label="是否加密">
        <template #input>
          <van-checkbox v-model="passwordChecked" shape="square">加密</van-checkbox>
        </template>
      </van-field>
      <div v-if="passwordChecked">
        <van-cell-group inset>
          <van-field v-model="form.password" placeholder="请输入队伍密码" :rules="[{ required: true, message: '请输入队伍密码' }]"/>
        </van-cell-group>
      </div>

    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        创建队伍
      </van-button>
    </div>
  </van-form>
</template>

<style scoped>

</style>