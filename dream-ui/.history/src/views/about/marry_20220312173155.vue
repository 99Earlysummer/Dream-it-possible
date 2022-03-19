<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-12 17:31:41
-->
<script>
import { ref } from 'vue';
import { Form, Field, CellGroup,Button ,RadioGroup, Radio,Calendar  } from 'vant'
export default {
   components: {
    [Button.name]: Button,
     [Form.name]: Form,
      [Field.name]: Field,
       [CellGroup.name]: CellGroup,
       [RadioGroup.name]: RadioGroup,
       [Radio.name]: Radio,
       [Calendar.name]: Calendar,
  },
  data() {
    return {
      username:"a",
      password:"",
      sex:ref(0),
      show:false,
      birth:'',
      onSubmit,
    };
  },
   methods: {
    formatCategoryCode(code) {

    },
    formatCategoryCode(code) {

    },
  }
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
        <van-radio-group v-model="sex" direction="horizontal">
          <van-radio name="0">男</van-radio>
          <van-radio name="1">女</van-radio>
        </van-radio-group>
      </template>
    </van-field>
    <van-cell title="生日" :value="birth" @click="show = true" />
    <van-calendar v-model:show="show" @confirm="onConfirm" />
  </van-cell-group>
  <div style="margin: 16px;">
    <van-button round block type="primary" native-type="submit">
      提交
    </van-button>
  </div>
</van-form>

</template>
