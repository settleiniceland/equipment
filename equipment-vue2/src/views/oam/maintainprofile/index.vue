<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="主键id" prop="id">
        <el-input v-model="queryParams.id" placeholder="请输入主键id" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="保养计划id" prop="equipMaintainPlanId">
        <el-input v-model="queryParams.equipMaintainPlanId" placeholder="请输入保养计划id" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="保养计划名称" prop="equipMaintainPlanName">
        <el-input v-model="queryParams.equipMaintainPlanName" placeholder="请输入保养计划名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="保养内容id" prop="equipMaintainDetailId">
        <el-input v-model="queryParams.equipMaintainDetailId" placeholder="请输入保养内容id" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="保养内容" prop="equipMaintainDetail">
        <el-input v-model="queryParams.equipMaintainDetail" placeholder="请输入保养内容" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="是否特殊设备【字典：0否；1是】" prop="isSpecial">
        <el-input v-model="queryParams.isSpecial" placeholder="请输入是否特殊设备【字典：0否；1是】" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="设备档案id" prop="equipmentprofileId">
        <el-input v-model="queryParams.equipmentprofileId" placeholder="请输入设备档案id" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="设备档案编码" prop="equipmentprofileCode">
        <el-input v-model="queryParams.equipmentprofileCode" placeholder="请输入设备档案编码" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="设备名称" prop="equipName">
        <el-input v-model="queryParams.equipName" placeholder="请输入设备名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="保养周期" prop="maintainCycle">
        <el-input v-model="queryParams.maintainCycle" placeholder="请输入保养周期" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="是否更换自身【字典：0否；1是】" prop="replaceSelf">
        <el-input v-model="queryParams.replaceSelf" placeholder="请输入是否更换自身【字典：0否；1是】" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="保养图片【地址，中间以-_-隔开】" prop="resultPhotos">
        <el-input v-model="queryParams.resultPhotos" placeholder="请输入保养图片【地址，中间以-_-隔开】" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="保养日期" prop="maintainDate">
        <el-date-picker v-model="queryParams.maintainDate" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="计划执行部门id【从保养计划中来】" prop="executeDeptId">
        <el-input v-model="queryParams.executeDeptId" placeholder="请输入计划执行部门id【从保养计划中来】" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="计划执行部门名称【从保养计划中来】" prop="executeDeptName">
        <el-input v-model="queryParams.executeDeptName" placeholder="请输入计划执行部门名称【从保养计划中来】" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="实际保养人" prop="actualMaintainNames">
        <el-input v-model="queryParams.actualMaintainNames" placeholder="请输入实际保养人" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="queryParams.remark" placeholder="请输入备注" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建者" prop="creator">
        <el-input v-model="queryParams.creator" placeholder="请输入创建者" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="更新者" prop="updater">
        <el-input v-model="queryParams.updater" placeholder="请输入更新者" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-date-picker v-model="queryParams.updateTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
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
                   v-hasPermi="['oam:maintain-profile:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['oam:maintain-profile:export']">导出</el-button>
      </el-col>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

            <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
            <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="保养计划id" align="center" prop="equipMaintainPlanId" />
      <el-table-column label="保养计划名称" align="center" prop="equipMaintainPlanName" />
      <el-table-column label="保养内容id" align="center" prop="equipMaintainDetailId" />
      <el-table-column label="保养内容" align="center" prop="equipMaintainDetail" />
      <el-table-column label="是否特殊设备【字典：0否；1是】" align="center" prop="isSpecial" />
      <el-table-column label="设备档案id" align="center" prop="equipmentprofileId" />
      <el-table-column label="设备档案编码" align="center" prop="equipmentprofileCode" />
      <el-table-column label="设备名称" align="center" prop="equipName" />
      <el-table-column label="保养周期" align="center" prop="maintainCycle" />
      <el-table-column label="是否更换自身【字典：0否；1是】" align="center" prop="replaceSelf" />
      <el-table-column label="保养图片【地址，中间以-_-隔开】" align="center" prop="resultPhotos" />
      <el-table-column label="保养日期" align="center" prop="maintainDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.maintainDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划执行部门id【从保养计划中来】" align="center" prop="executeDeptId" />
      <el-table-column label="计划执行部门名称【从保养计划中来】" align="center" prop="executeDeptName" />
      <el-table-column label="实际保养人" align="center" prop="actualMaintainNames" />
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
          <el-button size="mini" type="text" icon="el-icon-edit" @click="openForm(scope.row.id)"
                     v-hasPermi="['oam:maintain-profile:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['oam:maintain-profile:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>
    <!-- 对话框(添加 / 修改) -->
    <MaintainProfileForm ref="formRef" @success="getList" />
    </div>
</template>

<script>
import * as MaintainProfileApi from '@/api/oam/maintainprofile';
import MaintainProfileForm from './MaintainProfileForm.vue';
export default {
  name: "MaintainProfile",
  components: {
          MaintainProfileForm,
  },
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
      // 保养日志表列表
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
        id: null,
        equipMaintainPlanId: null,
        equipMaintainPlanName: null,
        equipMaintainDetailId: null,
        equipMaintainDetail: null,
        isSpecial: null,
        equipmentprofileId: null,
        equipmentprofileCode: null,
        equipName: null,
        maintainCycle: null,
        replaceSelf: null,
        resultPhotos: null,
        maintainDate: [],
        executeDeptId: null,
        executeDeptName: null,
        actualMaintainNames: null,
        remark: null,
        creator: null,
        createTime: [],
        updater: null,
        updateTime: [],
      },
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
              const res = await MaintainProfileApi.getMaintainProfilePage(this.queryParams);
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
      await this.$modal.confirm('是否确认删除保养日志表编号为"' + id + '"的数据项?')
      try {
       await MaintainProfileApi.deleteMaintainProfile(id);
       await this.getList();
       this.$modal.msgSuccess("删除成功");
      } catch {}
    },
    /** 导出按钮操作 */
    async handleExport() {
      await this.$modal.confirm('是否确认导出所有保养日志表数据项?');
      try {
        this.exportLoading = true;
        const res = await MaintainProfileApi.exportMaintainProfileExcel(this.queryParams);
        this.$download.excel(res, '保养日志表.xls');
      } catch {
      } finally {
        this.exportLoading = false;
      }
    },
              }
};
</script>