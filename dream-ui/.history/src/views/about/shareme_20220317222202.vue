<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-17 22:22:02
-->
<script>
import { ResumeApi } from "@/api/blog";
import {
  Form,
  Cell,
  CellGroup,
  Button,
  Divider,
  Swipe,
  SwipeItem
} from "vant";
export default {
  components: {
    [Button.name]: Button,
    [Form.name]: Form,
    [Cell.name]: Cell,
    [CellGroup.name]: CellGroup,
    [Divider.name]: Divider,
    [Swipe.name]: Swipe,
    [SwipeItem.name]: SwipeItem,
  },
  data() {
    return {
      fileList: [],
      gid:"",
      form: {
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
    if (this.$route.query.gid) {
      this.gid = this.$route.query.gid;
      this.initDetail(this.gid);
    }
  },
  methods: {
    initDetail(gid) {
      ResumeApi.getShareData(gid).then((response) => {
        if (response.code == 0) {
          this.form = response.data;
        }
      });
    },

  },
};
</script>
<template>
  <div>
    <van-swipe style="height: 100vh;color:orange" vertical>
      <van-swipe-item>
        <van-cell-group title="基本信息">
        <van-cell title="姓名" :value="form.name" />
        <van-cell title="性别" :value="form.sex==0?'男':'女'" />
        <van-cell title="生日" :value="form.birth" />
        <van-cell title="生肖" :value="form.shengXiao" />
        <van-cell title="星座" :value="form.xingZhuo" />
        </van-cell-group>
      </van-swipe-item>
      <van-swipe-item>
        <van-cell-group title="基本信息">
        <van-cell title="身高(cm)" :value="form.height" />
        <van-cell title="体重(kg)" :value="form.weight" />
        <van-cell title="籍贯" :value="form.regionVal" />
        <van-cell title="学校" :value="form.school" />
        <van-cell title="单位" :value="form.company" />
        </van-cell-group>
      </van-swipe-item>
      <van-swipe-item>
        <van-cell-group title="基本信息">
          <van-cell title="自我介绍" :value="form.description" />
        </van-cell-group>
      </van-swipe-item>
      <van-swipe-item>
        <van-cell-group title="基本信息">

          <van-cell title="对Ta的期待" :value="form.wanted" />
        </van-cell-group>
      </van-swipe-item>
    </van-swipe>
    <!-- <van-form>
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
            <div class="custom-indicator">{{ index + 1 }}/{{ fileList.length }}</div>
          </van-swipe-item>

        </van-swipe>
      </van-cell-group>
    </van-form>
    <van-divider dashed></van-divider> -->
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
