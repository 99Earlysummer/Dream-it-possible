<template>
  <el-row>
    <el-col :span="12">
      <el-card style="min-height: 520px" class="box-card">
        <div slot="header" class="clearfix">
          <span>当前友链</span>
        </div>
        <el-table :data="Partners" style="width: 100%">
          <el-table-column prop="siteName" label="名称" width="180">
          </el-table-column>
          <el-table-column prop="siteUrl" label="链接"> </el-table-column>
          <el-table-column prop="sort" label="排序码" align="center" width="80">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            align="center"
            width="180"
          >
            <template slot-scope="scope">
              <el-button
                type="primary"
                @click="editPartner(scope.row)"
                size="mini"
                >编辑</el-button
              >
              <el-button
                type="danger"
                @click="removePartner(scope.row)"
                size="mini"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card style="min-height: 520px" class="box-card">
        <div slot="header" class="clearfix">
          <span>添加选项</span>
        </div>
        <el-form label-width="60px">
          <el-form-item label="名称">
            <el-input
              placeholder="请输入新名称"
              v-model="defaultPartner"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="链接">
            <el-input
              placeholder="请输入新链接"
              v-model="defaultLink"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="描述">
            <el-input
              type="textarea"
              class="article-textarea"
              :rows="1"
              placeholder="请输入 描述"
              v-model="defaultDesc"
              required
              :maxlength="100"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="排序">
            <el-input-number
              v-model="defaultSort"
              :min="0"
              :max="20"
              label="描述文字"
            ></el-input-number>
          </el-form-item>
          <el-form-item>
            <el-button
              v-loading="loading"
              style="margin-left: 10px"
              type="primary"
              @click="addPartner"
              >{{ defaultAction }}
            </el-button>
            <el-button @click="clearForm">清空</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
</template>
<script>
import { PartnerApi } from '@/api/blog'
export default {
  name: 'partner',
  data() {
    return {
      loading: false,
      defaultId: 0,
      defaultSort: 0,
      defaultLink: '',
      defaultDesc: '',
      defaultPartner: '',
      Partners: [],
      defaultAction: '添加'
    }
  },
  created() {
    this.getPartnerList()
  },
  methods: {
    getPartnerList() {
      let _this = this
      PartnerApi.getPartnerList().then(response => {
        if (response.code == 0) {
          _this.Partners = response.data
        }
      })
    },
    removePartner(partner) {
      this.$confirm('确定要[删除]该条记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        PartnerApi.deletePartner(partner.id).then(response => {
          if (response.code == 0) {
            this.Partners.splice(this.Partners.indexOf(partner), 1)
          }
        })
      })
    },
    clearForm() {
      this.defaultAction = '添加'
      this.defaultId = 0
      this.defaultPartner = ''
      this.defaultLink = ''
      this.defaultDesc = ''
      this.defaultSort = 0
    },
    addPartner() {
      var value = this.defaultPartner.trim()
      var param = {}
      param.id = this.defaultId
      param.siteName = this.defaultPartner
      param.siteDesc = this.defaultDesc
      param.siteUrl = this.defaultLink
      param.sort = this.defaultSort
      PartnerApi.savePartner(param).then(response => {
        if (response.code == 0) {
          this.getPartnerList()
          this.clearForm()
        }
      })
    },
    editPartner(row) {
      this.defaultAction = '保存'
      this.defaultId = row.id
      this.defaultPartner = row.siteName
      this.defaultDesc = row.siteDesc
      this.defaultLink = row.siteUrl
      this.defaultSort = row.sort
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
  color: #fff;
}
</style>
