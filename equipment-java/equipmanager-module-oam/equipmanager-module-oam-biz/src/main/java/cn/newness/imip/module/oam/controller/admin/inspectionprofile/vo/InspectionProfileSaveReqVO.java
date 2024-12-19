package cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo;

import cn.newness.imip.framework.jackson.core.databind.TimeFormatLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 点检日志表新增/修改 Request VO")
@Data
public class InspectionProfileSaveReqVO {
    @Schema(description = "主键id", example = "25532")
    private String id;

    @Schema(description = "点检计划id", example = "15588")
    private String inspectionPlanId;

    @Schema(description = "计划执行次数", example = "15588")
    private Integer planExecuteCount;

    @Schema(description = "点检计划名称【点检计划表中来】", example = "李四")
    private String inspectionPlanName;

    @Schema(description = "点检类型【点检计划表中来】", example = "2")
    private Integer inspectionType;

    @Schema(description = "点检周期【点检计划表中来】")
    private Long inspectionCycle;

    @Schema(description = "设备区域id【点检计划表中来】", example = "3043")
    private String equiplocationId;

    @Schema(description = "设备区域名称【点检计划表中来】", example = "张三")
    private String equiplocationName;

    @Schema(description = "点检内容id", example = "29138")
    private String inspectionDetailId;

    @Schema(description = "点击内容详情【点检内容表中来】")
    private String inspectionDetail;

    @Schema(description = "设备档案id【根据点检内容表中的设备id列一个列表供选择】", example = "7973")
    private String equipProfileId;

    @Schema(description = "设备档案名称【跟随档案id】", example = "李四")
    private String equipProfileName;

    @Schema(description = "是否停机【字典1开机；2停机】")
    private Integer isStop;

    @Schema(description = "设备档案编码【跟随档案id】")
    private String equipCode;

    @Schema(description = "设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "设备属性不能为空")
    private Integer equipAttribute;

    @Schema(description = "点检结果【字典1正常；2异常】")
    private Integer result;

    @Schema(description = "点检结果图片地址")
    private String resultPhotos;

    @Schema(description = "点检结果详情")
    private String resultDetail;

    @Schema(description = "点检日期")
    @JsonDeserialize(using = TimeFormatLocalDateTimeDeserializer.class)
    private LocalDateTime inspectionDate;

    @Schema(description = "点检人【每组数据之间用特殊符号隔开，每组数据又包含用户id和用户名，之间同样以特殊符号隔开】")
    private String inspectionUsers;

    @Schema(description = "区域负责人【每组数据之间用特殊符号隔开，每组数据又包含用户id和用户名，之间同样以特殊符号隔开】")
    private String dutyUsers;

    @Schema(description = "设备状态2,此属性与点检日志无关,有其他用处")
    private Integer status2;
}