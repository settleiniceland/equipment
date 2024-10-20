package cn.newness.imip.module.property.controller.admin.manufacturer.vo;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备生产厂家信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ManufacturerRespVO {
    @Schema(description = "厂家id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29967")
    @ExcelIgnore
    private String id;

    @Schema(description = "厂家名称", example = "王五")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    @ExcelProperty("厂家名称")
    private String name;

    @Schema(description = "厂家编码")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    @ExcelProperty("厂家编码")
    private String code;

    @Schema(description = "厂家状态;0正常；1禁用", example = "2")
    @ExcelIgnore
    private Integer status;

    @Schema(description = "厂家状态名称", example = "正常")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(20)
    @ExcelProperty("厂家状态")
    private String statusName;

    @Schema(description = "备注", example = "你猜")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建者", requiredMode = Schema.RequiredMode.REQUIRED)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(23)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新者", requiredMode = Schema.RequiredMode.REQUIRED)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    @ExcelProperty("更新者")
    private String updater;

    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(23)
    @ExcelProperty("更新时间")
    private LocalDateTime updateTime;

}