<template>
  <header>
    <div :class="[istran ? 'menu_trans' : '', 'menu']">
      <nav class="nav" id="topnav">
        <h1 class="logo">
          <a href="/"
            ><img
              style="width: 60px; margin-left: 50px"
              src="@/assets/blog/image/logo_o.png"
          /></a>
        </h1>
        <ul v-if="navList.length > 0">
          <template v-for="nav in navList">
            <li @mouseover="selectStyle(nav)" @mouseout="outStyle(nav)">
              <a v-if="nav.linkUrl && (nav.linkUrl.indexOf('http')>-1||nav.linkUrl=='javascript:void(0);')" :href="nav.linkUrl" target="target">
                <slot>{{ nav.title }}</slot>
                <ul
                  class="sub-nav"
                  v-if="nav.childrens.length > 0"
                  v-show="nav.active"
                >
                  <li v-for="children in nav.childrens">
                    <a v-if="children.linkUrl &&  (children.linkUrl.indexOf('http')>-1||children.linkUrl=='javascript:void(0);')" :href="children.linkUrl" target="target">
                      <slot>{{ children.title }}</slot>
                    </a>
                    <router-link :to="children.linkUrl" v-else>
                      {{ children.title }}
                    </router-link>
                  </li>
                </ul>
              </a>
              <router-link v-else
                :to="nav.linkUrl"
                :class="{ a_active: currentUrl == nav.linkUrl }"
              >
                {{ nav.title }}
                <ul
                  class="sub-nav"
                  v-if="nav.childrens.length > 0"
                  v-show="nav.active"
                >
                  <li v-for="children in nav.childrens">
                    <a v-if="children.linkUrl && (children.linkUrl.indexOf('http')>-1||children.linkUrl=='javascript:void(0);')" :href="children.linkUrl" target="target">
                      <slot>{{ children.title }}</slot>
                    </a>
                    <router-link :to="children.linkUrl" v-else>
                      {{ children.title }}
                    </router-link>
                  </li>
                </ul>
              </router-link>
            </li>
          </template>
          <li id="controlSearch" class="nav_menu_li">
            <a
              id="switchicon"
              @click="switchSearch"
              class="navmla2 nav_menu_li_a2"
            >
              <i
                v-if="issearch"
                class="fa fa-times"
                style="font-size: medium"
                aria-hidden="true"
              ></i>
              <i
                v-else
                class="fa fa-search"
                style="font-size: medium"
                aria-hidden="true"
              ></i>
            </a>
          </li>
        </ul>
        <!--search begin-->
        <div
          v-show="issearch"
          id="topsearch"
          class="topsearch"
          style="opacity: 1"
        >
          <div class="intopsearch">
            <div class="mainsearch">
              <div id="search">
                <input
                  type="text"
                  id="s"
                  v-model="searchkeyword"
                  @keyup.enter="searchResult"
                  name="s"
                  class="text"
                  placeholder="你要找些什么..."
                />
                <button @click="searchResult" class="submit">Search</button>
              </div>
            </div>
          </div>
        </div>
        <!--search end-->
      </nav>
    </div>
    <div id="mnav">
      <h2 :class="{ open: isOpen }">
        <a href="/" class="mlogo"
          ><img
            style="padding: 15px; margin-left: -10px"
            src="@/assets/blog/image/logo_o.png" /></a
        ><span class="navicon" @click="changeIcon"></span>
      </h2>
      <dl class="list_dl" v-if="navList.length > 0" v-show="isOpen">
        <template v-for="nav in navList">
          <dt class="list_dt">
            <a v-if="nav.linkUrl && (nav.linkUrl.indexOf('http')>-1||nav.linkUrl=='javascript:void(0);')" :href="nav.linkUrl" target="target">
              <slot>{{ nav.title }}</slot>
            </a>
            <router-link v-else :to="nav.linkUrl">{{ nav.title }}</router-link>
          </dt>
          <dd
            class="list_dd"
            v-if="nav.childrens.length > 0"
            v-show="nav.active"
          >
            <ul>
              <li v-for="c in nav.childrens">
                <a v-if="c.linkUrl &&  (c.linkUrl.indexOf('http')>-1||c.linkUrl=='javascript:void(0);')" :href="c.linkUrl" target="target">
                  <slot>{{ c.title }}</slot>
                </a>
                <router-link :to="c.linkUrl">
                  {{ c.title }}
                </router-link>
              </li>
            </ul>
          </dd>
        </template>
      </dl>
    </div>
  </header>
</template>
<script>
export default {
  name: 'BlogHeader',
  currentRouter: {
    type: String,
    default: '/'
  },
  data() {
    return {
      currentUrl: '', //当前路由
      navList: [
        {
          title: '首页',
          icon: 'glyphicon glyphicon-cog',
          linkUrl: '/',
          active: false,
          childrens: []
        },
        {
          title: '开源',
          icon: 'glyphicon glyphicon-cog',
          linkUrl: 'javascript:void(0);',
          active: false,
          childrens: [
            {
              title: 'KGBuilder',
              icon: '',
              linkUrl: 'http://kg.miaoleyan.com',
              active: false,
              childrens: []
            },
            {
              title: '博客',
              icon: '',
              linkUrl: 'http://www.miaoleyan.com',
              active: false,
              childrens: []
            },
            {
              title: '族谱',
              icon: '',
              linkUrl: 'javascript:void(0);',
              active: false,
              childrens: []
            }
          ]
        },
        {
          title: '生活',
          icon: 'glyphicon glyphicon-cog',
          linkUrl: 'javascript:void(0);',
          active: false,
          childrens: [
            {
              title: '自律',
              icon: '',
              linkUrl: 'javascript:void(0);',
              active: false,
              childrens: []
            },
            {
              title: '美食',
              icon: '',
              linkUrl: 'javascript:void(0);',
              active: false,
              childrens: []
            },
            {
              title: '期待',
              icon: '',
              linkUrl: 'javascript:void(0);',
              active: false,
              childrens: []
            }
          ]
        },
        {
          title: '前端组件',
          icon: 'glyphicon glyphicon-cog',
          linkUrl: 'javascript:void(0);',
          active: false,
          childrens: [
            {
              title: '穿梭框',
              icon: '',
              linkUrl: '/transfer',
              active: false,
              childrens: []
            },
            {
              title: 'scrollreveal',
              icon: '',
              linkUrl: '/scrollreveal',
              active: false,
              childrens: []
            },
            {
              title: '3D相册',
              icon: '',
              linkUrl: '/magicphoto',
              active: false,
              childrens: []
            },
            {
              title: '时间轴',
              icon: '',
              linkUrl: '/timeline',
              active: false,
              childrens: []
            }
          ]
        },
        {
          title: '分享',
          icon: 'glyphicon glyphicon-th-list',
          linkUrl: 'javascript:void(0);',
          active: false,
          childrens: [
            {
              title: '杨青博客',
              icon: '',
              linkUrl: 'http://www.yangqq.com/',
              active: false,
              childrens: []
            },
            {
              title: '程序猿DD',
              icon: '',
              linkUrl: 'http://blog.didispace.com/',
              active: false,
              childrens: []
            },
            {
              title: 'editor.md',
              icon: '',
              linkUrl: 'https://pandao.github.io/editor.md/',
              active: false,
              childrens: []
            },
            {
              title: 'hAdmin',
              icon: '',
              linkUrl: 'http://demo.mycodes.net/houtai/hAdmin',
              active: false,
              childrens: []
            }
          ]
        },
        {
          title: '关于我',
          icon: 'glyphicon glyphicon-cog',
          linkUrl: '/about',
          active: false,
          childrens: []
        }
      ],
      isOpen: false,
      search_active: false,
      searchkeyword: '',
      istran: true,
      issearch: false
    }
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll, true)
  },
  methods: {
    switchSearch() {
      this.issearch = !this.issearch
      if (!this.issearch) {
        if (this.searchkeyword) {
          this.searchResult()
        }
      }
    },
    handleScroll() {
      var scrollTop =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop
      if (scrollTop > 56) {
        this.istran = false
      } else {
        this.istran = true
      }
    },
    selectStyle(nav) {
      var _this = this
      this.$nextTick(function() {
        _this.navList.forEach(function(item) {
          item.active = false
        })
        nav.active = true
      })
    },
    outStyle(nav) {
      nav.active = false
    },
    changeIcon() {
      this.isOpen = !this.isOpen
    },
    searchActive() {
      this.search_active = !this.search_active
    },
    clickNav(nav) {
      nav.active = !nav.active
    },
    searchResult() {
      //父容器app
      this.queryForm.pageIndex = 1
      this.queryForm.title = this.searchkeyword.trim()
      //this.initData()
      this.articleList = []
    }
  }
}
</script>
<style >
.menu {
  height: 56px;
  line-height: 56px;
  width: 100%;
  color: #fff;
  background-color: #000;
}
.menu_trans {
  background: rgba(0, 0, 0, 0.3);
}
.nav {
  height: 56px;
  width: 100%;
  margin: 0 auto;
}
.nav li {
  float: left;
  position: relative;
}
.nav li a {
  color: #bdbdbd;
  padding: 0 20px;
  display: inline-block;
}
.nav li a:hover {
  color: #fff;
}
.nav li .sub-nav {
  position: absolute;
  top: 58px;
  width: 140px;
  background: #fff;
  left: -20px; /* display: none;  */
}
.nav li .sub-nav li {
  clear: left;
  height: 35px;
  line-height: 35px;
  position: relative;
  width: 200px;
  padding: 5px 20px;
}
.nav li .sub-nav li a {
  font-size: 15px;
  font-weight: 400;
  color: #404040;
  line-height: 28px;
}
.nav li .sub-nav li a:hover {
  color: #000;
  border-left: 2px solid #000;
}
.nav_menu_li {
  position: static;
  display: table-cell;
  cursor: pointer;
  float: right !important;
  margin-right: 20%;
}
li#controlSearch {
  width: 43px;
}
.nav_menu_li_a2 {
  font-size: 12px;
  color: #fff;
  padding: 0px 15px;
  line-height: 56px;
  transition: all 0.5s ease-out;
  -webkit-transition: all 0.5s ease-in-out;
  -moz-transition: all 0.5s ease-in-out;
  -ms-transition: all 0.5s ease-in-out;
  -o-transition: all 0.5s ease-in-out;
}
@media screen and (min-width: 768px) {
  #topsearch {
    width: 100vw;
    position: fixed;
    z-index: 99;
  }
  .topsearch {
    transition: all 0.5s ease-out;
    -webkit-transition: all 0.5s ease-in-out;
    -moz-transition: all 0.5s ease-in-out;
    -ms-transition: all 0.5s ease-in-out;
    -o-transition: all 0.5s ease-in-out;
    top: 60px;
  }
}
@media screen and (min-width: 1200px) {
  .intopsearch {
    max-width: 1160px;
  }
}

.intopsearch {
  margin: 0 auto;
  padding: 0px 10px;
}
.mainsearch {
  border-radius: 3px;
}
div#search {
  display: flex;
}
input#s {
  flex-grow: 1;
  border: none;
  padding: 10px;
  outline: none;
  opacity: 0.8;
  text-align: center;
  font-weight: bold;
  border-radius: 5px 0 0 5px;
}
button.submit {
  border: none;
  outline: none;
  padding: 10px;
  cursor: pointer;
  background: rgba(36, 137, 255, 0.8);
  color: #fff;
  border-radius: 0 5px 5px 0;
  transition: all 0.2s ease-in-out;
  -webkit-transition: all 0.2s ease-in-out;
  -moz-transition: all 0.2s ease-in-out;
  -ms-transition: all 0.2s ease-in-out;
  -o-transition: all 0.2s ease-in-out;
}
#topnav_current {
  color: #00a7eb;
}
.a_active {
  color: #00a7eb !important;
}
/*search*/
.search_bar {
  position: relative;
  width: 0%;
  min-width: 56px;
  height: 56px;
  margin-right: 20%;
  float: right;
  overflow: hidden;
  -webkit-transition: width 0.3s;
  -moz-transition: width 0.3s;
  transition: width 0.3s;
  -webkit-backface-visibility: hidden;
}
.input {
  position: absolute;
  top: 15px;
  right: 0;
  border: none;
  outline: none;
  width: 98%;
  height: 30px;
  line-height: 30px;
  z-index: 10;
  font-size: 16px;
  color: #333;
  padding-left: 5px;
}
.search_ico,
.search_btn {
  width: 60px;
  height: 60px;
  display: block;
  position: absolute;
  right: 0;
  top: 0;
  padding: 0;
  margin: 0;
  line-height: 60px;
  cursor: pointer;
}
.search_ico {
  background: #000 url(/images/blog/searchbg.png) no-repeat center;
  z-index: 90;
}
.search_open {
  width: 200px;
}
#show {
  position: absolute;
  padding: 20px;
}
@media only screen and (max-width: 360px) {
  #mnav {
    display: block !important;
    z-index: 99999;
  }
  .github {
    display: none;
  }
}
@media only screen and (max-width: 375px) and (min-width: 360px) {
  #mnav {
    display: block !important;
    z-index: 99999;
  }
  .github {
    display: none;
  }
}
@media only screen and (max-width: 812px) and (min-width: 375px) {
  #mnav {
    display: block !important;
    z-index: 99999;
  }
  .github {
    display: none;
  }
}
/*phone  nav */
#mnav {
  display: none;
  width: 100%;
  position: fixed;
  top: 0;
  right: 0;
}
.mlogo {
  color: #00a7eb;
  line-height: 50px;
  font-size: 22px;
  float: left;
  padding-left: 20px;
}
#mnav h2 {
  text-align: right;
  color: #fff;
  font-size: 18px;
  height: 56px;
  line-height: 40px;
  width: 100%;
  background: rgba(0, 0, 0, 0.3);
}
#mnav h2.open {
  text-align: right;
  width: 100%;
}
#mnav dl {
  /* display: none; */
  background: rgba(0, 0, 0, 0.3);
  width: 100%;
  padding-bottom: 40px;
}
#mnav .list_dt {
  line-height: 40px;
  vertical-align: top;
  font-size: 16px;
  display: block; /* overflow: hidden; */
  text-align: center;
  border-bottom: 1px solid #464646;
  margin: 0 20%;
}
#mnav h2 .navicon {
  margin-right: 15px;
}
.navicon {
  display: inline-block;
  position: relative;
  width: 30px;
  height: 5px;
  background-color: #ffffff;
}
.navicon:before,
.navicon:after {
  content: '';
  display: block;
  width: 30px;
  height: 5px;
  position: absolute;
  background: #ffffff;
  -webkit-transition-property: margin, -webkit-transform;
  transition-property: margin, -webkit-transform;
  transition-property: margin, transform;
  transition-property: margin, transform, -webkit-transform;
  -webkit-transition-duration: 300ms;
  transition-duration: 300ms;
}
.navicon:before {
  margin-top: -10px;
}
.navicon:after {
  margin-top: 10px;
}
/* open */
#mnav h2.open .navicon {
  background: none;
} /* hidden */
#mnav h2.open .navicon:before {
  margin-top: 0;
  -webkit-transform: rotate(45deg);
  transform: rotate(45deg);
}
#mnav h2.open .navicon:after {
  margin-top: 0;
  -webkit-transform: rotate(-45deg);
  transform: rotate(-45deg);
}
#mnav h2.open .navicon:before,
#mnav h2.open .navicon:after {
  content: '';
  display: block;
  width: 30px;
  height: 5px;
  position: absolute;
  background: #ffffff;
  -webkit-transition-property: margin, -webkit-transform;
  transition-property: margin, -webkit-transform;
  transition-property: margin, transform;
  transition-property: margin, transform, -webkit-transform;
  -webkit-transition-duration: 300ms;
  transition-duration: 300ms;
}
#mnav .list_dt a {
  color: #fff;
}
.mlogo img {
  width: 100px;
}
</style>
