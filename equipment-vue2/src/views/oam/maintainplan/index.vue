<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="计划名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入计划名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="执行部门" prop="executeDeptName">
        <el-input v-model="queryParams.executeDeptName" placeholder="请输入执行部门名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="状态" clearable size="small">
          <el-option v-for="dict in statusDictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
        </el-select>
      </el-form-item>
      <el-form-item label="区域" prop="equiplocationId">
        <TreeSelect
          v-model="queryParams.equiplocationId"
          :options="installlocationTree"
          :normalizer="normalizer1"
          placeholder="请选择位置"
          class="treeSelectCSS"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="queryParams.remark" placeholder="请输入备注" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="openForm(undefined)"
                   v-hasPermi="['oam:maintain-plan:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['oam:maintain-plan:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="计划名称" align="center" prop="name" />
      <el-table-column label="执行部门" align="center" prop="executeDeptName" />
      <el-table-column label="计划状态" align="center" prop="statusName" />
      <el-table-column label="执行区域" align="center" prop="equiplocationName" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建者" align="center" prop="creator" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新者" align="center" prop="updater" />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-notebook-1" @click="openDetailForm(scope.row)"
                     v-hasPermi="['oam:maintain-plan:update']">计划内容</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="openForm(scope.row.id)"
                     v-hasPermi="['oam:maintain-plan:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" :disabled="scope.row.status === 0"
                     v-hasPermi="['oam:maintain-plan:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize" @pagination="getList"/>
    <!-- 对话框(添加 / 修改) -->
    <MaintainPlanForm ref="formRef" :statusDictDatas="statusDictDatas" @success="getList" />
    <!-- 保养内容框 -->
    <MaintainPlanDetail ref="detailRef" />
  </div>
</template>

<script>
import * as MaintainPlanApi from '@/api/oam/maintainplan';
import MaintainPlanForm from './MaintainPlanForm.vue';
import MaintainPlanDetail from './MaintainPlanDetail.vue';
import * as InstalllocationApi from '@/api/property/installlocation';
import TreeSelect from "@riophae/vue-treeselect";
export default {
  name: "MaintainPlan",
  components: {MaintainPlanForm,MaintainPlanDetail,TreeSelect},
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
      // 保养计划列表
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
        name: null,
        executeDeptName: null,
        status: null,
        remark: null,
      },
      //状态字典;0正常；1禁用
      statusDictDatas: [
        {value:0,label: this.$t("message.Button.normal")},
        {value:1,label: this.$t("message.Button.disable")}
      ],
      installlocationTree:[],//设备安装位置树形结构
    };
  },
  created() {
    this.getList();
    this.getAllTreeOrList();
  },
  methods: {
    /** 查询列表 */
    async getList() {
      try {
        this.loading = true;
        const res = await MaintainPlanApi.getMaintainPlanPage(this.queryParams);
        this.list = res.data.list;
        this.total = res.data.total;
      } finally {
        this.loading = false;
      }
    },
    async getAllTreeOrList() {
      this.installlocationTree = [];
      const InstalllocationRes = await InstalllocationApi.getInstalllocationList();
      this.installlocationTree = this.handleTreeForString(InstalllocationRes.data,'id','supId');//构建设备位置树
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
    /** 转换设备类型数据结构 */
    normalizer1(node){
      return this.myNormalizer(node,"id","name");
    },
    /** 添加/修改操作 */
    openForm(id) {
      this.$refs["formRef"].open(id);
    },
    /** 删除按钮操作 */
    async handleDelete(row) {

      await this.$modal.confirm('是否确认删除保养计划及其下面所有的保养内容《"' + row.name + '》?')
      try {
       await MaintainPlanApi.deleteMaintainPlan(row.id);
       await this.getList();
       this.$modal.msgSuccess("删除成功");
      } catch {}
    },
    /** 导出按钮操作 */
    async handleExport() {
      await this.$modal.confirm('是否确认导出所有保养计划数据项?');
      try {
        this.exportLoading = true;
        const res = await MaintainPlanApi.exportMaintainPlanExcel(this.queryParams);
        this.$download.excel(res, '保养计划.xls');
      } catch {
      } finally {
        this.exportLoading = false;
      }
    },
    openDetailForm(plan){
      this.$refs["detailRef"].open(plan);
    }
  }
};
</script>