<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label='$t("message.Manufacturer.manufacturer_name")' prop="name">
        <el-input v-model="queryParams.name" :placeholder='$t("message.Manufacturer.manufacturer_name")' clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item :label='$t("message.Manufacturer.manufacturer_code")' prop="code">
        <el-input v-model="queryParams.code" :placeholder='$t("message.Manufacturer.manufacturer_code")' clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item :label='$t("message.Manufacturer.manufacturer_status")' prop="status">
        <el-select v-model="queryParams.status" :placeholder='$t("message.Manufacturer.manufacturer_status")' clearable size="small">
          <el-option v-for="dict in statusDictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
        </el-select>
      </el-form-item>
      <el-form-item :label='$t("message.Button.remark")' prop="remark">
        <el-input v-model="queryParams.remark" :placeholder='$t("message.Button.remark")' clearable @keyup.enter.native="handleQuery"/>
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
                   v-hasPermi="['property:manufacturer:create']">{{ $t("message.Button.add") }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['property:manufacturer:export']">{{ $t("message.Button.export") }}</el-button>
      </el-col>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据展示栏 -->
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column :label='$t("message.Manufacturer.manufacturer_name")' align="center" prop="name" />
      <el-table-column :label='$t("message.Manufacturer.manufacturer_code")' align="center" prop="code" />
      <el-table-column :label='$t("message.Manufacturer.manufacturer_status")' align="center" prop="status">
        <template v-slot="scope">
          <el-switch v-model="scope.row.status" :active-value="0" :inactive-value="1" @change="handleStatusChange(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column :label='$t("message.Button.remark")' align="center" prop="remark" show-overflow-tooltip/>
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
                     v-hasPermi="['property:manufacturer:update']">{{ $t("message.Button.update") }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['property:manufacturer:delete']">{{ $t("message.Button.del") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>
    <!-- 对话框(添加 / 修改) -->
    <ManufacturerForm ref="formRef" :statusDictDatas="statusDictDatas"  @success="getList"/>
    </div>
</template>

<script>
  import * as ManufacturerApi from '@/api/property/manufacturer';
  import ManufacturerForm from './ManufacturerForm.vue';
  import {CommonStatusEnum} from "@/utils/constants";
  export default {
    name: "Manufacturer",
    components: {ManufacturerForm,},
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
        // 设备生产厂家信息列表
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
          code: null,
          status: null,
          remark: null,
        },
        //固定厂家状态;0正常；1禁用
        statusDictDatas:[
          {value:0,label: this.$t("message.Button.normal")},
          {value:1,label: this.$t("message.Button.disable")}
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
                const res = await ManufacturerApi.getManufacturerPage(this.queryParams);
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
        await this.$modal.confirm('是否确认删除设备生产厂家【"' + row.name + '"】?')
        try {
        await ManufacturerApi.deleteManufacturer(row.id);
        await this.getList();
        this.$modal.msgSuccess( this.$t("message.Button.del_success") );
        } catch {}
      },
      /** 导出按钮操作 */
      async handleExport() {
        await this.$modal.confirm('是否确认导出所有设备生产厂家信息数据项?');
        try {
          this.exportLoading = true;
          const res = await ManufacturerApi.exportManufacturerExcel(this.queryParams);
          this.$download.excel(res, '设备生产厂家信息.xls');
        } catch {
        } finally {
          this.exportLoading = false;
        }
      },
      // 厂家状态更改
      async handleStatusChange(row){
        let text = row.status === CommonStatusEnum.ENABLE ? "启用" : "停用";
        this.$modal.confirm('确认要"' + text + '""' + row.name + row.code + '"吗?').then(function() {
          return ManufacturerApi.updateManufacturer(row);
        }).then(() => {
          this.$modal.msgSuccess(text + "成功");
        }).catch(function() {
          row.status = row.status === CommonStatusEnum.ENABLE ? CommonStatusEnum.DISABLE
              : CommonStatusEnum.ENABLE;
        });
      }
    }
  };
</script>
