<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="45%" :style="{ minHeight: '100vh' }" v-dialogDrag append-to-body>
      <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="100px">
        <el-form-item label="设备" prop="equipName">
          <TreeSelect
            v-model="formData.equipId"
            :options="equipTree"
            :normalizer="normalizer1"
            placeholder="请选择设备"
            @input="equipHandleSelect"
          />
        </el-form-item>
        <el-form-item label="点检内容" prop="details">
          <el-input type="textarea" :rows="9" v-model="formData.details" placeholder="请输入点检内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :disabled="formLoading">{{ $t("message.Button.determine") }}</el-button>
        <el-button @click="dialogVisible = false">{{ $t("message.Button.cancel") }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import * as InspectionSubstanceApi from '@/api/oam/inspectionsubstance';
  import * as EquipApi from '@/api/property/equip';
  import TreeSelect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  export default {
    name: "InspectionSubstanceForm",
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
          equipId: undefined,
          equipName: undefined,
          equipSpecification: undefined,
          details: undefined,
          deptId: undefined,
        },
        // 表单校验
        formRules: {
          equipId: [{ required: true, message: '设备id不能为空', trigger: 'blur' }],
          equipName: [{ required: true, message: '设备名称不能为空', trigger: 'blur' }],
          details: [{ required: true, message: '点检内容不能为空', trigger: 'blur' }],
        },
        equipTree: [], // 设备树形结构
      };
    },
    methods: {
      /** 打开弹窗 */
     async open(id) {
        this.dialogVisible = true;
        this.reset();
        // 修改时，设置数据
        if (id) {
          this.formLoading = true;
          try {
            const res = await InspectionSubstanceApi.getInspectionSubstance(id);
            this.formData = res.data;
            this.dialogTitle = "修改点检内容";
          } finally {
            this.formLoading = false;
          }
        }else{
          this.dialogTitle = "新增点检内容";
        }
        this.getAllTree();
      },
      /** 提交按钮 */
      async submitForm() {
        // 校验主表
        await this.$refs["formRef"].validate();
        this.formLoading = true;
        try {
          const data = this.formData;
          // 修改的提交
          if (data.id) {
            await InspectionSubstanceApi.updateInspectionSubstance(data);
            this.$modal.msgSuccess("修改成功");
            this.dialogVisible = false;
            this.$emit('success');
            return;
          }else{
            // 添加的提交
            await InspectionSubstanceApi.createInspectionSubstance(data);
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
          equipId: undefined,
          equipName: undefined,
          equipSpecification: undefined,
          details: undefined,
          deptId: undefined,
        };
        this.resetForm("formRef");
      },
      /** 获取所有所需要的树型结构 */
      async getAllTree(){
        //设备树形结构
        this.equipTree = [];
        const res = await EquipApi.getEquipList();
        this.equipTree = this.handleTreeForString(res.data, 'id', 'supId');
      },
      /** 转换设备表数据结构 */
      normalizer1(node) {
        return this.myNormalizer(node,"id","equipName");
      },
      /** 选中设备项后给某些值做的自动赋值操作 */
      async equipHandleSelect(selectedValue){
        const selectNode = this.findNodeById(this.equipTree, selectedValue);
        this.formData.equipName = selectNode.equipName;
        this.formData.equipSpecification = selectNode.equipSpecification;
      }
    }
  };
</script>
