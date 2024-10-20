package cn.newness.imip.module.oam.controller.admin.maintaindetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 保养内容表新增/修改 Request VO")
@Data
public class MaintainDetailSaveReqVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23200")
    private String id;

    @Schema(description = "保养计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15928")
    @NotEmpty(message = "保养计划id不能为空")
    private String equipMaintainPlanId;

    @Schema(description = "保养计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "保养计划名称不能为空")
    private String equipMaintainPlanName;

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11594")
    @NotEmpty(message = "设备id不能为空")
    private String equipId;

    @Schema(description = "是否特殊设备【字典：0否；1是】", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否特殊设备未选择")
    private Integer isSpecial;

    @Schema(description = "设备档案id【只有当is_special值为1时才有此值】")
    private String equipprofileId;

    @Schema(description = "是否参照其他非特殊设备保养内容【0否，1是】")
    private Integer isReferto;

    @Schema(description = "内容参照对象id")
    private String refertoId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "设备名称不能为空")
    private String equipName;

    @Schema(description = "设备规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备规格不能为空")
    private String equipSpecification;

    @Schema(description = "保养周期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "保养周期不能为空")
    private BigDecimal maintainCycle;

    @Schema(description = "是否更换自身【字典：0否；1是】", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否更换自身未选择")
    private Integer replaceSelf;

    @Schema(description = "保养内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "保养内容不能为空")
    private String details;

}