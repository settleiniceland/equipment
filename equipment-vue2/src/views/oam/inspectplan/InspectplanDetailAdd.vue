<template>
    <div class="app-container">
      <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" @close="handleDialogClose" width="53%" v-dialogDrag append-to-body>
        <!-- 搜索栏 -->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
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
        <!-- 工具栏 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="addSubstance"
                      v-hasPermi="['oam:inspectplan:create']">添加</el-button>
          </el-col>
        </el-row>
        <!-- 展示栏 -->
        <el-table v-loading="loading" :data="list" :show-overflow-tooltip="true" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55"/>
          <el-table-column label="设备名称" align="center" prop="equipName" width="180"/>
          <el-table-column label="设备规格" align="center" prop="equipSpecification" show-overflow-tooltip/>
          <el-table-column label="点检内容" align="center" prop="details" width="180"/>
          <el-table-column :label='$t("message.Button.creator")' align="center" prop="creator" show-overflow-tooltip/>
          <el-table-column :label='$t("message.Button.createTime")' align="center" prop="createTime" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column :label='$t("message.Button.modifier")' align="center" prop="updater" show-overflow-tooltip/>
          <el-table-column :label='$t("message.Button.updateTime")' align="center" prop="updateTime" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.updateTime) }}</span>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页组件 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize" @pagination="getList"/>
      </el-dialog>
    </div>
  </template>
  
  <script>
    import * as InspectionSubstanceApi from '@/api/oam/inspectionsubstance';
    import * as EquipApi from '@/api/property/equip';
    import * as InspectplanApi from '@/api/oam/inspectplan';
    import TreeSelect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    export default {
      name:'InspectplanDetail',
      components: {TreeSelect},
      data() {
        return {
          // 遮罩层
          loading: true,
          // 弹出层标题
          dialogTitle: "",
          // 是否显示弹出层
          dialogVisible: false,
          // 总条数
          total: 0,
          // 点检内容列表
          list: [],
          // 查询参数
          queryParams: {
            pageNo: 1,
            pageSize: 10,
            planId: null,
            equipId: null,
            details: null,
          },
          equipTree: [], // 设备树形结构
          addSubstanceIds: [], // 要添加的点检内容id 
        };
      },
      props: {
  
      },
      methods: {
        async open(id,name){
          this.reset(); 
          this.queryParams.planId = id;
          this.dialogVisible = true;
          this.dialogTitle = "点检计划《" + name + "》内容添加";
          this.getAllTree();
          this.getList();
        },
        async getList(){
          try {
            this.loading = true;
            const res = await InspectionSubstanceApi.getInspectionSubstanceAddPageByPlan(this.queryParams);
            this.list = res.data.list;
            this.total = res.data.total;
          } finally {
            this.loading = false;
          }
        },
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
        /** 初始化查询条件 */
        reset(){
            this.queryParams = {
                pageNo: 1,
                pageSize: 10,
                planId: null,
                equipId: null,
                details: null,
            }
            this.resetForm("queryForm");
            this.addSubstanceIds = [];
        },
        /** 选中事件 */
        handleSelectionChange(val){
            this.addSubstanceIds = [];
            val.forEach(item => {
                this.addSubstanceIds.push(item.id)
            })
        },
        /** 点检计划添加点检内容 */
        async addSubstance(){
            await InspectplanApi.addSubstances(this.queryParams.planId,this.addSubstanceIds);
            this.getList();
            this.addSubstanceIds = [];
        },
        /** 弹窗关闭事件 */
        handleDialogClose(){
            this.$emit('success');
        }
      }
    };          
  </script>