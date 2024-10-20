package cn.newness.imip.module.oam.controller.admin.flaws.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.newness.imip.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.newness.imip.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 缺陷库分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FlawsPageReqVO extends PageParam {

    @Schema(description = "主键id", example = "14140")
    private String id;

    @Schema(description = "设备档案id", example = "24626")
    private String equipProfileId;

    @Schema(description = "设备档案名称", example = "张三")
    private String equipProfileName;

    @Schema(description = "设备档案编码")
    private String equipCode;

    @Schema(description = "设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】")
    private Integer equipAttribute;

    @Schema(description = "缺陷状态【字典：1待排维修计划；2已排计划待维修；3已维修】", example = "1")
    private Integer status;

    @Schema(description = "是否停机【字典：1开机；2停机】")
    private Integer isStop;

    @Schema(description = "缺陷详情")
    private String details;

    @Schema(description = "缺陷开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] beginTime;

    @Schema(description = "缺陷解决时间【缺陷状态为 3 时才有此值】")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] solveTime;

    @Schema(description = "对应维修计划id【缺陷状态为 2或3 时才有此值】", example = "22454")
    private String fixPlanId;

    @Schema(description = "对应维修计划名称【缺陷状态为 2或3 时才有此值】", example = "22454")
    private String fixPlanName;

    @Schema(description = "维修时长【单位h,可小数】【缺陷状态为 3 时才有此值】")
    private Long solveDuration;

    @Schema(description = "缺陷解决车间id【缺陷状态为 3 时才有此值】", example = "24226")
    private String solveDeptId;

    @Schema(description = "缺陷解决车间名称【缺陷状态为 3 时才有此值】", example = "赵六")
    private String solveDeptName;

    @Schema(description = "缺陷解决人数【缺陷状态为 3 时才有此值】")
    private Integer solveHumanNum;

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