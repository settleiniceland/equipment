package cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo;

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

@Schema(description = "管理后台 - 点检日志表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class InspectionProfileRespVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25532")
    @ExcelIgnore
    private String id;

    @Schema(description = "点检计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15588")
    @ExcelIgnore
    private String inspectionPlanId;

    @Schema(description = "点检计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("点检计划名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String inspectionPlanName;

    @Schema(description = "点检类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelIgnore
    private Integer inspectionType;

    @Schema(description = "点检类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("点检类型")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(11)
    private String inspectionTypeName;

    @Schema(description = "点检周期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("点检周期【单位h】")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(7)
    private Long inspectionCycle;

    @Schema(description = "点检内容id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29138")
    @ExcelIgnore
    private String inspectionDetailId;

    @Schema(description = "点击内容详情", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("点击内容")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String inspectionDetail;

    @Schema(description = "设备档案id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7973")
    @ExcelIgnore
    private String equipProfileId;

    @Schema(description = "设备档案名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("设备档案名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipProfileName;

    @Schema(description = "是否停机")
    @ExcelIgnore
    private Integer isStop;

    @Schema(description = "是否停机")
    @ExcelProperty("是否停机")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(7)
    private String isStopName;

    @Schema(description = "设备档案编码【跟随档案id】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备编码")
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

    @Schema(description = "点检结果【字典1正常；2异常】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelIgnore
    private Integer result;

    @Schema(description = "点检结果图片地址")
    @ExcelIgnore
    private String resultPhotos;

    @Schema(description = "点检结果名称")
    @ExcelProperty("结果")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(5)
    private String resultName;

    @Schema(description = "点检结果详情")
    @ExcelProperty("点检结果详情")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String resultDetail;

    @Schema(description = "点检日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("点检日期")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private LocalDateTime inspectionDate;

    @Schema(description = "点检人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("点检人")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String inspectionUsers;

    @Schema(description = "区域负责人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("区域负责人")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String dutyUsers;

    @Schema(description = "设备区域id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3043")
    @ExcelIgnore
    private String equiplocationId;

    @Schema(description = "设备区域名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("设备区域名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equiplocationName;

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