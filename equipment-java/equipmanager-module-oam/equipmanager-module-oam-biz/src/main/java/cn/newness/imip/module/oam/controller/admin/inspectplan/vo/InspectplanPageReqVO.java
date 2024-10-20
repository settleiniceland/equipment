package cn.newness.imip.module.oam.controller.admin.inspectplan.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.newness.imip.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.newness.imip.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 点检计划表分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InspectplanPageReqVO extends PageParam {

    @Schema(description = "主键id", example = "30856")
    private String id;

    @Schema(description = "计划状态;0正常；1禁用", example = "2")
    private Integer status;

    @Schema(description = "点检类型【字典：1普通点检；2重点点检；】", example = "2")
    private Integer inspectionType;

    @Schema(description = "点检周期【单位h】")
    private BigDecimal inspectionCycle;

    @Schema(description = "点检计划名称", example = "张三")
    private String name;

    @Schema(description = "设备区域id", example = "8544")
    private String equiplocationId;

    @Schema(description = "设备区域名称", example = "王五")
    private String equiplocationName;

    @Schema(description = "备注")
    private String detail;

}