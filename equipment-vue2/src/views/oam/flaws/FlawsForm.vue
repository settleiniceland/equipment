<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="45%" v-dialogDrag append-to-body>
      <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="17%">
        <el-form-item label="请选择设备" prop="equipProfileId">
          <TreeSelect
            v-model="formData.equipProfileId"
            :options="equipmentprofileTree"
            :normalizer="normalizer"
            placeholder="请选择设备"
            @input="handleSelect"
          />
        </el-form-item>
        <el-form-item label="是否停机" prop="isStop">
          <el-select v-model="formData.isStop" placeholder="是否停机" clearable filterable size="small">
            <el-option v-for="equip in equipStatusDict" :key="equip.id" :label="equip.name" :value="equip.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="缺陷开始时间" prop="beginTime">
          <el-date-picker clearable v-model="formData.beginTime" type="datetime" value-format="timestamp" placeholder="选择缺陷开始时间" />
        </el-form-item>
        <el-form-item label="缺陷详情" prop="details">
          <el-input  type="textarea" :rows="9" v-model="formData.details" placeholder="请输入缺陷详情" />
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
  import * as EquipmentprofileApi from '@/api/property/equipmentprofile';
  import * as FlawsApi from '@/api/oam/flaws';
  import TreeSelect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  export default {
    name: "FlawsForm",
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
          fixPlanName: undefined,
          equipProfileId: undefined,
          equipProfileName: undefined,
          equipCode: undefined,
          equipAttribute: undefined,
          status: undefined,
          isStop: undefined,
          details: undefined,
          beginTime: undefined,
          solveTime: undefined,
          fixPlanId: undefined,
          solveDuration: undefined,
          solveDeptId: undefined,
          solveDeptName: undefined,
          solveHumanNum: undefined,
        },
        // 表单校验
        formRules: {
          equipProfileId: [{ required: true, message: '设备档案id不能为空', trigger: 'blur' }],
          equipProfileName: [{ required: true, message: '设备档案名称不能为空', trigger: 'blur' }],
          equipCode: [{ required: true, message: '设备档案编码不能为空', trigger: 'blur' }],
          isStop: [{ required: true, message: '是否停机不能为空', trigger: 'blur' }],
          details: [{ required: true, message: '缺陷描述不能为空', trigger: 'blur' }],
          beginTime: [{ required: true, message: '缺陷开始时间不能为空', trigger: 'blur' }],
        },
        // 查询参数【只是当个空参数而已】
        queryParams: {
          equipAttribute: null,
          code: null,
          manufacturerName: null,
          equipId: null,
          equiptypeId: null,
          dutyName: null,
          status: null,
          installDate: [],
          buyTime: [],
          locationId: null,
          workshopId: null,
        },
        equipmentprofileTree:[],//实体设备树形结构
        equipStatusDict: [
          {id:1, name:'开机'},
          {id:2, name:'停机'}
        ],
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
            const res = await FlawsApi.getFlaws(id);
            this.formData = res.data;
            this.dialogTitle = "修改缺陷库";
          } finally {
            this.formLoading = false;
          }
        }else{
          this.dialogTitle = "新增缺陷库";
        }
        const res = await EquipmentprofileApi.getEquipmentprofileList(this.queryParams);
        for(let i=0;i<res.data.length;i++){
          this.$set(res.data[i],"entireName",res.data[i].equipName+res.data[i].code);
        }
        this.equipmentprofileTree = this.handleTreeForString(res.data, 'id', 'supId');
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
            await FlawsApi.updateFlaws(data);
            this.$modal.msgSuccess("修改成功");
            this.dialogVisible = false;
            this.$emit('success');
            return;
          }else{
            // 添加的提交
            await FlawsApi.createFlaws(data);
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
          fixPlanName: undefined,
          equipProfileId: undefined,
          equipProfileName: undefined,
          equipCode: undefined,
          equipAttribute: undefined,
          status: undefined,
          isStop: undefined,
          details: undefined,
          beginTime: undefined,
          solveTime: undefined,
          fixPlanId: undefined,
          solveDuration: undefined,
          solveDeptId: undefined,
          solveDeptName: undefined,
          solveHumanNum: undefined,
        };
        this.resetForm("formRef");
      },
      //树型构造函数
      normalizer(node) {
        return this.myNormalizer(node,"id","entireName");
      },
      //选中某项后，多个值赋值
      handleSelect(selectedValue) {
        const selectedNode = this.findNodeById(this.equipmentprofileTree, selectedValue);
        if(selectedNode){
          this.formData.equipProfileName = selectedNode.equipName;
          this.formData.equipCode = selectedNode.code;
          this.formData.equipAttribute = selectedNode.equipAttribute;
        }
      }
    }
  };
</script>
