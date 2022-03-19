<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-12 18:23:47
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
      username:"谭超",
      password:"",
      sex:"1",
      showCalendar:false,
      showArea:false,
      birth:'',
      shengXiao:"羊",
      xingZhuo:"天秤",
      height:"",
      wei"",
      region:"",
      company:"",
      phone:"",
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

    <van-field name="radio" label="性别">
      <template #input>
        <van-radio-group v-model="sex" direction="horizontal">
          <van-radio name="0">男</van-radio>
          <van-radio name="1">女</van-radio>
        </van-radio-group>
      </template>
    </van-field>
    <van-field label="生日" v-model="birth" placeholder="点击选择生日" @click="showCalendar = true" />
    <van-calendar v-model:show="showCalendar" @confirm="onConfirm" />
    <van-cell title="生肖" :value="shengXiao" />
    <van-cell title="星座" :value="xingZhuo" />
    <van-field v-model="phone" label="手机" placeholder="请输入手机号" />
    <van-field v-model="height" label="身高" placeholder="身高cm" />
    <van-field v-model="weight" label="体重" placeholder="体重kg" />
    <van-field
      v-model="region"
      is-link
      readonly
      name="area"
      label="籍贯"
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
     <van-field v-model="company" label="就职单位" placeholder="请输入就职单位" />
  </van-cell-group>
  <div style="margin: 16px;">
    <van-button round block type="primary" native-type="submit">
      提交
    </van-button>
  </div>
</van-form>

</template>
