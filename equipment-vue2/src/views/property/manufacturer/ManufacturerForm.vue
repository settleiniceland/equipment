<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="45%" v-dialogDrag append-to-body>
      <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="100px">
        <el-form-item :label='$t("message.Manufacturer.manufacturer_name")' prop="name">
          <el-input v-model="formData.name" :placeholder='$t("message.Manufacturer.manufacturer_name")' />
        </el-form-item>
        <el-form-item :label='$t("message.Manufacturer.manufacturer_code")' prop="code">
          <el-input v-model="formData.code" :placeholder='$t("message.Manufacturer.manufacturer_code")' />
        </el-form-item>
        <el-form-item :label='$t("message.Manufacturer.manufacturer_status")' prop="status">
          <el-select v-model="formData.status" :placeholder='$t("message.Manufacturer.manufacturer_status")' clearable size="small">
            <el-option v-for="dict in statusDictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
          </el-select>
        </el-form-item>
        <el-form-item :label='$t("message.Button.remark")' prop="remark">
          <el-input type="textarea" :rows="9" v-model="formData.remark" :placeholder='$t("message.Button.remark")' />
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
  import * as ManufacturerApi from '@/api/property/manufacturer';
  export default {
    name: "ManufacturerForm",
    components: {},
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
          name: undefined,
          code: undefined,
          status: 0,
          remark: undefined,
        },
        // 表单校验
        formRules: {
          name: [{ required: true, message: '厂家名称不能为空', trigger: 'blur' }],
          code: [{ required: true, message: '厂家编码不能为空', trigger: 'blur' }],
        }
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
        this.reset();
        // 修改时，设置数据
        if (id) {
          this.formLoading = true;
          try {
            const res = await ManufacturerApi.getManufacturer(id);
            this.formData = res.data;
            this.dialogTitle = "修改设备生产厂家信息";
          } finally {
            this.formLoading = false;
          }
        }else{
          this.dialogTitle = "新增设备生产厂家信息";
        }
        
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
            await ManufacturerApi.updateManufacturer(data);
            this.$modal.msgSuccess("修改成功");
            this.dialogVisible = false;
            this.$emit('success');
            return;
          }
          // 添加的提交
          await ManufacturerApi.createManufacturer(data);
          this.$modal.msgSuccess("新增成功");
          this.dialogVisible = false;
          this.$emit('success');
        } finally {
          this.formLoading = false;
        }
      },
      /** 表单重置 */
      reset() {
        this.formData = {
          name: undefined,
          code: undefined,
          status: 0,
          remark: undefined,
        };
        this.resetForm("formRef");
      }
    }
  };
</script>
