<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-12 18:09:35
-->
<script>
import { areaList } from '@vant/area-data';
import { Form, Field,Cell, CellGroup,Button ,RadioGroup, Radio,Calendar ,Area,Popup } from 'vant'
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
       [Area.name]: Area,
       [Popup.name]: Popup,
      areaList,
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
      regionList:areaList
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
      name="姓名"
      label="姓名"
      placeholder="姓名"
      :rules="[{ required: true, message: '请填写姓名' }]"
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
    <van-cell value="" />
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
        :area-list="regionList"
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
