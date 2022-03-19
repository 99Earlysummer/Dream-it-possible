<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-12 17:20:13
-->
<script>
import { Form, Field, CellGroup,Button ,RadioGroup, Radio } from 'vant'
export default {
   components: {
    [Button.name]: Button,
     [Form.name]: Form,
      [Field.name]: Field,
       [CellGroup.name]: CellGroup,
       [RadioGroup.name]: RadioGroup,
       [Radio.name]: Radio,
  },
  data() {
    return {
      username:"a",
      password",
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
    <van-field name="radio" label="单选框">
  <template #input>
    <van-radio-group v-model="checked" direction="horizontal">
      <van-radio name="1">男</van-radio>
      <van-radio name="2">女</van-radio>
    </van-radio-group>
  </template>
</van-field>
  </van-cell-group>
  <div style="margin: 16px;">
    <van-button round block type="primary" native-type="submit">
      提交
    </van-button>
  </div>
</van-form>

</template>
