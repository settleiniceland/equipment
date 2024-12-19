package cn.newness.imip.module.oam.controller.admin.maintainplan.vo;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 保养计划 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MaintainPlanRespVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15344")
    @ExcelIgnore
    private String id;

    @Schema(description = "计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    @ExcelProperty("计划名称")
    private String name;

    @Schema(description = "执行部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9384")
    @ExcelIgnore
    private String executeDeptId;

    @Schema(description = "执行部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    @ExcelProperty("执行部门名称")
    private String executeDeptName;

    @Schema(description = "计划状态【字典：0正常；1禁用】", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelIgnore
    private Integer status;

    @Schema(description = "计划状态【字典：0正常；1禁用】", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    @ExcelProperty("计划状态")
    private String statusName;

    @Schema(description = "设备区域id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8544")
    @ExcelIgnore
    private String equiplocationId;

    @Schema(description = "设备区域名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("设备区域名称")
    private String equiplocationName;

    @Schema(description = "备注", example = "你说的对")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    @ExcelProperty("备注")
    private String remark;

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