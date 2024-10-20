<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="45%" v-dialogDrag append-to-body>
      <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="100px">
                    <el-form-item label="保养计划id" prop="equipMaintainPlanId">
                      <el-input v-model="formData.equipMaintainPlanId" placeholder="请输入保养计划id" />
                    </el-form-item>
                    <el-form-item label="保养计划名称" prop="equipMaintainPlanName">
                      <el-input v-model="formData.equipMaintainPlanName" placeholder="请输入保养计划名称" />
                    </el-form-item>
                    <el-form-item label="保养内容id" prop="equipMaintainDetailId">
                      <el-input v-model="formData.equipMaintainDetailId" placeholder="请输入保养内容id" />
                    </el-form-item>
                    <el-form-item label="保养内容" prop="equipMaintainDetail">
                      <el-input v-model="formData.equipMaintainDetail" placeholder="请输入保养内容" />
                    </el-form-item>
                    <el-form-item label="是否特殊设备【字典：0否；1是】" prop="isSpecial">
                      <el-input v-model="formData.isSpecial" placeholder="请输入是否特殊设备【字典：0否；1是】" />
                    </el-form-item>
                    <el-form-item label="设备档案id" prop="equipmentprofileId">
                      <el-input v-model="formData.equipmentprofileId" placeholder="请输入设备档案id" />
                    </el-form-item>
                    <el-form-item label="设备档案编码" prop="equipmentprofileCode">
                      <el-input v-model="formData.equipmentprofileCode" placeholder="请输入设备档案编码" />
                    </el-form-item>
                    <el-form-item label="设备名称" prop="equipName">
                      <el-input v-model="formData.equipName" placeholder="请输入设备名称" />
                    </el-form-item>
                    <el-form-item label="保养周期" prop="maintainCycle">
                      <el-input v-model="formData.maintainCycle" placeholder="请输入保养周期" />
                    </el-form-item>
                    <el-form-item label="是否更换自身【字典：0否；1是】" prop="replaceSelf">
                      <el-input v-model="formData.replaceSelf" placeholder="请输入是否更换自身【字典：0否；1是】" />
                    </el-form-item>
                    <el-form-item label="保养图片【地址，中间以-_-隔开】" prop="resultPhotos">
                      <el-input v-model="formData.resultPhotos" placeholder="请输入保养图片【地址，中间以-_-隔开】" />
                    </el-form-item>
                    <el-form-item label="保养日期" prop="maintainDate">
                      <el-date-picker clearable v-model="formData.maintainDate" type="date" value-format="timestamp" placeholder="选择保养日期" />
                    </el-form-item>
                    <el-form-item label="计划执行部门id【从保养计划中来】" prop="executeDeptId">
                      <el-input v-model="formData.executeDeptId" placeholder="请输入计划执行部门id【从保养计划中来】" />
                    </el-form-item>
                    <el-form-item label="计划执行部门名称【从保养计划中来】" prop="executeDeptName">
                      <el-input v-model="formData.executeDeptName" placeholder="请输入计划执行部门名称【从保养计划中来】" />
                    </el-form-item>
                    <el-form-item label="实际保养人" prop="actualMaintainNames">
                      <el-input v-model="formData.actualMaintainNames" placeholder="请输入实际保养人" />
                    </el-form-item>
                    <el-form-item label="备注" prop="remark">
                      <el-input v-model="formData.remark" placeholder="请输入备注" />
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
  import * as MaintainProfileApi from '@/api/oam/maintainprofile';
      export default {
    name: "MaintainProfileForm",
    components: {
                    },
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
                            equipMaintainPlanId: undefined,
                            equipMaintainPlanName: undefined,
                            equipMaintainDetailId: undefined,
                            equipMaintainDetail: undefined,
                            isSpecial: undefined,
                            equipmentprofileId: undefined,
                            equipmentprofileCode: undefined,
                            equipName: undefined,
                            maintainCycle: undefined,
                            replaceSelf: undefined,
                            resultPhotos: undefined,
                            maintainDate: undefined,
                            executeDeptId: undefined,
                            executeDeptName: undefined,
                            actualMaintainNames: undefined,
                            remark: undefined,
        },
        // 表单校验
        formRules: {
                        equipMaintainPlanId: [{ required: true, message: '保养计划id不能为空', trigger: 'blur' }],
                        equipMaintainPlanName: [{ required: true, message: '保养计划名称不能为空', trigger: 'blur' }],
                        equipMaintainDetailId: [{ required: true, message: '保养内容id不能为空', trigger: 'blur' }],
                        equipMaintainDetail: [{ required: true, message: '保养内容不能为空', trigger: 'blur' }],
                        isSpecial: [{ required: true, message: '是否特殊设备【字典：0否；1是】不能为空', trigger: 'blur' }],
                        equipmentprofileId: [{ required: true, message: '设备档案id不能为空', trigger: 'blur' }],
                        equipmentprofileCode: [{ required: true, message: '设备档案编码不能为空', trigger: 'blur' }],
                        equipName: [{ required: true, message: '设备名称不能为空', trigger: 'blur' }],
                        maintainCycle: [{ required: true, message: '保养周期不能为空', trigger: 'blur' }],
                        replaceSelf: [{ required: true, message: '是否更换自身【字典：0否；1是】不能为空', trigger: 'blur' }],
                        maintainDate: [{ required: true, message: '保养日期不能为空', trigger: 'blur' }],
                        executeDeptId: [{ required: true, message: '计划执行部门id【从保养计划中来】不能为空', trigger: 'blur' }],
                        executeDeptName: [{ required: true, message: '计划执行部门名称【从保养计划中来】不能为空', trigger: 'blur' }],
                        actualMaintainNames: [{ required: true, message: '实际保养人不能为空', trigger: 'blur' }],
        },
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
            const res = await MaintainProfileApi.getMaintainProfile(id);
            this.formData = res.data;
            this.dialogTitle = "修改保养日志表";
          } finally {
            this.formLoading = false;
          }
        }else{
          this.dialogTitle = "新增保养日志表";
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
            await MaintainProfileApi.updateMaintainProfile(data);
            this.$modal.msgSuccess("修改成功");
            this.dialogVisible = false;
            this.$emit('success');
            return;
          }else{
            // 添加的提交
            await MaintainProfileApi.createMaintainProfile(data);
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
                            equipMaintainPlanId: undefined,
                            equipMaintainPlanName: undefined,
                            equipMaintainDetailId: undefined,
                            equipMaintainDetail: undefined,
                            isSpecial: undefined,
                            equipmentprofileId: undefined,
                            equipmentprofileCode: undefined,
                            equipName: undefined,
                            maintainCycle: undefined,
                            replaceSelf: undefined,
                            resultPhotos: undefined,
                            maintainDate: undefined,
                            executeDeptId: undefined,
                            executeDeptName: undefined,
                            actualMaintainNames: undefined,
                            remark: undefined,
        };
        this.resetForm("formRef");
      }
    }
  };
</script>