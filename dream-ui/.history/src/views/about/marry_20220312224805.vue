<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-12 22:48:04
-->
<script>
import { areaList } from "@vant/area-data";
import { ResumeApi } from "@/api/blog";
import {
  Form,
  Field,
  Cell,
  CellGroup,
  Button,
  RadioGroup,
  Radio,
  Calendar,
  Area,
  Popup,
  Uploader,
} from "vant";
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
    [Uploader.name]: Uploader,
    areaList,
  },
  data() {
    return {
      showCalendar: false,
      showArea: false,
      form: {
        username: "",
        sex: "1",
        birth: "",
        shengXiao: "",
        xingZhuo: "",
        height: "",
        weight: "",
        region: "",
        school: "",
        company: "",
        phone: "",
        regionList: areaList,
        description: "",
        hobbies: "",
        wanted: "",
        headImage: "",
        photos: [
          { url: "https://img.yzcdn.cn/vant/leaf.jpg" },
          // Uploader 根据文件后缀来判断是否为图片文件
          // 如果图片 URL 中不包含类型信息，可以添加 isImage 标记来声明
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
        ],
      },
    };
  },
  methods: {
    onSubmit() {
      let data=this.form;
      debugger
      // ResumeApi.saveResume(data).then((response) => {
      //   if (response.code == 0) {
      //    Toast.success(response.msg);
      //   }
      // });
    },
    onConfirm() {},
    afterRead(res) {
      console.log(res);
    },
  },
};
</script>
<template>
  <van-form @submit="onSubmit">
    <van-cell-group title="基本信息">
      <van-field
        v-model="form.name"
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
      <van-field
        label="生日"
        v-model="birth"
        placeholder="点击选择生日"
        @click="showCalendar = true"
      />
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
      <van-field name="uploader" label="照片">
        <template #input>
          <van-uploader
            :preview-size="60"
            multiple
            :max-count="9"
            :after-read="afterRead"
            v-model="photos"
          />
        </template>
      </van-field>

      <van-popup v-model:show="showArea" position="bottom">
        <van-area
          :area-list="regionList"
          @confirm="onConfirm"
          @cancel="showArea = false"
        />
      </van-popup>
      <van-field v-model="school" label="学校" placeholder="请输入毕业院校" />
      <van-field v-model="company" label="单位" placeholder="请输入就职单位" />
      <van-field
        v-model="description"
        rows="3"
        autosize
        label="自我介绍"
        type="textarea"
        maxlength="500"
        placeholder="展现优秀的你！"
        show-word-limit
      />
      <van-field
        v-model="description"
        rows="3"
        autosize
        label="对Ta的期待"
        type="textarea"
        maxlength="500"
        placeholder="喜欢和合适总能撞个满怀"
        show-word-limit
      />
    </van-cell-group>
    <van-button type="primary" size="normal">普通按钮</van-button>
    <van-button type="primary" size="normal">普通按钮</van-button>
    <van-button round block type="primary" native-type="submit"
      >提交</van-button
    >
  </van-form>
</template>
