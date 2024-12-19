package cn.newness.imip.module.oam.controller.admin.maintaindetail.vo;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 保养内容表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MaintainDetailRespVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23200")
    @ExcelIgnore
    private String id;

    @Schema(description = "保养计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15928")
    @ExcelIgnore
    private String equipMaintainPlanId;

    @Schema(description = "保养计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("保养计划名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipMaintainPlanName;

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11594")
    @ExcelIgnore
    private String equipId;

    @Schema(description = "是否特殊设备【字典：0否；1是】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelIgnore
    private Integer isSpecial;

    @Schema(description = "是否特殊设备", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否特殊设备")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String isSpecialName;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("设备名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipName;

    @Schema(description = "设备区域id")
    @ExcelIgnore
    private String equiplocationId;

    @Schema(description = "设备区域名称")
    @ExcelProperty("设备区域名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equiplocationName;

    @Schema(description = "设备档案id列表")
    @ExcelIgnore
    private String equipprofileIds;

    @Schema(description = "设备档案编码列表", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("设备档案编码列表")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipprofileCodes;

    @Schema(description = "设备规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备规格")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipSpecification;

    @Schema(description = "是否参照其他非特殊设备保养内容【0否，1是】")
    @ExcelIgnore
    private Integer isReferto;

    @Schema(description = "是否参照其他非特殊设备保养内容【0否，1是】")
    @ExcelProperty("是否参考其他内容")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String isRefertoName;

    @Schema(description = "内容参照对象id")
    @ExcelIgnore
    private String refertoId;

    @Schema(description = "保养周期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保养周期【单位：h】")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private BigDecimal maintainCycle;

    @Schema(description = "保养内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保养内容")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String details;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private LocalDateTime createTime;

    @Schema(description = "更新者")
    @ExcelProperty("更新者")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String updater;

    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("更新时间")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private LocalDateTime updateTime;
}