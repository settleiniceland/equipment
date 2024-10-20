package cn.newness.imip.module.property.controller.admin.manufacturer.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.newness.imip.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.newness.imip.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备生产厂家信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ManufacturerPageReqVO extends PageParam {
    @Schema(description = "厂家id", example = "29967")
    private String id;

    @Schema(description = "厂家名称", example = "王五")
    private String name;

    @Schema(description = "厂家编码")
    private String code;

    @Schema(description = "厂家状态;0正常；1禁用", example = "2")
    private Integer status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}