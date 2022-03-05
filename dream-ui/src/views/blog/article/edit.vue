<template>
  <div class="createPost-container app-container">
    <el-form
      class="form-container"
      :model="submitForm"
      :rules="rules"
      ref="submitForm"
    >
      <div class="createPost-main-container">
        <div :class="[saveoptionshow ? 'w-show' : '', 's-choose-wrap']">
          <a
            href="javascript:void(0)"
            :class="[saveoptionshow ? 'c-l-w' : '', 's-choose-btn']"
            class=""
            @click="btnsaveoption"
          >
            <i class="el-icon-menu s-c-i"></i>
            <span>发布选项</span>
          </a>
          <el-scrollbar class="s-choose-box">
            <el-card class="box-card" style="margin-left: 20px">
              <div class="block">
                <el-form-item label="表情"><span>可以复制粘贴到文本框</span>
                  <div><span>🌹🍀💖🍎🍁🍂🍃🌷💎🏀⚡👄🔥🌲🌳🌴🌵🌾🌿☘️</span></div>
                  <div><span>😀😁😂😃😄😅😆😉😊😋😎😍😘😗😙😏😣</span></div>
                </el-form-item>
                <el-form-item
                  style=""
                  prop="title"
                  label-width="45px"
                  label="标题:"
                >
                  <el-input
                    type="textarea"
                    class="article-textarea"
                    :rows="1"
                    placeholder="请输入 标题"
                    v-model="submitForm.title"
                    required
                    :maxlength="100"
                    clearable
                  ></el-input>
                </el-form-item>
                <el-form-item style="" label-width="45px" label="摘要:">
                  <el-input
                    type="textarea"
                    class="article-textarea"
                    :rows="2"
                    placeholder="请输入内容"
                    v-model="submitForm.abstractContent"
                  ></el-input>
                </el-form-item>
                <el-form-item label="专题分类">
                  <el-cascader
                    v-model="cateCodeformat"
                    :options="categoryOptions"
                    :props="{ expandTrigger: 'hover', value: 'code' }"
                    @change="cateCheckChange"
                  ></el-cascader>
                </el-form-item>
                <el-form-item prop="title" label-width="90px" label="能否评论:">
                  <el-radio-group v-model="submitForm.openComment">
                    <el-radio :label="0" style="width: 60px">关闭</el-radio>
                    <el-radio :label="1" style="width: 60px">打开</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item prop="title" label-width="90px" label="是否推荐:">
                  <el-radio-group v-model="submitForm.isRecommend">
                    <el-radio :label="0" style="width: 60px">最新发布</el-radio>
                    <el-radio :label="1" style="width: 60px">特别推荐</el-radio>
                  </el-radio-group>
                </el-form-item>
              </div>
            </el-card>
            <el-card class="box-card" style="margin-left: 20px">
              <div slot="header" class="clearfix">
                <i class="el-icon-edit"></i><span>选择标签</span>
              </div>
              <el-autocomplete
                class="inline-input"
                v-model="defaultTag"
                :fetch-suggestions="querySearch"
                clearable
                placeholder="请输入内容"
                @select="selectTag"
              ></el-autocomplete>
              <el-button
                v-loading="loading"
                style="margin-left: 10px"
                type="success"
                @click="addTag"
                >新建
              </el-button>
              <div style="margin-top: 10px">
                <el-tag
                  v-for="tag in selectedTags"
                  :key="tag.name"
                  style="margin: 5px"
                  closable
                  @close="removeTag(tag)"
                  :type="tag.type"
                >
                  {{ tag.name }}
                </el-tag>
              </div>
            </el-card>

            <el-card class="box-card" style="margin-left: 20px">
              <div slot="header" class="clearfix">
                <i class="el-icon-upload"></i><span>添加封面</span>
              </div>
              <div style="">
                <el-carousel :interval="3000" type="card" height="88px" trigger="click">
                  <el-carousel-item v-for="item in demoImageList" :key="item" >
                    <img title="点击使用" @click="useItem(item)" :src="item" class="el-demo-list__img">
                  </el-carousel-item>
                </el-carousel>
              </div>
              <div style="margin: 20px 0">
                <el-radio-group v-model="submitForm.showStyle">
                  <el-radio :label="0">无图</el-radio>
                  <el-radio :label="1">单图</el-radio>
                  <el-radio :label="2">多图</el-radio>
                  <el-radio :label="3">大图</el-radio>
                </el-radio-group>
              </div>
              <el-tabs v-model="coverImageActive" type="card">
                <el-tab-pane label="本地上传" name="first">
                  <el-upload
                    title="选择图片"
                    :headers="headers"
                    :action="imgApiUrl"
                    multiple
                    :show-file-list="showfileList"
                    :on-success="uploadCoverImage"
                    :before-upload="validCoverImageUpload"
                    :limit="limit"
                    list-type="picture-card"
                  >
                    <i class="el-icon-plus"></i>
                  </el-upload>
                </el-tab-pane>
                <el-tab-pane label="网络地址" name="second">
                  <div style="margin: 20px 0">
                    <el-input v-model="netimageurl" style="width: 60%"></el-input>
                    <el-button
                      v-loading="loading"
                      style="margin-left: 10px"
                      type="success"
                      @click="addnetimage"
                      >添加网络图片
                    </el-button>
                  </div>
                </el-tab-pane>
              </el-tabs>
              <ul class="el-upload-list el-upload-list--picture-card">
                <li
                  v-for="item in submitForm.coverImageList"
                  class="el-upload-list__item is-success"
                >
                  <img
                    :src="item"
                    alt=""
                    class="el-upload-list__item-thumbnail"
                  />
                  <label class="el-upload-list__item-status-label">
                    <i class="el-icon-upload-success el-icon-check"></i>
                  </label>
                  <i class="el-icon-close" @click="handleRemove"></i>
                  <span class="el-upload-list__item-actions">
                    <span class="el-upload-list__item-preview">
                      <i
                        class="el-icon-zoom-in"
                        @click="handlePictureCardPreview(item)"
                      ></i>
                    </span>
                    <span class="el-upload-list__item-delete">
                      <i
                        class="el-icon-delete"
                        @click="handleRemove(item)"
                      ></i>
                    </span>
                  </span>
                </li>
              </ul>

            </el-card>
            <div class="toolbars" v-show="saveoptionshow">
              <ul>
                <li>
                  <a
                    href="javascript:void(0)"
                    class="blue"
                    @click="submitArticle(0)"
                  >
                    <i class="el-icon-s-flag s-c-i-b"></i>
                    <span>暂存</span>
                  </a>
                </li>
                <li>
                  <a
                    href="javascript:void(0)"
                    class="green"
                    @click="submitArticle(1)"
                  >
                    <i class="el-icon-finished s-c-i-b"></i>
                    <span>发布</span>
                  </a>
                </li>
                <li>
                  <a
                    href="javascript:void(0)"
                    class="gray"
                    @click="btnsaveoption()"
                  >
                    <i class="el-icon-edit s-c-i-b"></i>
                    <span>取消</span>
                  </a>
                </li>
              </ul>
            </div>
          </el-scrollbar>
        </div>
        <mavon-editor
          ref="md"
          v-model="submitForm.content"
          :toolbars="markDownToolbars"
          @imgAdd="myUpload"
          @imgDel="imgDel"
          class="md-style"
        />
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" />
        </el-dialog>
      </div>
    </el-form>
  </div>
</template>
<script>
import axios from 'axios'
import { getToken } from '@/utils/auth'
import { ArticleApi, CategoryApi, TagApi } from '@/api/blog'
export default {
  name: 'ArticleEdit',
  data() {
    return {
      articleId: 0,
      coverImageActive: 'first',
      netimageurl: '',
      imgApiUrl: process.env.VUE_APP_BASE_API + '/qiNiu/upload',
      submitForm: {
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
        articleType: 0,
        id: 0,
        isRecommend: 0,
        openComment: 0, //打开评论
        articleTags: ''
      },
      treeProps: {
        label: 'name',
        children: 'children',
        isLeaf: 'isLeaf'
      },
      markDownToolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        mark: true, // 标记
        superscript: true, // 上角标
        quote: true, // 引用
        ol: true, // 有序列表
        ink: true, // 链接
        imagelink: true, // 图片链接
        help: true, // 帮助
        code: true, // code
        subfield: true, // 是否需要分栏
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        /* 1.3.5 */
        undo: true, // 上一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true // 导航目录
      },
      saveoptionshow: false,
      cateCodeformat: [],
      dialogVisible: false,
      dialogImageUrl: '',
      categoryOptions: [],
      defaultTag: '',
      tagList: [],
      selectedTags: [],
      parentId: 0,
      tinymceId: 'tinymceId',
      active: false,
      position: '',
      currentTop: '',
      stickyTop: 0,
      zIndex: 20,
      width: undefined,
      height: undefined,
      child: null,
      stickyHeight: 0,
      fetchSuccess: true,
      loading: false,
      userLIstOptions: [],
      rules: {},
      hasChange: false,
      hasInit: false,
      limit: 3,
      showfileList: false,
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      demoImageList:[
        'http://file.miaoleyan.com/file/blog/OctWIYMuTTRo28Fl7w4EoWBn4YbxZQTS',
        'http://file.miaoleyan.com/file/blog/WnqV0XJmLJKLtLGkwYeNGM6J3TTtslqd',
        'http://file.miaoleyan.com/file/blog/zHeDWKEUV2vt50cwlSKiYwvlBR4KrBda',
        'http://file.miaoleyan.com/file/blog/UbQAfXZBobKC9c3rnKV8bO5lQDkzetTE',
        'http://file.miaoleyan.com/file/blog/sPuP7WoK3lnxQx3t2iDQjmP2HYiLXFlM',
        'http://file.miaoleyan.com/nndt/7QPDz055QMd7c8JoQF1qoXOLdghYN2an'
      ]
    }
  },
  mounted() {
    this.height = this.$el.getBoundingClientRect().height
    this.loadAllTagList()
  },
  computed: {
    contentShortLength() {
      return this.submitForm.abstractContent.length
    }
  },
  created() {
    this.articleId = this.$route.query.id
    this.initArticleModel(this.articleId)
    this.loadCategoryOptions()
  },
  methods: {
    formatCategoryCode(code) {
      var categoryArr = []
      if (code) {
        var level = code.length / 4
        for (var i = 0; i < level; i++) {
          var c = code.substring(0, 4 * (i + 1))
          categoryArr.push(c)
        }
        return categoryArr
      }
      return categoryArr
    },
    initArticleTags(value) {
      let _this = this
      if (!value) return
      _this.selectedTags = []
      var t = []
      for (var i = 0; i < value.length; i++) {
        t.push(value[i].aliasName)
        _this.selectedTags.push({ name: value[i].aliasName, type: '' })
      }
      _this.submitForm.articleTags = t.join(',')
      // TagApi.getTagByIds(value).then(response => {
      //   if (response.code == 0) {
      //     _this.selectedTags = []
      //     var t = []
      //     for (var i = 0; i < response.data.length; i++) {
      //       t.push(response.data[i].alia)
      //       _this.selectedTags.push({ name: response.data[i].alia, type: '' })
      //     }
      //     _this.submitForm.articleTags = t.join(',')
      //   }
      // })
    },
    useItem(m){
      this.netimageurl=m;
      this.addnetimage()
    },
    addnetimage() {
      if (this.netimageurl != '') {
        this.submitForm.coverImageList.push(this.netimageurl)
        this.netimageurl = ''
      }
    },
    btnsaveoption() {
      this.saveoptionshow = !this.saveoptionshow
    },
    opencommentChange(data) {
      this.submitForm.openComment = data
    },
    articleTypeChange(data) {
      this.submitForm.articleType = data
    },
    isRecommendChange(data) {
      this.submitForm.isRecommend = data
    },
    loadCategoryOptions() {
      CategoryApi.getCategoryTreeData(0).then(response => {
        this.categoryOptions = response.data
      })
    },
    initArticleModel(articleId) {
      if (articleId && articleId > 0) {
        ArticleApi.getDetailData(articleId).then(response => {
          let articleModel = response.data
          if (articleModel) {
            this.submitForm.status = articleModel.status
            this.submitForm.title = articleModel.title
            this.submitForm.author = articleModel.author
            this.submitForm.content = articleModel.content
            this.submitForm.abstractContent = articleModel.abstractContent
            if (
              articleModel.coverImageList &&
              articleModel.coverImageList.length > 0
            ) {
              for (var i = 0; i < articleModel.coverImageList.length; i++) {
                this.submitForm.coverImageList.push(
                  articleModel.coverImageList[i]
                )
              }
            }
            this.submitForm.publishTime = articleModel.publishTime
            this.submitForm.id = articleModel.id
            this.submitForm.showStyle = articleModel.showStyle
            this.submitForm.articleType = articleModel.articleType
            this.submitForm.categoryId = articleModel.categoryId
            this.submitForm.categoryCode = articleModel.categoryCode
            this.cateCodeformat = this.formatCategoryCode(
              articleModel.categoryCode
            )
            this.submitForm.categoryName = articleModel.category
              ? articleModel.category.categoryName
              : ''
            this.submitForm.openComment = articleModel.openComment
            this.submitForm.articleTags = articleModel.articleTags
            this.submitForm.isRecommend = articleModel.isRecommend
            this.initArticleTags(articleModel.articleTags)
          }
        })
      }
    },
    submitArticle(status) {
      var _this = this
      _this.submitForm.status = status
      _this.submitForm.editorType = 0
      ArticleApi.saveArticle(_this.submitForm).then(response => {
        if (response.code == 0) {
          //保存后关闭页面
          _this.$store.dispatch('tagsView/delView', _this.$route)
          //关闭页面后跳转别的页面
          _this.returnArticle()
        }
      })
    },
    convertBase64UrlToBlob() {},
    handleScroll() {
      this.width = this.$el.getBoundingClientRect().width
      var offsetTop = this.$el.getBoundingClientRect().top
      if (offsetTop <= this.stickyTop) {
        this.sticky()
        return
      }
    },
    returnArticle() {
      this.$router.push({path: '/article',query: {}})
    },
    loadNode() {},
    cateCheckChange(data) {
      if (data != null && data.length > 0) {
        this.submitForm.categoryCode = data[data.length - 1]
      } else {
        this.submitForm.categoryCode = ''
      }
    },
    querySearch(queryString, cb) {
      //debugger;
      var tagList = this.tagList
      var results = queryString
        ? tagList.filter(this.createFilter(queryString))
        : tagList
      // 调用 callback 返回建议列表的数据
      cb(results)
    },
    createFilter(queryString) {
      return tag => {
        return tag.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
      }
    },
    loadAllTagList() {
      let _this = this
      TagApi.getTagList().then(response => {
        if (response.code == 0) {
          _this.tagList = response.data
        }
      })
    },
    selectTag(item) {
      if (
        JSON.stringify(this.selectedTags)
          .toLowerCase()
          .indexOf(item.value.trim().toLowerCase()) == -1
      ) {
        this.selectedTags.push({ name: item.value, type: '' })
        if (this.submitForm.articleTags == '') {
          this.submitForm.articleTags = item.value
        } else {
          this.submitForm.articleTags += ',' + item.value
        }
      }
      this.defaultTag = ''
    },
    removeTag(tag) {
      this.selectedTags.splice(this.selectedTags.indexOf(tag), 1)
      var tags = []
      for (var i = 0; i < this.selectedTags.length; i++) {
        tags.push(this.selectedTags[i].name)
      }
      this.submitForm.articleTags = tags.join(',')
    },
    addTag() {
      var value = this.defaultTag.trim()
      if (
        JSON.stringify(this.selectedTags)
          .toLowerCase()
          .indexOf(value.toLowerCase()) == -1
      ) {
        this.selectedTags.push({ name: value, type: '' })
        if (this.submitForm.articleTags == '') {
          this.submitForm.articleTags = value
        } else {
          this.submitForm.articleTags += ',' + value
        }
      }
      this.defaultTag = ''
    },
    imgDel() {},
    myUpload(pos, $file) {
      var formData = new FormData()
      // 文件对象
      formData.append('file', $file)
      axios({
        url: this.imgApiUrl,
        method: 'post',
        data: formData,
        headers: {
          Authorization: 'Bearer ' + getToken(),
          'Content-Type': 'multipart/form-data'
        }
      }).then(res => {
        // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
        this.$refs.md.$img2Url(pos, res.data.result[0].url)
      })
    },
    uploadCoverImage(res, file) {
      if (res.success == 1) {
        for (var i = 0; i < res.result.length; i++) {
          this.submitForm.coverImageList.push(res.result[i].url)
        }
      } else {
        this.$message.error(res.msg)
      }
    },
    validCoverImageUpload(file) {
      if (
        this.submitForm.coverImageList &&
        this.submitForm.coverImageList.length >= 3
      ) {
        this.$message.error('封面图片最多三张!')
        return false
      }
      var isLt2M = file.size / 1024 / 1024 < 2
      if (
        file.type !== 'image/jpeg' &&
        file.type !== 'image/jpg' &&
        file.type !== 'image/gif' &&
        file.type !== 'image/png'
      ) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isLt2M
    },
    handleRemove(url) {
      this.submitForm.coverImageList.splice(
        this.submitForm.coverImageList.indexOf(url),
        1
      )
    },
    handlePictureCardPreview(url) {
      //debugger;
      this.dialogImageUrl = url
      this.dialogVisible = true
    }
  }
}
</script>
<style type="text/css">
.title-prompt {
  position: absolute;
  right: 0px;
  font-size: 12px;
  top: 10px;
}

.createPost-container {
  position: relative;
}

.createPost-main-container {
  padding: 10px;
}
.el-card {
  margin-bottom: 10px;
  margin-top: 5px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 270px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 270px;
  height: 178px;
  display: block;
}

.el-upload--picture-card {
  background-color: #fbfdff;
  border: 1px dashed #c0ccda;
  border-radius: 6px;
  box-sizing: border-box;
  margin: 0 8px 8px 0;
  width: 270px;
  height: 148px;
  line-height: 146px;
  vertical-align: top;
}

.el-upload-list--picture-card .el-upload-list__item {
  overflow: hidden;
  background-color: #fff;
  border: 1px solid #c0ccda;
  border-radius: 6px;
  box-sizing: border-box;
  width: 270px;
  height: 148px;
  margin: 0 8px 8px 0;
  display: inline-block;
}

.el-radio {
  color: #606266;
  font-weight: 500;
  line-height: 1;
  cursor: pointer;
  white-space: nowrap;
  outline: 0;
  width: 15%;
}

div#mceu_55 {
  left: 0 !important;
  width: 99% !important;
}

.mind-top {
  position: fixed;
  /*固定定位*/
  width: 100%;
  line-height: 70px;
  height: 70px;
  padding: 0 22px;
  background: '#fff';
  border-bottom: '1px solid #676a6c';
}

.s-choose-wrap {
  position: absolute;
  right: 16px;
  top: -5px;
  z-index: 2000;
  bottom: 10;
  width: 0px;
  transition: 0.3s;
}

.s-choose-btn {
  position: absolute;
  top: 6px;
  left: -16px;
  width: 32px;
  height: 130px;
  text-align: center;
  background: #ebf7ff;
  border: 2px solid #fff;
  border-radius: 6px 0 0 6px;
  box-shadow: 0 0 7px rgba(94, 39, 60, 0.35);
}

.s-choose-btn span {
  width: 1em;
  display: inline-block;
  line-height: 1.3;
}

.s-c-i {
  display: block;
  width: 24px;
  height: 24px;
  line-height: 25px;
  margin: 10px auto 5px;
  text-align: center;
  color: #fff;
  background: #156498;
  border-radius: 50%;
}

.s-c-i-b {
  display: block;
  width: 24px;
  height: 24px;
  line-height: 25px;
  margin: 10px auto 5px;
  text-align: center;
  color: #fff;
  border-radius: 50%;
}

.s-choose-box {
  box-sizing: border-box;
  text-align: left;
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 0 0 7px 2px rgba(145, 172, 189, 0.35);
  height: calc(100vh - 10px);
}

.w-show {
  width: 560px;
  right: 0;
}

.c-l-w {
  left: -30px !important;
}

.toolbars {
  position: fixed;
  top: 0;
  right: 0;
  height: 100%;
  width: 6px;
  background-color: #7a6e6e;
}

.toolbars ul {
  position: absolute;
  top: 50%;
  right: 0;
  transform: translate(0, -40%);
  z-index: 3;
}

.toolbars ul li {
  width: 32px;
  height: 120px;
  margin-bottom: 2px;
  /* 	background-color: #7A6E6E; */
  position: relative;
  list-style: none;
}

.toolbars ul li span {
  width: 1em;
  display: inline-block;
  line-height: 1.3;
}

.toolbars ul li a {
  position: absolute;
  text-align: center;
  width: 32px;
  height: 120px;
  text-align: center;
  color: #fff;
  border: 1px solid #fff;
  border-radius: 6px 0 0 6px;
  box-shadow: 0 0 7px rgba(94, 39, 60, 1);
}
.md-style {
  height: calc(100vh - 10px);
}
.blue {
  background: #409eff;
}

.green {
  background: #67c23a;
}
.gray {
  background: #696969;
}
.el-demo-list__img {
  overflow: hidden !important;
  background-color: #fff;
  border: 1px solid #c0ccda;
  border-radius: 6px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  width: 135px !important;
  height: 74px;
  margin: 0 8px 8px 0;
  display: inline-block;
  cursor: pointer;
}
</style>
