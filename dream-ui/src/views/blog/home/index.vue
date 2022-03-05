<template>
  <div>
    <BlogHeader :currentRouter="currentRouter"></BlogHeader>
    <GitHubFlag></GitHubFlag>
    <MusicPlayer></MusicPlayer>

    <div class="picsbox">
      <el-carousel :interval="5000" arrow="always">
        <el-carousel-item v-for="banner in bannerList" :key="banner.id">
          <div class="main-header" :style="'background: url('+banner.imgSrc+') center center / cover no-repeat rgb(34, 34, 34); overflow: hidden;'">
            <div class="vertical">
              <div class="main-header-content banner-inner">
                <h1 class="page-title" ><span>{{banner.title}}</span></h1>
                <h2 class="page-description"  style="display: -webkit-box;">{{ banner.subTitle }}</h2>
                <h3 class="page-author"  style="display: block;">{{banner.author}}</h3>
              </div>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
        <bubbles-effect :options="bubblesOptions"></bubbles-effect>
    </div>
    <a href="#arcList" class="up-arrow" >
      <i class="icon-angle-double-up"></i>
    </a>
    <div class="hotmessage">
      <div class="news">
        <div class="item">
          <i style="color: #ff0000" class="fa fa-heart" aria-hidden="true"></i>
          <VueTypedJs
            :strings="expression"
            :typeSpeed="200"
            :cursorChar="'ღ'"
            :startDelay="1000"
            :loop="true"
            :loopCount="Infinity"
          >
            <span id="typedItem" class="typing"></span>
          </VueTypedJs>
          <i style="color: #ff0000" class="fa fa-heart" aria-hidden="true"></i>
        </div>
      </div>
    </div>
    <div id="arcList" class="blogcontainer">
      <div class="blogsbox">
        <div class="blogs" v-for="item in articleList">
          <h3 class="blogtitle" @click="switchDetail(item.id)">
            {{ item.title }}
          </h3>
          <span v-if="item.showStyle == 1" class="blogpic">
            <a @click="switchDetail(item.id)" :title="item.title">
              <img
                v-if="item.coverImageList && item.coverImageList.length > 0"
                :src="item.coverImageList[0]"
                alt=""
              />
            </a>
          </span>
          <span v-else-if="item.showStyle == 2" class="bplist">
            <ul v-if="item.coverImageList && item.coverImageList.length > 0">
              <li v-for="img in item.coverImageList">
                <a @click="switchDetail(item.id)" :title="item.title">
                  <img :src="img" alt="" />
                </a>
              </li>
            </ul>
          </span>
          <span v-else-if="item.showStyle == 3" class="bigpic">
            <a
              @click="switchDetail(item.id)"
              :title="item.title"
              target="_blank"
            >
              <img
                v-if="item.coverImageList && item.coverImageList.length > 0"
                :src="item.coverImageList[0]"
                alt=""
              />
            </a>
          </span>
          <a @click="switchDetail(item.id)" target="_blank"
            ><p class="blogtext" v-html="item.abstractContent">
              {{ item.abstractContent }}
            </p></a
          >
          <div class="bloginfo">
            <ul>
              <li v-if="item.articleTags">
                <span v-if="t.aliasName" v-for="t in item.articleTags"  :style="'background:'+t.color" class="label">{{t.aliasName}}</span>
              </li>
<!--              <li ><span class="label c-b">{{ item.author }}</span></li>-->
<!--              <li v-if="item.categoryName"><span class="label c-b">{{ item.categoryName }}</span></li>-->
              <li v-if="item.publishTime"><span class="label c-b" >{{ item.publishTime }}</span></li>
            </ul>
          </div>
        </div>
        <div class="loading" v-show="loading" id="jsi-flying-fish-container">
        </div>
        <div v-show="!showMore" style="text-align: center" >
          <span>没有更多啦</span>
        </div>
      </div>
      <!--blogsbox end-->
      <div class="sidebar">
        <div class="cate">
          <div><h2 class="hometitle">崩~瞎卡拉卡🍀</h2></div>
          <SheroCat></SheroCat>
        </div>
        <div class="cate card" >
          <div class="sidecloumn">
            <div><h2 class="hometitle">标签</h2></div>
          </div>
          <div style="display: inline-block;">
            <span @click="tagClick(tag.id)" class="tagSpan" v-for="tag in tagList" :style="'background:' +tag.color">{{tag.value}}</span>
          </div>
        </div>
        <div class="tuijian card">
          <div class="sidecloumn">
            <div><h2 class="hometitle">动态</h2></div>
          </div>
          <ul class="sidenews" v-if="recommendArticlelist">
            <li v-for="article in recommendArticlelist">
              <a :href="['/detail/' + article.id]" target="_blank"
              >{{ article.title }}
              </a>
            </li>
          </ul>
        </div>
        <div class="links sidecloumn card">
          <div><h2 class="hometitle">友情链接</h2></div>
          <ul v-if="partnerList">
            <li v-for="partner in partnerList">
              <a
                :href="partner.siteUrl"
                :alt="partner.siteDesc"
                target="_blank"
                >{{ partner.siteName }}</a
              >
            </li>
          </ul>
        </div>
        <!-- 设置固定关注我们 -->
        <Focus></Focus>
      </div>

    </div>
    <BlogFooter></BlogFooter>
    <BackToTop></BackToTop>
  </div>
</template>
<script>
import '@/assets/blog/base.css'
import '@/assets/blog/index.css'
import '@/assets/blog/m.css'
import BlogHeader from '../components/BlogHeader'
import BlogFooter from '../components/BlogFooter'
import BackToTop from '../components/BackToTop'
import GitHubFlag from '../components/GitHubFlag'
import MusicPlayer from '../components/MusicPlayer'
import SheroCat from '../components/SheroCat'
import Focus from '../components/Focus'
import scrollReveal from 'scrollreveal'
import { VueTypedJs } from 'vue-typed-js'
import {L2Dwidget} from 'live2d-widget'
import {fishloading} from "@/utils/fish";
import {
  ArticleApi,
  CategoryApi,
  BannerApi,
  TagApi,
  PartnerApi,
  MusicApi
} from '@/api/blog'
export default {
  name: 'home',
  components: {
    BlogHeader,
    GitHubFlag,
    MusicPlayer,
    BlogFooter,
    BackToTop,
    Focus,
    VueTypedJs,
    SheroCat
  },
  data() {
    return {
      currentRouter: this.$route,
      queryForm: {
        pageIndex: 1,
        pageSize: 10,
        title: '',
        tag: '',
        categoryCodes: []
      },
      musicList: [],
      showMore: true,
      loading: true,
      articleList: [],
      topReadArticlelist: [],
      recommendArticlelist: [],
      bannerList: [],
      scrollReveal: scrollReveal(),
      partnerList: [],
      tagList: [],
      parentId: 0,
      treeData: {
        label: 'name',
        children: 'zones',
        isLeaf: 'isLeaf'
      },
      tabActiveName: 'first',
      expression: [
        '山野万里，你是我藏在微风中的欢喜。',
        '你都如何回忆我，带着笑或是很沉默。'
      ],
      bubblesOptions:{
        color: 'rgba(225,225,225,0.5)', //气泡颜色
        radius: 15, //气泡半径
        densety: 0.3, // 气泡密度 越大越密集(建议不要大于1)
        clearOffset: 0.2 // 气泡消失距离[0-1] 越大越晚消失
      }
    }
  },
  mounted() {
    // 缓存指针
    var _this = this
    _this.$nextTick(function() {
      _this.initScrollRevealOption()
      // 注册scroll事件并监听
      document.addEventListener('scroll', function() {
        var scrollTop = document.documentElement.scrollTop
        _this.scrollTop = scrollTop
        if (scrollTop + window.innerHeight >= document.body.clientHeight) {
          if (!_this.scrollDisable) {
            if (_this.showMore) {
              _this.initData()
            }
          }
        }
      })
      document.addEventListener('touchend', function() {
        var scrollTop = document.documentElement.scrollTop
        _this.scrollTop = scrollTop
        if (scrollTop + window.innerHeight + 20 >= document.body.clientHeight) {
          if (!_this.scrollDisable) {
            if (_this.showMore) {
              _this.initData()
            }
          }
        }
      })
    })
  },
  async created() {
    this.initData()
    this.initBanner()
    this.getRecommendArticleList()
    this.getTopReadArticleList()
    this.initPartner()
    this.initTagList()
    /* this.initMusic() */
    this.initLive2d();
  },
  methods: {
    initLive2d(){
      L2Dwidget.init({
        "model": {
          jsonPath: "https://unpkg.com/live2d-widget-model-tororo@1.0.5/assets/tororo.model.json",
          "scale": 1
        },
        "display": {
          "position": "left", //模型的表现位置
          "width": 150,  //模型的宽度
          "height": 300, //模型的高度
          "hOffset": 0,
          "vOffset": 10
        },
        "mobile": {
          "show": true,
          "scale": 0.5
        },
        "react": {
          "opacityDefault": 0.7,  //模型默认透明度
          "opacityOnHover": 0.2
        }
      });
    },
    switchDetail(id) {
      var routeData = this.$router.resolve({
        path: 'blog/detail',
        query: { id: id }
      })
      window.open(routeData.href, '_blank')
    },
    treeNodeClick(node) {
      var tree = this.$refs.mycatetree
      var nodes = tree.store.getCheckedNodes()
      var arraycodes = []
      for (var i = 0; i < nodes.length; i++) {
        var tem = nodes[i].code
        arraycodes.push(tem)
      }
      this.queryForm.categoryCodes = arraycodes
      this.articleList = []
      this.queryForm.pageIndex = 1
      this.initData()
    },
    loadCategory(node, resolve) {
      var _this = this
      if (node.level == 0) {
        _this.parentId = 0
      } else {
        _this.parentId = node.data.id
      }
      CategoryApi.getCategoryTreeData(_this.parentId).then(response => {
        if (response.code == 0) {
          resolve(response.data)
          _this.parentId = 0
        }
      })
    },
    initData() {
      var _this = this
      _this.scrollDisable = true
      _this.loading = true
      ArticleApi.listData(_this.queryForm).then(response => {
        if (response.code == 0) {
          var resultCount = response.data.rows.length
          if (resultCount > 0) {
            _this.articleList = _this.articleList.concat(response.data.rows)
            _this.showMore =response.data.totalPage > _this.queryForm.pageIndex ? true : false
            if(_this.showMore ){
              _this.queryForm.pageIndex = _this.queryForm.pageIndex + 1
            }
          }else{
            _this.showMore=false
          }
           setTimeout(function(result) {
             _this.loading = false
          }, 3000)
        }
        // 插入数据完成后
        _this.scrollDisable = false
      })
    },
    backToTop() {
      let timer = setInterval(function() {
        var osTop =
          document.documentElement.scrollTop || document.body.scrollTop
        var ispeed = Math.floor(-osTop / 5)
        document.documentElement.scrollTop = document.body.scrollTop =
          osTop + ispeed
        if (osTop === 0) {
          clearInterval(timer)
        }
      }, 30)
    },
    tagClick(value) {
      this.articleList=[];
      this.queryForm.tag=value;
      this.queryForm.pageIndex=1;
      this.initData();
    },
    initPartner() {
      var _this = this
      PartnerApi.getPartnerList().then(response => {
        if (response.code == 0) {
          _this.partnerList = response.data
        }
      })
    },
    initBanner() {
      let _this = this
      BannerApi.getBannerList({sortCode:1}).then(response => {
        if (response.code == 0) {
          _this.bannerList = response.data.rows
        }
      })
    },
    initTagList() {
      let _this = this
      TagApi.getTagList().then(response => {
        if (response.code == 0) {
          _this.tagList = response.data
          if(_this.tagList.length>0){
            _this.tagList.unshift({id:"",value:"全部",color:"red"})
          }
        }
      })
    },
    getRecommendArticleList() {
      let _this = this
      ArticleApi.getRecommendArticleList().then(response => {
        if (response.code == 0) {
          _this.recommendArticlelist = response.data
        }
      })
    },
    getTopReadArticleList() {
      let _this = this
      ArticleApi.getTopReadArticleList().then(response => {
        if (response.code == 0) {
          _this.topReadArticlelist = response.data
        }
      })
    },
    initScrollRevealOption() {
      this.scrollReveal.reveal('.blogs', {
        duration: 500, //动画的时长
        delay: 100, //延迟时间
        origin: 'bottom', //动画开始的位置，'bottom', 'left', 'top', 'right'
        reset: true, //回滚的时候是否再次触发动画
        mobile: false, //在移动端是否使用动画
        distance: '10px', //滚动的距离，单位可以用%，rem等
        opacity: 1, //其他可用的动画效果
        easing: 'linear',
        scale: 1
      })
    }
  }
}
</script>
<style scoped>
text {
  cursor: pointer;
}
.aplayer.aplayer-fixed.aplayer-narrow .aplayer-body {
  width: 78px !important;
}
#typedItem {
  font-size: 12px;
}
.typed-element {
  display: inline-block !important;
  font-size: 12px !important;
}
.hotmessage {
  /* height: 68px;*/
  z-index: 10;
  /*background: #000;*/
  opacity: 0.9;
}
.hotmessage .item {
  position: relative;
  text-align: center;
  border: none;
  font-size: 18px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  padding: 5px;
}
.tagSpan{
  cursor: pointer;
  line-height: 17px;
  height: 24px;
  display: block;
  float: left;
  padding: 3px 10px;
  margin: 10px 5px 0 0;
  border-radius: 3px;
  -moz-transition: all 0.5s;
  -webkit-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
  color: #fff;
}
.typed-element {
  display: block;
}
.main-header {
  position: absolute;
  display: table;
  width: 100%;
  height: 100vh;
  max-height: 100vh;
  text-align: center;
  background-size: cover;
  overflow: hidden;
  top: 0;
  left: 0;
  z-index: 3;
  box-shadow: 0 1px 2px rgba(150,150,150,0.7);
}
.vertical {
  display: table-cell;
  vertical-align: middle;
  width: 100%;
}
.banner-inner{
  margin: 0 auto;
  position: relative;
  width: 80%;
  max-width: 800px;
  padding: 10px;
}
.page-title {
  animation: pageTitleText 2s infinite !important;
}
.page-title{
  -webkit-animation: fade-in-down 1s both;
  mix-blend-mode: screen;
  font-size: 5rem;
  font-family: Playball,cursive;
}
@keyframes pageTitleText{
  0%{text-shadow:2px 0 0 tomato}10%{text-shadow:-2px -2px 0 gold}100%,
  20%{text-shadow:2px 0 0 #0f0}30%,70%{text-shadow:2px 0 0 #40e0d0}
  40%{text-shadow:2px 2px 0 tomato}50%{text-shadow:0 -2px 0 gold}
  60%{text-shadow:0 2px 0 #0f0}80%{text-shadow:2px -4px 0 tomato}90%{text-shadow:2px 2px 0 gold}
}
.page-title, .sb-title {
  letter-spacing: -1px;
  font-weight: 700;
  color: #fff;
}
.page-title, .sb-title {
  text-shadow: 0 3px 6px rgba(0,0,0,0.3);
}
.page-description {
  font-size: 4.2rem;
}
.page-description {
  font-size: 2.2rem;
  -webkit-animation: fade-in-down .9s;
  animation: fade-in-down .9s both;
  -webkit-animation-delay: .1s;
  animation-delay: .3s;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 10;
  overflow: hidden;
}
.page-author {
  font-family: Playball,cursive;
  margin-top: 10px;
  font-size: 2.5rem !important;
}
.page-author, .page-description {
  margin: 0;
  letter-spacing: .01rem;
  color: rgba(255,255,255,.8);
  text-shadow: 0 3px 6px rgba(0,0,0,0.5);
  display: none;
  font-weight: 400;
}
.page-description {
  font-family: 'Long Cang',cursive;
  line-height: 1.5em;
}
.picsbox canvas{
  z-index: 99;
  width: 90%;
  height: 98vh;
  margin-left: 5%;
  bottom: 10px;
}
.picsbox /deep/ .el-carousel__container {
  height: 100vh !important;
}
</style>
