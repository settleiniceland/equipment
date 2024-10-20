package cn.newness.imip.module.oam.controller.admin.maintainprofile.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 保养日志表新增/修改 Request VO")
@Data
public class MaintainProfileSaveReqVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24574")
    private String id;

    @Schema(description = "保养计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14747")
    @NotEmpty(message = "保养计划id不能为空")
    private String equipMaintainPlanId;

    @Schema(description = "保养计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "保养计划名称不能为空")
    private String equipMaintainPlanName;

    @Schema(description = "保养内容id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3351")
    @NotEmpty(message = "保养内容id不能为空")
    private String equipMaintainDetailId;

    @Schema(description = "保养内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "保养内容不能为空")
    private String equipMaintainDetail;

    @Schema(description = "是否特殊设备【字典：0否；1是】", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否特殊设备【字典：0否；1是】不能为空")
    private Integer isSpecial;

    @Schema(description = "设备档案id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25751")
    @NotEmpty(message = "设备档案id不能为空")
    private String equipmentprofileId;

    @Schema(description = "设备档案编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备档案编码不能为空")
    private String equipmentprofileCode;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "设备名称不能为空")
    private String equipName;

    @Schema(description = "保养周期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "保养周期不能为空")
    private BigDecimal maintainCycle;

    @Schema(description = "是否更换自身【字典：0否；1是】", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否更换自身【字典：0否；1是】不能为空")
    private Integer replaceSelf;

    @Schema(description = "保养图片【地址，中间以-_-隔开】")
    private String resultPhotos;

    @Schema(description = "保养日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "保养日期不能为空")
    private LocalDateTime maintainDate;

    @Schema(description = "计划执行部门id【从保养计划中来】", requiredMode = Schema.RequiredMode.REQUIRED, example = "4331")
    @NotEmpty(message = "计划执行部门id【从保养计划中来】不能为空")
    private String executeDeptId;

    @Schema(description = "计划执行部门名称【从保养计划中来】", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "计划执行部门名称【从保养计划中来】不能为空")
    private String executeDeptName;

    @Schema(description = "实际保养人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "实际保养人不能为空")
    private String actualMaintainNames;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}