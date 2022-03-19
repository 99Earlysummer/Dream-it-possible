<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-12 17:55:47
-->
<script>
import { ref } from 'vue';
import { Form, Field,Cell, CellGroup,Button ,RadioGroup, Radio,Calendar  } from 'vant'
export default {
   components: {
    [Button.name]: Button,
     [Form.name]: Form,
      [Field.name]: Field,
       [Cell.name]: Cell,
       [CellGroup.name]: CellGroup,
       [RadioGroup.name]: RadioGroup,
       [Radio.name]: Radio,
       [Calendar.name]: Calendar,
  },
  data() {
    return {
      username:"a",
      password:"",
      sex:"1",
      showCalendar:false,
      showArea:false,
      birth:'',
      region:"",
      areaList
    };
  },
   methods: {
    onSubmit() {

    },
    onConfirm() {

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
    <van-field label="生日" v-model="birth" placeholder="点击选择生日" @click="showCalendar = true" />
    <van-calendar v-model:show="showCalendar" @confirm="onConfirm" />
    <van-field
      v-model="region"
      is-link
      readonly
      name="area"
      label="地区选择"
      placeholder="点击选择省市区"
      @click="showArea = true"
    />
    <van-popup v-model:show="showArea" position="bottom">
      <van-area
        :area-list="areaList"
        @confirm="onConfirm"
        @cancel="showArea = false"
      />
    </van-popup>
  </van-cell-group>
  <div style="margin: 16px;">
    <van-button round block type="primary" native-type="submit">
      提交
    </van-button>
  </div>
</van-form>

</template>
