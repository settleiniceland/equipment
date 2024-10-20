package cn.newness.imip.module.property.controller.admin.installlocation.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.newness.imip.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.newness.imip.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备安装位置列表 Request VO")
@Data
public class InstalllocationListReqVO {

    @Schema(description = "主键id", example = "17180")
    private String id;

    @Schema(description = "地区编码")
    private String code;

    @Schema(description = "父地区id", example = "6754")
    private String supId;

    @Schema(description = "地区名称", example = "芋艿")
    private String name;

    @Schema(description = "负责人", example = "张三")
    private String dutyName;

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