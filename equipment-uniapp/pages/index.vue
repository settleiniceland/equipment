<template>
	<view class="help-container content">
		<image style="width: 500rpx;" src="/static/logo.png" mode="widthFix"></image>
		<!-- 点巡检计划 -->
		<view class="text-title">
			<uni-icons type="navigate-filled" size="20"/>
			{{$t("messages.words.text39")}}
		</view>
		<view class="childList">
			<view v-for="(item, zindex) in inspectplanList" 
					:key="zindex" 
					class="question" 
					hover-class="hover" 
					@click="handleInspectplan(item)"
					:style="{ backgroundColor: item.timeUp ? '#ff5e9f' : 'transparent' }">
				<view class="text-item">{{ item.name }}</view>
			</view>
		</view>
		<br>
		<!-- 维修计划 -->
		<view class="text-title">
			<uni-icons type="gear" size="20"/>
			{{$t("messages.words.text40")}}
		</view>
		<view class="childList">
2222
		</view>
		<br>
		<!-- 保养计划 -->
		<view class="text-title">
			<uni-icons type="map-pin-ellipse" size="20"/>
			{{$t("messages.words.text41")}}
		</view>
		<view class="childList">
			<view v-for="(item, zindex) in maintainplanList"
					:key="zindex" 
					class="question" 
					hover-class="hover" 
					@click="handleMaintainplan(item)">
				<view class="text-item">{{ item.name }}</view>
			</view>
		</view>
		<!-- 点巡检计划详情弹出框 -->
		<uni-popup ref="inspectPlanPopup" class="my-popul">
			<view class="my-popul-view">
				<uni-title type="h1" :title="inspectPlan.name" align="center"/>
				<uni-section class="mb-10" :title='$t("messages.words.text42")' :sub-title="inspectPlan.equiplocationName"/>
				<uni-section class="mb-10" :title='$t("messages.words.text43")' :sub-title="String(inspectPlan.inspectionCycle)"/>
				<uni-section class="mb-10" :title='$t("messages.words.text44")' :sub-title="inspectionTypeText"/>
				<uni-section class="mb-10" :title='$t("messages.words.text45")' :sub-title="inspectPlan.detail"/>
				<uni-section class="mb-10" :title='$t("messages.words.text46")' :sub-title='inspectPlan.timeUp ? $t("messages.words.text48") : $t("messages.words.text49")'/>
				<uni-section class="mb-10" :title='$t("messages.words.text47")'>
					<view v-for="(item, zindex) in inspectplanSubstanceList" 
						:key="zindex" 
						class="question" 
						hover-class="hover">
						<uni-section class="my-xxplan-detail mb-10" :title="item.equipName" :sub-title="item.details"/>
					</view>
				</uni-section>
				<button class="button" type="primary" @click="addProfile">{{$t("messages.words.text50")}}</button>
			</view>
		</uni-popup>
		<!-- 保养计划详情弹出框 -->
		<uni-popup ref="maintainPlanPopup" class="my-popul">
			<view class="my-popul-view">
				<uni-title type="h1" :title="maintainPlan.name" align="center"/>
				<uni-section class="mb-10" title='执行部门名称' :sub-title="maintainPlan.executeDeptName"/>
				<uni-section class="mb-10" title='备注' :sub-title="maintainPlan.remark"/>
				<view v-for="(item, zindex) in maintainDetailList"
					:key="zindex" 
					class="question" 
					hover-class="hover">
					<uni-section class="my-xxplan-detail mb-10" :title="item.details" :sub-title="item.maintainCycle+'(h)'">
						<view class="detail-label">是否特殊设备：</view>
						<view>{{ item.isSpecialName }}</view><br>
						<view class="detail-label">是否有参照对象：</view>
						<view>{{ item.isRefertoName }}</view><br>
						<view class="detail-label">设备名称：</view>
						<view>{{ item.equipName }}</view><br>
						<view class="detail-label">设备规格：</view>
						<view>{{ item.equipSpecification }}</view><br>
						<view class="detail-label">是否更换自身：</view>
						<view>{{ item.replaceSelfName }}</view><br>
						<button class="button" type="primary" @click="addMaintainProfile">{{$t("messages.words.text50")}}</button>
					</uni-section>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import * as InspectPlanApi from '@/api/pages/inspectplan.js';
	import * as MaintainPlanApi from '@/api/pages/maintainplan.js'
	export default {
		onLoad: function() {},
		data() {
			return {
				InspectplanQueryParams: {
					timeUp: false,
					inspectionType: undefined,
					inspectionCycle: undefined,
					name: undefined,
					equiplocationId: undefined,
					equiplocationName: undefined,
					detail: undefined
				},
				MaintainplanQueryParams: {
					name: undefined,
					executeDeptName: undefined,
					status: 0,
					remark: undefined,
				},
				inspectplanList: [],
				inspectPlan: {},
				inspectplanSubstanceList: [],
				maintainplanList: [],
				maintainPlan: {},
				maintainDetailList: [],
			}
		},
		onShow() {
		    // 使用 i18n 设置导航栏标题
		    uni.setNavigationBarTitle({
		      title: this.$t("messages.words.text18")
		    });
		},
		computed: {
		    inspectionTypeText() {
		      switch (this.inspectPlan.inspectionType) {
		        case 1:
		          return '普通点检';
		        case 2:
		          return '重点点检';
		        case 3:
		          return '专项点检';
		        case 4:
		          return '拆检';
		        default:
		          return '未知点检类型'; 
		      }
		    }
		},
		created() {
			this.getList();
		},
		methods: {
			async handleInspectplan(item) {
				this.inspectPlan = item;
				const substanceList = await InspectPlanApi.getInspectSubstanceListByPlanId(item.id);
				this.inspectplanSubstanceList = substanceList.data;
				this.$refs.inspectPlanPopup.open('left');
			},
			async getList(){
				const inspectplanRes = await InspectPlanApi.getInspectPlanList(this.InspectplanQueryParams);
				this.inspectplanList = inspectplanRes.data;
				const maintainplanRes = await MaintainPlanApi.getMaintainPlanList(this.MaintainplanQueryParams);
				this.maintainplanList = maintainplanRes.data;
			},
			addProfile(){
				uni.setStorageSync("plan",this.inspectPlan);
				uni.setStorageSync("planDetail",this.inspectplanSubstanceList);
				this.$tab.navigateTo('/pages/oam/inspectProfileForm');
			},
			async handleMaintainplan(item) {
				this.maintainPlan = item;
				const params = {
					equipMaintainPlanId: item.id,
				}
				const maintaindetailRes = await MaintainPlanApi.getMaintainDetailList(params);
				this.maintainDetailList = maintaindetailRes.data;
				this.$refs.maintainPlanPopup.open('left');
			},
			addMaintainProfile(){
				this.$modal.showToast('模块建设中~')
			}
		}
	}
</script>

<style>
  .content {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .logo {
    height: 200rpx;
    width: 200rpx;
    margin-top: 200rpx;
    margin-left: auto;
    margin-right: auto;
    margin-bottom: 50rpx;
  }

  .text-area {
    display: flex;
    justify-content: center;
  }

  .title {
    font-size: 36rpx;
    color: #8f8f94;
  }
  
  .help-container {
    margin-bottom: 100rpx;
    padding: 30rpx;
  }
  
  .list-title {
    margin-bottom: 30rpx;
  }
  
  .childList {
    background: #ffffff;
    box-shadow: 0px 0px 10rpx rgba(193, 193, 193, 0.2);
    border-radius: 16rpx;
    margin-top: 10rpx;
	width: 700rpx;
	max-height: 300rpx; 
	overflow-y: auto;
  }
  
  .line {
    width: 100%;
    height: 1rpx;
    background-color: #F5F5F5;
  }
  
  .text-title {
    color: #303133;
    font-size: 32rpx;
    font-weight: bold;
    margin-left: 10rpx;
  
    .iconfont {
      font-size: 16px;
      margin-right: 10rpx;
    }
  }
  
  .text-item {
    font-size: 28rpx;
    padding: 24rpx;
  }
  
  .question {
    color: #606266;
    font-size: 28rpx;
  }
  
  .my-popul {
	border-radius: "0 20px 20px 0 !important";
	background-color: "#fff";
  }
  
  .my-popul-view {
	width: 600rpx;
	max-height: 90vh;
	overflow-y: auto;
  }
  
  .my-xxplan-detail {
	background-color: #ddf0d8;
	border: 1px solid #ddd;
	padding: 10px;
	margin-bottom: 10px;
  }
  
  .detail-label {
	font-weight: bold; /* 字体加粗 */
	font-family: "KaiTi", "楷体", serif; /* 设置楷体字体 */
	font-size: 18px; /* 假设普通的view字号是14px，这里比普通view大4px */
  }
</style>