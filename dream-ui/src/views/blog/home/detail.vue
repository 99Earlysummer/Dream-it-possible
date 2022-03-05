<template>
  <div>
    <BlogHeader :currentRouter="currentRouter"></BlogHeader>
    <GitHubFlag></GitHubFlag>
    <MusicPlayer></MusicPlayer>
    <div
      class="warmer-section"
      :style="'background-image: url(' + bannerArray[0] + ') '"
    >
      <div class="main-header-content inner">
        <h3 class="news_title">{{ articleItem.title }}</h3>
        <div class="article-info" id="articleInfo">
          <p class="article-info-text">
            <span class="label">发表于 {{ articleItem.publishTime }}</span>
             <span v-if="t.aliasName" v-for="t in articleItem.articleTags"  :style="'background:'+t.color" class="label">{{t.aliasName}}</span>
          </p>
        </div>
      </div>
    </div>

    <div class="detailcontainer">
      <div class="infosbox">
        <div class="newsview">
          <div class="news_con" id="divcontent">
            <mavon-editor
              class="md-style"
              ref="md"
              :value="articleItem.content"
              :subfield="false"
              :defaultOpen="'preview'"
              :toolbarsFlag="true"
              :toolbars="{navigation:true}"
              :editable="false"
              :scrollStyle="false"
              :ishljs="true"
              :navigation="true"
              :boxShadow="false"
              :transition="true"
              @navigationToggle="navClick"
            />
          </div>
        </div>
      </div>
    </div>

    <div class="big_img">
      <div class="swiper-container2">
        <div class="swiper-wrapper"></div>
      </div>
    </div>
    <div class="sidebardetail">
      <div class="tuijian">
        <h2 class="hometitle">手机浏览</h2>
        <ul class="tjpic">
          <vue-qr ref="Qrcode" :text="qrCode.text" :logoSrc="qrCode.logo" qid="testQrId"></vue-qr>
        </ul>
      </div>
    </div>
    <div class="fihsh-loading"  id="jsi-flying-fish-container"></div>
    <BackToTop></BackToTop>
  </div>

</template>
<script>
import '@/assets/blog/base.css'
import '@/assets/blog/index.css'
import '@/assets/blog/comment.css'
import '@/assets/blog/m.css'
import { ArticleApi, CategoryApi, BannerApi, TagApi } from '@/api/blog'
import {L2Dwidget} from 'live2d-widget'
import MusicPlayer from '../components/MusicPlayer'
import BlogHeader from "../components/BlogHeader";
import GitHubFlag from "../components/GitHubFlag";
import BlogFooter from "../components/BlogFooter";
import BackToTop from "../components/BackToTop";
// 二维码组件
import VueQr from 'vue-qr'
import Focus from "../components/Focus";
import {fishloading} from "@/utils/fish";
import { formatDate} from 'element-ui/src/utils/date-util';
export default {
  name: 'detail',
  components: {
    BlogHeader,
    GitHubFlag,
    MusicPlayer,
    BlogFooter,
    BackToTop,
    VueQr,
    Focus,
    fishloading
  },
  data() {
    return {
      qrCode: {
        text: 'http://www.miaoleyan.com',
        logo: 'https://pic3.zhimg.com/80/v2-b1c4069afdec39fdd6fb0888395fc02e_720w.jpg?source=1940ef5c'
      },
      navStatus:false,
      currentRouter: this.$route,
      userItem: {},
      returnUrl: '',
      commentdata: '',
      bannerArray: [
        'http://file.miaoleyan.com/file/blog/WnqV0XJmLJKLtLGkwYeNGM6J3TTtslqd'
      ],
      randomIndex: 0,
      articleItem: {
        status: '0',
        title: '', // 文章题目
        author: '',
        categoryId: 0,
        categoryName: '',
        content: '', // 文章内容
        abstractContent: '', // 文章摘要
        coverImageList: [], // 文章图片
        publishTime: undefined, // 前台展示时间
        showStyle: 0,
        id: 0,
        openComment: 0, //打开评论
        articleTags: []
      },
      isShowLogin: false,
      articleTagAliaName: [],
      randomBannerItem: {},
      RelvantArticle: [],
      detailhtml: '',
      showMore: true,
      loading: true,
      offset: 300,
      offset_opacity: 1200,
      scroll_top_duration: 700,
      scrollTop: 0,
      defaultfollowUsPosition: 0,
      followUsPosition: 0,
      imageurl: '',
      showbigimage: false
    }
  },
  created() {
    var articleId = this.$route.query.id
    this.initArticleModel(articleId)
    this.getRelvantArticle()
    this.getRandomBanner()
    this.initLive2d();
    this.qrCode.text=window.location.origin+this.$route.fullPath
  },
   mounted() {
     var _this=this;
     setTimeout(function(){
        _this.navClick();
    },2000);
  },
  methods: {
    navClick(){
      this.$nextTick(function () {
        let _aList = document.querySelectorAll(".v-note-navigation-content a");
        for (let i = 0; i < _aList.length; i++) {
          let _aParent = _aList[i].parentNode;
          let _a = _aParent.firstChild;
          if (!_a.id) continue; // 把不属于导航中的a标签去掉，否则会报错
          let _text = _aParent.lastChild;
          let text = _text.textContent;
          _a.href = "#" + _a.id;
          _a.innerText = text;
          _aParent.removeChild(_text);
          // _a.style.color = "red";
        }
      });
    },
    initLive2d(){
      L2Dwidget.init({
        "model": {
          jsonPath: "https://unpkg.com/live2d-widget-model-tororo@1.0.5/assets/tororo.model.json",
          "scale": 1
        },
        "display": {
          "position": "right", //模型的表现位置
          "width": 150,  //模型的宽度
          "height": 300, //模型的高度
          "hOffset": 0,
          "vOffset": -60
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
    changeLoginState(data) {
      this.isShowLogin = data
    },
    getRandomBanner() {
      var _this = this
      var param = {
        pageIndex: 1,
        pageSize: 10,
        title: '',
        sortCode: '1'
      }
      BannerApi.getBannerList(param).then(response => {
        if (response.code == 0) {
          var random = parseInt(Math.random() * response.data.rows.length, 10)
          _this.randomBannerItem = response.data.rows[random]
        }
      })
    },
    getRelvantArticle() {
      var _this = this
      ArticleApi.getRelevantArticle(this.articleItem.id).then(response => {
        if (response.code == 0) {
          _this.RelvantArticle = response.data
        }
      })
    },
    initArticleModel(articleId) {
      if (articleId && articleId > 0) {
        ArticleApi.getDetailData(articleId).then(response => {
          let articleModel = response.data
          if (articleModel) {
            this.articleItem.status = articleModel.status
            this.articleItem.title = articleModel.title
            this.articleItem.author = articleModel.author
            this.articleItem.content = articleModel.content
            this.articleItem.abstractContent = articleModel.abstractContent
            this.articleItem.coverImageList = articleModel.coverImageList
            this.bannerArray = articleModel.coverImageList
            this.articleItem.publishTime = formatDate(articleModel.publishTime)
            this.articleItem.id = articleModel.id
            this.articleItem.showStyle = articleModel.showStyle
            this.articleItem.categoryId = articleModel.categoryId
            this.articleItem.categoryName = articleModel.category.categoryName
            this.articleItem.openComment = articleModel.openComment
            this.articleItem.articleTags = articleModel.articleTags
          }
        })
      }
    },
    backToTop() {
      document.body.scrollTop = 0
      document.documentElement.scrollTop = 0
    }
  }
}
</script>
<style scoped>
.markdown-body  /deep/a{
  color: #404040 !important;
  font-size: 14px !important;;
}
.v-note-navigation-wrapper a{
  text-decoration: none !important;
  cursor: pointer;
}
/*md导航目录*/
.detailcontainer /deep/ .v-note-wrapper .v-note-op{
  z-index: 0 !important;
  height: 0px !important;
  min-height: 0px !important;
  padding: 0px !important;
}
</style>
