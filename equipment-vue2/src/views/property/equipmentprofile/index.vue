<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备属性" prop="equipAttribute">
        <el-select v-model="queryParams.equipAttribute" placeholder="请选择设备属性" clearable size="small">
          <el-option v-for="dict in equipAttributeDictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
        </el-select>
      </el-form-item>
      <el-form-item label="设备编码" prop="code">
        <el-input v-model="queryParams.code" placeholder="请输入设备编码" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="生产厂家" prop="manufacturerName">
        <el-input v-model="queryParams.manufacturerName" placeholder="请输入生产厂家名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="设备" prop="equipId">
        <TreeSelect
          v-model="queryParams.equipId"
          :options="equipTree"
          :normalizer="normalizer"
          placeholder="请选择设备"
          class="treeSelectCSS"
        />
      </el-form-item>
      <el-form-item label="设备类别" prop="equiptypeId">
        <TreeSelect
            v-model="queryParams.equiptypeId"
            :options="equiptypeTree"
            :normalizer="normalizer1"
            placeholder="请选择设备类别"
            class="treeSelectCSS"
          />
      </el-form-item>
      <el-form-item label="负责人" prop="dutyName">
        <el-input v-model="queryParams.dutyName" placeholder="请输入设备负责人" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="启停状态" prop="status1">
        <el-select v-model="queryParams.status1" placeholder="请选择设备启停状态" clearable size="small">
          <el-option v-for="dict in status1DictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
        </el-select>
      </el-form-item>
      <el-form-item label="其他状态" prop="status1">
        <el-select v-model="queryParams.status2" placeholder="请选择设备其他状态" clearable size="small">
          <el-option v-for="dict in status2DictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
        </el-select>
      </el-form-item>
      <el-form-item label="安装日期" prop="installDate">
        <el-date-picker v-model="queryParams.installDate" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="购买日期" prop="buyTime">
        <el-date-picker v-model="queryParams.buyTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="设备位置" prop="locationId">
        <TreeSelect
            v-model="queryParams.locationId"
            :options="installlocationTree"
            :normalizer="normalizer1"
            placeholder="请选择位置"
            class="treeSelectCSS"
          />
      </el-form-item>
      <el-form-item label="所属车间" prop="workshopId">
        <TreeSelect
            v-model="queryParams.workshopId"
            :options="deptTree"
            :normalizer="normalizer1"
            placeholder="请选择部门"
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
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="openForm(undefined)"
                   v-hasPermi="['property:equipmentprofile:create']">{{ $t("message.Button.add") }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['property:equipmentprofile:export']">{{ $t("message.Button.export") }}</el-button>
      </el-col>
      <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-sort" size="mini" @click="toggleExpandAll">
              {{ $t("message.Button.unfoldFold") }}
            </el-button>
          </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      border
      v-loading="loading"
      :data="list"
      :show-overflow-tooltip="true"
      v-if="refreshTable"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      :row-class-name="getRowClassName"
    >
      <el-table-column label="设备图片"  width="220">
        <template slot-scope="scope">
          <el-carousel v-if="scope.row.iconUrls !== ''" :interval="3000" arrow="always" height="15vh">
            <el-carousel-item v-for="(imgUrl, index) in processUrls(scope.row.iconUrls)" :key="index">
              <img :src="imgUrl" 
                alt="图片加载失败" 
                style="width: 100%; height: 100%; object-fit: cover;"
                @click="showLargeImage(imgUrl)">
            </el-carousel-item>
          </el-carousel>
        </template>
      </el-table-column>
      <el-table-column label="设备属性" align="center" prop="equipAttributeName" show-overflow-tooltip />
      <el-table-column label="设备编码" align="center" prop="code" show-overflow-tooltip width="180"/>
      <el-table-column label="生产厂家" align="center" prop="manufacturerName" show-overflow-tooltip width="180"/>
      <el-table-column label="设备名称" align="center" prop="equipName" show-overflow-tooltip width="180"/>
      <el-table-column label="设备规格" align="center" prop="equipSpecification" show-overflow-tooltip width="180"/>
      <el-table-column label="上级设备" align="center" prop="supName" show-overflow-tooltip width="180"/>
      <el-table-column label="设备类别名称" align="center" prop="equiptypeName" show-overflow-tooltip />
      <el-table-column label="设备负责人" align="center" prop="dutyName" show-overflow-tooltip />
      <el-table-column label="设备启停状态" align="center" prop="status1Name" show-overflow-tooltip />
      <el-table-column label="其他状态" align="center" prop="status2Name" width="180"/>
      <el-table-column label="状态更新" align="center">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-s-tools" @click="openStatusUpdateForm(scope.row)"
                    v-hasPermi="['property:equipmentprofile:update']"
                    :disabled="scope.row.status2 === 7">
                    {{ $t("message.Button.update") }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="安装日期" align="center" prop="installDate" show-overflow-tooltip width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.installDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="购买日期" align="center" prop="buyTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.buyTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="设备位置" align="center" prop="locationName" show-overflow-tooltip width="180"/>
      <el-table-column label="设备所属车间" align="center" prop="workshopName" show-overflow-tooltip width="180"/>
      <el-table-column :label='$t("message.Button.creator")' align="center" prop="creator" show-overflow-tooltip width="180"/>
      <el-table-column :label='$t("message.Button.createTime")' align="center" prop="createTime" show-overflow-tooltip width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label='$t("message.Button.modifier")' align="center" prop="updater" show-overflow-tooltip width="180"/>
      <el-table-column :label='$t("message.Button.updateTime")' align="center" prop="updateTime" show-overflow-tooltip width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label='$t("message.Button.operation")' align="center" width="180">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="openForm(scope.row.id)"
                    v-hasPermi="['property:equipmentprofile:update']">{{ $t("message.Button.update") }}</el-button>
          <el-button  v-if="scope.row.qrCode !== null" size="mini" type="text" icon="el-icon-full-screen" 
                    @click="downloadQR(scope.row.qrCode)">下载二维码</el-button>
          <br>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                    v-hasPermi="['property:equipmentprofile:delete']">{{ $t("message.Button.del") }}</el-button>
          <el-button  v-if="scope.row.fileUrls !== ''" size="mini" type="text" icon="el-icon-folder-opened" 
                    @click="downloadFiles(scope.row.fileUrls)">下载技术文档</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 对话框(添加 / 修改) 【现无添加/修改状态的功能】-->
    <EquipmentprofileForm ref="formRef" :checkToolData="list" @success="getList"/>
    <!-- 状态修改对话框【所有的状态】 -->
    <EquipmentprofileStatusUpdateForm ref="statusFromRef" 
      :status1DictDatas="status1DictDatas" 
      :status2DictDatas="status2DictDatas" 
      @success="getList"/>
    <!-- 大图展示弹窗 -->
    <el-dialog :visible.sync="LargeImgVisible" width="50%" center>
      <img :src="LargeImgUrl" alt="大图加载失败" style="width: 100%;"/>
    </el-dialog>
  </div>
</template>

<script>
  import * as EquipmentprofileApi from '@/api/property/equipmentprofile';
  import * as EquipApi from '@/api/property/equip';
  import * as EquiptypeApi from '@/api/property/equiptype';
  import * as InstalllocationApi from '@/api/property/installlocation';
  import EquipmentprofileStatusUpdateForm from './EquipmentprofileStatusUpdateForm.vue'
  import EquipmentprofileForm from './EquipmentprofileForm.vue';
  import TreeSelect from "@riophae/vue-treeselect";
  import {EquipStatusEnum} from "@/utils/constants";
  export default {
    name: "Equipmentprofile",
    components: {EquipmentprofileForm,TreeSelect,EquipmentprofileStatusUpdateForm},
    data() {
      return {
        // 遮罩层
        loading: true,
        // 导出遮罩层
        exportLoading: false,
        // 显示搜索条件
        showSearch: true,
        // 设备档案数据列表
        list: [],
        // 是否展开，默认全部展开
        isExpandAll: true,
        // 重新渲染表格状态
        refreshTable: true,
        // 选中行
        currentRow: {},
        // 查询参数
        queryParams: {
          equipAttribute: null,
          code: null,
          manufacturerName: null,
          equipId: null,
          equiptypeId: null,
          dutyName: null,
          status1: null,
          status2: null,
          installDate: [],
          buyTime: [],
          locationId: null,
          workshopId: null,
        },
        //设备属性字典
        equipAttributeDictDatas:[
          {value:1,label:'设备组'},
          {value:2,label:'设备'},
          {value:3,label:'设备组件'}
        ],
        //设备启停状态字典
        status1DictDatas:[
          {value:1,label:'开启'},
          {value:2,label:'关闭'}
        ],
        //设备其他状态字典
        status2DictDatas:[
          {value:3,label:'异动中'},
          {value:4,label:'异动完毕'},
          {value:5,label:'回国返修中'},
          {value:6,label:'回国返修完毕'},
          {value:7,label:'报废'}
        ],
        equipTree: [], // 设备树形结构
        equiptypeTree:[],//设备类别树形结构
        installlocationTree:[],//设备安装位置树形结构
        deptTree:[],//部门树
        toolMap: new Map(),//map工具
        LargeImgVisible: false,//大图窗口开关
        LargeImgUrl: null,//大图url地址
      };
    },
    created() {
      this.getList();
      this.getAllTree();
    },
    methods: {
      /** 查询列表 */
      async getList() {
        try {
          this.loading = true;
          const res = await EquipmentprofileApi.getEquipmentprofileList(this.queryParams);
          res.data.forEach(element => {
            this.toolMap.set(element.id,element);
          });
          res.data.forEach(element => {
            for(const item of this.equipAttributeDictDatas){
              if(item.value === element.equipAttribute){
                element.equipAttributeName=item.label;
                break;
              }
            }
            for(const item of this.status1DictDatas){
              if(item.value === element.status1){
                element.status1Name=item.label;
                break;
              }
            }
            for(const item of this.status2DictDatas){
              if(item.value === element.status2){
                element.status2Name=item.label;
              }
            }
            if(element.supId!=="0"){
                const f = this.toolMap.get(element.supId);
                element.supName=f?f.equipName:"";
              }else{
                element.supName="---";
              }
          });
          this.list = this.handleTreeForString(res.data, 'id', 'supId');
        } finally {
          this.loading = false;
        }
      },
      /** 获取所有所需要的树形结构 */
      async getAllTree(){
        this.equipTree = [];
        this.equiptypeTree = [];
        this.installlocationTree = [];
        this.deptTree = [];
        const equipRes = await EquipApi.getEquipList();
        this.equipTree = this.handleTreeForString(equipRes.data,'id','supId');//构建设备树
        const equipTypeRes = await EquiptypeApi.getEquiptypeList();
        this.equiptypeTree = this.handleTreeForString(equipTypeRes.data,'id','supId');//构建设备类型树
        const InstalllocationRes = await InstalllocationApi.getInstalllocationList();
        this.installlocationTree = this.handleTreeForString(InstalllocationRes.data,'id','supId');//构建设备位置树
        const deptRes = await EquipmentprofileApi.getAllDeptList();
        this.deptTree = this.handleTree(deptRes.data,'id');//构建部门树
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
      /** 添加/修改操作 */
      openForm(id) {
        this.$refs["formRef"].open(id);
      },
      /** 状态更新 */
      openStatusUpdateForm(row) {
        this.$refs["statusFromRef"].open(row);
      },
      /** 删除按钮操作 */
      async handleDelete(row) {
        const id = row.id;
        const code = row.code;
        const equipAttribute = row.equipAttribute;
        await this.$modal.confirm('是否确认删除设备档案"' + code + row.equipName + '"?')
        try {
        await EquipmentprofileApi.deleteEquipmentprofile(id,code,equipAttribute);
        await this.getList();
        this.$modal.msgSuccess("删除成功");
        } catch {}
      },
      /** 导出按钮操作 */
      async handleExport() {
        await this.$modal.confirm('是否确认导出所有设备档案数据数据项?');
        try {
          this.exportLoading = true;
          const res = await EquipmentprofileApi.exportEquipmentprofileExcel(this.queryParams);
          this.$download.excel(res, '设备档案数据.xls');
        } catch {
        } finally {
          this.exportLoading = false;
        }
      },
      /** 展开/折叠操作 */
      toggleExpandAll() {
        this.refreshTable = false
        this.isExpandAll = !this.isExpandAll
        this.$nextTick(function () {
          this.refreshTable = true
        })
      },
      //下载二维码文件
      async downloadQR(row){
        if(row === null || row === ''){
          alert("无存储文件");
        }
        this.downloadFile(row);
      },
      //下载技术文档
      async downloadFiles(row){
        if(row === null || row === ''){
          alert("无存储文件");
        }
        const fileUrls = this.processUrls(row);
        fileUrls.forEach(url => {
          this.downloadFile(url.split('-_-')[1]);
        })
      },
      //下载方法
      async downloadFile(url) {
        const errorWords='download file fail,please try again later'
        try {
          const response = await fetch(url);//发送请求，返回一个 Promise 对象，这个 Promise 解析为一个 Response 对象。这个 Response 对象代表了 HTTP 响应的完整信息，包括状态码、头部和响应体等。
          if(!response.ok){
            throw new Error(errorWords);
          }
          const fileName = url.split('/').pop();//获取文件名
          const blob = await response.blob();//将响应转换为 Blob 对象。Blob 对象可以表示文件数据。
          const a = document.createElement('a');//创建一个a标签
          a.href = URL.createObjectURL(blob);// 创建一个指向 Blob 对象的临时 URL并赋给a标签href属性
          a.download = fileName;//设置下载的文件名
          a.style.display = 'none';//隐藏a标签
          document.body.appendChild(a);//将链接添加到页面
          a.click();//点击下载
          document.body.removeChild(a);//将链接从页面移除
          URL.revokeObjectURL(a.href);//释放之前创建的 Blob URL。
        } catch (error) {
          console.log("download fail reason is:",error);
          throw new Error(errorWords);
        }          
      },
      /** 转换设备表数据结构 */
      normalizer(node){
        return this.myNormalizer(node,'id','equipName');
      },
      /** 转换设备类型数据结构 */
      normalizer1(node){
        return this.myNormalizer(node,"id","name");
      },
      /** 处理url，将字符串处理为数组 */
      processUrls(node){
        return node.split('_-_');
      },
      /** 展示大图 */
      showLargeImage(url){
        this.LargeImgUrl = url;
        this.LargeImgVisible = true;
      },
      //每一层的颜色
      getRowClassName({ row }) {
        return `equipmentprofile-level-${row.equipAttribute}`;
      },
      // 单体设备状态更改
      async handleStatusChange(row){
        let text = row.status1 === EquipStatusEnum.ENABLE ? "启用" : "停用";
        this.$modal.confirm('确认要"' + text + '""' + row.equipName + row.code + '"吗?').then(async () => {
          await EquipmentprofileApi.updateEquipmentprofile(row);
          await this.getList();
          this.$modal.msgSuccess(text + "成功");   
        }).catch(function() {
          row.status1 = row.status1 === EquipStatusEnum.ENABLE ? EquipStatusEnum.DISABLE
              : EquipStatusEnum.ENABLE;
        });
      }
    }
  };
</script>
<style lang="scss" >
  .equipmentprofile-level-1 {
    background-color: #ffffff !important;
  }
  .equipmentprofile-level-2 {
    background-color: #45cc57 !important;
  }
  .equipmentprofile-level-3 {
    background-color: #ceec49 !important;
  }
</style>