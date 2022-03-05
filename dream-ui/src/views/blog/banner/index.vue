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
        v-model="listQuery.sortCode"
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
      <el-button
        class="filter-item"
        style="margin-left: 10px"
        @click="linktoEdit(0)"
        type="primary"
        icon="el-icon-edit"
        >新增</el-button
      >
      <el-button
        class="filter-item"
        style="margin-left: 10px"
        @click="reviewBanner"
        type="primary"
        icon="el-icon-view"
        >预览</el-button
      >
    </div>

    <el-table
      height="678"
      :key="tableKey"
      :data="list"
      v-loading="listLoading"
      element-loading-text="给我一点时间"
      border
      fit
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column align="center" label="编号" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.serialNumber }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="250px" label="标题">
        <template slot-scope="scope">
          <span class="link-type">{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="排序码" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.sortCode }}</span>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="最后更新" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.updateOn }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="操作"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            @click="linktoEdit(scope.row.id)"
            >编辑</el-button
          >
          <el-button
            type="danger"
            icon="el-icon-delete"
            @click="deleteBanner(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗, 新增 / 修改 -->
    <el-dialog title="预览" :visible.sync="dialogFormVisible">
      <el-carousel :interval="3000" arrow="always" height="400px">
        <el-carousel-item v-for="banner in bannerList" :key="banner.id">
          <a :href="banner.linkUrl">
            <img
              :src="banner.imgSrc"
              alt="个人博客"
              data-bd-imgshare-binded="1"
            />
          </a>
          <span class="imginfo">{{ banner.title }}</span>
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
        :page-sizes="[10, 20, 30, 50]"
        :page-size="listQuery.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script type="text/javascript">
import { BannerApi } from '@/api/blog'
export default {
  name: 'Banner',
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      dialogFormVisible: false,
      bannerList: [],
      listQuery: {
        pageIndex: 1,
        pageSize: 10,
        title: '',
        sortCode: '1'
      },
      sortOptions: [
        { name: '最后更新 升序', key: '+updateOn', value: '0' },
        { name: '最后更新 降序', key: '-updateOn', value: '1' }
      ]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      var param = this.listQuery
      let _this = this
      BannerApi.getBannerList(param).then(response => {
        if (response.code == 0) {
          _this.list = response.data.rows
          _this.total = response.data.totalCount
          _this.listLoading = false
        }
      })
    },
    linktoEdit(bannerId) {
      this.$router.push({
        path: '/bannerEdit',
        query: { id: bannerId }
      })
    },
    reviewBanner() {
      var _this = this
      _this.dialogFormVisible = true
      BannerApi.getBannerList().then(response => {
        if (response.code == 0) {
          var resultCount = response.data.rows.length
          if (resultCount > 0) {
            _this.bannerList = response.data.rows
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
    deleteBanner(rowId) {
      this.$confirm('确定要[删除]该条记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          BannerApi.deleteBanner(rowId).then(response => {
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
.el-table .fixed-width .el-button--mini{
  color: #fff;
  padding-left: 10px;
  padding-right: 10px;
  width: inherit;
}
</style>
