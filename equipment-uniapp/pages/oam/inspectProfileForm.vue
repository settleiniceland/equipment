<template>
	<view style="margin: 0 5%; max-height: 90vh; overflow-y: auto;">
		<view class="uni-box-head">
			<uni-title type="h1" align="center" :title="plan.name"></uni-title>
		</view>
		<uni-section title="基础信息" type="line">
			<uni-card>
				<uni-forms :modelValue="commonDetail">
					<uni-forms-item label="点检日期" name="inspectionDate">
						<button class="input-border" @click="showDatePicker = !showDatePicker">{{commonDetail.inspectionDate?commonDetail.inspectionDate:"请选择时间"}}</button>
					</uni-forms-item>
					<uni-forms-item label="点检人" name="inspectionUsers">
						<input class="input-border" type="text" v-model="commonDetail.inspectionUsers" placeholder="请输入点检人" />
					</uni-forms-item>
					<uni-forms-item label="区域负责人" name="dutyUsers">
						<input class="input-border" type="text" v-model="commonDetail.dutyUsers" placeholder="请输入区域负责人" />
					</uni-forms-item>
				</uni-forms>
			</uni-card>
		</uni-section>
		<uni-section title="结果信息" type="line">
			<uni-card>
				<view v-for="(item, index) in planDetailProfileList" :key="index">
					<uni-forms :modelValue="item">
						<uni-forms-item label="具体设备" name="equipProfileId">
							<button class="input-border" @click="showPicker(index,item.equipSelectList)">{{item.equipProfileId?item.equipCode+item.equipProfileName:'请选择设备'}}</button>
						</uni-forms-item>
						<uni-forms-item label="点检内容" name="details">
							<text>{{item.details}}</text>
						</uni-forms-item>
						<uni-forms-item label="结果" name="result">
							<uni-data-checkbox v-model="item.result" :localdata="resultType"/>
						</uni-forms-item>
						<uni-forms-item v-if="item.equipCode !== ''" label="现场图片" name="resultPhotos">
							<uni-section title="调用相机拍照上传图片" type="line">
								<!-- todo 此块需要安卓机实测，较浪费时间，最后搞 -->
								<view class="example-body">
									<uni-file-picker 
										ref="filePicker" 
										limit="9" 
										title="最多选择9张图片"
										:auto-upload="false"
										@select="(event) => addfile(event, item.equipCode)"
										@delete="(event) => delfile(event, item.equipCode)"
									/>
								</view>
							</uni-section>
						</uni-forms-item>
						<uni-forms-item v-if="item.result === 2" label="是否停机" name="isStop">
							<uni-data-checkbox v-model="item.isStop" :localdata="statusType"/>
						</uni-forms-item>
						<uni-forms-item label="结果详情" name="resultDetail">
							<textarea rows="9" class="textarea-border" v-model="item.resultDetail" placeholder="请输入详情"></textarea>
						</uni-forms-item>
					</uni-forms>
					<hr><br>
				</view>
			</uni-card>
		</uni-section>
		<button type="primary" @click="submit">Submit</button>
		<ba-tree-picker ref="treePicker" :multiple='false' @select-change="selectChange" title="选择具体设备"
			valueKey="id" textKey2="equipName" textKey1="code" textKey3="equipAttribute" childrenKey="children" />
		<mx-date-picker :show="showDatePicker" type="datetime" :showHoliday="false" :show-tips="true" 
			:show-seconds="true" @confirm="ed" @cancel="ed" />
	</view>
</template>

<script>
	import baTreePicker from "@/components/ba-tree-picker/ba-tree-picker.vue"
	import MxDatePicker from "@/components/mx-datepicker/mx-datepicker.vue";
	import * as InspectPlanApi from '@/api/pages/inspectplan.js';
	import { handleTreeForString } from "@/utils/ruoyi"
	import { initResumableAndUpload } from "@/utils/equip.js"
	export default{
		components: {baTreePicker,MxDatePicker},
		data(){
			return {
				plan: {},
				planDetailProfileList: [],
				commonDetail:{
					inspectionDate:undefined,
					inspectionUsers:undefined,
					dutyUsers:undefined,
				},
				showDatePicker: false,
				resultType:[
					{value:1,text:'正常'},
					{value:2,text:'异常'}
				],
				statusType:[
					{value:1,text:'开机'},
					{value:2,text:'停机'}
				],
				equipProfileMap: new Map([]),
				selectedFilesMap: new Map([]),//要上传的图片文件[key是设备编码，value就是图片文件数组]
			}
		},
		onShow() {
		    // 使用 i18n 设置导航栏标题
		    uni.setNavigationBarTitle({
		      title: this.$t("messages.words.text16")
		    });
		},
		async created(){
			this.plan = uni.getStorageSync("plan");
			await this.createEquipMap();
			this.planDetailProfileList = await this.buildPlanDetailProfile(uni.getStorageSync("planDetail"));
		},
		methods: {
			// 显示选择器
			showPicker(index,tree) {
				this.$refs.treePicker._show(index,tree);
			},
			//监听选择
			selectChange(item,index) {
				this.planDetailProfileList[index].equipProfileId = item.id;
				this.planDetailProfileList[index].equipProfileName = item.equipName;
				this.planDetailProfileList[index].equipCode = item.code;
				this.planDetailProfileList[index].equipAttribute = item.attribute
			},
			//选择
			ed(e) {
				this.showDatePicker = false;
				if(e) {
					this.commonDetail.inspectionDate = e.value;
				}
			},
			async submit(){
				const inspectionProfileList = [];
				for (const planDetailProfile of this.planDetailProfileList) {
					let rp='';
					if(this.selectedFilesMap.has(planDetailProfile.equipCode)&&this.selectedFilesMap.get(planDetailProfile.equipCode).length!==0){
						// let warn = '';
						// await initResumableAndUpload(this.selectedFilesMap.get(planDetailProfile.equipCode),planDetailProfile.equipCode)
						// 	.then(results => {
						// 		results.forEach(res => {
						// 			if(res.code === 0){
						// 				rp += res.value + '-_-';
						// 			}else{
						// 				warn += res.name + ",";
						// 		}
						// 	});
						// });
						// if(warn !== ''){
						// 	uni.showToast({
						// 	    title: '图片{' + warn + '}上传失败',
						// 	    icon: 'none', // 使用 'none' 不显示图标
						// 	});
						// }
						//这里先不上传文件，等下面表单校验通过后再真正上传，节省文件服务器空间
						rp='baik baik saja';
					}
					const inspectionProfile = {
						inspectionPlanId: this.plan.id,
						inspectionPlanName: this.plan.name,
						inspectionType: this.plan.inspectionType,
						inspectionCycle: this.plan.inspectionCycle,
						equiplocationId: this.plan.equiplocationId,
						equiplocationName: this.plan.equiplocationName,
						inspectionDetailId: planDetailProfile.id,
						inspectionDetail: planDetailProfile.details,
						equipProfileId: planDetailProfile.equipProfileId,
						equipProfileName: planDetailProfile.equipProfileName,
						equipCode: planDetailProfile.equipCode,
						equipAttribute: planDetailProfile.equipAttribute,
						resultPhotos: rp,
						isStop: planDetailProfile.result === 1?1:planDetailProfile.isStop,
						result: planDetailProfile.result,
						resultDetail: planDetailProfile.resultDetail,
						inspectionDate: this.commonDetail.inspectionDate,
						inspectionUsers: this.commonDetail.inspectionUsers,
						dutyUsers: this.commonDetail.dutyUsers,
					};
					if(Object.values(inspectionProfile).some((value, index) => {
						const key = Object.keys(inspectionProfile)[index];
						if(key === "resultDetail"){
							if((value === null || value === '' || value === undefined)
								&&
								inspectionProfile.result==2){
								return true;
							}
						}else if(key === "resultPhotos"){
							if((value === null || value === '' || value === undefined)
								&&
								inspectionProfile.inspectionType ==4){
								return true;
							}
						}else{
							if(value === null || value === '' || value === undefined){
								return true;
							}
						}
						return false;
					})){
						uni.showToast({
						  title: "未填写完整，请填写完整",
						  icon: 'none',
						  duration: 5000
						});
						return;
					}else{
						inspectionProfileList.push(inspectionProfile);
					}
				}
				if(inspectionProfileList.length === 0){
					uni.showToast({
					  title: "未填写完整，请填写完整",
					  icon: 'none',
					  duration: 5000
					});
					return;
				}
				try{
					for(const inspectionProfile of inspectionProfileList){
						//更改原逻辑，只有当全form都校验通过时才上传图片，节省文件服务器空间
						if(this.selectedFilesMap.has(inspectionProfile.equipCode)&&this.selectedFilesMap.get(inspectionProfile.equipCode).length!==0){
							let rp = '';
							let warn = '';
							await initResumableAndUpload(this.selectedFilesMap.get(inspectionProfile.equipCode),inspectionProfile.equipCode)
								.then(results => {
									results.forEach(res => {
										if(res.code === 0){
											rp += res.value + '-_-';
										}else{
											warn += res.name + ",";
									}
								});
							});
							if(warn !== ''){
								uni.showToast({
								    title: '图片{' + warn + '}上传失败',
								    icon: 'none', // 使用 'none' 不显示图标
								});
							}
							inspectionProfile.resultPhotos = rp;
						}
					}
					InspectPlanApi.addInspectProfileList(inspectionProfileList);
				}finally{
					await new Promise((resolve) => {
						uni.showToast({
						  title: "添加成功",
						  icon: 'success',
						  duration: 2000 
						});
						setTimeout(() => {
						  resolve();
						}, 2000);
					});
					this.$tab.reLaunch('/pages/index')
				}
			},
			async buildPlanDetailProfile(planDetail){
				for(let i=0;i<planDetail.length;i++){
					this.$set(planDetail[i],'equipCode','');
					this.$set(planDetail[i],'equipProfileId','');
					this.$set(planDetail[i],'equipProfileName','');
					this.$set(planDetail[i],'equipAttribute','');
					this.$set(planDetail[i],'isStop','');
					this.$set(planDetail[i],'result','');
					this.$set(planDetail[i],'resultPhotos','');
					this.$set(planDetail[i],'resultDetail','');
					//方法1，优化前的
					// const equipProfileList = await InspectPlanApi.getEquipmentProfile(uni.getStorageSync("plan").equiplocationId,planDetail[i].equipId);
					//方法2，优化后的
					const equipProfileList = this.equipProfileMap.get(planDetail[i].equipId);
					this.$set(planDetail[i],'equipSelectList',await handleTreeForString(equipProfileList,'id','supId'));
				}
				return planDetail;
			},
			async createEquipMap(){
				const allEquipProfileList = await InspectPlanApi.getEquipmentProfile(uni.getStorageSync("plan").equiplocationId);
				allEquipProfileList.data.forEach( equipProfile => {
					this.equipProfileMap.set(equipProfile.equipId,[]);
				})
				allEquipProfileList.data.forEach( equipProfile => {
					this.equipProfileMap.get(equipProfile.equipId).push(equipProfile);
				})
			},
			addfile(e,code){
				if(this.selectedFilesMap.has(code)){
					this.selectedFilesMap.get(code).push(...e.tempFiles);
				}else{
					this.selectedFilesMap.set(code,e.tempFiles);
				}
			},
			delfile(e,code){
				if(this.selectedFilesMap.has(code)){
					this.selectedFilesMap.set(
						code,
						this.selectedFilesMap.get(code).filter(item => item.uuid !== e.tempFile.uuid)
					);
				}else{
					console.log("some thing error in del file");
				}
			},
		}
	}
</script>

<style>
	.input-border {
		border: 1px solid #ccc;
		height: 40px;
		border-radius: 4px;
		width: 100%;
		box-sizing: border-box;
	}
	.textarea-border {
		border: 1px solid #ccc;
		border-radius: 4px;
		width: 100%;
		box-sizing: border-box;
	}
</style>