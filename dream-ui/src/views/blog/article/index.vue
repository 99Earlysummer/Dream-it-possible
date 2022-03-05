<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      v-show="showSearch"
      :inline="true"
    >
      <el-form-item label="文章名称" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入文章名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select
          clearable
          style="width: 110px"
          class="filter-item"
          v-model="queryParams.status"
          placeholder="状态"
        >
          <el-option
            v-for="item in statusOptions"
            :key="item.code"
            :label="item.name"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-select
          filterable
          class="filter-item"
          v-model="queryParams.categoryId"
          style="width: 130px"
          placeholder="分类"
        >
          <el-option
            v-for="item in categoryOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:role:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:role:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:role:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:role:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="articleList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="标题"
        prop="title"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="作者"
        prop="author"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column label="浏览量" prop="viewCount" width="100" />
      <el-table-column
        label="状态"
        align="center"
        prop="statusAliaName"
        width="100"
      >
      </el-table-column>
      <el-table-column
        label="发布时间"
        align="center"
        prop="publishTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="180"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:role:edit']"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:role:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageIndex"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { ArticleApi, CategoryApi } from '@/api/blog'
export default {
  name: 'Article',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 文章表格数据
      articleList: [],
      // 弹出层标题
      title: '',
      // 日期范围
      dateRange: [],
      // 文章状态
      statusOptions: [
        { name: '草稿', code: 'draft', value: '0' },
        {
          name: '已发布',
          code: 'published',
          value: '1'
        },
        { name: '定时发布', code: 'timer', value: '2' },
        { name: '删除', value: '-1', code: 'deleted' }
      ],
      // 文章分类
      categoryOptions: [],
      // 查询参数
      queryParams: {
        pageIndex: 1,
        pageSize: 10,
        status: '',
        title: '',
        categoryId: '',
        categoryCodes: [],
        sort: '1'
      }
    }
  },
  created() {
    this.getList()
    this.getCategoryData()
  },
  methods: {
    /** 查询文章列表 */
    getList() {
      this.loading = true
      ArticleApi.getArticlePage(this.queryParams).then(response => {
        this.articleList = response.data.rows
        this.total = response.data.totalCount
        this.loading = false
      })
    },
    /** 查询文章分类 */
    getCategoryData() {
      CategoryApi.getCategoryData().then(response => {
        this.categoryOptions = response.data
      })
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageIndex = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push({
        path: '/articleEdit',
        query: { id: 0 }
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$router.push({
        path: '/articleEdit',
        query: { id: row.id }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const articleIds = row.id || this.ids
      this.$confirm(
        '是否确认删除该篇文章?',
        '警告',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(function() {
          return ArticleApi.deleteData(articleIds)
        })
        .then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      /*    const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有角色数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportRole(queryParams);
        }).then(response => {
          this.download(response.msg);
        }) */
    }
  }
}
</script>
<style>
.el-table .fixed-width .el-button--mini{
  color: #fff;
  padding-left: 10px;
  padding-right: 10px;
  width: inherit;
}
</style>
