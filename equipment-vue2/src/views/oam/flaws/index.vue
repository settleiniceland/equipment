<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="12vh">
      <el-form-item label="设备名称" prop="equipProfileName">
        <el-input v-model="queryParams.equipProfileName" placeholder="请输入设备档案名称" clearable/>
      </el-form-item>
      <el-form-item label="设备编码" prop="equipCode">
        <el-input v-model="queryParams.equipCode" placeholder="请输入设备档案编码" clearable/>
      </el-form-item>
      <el-form-item label="设备属性" prop="equipAttribute">
        <el-select v-model="queryParams.equipAttribute" placeholder="请选择设备属性" clearable filterable size="small">
          <el-option v-for="attribute in equipAttributeDict" :key="attribute.id" :label="attribute.name" :value="attribute.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="是否停机" prop="isStop">
        <el-select v-model="queryParams.isStop" placeholder="是否停机" clearable filterable size="small">
          <el-option v-for="equip in equipStatusDict" :key="equip.id" :label="equip.name" :value="equip.id"/>
        </el-select>
      </el-form-item>
      <br>
      <el-form-item label="缺陷状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择缺陷状态" clearable filterable size="small">
          <el-option v-for="flaw in flawsStatusDict" :key="flaw.id" :label="flaw.name" :value="flaw.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="缺陷详情" prop="details">
        <el-input v-model="queryParams.details" placeholder="请输入缺陷详情" clearable/>
      </el-form-item>
      <el-form-item label="开始时间" prop="beginTime">
        <el-date-picker v-model="queryParams.beginTime" style="width: 194px" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="解决时间" prop="solveTime">
        <el-date-picker v-model="queryParams.solveTime" style="width: 194px" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <br>
      <el-form-item label="维修计划" prop="fixPlanName">
        <el-input v-model="queryParams.fixPlanName" placeholder="请输入维修计划名称" clearable/>
      </el-form-item>
      <el-form-item label="维修时长" prop="solveDuration">
        <el-input v-model="queryParams.solveDuration" placeholder="请输入维修时长【单位h】" clearable/>
      </el-form-item>
      <el-form-item label="维修车间" prop="solveDeptName">
        <el-input v-model="queryParams.solveDeptName" placeholder="维修车间" clearable/>
      </el-form-item>
      <el-form-item label="维修人数" prop="solveHumanNum">
        <el-input v-model="queryParams.solveHumanNum" placeholder="维修人数" clearable/>
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
                   v-hasPermi="['oam:flaws:create']">{{ $t("message.Button.add") }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['oam:flaws:export']">{{ $t("message.Button.export") }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="list" :show-overflow-tooltip="true" :row-class-name="getRowClassName">
      <el-table-column label="设备名称" align="center" prop="equipProfileName" />
      <el-table-column label="设备编码" align="center" prop="equipCode" />
      <el-table-column label="设备属性" align="center" prop="equipAttributeName" />
      <el-table-column label="设备状态" align="center" prop="isStopName" />
      <el-table-column label="缺陷状态" align="center" prop="statusName" />
      <el-table-column label="缺陷详情" align="center" prop="details" />
      <el-table-column label="缺陷开始时间" align="center" prop="beginTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.beginTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="缺陷解决时间" align="center" prop="solveTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.solveTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="持续时间" align="center" prop="duration" />
      <el-table-column label="维修计划" align="center" prop="fixPlanName" />
      <el-table-column label="维修时长" align="center" prop="solveDuration" />
      <el-table-column label="解决车间" align="center" prop="solveDeptName" />
      <el-table-column label="维修人数" align="center" prop="solveHumanNum" />
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
                     v-hasPermi="['oam:flaws:update']">{{ $t("message.Button.update") }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['oam:flaws:delete']">{{ $t("message.Button.del") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize" @pagination="getList"/>
    <!-- 对话框(添加 / 修改) -->
    <FlawsForm ref="formRef" @success="getList" />
    </div>
</template>

<script>
import * as FlawsApi from '@/api/oam/flaws';
import FlawsForm from './FlawsForm.vue';
export default {
  name: "Flaws",
  components: {FlawsForm,},
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
      // 缺陷库列表
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
        equipProfileId: null,
        equipProfileName: null,
        equipCode: null,
        status: null,
        isStop: null,
        details: null,
        beginTime: [],
        solveTime: [],
        fixPlanId: null,
        solveDuration: null,
        solveDeptId: null,
        solveDeptName: null,
        solveHumanNum: null,
        createTime: [],
        updateTime: [],
        fixPlanName: null,
      },
      flawsStatusDict: [
        {id:1, name:'待排维修计划'},
        {id:2, name:'已排计划待维修'},
        {id:3, name:'已维修'}
      ],
      equipStatusDict: [
        {id:1, name:'开机'},
        {id:2, name:'停机'}
      ],
      equipAttributeDict: [
        {id:1, name:'设备组'},
        {id:2, name:'单体设备'},
        {id:3, name:'设备组件'}
      ]
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
        const res = await FlawsApi.getFlawsPage(this.queryParams);
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
      const id = row.id;
      await this.$modal.confirm('是否确认删除缺陷库编号为"' + id + '"的数据项?')
      try {
       await FlawsApi.deleteFlaws(id);
       await this.getList();
       this.$modal.msgSuccess("删除成功");
      } catch {}
    },
    /** 导出按钮操作 */
    async handleExport() {
      await this.$modal.confirm('是否确认导出所有缺陷库数据项?');
      try {
        this.exportLoading = true;
        const res = await FlawsApi.exportFlawsExcel(this.queryParams);
        this.$download.excel(res, '缺陷库.xls');
      } catch {
      } finally {
        this.exportLoading = false;
      }
    },
    //每一层的颜色
    getRowClassName({ row }) {
      return `flaws-level-${row.status}`;
    },
  }
};
</script>
<style lang="scss" >
  .flaws-level-1 {
    background-color: #e04747 !important;
  }
  .flaws-level-2 {
    background-color: #c7e65a !important;
  }
  .flaws-level-3 {
    background-color: #72e655 !important;
  }
</style>
