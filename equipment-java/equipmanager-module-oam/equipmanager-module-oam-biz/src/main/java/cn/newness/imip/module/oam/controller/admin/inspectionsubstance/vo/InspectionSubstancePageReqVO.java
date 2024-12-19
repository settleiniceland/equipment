package cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.newness.imip.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.newness.imip.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 点检内容分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InspectionSubstancePageReqVO extends PageParam {

    @Schema(description = "id", example = "16781")
    private String id;

    @Schema(description = "点检计划Id", example = "16781")
    private String planId;

    @Schema(description = "点检计划名称", example = "16781")
    private String planName;

    @Schema(description = "设备id", example = "22983")
    private String equipId;

    @Schema(description = "设备名称", example = "王五")
    private String equipName;

    @Schema(description = "设备规格")
    private String equipSpecification;

    @Schema(description = "点检内容")
    private String details;

    @Schema(description = "点检标准")
    private String standard;

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