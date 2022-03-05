<template>
  <div
    ref="followUs"
    :class="[scrollTop > followUsPosition ? 'gd' : '', 'guanzhu']"
    id="follow-us"
  >
    <h2 class="hometitle">造作啊 骚年！</h2>
    <ul>
<!--      <li class="wx" @mouseenter="mouseenter" @mouseleave="mouseleave">
        <img :src="showqrcode" />
      </li>-->
      <li class="wx" >
        <img src="http://file.miaoleyan.com/travelgroup.jpg" />
      </li>
      <!-- <li>
          <div class="qqgroup">
                <a v-for="(m,index) in qqloginarr" target="_blank" :href="m.url">
                  <img border="0" :src="index==currentindex?qqgroupimged:showqqimg" :alt="m.title" :title="m.title">
                </a>
            </div>
      </li>-->
    </ul>
  </div>
</template>
<script>
export default {
  name: 'Focus',
  data() {
    return {
      scrollDisable: false,
      scrollTop: 0,
      defaultfollowUsPosition: 0,
      followUsPosition: 0,
      wechatqrurl: '~@/assets/blog/image/tan_weixin_qr_1.jpg',
      qqgroupimg: '~@/assets/blog/image/qqgroup.png',
      qqgroupimged: '~@/assets/blog/image/qqgrouped.png',
      showqqimg: '~@/assets/blog/image/qqgroup.png',
      showqrcode: '',
      qqloginarr: [
        {
          title: '说走就走的旅行',
          url:
            'http://shang.qq.com/wpa/qunwpa?idkey=cd43ce7a73d88cdff96bb954294a0cc8de9cc05f4c99b1646eec922745b9fb63',
          qrcode: 'http://file.miaoleyan.com/travelgroup.jpg'
        }
      ],
      currentindex: 2,
      timer: null
    }
  },
  mounted() {
    var _this = this
    // 注册scroll事件并监听
    window.addEventListener(
      'scroll',
      () => {
        var scrollTop = document.documentElement.scrollTop
        _this.scrollTop = scrollTop
        var followUsPosition = _this.$refs.followUs.offsetTop
        _this.defaultfollowUsPosition = followUsPosition
        if (scrollTop <= _this.defaultfollowUsPosition) {
          _this.followUsPosition = _this.defaultfollowUsPosition
        }
      },
      true
    )
    //this.timer = setInterval(this.changeImage, 5000)
  },
  methods: {
    changeImage() {
      this.showqrcode = this.qqloginarr[this.currentindex].qrcode
      if (this.currentindex >= 2) {
        this.currentindex = 0
      } else {
        this.currentindex++
      }
    },
    mouseenter() {
      clearInterval(this.timer)
    },
    mouseleave() {
      this.timer = setInterval(this.changeImage, 5000)
    }
  }
}
</script>
<style scoped>
.guanzhu ul li {
  font-size: 12px;
  margin-bottom: 10px;
  background: #fff;
  color: #525252;
  line-height: 40px;
  padding: 0 0 0 34px;
  border: 1px solid #ddd;
  border-radius: 2px;
  position: relative;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.guanzhu .sina {
  border: #ec3d51 1px solid;
  background: url(~@/assets/blog/image/blog/gzbg.jpg) no-repeat 0 10px;
}
.guanzhu .tencent {
  border: #68a6d6 1px solid;
  background: url(~@/assets/blog/image/blog/gzbg.jpg) no-repeat 0 -43px;
}
.guanzhu .qq {
  border: #2ab39a 1px solid;
  background: url(~@/assets/blog/image/blog/gzbg.jpg) no-repeat 0 -98px;
}
.guanzhu .email {
  border: #12aae8 1px solid;
  background: url(~@/assets/blog/image/blog/gzbg.jpg) no-repeat 0 -150px;
}
.guanzhu .wxgzh {
  border: #199872 1px solid;
  background: url(~@/assets/blog/image/blog/gzbg.jpg) no-repeat 0 -200px;
}
.guanzhu .wx {
  overflow: hidden;
  padding: 0;
}
.guanzhu .wx img {
  width: 100%;
}
.guanzhu ul li span {
  float: right;
  text-align: center;
  width: 100px;
  -moz-transition: all 0.5s ease;
  -webkit-transition: all 0.5s ease;
  -ms-transition: all 0.5s ease;
  -o-transition: all 0.5s ease;
  transition: all 0.5s ease;
  transition: all 0.5s;
}
.guanzhu .sina span {
  background: #ec3d51;
}
.guanzhu .tencent span {
  background: #68a6d6;
}
.guanzhu .qq span {
  background: #2ab39a;
}
.guanzhu .email span {
  background: #12aae8;
}
.guanzhu .wxgzh span {
  background: #199872;
}
.guanzhu a span {
  color: #fff;
}
.guanzhu ul li:hover span {
  width: 120px;
}
.qqgroup {
  padding: 0px !important ;
  margin: 0 auto;
  display: block;
  clear: both;
}
.qqgroup a {
  float: left;
  width: 30%;
}
</style>
