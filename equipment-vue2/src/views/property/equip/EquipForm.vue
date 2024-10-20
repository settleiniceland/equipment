<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="45%" v-dialogDrag append-to-body>
      <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="100px">
        <el-form-item label="上级设备" prop="supId">
          <TreeSelect
            v-model="formData.supId"
            :options="equipTree"
            :normalizer="normalizer"
            placeholder="请选择上级设备"
          />
        </el-form-item>
        <el-form-item label="设备名称" prop="equipName">
          <el-input v-model="formData.equipName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备属性" prop="equipAttribute">
          <el-select v-model="formData.equipAttribute" placeholder="请选择设备属性" clearable size="small">
            <el-option v-for="dict in equipAttributeDictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
          </el-select>
        </el-form-item>
        <el-form-item label="设备类别" prop="equiptypeId">
          <TreeSelect
            v-model="formData.equiptypeId"
            :options="equiptypeTree"
            :normalizer="normalizer1"
            @input="handleSelect"
            placeholder="请选择设备类别"
          />
        </el-form-item>
        <el-form-item label="设备规格" prop="equipSpecification">
          <el-input type="textarea" :rows="9" v-model="formData.equipSpecification" placeholder="请输入设备规格" />
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
  import * as EquipApi from '@/api/property/equip';
  import * as EquiptypeApi from '@/api/property/equiptype';
  import TreeSelect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  export default {
    name: "EquipForm",
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
          supId: undefined,
          equipName: undefined,
          equipSpecification: undefined,
          equipAttribute: undefined,
          equiptypeId: undefined,
          equiptypeName: undefined,
        },
        // 表单校验
        formRules: {
          supId: [{ required: true, message: '上级不能为空', trigger: 'blur' }],
          equipName: [{ required: true, message: '设备名称不能为空', trigger: 'blur' }],
          equipSpecification: [{ required: true, message: '设备规格不能为空', trigger: 'blur' }],
          equipAttribute: [{ required: true, message: '设备属性不能为空', trigger: 'blur' }],
          equiptypeId: [{ required: true, message: '设备类别不能为空', trigger: 'blur' }],
          equiptypeName: [{ required: true, message: '设备类别名称不能为空', trigger: 'blur' }],
        },
        equipTree: [], // 设备树形结构
        equiptypeTree:[],//设备类别树形结构
      };
    },
    //父页面传递来的参数
    props:{
      equipAttributeDictDatas:{type:Array,required:true}
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
            const res = await EquipApi.getEquip(id);
            this.formData = res.data;
            this.dialogTitle = "修改设备表";
          } finally {
            this.formLoading = false;
          }
        }else{
          this.dialogTitle = "新增设备表";
        }
        await this.getEquipTree();
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
            await EquipApi.updateEquip(data);
            this.$modal.msgSuccess("修改成功");
            this.dialogVisible = false;
            this.$emit('success');
            return;
          }
          // 添加的提交
          await EquipApi.createEquip(data);
          this.$modal.msgSuccess("新增成功");
          this.dialogVisible = false;
          this.$emit('success');
        } finally {
          this.formLoading = false;
        }
      },
      /** 获得设备树 */
      async getEquipTree() {
        this.equipTree = [];
        const res = await EquipApi.getEquipList();
        const root = { id: 0, equipName: '---', children: [] };
        root.children = this.handleTreeForString(res.data, 'id', 'supId')
        this.equipTree.push(root)
      },
      /** 构建设备类型树 */
      async getEquiptypeTree(){
        this.equiptypeTree = [];
        const res = await EquiptypeApi.getEquiptypeList();
        this.equiptypeTree = this.handleTreeForString(res.data, 'id', 'supId');
      },
      /** 转换设备表数据结构 */
      normalizer(node) {
        return this.myNormalizer(node,"id","equipName");
      },
      /** 转换设备类型数据结构 */
      normalizer1(node) {
        return this.myNormalizer(node,"id","name");
      },
      // 在选中某项后，通过selectedValue查找对应的label并赋值给formData.equiptypeName属性【无需后端再查】
      handleSelect(selectedValue) {
        const selectedNode = this.findNodeById(this.equiptypeTree, selectedValue);
        if (selectedNode) {
          this.formData.equiptypeName = selectedNode.name;
        }
      },
      /** 表单重置 */
      reset() {
        this.formData = {
          supId: undefined,
          equipName: undefined,
          equipSpecification: undefined,
          equipAttribute: undefined,
          equiptypeId: undefined,
          equiptypeName: undefined,
        };
        this.resetForm("formRef");
      }
    }
  };
</script>
