<template>
    <div class="app-container">
        <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="80%" v-dialogDrag append-to-body>
            <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="120px">
                <el-form-item label="设备" prop="equipId">
                    <TreeSelect
                        v-model="formData.equipId"
                        :options="equipTree"
                        :normalizer="normalizer1"
                        placeholder="请选择设备"
                        @input="handleEquipChange"
                    />
                </el-form-item>
                <el-form-item label="是否特殊设备" prop="isSpecial">
                    <el-select v-model="formData.isSpecial" placeholder='是否特殊设备' clearable size="small" @change="handleSpecialChange">
                        <el-option v-for="dict in commonDict" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
                    </el-select>
                </el-form-item>
                <el-form-item v-if="formData.equipId !== undefined && formData.equipId !== '' && formData.equipId !== null" label="具体设备" prop="equipprofileIds">
                    <el-select multiple v-model="formData.equipprofileIds" placeholder='具体设备' clearable size="small">
                        <el-option v-for="equipprofile in equipprofileList" :key="equipprofile.id" :label="equipprofile.equipName + equipprofile.code" :value="equipprofile.id"/>
                    </el-select>
                </el-form-item>
                <el-form-item v-if="formData.isSpecial === 1" label="内容选择" prop="isReferto">
                    <el-radio v-model="formData.isReferto" :label="1" border @change="handleIsRefer">选择参考项</el-radio>
                    <el-radio v-model="formData.isReferto" :label="0" border @change="handleIsRefer">创建新的</el-radio>
                </el-form-item>
                <el-form-item v-if="formData.isReferto === 1 && formData.isSpecial === 1" label="参考项" prop="refertoId">
                    <el-select v-model="formData.refertoId" @input="handleReferto" placeholder='参考项' clearable size="small">
                        <el-option v-for="maintain in realMaintainList" :key="maintain.id" :label="maintain.details" :value="maintain.id"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="周期【单位:h】" prop="maintainCycle">
                    <el-input-number v-model="formData.maintainCycle" :precision="2" :step="0.1"></el-input-number>
                </el-form-item>
                <el-form-item label="保养内容" prop="details">
                    <el-input type="textarea" :rows="9" v-model="formData.details" placeholder="保养内容"
                        :disabled="formData.isReferto === 1"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submit" :disabled="formLoading">确 定</el-button>
                <el-button @click="dialogVisible = false">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import * as MaintainDetailApi from '@/api/oam/maintaindetail';
    import * as EquipApi from '@/api/property/equip';
    import * as EquipmentprofileApi from '@/api/property/equipmentprofile';
    import TreeSelect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    export default {
        name: "MaintainPlanDetailForm",
        components: {TreeSelect},
        props:{
            commonDict:{type:Array,required:true},
            maintainPlan:{type:Object,required:true},
        },
        data() {
            return{
                // 弹出层标题
                dialogTitle: "",
                // 是否显示弹出层
                dialogVisible: false,
                // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
                formLoading: false,
                formData: {
                    id: undefined,
                    equipMaintainPlanId: undefined,
                    equipMaintainPlanName: undefined,
                    equipId: undefined,
                    isSpecial: undefined,
                    equipName: undefined,
                    equiplocationId: undefined,
                    equiplocationName: undefined,
                    equipprofileIds: undefined,
                    equipprofileCodes: undefined,
                    equipSpecification: undefined,
                    isReferto: undefined,
                    refertoId: undefined,
                    maintainCycle: undefined,
                    details: undefined,
                },
                // 表单校验
                formRules: {
                    equipMaintainPlanId: [{ required: true, message: '保养计划id不能为空', trigger: 'blur' }],
                    equipMaintainPlanName: [{ required: true, message: '保养计划名称不能为空', trigger: 'blur' }],
                    equipId: [{ required: true, message: '设备不能为空', trigger: 'blur' }],
                    isSpecial: [{ required: true, message: '未选择是否为特殊设备', trigger: 'blur' }],
                    equipprofileIds: [{ required: true, message: '未选择特殊设备', trigger: 'blur' }],
                    equipName: [{ required: true, message: '设备名称不能为空', trigger: 'blur' }],
                    equipSpecification: [{ required: true, message: '设备规格不能为空', trigger: 'blur' }],
                    isReferto: [{ required: true, message: '未选择是否参考其他', trigger: 'blur' }],
                    refertoId: [{ required: true, message: '未选择参考项', trigger: 'blur' }],
                    maintainCycle: [{ required: true, message: '保养周期不能为空', trigger: 'blur' }],
                    details: [{ required: true, message: '保养内容不能为空', trigger: 'blur' }],
                },
                equipTree: [],          //设备树形结构
                equipprofileList: [],   //对应设备的设备档案列表
                maintainList: [],       //全部非特殊设备的保养内容list列表
                realMaintainList: [],   //真正要参照的保养内容list列表
            }
        },
        methods:{
            /** 打开弹窗 */
            async open(id) {
                this.dialogVisible = true;
                this.reset();
                this.formLoading = true;
                try{
                    await this.getAllTreeAndList();
                    if (id) {//修改
                        const res = await MaintainDetailApi.getMaintainDetail(id);
                        this.formData = res.data;
                        this.formData.equipprofileIds = this.formData.equipprofileIds.split("-_-");
                        this.formData.equipprofileCodes = this.formData.equipprofileCodes.split("-_-");
                        this.handleSpecialChange(this.formData.isSpecial);
                        this.dialogTitle = "修改保养内容表";
                    }else{//新增
                        this.dialogTitle = "新增保养内容表";
                        this.formData.equipMaintainPlanId = this.maintainPlan.id;
                        this.formData.equipMaintainPlanName = this.maintainPlan.name;
                        this.formData.equiplocationId = this.maintainPlan.equiplocationId;
                        this.formData.equiplocationName = this.maintainPlan.equiplocationName;
                    }
                }finally {
                    this.formLoading = false;
                }
            },
            /** 表单重置 */
            reset() {
                this.formData = {
                    id: undefined,
                    equipMaintainPlanId: undefined,
                    equipMaintainPlanName: undefined,
                    equipId: undefined,
                    isSpecial: undefined,
                    equipName: undefined,
                    equiplocationId: undefined,
                    equiplocationName: undefined,
                    equipprofileIds: undefined,
                    equipprofileCodes: undefined,
                    equipSpecification: undefined,
                    isReferto: undefined,
                    refertoId: undefined,
                    maintainCycle: undefined,
                    details: undefined,
                };
                this.equipprofileList = [];
                this.resetForm("formRef");
            },
            /** 转换设备表数据结构 */
            normalizer1(node) {
                return this.myNormalizer(node,"id","equipName");
            },
            /** 获取刚开始所需要的设备树型结构 */
            async getAllTreeAndList(){
                //设备树
                this.equipTree = [];
                const res = await EquipApi.getEquipList();
                this.equipTree = this.handleTreeForString(res.data, 'id', 'supId');
                //全部非特殊设备的保养内容list
                this.maintainList = [];
                const param = {
                    isSpecial: 0,
                }
                const res1 = await MaintainDetailApi.getMaintainDetailList(param);
                this.maintainList = res1.data;
            },
            /** 设备选中事件 */
            async handleEquipChange(selectValue) {
                if(selectValue !== '' && selectValue !== undefined && selectValue !== null){
                    //查询到对相应的设备档案list
                    this.equipprofileList = [];
                    const param = {
                        equipId: selectValue,
                        locationId: this.formData.equiplocationId,
                    }
                    const resp = await EquipmentprofileApi.getEquipmentprofileList(param);  
                    this.equipprofileList = resp.data;
                    //查询要使用的参照对象list
                    this.realMaintainList = [];
                    this.maintainList.forEach(maintain => {
                        if(maintain.equipId === selectValue && maintain.id !== this.formData.id){
                            this.realMaintainList.push(maintain);
                        }
                    })
                }
                //设备名与规格的赋值放在提交前
            },
            /** 是否特殊设备选中事件 */
            handleSpecialChange(selectValue) {
                if(selectValue === 0){//不是特殊设备的话则清空是否借鉴和借鉴id属性
                    this.formData.isReferto = undefined;
                    this.formData.refertoId = undefined;
                }
            },
            /** 参考对象列表选中事件 */
            handleReferto(selectValue){
                this.realMaintainList.forEach(maintain => {
                    if(maintain.id === selectValue){
                        this.formData.details = maintain.details;
                        return;
                    }
                });
            },
            /** 是否参考其他触发事件 */
            handleIsRefer(selectValue){
                this.formData.refertoId = undefined;
                this.formData.details = undefined;
            },
            /** 表单提交 */
            async submit(){
                try{
                    // 先验证表格
                    await this.$refs["formRef"].validate();
                    //给设备名设备规格赋值
                    const equipNode = this.findNodeById(this.equipTree,this.formData.equipId);
                    this.formData.equipName = equipNode.equipName;
                    this.formData.equipSpecification = equipNode.equipSpecification;
                    const ids = this.formData.equipprofileIds;
                    this.formData.equipprofileIds = '';
                    this.formData.equipprofileCodes = '';
                    const splitMark = "-_-";
                    ids.forEach((id,index) => {
                        const equipprofileNode = this.findNodeById(this.equipprofileList,id);
                        if(equipprofileNode === undefined || equipprofileNode === null){
                            this.$modal.msgError("特殊设备信息与设备信息不符");
                            return;
                        }
                        this.formData.equipprofileIds += equipprofileNode.id;
                        this.formData.equipprofileCodes += equipprofileNode.code;
                        if(index !== ids.length - 1){
                            this.formData.equipprofileIds += splitMark;
                            this.formData.equipprofileCodes += splitMark;
                        }
                    })
                    //验证通过正式提交
                    if(this.formData.id){//修改
                        await MaintainDetailApi.updateMaintainDetail(this.formData);
                        this.$modal.msgSuccess("修改成功");
                        this.dialogVisible = false;
                        this.$emit('success');
                        return;
                    }else{//添加
                        await MaintainDetailApi.createMaintainDetail(this.formData);
                        this.$modal.msgSuccess("新增成功");
                        this.dialogVisible = false;
                        this.$emit('success');
                        return;
                    }
                }catch(errors){this.$modal.msgError(errors)}
            },
        }
    }
</script>