package cn.newness.imip.module.property.controller.admin.equipmentprofile.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.newness.imip.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.newness.imip.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备档案数据列表 Request VO")
@Data
public class EquipmentprofileListReqVO {

    @Schema(description = "设备档案id", example = "1332")
    private String id;

    @Schema(description = "设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】")
    private Integer equipAttribute;

    @Schema(description = "设备编码")
    private String code;

    @Schema(description = "二维码地址【设备属性为2时此属性才有值】")
    private String qrCode;

    @Schema(description = "生产厂家id【设备属性为2时此属性才有值】", example = "29980")
    private String manufacturerId;

    @Schema(description = "生产厂家名称【设备属性为2时此属性才有值】", example = "李四")
    private String manufacturerName;

    @Schema(description = "设备id", example = "4987")
    private String equipId;

    @Schema(description = "设备名称", example = "王五")
    private String equipName;

    @Schema(description = "设备规格")
    private String equipSpecification;

    @Schema(description = "父设备档案id;最顶级父id为0", example = "18549")
    private String supId;

    @Schema(description = "设备类别id", example = "6333")
    private String equiptypeId;

    @Schema(description = "设备类别名称", example = "王五")
    private String equiptypeName;

    @Schema(description = "设备负责人", example = "李四")
    private String dutyName;

    @Schema(description = "设备状态1;【字典：1开机；2停机；（设备属性为2时才有开关机状态）】", example = "2")
    private Integer status1;

    @Schema(description = "设备状态2;【字典：3异动中；4异动完毕；5回国返修中；6回国返修完毕；7报废】", example = "2")
    private Integer status2;

    @Schema(description = "安装日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] installDate;

    @Schema(description = "购买日期【设备属性为2时此属性才有值】")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] buyTime;

    @Schema(description = "设备技术手册地址;存放技术手册obs地址，多个文件地址中间以特殊符号连接【设备属性为2时此属性才有值】")
    private String fileUrls;

    @Schema(description = "设备图片地址;存放设备图片obs地址，多个文件地址中间以特殊符号连接【设备属性为2时此属性才有值】")
    private String iconUrls;

    @Schema(description = "设备位置id", example = "5208")
    private String locationId;

    @Schema(description = "设备位置名称")
    private String locationName;

    @Schema(description = "设备所属车间id", example = "3068")
    private Long workshopId;

    @Schema(description = "设备所属车间名称", example = "李四")
    private String workshopName;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "更新者")
    private String updater;

    @Schema(description = "更新时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] updateTime;

}