<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-19 17:33:01
-->
<script>
import { ResumeApi,MusicApi } from "@/api/blog";
import { VueTypedJs } from 'vue-typed-js'
import APlayer from 'aplayer';
import 'aplayer/dist/APlayer.min.css';
import {
  Form,
  Cell,
  CellGroup,
  Button,
  Divider,
  Swipe,
  SwipeItem,
  Image as VanImage,
  ImagePreview
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
   [ImagePreview.Component.name]: ImagePreview.Component,
    VueTypedJs,
    APlayer
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
      index:1,
      photos:['https://img.yzcdn.cn/vant/apple-1.jpg',
      'https://img.yzcdn.cn/vant/apple-2.jpg',],
      expression:["山野万里 你是我藏在微风中的欢喜"],
      musicData:[],
      musicPanelShowState: false,
      musicPlayState:true,
      errorImg: 'this.src="/images/music/a1.png"',
      isload: false,
      aplayerMo:{}
    };
  },
  created() {
    if (this.$route.query.gid) {
      this.gid = this.$route.query.gid;
      this.initDetail(this.gid);
    }
  },
  mounted() {
    //初始化音乐列表
    this.getTopMusicList()
    console.log('初始化音乐完成')
    if(!this.aplayerMo.paused){
      this.musicPlayState=true;
    }else{
      this.musicPlayState=false;
    }
  },
  methods: {
    initDetail(gid) {
      ResumeApi.getShareData(gid).then((response) => {
        if (response.code == 0) {
          this.form = response.data;
          this.photos=response.data.photos.split(",");
          this.form.region =response.data.province +
            " " +
            response.data.city +
            " " +
            response.data.county;
          this.form.xingZhuo = this.birthToConstellation(this.form.birth);
          this.form.shengXiao = this.birthToShengxiao(this.form.birth);
        }
      });
    },
    getTopMusicList(){
      var _this=this;
      MusicApi.getTopMusicList({})
        .then(res => {
          if (res.code==0&&res.data.length>0 ) {
            _this.musicData = _this.formatMusicData(res.data);
          } else {
            let defaultData = [
              {
                name: "暖暖",
                artist: "梁静茹",
                url:
                  "http://file.miaoleyan.com/nndt/SU8Wxyi9s7VLtJlNsQl3a3Bw0Gm53Qm0",
                cover:
                  "https://tvax2.sinaimg.cn/crop.0.0.512.512.180/67ab17f5ly8giqfx4tro7j20e80e8dfz.jpg?KID=imgbed,tva&Expires=1606910591&ssig=BloRN1gnw0"
              }
            ];
            _this.musicData =defaultData;
          }
          this.aplayerMo=new APlayer({
            container: document.getElementById('aplayer'),
            //mini: false,
            autoplay: true,
            fixed: false,
            theme: '#FADFA3',
            loop: 'all',
            order: 'list',
            preload: 'auto',
            volume: 0.7,
            mutex: true,
            listFolded: false,
            listMaxHeight: 90,
            lrcType: 3,
            audio: _this.musicData
          })
        })
        .catch(error => {
        });
    },
    formatMusicData(dataList) {
      var arr = []
      for (var item of dataList) {
        var currentModel = {
          url: item.url,
          name: item.title,
          artist: item.singer,
          cover: item.converUrl
        }
        arr.push(currentModel)
      }
      return arr
    },
    phClick(e){
      ImagePreview(this.photos);
    },
    onChange(newIndex){
      this.index = newIndex;
    }
  },
};
</script>
<template>
  <div class="loader-section">
    <van-swipe
      style="height: 100vh; color: #7171c6"
      vertical
      :loop="false"
      indicator-color="orange"
    >
      <van-swipe-item>
        <img class="profile-thumb" src="https://portrait.gitee.com/uploads/avatars/user/215/645488_MiraculousWarmHeart_1625814478.png" />
        <div class="subtitle">
            <ul>
              <li>山野万里</li>
              <li>你是我藏在微风中的欢喜</li>
            </ul>
        </div>
       <van-cell-group title="基本信息">
          <van-cell title="姓名" :value="form.name" />
          <van-cell title="性别" :value="form.sex == 0 ? '男' : '女'" />
          <van-cell title="生日" :value="form.birth" />
          <van-cell title="生肖" :value="form.shengXiao" />
          <van-cell title="星座" :value="form.xingZhuo" />
          <van-cell title="身高" :value="form.height+'cm'" />
          <van-cell title="体重" :value="form.weight+'kg'" />
          <van-cell title="籍贯" :value="form.region" />
          <van-cell title="毕业院校" :value="form.school" />
          <van-cell title="就职单位" :value="form.company" />
        </van-cell-group>
      </van-swipe-item>
      <van-swipe-item>
         <van-cell-group title="自我介绍">
          <van-cell :value="form.description" />
        </van-cell-group>
         <van-cell-group title="对Ta的期待">
          <van-cell :value="form.wanted" />
        </van-cell-group>
        <van-cell-group title="">

        </van-cell-group>
        <van-cell-group title="有趣的灵魂总是逗比">
           <ul style="margin:1em">
              <li v-for="(m,index) in photos">
                <img class="vphoto" :src="m" @click="phClick"/>
              </li>
            </ul>
           <van-image-preview v-show="photos.length>0" :images="photos"></van-image-preview>
        </van-cell-group>
      </van-swipe-item>
      <template #indicator>
        <a href="#arcList" class="up-arrow">
          <i class="icon-angle-double-up"></i>
        </a>
      </template>
    </van-swipe>
  </div>
</template>
<style scoped>
.loader-section {
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  background: #fff;
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
  margin-bottom: 1em;
  -webkit-border-radius: 50%;
  -moz-border-radius: 50%;
  -ms-border-radius: 50%;
  border-radius: 50%;
  display: block;
  margin-top: 2em;
}
.subtitle{
    font-family: "Kaushan Script", cursive;
   margin-bottom: 2em;
   text-align:center;
   font-size: 1.3em;;
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
#aplayer{
  padding: 1em;
}
#aplayer /deep/.aplayer-pic {
  height: 66px;
  width: 66px;
  transition: 0.5s;
  border-radius: 50%;
  border: 2px #ff8c00 solid;
  -webkit-animation: rotating 3.5s linear infinite;
  -moz-animation: rotating 3.5s linear infinite;
  -o-animation: rotating 3.5s linear infinite;
  animation: rotating 3.5s linear infinite;
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
@keyframes titleText{
  0%{text-shadow:2px 0 0 tomato}10%{text-shadow:-2px -2px 0 gold}100%,
  20%{text-shadow:2px 0 0 #0f0}30%,70%{text-shadow:2px 0 0 #40e0d0}
  40%{text-shadow:2px 2px 0 tomato}50%{text-shadow:0 -2px 0 gold}
  60%{text-shadow:0 2px 0 #0f0}80%{text-shadow:2px -4px 0 tomato}90%{text-shadow:2px 2px 0 gold}
}
.vphoto{
  float: left;
  margin: 0.2em;
  height: 6em;
  width:  7em;
}
</style>
