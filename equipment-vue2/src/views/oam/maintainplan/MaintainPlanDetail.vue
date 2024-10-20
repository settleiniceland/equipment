<template>
    <div class="app-container">
        <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="80%" v-dialogDrag append-to-body>
            <!-- 搜索框 -->
            <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="97px">
                <el-form-item label="计划名称" prop="equipMaintainPlanName">
                    <el-input v-model="queryParams.equipMaintainPlanName" placeholder="请输入保养计划名称" clearable />
                </el-form-item>
                <el-form-item label="是否特殊设备" prop="isSpecial">
                    <el-select v-model="queryParams.isSpecial" placeholder="是否特殊设备？" clearable size="small">
                        <el-option v-for="dict in commonDict" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否参照其他" prop="isReferto">
                    <el-select v-model="queryParams.isReferto" placeholder="是否参照其他？" clearable size="small">
                        <el-option v-for="dict in commonDict" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="设备名称" prop="equipName">
                    <el-input v-model="queryParams.equipName" placeholder="请输入设备名称" clearable />
                </el-form-item>
                <el-form-item label="设备规格" prop="equipSpecification">
                    <el-input v-model="queryParams.equipSpecification" placeholder="请输入设备规格" clearable />
                </el-form-item>
                <el-form-item label="保养周期" prop="maintainCycle">
                    <el-input v-model="queryParams.maintainCycle" placeholder="请输入保养周期" clearable />
                </el-form-item>
                <el-form-item label="是否更换自身" prop="replaceSelf">
                    <el-select v-model="queryParams.replaceSelf" placeholder="是否更换自身？" clearable size="small">
                        <el-option v-for="dict in commonDict" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="保养内容" prop="details">
                    <el-input v-model="queryParams.details" placeholder="请输入保养内容" clearable />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
                    <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
                </el-form-item>
            </el-form>
            <!-- 操作工具栏 -->
            <el-row :gutter="10" class="mb8">
                <el-col :span="1.5">
                    <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="openForm(undefined)"
                            v-hasPermi="['property:equipmentprofile:create']">{{ $t("message.Button.add") }}</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading" 
                            v-hasPermi="['property:equipmentprofile:export']">{{ $t("message.Button.export") }}</el-button>
                </el-col>
                <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </el-row>
            <!-- 展示栏 -->
            <el-table v-loading="loading" :data="list" :show-overflow-tooltip="true">
                <el-table-column label="保养计划名称" align="center" prop="equipMaintainPlanName" />
                <el-table-column label="是否特殊设备" align="center" prop="isSpecialName"/>
                <el-table-column label="是否有参照对象" align="center" prop="isRefertoName"/>
                <el-table-column label="设备名称" align="center" prop="equipName" />
                <el-table-column label="设备规格" align="center" prop="equipSpecification" />
                <el-table-column label="保养周期(单位h)" align="center" prop="maintainCycle" />
                <el-table-column label="是否更换自身" align="center" prop="replaceSelfName"/>
                <el-table-column label="保养内容" align="center" prop="details" />
                <el-table-column label="创建者" align="center" prop="creator" />
                <el-table-column label="创建时间" align="center" prop="createTime" width="180">
                    <template v-slot="scope">
                    <span>{{ parseTime(scope.row.createTime) }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="更新者" align="center" prop="updater" />
                <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
                    <template v-slot="scope">
                    <span>{{ parseTime(scope.row.updateTime) }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                    <template v-slot="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="openForm(scope.row.id)"
                                v-hasPermi="['oam:maintain-detail:update']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                                v-hasPermi="['oam:maintain-detail:delete']">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页组件 -->
            <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize" @pagination="getList"/>
            <MaintainPlanDetailForm ref="detailFormRef" :commonDict="commonDict" :maintainPlan="maintainPlan" @success="getList"/>
        </el-dialog>
    </div>
</template>
<script>
    import * as MaintainDetailApi from '@/api/oam/maintaindetail';
    import MaintainPlanDetailForm from './MaintainPlanDetailForm.vue'
    export default {
        name:'MaintainPlanDetail',
        components: {MaintainPlanDetailForm},
        data() {
            return {
                // 遮罩层
                loading: true,
                // 导出遮罩层
                exportLoading: false,
                // 弹出层标题
                dialogTitle: "",
                // 是否显示弹出层
                dialogVisible: false,
                // 总条数
                total: 0,
                // 显示搜索条件
                showSearch: true,
                // 点检内容列表
                list: [],
                //保养计划
                maintainPlan: undefined,
                // 查询参数
                queryParams: {
                    pageNo: 1,
                    pageSize: 10,
                    equipMaintainPlanId: null,
                    isSpecial: null,
                    isReferto: null,
                    equipName: null,
                    equipSpecification: null,
                    maintainCycle: null,
                    replaceSelf: null,
                    details: null,
                },
                commonDict: [
                    {value:0, label:'否'},
                    {value:1, label:'是'}
                ],
            }
        },
        props: {},
        methods: {
            async open(plan){
                this.dialogVisible = true;
                this.maintainPlan = plan;
                this.dialogTitle = "《"+ this.maintainPlan.name + "》详情";
                this.queryParams.equipMaintainPlanId = plan.id;
                await this.getList();
            },
            handleQuery(){
                this.queryParams.pageNo = 1;
                this.getList();
            },
            resetQuery(){
                this.resetQueryParams();
                this.handleQuery();
            },
            async getList(){
                try {
                    this.loading = true;
                    const res = await MaintainDetailApi.getMaintainDetailPage(this.queryParams);
                    this.list = res.data.list;
                    this.total = res.data.total;
                } finally {
                    this.loading = false;
                }
            },
            resetQueryParams(){
                this.queryParams = {
                    pageNo: 1,
                    pageSize: 10,
                    equipMaintainPlanId: this.maintainPlan.id,
                    isSpecial: null,
                    isReferto: null,
                    equipName: null,
                    equipSpecification: null,
                    maintainCycle: null,
                    replaceSelf: null,
                    details: null,
                }
            },
            openForm(id){
                this.$refs["detailFormRef"].open(id);
            },
            /** 删除按钮操作 */
            async handleDelete(row) {
                await this.$modal.confirm('确定删除保养内容-->"' + row.details + '"?')
                try {
                    await MaintainDetailApi.deleteMaintainDetail(row.id);
                    await this.getList();
                    this.$modal.msgSuccess("删除成功");
                } catch {}
            },
            /** 导出按钮操作 */
            async handleExport() {
                await this.$modal.confirm('是否确认导出该保养计划《'+ this.maintainPlan.name +'》?');
                try {
                    this.exportLoading = true;
                    const res = await MaintainDetailApi.exportMaintainDetailExcel(this.queryParams);
                    this.$download.excel(res, '《'+ this.maintainPlan.name +'》表.xls');
                } catch {
                } finally {
                    this.exportLoading = false;
                }
            },
        }
    };
</script>