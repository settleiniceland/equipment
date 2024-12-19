package cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 点检内容新增/修改 Request VO")
@Data
public class InspectionSubstanceSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16781")
    private String id;

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22983")
    @NotEmpty(message = "设备id不能为空")
    private String equipId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "设备名称不能为空")
    private String equipName;

    @Schema(description = "设备规格")
    private String equipSpecification;

    @Schema(description = "点检内容")
    private String details;

    @Schema(description = "点检标准")
    private String standard;
}