package cn.newness.imip.module.property.dal.dataobject.equipmentprofile;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.newness.imip.framework.mybatis.core.dataobject.DataPermissionDO;

/**
 * 设备档案数据 DO
 *
 * @author mcr
 */
@TableName("equip_equipmentprofile")
@KeySequence("equip_equipmentprofile_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentprofileDO extends
    DataPermissionDO
{

    /**
     * 设备档案id
     */
    @TableId(type = IdType.INPUT)
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
     * 二维码地址【设备属性为2时此属性才有值】
     */
    private String qrCode;
    /**
     * 生产厂家id【设备属性为2时此属性才有值】
     */
    private String manufacturerId;
    /**
     * 生产厂家名称【设备属性为2时此属性才有值】
     */
    private String manufacturerName;
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
     * 设备负责人
     */
    private String dutyName;
    /**
     * 设备状态1;【字典：1开机；2停机；（设备属性为2时才有开关机状态）】
     */
    private Integer status1;
    /**
     * 设备状态2;【字典：3异动中；4异动完毕；5回国返修中；6回国返修完毕；7报废】
     */
    private Integer status2;
    /**
     * 安装日期
     */
    private LocalDateTime installDate;
    /**
     * 购买日期【设备属性为2时此属性才有值】
     */
    private LocalDateTime buyTime;
    /**
     * 设备技术手册地址;存放技术手册obs地址，多个文件地址中间以特殊符号连接【设备属性为2时此属性才有值】
     */
    private String fileUrls;
    /**
     * 设备图片地址;存放设备图片obs地址，多个文件地址中间以特殊符号连接【设备属性为2时此属性才有值】
     */
    private String iconUrls;
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
     * 备用字段1
     */
    private String sBackup01;
    /**
     * 备用字段2
     */
    private String sBackup02;
    /**
     * 备用字段3
     */
    private String sBackup03;
    /**
     * 备用字段4
     */
    private String sBackup04;
    /**
     * 备用字段5
     */
    private String sBackup05;
    /**
     * 备用字段6
     */
    private String sBackup06;
    /**
     * 备用字段7
     */
    private String sBackup07;
    /**
     * 备用字段8
     */
    private String sBackup08;
    /**
     * 备用字段9
     */
    private String sBackup09;
    /**
     * 备用字段10
     */
    private String sBackup10;

}