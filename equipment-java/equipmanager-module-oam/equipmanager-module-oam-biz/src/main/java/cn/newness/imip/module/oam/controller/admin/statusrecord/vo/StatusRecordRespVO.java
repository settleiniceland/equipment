package cn.newness.imip.module.oam.controller.admin.statusrecord.vo;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 停机表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class StatusRecordRespVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6350")
    @ExcelIgnore
    private String id;

    @Schema(description = "设备档案id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11737")
    @ExcelIgnore
    private String equipmentprofileId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("设备名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipName;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备编码")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipCode;

    @Schema(description = "操作类型【字典:1点检结果异常:2手动操作】")
    @ExcelIgnore
    private Integer operationType;

    @Schema(description = "操作类型名称")
    @ExcelProperty("操作类型")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String operationTypeName;

    @Schema(description = "操作时间")
    @ExcelProperty("操作时间")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private LocalDateTime operationTime;

    @Schema(description = "改变详情")
    @ExcelIgnore
    private String changeDetails;

    @Schema(description = "改变详情书面化表达")
    @ExcelProperty("改变详情")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String changeDetailsName;

    @Schema(description = "启停状态【字典：1开机；2停机】")
    @ExcelIgnore
    private Integer newStatus1;

    @Schema(description = "启停状态名")
    @ExcelProperty("启停状态")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String newStatus1Name;

    @Schema(description = "异动返修等状态【字典：3异动中；4异动完毕；5回国返修中；6回国返修完毕；7报废】")
    @ExcelIgnore
    private Integer newStatus2;

    @Schema(description = "异动返修等状态名")
    @ExcelProperty("设备状态")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String newStatus2Name;

    @Schema(description = "详情")
    @ExcelProperty("详情")
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

    @Schema(description = "更新时间")
    @ExcelProperty("更新时间")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private LocalDateTime updateTime;

}