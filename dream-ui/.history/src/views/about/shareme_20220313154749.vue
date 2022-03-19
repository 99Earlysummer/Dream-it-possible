<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-13 15:47:26
-->
<script>
import axios from "axios";
import { nanoid } from "nanoid";
import { areaList } from "@vant/area-data";
import { ResumeApi } from "@/api/blog";
import { getToken } from "@/utils/auth";
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
  Divider,
  DatetimePicker,
} from "vant";
export default {
  components: {
    [Button.name]: Button,
    [Form.name]: Form,
    [Cell.name]: Cell,
    [CellGroup.name]: CellGroup,
    [Divider.name]: Divider,

    areaList,
  },
  data() {
    return {
      fileList: [],
      form: {
        uuid: "",
        name: "",
        sex: "1",
        birth: "",
        shengXiao: "",
        xingZhuo: "",
        height: "",
        weight: "",
        region: "",
        regionVal: "",
        regionItem: [],
        school: "",
        company: "",
        phone: "",
        description: "",
        wanted: "",
        headImage: "",
        photos: "",
      },
    };
  },
  created() {
    if (this.$route.query.uuid) {
      this.uuid = this.$route.query.uuid;
      this.initDetail(this.uuid);
    }
  },
  methods: {
    initDetail(uuid) {
      ResumeApi.getDetail(uuid).then((response) => {
        if (response.code == 0) {
          this.form = response.data;
          let regionItems = response.data.regionItem;
          this.form.region =
            regionItems[0].name +
            " " +
            regionItems[1].name +
            " " +
            regionItems[2].name;
          this.form.regionVal = regionItems[2].code;
          this.fileList=response.data.photos.split(",");
        }
      });
    },

  },
};
</script>
<template>
  <div>
    <van-form>
      <van-cell-group title="基本信息">
        <van-cell title="姓名" :value="form.name" />
        <van-cell title="性别" :value="form.sex==0?'男':'女'" />
        <van-cell title="生日" :value="form.birth" />
        <van-cell title="生肖" :value="form.shengXiao" />
        <van-cell title="星座" :value="form.xingZhuo" />
        <van-cell title="手机" :value="form.phone" />
        <van-cell title="身高(cm)" :value="form.height" />
        <van-cell title="体重(kg)" :value="form.weight" />
        <van-cell title="籍贯" :value="form.regionVal" />
        <van-cell title="学校" :value="form.school" />
        <van-cell title="单位" :value="form.company" />
        <van-cell title="自我介绍" :value="form.description" />
        <van-cell title="对Ta的期待" :value="form.wanted" />
        <van-swipe :autoplay="3000" lazy-render>
          <van-swipe-item v-for="(image,index) in fileList" :key="image">
            <img :src="image" />
          </van-swipe-item>
          <div class="custom-indicator">{{ index + 1 }}/{{ fileList.length }}</div>
        </van-swipe>
      </van-cell-group>
    </van-form>
    <van-divider dashed></van-divider>
  </div>
</template>
<style scoped>
 .custom-indicator {
    position: absolute;
    right: 5px;
    bottom: 5px;
    padding: 2px 5px;
    font-size: 12px;
    background: rgba(0, 0, 0, 0.1);
  }
</style>
