<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-17 23:32:35
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
  SwipeItem,
  Image as VanImage,
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
    [VanImage.name]: VanImage,
  },
  data() {
    return {
      fileList: [],
      gid: "",
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
  <div class="loader-section">
    <van-swipe
      style="height: 100vh; color: orange"
      vertical
      :loop="false"
      indicator-color="orange"
    >
      <van-swipe-item>
        <img class="profile-thumb" src="@/assets/image/profile.jpg" />
        <h1>
          <span class="subtitle">山野万里，你是我藏在微风中的欢喜。</span>
        </h1>
      </van-swipe-item>
      <van-swipe-item>
        <van-cell-group title="基本信息">
          <van-cell :value="form.name" />
          <van-cell :value="form.sex == 0 ? '男' : '女'" />
          <van-cell :value="form.birth" />
          <van-cell :value="form.shengXiao" />
          <van-cell :value="form.xingZhuo" />
          <van-cell :value="form.height" />
          <van-cell :value="form.weight" />
          <van-cell :value="form.regionVal" />
          <van-cell :value="form.school" />
          <van-cell :value="form.company" />
        </van-cell-group>
      </van-swipe-item>
      <van-swipe-item>
        <van-cell-group title="自我介绍">
          <van-cell :value="form.description" />
        </van-cell-group>
      </van-swipe-item>
      <van-swipe-item>
        <van-cell-group title="对Ta的期待">
          <van-cell :value="form.wanted" />
        </van-cell-group>
      </van-swipe-item>
      <van-swipe-item>
        <van-cell-group title="生活照片">
          <van-cell :value="form.wanted" />
        </van-cell-group>
      </van-swipe-item>
      <template #indicator>
        <a href="#arcList" class="up-arrow">
          <i class="icon-angle-double-up"></i>
        </a>
      </template>
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
.loader-section {
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  background: #7171c6;

  -webkit-transform: translateX(0);
  -ms-transform: translateX(0);
  transform: translateX(0);
}
.custom-indicator {
  position: absolute;
  right: 5px;
  bottom: 5px;
  padding: 2px 5px;
  font-size: 12px;
  background: rgba(0, 0, 0, 0.1);
}
.profile-thumb {
  background-size: cover !important;
  background-position: center center;
  background-repeat: no-repeat;
  position: relative;
  height: 10rem;
  width: 10rem;
  margin: 0 auto;
  margin-bottom: 30px;
  -webkit-border-radius: 50%;
  -moz-border-radius: 50%;
  -ms-border-radius: 50%;
  border-radius: 50%;
  display: block;
}
.subtitle{
    font-family: "Kaushan Script", cursive;
    margin-bottom: 30px;
    font-size: 50px;
    line-height: 1.3;
    font-weight: 300;
    -webkit-transform: rotate(-5deg);
    -moz-transform: rotate(-5deg);
    -ms-transform: rotate(-5deg);
    -o-transform: rotate(-5deg);
    transform: rotate(-5deg);
}
.up-arrow {
  width: 2rem;
  height: 2rem;
  position: fixed;
  bottom: 2rem;
  left: 50%;
  transform: translate3d(-50%, 0, 0);
  z-index: 999;
  background: none;
  border: none;
  padding: 0;
  animation: upArrowAni 2s infinite linear;
}
.up-arrow i {
  width: 5rem;
  height: 5rem;
  text-align: center;
  line-height: 5rem;
  color: red;
  font-size: 2.5rem;
}
@keyframes upArrowAni {
  0% {
    opacity: 0;
    transform: translate3d(-50%, 30%, 0);
  }

  30% {
    opacity: 1;
    transform: translate3d(-50%, -20%, 0);
  }

  60% {
    opacity: 0;
    transform: translate3d(-50%, -35%, 0);
  }

  100% {
    opacity: 0;
    transform: translate3d(-50%, -50%, 0);
  }
}
[class^="icon-"]:before,
[class*=" icon-"]:before {
  font-family: "my-custom-icon-font";
  font-style: normal;
  font-weight: normal;
  speak: none;
  display: inline-block;
  text-decoration: inherit;
  width: 1em;
  margin-right: 0.2em;
  text-align: center;
  font-variant: normal;
  text-transform: none;
  line-height: 1em;
  margin-left: 0.2em;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.icon-note:before {
  content: "\e800";
}
.icon-angle-double-up:before {
  content: "\e801";
}
@font-face {
  font-family: "my-custom-icon-font";
  src: url(~@/assets/fonts/my-custom-icon-font.eot);
  src: url(~@/assets/fonts/my-custom-icon-font.eot) format("embedded-opentype"),
    url(~@/assets/fonts/my-custom-icon-font.woff) format("woff"),
    url(~@/assets/fonts/my-custom-icon-font.ttf) format("truetype"),
    url(~@/assets/fonts/my-custom-icon-font.svg#my-custom-icon-font)
      format("svg");
  font-weight: normal;
  font-style: normal;
}
</style>
