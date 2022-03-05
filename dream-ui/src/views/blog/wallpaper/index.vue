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
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
        >搜索</el-button
      >
      <el-button
        class="filter-item"
        style="margin-left: 10px"
        @click="linktoEdit(0)"
        type="primary"
        icon="el-icon-edit"
        >新增</el-button
      >
    </div>

    <el-row :span="24">
      <el-col v-bind:key="index" v-for="(m,index) in list" :span="4">
        <el-card class=""   :body-style="{ padding: '10px',width:'220px' }">
          <img :src="m.imgSrc" class="image">
          <div style="padding: 14px;">
            <span class="title">{{ m.title }}</span>
            <div class="bottom clearfix">
              <el-button size="mini" type="primary" @click="linktoEdit(m.id)" class="button">编辑</el-button>
              <el-button size="mini" type="danger" @click="deleteWallpaper(m.id)" class="button">删除</el-button>
            </div>
          </div>
        </el-card>
      </el-col>

    </el-row>
    <!-- 弹窗, 新增 / 修改 -->
    <el-dialog title="预览" :visible.sync="dialogFormVisible">
      <el-carousel :interval="3000" arrow="always" height="400px">
        <el-carousel-item v-for="wallpaper in wallpaperList" :key="wallpaper.id">
          <a :href="wallpaper.linkUrl">
            <img
              :src="wallpaper.imgSrc"
              alt="个人博客"
              data-bd-imgshare-binded="1"
            />
          </a>
          <span class="imginfo">{{ wallpaper.title }}</span>
        </el-carousel-item>
      </el-carousel>
    </el-dialog>
    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        class="txtCenter"
        @current-change="handleCurrentChange"
        :current-page="listQuery.pageIndex"
        :page-sizes="[12, 24, 36, 48]"
        :page-size="listQuery.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script type="text/javascript">
import { WallpaperApi  } from '@/api/blog'

export default {
  name: 'Wallpaper',
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      dialogFormVisible: false,
      wallpaperList: [],
      listQuery: {
        pageIndex: 1,
        pageSize: 24,
        title: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      let _this = this
      let param = _this.listQuery
      WallpaperApi.getWallpaper(param).then(response => {
        if (response.code == 0) {
          _this.list = response.data.rows
          _this.total = response.data.totalCount
          _this.listLoading = false
        }
      })
    },
    linktoEdit(wallpaperId) {
      this.$router.push({
        path: '/wallpaperEdit',
        query: { id: wallpaperId }
      })
    },
    reviewWallpaper() {
      var _this = this
      _this.dialogFormVisible = true
      WallpaperApi.getWallpaper().then(response => {
        if (response.code == 0) {
          var resultCount = response.data.rows.length
          if (resultCount > 0) {
            _this.wallpaperList = response.data.rows
          }
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
    deleteWallpaper(rowId) {
      this.$confirm('确定要[删除]该条记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          WallpaperApi.deleteWallpaper(rowId).then(response => {
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
<style scoped>
.app-container {
  height: calc(100vh - 150px);
}
.filter-item {
  margin: 10px;
}
.image{
  width: 200px;
  max-height: 112.5px;
}
.title{
  font-size: 12px;
}
.el-card{
  width: 220px !important;
  float: left !important;
  margin: 5px !important;
}
.bottom {
  margin-top: 13px;
  line-height: 12px;
}
</style>
