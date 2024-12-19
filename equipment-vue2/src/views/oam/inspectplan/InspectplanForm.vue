<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="45%" v-dialogDrag append-to-body>
      <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="100px">
        <el-form-item label="点检类型" prop="inspectionType">
          <el-select v-model="formData.inspectionType" placeholder="请选择点检类型" clearable size="small">
            <el-option v-for="type in typeInspectplan" :key="parseInt(type.value)" :label="type.label" :value="parseInt(type.value)"/>
          </el-select>
        </el-form-item>
        <el-form-item label="点检周期【单位h】" prop="inspectionCycle">
          <el-input-number v-model="formData.inspectionCycle" :precision="2" :step="0.1"></el-input-number>
        </el-form-item>
        <el-form-item label="计划状态" prop="status">
          <el-select v-model="formData.status" placeholder="请选择计划状态" clearable size="small">
            <el-option v-for="status in statusInspectplan" :key="parseInt(status.value)" :label="status.label" :value="parseInt(status.value)"/>
          </el-select>
        </el-form-item>
        <el-form-item label="点检计划名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入点检计划名称" />
        </el-form-item>
        <el-form-item label="点检区域" prop="equiplocationId">
          <TreeSelect
            v-model="formData.equiplocationId"
            :options="installlocationTree"
            :normalizer="normalizer1"
            placeholder="请选择位置"
            class="treeSelectCSS"
            @input="selectLocation"
          />
        </el-form-item>
        <el-form-item label="点检设备" prop="equipId" v-show="formData.equiplocationId !== undefined">
          <el-select v-model="formData.equipId" placeholder="请选择设备" clearable size="small" @input="selectEquip">
            <el-option v-for="equipprofile in equipprofileList" :key="equipprofile.equipId" :label="equipprofile.equipName" :value="equipprofile.equipId"/>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="detail">
          <el-input type="textarea" :rows="9" v-model="formData.detail" placeholder="请输入备注" />
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
  import * as InspectplanApi from '@/api/oam/inspectplan';
  import * as InstalllocationApi from '@/api/property/installlocation';
  import * as EquipmentprofileApi from '@/api/property/equipmentprofile';
  import TreeSelect from "@riophae/vue-treeselect";
  import '@riophae/vue-treeselect/dist/vue-treeselect.css';
  export default {
    name: "InspectplanForm",
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
          status: 0,
          inspectionType: undefined,
          inspectionCycle: 0,
          name: undefined,
          equiplocationId: undefined,
          equiplocationName: undefined,
          equiplocationDutyName: undefined,
          equipId: undefined,
          equipName: undefined,
          detail: undefined,
        },
        // 表单校验
        formRules: {
          status: [{ required: true, message: '计划状态不能为空', trigger: 'change' }],
          inspectionType: [{ required: true, message: '点检类型不能为空', trigger: 'change' }],
          inspectionCycle: [{ required: true, message: '点检周期不能为空', trigger: 'blur' }],
          name: [{ required: true, message: '点检计划名称不能为空', trigger: 'blur' }],
          equiplocationId: [{ required: true, message: '设备区域id不能为空', trigger: 'change' }],
          equipId: [{ required: true, message: '设备id不能为空', trigger: 'change' }],
          equipName: [{ required: true, message: '设备名称不能为空', trigger: 'change' }],
        },
        installlocationTree: [],//设备安装位置树型结构
        equipprofileList: [],//设备列表（该位置下所有存在实体设备的设备种类列表）
      };
    },
    props:{
      typeInspectplan:{type:Array,required:true},
      statusInspectplan:{type:Array,required:true}
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
            const res = await InspectplanApi.getInspectplan(id);
            this.formData = res.data;
            this.dialogTitle = "修改点检计划表";
          } finally {
            this.formLoading = false;
          }
        }else{
          this.dialogTitle = "新增点检计划表";
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
            await InspectplanApi.updateInspectplan(data);
            this.$modal.msgSuccess("修改成功");
            this.dialogVisible = false;
            this.$emit('success');
            return;
          }else{
            // 添加的提交
          await InspectplanApi.createInspectplan(data);
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
          status: 0,
          inspectionType: undefined,
          inspectionCycle: 0,
          name: undefined,
          equiplocationId: undefined,
          equiplocationName: undefined,
          equiplocationDutyName: undefined,
          detail: undefined,
        };
        this.resetForm("formRef");
      },
      /** 获取用的所有的树型结构 */
      async getAllTree(){
        //获取安装位置树形结构
        this.installlocationTree = [];
        const InstalllocationRes = await InstalllocationApi.getInstalllocationList();
        this.installlocationTree = this.handleTreeForString(InstalllocationRes.data,'id','supId');
      },
      /** 转换设备类型数据结构 */
      normalizer1(node){
        return this.myNormalizer(node,"id","name");
      },
      /** 地址选中事件 */
      async selectLocation(locationId){
        if(locationId === undefined || locationId === '' || locationId === null){
          this.formData.equipId = undefined;
          this.formData.equipName = undefined;
          this.formData.equiplocationDutyName = undefined;
        }else{
          //查设备列表
          const equipprofilereqvo = {
            equipAttribute: 2,
            locationId: locationId,
          }
          const resp = await EquipmentprofileApi.getEquipmentprofileList(equipprofilereqvo);
          this.equipprofileList = Array.from(new Map(resp.data.map(item => [item.equipId, item])).values());
          //根据地址id赋值负责人值
          const location = this.findNodeById(this.installlocationTree,locationId);
          this.formData.equiplocationDutyName = location.dutyName;
        }
      },
      /** 设备选中事件 */
      selectEquip(equipId){
        if(equipId === undefined || equipId === '' || equipId === null){
          this.formData.equipName = undefined;
        }else{
          for(const equipprofile of this.equipprofileList){
            if(equipprofile.equipId === equipId){
              this.formData.equipName = equipprofile.equipName;
              break;
            }
          }
        }
      }
    }
  };
</script>
