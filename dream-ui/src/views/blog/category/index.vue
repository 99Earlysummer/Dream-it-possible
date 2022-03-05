<template>
  <div class="app-container calendar-list-container">
    <div class="mod-menu">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="primary" @click="addRoot">新增</el-button>
        </el-form-item>
      </el-form>
      <el-table
        :data="data"
        :columns="columns"
        border
        :row-style="showRow"
        v-bind="$attrs"
      >
        <el-table-column
          v-for="(column, index) in columns"
          :key="column.value"
          :label="column.text"
          :width="column.width"
        >
          <template slot-scope="scope">
            <span
              v-if="index === 0"
              v-for="space in scope.row._level"
              class="ms-tree-space"
              :key="space"
            ></span>
            <span
              class="tree-ctrl"
              v-if="iconShow(index, scope.row)"
              @click="toggleExpanded(scope.$index)"
            >
              <i v-if="!scope.row._expanded" class="el-icon-caret-right"></i>
              <i v-else class="el-icon-caret-bottom"></i>
            </span>
            {{ scope.row[column.value] }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="280">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="success"
              @click="handleCreate(scope.row, 0)"
              icon="el-icon-circle-plus-outline"
              >添加</el-button
            >
            <el-button
              size="mini"
              type="primary"
              @click="handleCreate(scope.row, 1)"
              icon="el-icon-edit"
              >编辑</el-button
            >
            <el-button
              size="mini"
              type="danger"
              @click="deleteCate(scope.row)"
              icon="el-icon-delete"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!-- 弹窗, 新增 / 修改 -->
      <el-dialog
        :title="textMap[dialogStatus]"
        :visible.sync="dialogFormVisible"
      >
        <el-form
          :rules="rules"
          :model="temp"
          label-position="left"
          label-width="140px"
          style="width: 400px; margin-left: 50px"
        >
          <el-form-item label="分类名称" prop="name">
            <el-input v-model="temp.name"></el-input>
          </el-form-item>
          <el-form-item label="排序码" prop="sort">
            <el-input-number
              v-model="temp.sort"
              controls-position="right"
              :min="1"
              :max="10"
            ></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="resetData">取消</el-button>
          <el-button
            v-if="dialogStatus == 'create'"
            type="primary"
            @click="addOrUpdate"
            >确定</el-button
          >
          <el-button v-else type="primary" @click="addOrUpdate">更新</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { CategoryApi } from '@/api/blog'
export default {
  name: 'category',
  data() {
    return {
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        name: [
          { required: true, message: '分类名称 是必须的', trigger: 'blur' }
        ]
      },
      temp: {
        id: undefined,
        parentId: 0,
        parentcode: '',
        name: '',
        code: '',
        sort: ''
      },
      columns: [
        {
          text: '分类名称',
          value: 'name'
        },
        {
          text: '分类编码',
          value: 'code',
          width: 200
        },
        {
          text: '等级',
          value: 'level',
          width: 200
        },
        {
          text: '排序码',
          value: 'sort',
          width: 200
        }
      ],
      data: []
    }
  },
  mounted() {},
  created() {
    this.getDataList()
  },
  watch: {},
  methods: {
    formatData() {
      var tmp
      console.log(this.data)
      if (!Array.isArray(this.data)) {
        tmp = [this.data]
      } else {
        tmp = this.data
      }
      var func = this.evalFunc || this.treeToArray
      var args = this.evalArgs
        ? Array.concat([tmp, false], this.evalArgs)
        : [tmp, false]
      var results = func.apply(null, args)
      this.data = results
    },
    treeToArray(data, expandAll, parent, level) {
      var _this = this
      var tmp = []
      if (data) {
        Array.from(data).forEach(function(record) {
          if (record._expanded === undefined) {
            _this.$set(record, '_expanded', expandAll)
          }
          var _level = 1
          if (level !== undefined && level !== null) {
            _level = level + 1
          }
          _this.$set(record, '_level', _level)
          // 如果有父元素
          if (parent) {
            _this.$set(record, 'parent', parent)
          }
          tmp.push(record)
          if (record.children && record.children.length > 0) {
            var children = _this.treeToArray(
              record.children,
              expandAll,
              record,
              _level
            )
            tmp = tmp.concat(children)
          }
        })
      }
      return tmp
    },
    handleCreate(row, operate) {
      this.dialogFormVisible = true
      if (operate == 0) {
        //新增
        this.dialogStatus = 'create'
        this.temp.id = 0
        this.temp.parentId = row.id
        this.temp.parentcode = row.code
      } else {
        this.temp.id = row.id
        this.temp.name = row.name
        this.temp.code = row.code
        this.temp.sort = row.sort
        this.temp.parentId = row.parentId
        this.temp.parentcode = row.code
      }
    },
    addRoot() {
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.temp.id = 0
      this.temp.parentId = 0
    },
    addOrUpdate() {
      var _this = this
      CategoryApi.saveCategory(_this.temp).then(response => {
        if (response.code == 0) {
          _this.resetData()
          _this.getDataList()
        }
      })
    },
    resetData() {
      this.temp.id = 0
      this.temp.parentId = 0
      this.temp.name = ''
      this.temp.code = ''
      this.temp.sort = 0
      this.dialogStatus = ''
      this.dialogFormVisible = false
    },
    deleteCate(row) {
      var _this = this
      _this
        .$confirm('确定要[删除]' + row.name + '吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          CategoryApi.deleteCategory(row.id).then(response => {
            if (response.code == 0) {
              _this.getDataList()
            }
          })
        })
    },
    getDataList() {
      var _this = this
      _this.dataListLoading = true
      CategoryApi.getCategoryTreeData(0).then(response => {
        if (response.code == 0) {
          _this.data = response.data
          _this.formatData()
          _this.dataListLoading = false
          _this.temp.parentId = 0
        }
      })
    },
    showRow(row) {
      var show = row.row.parent
        ? row.row.parent._expanded && row.row.parent._show
        : true
      row.row._show = show
      return show
        ? {
            animation: 'treeTableShow 1s',
            '-webkit-animation': 'treeTableShow 1s'
          }
        : { display: 'none' }
    },
    // 切换下级是否展开
    toggleExpanded(trIndex) {
      var record = this.data[trIndex]
      record._expanded = !record._expanded
    },
    // 图标显示
    iconShow(index, record) {
      return index === 0 && record.children && record.children.length > 0
    }
  }
}
</script>
<style scoped>
.el-tree-node__content {
  height: 40px;
}
.w-500 {
  width: 500px;
  display: inline-block;
}
@keyframes treeTableShow {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
@-webkit-keyframes treeTableShow {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
.ms-tree-space {
  position: relative;
  top: 1px;
  display: inline-block;
  font-style: normal;
  font-weight: 400;
  line-height: 1;
  width: 18px;
  height: 14px;
}
.processContainer {
  width: 100%;
  height: 100%;
}
table td {
  line-height: 26px;
}

.tree-ctrl {
  position: relative;
  cursor: pointer;
  margin-left: -18px;
}
</style>
