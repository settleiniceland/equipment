<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="98px">
      <el-form-item label="设备名称" prop="equipName">
        <el-input v-model="queryParams.equipName" placeholder="请输入设备名称" clearable/>
      </el-form-item>
      <el-form-item label="设备编码" prop="equipCode">
        <el-input v-model="queryParams.equipCode" placeholder="请输入设备编码" clearable/>
      </el-form-item>
      <el-form-item label="类型" prop="operationType">
        <el-select v-model="queryParams.operationType" placeholder="请选择类型" clearable filterable size="small">
          <el-option v-for="sr in statusRecordOperationTypeDict" :key="sr.id" :label="sr.name" :value="sr.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="改变详情" prop="changeDetails">
        <el-select v-model="changeDetailsList" multiple placeholder="请选择">
            <el-option
                v-for="item in changeDetailsDict"
                :key="parseInt(item.id)"
                :label="item.name"
                :value="parseInt(item.id)"
            ></el-option>
          </el-select>
      </el-form-item>
      <br>
      <el-form-item label="启停状态" prop="newStatus1">
        <el-select v-model="queryParams.newStatus1" placeholder="请选择启停状态" clearable filterable size="small">
          <el-option v-for="s1 in statusRecordStatus1Dict" :key="s1.id" :label="s1.name" :value="s1.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="其他状态" prop="newStatus2">
        <el-select v-model="queryParams.newStatus2" placeholder="请选择其他状态" clearable filterable size="small">
          <el-option v-for="s2 in statusRecordStatus2Dict" :key="s2.id" :label="s2.name" :value="s2.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="详情" prop="details">
        <el-input v-model="queryParams.details" placeholder="请输入详情" clearable/>
      </el-form-item>
      <el-form-item label="操作时间" prop="operationTime">
        <el-date-picker v-model="queryParams.operationTime" style="width: 194px" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <br>
      <el-form-item :label='$t("message.Button.creator")' prop="creator">
        <el-input v-model="queryParams.creator" placeholder="请输入创建者" clearable/>
      </el-form-item>
      <el-form-item :label='$t("message.Button.createTime")' prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 194px" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item :label='$t("message.Button.modifier")' prop="updater">
        <el-input v-model="queryParams.updater" placeholder="请输入更新者" clearable/>
      </el-form-item>
      <el-form-item :label='$t("message.Button.updateTime")' prop="updateTime">
        <el-date-picker v-model="queryParams.updateTime" style="width: 194px" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">{{ $t("message.Button.search") }}</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">{{ $t("message.Button.origin") }}</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['oam:stop:export']">{{ $t("message.Button.export") }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="list" :show-overflow-tooltip="true">
      <el-table-column label="设备名称" align="center" prop="equipName" />
      <el-table-column label="设备编码" align="center" prop="equipCode" />
      <el-table-column label="类型" align="center" prop="operationTypeName" />
      <el-table-column label="改变详情" align="center" prop="changeDetailsName" />
      <el-table-column label="启停状态" align="center" prop="newStatus1Name" />
      <el-table-column label="其他状态" align="center" prop="newStatus2Name" />
      <el-table-column label="详情" align="center" prop="details" />
      <el-table-column label="操作时间" align="center" prop="operationTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.operationTime) }}</span>
        </template>
      </el-table-column>
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
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['oam:stop:delete']">{{ $t("message.Button.del") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize" @pagination="getList"/>
    </div>
</template>
<script>
import * as StatusRecordApi from '@/api/oam/statusrecord';
export default {
  name: "StatusRecord",
  components: {},
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
      // 停机表列表
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
        equipmentprofileId: null,
        equipName: null,
        equipCode: null,
        operationType: null,
        operationTime: [],
        changeDetails: null,
        newStatus1: null,
        newStatus2: null,
        details: null,
        creator: null,
        createTime: [],
        updater: null,
        updateTime: [],
      },
      //操作类型字典
      statusRecordOperationTypeDict:[
        {id:1, name:'点检结果异常'},
        {id:2, name:'手动操作'}
      ],
      //启停状态字典
      statusRecordStatus1Dict:[
        {id:1, name:'开机'},
        {id:2, name:'停机'}
      ],
      //其他状态字典
      statusRecordStatus2Dict:[
        {id:3, name:'异动中'},
        {id:4, name:'异动完毕'},
        {id:5, name:'回国返修中'},
        {id:6, name:'回国返修完毕'},
        {id:7, name:'报废'}
      ],
      changeDetailsList:[],//改变详情数组
      //改变详情字典
      changeDetailsDict:[
        {id:1, name:'启停状态改变'},
        {id:2, name:'其他状态改变'}
      ],
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
        if(this.changeDetailsList.length!==0){
          let changeDetail1 = "";
          let changeDetail2 = "";
          this.changeDetailsList.forEach(detail => {
            if(detail === 1){
              changeDetail1 = "1";
            }else if(detail === 2){
              changeDetail2 = "1";
            }
          });
          this.queryParams.changeDetails = changeDetail1 + "_" + changeDetail2;
        }else{
          this.queryParams.changeDetails = null;
        }
        const res = await StatusRecordApi.getStopPage(this.queryParams);
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
    /** 删除按钮操作 */
    async handleDelete(row) {
      const id = row.id;
      await this.$modal.confirm('是否确认删除停机表编号为"' + id + '"的数据项?')
      try {
       await StatusRecordApi.deleteStop(id);
       await this.getList();
       this.$modal.msgSuccess("删除成功");
      } catch {}
    },
    /** 导出按钮操作 */
    async handleExport() {
      await this.$modal.confirm('是否确认导出所有停机表数据项?');
      try {
        this.exportLoading = true;
        const res = await StatusRecordApi.exportStopExcel(this.queryParams);
        this.$download.excel(res, '设备状态记录.xls');
      } catch {
      } finally {
        this.exportLoading = false;
      }
    },
    //每一层的颜色
    getRowClassName({ row }) {
      if(row.endTime === null || row.endTime === undefined){
        return 'stop-level-1';
      }else{
        return 'stop-level-2';
      }
    },
  }
};
</script>
<style lang="scss">
   .stop-level-1 {
    background-color: #e04747 !important;
  }
   .stop-level-2 {
    background-color: #72e655 !important;
  }
</style>