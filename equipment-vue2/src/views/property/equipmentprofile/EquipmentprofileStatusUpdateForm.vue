<template>
    <div class="app-container">
        <el-dialog :visible.sync="dialogVisible" width="27vh" v-dialogDrag append-to-body>
            <template #title>
                <div class="el-dialog__title">
                    <br>{{ dialogTitle }}
                </div>
            </template>
            <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="7hv" :style="{ minHeight: '27vh' }">
                <el-form-item v-if="attribute === 2" label="设备启停" prop="status1">
                    <el-switch v-model="formData.status1" :active-value="1" :inactive-value="2"/>
                    【{{ status1Name(formData.status1) }}】
                </el-form-item>
                <el-form-item label="其他状态" prop="status2">
                    <el-radio-group v-model="formData.status2">
                        <el-radio-button 
                            v-for="(s2) in customStatus2DictDatas" 
                            :label="s2.value"
                            :key="s2.value">
                            {{ s2.label }}
                        </el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item :label='$t("message.Button.remark")' prop="remark">
                    <el-input type="textarea" :rows="11" v-model="formData.remark" :placeholder='$t("message.Button.remark")' />
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
    export default{
        name:'EquipmentprofileStatusUpdateForm',
        data(){
            return {
                // 标题
                dialogTitle: '',
                // 是否显示弹出层
                dialogVisible: false,
                // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
                formLoading: false,
                //设备属性
                attribute: undefined,
                //表单参数
                formData: {
                    id: undefined,
                    equipName: undefined,
                    code: undefined,
                    status1: undefined,
                    status2: undefined,
                    remark: undefined,
                },
                //经筛选后的其他属性字典数组
                customStatus2DictDatas:[],
                //表单验证规则（备注不能为空）
                formRules: {
                    remark: [{ required: true, message: '备注不能为空', trigger: 'blur' }],
                }
            };
        },
        props: {
            status1DictDatas:{type:Array,required:true},
            status2DictDatas:{type:Array,required:true},
        },
        methods: {
            /** 打开弹窗 */
            open(row) {
                this.dialogVisible = true;
                this.formLoading = true;
                this.formData = {
                    id: undefined,
                    equipName: undefined,
                    code: undefined,
                    status1: undefined,
                    status2: undefined,
                };
                try {
                    this.dialogTitle = '《'+ row.equipName + row.code +'》状态修改';
                    this.formData.id = row.id;
                    this.formData.equipName = row.equipName;
                    this.formData.code = row.code;
                    this.formData.status1 = row.status1;
                    this.formData.status2 = row.status2;
                    this.attribute = row.equipAttribute;
                    this.customStatus2DictDatas = [];
                    // （状态2为	空、4异动完毕、6回国返修完毕	 时）显示：5回国返修中，7报废
                    // （状态2为	3异动中			               时）显示：4异动完毕，7报废
                    // （状态2为	5回国返修中		               时）显示：6回国返修完毕，7报废
                    // （状态2为    7报废			              时）均不显示
                    // 状态2若要改为	3异动中	或	需在其他地方
                    for(const status2 of this.status2DictDatas){
                        if(this.formData.status2===null || this.formData.status2===4 || this.formData.status2===6){
                            if(status2.value === 7 || status2.value === 5){
                                this.customStatus2DictDatas.push(status2);
                            }
                        }else if(this.formData.status2===3){
                            if(status2.value === 7 || status2.value === 4){
                                this.customStatus2DictDatas.push(status2);
                            }
                        }else if(this.formData.status2===5){
                            if(status2.value === 7 || status2.value === 6){
                                this.customStatus2DictDatas.push(status2);
                            }
                        }
                    }
                }finally{
                    this.formLoading = false;
                }
            },
            /** status1name */
            status1Name(status1){
                for(const item of this.status1DictDatas){
                    if(item.value === status1){
                        return item.label;
                    }
                }
                return "";
            },
            /** 修改状态 */
            async submitForm(){
                // 校验主表
                await this.$refs["formRef"].validate();
                await EquipmentprofileApi.updateStatusById(this.formData);
                this.$modal.msgSuccess("修改成功");
                this.dialogVisible = false;
                this.$emit('success');
            }
        }
    }
</script>