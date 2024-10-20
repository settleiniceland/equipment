package cn.newness.imip.module.property.controller.admin.equipmentprofile.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备档案数据新增/修改 Request VO")
@Data
public class EquipmentprofileSaveReqVO {

    @Schema(description = "设备档案id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1332")
    private String id;

    @Schema(description = "设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "设备属性不能为空")
    @DiffLogField(name = "设备属性")
    private Integer equipAttribute;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备编码不能为空")
    @DiffLogField(name = "设备编码")
    private String code;

    @Schema(description = "二维码地址【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "二维码地址")
    private String qrCode;

    @Schema(description = "生产厂家id【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED, example = "29980")
    private String manufacturerId;

    @Schema(description = "生产厂家名称【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @DiffLogField(name = "生产厂家")
    private String manufacturerName;

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4987")
    @NotEmpty(message = "设备id不能为空")
    private String equipId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "设备名称不能为空")
    @DiffLogField(name = "设备名称")
    private String equipName;

    @Schema(description = "设备规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "设备规格")
    private String equipSpecification;

    @Schema(description = "父设备档案id;最顶级父id为0", requiredMode = Schema.RequiredMode.REQUIRED, example = "18549")
    @NotEmpty(message = "父设备档案id不能为空")
    @DiffLogField(name = "父设备档案id")
    private String supId;

    @Schema(description = "设备类别id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6333")
    @NotEmpty(message = "设备类别id不能为空")
    private String equiptypeId;

    @Schema(description = "设备类别名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "设备类别名称不能为空")
    @DiffLogField(name = "设备类别")
    private String equiptypeName;

    @Schema(description = "设备负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "设备负责人不能为空")
    @DiffLogField(name = "设备负责人")
    private String dutyName;

    @Schema(description = "设备状态1;【字典：1开机；2停机；（设备属性为2时才有开关机状态）】", example = "2")
    @DiffLogField(name = "设备开关机状态")
    private Integer status1;

    @Schema(description = "设备状态2;【字典：3异动中；4异动完毕；5回国返修中；6回国返修完毕；7报废】", example = "2")
    @DiffLogField(name = "设备异动返修等状态")
    private Integer status2;

    @Schema(description = "安装日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "安装日期")
    private LocalDateTime installDate;

    @Schema(description = "购买日期【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "购买日期")
    private LocalDateTime buyTime;

    @Schema(description = "设备技术手册地址【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "设备技术手册地址")
    private String fileUrls;

    @Schema(description = "设备图片地址【设备属性为2时此属性才有值】", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "设备图片地址")
    private String iconUrls;

    @Schema(description = "设备位置id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5208")
    private String locationId;

    @Schema(description = "设备位置名字")
    @DiffLogField(name = "设备安装位置")
    private String locationName;

    @Schema(description = "设备所属车间id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3068")
    @NotNull(message = "设备所属车间id不能为空")
    private Long workshopId;

    @Schema(description = "设备所属车间名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "设备所属车间名称不能为空")
    @DiffLogField(name = "设备所属车间")
    private String workshopName;

}