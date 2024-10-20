package cn.newness.imip.module.oam.controller.admin.flaws.vo;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 缺陷库 Response VO")
@Data
@ExcelIgnoreUnannotated
public class FlawsRespVO {

    @Schema(description = "主键id", example = "14140")
    @ExcelIgnore
    private String id;

    @Schema(description = "设备档案id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24626")
    @ExcelIgnore
    private String equipProfileId;

    @Schema(description = "设备档案名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("设备档案名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipProfileName;

    @Schema(description = "设备档案编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备档案编码")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipCode;

    @Schema(description = "设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelIgnore
    private Integer equipAttribute;

    @Schema(description = "设备属性名称")
    @ExcelProperty("设备属性")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipAttributeName;

    @Schema(description = "是否停机【字典：1开机；2停机】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelIgnore
    private Integer isStop;

    @Schema(description = "是否停机【字典：1开机；2停机】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备状态")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String isStopName;

    @Schema(description = "缺陷状态【字典：1待排维修计划；2已排计划待维修；3已维修】", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelIgnore
    private Integer status;

    @Schema(description = "缺陷状态【字典：1待排维修计划；2已排计划待维修；3已维修】", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("缺陷状态")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String statusName;

    @Schema(description = "缺陷详情")
    @ExcelProperty("缺陷详情")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String details;

    @Schema(description = "缺陷开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("缺陷开始时间")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private LocalDateTime beginTime;

    @Schema(description = "缺陷解决时间【缺陷状态为 3 时才有此值】")
    @ExcelProperty("缺陷解决时间")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private LocalDateTime solveTime;

    @Schema(description = "缺陷持续时间【缺陷状态为 3 时才有此值,单位h】")
    @ExcelProperty("缺陷持续时间【单位h】")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private BigDecimal duration;

    @Schema(description = "对应维修计划id【缺陷状态为 2或3 时才有此值】", example = "22454")
    @ExcelIgnore
    private String fixPlanId;

    @Schema(description = "对应维修计划名称【缺陷状态为 2或3 时才有此值】", example = "22454")
    @ExcelProperty("维修计划名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String fixPlanName;

    @Schema(description = "维修时长【单位h,可小数】【缺陷状态为 3 时才有此值】")
    @ExcelProperty("维修时长【单位h】")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private Long solveDuration;

    @Schema(description = "缺陷解决车间id【缺陷状态为 3 时才有此值】", example = "24226")
    @ExcelIgnore
    private String solveDeptId;

    @Schema(description = "缺陷解决车间名称【缺陷状态为 3 时才有此值】", example = "赵六")
    @ExcelProperty("缺陷解决车间名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String solveDeptName;

    @Schema(description = "缺陷解决人数【缺陷状态为 3 时才有此值】")
    @ExcelProperty("缺陷解决人数")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private Integer solveHumanNum;

    @Schema(description = "创建者", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建者")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private LocalDateTime createTime;

    @Schema(description = "更新者", requiredMode = Schema.RequiredMode.REQUIRED)
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