<template>
  <div class="createPost-container">
    <el-form
      class="form-container"
      :model="submitForm"
      :rules="rules"
      ref="submitForm"
    >
      <div
        class="sub-navbar"
        :style="{
          top: stickyTop + 'px',
          zIndex: zIndex,
          position: position,
          width: width,
          height: 52 + 'px',
          background: '#BDBFBA',
        }"
      >
        <slot>
          <div>
            <template v-if="fetchSuccess">
              <el-button v-loading="loading" type="success" @click="submitMusic"
                >保存</el-button
              >
              <el-button v-loading="loading" type="default" @click="returnMusic"
                >返回</el-button
              >
            </template>
          </div>
        </slot>
      </div>
      <div class="createPost-main-container">
        <el-row>
          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <i class="el-icon-upload"></i><span>添加音乐</span>
              </div>

              <el-form ref="form" :model="submitForm" label-width="80px">
                <el-form-item label="选择音乐">
                  <el-input v-model="submitForm.linkUrl" style="width:60%"></el-input>
                  <el-upload
                    style="display: inline-block;margin-left: 6px;"
                    :headers="headers"
                    :action="fileApiUrl"
                    :show-file-list="false"
                    :on-success="uploadMusic"
                    :before-upload="validFileUpload"
                  >
                    <el-button
                      v-loading="loading"
                      type="success"
                    ><i class="el-icon-edit"></i>浏览</el-button
                    >
                  </el-upload>
                </el-form-item>
                <el-form-item label="标题" >
                  <el-input v-model="submitForm.title" style="width:60%"></el-input>
                </el-form-item>
                <el-form-item label="排序码">
                  <el-input v-model="submitForm.sortCode" style="width:60%"></el-input>
                </el-form-item>
                <el-form-item label="选择封面">
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
                  <el-upload
                    class="avatar-uploader"
                    :headers="headers"
                    :action="fileApiUrl"
                    :show-file-list="false"
                    :on-success="uploadCoverImage"
                    :before-upload="validFileUpload"
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
    </el-form>
    <div id="aplayer"></div>
  </div>
</template>
<script>
import { getToken } from '@/utils/auth'
import { MusicApi } from '@/api/blog'
import 'aplayer/dist/APlayer.min.css';
import APlayer from 'aplayer';
export default {
  name: 'musicEdit',
  components: {
    APlayer,
  },
  data() {
    return {
      fileApiUrl: process.env.VUE_APP_BASE_API + '/qiNiu/upload',
      submitForm: {
        title: '',
        linkUrl: '',
        id: 0,
        isShow: 1,
        imgSrc: '',
        sortCode: 0
      },
      musicData:[],
      aplayerMo:null,
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

  },
  created() {
    if(this.$route.query.id>0){
      this.initMusicModel(this.$route.query.id)
    }
  },
  methods: {
    isShowChange(data) {
      this.submitForm.isShow = data
    },
    initMusicModel(musicId) {
      MusicApi.getMusicDetail(musicId).then(response => {
        if (response.code == 0) {
          var musicModel = response.data
          this.submitForm.imgSrc = musicModel.converUrl
          this.submitForm.title = musicModel.title
          this.submitForm.linkUrl = musicModel.url
          this.submitForm.sortCode = musicModel.sortCode
          this.submitForm.id = musicModel.id
          this.musicData = [
            {
              name: musicModel.title,
              artist: "",
              url:musicModel.url,
              cover:musicModel.converUrl
            }
          ];
          this.initMusicPlayer()
        }
      })
    },
    submitMusic() {
      var _this = this
      let data={
        id:this.submitForm.id,
        title:this.submitForm.title,
        url:this.submitForm.linkUrl,
        converUrl:this.submitForm.imgSrc,
        sortCode:this.submitForm.sortCode
      }
      MusicApi.saveMusic(data).then(response => {
        if (response.code == 0) {
          //保存后关闭页面
          _this.returnMusic()
        }
      })
    },
    returnMusic() {
      this.pause()
      this.$store
        .dispatch('tagsView/delView', this.$route)
        .then(({ visitedViews }) => {
          this.$router.push({
            path: '/music',
            query: {}
          })
        })
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
    uploadMusic(res, file) {
      if (res.success == 1) {
        this.submitForm.linkUrl = res.result[0].url
        this.submitForm.title = res.result[0].name
      } else {
        this.$message.error(res.msg)
      }
    },
    validFileUpload(file) {
      const issync = true
      const isLt2M = file.size / 1024 / 1024 < 36
      if (!isLt2M) {
        this.$message.error('上传文件大小不能超过 36MB!')
      }
      return issync && isLt2M
    },
    handleRemove() {
      this.submitForm.imgSrc = ''
    },
    handlePictureCardPreview() {
      this.dialogVisible = true
    },
    initMusicPlayer(){
      let _this=this;
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
    },
    pause() {
      if(this.aplayerMo){
        this.aplayerMo.pause()
      }
    },
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
</style>
