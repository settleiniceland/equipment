<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="73px">
      <el-form-item label="点检计划" prop="inspectionPlanId">
        <el-select v-model="queryParams.inspectionPlanId" placeholder="请选择点检计划" clearable filterable size="small">
          <el-option v-for="plan in allInspectPlan" :key="plan.id" :label="plan.name" :value="plan.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="周期" prop="inspectionCycle">
        <el-input v-model="queryParams.inspectionCycle" placeholder="请输入点检周期【单位:h】" clearable/>
      </el-form-item>
      <el-form-item label="点检内容" prop="inspectionDetail">
        <el-input v-model="queryParams.inspectionDetail" placeholder="请输入点检内容" clearable/>
      </el-form-item>
      <el-form-item label="设备编码" prop="equipCode">
        <el-input v-model="queryParams.equipCode" placeholder="请输入设备编码" clearable/>
      </el-form-item>
      <el-form-item label="设备属性" prop="equipAttribute">
        <el-select v-model="queryParams.equipAttribute" placeholder="请选择设备属性" clearable filterable size="small">
          <el-option v-for="attribute in equipAttributeDict" :key="attribute.id" :label="attribute.name" :value="attribute.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="是否停机" prop="isStop">
        <el-select v-model="queryParams.isStop" placeholder="是否停机" clearable filterable size="small">
          <el-option v-for="status in statusDict" :key="status.id" :label="status.name" :value="status.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="点检结果" prop="result">
        <el-select v-model="queryParams.result" placeholder="请选择点检类型" clearable filterable size="small">
          <el-option v-for="result in resultDict" :key="result.id" :label="result.name" :value="result.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="结果详情" prop="resultDetail">
        <el-input v-model="queryParams.resultDetail" placeholder="请输入点检结果详情" clearable/>
      </el-form-item>
      <el-form-item label="点检日期" prop="inspectionDate">
        <el-date-picker v-model="queryParams.inspectionDate" style="width: 200px" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="点检人" prop="inspectionUsers">
        <el-input v-model="queryParams.inspectionUsers" placeholder="请输入点检人" clearable/>
      </el-form-item>
      <el-form-item label="负责人" prop="dutyUsers">
        <el-input v-model="queryParams.dutyUsers" placeholder="请输入区域负责人" clearable/>
      </el-form-item>
      <el-form-item label="点检类型" prop="inspectionType">
        <el-select v-model="queryParams.inspectionType" placeholder="请选择点检类型" clearable filterable size="small">
          <el-option v-for="type in typeDict" :key="type.id" :label="type.name" :value="type.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="设备位置" prop="equiplocationId">
        <TreeSelect
            v-model="queryParams.equiplocationId"
            :options="installlocationTree"
            :normalizer="normalizer1"
            placeholder="请选择位置"
            class="treeSelectCSS"
          />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">{{ $t("message.Button.search") }}</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">{{ $t("message.Button.origin") }}</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['oam:inspection-profile:export']">{{ $t("message.Button.export") }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true" border>
      <el-table-column label="点检计划名称" align="center" prop="inspectionPlanName" />
      <el-table-column label="点检类型" align="center" prop="inspectionTypeName" />
      <el-table-column label="点检周期【单位h】" align="center" prop="inspectionCycle" />
      <el-table-column label="设备区域名称" align="center" prop="equiplocationName" />
      <el-table-column label="点击内容" align="center" prop="inspectionDetail" />
      <el-table-column label="设备档案名称" align="center" prop="equipProfileName" />
      <el-table-column label="设备是否停机" align="center" prop="isStopName" />
      <el-table-column label="设备档案编码" align="center" prop="equipCode" />
      <el-table-column label="设备属性" align="center" prop="equipAttributeName" />
      <el-table-column label="点检结果" align="center" prop="resultName" />
      <el-table-column label="现场图片" width="180">
        <template slot-scope="scope">
          <el-carousel v-if="scope.row.resultPhotos !== '' && scope.row.resultPhotos !== null" :interval="3000" arrow="always" height="15vh">
            <el-carousel-item v-for="(imgUrl, index) in processUrls(scope.row.resultPhotos)" :key="index">
              <img :src="imgUrl" 
                alt="图片加载失败" 
                style="width: 100%; height: 100%; object-fit: cover;"
                @click="showLargeImage(imgUrl)">
            </el-carousel-item>
          </el-carousel>
        </template>
      </el-table-column>
      <el-table-column label="点检结果详情" align="center" prop="resultDetail" />
      <el-table-column label="点检日期" align="center" prop="inspectionDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.inspectionDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="点检人" align="center" prop="inspectionUsers" />
      <el-table-column label="区域负责人" align="center" prop="dutyUsers" />
      <el-table-column :label='$t("message.Button.creator")' align="center" prop="creator" />
      <el-table-column :label='$t("message.Button.createTime")' align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label='$t("message.Button.modifier")' align="center" prop="updater" />
      <el-table-column :label='$t("message.Button.updateTime")' align="center" prop="updateTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label='$t("message.Button.operation")' align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['oam:inspection-profile:delete']">{{ $t("message.Button.del") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize" @pagination="getList"/>
    <!-- 大图展示弹窗 -->
    <el-dialog :visible.sync="LargeImgVisible" width="50%" center>
      <img :src="LargeImgUrl" alt="大图加载失败" style="width: 100%;"/>
    </el-dialog>  
  </div>
</template>

<script>
import * as InspectionProfileApi from '@/api/oam/inspectionprofile';
import * as InspectplanApi from '@/api/oam/inspectplan';
import * as InstalllocationApi from '@/api/property/installlocation';
import TreeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "InspectionProfile",
  components: {TreeSelect},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 点检日志表列表
      list: [],
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 选中行
      currentRow: {},
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        inspectionPlanId: null,
        inspectionDetail: null,
        equipCode: null,
        result: null,
        isStop: null,
        resultDetail: null,
        inspectionDate: [],
        inspectionUsers: null,
        dutyUsers: null,
        inspectionType: null,
        inspectionCycle: null,
        equiplocationId: null,
      },
      allInspectPlan:[],//所有计划列表
      typeDict:[
        {id:1, name:"普通点检"},
        {id:2, name:"重点点检"},
        {id:3, name:'专项点检'},
        {id:4, name:'拆检'}
      ],//点检类型字典
      resultDict:[
        {id:1, name:"正常"},
        {id:2, name:"异常"}
      ],//点检结果字典
      statusDict:[
        {id:1, name:"开机"},
        {id:2, name:"停机"}
      ],//设备状态字典
      equipAttributeDict: [
        {id:1, name:'设备组'},
        {id:2, name:'单体设备'},
        {id:3, name:'设备组件'}
      ],//设备属性字典
      installlocationTree:[],//设备安装位置树形结构
      LargeImgUrl: null,//大图url地址
      LargeImgVisible: false,//大图窗口开关
    };
  },
  created() {
    this.getList();
    this.getAllTreeAndList();
  },
  methods: {
    /** 查询列表 */
    async getList() {
      try {
        this.loading = true;
        const res = await InspectionProfileApi.getInspectionProfilePage(this.queryParams);
        this.list = res.data.list;
        this.total = res.data.total;
      } finally {
        this.loading = false;
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 删除按钮操作 */
    async handleDelete(row) {
      const id = row.id;
      await this.$modal.confirm('是否确认删除点检日志表编号为"' + id + '"的数据项?')
      try {
       await InspectionProfileApi.deleteInspectionProfile(id);
       await this.getList();
       this.$modal.msgSuccess("删除成功");
      } catch {}
    },
    /** 导出按钮操作 */
    async handleExport() {
      await this.$modal.confirm('是否确认导出所有点检日志表数据项?');
      try {
        this.exportLoading = true;
        const res = await InspectionProfileApi.exportInspectionProfileExcel(this.queryParams);
        this.$download.excel(res, '点检日志表.xls');
      } catch {
      } finally {
        this.exportLoading = false;
      }
    },
    /** 获取查询栏中所有所需要的树形结构或列表 */
    async getAllTreeAndList() {
      this.allInspectPlan = [];
      this.installlocationTree = [];
      const planRes = await InspectplanApi.selectAllPlan();
      this.allInspectPlan = planRes.data;//取得计划列表
      const InstalllocationRes = await InstalllocationApi.getInstalllocationList();
      this.installlocationTree = this.handleTreeForString(InstalllocationRes.data,'id','supId');//构建设备位置树
    },
    /** 转换设备类型数据结构 */
    normalizer1(node){
      return this.myNormalizer(node,"id","name");
    },
    /** 展示大图 */
    showLargeImage(url){
      this.LargeImgUrl = url;
      this.LargeImgVisible = true;
    },
    /** 处理url，将字符串处理为数组 */
    processUrls(node){
      return node.split('-_-').filter(item => item !== '');
    },
  }
};
</script>
