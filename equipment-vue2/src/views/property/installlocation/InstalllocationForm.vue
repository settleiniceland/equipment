<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="45%" v-dialogDrag append-to-body>
      <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="100px">
        <el-form-item label="地区编码" prop="code">
          <el-input v-model="formData.code" placeholder="请输入地区编码" />
        </el-form-item>
        <el-form-item label="父地区" prop="supId">
          <TreeSelect
            v-model="formData.supId"
            :options="installlocationTree"
            :normalizer="normalizer"
            placeholder="请选择父地区"
          />
        </el-form-item>
        <el-form-item label="地区名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入地区名称" />
        </el-form-item>
        <el-form-item label="负责人" prop="dutyName">
          <el-input v-model="formData.dutyName" placeholder="请输入负责人" />
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
  import * as InstalllocationApi from '@/api/property/installlocation';
  import TreeSelect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  export default {
    name: "InstalllocationForm",
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
          supId: undefined,
          name: undefined,
          dutyName: undefined,
        },
        // 表单校验
        formRules: {
          name: [{ required: true, message: '位置名称不能为空', trigger: 'blur' }],
          code: [{ required: true, message: '位置编码不能为空', trigger: 'blur' }],
          dutyName: [{ required: true, message: '责任人不能为空', trigger: 'blur' }],
          supId: [{ required: true, message: '上级地区不能为空', trigger: 'blur' }],
        },
        installlocationTree: [], // 树形结构
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
            const res = await InstalllocationApi.getInstalllocation(id);
            this.formData = res.data;
            this.dialogTitle = "修改设备安装位置";
          } finally {
            this.formLoading = false;
          }
        }else{
          this.dialogTitle = "新增设备安装位置";
        }
        await this.getInstalllocationTree();
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
            await InstalllocationApi.updateInstalllocation(data);
            this.$modal.msgSuccess("修改成功");
            this.dialogVisible = false;
            this.$emit('success');
            return;
          }
          // 添加的提交
          await InstalllocationApi.createInstalllocation(data);
          this.$modal.msgSuccess("新增成功");
          this.dialogVisible = false;
          this.$emit('success');
        } finally {
          this.formLoading = false;
        }
      },
      /** 获得设备安装位置树 */
      async getInstalllocationTree() {
        this.installlocationTree = [];
        const res = await InstalllocationApi.getInstalllocationList();
        const root = { id: 0, name: '莫罗瓦利园区Morowali', children: [] };
        root.children = this.handleTreeForString(res.data, 'id', 'supId');
        this.installlocationTree.push(root);
      },
      /** 转换设备安装位置数据结构 */
      normalizer(node) {
        return this.myNormalizer(node,'id','name')
      },
      /** 表单重置 */
      reset() {
        this.formData = {
          code: undefined,
          supId: undefined,
          name: undefined,
          dutyName: undefined,
        };
        this.resetForm("formRef");
      }
    }
  };
</script>
