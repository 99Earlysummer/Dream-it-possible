<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input
        @keyup.enter.native="handleFilter"
        style="width: 200px"
        class="filter-item"
        placeholder="请输入标题"
        v-model="listQuery.title"
      >
      </el-input>
      <el-select
        @change="handleFilter"
        style="width: 140px"
        class="filter-item"
        v-model="listQuery.sort"
      >
        <el-option
          v-for="item in sortOptions"
          :key="item.key"
          :label="item.name"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
        >搜索</el-button
      >
       <el-button v-loading="loading" type="success" @click="linktoEdit(0)"
                ><i class="el-icon-edit"></i>上传</el-button
              >
    </div>

    <el-table
      :key="tableKey"
      :data="list"
      v-loading="listLoading"
      element-loading-text="给我一点时间"
      border
      fit
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column
        align="center"
        label="编号"
        width="120"
        type="index"
      ></el-table-column>
      <el-table-column min-width="250px" label="歌曲名称">
        <template slot-scope="scope">
          <span class="link-type">{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="排序码" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.sortCode }}</span>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="最后更新" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.updateOn }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="操作"
        width="230"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            type="success"
            size="mini"
            icon="el-icon-edit"
            @click="linktoEdit(scope.row.id)"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            @click="deleteMusic(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        class="txtCenter"
        @current-change="handleCurrentChange"
        :current-page="listQuery.pageIndex"
        :page-sizes="[10, 20, 30, 50]"
        :page-size="listQuery.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
import { getToken } from '@/utils/auth'
import { MusicApi } from '@/api/blog'
export default {
  name: 'music',
  data() {
    return {
      loading: false,
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      dialogFormVisible: false,
      musicList: [],
      submitForm: [],
      listQuery: {
        pageIndex: 1,
        pageSize: 10,
        title: '',
        sortCode: '1'
      },
      fileApiUrl: process.env.VUE_APP_BASE_API + '/blog/music/upload',
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      sortOptions: [
        { name: '最后更新 升序', key: '+updateOn', value: '0' },
        { name: '最后更新 降序', key: '-updateOn', value: '1' }
      ]
    }
  },
  mounted() {},
  created() {
    this.getList()
  },
  methods: {
    linktoEdit(musicId) {
      this.$router.push({
        path: '/musicEdit',
        query: { id: musicId }
      })
    },
    submitMusic(status) {
      MusicApi.batchSaveMusic(this.submitForm).then(response => {
        if (response.code == 0) {
          this.getList()
        }
      })
    },
    myupload(param) {
      var formData = new FormData()
      // 文件对象
      formData.append('file', param.file)
      axios({
        url: this.fileApiUrl,
        method: 'post',
        data: formData,
        headers: {
          Authorization: 'Bearer ' + getToken(),
          'Content-Type': 'multipart/form-data'
        }
      }).then(res => {
        if (res.success == 1) {
          for (var i = 0; i < res.results.length; i++) {
            var f = res.results[i]
            var item = { title: f.name, url: f.url }
            this.submitForm.push(item)
          }
          this.submitMusic()
          this.getList()
        }
      })
    },
    uploadFile(res, file) {
      if (res.success == 1) {
        for (var i = 0; i < res.results.length; i++) {
          var f = res.results[i]
          var item = { title: f.name, url: f.url }
          this.submitForm.push(item)
          this.submitMusic()
          this.submitForm = []
          this.getList()
        }
      } else {
        this.$message.error(res.msg)
      }
    },
    validFileUpload(file) {
      const issync = true
      const isLt5M = file.size / 1024 / 1024 < 15
      if (!isLt5M) {
        this.$message.error('上传文件大小不能超过 5MB!')
      }
      return issync && isLt5M
    },
    getList() {
      MusicApi.getMusicList(this.listQuery).then(response => {
        if (response.code == 0) {
          this.list = response.data.rows
          this.total = response.data.totalCount
          this.listLoading = false
        }
      })
    },
    handleFilter() {
      this.listQuery.pageIndex = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.pageIndex = val
      this.getList()
    },
    deleteMusic(rowId) {
      this.$confirm('确定要[删除]该条记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          MusicApi.deleteMusic(rowId).then(response => {
            if (response.code == 0) {
              this.getList()
            }
          })
        })
        .catch(() => {})
    }
  }
}
</script>
<style >
.filter-container .filter-item {
  margin: 10px 10px;
}
.el-table .fixed-width .el-button--mini {
  width: inherit;
  padding: 7px 10px;
  color: #fff;
}
.app-container calendar-list-container {
  height: calc(100vh - 250px);
}
</style>
