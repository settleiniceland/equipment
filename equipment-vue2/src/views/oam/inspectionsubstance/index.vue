<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备" prop="equipId">
        <TreeSelect
          v-model="queryParams.equipId"
          :options="equipTree"
          :normalizer="normalizer"
          placeholder="请选择设备"
          class="treeSelectCSS"
        />
      </el-form-item>
      <el-form-item label="点检内容" prop="details">
        <el-input v-model="queryParams.details" placeholder="请输入点检内容" clearable @keyup.enter.native="handleQuery"/>
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
                   v-hasPermi="['oam:inspection-substance:create']">{{ $t("message.Button.add") }}</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['oam:inspection-substance:export']">{{ $t("message.Button.export") }}</el-button>
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="设备名称" align="center" prop="equipName" />
      <el-table-column label="设备规格" align="center" prop="equipSpecification" show-overflow-tooltip />
      <el-table-column label="点检内容" align="center" prop="details" width="180"/>
      <el-table-column :label='$t("message.Button.creator")' align="center" prop="creator" show-overflow-tooltip />
      <el-table-column :label='$t("message.Button.createTime")' align="center" prop="createTime" show-overflow-tooltip >
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label='$t("message.Button.modifier")' align="center" prop="updater" show-overflow-tooltip />
      <el-table-column :label='$t("message.Button.updateTime")' align="center" prop="updateTime" show-overflow-tooltip >
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label='$t("message.Button.operation")' align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="openForm(scope.row.id)"
                     v-hasPermi="['oam:inspection-substance:update']">{{ $t("message.Button.update") }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['oam:inspection-substance:delete']">{{ $t("message.Button.del") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize" @pagination="getList"/>
    <!-- 对话框(添加 / 修改) -->
    <InspectionSubstanceForm ref="formRef" @success="getList" />
  </div>
</template>

<script>
  import * as InspectionSubstanceApi from '@/api/oam/inspectionsubstance';
  import * as EquipApi from '@/api/property/equip';
  import InspectionSubstanceForm from './InspectionSubstanceForm.vue';
  import TreeSelect from "@riophae/vue-treeselect";
  export default {
    name: "InspectionSubstance",
    components: {InspectionSubstanceForm,TreeSelect},
    data() {
      return {
        // 遮罩层
        loading: true,
        // 导出遮罩层
        exportLoading: false,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 点检内容列表
        list: [],
        // 是否展开，默认全部展开
        isExpandAll: true,
        // 重新渲染表格状态
        refreshTable: true,
        // 选中行
        currentRow: {},
        // 查询参数
        queryParams: {
          pageNo: 1,
          pageSize: 10,
          equipId: null,
          equipName: null,
          equipSpecification: null,
          details: null,
          deptId: null,
          creator: null,
          createTime: [],
          updater: null,
          updateTime: [],
        },
        equipTree: [], // 设备树形结构
      };
    },
    created() {
      this.getList();
      this.getAllTree();
    },
    methods: {
      /** 查询列表 */
      async getList() {
        try {
          this.loading = true;
          const res = await InspectionSubstanceApi.getInspectionSubstancePage(this.queryParams);
          this.list = res.data.list;
          this.total = res.data.total;
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
        await this.$modal.confirm('确认删除点检内容《' + row.details.substring(0, 6) + '...》数据项吗?')
        try {
          await InspectionSubstanceApi.deleteInspectionSubstance(row.id);
          await this.getList();
          this.$modal.msgSuccess("删除成功");
        } catch {}
      },
      /** 导出按钮操作 */
      // async handleExport() {
      //   await this.$modal.confirm('是否确认导出所有点检内容数据项?');
      //   try {
      //     this.exportLoading = true;
      //     const res = await InspectionSubstanceApi.exportInspectionSubstanceExcel(this.queryParams);
      //     this.$download.excel(res, '点检内容.xls');
      //   } catch {
      //   } finally {
      //     this.exportLoading = false;
      //   }
      // },
      /** 转换设备表数据结构 */
      normalizer(node){
        return this.myNormalizer(node,'id','equipName');
      },
      /** 获取所有所需要的树形结构 */
      async getAllTree(){
        //设备树型结构
        this.equipTree = [];
        const equipRes = await EquipApi.getEquipList();
        this.equipTree = this.handleTreeForString(equipRes.data,'id','supId');
      }
    }
  };
</script>
