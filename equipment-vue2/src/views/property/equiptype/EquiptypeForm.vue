<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="45%" v-dialogDrag append-to-body>
      <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="100px">
        <el-form-item label="类别编码" prop="code">
          <el-input v-model="formData.code" placeholder="请输入设备类别编码" />
        </el-form-item>
        <el-form-item label="类别名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入设备类别名称" />
        </el-form-item>
        <el-form-item label="上级类别" prop="supId">
          <TreeSelect
            v-model="formData.supId"
            :options="equiptypeTree"
            :normalizer="normalizer"
            placeholder="请选择上级类别"
          />
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
  import * as EquiptypeApi from '@/api/property/equiptype';
  import TreeSelect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  export default {
    name: "EquiptypeForm",
    components: {TreeSelect,},
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
          code: undefined,
          name: undefined,
          supId: undefined,
        },
        // 表单校验
        formRules: {
          code: [{ required: true, message: '设备类别编码不能为空', trigger: 'blur' }],
          name: [{ required: true, message: '设备类别名称不能为空', trigger: 'blur' }],
          supId: [{ required: true, message: '父类别id不能为空', trigger: 'blur' }],
        },
        equiptypeTree: [], // 树形结构
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
            const res = await EquiptypeApi.getEquiptype(id);
            this.formData = res.data;
            this.dialogTitle = "修改设备类别";
          } finally {
            this.formLoading = false;
          }
        }else{
          this.dialogTitle = "新增设备类别";
        }
        await this.getEquiptypeTree();
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
            await EquiptypeApi.updateEquiptype(data);
            this.$modal.msgSuccess("修改成功");
            this.dialogVisible = false;
            this.$emit('success');
            return;
          }
          // 添加的提交
          await EquiptypeApi.createEquiptype(data);
          this.$modal.msgSuccess("新增成功");
          this.dialogVisible = false;
          this.$emit('success');
        } finally {
          this.formLoading = false;
        }
      },
      /** 获得设备类别树 */
      async getEquiptypeTree() {
        this.equiptypeTree = [];
        const res = await EquiptypeApi.getEquiptypeList();
        const root = { id: 0, name: '---', children: [] };
        root.children = this.handleTreeForString(res.data, 'id', 'supId');
        this.equiptypeTree.push(root);
      },
      /** 转换设备类别数据结构 */
      normalizer(node) {
        return this.myNormalizer(node,'id','name');
      },
      /** 表单重置 */
      reset() {
        this.formData = {
          code: undefined,
          name: undefined,
          supId: undefined,
        };
        this.resetForm("formRef");
      }
    }
  };
</script>
