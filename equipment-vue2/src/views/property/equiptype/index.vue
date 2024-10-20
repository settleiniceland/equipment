<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="类别编码" prop="code">
        <el-input v-model="queryParams.code" placeholder="请输入设备类别编码" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="类别名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入设备类别名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">{{ $t("message.Button.search") }}</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">{{ $t("message.Button.origin") }}</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="openForm(undefined)"
                   v-hasPermi="['property:equiptype:create']">{{ $t("message.Button.add") }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['property:equiptype:export']">{{ $t("message.Button.export") }}</el-button>
      </el-col>
      <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-sort" size="mini" @click="toggleExpandAll">
              {{ $t("message.Button.unfoldFold") }}
            </el-button>
          </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
        v-loading="loading"
        :data="list"
        :stripe="true"
        :show-overflow-tooltip="true"
        v-if="refreshTable"
        row-key="id"
        :default-expand-all="isExpandAll"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
      <el-table-column label="类别编码" align="center" prop="code" />
      <el-table-column label="类别名称" align="center" prop="name" />
      <el-table-column label="上级类别" align="center" prop="supName" />
      <el-table-column :label='$t("message.Button.creator")' align="center" prop="creator" />
      <el-table-column :label='$t("message.Button.createTime")' align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label='$t("message.Button.modifier")' align="center" prop="updater" />
      <el-table-column :label='$t("message.Button.updateTime")' align="center" prop="updateTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label='$t("message.Button.operation")' align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="openForm(scope.row.id)"
                     v-hasPermi="['property:equiptype:update']">{{ $t("message.Button.update") }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['property:equiptype:delete']">{{ $t("message.Button.del") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 对话框(添加 / 修改) -->
    <EquiptypeForm ref="formRef" @success="getList" />
    </div>
</template>

<script>
  import * as EquiptypeApi from '@/api/property/equiptype';
  import EquiptypeForm from './EquiptypeForm.vue';
  export default {
    name: "Equiptype",
    components: {EquiptypeForm,},
    data() {
      return {
        // 遮罩层
        loading: true,
        // 导出遮罩层
        exportLoading: false,
        // 显示搜索条件
        showSearch: true,
        // 设备类别列表
        list: [],
        // 是否展开，默认全部展开
        isExpandAll: true,
        // 重新渲染表格状态
        refreshTable: true,
        // 选中行
        currentRow: {},
        // 查询参数
        queryParams: {
          code: null,
          name: null,
        },
        // 工具map键值对对象
        listMap: new Map(),
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询列表 */
      async getList() {
        try {
          this.loading = true;
          const res = await EquiptypeApi.getEquiptypeList(this.queryParams);
          res.data.forEach(element => {
            this.listMap.set(element.id,element);
          });
          res.data.forEach(element => {
            if(element.supId!=="0"){
              const f = this.listMap.get(element.supId);
              element.supName=f?f.name:"";
            }else{
              element.supName="---";
            }
          })
        this.list = this.handleTreeForString(res.data, 'id', 'supId');
        } finally {
          this.loading = false;
        }
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNo = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      /** 添加/修改操作 */
      openForm(id) {
        this.$refs["formRef"].open(id);
      },
      /** 删除按钮操作 */
      async handleDelete(row) {
        await this.$modal.confirm('是否确认删除设备类别《' + row.name + row.code + '》?')
        try {
        await EquiptypeApi.deleteEquiptype(row.id);
        await this.getList();
        this.$modal.msgSuccess("删除成功");
        } catch {}
      },
      /** 导出按钮操作 */
      async handleExport() {
        await this.$modal.confirm('是否确认导出所有设备类别数据项?');
        try {
          this.exportLoading = true;
          const res = await EquiptypeApi.exportEquiptypeExcel(this.queryParams);
          this.$download.excel(res, '设备类别.xls');
        } catch {
        } finally {
          this.exportLoading = false;
        }
      },
      /** 展开/折叠操作 */
      toggleExpandAll() {
        this.refreshTable = false
        this.isExpandAll = !this.isExpandAll
        this.$nextTick(function () {
          this.refreshTable = true
        })
      }
    }
  };
</script>
