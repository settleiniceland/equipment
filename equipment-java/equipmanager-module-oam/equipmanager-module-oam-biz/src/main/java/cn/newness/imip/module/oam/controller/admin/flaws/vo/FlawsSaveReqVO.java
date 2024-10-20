package cn.newness.imip.module.oam.controller.admin.flaws.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 缺陷库新增/修改 Request VO")
@Data
public class FlawsSaveReqVO {

    @Schema(description = "主键id", example = "14140")
    private String id;

    @Schema(description = "设备档案id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24626")
    @NotEmpty(message = "设备档案id不能为空")
    private String equipProfileId;

    @Schema(description = "设备档案名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "设备档案名称不能为空")
    private String equipProfileName;

    @Schema(description = "设备档案编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备档案编码不能为空")
    private String equipCode;

    @Schema(description = "设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "设备属性不能为空")
    private Integer equipAttribute;

    @Schema(description = "缺陷状态【字典：1待排维修计划；2已排计划待维修；3已维修】", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "是否停机【字典：1开机；2停机】", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否停机【字典：1开机；2停机】不能为空")
    private Integer isStop;

    @Schema(description = "缺陷详情")
    @NotNull(message = "缺陷描述不能为空")
    private String details;

    @Schema(description = "缺陷开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "缺陷开始时间不能为空")
    private LocalDateTime beginTime;

    @Schema(description = "缺陷解决时间【缺陷状态为 3 时才有此值】")
    private LocalDateTime solveTime;

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

}