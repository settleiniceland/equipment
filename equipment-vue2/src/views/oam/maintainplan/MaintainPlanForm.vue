<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="45%" v-dialogDrag append-to-body>
      <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="100px">
        <el-form-item label="计划名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入计划名称" />
        </el-form-item>
        <el-form-item label="执行部门" prop="executeDeptId">
          <TreeSelect
            v-model="formData.executeDeptId"
            :options="deptTree"
            :normalizer="normalizer"
            placeholder="请选择执行部门"
            @input="executeDeptHandleSelect"
          />
        </el-form-item>
        <el-form-item label="计划状态" prop="status">
          <el-select v-model="formData.status" placeholder=状态 clearable size="small">
            <el-option v-for="dict in statusDictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" :rows="9" v-model="formData.remark" :placeholder='$t("message.Button.remark")' />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :disabled="formLoading">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import * as MaintainPlanApi from '@/api/oam/maintainplan';
  import * as EquipmentprofileApi from '@/api/property/equipmentprofile';
  import TreeSelect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  export default {
    name: "MaintainPlanForm",
    components: {TreeSelect},
    data() {
      return {
        // 弹出层标题
        dialogTitle: "",
        // 是否显示弹出层
        dialogVisible: false,
        // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
        formLoading: false,
        // 表单参数
        formData: {
          id: undefined,
          name: undefined,
          executeDeptId: undefined,
          executeDeptName: undefined,
          status: 0,
          remark: undefined,
        },
        // 表单校验
        formRules: {
          name: [{ required: true, message: '计划名称不能为空', trigger: 'blur' }],
          executeDeptId: [{ required: true, message: '执行部门id不能为空', trigger: 'blur' }],
          executeDeptName: [{ required: true, message: '执行部门名称不能为空', trigger: 'blur' }],
          status: [{ required: true, message: '计划状态不能为空', trigger: 'blur' }],
        },
        deptTree:[],//部门树
      };
    },
    //传递来的参数
    props:{
      statusDictDatas:{type:Array,required:true}
    },
    methods: {
      /** 打开弹窗 */
      async open(id) {
        this.dialogVisible = true;
        this.getAllTree();
        this.reset();
        // 修改时，设置数据
        if (id) {
          this.formLoading = true;
          try {
            const res = await MaintainPlanApi.getMaintainPlan(id);
            this.formData = res.data;
            this.dialogTitle = "修改保养计划基本信息";
          } finally {
            this.formLoading = false;
          }
        }else{
          this.dialogTitle = "新增保养计划";
        }
      },
      /** 提交按钮 */
      async submitForm() {
        // 校验主表
        await this.$refs["formRef"].validate();
        this.formLoading = true;
        try {
          const data = this.formData;
          if (data.id) {// 修改的提交
            await MaintainPlanApi.updateMaintainPlan(data);
            this.$modal.msgSuccess("修改成功");
            this.dialogVisible = false;
            this.$emit('success');
            return;
          }else{// 添加的提交
            await MaintainPlanApi.createMaintainPlan(data);
            this.$modal.msgSuccess("新增成功");
            this.dialogVisible = false;
            this.$emit('success');
          }
        } finally {
          this.formLoading = false;
        }
      },
      /** 表单重置 */
      reset() {
        this.formData = {
          id: undefined,
          name: undefined,
          executeDeptId: undefined,
          executeDeptName: undefined,
          status: 0,
          remark: undefined,
        };
        this.resetForm("formRef");
      },
      async getAllTree(){
        //部门树型结构
        this.deptTree = [];
        const deptRes = await EquipmentprofileApi.getAllDeptList();
        this.deptTree = this.handleTree(deptRes.data,'id');
      },
      /** 转换传统数据数据结构 */
      normalizer(node) {
        return this.myNormalizer(node,'id','name');
      },
      /** 选中部门后自动给部门名称赋值 */
      executeDeptHandleSelect(selectedValue){
        const selectNode = this.findNodeById(this.deptTree, selectedValue);
        if(selectNode){
          this.formData.executeDeptName = selectNode.name;
        }
      },
    }
  };
</script>
