package cn.newness.imip.module.oam.controller.admin.inspectplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 点检计划表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class InspectplanRespVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30856")
    @ExcelIgnore
    private String id;

    @Schema(description = "计划状态;0正常；1禁用", example = "2")
    @ExcelIgnore
    private Integer status;

    @Schema(description = "点检类型【字典：1普通点检；2重点点检；3专项点检；4拆检】", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelIgnore
    private Integer inspectionType;

    @Schema(description = "点检类型名")
    @ExcelProperty("点检类型")
    private String inspectionTypeName;

    @Schema(description = "点检周期【单位h】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("点检周期【单位h】")
    private BigDecimal inspectionCycle;

    @Schema(description = "点检计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("点检计划名称")
    private String name;

    @Schema(description = "设备区域id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8544")
    @ExcelProperty("设备区域id")
    private String equiplocationId;

    @Schema(description = "设备区域名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("设备区域名称")
    private String equiplocationName;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String detail;

    @Schema(description = "创建者", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新者", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("更新者")
    private String updater;

    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("更新时间")
    private LocalDateTime updateTime;

}