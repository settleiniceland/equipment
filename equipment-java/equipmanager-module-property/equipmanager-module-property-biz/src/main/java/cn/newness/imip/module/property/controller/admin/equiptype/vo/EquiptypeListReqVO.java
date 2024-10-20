package cn.newness.imip.module.property.controller.admin.equiptype.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.newness.imip.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.newness.imip.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备类别列表 Request VO")
@Data
public class EquiptypeListReqVO {

    @Schema(description = "主键id", example = "16120")
    private String id;

    @Schema(description = "设备类别编码")
    private String code;

    @Schema(description = "设备类别名称", example = "王五")
    private String name;

    @Schema(description = "父类别id;比如生产类设备下面有子类型切割类设备，切割类设备下面还有手持式切割类设备和固定式切割类设备等等", example = "11730")
    private String supId;

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