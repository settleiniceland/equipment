<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="equipName">
        <el-input v-model="queryParams.equipName" placeholder="请输入设备名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="设备规格" prop="equipSpecification">
        <el-input v-model="queryParams.equipSpecification" placeholder="请输入设备规格" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="设备属性" prop="equipAttribute">
        <el-select v-model="queryParams.equipAttribute" placeholder="请选择设备属性" clearable size="small">
          <el-option v-for="dict in equipAttributeDictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
        </el-select>
      </el-form-item>
      <el-form-item label="设备类别" prop="equiptypeId">
          <TreeSelect
            v-model="queryParams.equiptypeId"
            :options="equiptypeTree"
            :normalizer="normalizer"
            placeholder="请选择设备类别"
            class="treeSelectCSS"
          />
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
                   v-hasPermi="['property:equip:create']">{{ $t("message.Button.add") }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['property:equip:export']">{{ $t("message.Button.export") }}</el-button>
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
      :show-overflow-tooltip="true"
      v-if="refreshTable"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      :row-class-name="getRowClassName"
      >
      <el-table-column label="上级设备名称" align="center" prop="supName" />
      <el-table-column label="设备名称" align="center" prop="equipName" />
      <el-table-column label="设备属性" align="center" prop="equipAttributeName" />
      <el-table-column label="设备类别名称" align="center" prop="equiptypeName" />
      <el-table-column label="设备规格" align="center" prop="equipSpecification" />
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
                     v-hasPermi="['property:equip:update']">{{ $t("message.Button.update") }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['property:equip:delete']">{{ $t("message.Button.del") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 对话框(添加 / 修改) -->
    <EquipForm ref="formRef" :equipAttributeDictDatas="equipAttributeDictDatas" @success="getList" />
    </div>
</template>

<script>
  import * as EquipApi from '@/api/property/equip';
  import EquipForm from './EquipForm.vue';
  import * as EquiptypeApi from '@/api/property/equiptype';
  import TreeSelect from "@riophae/vue-treeselect";
  export default {
    name: "Equip",
    components: {EquipForm,TreeSelect,},
    data() {
      return {
        // 遮罩层
        loading: true,
        // 导出遮罩层
        exportLoading: false,
        // 显示搜索条件
        showSearch: true,
        // 设备表列表
        list: [],
        // 是否展开，默认全部展开
        isExpandAll: true,
        // 重新渲染表格状态
        refreshTable: true,
        // 选中行
        currentRow: {},
        // 查询参数
        queryParams: {
          equipName: null,
          equipSpecification: null,
          equipAttribute: null,
          equiptypeId: null,
        },
        //设备属性字典【字典，1代表设备组；2代表单个完整设备；3代表设备组件】
        equipAttributeDictDatas:[
          {value:1,label:'设备组'},
          {value:2,label:'设备'},
          {value:3,label:'设备组件'}
        ],
        //设备类别树形结构
        equiptypeTree:[],
        // 工具map键值对对象
        listMap: new Map(),
      };
    },
    created() {
      this.getList();
      this.getEquiptypeTree();
    },
    methods: {
      /** 查询列表 */
      async getList() {
        try {
          this.loading = true;
          const res = await EquipApi.getEquipList(this.queryParams);
          res.data.forEach(element => {
            this.listMap.set(element.id,element);
          });
          res.data.forEach(element => {
            for(const item of this.equipAttributeDictDatas){
              if(item.value === element.equipAttribute){
                element.equipAttributeName=item.label;
                break;
              }
            }
            if(element.supId!=="0"){
              const f = this.listMap.get(element.supId);
              element.supName=f?f.equipName:"";
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
        await this.$modal.confirm('是否确认删除设备《' + row.equipName + '》?')
        try {
        await EquipApi.deleteEquip(row.id);
        await this.getList();
        this.$modal.msgSuccess("删除成功");
        } catch {}
      },
      /** 导出按钮操作 */
      async handleExport() {
        await this.$modal.confirm('是否确认导出所有设备表数据项?');
        try {
          this.exportLoading = true;
          const res = await EquipApi.exportEquipExcel(this.queryParams);
          this.$download.excel(res, '设备表.xls');
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
      },
      /** 构建设备类型树 */
      async getEquiptypeTree(){
        this.equiptypeTree = [];
        const res = await EquiptypeApi.getEquiptypeList();
        this.equiptypeTree = this.handleTreeForString(res.data, 'id', 'supId');
      },
      /** 转换设备类别数据结构 */
      normalizer(node) {
        return this.myNormalizer(node,'id','name')
      },
      //每一层的颜色
      getRowClassName({ row }) {
        return `equip-level-${row.equipAttribute}`;
      },
    },
  };
</script>
<style lang="scss" >
  .equip-level-1 {
    background-color: #ffffff !important;
  }
  .equip-level-2 {
    background-color: #45cc57 !important;
  }
  .equip-level-3 {
    background-color: #ceec49 !important;
  }
</style>