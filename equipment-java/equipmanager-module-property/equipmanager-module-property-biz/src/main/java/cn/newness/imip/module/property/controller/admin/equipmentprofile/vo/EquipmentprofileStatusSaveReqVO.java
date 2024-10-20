package cn.newness.imip.module.property.controller.admin.equipmentprofile.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author machuran
 * @date 10/8/2024
 * @time 8:43 AM
 * @Description
 */
@Schema(description = "设备状态修改临时对象")
@Data
public class EquipmentprofileStatusSaveReqVO {
    @Schema(description = "设备档案id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1332")
    @NotEmpty(message = "设备档案id不能为空")
    private String id;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "设备名称不能为空")
    @DiffLogField(name = "设备名称")
    private String equipName;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备编码不能为空")
    @DiffLogField(name = "设备编码")
    private String code;

    @Schema(description = "操作类型【字典:1点检结果异常:2手动操作】")
    private Integer operationType;

    @Schema(description = "设备状态1;【字典：1开机；2停机；（设备属性为2时才有开关机状态）】", example = "2")
    @DiffLogField(name = "设备开关机状态")
    private Integer status1;

    @Schema(description = "设备状态2;【字典：3异动中；4异动完毕；5回国返修中；6回国返修完毕；7报废】", example = "2")
    @DiffLogField(name = "设备异动返修等状态")
    private Integer status2;

    @Schema(description = "状态修改备注", example = "2")
    @DiffLogField(name = "状态修改备注")
    private String remark;
}
