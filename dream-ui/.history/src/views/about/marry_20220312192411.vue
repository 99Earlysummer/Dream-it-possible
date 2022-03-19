<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-12 19:23:40
-->
<script>
import { areaList } from "@vant/area-data";
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
      username: "谭超",
      password: "",
      sex: "1",
      showCalendar: false,
      showArea: false,
      birth: "",
      shengXiao: "羊",
      xingZhuo: "天秤",
      height: "",
      weight: "",
      region: "",
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
    };
  },
  methods: {
    onSubmit() {},
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

    </van-cell-group>
    <van-cell-group inset>
      
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
          <van-uploader :preview-size="60"
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
    <div style="margin: 16px">
      <van-button round block type="primary" native-type="submit">
        提交
      </van-button>
    </div>
  </van-form>
</template>
