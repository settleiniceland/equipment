package cn.newness.imip.module.property.controller.admin.equipmentprofile.vo;

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

@Schema(description = "管理后台 - 设备档案数据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EquipmentprofileRespVO {

    @Schema(description = "设备档案id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1332")
    @ExcelIgnore
    private String id;

    @Schema(description = "设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelIgnore
    private Integer equipAttribute;

    @Schema(description = "设备属性名称")
    @ExcelProperty("设备属性")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipAttributeName;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备编码")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String code;

    @Schema(description = "二维码地址【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelIgnore
    private String qrCode;

    @Schema(description = "生产厂家id【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED, example = "29980")
    @ExcelIgnore
    private String manufacturerId;

    @Schema(description = "生产厂家名称【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("生产厂家名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String manufacturerName;

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4987")
    @ExcelIgnore
    private String equipId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("设备名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipName;

    @Schema(description = "设备规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备规格")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equipSpecification;

    @Schema(description = "父设备档案id;最顶级父id为0", requiredMode = Schema.RequiredMode.REQUIRED, example = "18549")
    @ExcelIgnore
    private String supId;

    @Schema(description = "父设备档案名称")
    @ExcelProperty("上级设备名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String supName;

    @Schema(description = "设备类别id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6333")
    @ExcelIgnore
    private String equiptypeId;

    @Schema(description = "设备类别名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("设备类别名称")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String equiptypeName;

    @Schema(description = "设备负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("设备负责人")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String dutyName;

    @Schema(description = "设备状态1;【字典：1开机；2停机；（设备属性为2时才有开关机状态）】", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelIgnore
    private Integer status1;

    @Schema(description = "设备启停状态名")
    @ExcelProperty("设备启停状态")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String status1Name;

    @Schema(description = "设备状态2;【字典：3异动中；4异动完毕；5回国返修中；6回国返修完毕；7报废】", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelIgnore
    private Integer status2;

    @Schema(description = "设备异动返修状态名")
    @ExcelProperty("设备异动返修状态")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String status2Name;

    @Schema(description = "安装日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("安装日期")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private LocalDateTime installDate;

    @Schema(description = "购买日期【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("购买日期")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private LocalDateTime buyTime;

    @Schema(description = "设备技术手册地址;存放技术手册obs地址，多个文件地址中间以特殊符号连接【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelIgnore
    private String fileUrls;

    @Schema(description = "设备图片地址;存放设备图片obs地址，多个文件地址中间以特殊符号连接【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelIgnore
    private String iconUrls;

    @Schema(description = "设备位置id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5208")
    @ExcelIgnore
    private String locationId;

    @Schema(description = "设备位置名字")
    @ExcelProperty("设备位置名字")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String locationName;

    @Schema(description = "设备所属车间id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3068")
    @ExcelIgnore
    private Long workshopId;

    @Schema(description = "设备所属车间名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("设备所属车间")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ColumnWidth(30)
    private String workshopName;

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