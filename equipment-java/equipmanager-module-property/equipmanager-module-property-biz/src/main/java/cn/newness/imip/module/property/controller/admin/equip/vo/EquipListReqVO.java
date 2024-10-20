package cn.newness.imip.module.property.controller.admin.equip.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.newness.imip.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.newness.imip.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备表列表 Request VO")
@Data
public class EquipListReqVO {

    @Schema(description = "主键id;【设备档案表存放的是一个个设备，而设备表存放的是一种设备】", example = "20473")
    private String id;

    @Schema(description = "父id", example = "15768")
    private String supId;

    @Schema(description = "设备名称", example = "张三")
    private String equipName;

    @Schema(description = "设备规格")
    private String equipSpecification;

    @Schema(description = "设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】")
    private Integer equipAttribute;

    @Schema(description = "设备类别id", example = "8971")
    private String equiptypeId;

    @Schema(description = "设备类别名称", example = "王五")
    private String equiptypeName;

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