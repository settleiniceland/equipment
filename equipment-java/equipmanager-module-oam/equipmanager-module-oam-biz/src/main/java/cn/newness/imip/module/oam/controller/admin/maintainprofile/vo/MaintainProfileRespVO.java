package cn.newness.imip.module.oam.controller.admin.maintainprofile.vo;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 保养日志表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MaintainProfileRespVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24574")
    @ExcelProperty("主键id")
    private String id;

    @Schema(description = "保养计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14747")
    @ExcelProperty("保养计划id")
    private String equipMaintainPlanId;

    @Schema(description = "保养计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("保养计划名称")
    private String equipMaintainPlanName;

    @Schema(description = "保养内容id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3351")
    @ExcelProperty("保养内容id")
    private String equipMaintainDetailId;

    @Schema(description = "保养内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保养内容")
    private String equipMaintainDetail;

    @Schema(description = "是否特殊设备【字典：0否；1是】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否特殊设备【字典：0否；1是】")
    private Integer isSpecial;

    @Schema(description = "是否特殊设备", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否特殊设备")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String isSpecialName;

    @Schema(description = "设备档案id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25751")
    @ExcelProperty("设备档案id")
    private String equipmentprofileId;

    @Schema(description = "设备档案编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备档案编码")
    private String equipmentprofileCode;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("设备名称")
    private String equipName;

    @Schema(description = "保养周期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保养周期")
    private BigDecimal maintainCycle;

    @Schema(description = "保养图片【地址，中间以-_-隔开】")
    @ExcelProperty("保养图片【地址，中间以-_-隔开】")
    private String resultPhotos;

    @Schema(description = "保养日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保养日期")
    private LocalDateTime maintainDate;

    @Schema(description = "计划执行部门id【从保养计划中来】", requiredMode = Schema.RequiredMode.REQUIRED, example = "4331")
    @ExcelProperty("计划执行部门id【从保养计划中来】")
    private String executeDeptId;

    @Schema(description = "计划执行部门名称【从保养计划中来】", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("计划执行部门名称【从保养计划中来】")
    private String executeDeptName;

    @Schema(description = "实际保养人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("实际保养人")
    private String actualMaintainNames;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新者")
    @ExcelProperty("更新者")
    private String updater;

    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("更新时间")
    private LocalDateTime updateTime;

}