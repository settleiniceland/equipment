<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="点检类型" prop="inspectionType">
        <el-select v-model="queryParams.inspectionType" placeholder="请选择点检类型" clearable size="small">
          <el-option v-for="type in typeInspectplan" :key="parseInt(type.value)" :label="type.label" :value="parseInt(type.value)"/>
        </el-select>
      </el-form-item>
      <el-form-item label="计划状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择计划状态" clearable size="small">
          <el-option v-for="status in statusInspectplan" :key="parseInt(status.value)" :label="status.label" :value="parseInt(status.value)"/>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入点检计划名称" clearable @keyup.enter.native="handleQuery"/>
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
      <el-form-item label="设备" prop="equipId">
        <el-select v-model="queryParams.equipId" placeholder="请选择设备" clearable size="small">
          <el-option v-for="equip in equipList" :key="equip.id" :label="equip.equipName" :value="equip.id"/>
        </el-select>
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
                   v-hasPermi="['oam:inspectplan:create']">{{ $t("message.Button.add") }}</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['oam:inspectplan:export']">{{ $t("message.Button.export") }}</el-button>
      </el-col> -->
      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>

    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="类型" align="center" prop="inspectionTypeName" />
      <el-table-column label="周期【单位h】" align="center" prop="inspectionCycle" />
      <el-table-column label="计划名称" align="center" prop="name" />
      <el-table-column label="计划状态" align="center" prop="status">
        <template v-slot="scope">
          <el-switch v-model="scope.row.status" :active-value="0" :inactive-value="1" @change="handleStatusChange(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column label="区域" align="center" prop="equiplocationName" show-overflow-tooltip />
      <el-table-column label="设备" align="center" prop="equipName" show-overflow-tooltip />
      <el-table-column label="备注" align="center" prop="detail" show-overflow-tooltip />
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
          <el-button size="mini" type="text" icon="el-icon-notebook-1" @click="openDetailForm(scope.row.id,scope.row.name,scope.row.equipId)"
                     v-hasPermi="['oam:inspectplan:update']">计划内容</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="openForm(scope.row.id)"
                     v-hasPermi="['oam:inspectplan:update']">修改基本信息</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['oam:inspectplan:delete']">{{ $t("message.Button.del") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize" @pagination="getList"/>
    <!-- 对话框(添加 / 修改) -->
    <InspectplanForm ref="formRef" @success="getList" :typeInspectplan="typeInspectplan" :statusInspectplan="statusInspectplan"/>
    <!-- 对话框(计划内容) -->
    <InspectplanDetail ref="tableDetail" />
  </div>
</template>

<script>
  import * as InspectplanApi from '@/api/oam/inspectplan';
  import * as InstalllocationApi from '@/api/property/installlocation';
  import * as EquipApi from '@/api/property/equip';
  import InspectplanForm from './InspectplanForm.vue';
  import InspectplanDetail from './InspectplanDetail.vue';
  import TreeSelect from "@riophae/vue-treeselect";
  import {CommonStatusEnum} from "@/utils/constants";
  export default {
    name: "Inspectplan",
    components: {InspectplanForm,TreeSelect,InspectplanDetail},
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
        // 点检计划表列表
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
          status: null,
          inspectionType: null,
          inspectionCycle: null,
          name: null,
          equiplocationId: null,
          equiplocationName: null,
          equipId: null,
          detail: null,
        },
        //固定点检计划状态；1启用，2禁用
        statusInspectplan:[
          {value:0,label:'启动'},
          {value:1,label:'禁用'}
        ],
        //固定点检计划类型；1普通点检，2重点点检
        typeInspectplan:[
          {value:1,label:'普通点检'},
          {value:2,label:'重点点检'},
          {value:3,label:'专项点检'},
          {value:4,label:'拆检'}
        ],
        installlocationTree:[],//设备安装位置树形结构
        equipList: [],//设备待选列表
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
          const res = await InspectplanApi.getInspectplanPage(this.queryParams);
          this.list = res.data.list;
          this.total = res.data.total;
          this.list.forEach(element => {
            for(const item of this.typeInspectplan){
              if(item.value === element.inspectionType){
                element.inspectionTypeName = item.label;
              }
            }
          })
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
        await this.$modal.confirm('是否确认删除点检计划"' + row.name + '"?')
        try {
          await InspectplanApi.deleteInspectplan(row.id,row.status);
          await this.getList();
          this.$modal.msgSuccess("删除成功");
        } catch {}
      },
      /** 导出按钮操作 */
      // async handleExport() {
      //   await this.$modal.confirm('是否确认导出所有点检计划表数据项?');
      //   try {
      //     this.exportLoading = true;
      //     const res = await InspectplanApi.exportInspectplanExcel(this.queryParams);
      //     this.$download.excel(res, '点检计划表.xls');
      //   } catch {
      //   } finally {
      //     this.exportLoading = false;
      //   }
      // },
      /** 转换设备类型数据结构 */
      normalizer1(node){
        return this.myNormalizer(node,"id","name");
      },
      /** 获取用的所有的树型结构 */
      async getAllTree(){
        this.installlocationTree = [];
        const InstalllocationRes = await InstalllocationApi.getInstalllocationList();
        this.installlocationTree = this.handleTreeForString(InstalllocationRes.data,'id','supId');//构建设备位置树
        this.equipList = [];
        const equipRes = await EquipApi.getEquipList({equipAttribute: 2,});
        this.equipList = equipRes.data;
      },
      /** 进入计划内容 */
      openDetailForm(id,name,equipId) {
        this.$refs["tableDetail"].open(id,name,equipId);
      },
      /** 计划状态更改 */
      async handleStatusChange(row){
        let text = row.status === CommonStatusEnum.ENABLE ? "启用" : "停用";
        this.$modal.confirm('确认要"' + text + '"点巡检计划《"' + row.name + '"》吗?').then(function() {
          return InspectplanApi.updateInspectplan(row);
        }).then(() => {
          this.$modal.msgSuccess(text + "成功");
        }).catch(function() {
          row.status = row.status === CommonStatusEnum.ENABLE ? CommonStatusEnum.DISABLE : CommonStatusEnum.ENABLE;
        });
      }
    }
  };
</script>
