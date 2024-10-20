package cn.newness.imip.module.oam.controller.admin.statusrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.newness.imip.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 停机表新增/修改 Request VO")
@Data
public class StatusRecordSaveReqVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6350")
    private String id;

    @Schema(description = "设备档案id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11737")
    @NotEmpty(message = "设备档案id不能为空")
    private String equipmentprofileId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "设备名称不能为空")
    private String equipName;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备编码不能为空")
    private String equipCode;

    @Schema(description = "操作类型【字典:1点检结果异常:2手动操作】")
    private Integer operationType;

    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime operationTime;

    @Schema(description = "改变详情")
    private String changeDetails;

    @Schema(description = "启停状态【字典：1开机；2停机】")
    private Integer newStatus1;

    @Schema(description = "异动返修等状态【字典：3异动中；4异动完毕；5回国返修中；6回国返修完毕；7报废】")
    private Integer newStatus2;

    @Schema(description = "详情")
    private String details;

}