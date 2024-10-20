package cn.newness.imip.module.property.controller.admin.equip.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 设备表新增/修改 Request VO")
@Data
public class EquipSaveReqVO {

    @Schema(description = "主键id;【设备档案表存放的是一个个设备，而设备表存放的是一种设备】", example = "20473")
    private String id;

    @Schema(description = "父id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15768")
    @NotEmpty(message = "父id不能为空")
    @DiffLogField(name="父id不能为空")
    private String supId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "设备名称不能为空")
    @DiffLogField(name = "设备名称")
    private String equipName;

    @Schema(description = "设备规格")
    @DiffLogField(name = "设备规格")
    private String equipSpecification;

    @Schema(description = "设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "设备属性不能为空")
    @DiffLogField(name = "设备属性")
    private Integer equipAttribute;

    @Schema(description = "设备类别id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8971")
    @NotEmpty(message = "设备类别id不能为空")
    private String equiptypeId;

    @Schema(description = "设备类别名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @DiffLogField(name = "设备类别")
    private String equiptypeName;

}