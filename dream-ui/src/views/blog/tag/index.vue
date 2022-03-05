<template>
  <el-row>
    <el-col :span="12">
      <el-card class="box-card" style="min-height: 420px">
        <div slot="header" class="clearfix">
          <span>当前可用标签</span>
        </div>
        <el-tag
          v-for="tag in tags"
          :key="tag.name"
          style="margin: 5px"
          :color="tag.color"
          closable
          @close="removeTag(tag)"
          @click="editTag(tag)"
        >
          {{ tag.value }}
        </el-tag>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card class="box-card" style="min-height: 420px">
        <div slot="header" class="clearfix">
          <span>添加选项</span>
        </div>
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="标签名称">
            <el-input
              placeholder="请输入新标签"
              v-model="defaultTag"
              style="max-width: 220px"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="选择颜色">
            <el-color-picker
              v-model="defaultColor"
              show-alpha
              @change="colorChange"
              :predefine="predefineColors"
            >
            </el-color-picker>
          </el-form-item>
          <el-button
            v-loading="loading"
            style="margin-left: 10px"
            type="success"
            @click="addTag"
            >添加新标签
          </el-button>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
</template>
<script>
import { TagApi } from '@/api/blog'
export default {
  name: 'Tag',
  data() {
    return {
      // 遮罩层
      loading: false,
      defaultId: 0,
      defaultColor: '#EB6841',
      predefineColors: [
        '#036564',
        '#EB6841',
        '#3FB8AF',
        '#FE4365',
        '#FC9D9A',
        '#EDC951',
        '#C8C8A9',
        '#83AF9B',
        '#8A9B0F',
        '#3299BB',
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#c7158577'
      ],
      defaultTag: '',
      tags: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      let _this = this
      TagApi.getTagList().then(response => {
        if (response.code == 0) {
          _this.tags = response.data
        }
      })
    },
    removeTag(tag) {
      let _this = this
      TagApi.deleteTag(tag.id).then(response => {
        if (response.code == 0) {
          _this.tags.splice(_this.tags.indexOf(tag), 1)
        }
      })
    },
    addTag() {
      var value = this.defaultTag.trim()
      if (
        JSON.stringify(this.tags)
          .toLowerCase()
          .indexOf(value.toLowerCase()) == -1
      ) {
        var param = {}
        param.id = this.defaultId
        param.color = this.defaultColor
        param.name = this.defaultTag
        param.alia = this.defaultTag
        let _this = this
        TagApi.saveTag(param).then(response => {
          if (response.code == 0) {
            _this.tags.push({
              value: value,
              color: _this.defaultColor,
              id: response.id,
              alia: value
            })
            _this.defaultTag = ''
          }
        })
      }
    },
    editTag(tag) {
      debugger
      this.defaultId = tag.id
      this.defaultColor = tag.color
      this.defaultTag = tag.value
    },
    colorChange(data) {
      this.defaultColor = data
    }
  }
}
</script>
<style scoped>
.el-row {
  padding: 15px;
}
.el-card {
  height: calc(100vh - 115px);
  margin: 0 10px;
}
.el-tag {
  padding: 0 10px;
  height: 32px;
  line-height: 30px;
  font-size: 12px;
  color: #fff;
  border-radius: 4px;
  box-sizing: border-box;
  border: 1px solid rgba(64, 158, 255, 0.2);
}
.el-tag .el-icon-close {
  color: #fff !important;
}
.el-tag /deep/ .el-tag__close {
  color: #fff;
}
</style>
