package cn.newness.imip.module.property.controller.admin.manufacturer.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 设备生产厂家信息新增/修改 Request VO")
@Data
public class ManufacturerSaveReqVO {
    @Schema(description = "厂家id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29967")
    private String id;

    @Schema(description = "厂家名称", example = "王五")
    @DiffLogField(name = "厂家名称")
    private String name;

    @Schema(description = "厂家编码")
    @DiffLogField(name = "厂家编码")
    private String code;

    @Schema(description = "厂家状态;0正常；1禁用", example = "2")
    @DiffLogField(name = "厂家状态")
    private Integer status;

    @Schema(description = "备注", example = "你猜")
    @DiffLogField(name = "备注")
    private String remark;

}