<template>
  <div class="app-container">
    <!-- 对话框(添加或修改) -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="75%" v-dialogDrag append-to-body>
      <el-container>
        <el-aside width="60%" style="padding: 1px;">
          <EquipmentprofileCheckTool ref="checkTool" :checkToolData="checkToolData"/>
        </el-aside>
        <el-container>
          <el-form ref="formRef" :model="formData" :rules="formRules" v-loading="formLoading" label-width="100px" :style="{ minHeight: '60vh' }">
            <el-form-item label="设备" prop="equipId">
              <TreeSelect
                v-model="formData.equipId"
                :options="equipTree"
                :normalizer="normalizer1"
                placeholder="请选择设备"
                @input="equipHandleSelect"
                v-if="addOrUpdate"
              />
              <el-input v-else v-model="formData.equipName" disabled/>
            </el-form-item>
            <el-form-item v-if="formData.equipId !== null && formData.equipId !== undefined" label="设备编码" prop="code">
              <el-input v-model="formData.code" placeholder="请输入设备编码" />
            </el-form-item>
            <el-form-item v-if="formData.equipId !== null && formData.equipId !== undefined && supEquipprofileList.length !== 0" label="上级设备" prop="supId">
              <el-select v-model="formData.supId" placeholder="请选择上级设备" clearable size="small">
                <el-option v-for="equipprofile in supEquipprofileList" :key="equipprofile.id" :label="equipprofile.equipName + equipprofile.code" :value="equipprofile.id"/>
              </el-select>
            </el-form-item>
            <el-form-item v-if="formData.equipAttribute === 2" label="生产厂家" prop="manufacturerId">
              <el-select v-model="formData.manufacturerId" placeholder="请选择生产厂家" clearable size="small" @change="manufacturerHandleSelect">
                <el-option v-for="manufacturer in manufacturerList" :key="manufacturer.id" :label="manufacturer.name" :value="manufacturer.id"/>
              </el-select>
            </el-form-item>
            <el-form-item v-if="formData.equipId !== null && formData.equipId !== undefined" label="设备负责人" prop="dutyName">
              <el-input v-model="formData.dutyName" placeholder="请输入设备负责人" />
            </el-form-item>
            <el-form-item v-if="formData.equipId !== null && formData.equipId !== undefined" label="安装日期" prop="installDate">
              <el-date-picker clearable v-model="formData.installDate" type="date" value-format="timestamp" placeholder="选择安装日期" />
            </el-form-item>
            <el-form-item v-if="formData.equipAttribute === 2" label="购买日期" prop="buyTime">
              <el-date-picker clearable v-model="formData.buyTime" type="date" value-format="timestamp" placeholder="选择购买日期" />
            </el-form-item>
            <el-form-item v-if="formData.equipAttribute === 2" label="设备技术手册地址" prop="fileUrls">
              <el-upload
                class="upload-demo"
                action="#"
                :auto-upload="false"
                :file-list="fileList"
                :on-remove="fileHandleRemove"
                :on-change="fileHandleChange"
              >
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                <div slot="tip" class="el-upload__tip">上传文件总大小必须小于700mb,且文件名不可包括“,”逗号</div>
              </el-upload>
            </el-form-item>
            <el-form-item v-if="formData.equipAttribute === 2" label="设备图片地址" prop="iconUrls">
              <el-upload
                action="#"
                list-type="picture-card"
                :auto-upload="false"
                :file-list="iconList"
                :on-change="iconHandleChange"
              >
                <i slot="default" class="el-icon-plus"></i>
                <div slot="file" slot-scope="{file}">
                  <img class="el-upload-list__item-thumbnail" :src="file.url" alt="图片加载失败">
                  <span class="el-upload-list__item-actions">
                    <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                      <i class="el-icon-zoom-in"></i>
                    </span>
                    <span class="el-upload-list__item-delete" @click="iconHandleRemove(file)">
                      <i class="el-icon-delete"></i>
                    </span>
                  </span>
                </div>
              </el-upload>
            </el-form-item>
            <el-form-item v-if="formData.equipId !== null && formData.equipId !== undefined" label="设备位置" prop="locationId">
              <TreeSelect
                v-model="formData.locationId"
                :options="installlocationTree"
                :normalizer="normalizer"
                placeholder="请选择位置"
              />
            </el-form-item>
            <el-form-item v-if="formData.equipId !== null && formData.equipId !== undefined" label="所属车间" prop="workshopId">
              <TreeSelect
                v-model="formData.workshopId"
                :options="deptTree"
                :normalizer="normalizer"
                placeholder="请选择车间"
                @input="workshopHandleSelect"
              />
            </el-form-item>
          </el-form>
        </el-container>
      </el-container>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :disabled="formLoading">{{ $t("message.Button.determine") }}</el-button>
        <el-button @click="dialogVisible = false">{{ $t("message.Button.cancel") }}</el-button>
      </div>
    </el-dialog>
    <!-- 大图展示页面 -->
    <el-dialog :visible.sync="dialogImageVisible" width="100vh" center>
      <img alt="大图加载失败" style="width: 100%;" :src="dialogImageUrl">
    </el-dialog>
    <!-- 上传进度条展示 -->
    <el-dialog :visible.sync="dialogUploadLoading" :show-close="false" width="80vh" center >
      <el-progress :percentage="uploadPercentage" :color="customColorMethod"></el-progress>
    </el-dialog>
  </div>
</template>

<script>
  import * as EquipmentprofileApi from '@/api/property/equipmentprofile';
  import * as EquipApi from '@/api/property/equip';
  import * as ManufacturerApi from '@/api/property/manufacturer';
  import * as InstalllocationApi from '@/api/property/installlocation';
  import {initResumableAndUpload , getUploadProgress , delFile} from '@/api/system/equip';
  import TreeSelect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import EquipmentprofileCheckTool from './EquipmentprofileCheckTool.vue';
  export default {
    name: "EquipmentprofileForm",
    components: {TreeSelect,EquipmentprofileCheckTool},
    data() {
      return {
        // 弹出层标题
        dialogTitle: "",
        // 是否显示弹出层
        dialogVisible: false,
        // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
        formLoading: false,
        // 表明是新增还是修改：true表示新增，false表示修改
        addOrUpdate: undefined,
        // 表单参数
        formData: {
          equipAttribute: undefined,//由设备id赋值
          code: undefined,//直接填入----------
          manufacturerId: undefined,//直接填入----------【属性为2时才填入】
          manufacturerName: undefined,//由厂家id赋值
          equipId: undefined,//直接填入----------
          equipName: undefined,//由设备id赋值
          equipSpecification: undefined,//由设备id赋值
          supId: undefined,//由设备id赋值
          equiptypeId: undefined,//由设备id赋值
          equiptypeName: undefined,//由设备id赋值
          dutyName: undefined,//直接填入----------
          status: undefined,//直接填入----------
          installDate: undefined,//直接填入----------
          buyTime: undefined,//直接填入----------【属性为2时才填入】
          fileUrls: undefined,//直接填入----------【属性为2时才填入】
          iconUrls: undefined,//直接填入----------【属性为2时才填入】
          locationId: undefined,//直接填入----------
          locationName: undefined,//直接填入----------
          workshopId: undefined,//直接填入----------
          workshopName: undefined,//由车间id填入
        },
        // 表单校验
        formRules: {
          equipAttribute: [{ required: true, message: '设备属性不能为空', trigger: 'blur' }],
          code: [{ required: true, message: '设备编码不能为空', trigger: 'blur' }],
          manufacturerId: [{ required: true, message: '生产厂家id不能为空', trigger: 'blur' }],
          manufacturerName: [{ required: true, message: '生产厂家名称不能为空', trigger: 'blur' }],
          equipId: [{ required: true, message: '设备id不能为空', trigger: 'blur' }],
          equipName: [{ required: true, message: '设备名称不能为空', trigger: 'blur' }],
          equipSpecification: [{ required: true, message: '设备规格不能为空', trigger: 'blur' }],
          supId: [{ required: true, message: '父设备档案id不能为空', trigger: 'blur' }],
          equiptypeId: [{ required: true, message: '设备类别id不能为空', trigger: 'blur' }],
          equiptypeName: [{ required: true, message: '设备类别名称不能为空', trigger: 'blur' }],
          dutyName: [{ required: true, message: '设备负责人不能为空', trigger: 'blur' }],
          status: [{ required: true, message: '设备状态不能为空', trigger: 'blur' }],
          installDate: [{ required: true, message: '安装日期不能为空', trigger: 'blur' }],
          buyTime: [{ required: true, message: '购买日期不能为空', trigger: 'blur' }],
          locationId: [{ required: true, message: '设备位置id不能为空', trigger: 'blur' }],
          workshopId: [{ required: true, message: '设备所属车间id不能为空', trigger: 'blur' }],
          workshopName: [{ required: true, message: '设备所属车间名称不能为空', trigger: 'blur' }],
        },
        /** 相关树型或列表数据 */
        deptTree:[],//部门树
        equipTree: [], // 设备树形结构
        installlocationTree: [],//设备安装位置树型结构
        manufacturerList: [],//生产厂家列表
        supEquipprofileList: [],//供选择的父级设备档案列表
        /** 文件上传相关属性 */
        //设备图片相关
        iconList: [],//展示用设备图片数组【老元素是伪文件对象，新元素是真文件对象】
        finalIconList: [],//最终确定的设备图片数组，接收上传请求返回来的url【元素是url字符串】
        addIconList: [],//要添加的新的设备图片数组【元素是文件对象】
        //设备技术文档相关
        fileList: [],//技术文件数组
        finalFileList: [],//最终确定的设备级数文档数组，接收上传请求返回来的url【元素是url字符串】
        addFileList: [],//要添加的新的设备技术文档文件
        //其他类
        dialogImageVisible: false,//图片大图是否展示
        dialogImageUrl: '',//大图地址
        delList: [],//记录要删除的文件，传给后端让其在minio中删除
        dialogUploadLoading: false,//上传进度条展示
        uploadPercentage: 0,//上传进度
      };
    },
    props:{
      checkToolData:{type:Array,required:true}
    },
    methods: {
      /** 打开弹窗 */
     async open(id) {
        this.dialogVisible = true;
        this.reset();
        this.addOrUpdate = undefined;
        // 修改时，设置数据
        if (id) {
          this.addOrUpdate = false;
          this.formLoading = true;
          try {
            const res = await EquipmentprofileApi.getEquipmentprofile(id);
            this.formData = res.data;
            this.dialogTitle = "修改设备档案数据";
            if(this.formData.iconUrls !== undefined && this.formData.iconUrls !== '' && this.formData.iconUrls !== null){
              this.formData.iconUrls.split('_-_').forEach(item => {
                this.iconList.push({name:item,url:item});
                this.finalIconList.push(item);
              });
            }
            if(this.formData.fileUrls !== undefined && this.formData.fileUrls !== '' && this.formData.fileUrls !== null){
              this.formData.fileUrls.split('_-_').forEach(item => {
                this.fileList.push({name:item.split('-_-')[0],url:item.split('-_-')[1]});
                this.finalFileList.push({name:item.split('-_-')[0],url:item.split('-_-')[1]});
              });
            }
          } finally {
            this.formLoading = false;
          }
        }else{
          this.addOrUpdate = true;
          this.dialogTitle = "新增设备档案数据";
        }
        await this.getALlTreeandAllList();
      },
      /** 提交按钮 */
      async submitForm() {
        // 校验主表
        await this.$refs["formRef"].validate();
        this.formLoading = true;
        try {
          this.formData.iconUrls='';
          this.formData.fileUrls='';
          //1、无论是修改还是新增，先把技术手册和图片上传,该删除的删除；并返回对应url赋值给data的相关属性
          this.uploadPercentage = 0;//进度清零
          this.dialogUploadLoading = true;//开启进度弹窗
          let uploadInterval = setInterval(this.fetchUploadProgress,1000);//开启定时任务查询进度
          if(this.addIconList.length!==0){//上传图片
            await initResumableAndUpload(this.addIconList,this.formData.code)
              .then(results => {
                this.disposeUploadResult(results,1)
              });
          }
          if(this.addFileList.length!==0){//上传文档
            await initResumableAndUpload(this.addFileList,this.formData.code)
              .then(results => {
                this.disposeUploadResult(results,2);
              });
          }
          this.dialogUploadLoading = false;//关闭进度弹窗
          if(uploadInterval){//关闭定时任务
            clearInterval(uploadInterval);
            uploadInterval = null;
          }
          if(this.delList.length!==0){//删除文件
            delFile(this.delList);
          }
          /* 最终图片地址与文件地址处理【先判断是否需要处理】 */
          if(this.formData.equipAttribute===2){
            for(let i=0;i<this.finalIconList.length;i++){
              if(i!==0){
                this.formData.iconUrls+="_-_";
              }
              this.formData.iconUrls+=this.finalIconList[i];
            }
            for(let i=0;i<this.finalFileList.length;i++){
              if(i!==0){
                this.formData.fileUrls+="_-_";
              }
              this.formData.fileUrls+=this.finalFileList[i].name+"-_-"+this.finalFileList[i].url;//这里
            }
          }
          //2、修改的提交
          if (this.formData.id) {
            await EquipmentprofileApi.updateEquipmentprofile(this.formData);
            this.$modal.msgSuccess("修改成功");
            this.dialogVisible = false;
            this.$emit('success');
            return;
          }else{
            // 添加的提交
            await EquipmentprofileApi.createEquipmentprofile(this.formData);
            this.$modal.msgSuccess("新增成功");
            this.dialogVisible = false;
            this.$emit('success');
          }
        } finally {
          this.formLoading = false;
        }
      },
      /** 获得所有所需要的树型结构 */
      async getALlTreeandAllList() {
        //设备树赋值
        this.equipTree = [];
        const res = await EquipApi.getEquipList();
        this.equipTree = this.handleTreeForString(res.data, 'id', 'supId');
        //生产厂家集合赋值
        this.manufacturerList = [];
        const res1 = await ManufacturerApi.getManufacturerList();
        this.manufacturerList = res1.data;
        //获取安装位置树形结构
        this.installlocationTree = [];
        const InstalllocationRes = await InstalllocationApi.getInstalllocationList();
        this.installlocationTree = this.handleTreeForString(InstalllocationRes.data,'id','supId');
        //部门树型结构
        this.deptTree = [];
        const deptRes = await EquipmentprofileApi.getAllDeptList();
        this.deptTree = this.handleTree(deptRes.data,'id');
      },
      /** 转换传统数据数据结构 */
      normalizer(node) {
        return this.myNormalizer(node,'id','name');
      },
      /** 表单重置 */
      reset() {
        this.formData = {
          equipAttribute: undefined,//由设备id赋值【属性有1，2，3】
          code: undefined,//直接填入----------
          manufacturerId: undefined,//直接填入----------【属性为2时才填入】
          manufacturerName: undefined,//由厂家id赋值
          equipId: undefined,//直接填入----------
          equipName: undefined,//由设备id赋值
          equipSpecification: undefined,//由设备id赋值
          supId: undefined,//由设备id赋值
          equiptypeId: undefined,//由设备id赋值
          equiptypeName: undefined,//由设备id赋值
          dutyName: undefined,//直接填入----------
          status: undefined,//直接填入----------
          installDate: undefined,//直接填入----------
          buyTime: undefined,//直接填入----------【属性为2时才填入】
          fileUrls: undefined,//直接填入----------【属性为2时才填入】
          iconUrls: undefined,//直接填入----------【属性为2时才填入】
          locationId: undefined,//直接填入----------
          locationName: undefined,//直接填入----------
          workshopId: undefined,//直接填入----------
          workshopName: undefined,//由车间id填入
        };
        this.resetForm("formRef");
        /** 其余辅助项 */
        this.delList = [];
        //图片地址相关数组清空
        this.iconList = [];
        this.finalIconList = [];
        this.addIconList = [];
        //技术文档地址相关数组清空
        this.fileList = [];
        this.finalFileList = [];
        this.addFileList = [];
        //可选父级设备档案列表清空
        this.supEquipprofileList = [];
      },
      /** 转换设备表数据结构 */
      normalizer1(node) {
        return this.myNormalizer(node,"id","equipName");
      },
      /** 选中设备项后给某些值做的自动赋值操作 */
      async equipHandleSelect(selectedValue){
        if(this.addOrUpdate){
          this.reset();
          this.formData.equipId = selectedValue;
        }
        const selectedNode = this.findNodeById(this.equipTree, selectedValue);
        if(selectedNode){
          //选中后给设备属性，设备名称，设备规格，设备类别id，设备类别名称这5个属性赋值，避免后端再连数据库查
          this.formData.equipAttribute = selectedNode.equipAttribute;
          this.formData.equipName = selectedNode.equipName;
          this.formData.equipSpecification = selectedNode.equipSpecification;
          this.formData.equiptypeId = selectedNode.equiptypeId;
          this.formData.equiptypeName = selectedNode.equiptypeName;
        }
        if(selectedNode.supId === "0"){
          this.formData.supId = "0";
        }else{
          const resp = await EquipmentprofileApi.getListByEquipId(selectedNode.supId);
          this.supEquipprofileList = resp.data;
          if(this.supEquipprofileList.length === 0){
            this.reset();
            alert("当前设备暂无上级设备实体，请先添加上级设备实体")
          }
        }
      },
      /** 选中安装位置项后自动赋值安装位置名称属性 */
      // locationHandleSelect(selectValue){
      //   const selectNode = this.findNodeById(this.installlocationTree, selectValue);
      //   if(selectNode){
      //     this.formData.locationName = selectNode.locationName;
      //   }【设备名称需要层级关系，所以必须后端一层一层查】
      // },
      /** 选中车间后自动给车间名称赋值 */
      workshopHandleSelect(selectedValue){
        const selectNode = this.findNodeById(this.deptTree, selectedValue);
        if(selectNode){
          this.formData.workshopName = selectNode.name;
        }
      },
      /** 选中厂家后自动赋值厂家名称 */
      manufacturerHandleSelect(selectedValue){
        for(const manufacturer of this.manufacturerList){
          if(manufacturer.id === selectedValue){
            this.formData.manufacturerName = manufacturer.name;
            break;
          }
        }
      },
      /** 图片大图展示 */
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogImageVisible = true;
      },
      /** 通用上传返回值处理(num为1为图片上传，num为2为文档上传) */
      disposeUploadResult(results,num){
        if(num === 1){
          let warn = '';
          results.forEach(result => {
            if(result.code !== 0){
              warn += result.name + ",";
            }else{
              this.finalIconList.push(result.value);
            }
          });
          if(warn !== ''){
            alert("图片{" + warn + "}上传失败，请稍后重试");
          }
        }else if(num === 2){
          let warn = '';
          results.forEach(result => {
            if(result.code !== 0){
              warn += result.name + ",";
            }else{
              this.finalFileList.push({name:result.name,url:result.value});
            }
          });
          if(warn !== ''){
            alert("文档{" + warn + "}上传失败，请稍后重试")
          }
        }
      },
      /** 通用文件删除处理(num为1为图片删除，num为2为文档删除) */
      commonRemove(file,num){
        if(num === 1){
          //1、先判断是老图片还是新图片；【判断url属性值是否以http开头，新图片的话一定不以http开头】
          if(file.url.startsWith('http')){//老图片的话在finalIconList中清除该图片,并记录在delList中
            for(let i=0;i<this.finalIconList.length;i++){
              if(this.finalIconList[i] === file.url){
                this.finalIconList.splice(i,1);
                break;
              }
            }
            this.delList.push(file.url);
          }else{//新图片的话在addIconList中清除掉
            for(let i=0;i<this.addIconList.length;i++){
              if(this.addIconList[i].uid === file.uid){
                this.addIconList.splice(i,1);
                break;
              }
            }
          }
        }else{
          //文件url以'http'开头，说明是老文件
          if(file.url !== undefined && file.url.startsWith('http')){
            //删老文件的话就在finalFileList列表里清除,并记录在delList中
            for(let i=0;i<this.finalFileList.length;i++){
              if(this.finalFileList[i].url === file.url){
                this.finalFileList.splice(i,1);
                break;
              }
            }
            this.delList.push(file.url);
          }else{//否则一律是新文件
            //删新文件的话就在addFileList列表里移除
            for(let i=0;i<this.addFileList.length;i++){
              if(this.addFileList[i].uid === file.uid){
                this.addFileList.splice(i,1);
                break;
              }
            }
          }
        }
      },
/**********************************************************************图片相关 */
      /** 删除图片文件 */
      iconHandleRemove(file) {
        this.commonRemove(file,1);
        //2、并在iconList清除掉该元素【即可清理el-upload的内部文件数组中该文件信息】
        for(let i=0;i<this.iconList.length;i++){
          if(this.iconList[i].url === file.url){
            this.iconList.splice(i,1);
            break;
          }
        }
      },
      /** 选择图片文件后及时处理数据 */
      iconHandleChange(file, fileList) {
        const isJPG = file.raw.type === 'image/jpeg';
        const isPNG = file.raw.type === 'image/png';
        if (!isJPG && !isPNG) {
          this.$message.error('只能上传 JPG 或 PNG 格式的图片!');
          this.dialogVisible = false;
          return false;
        }
        this.addIconList.push(file);
        this.iconList = fileList;
      },
/***********************************************************************技术文档相关 */
      /** 删除技术文档文件 */
      fileHandleRemove(file) {
        this.commonRemove(file,2);
      },
      /** 选择技术文档后及时处理数据 */
      fileHandleChange(file) {
        this.addFileList.push(file);
      },
      /** 实时获取下载进度 */
      fetchUploadProgress() {
        getUploadProgress().then(response => {
          this.uploadPercentage = response.data;
        }).catch(error => {
          alert("上传出现错误：",error)
        });
      },
      /** 进度条颜色随进度变化而变化 */
      customColorMethod(percentage) {
        if (percentage < 30) {
          return '#909399';
        } else if (percentage < 70) {
          return '#e6a23c';
        } else {
          return '#67c23a';
        }
      },
    }
  };
</script>
