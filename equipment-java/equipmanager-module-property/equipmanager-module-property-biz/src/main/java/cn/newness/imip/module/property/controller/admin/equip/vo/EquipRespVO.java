package cn.newness.imip.module.property.controller.admin.equip.vo;

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

@Schema(description = "管理后台 - 设备表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EquipRespVO {
    @Schema(description = "主键id", example = "20473")
    @ExcelIgnore
    private String id;

    @Schema(description = "父id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15768")
    @ExcelIgnore
    private String supId;

    @Schema(description = "上级设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("上级设备名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String supName;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("设备名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipName;

    @Schema(description = "设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelIgnore
    private Integer equipAttribute;

    @Schema(description = "设备属性名称")
    @ExcelProperty("设备属性")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipAttributeName;

    @Schema(description = "设备类别id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8971")
    @ExcelIgnore
    private String equiptypeId;

    @Schema(description = "设备类别名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("设备类别")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equiptypeName;

    @Schema(description = "设备规格")
    @ExcelProperty("设备规格")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipSpecification;


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