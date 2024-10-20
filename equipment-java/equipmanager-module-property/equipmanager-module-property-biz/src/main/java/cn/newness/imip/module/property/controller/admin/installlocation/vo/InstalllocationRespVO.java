package cn.newness.imip.module.property.controller.admin.installlocation.vo;

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

@Schema(description = "管理后台 - 设备安装位置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class InstalllocationRespVO {

    @Schema(description = "主键id", example = "17180")
    @ExcelIgnore
    private String id;

    @Schema(description = "地区编码")
    @ExcelProperty("地区编码")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String code;

    @Schema(description = "父地区id", example = "6754")
    @ExcelIgnore
    private String supId;

    @Schema(description = "上级地区", example = "6754")
    @ExcelProperty("上级地区")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String supName;

    @Schema(description = "地区名称", example = "芋艿")
    @ExcelProperty("地区名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String name;

    @Schema(description = "负责人", example = "张三")
    @ExcelProperty("负责人")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String dutyName;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String creator;

    @Schema(description = "创建时间")
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