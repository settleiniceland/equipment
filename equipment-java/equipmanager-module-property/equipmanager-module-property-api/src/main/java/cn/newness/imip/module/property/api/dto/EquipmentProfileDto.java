package cn.newness.imip.module.property.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author machuran
 * @date 9/18/2024
 * @time 10:13 AM
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentProfileDto {
    private String id;
    /**
     * 设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】
     */
    private Integer equipAttribute;
    /**
     * 设备编码
     */
    private String code;
    /**
     * 设备id
     */
    private String equipId;
    /**
     * 设备名称
     */
    private String equipName;
    /**
     * 设备规格
     */
    private String equipSpecification;
    /**
     * 父设备档案id;最顶级父id为0
     */
    private String supId;
    /**
     * 设备类别id
     */
    private String equiptypeId;
    /**
     * 设备类别名称
     */
    private String equiptypeName;
    /**
     * 设备位置id
     */
    private String locationId;
    /**
     * 设备位置名字
     */
    private String locationName;
    /**
     * 设备所属车间id
     */
    private Long workshopId;
    /**
     * 设备所属车间名称
     */
    private String workshopName;
    /**
     * 设备状态2;【字典：3异动中；4异动完毕；5回国返修中；6回国返修完毕；7报废】
     */
    private Integer status2;
}
