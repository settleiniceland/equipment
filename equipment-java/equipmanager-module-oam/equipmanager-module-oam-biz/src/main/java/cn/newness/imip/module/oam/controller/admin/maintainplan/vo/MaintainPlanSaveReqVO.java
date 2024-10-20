package cn.newness.imip.module.oam.controller.admin.maintainplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 保养计划新增/修改 Request VO")
@Data
public class MaintainPlanSaveReqVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15344")
    private String id;

    @Schema(description = "计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "计划名称不能为空")
    private String name;

    @Schema(description = "执行部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9384")
    @NotEmpty(message = "执行部门id不能为空")
    private String executeDeptId;

    @Schema(description = "执行部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "执行部门名称不能为空")
    private String executeDeptName;

    @Schema(description = "计划状态【字典：0正常；1禁用】", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "计划状态【字典：0正常；1禁用】不能为空")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}