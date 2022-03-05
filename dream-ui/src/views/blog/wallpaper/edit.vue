<template>
<div class="createPost-main-container">
  <div style="float: right;display: block;z-index: 99;position: relative">
    <el-button
    v-loading="loading"
    type="success"
    @click="submitWallpaper"
  >保存</el-button
  >
    <el-button
      v-loading="loading"
      type="default"
      @click="returnWallpaper"
    >返回</el-button
    ></div>
  <el-row>
    <el-col :span="24">
      <el-card  class="box-card">
        <div slot="header" class="clearfix">
          <i class="el-icon-upload"></i><span>添加图片</span>
        </div>
        <el-form ref="form" :model="submitForm" label-width="80px">
          <el-form-item label="后缀">
            <span>🌹🍀💖🍎🍁🍂🍃🌷💎🏀⚡👄🔥🌲🌳🌴🌵🌾🌿☘️😀😁😂😃😄😅😆😉😊😋😎😍😘😗😙😏😣可以复制粘贴到文本框</span>
          </el-form-item>
          <el-form-item label="标题">
            <el-input v-model="submitForm.title"></el-input>
          </el-form-item>
          <el-form-item label="上传图片">
            <div
              v-if="submitForm.imgSrc"
              class="el-upload-list el-upload-list--picture-card"
            >
              <div class="el-upload-list__item is-success">
                <img :src="submitForm.imgSrc" class="avatar" />
                <label class="el-upload-list__item-status-label">
                  <i class="el-icon-upload-success el-icon-check"></i>
                </label>
                <i class="el-icon-close" @click="handleRemove"></i>
                <span class="el-upload-list__item-actions">
                  <span class="el-upload-list__item-preview">
                    <i
                      class="el-icon-zoom-in"
                      @click="handlePictureCardPreview"
                    ></i>
                  </span>
                  <span class="el-upload-list__item-delete">
                    <i class="el-icon-delete" @click="handleRemove"></i>
                  </span>
                </span>
              </div>
            </div>
            <el-upload v-else
              class="avatar-uploader"
              :headers="headers"
              :action="imgApiUrl"
              :show-file-list="false"
              :on-success="uploadCoverImage"
              :before-upload="validCoverImageUpload"
            >
              <el-button
                v-if="submitForm.imgSrc"
                v-loading="loading"
                type="success"
              ><i class="el-icon-edit"></i>更换</el-button
              >
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="submitForm.imgSrc" />
        </el-dialog>
      </el-card>
    </el-col>
  </el-row>
</div>
</template>
<script>
import { getToken } from '@/utils/auth'
import { WallpaperApi } from '@/api/blog'
export default {
  name: 'wallpaperEdit',
  data() {
    return {
      submitForm: {
        title: '',
        id: 0,
        imgSrc: ''
      },
      imgApiUrl: process.env.VUE_APP_BASE_API + '/qiNiu/upload',
      dialogVisible: false,
      active: false,
      position: '',
      currentTop: '',
      width: undefined,
      height: undefined,
      stickyHeight: 0,
      fetchSuccess: true,
      loading: false,
      rules: {},
      hasChange: false,
      hasInit: false,
      stickyTop: 0,
      zIndex: 2,
      headers: {
        Authorization: 'Bearer ' + getToken()
      }
    }
  },
  mounted() {
    this.height = this.$el.getBoundingClientRect().height
    window.addEventListener('scroll', this.handleScroll)
  },
  created() {
    this.initWallpaperModel(this.$route.query.id)
  },
  watch: {
    value(val) {
      this.submitForm.content = val
      if (!this.hasChange && this.hasInit) {
        this.$nextTick(() => tinymce.get(this.tinymceId).setContent(val))
      }
    }
  },
  methods: {
    isShowChange(data) {
      this.submitForm.isShow = data
    },
    initWallpaperModel(wallpaperId) {
      WallpaperApi.getDetailData(wallpaperId).then(response => {
        if (response.code == 0) {
          var wallpaperModel = response.data
          this.submitForm.title = wallpaperModel.title
          this.submitForm.imgSrc = wallpaperModel.imgSrc
          this.submitForm.id = wallpaperModel.id
        }
      })
    },
    submitWallpaper() {
      var _this = this
      WallpaperApi.saveWallpaper(_this.submitForm).then(response => {
        if (response.code == 0) {
          //保存后关闭页面
          _this.returnWallpaper()
        }
      })
    },
    returnWallpaper() {
      this.$store.dispatch('tagsView/delView', this.$route)
      this.$router.push({path: '/wallpaper',query: {}})
    },
    sticky() {
      if (this.active) {
        return
      }
      this.position = 'fixed'
      this.active = true
      this.width = this.width + 'px'
    },
    reset() {
      if (!this.active) {
        return
      }
      this.position = ''
      this.width = 'auto'
      this.active = false
    },
    handleScroll() {
      this.width = this.$el.getBoundingClientRect().width
      const offsetTop = this.$el.getBoundingClientRect().top
      if (offsetTop <= this.stickyTop) {
        this.sticky()
        return
      }
      this.reset()
    },
    uploadCoverImage(res, file) {
      if (res.success == 1) {
        this.submitForm.imgSrc = res.result[0].url
      } else {
        this.$message.error(res.msg)
      }
    },
    validCoverImageUpload(file) {
      const issync = true
      const isLt2M = file.size / 1024 / 1024 < 2
      if (
        file.type !== 'image/jpeg' &&
        file.type !== 'image/jpg' &&
        file.type !== 'image/gif' &&
        file.type !== 'image/png'
      ) {
        issync = false
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return issync && isLt2M
    },
    handleRemove() {
      this.submitForm.imgSrc = ''
    },
    handlePictureCardPreview() {
      this.dialogVisible = true
    }
  }
}
</script>
<style scoped>
.title-prompt {
  position: absolute;
  right: 0px;
  font-size: 12px;
  top: 10px;
}

.createPost-container {
  position: relative;
  height: calc(100vh - 150px);
}

.createPost-main-container {
  padding: 40px 45px 20px 50px;
}

.postInfo-container {
  position: relative;
  margin-bottom: 10px;
}

.postInfo-container-item {
  float: left;
}

.editor-container {
  min-height: 500px;
  margin: 0 0 30px;
}

.editor-upload-btn-container {
  text-align: right;
  margin-right: 10px;
}

.editor-upload-btn {
  display: inline-block;
}

.word-counter {
  width: 40px;
  position: absolute;
  right: -10px;
  top: 0px;
}
.el-upload {
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
  width: 400px;
  height: 240px;
  line-height: 240px;
  text-align: center;
}
.avatar {
  width: 400px;
  height: 240px;
  display: block;
}
.el-upload--picture-card {
  background-color: #fbfdff;
  border: 1px dashed #c0ccda;
  border-radius: 6px;
  box-sizing: border-box;
  margin: 0px;
  width: 400px;
  height: 240px;
  line-height: 146px;
  vertical-align: top;
}
.el-upload-list--picture-card .el-upload-list__item {
  overflow: hidden;
  background-color: #fff;
  border: 1px solid #c0ccda;
  border-radius: 6px;
  box-sizing: border-box;
  width: 400px;
  height: 240px;
  margin: 0px;
  display: inline-block;
}
.el-card{
  width: 100%  !important;
  float: left !important;
  margin: 5px !important;
}
</style>
