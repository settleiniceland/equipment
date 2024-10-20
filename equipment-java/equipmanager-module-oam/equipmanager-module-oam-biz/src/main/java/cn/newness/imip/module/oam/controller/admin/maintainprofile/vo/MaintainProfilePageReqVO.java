package cn.newness.imip.module.oam.controller.admin.maintainprofile.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.newness.imip.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.newness.imip.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 保养日志表分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaintainProfilePageReqVO extends PageParam {

    @Schema(description = "主键id", example = "24574")
    private String id;

    @Schema(description = "保养计划id", example = "14747")
    private String equipMaintainPlanId;

    @Schema(description = "保养计划名称", example = "王五")
    private String equipMaintainPlanName;

    @Schema(description = "保养内容id", example = "3351")
    private String equipMaintainDetailId;

    @Schema(description = "保养内容")
    private String equipMaintainDetail;

    @Schema(description = "是否特殊设备【字典：0否；1是】")
    private Integer isSpecial;

    @Schema(description = "设备档案id", example = "25751")
    private String equipmentprofileId;

    @Schema(description = "设备档案编码")
    private String equipmentprofileCode;

    @Schema(description = "设备名称", example = "李四")
    private String equipName;

    @Schema(description = "保养周期")
    private BigDecimal maintainCycle;

    @Schema(description = "是否更换自身【字典：0否；1是】")
    private Integer replaceSelf;

    @Schema(description = "保养图片【地址，中间以-_-隔开】")
    private String resultPhotos;

    @Schema(description = "保养日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] maintainDate;

    @Schema(description = "计划执行部门id【从保养计划中来】", example = "4331")
    private String executeDeptId;

    @Schema(description = "计划执行部门名称【从保养计划中来】", example = "张三")
    private String executeDeptName;

    @Schema(description = "实际保养人")
    private String actualMaintainNames;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "更新者")
    private String updater;

    @Schema(description = "更新时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] updateTime;

}