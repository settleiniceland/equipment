package cn.newness.imip.module.property.controller.admin.equiptype.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 设备类别新增/修改 Request VO")
@Data
public class EquiptypeSaveReqVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16120")
    private String id;

    @Schema(description = "设备类别编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备类别编码不能为空")
    @DiffLogField(name = "类别编码")
    private String code;

    @Schema(description = "设备类别名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "设备类别名称不能为空")
    @DiffLogField(name = "类别名称")
    private String name;

    @Schema(description = "父类别id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11730")
    @NotEmpty(message = "父类别id")
    @DiffLogField(name = "父类别id")
    private String supId;

}