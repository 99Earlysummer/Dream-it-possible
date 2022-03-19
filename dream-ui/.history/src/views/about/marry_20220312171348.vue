<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-12 17:13:46
-->
<script>
 import { createApp } from 'vue';
import { Form, Field, CellGroup } from 'vant'
export default {
   components: {
    [Button.name]: Button,
  },
  setup() {
    const username = ref('');
    const password = ref('');
    const onSubmit = (values) => {
      console.log('submit', values);
    };

    return {
      username,
      password,
      onSubmit,
    };
  },
}
</script>
<template>
<van-form @submit="onSubmit">
  <van-cell-group inset>
    <van-field
      v-model="username"
      name="用户名"
      label="用户名"
      placeholder="用户名"
      :rules="[{ required: true, message: '请填写用户名' }]"
    />
    <van-field
      v-model="password"
      type="password"
      name="密码"
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
