package cn.newness.imip.module.oam.controller.admin.inspectplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 点检计划表新增/修改 Request VO")
@Data
public class InspectplanSaveReqVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30856")
    private String id;

    @Schema(description = "计划状态;0正常；1禁用", example = "2")
    @NotNull(message = "计划状态不能为空")
    private Integer status;

    @Schema(description = "点检类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "点检类型不能为空")
    private Integer inspectionType;

    @Schema(description = "点检周期【单位h】", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "点检周期【单位h】不能为空")
    private BigDecimal inspectionCycle;

    @Schema(description = "点检计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "点检计划名称不能为空")
    private String name;

    @Schema(description = "设备区域id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8544")
    @NotEmpty(message = "设备区域id不能为空")
    private String equiplocationId;

    @Schema(description = "设备区域名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    private String equiplocationName;

    @Schema(description = "设备区域负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    private String equiplocationDutyName;

    @Schema(description = "设备id")
    @NotEmpty(message = "设备id不能为空")
    private String equipId;

    @Schema(description = "设备名称")
    @NotEmpty(message = "设备名称不能为空")
    private String equipName;

    @Schema(description = "备注")
    private String detail;
}