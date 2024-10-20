package cn.newness.imip.module.property.dal.dataobject.equip;

import cn.newness.imip.framework.mybatis.core.dataobject.DataPermissionDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.newness.imip.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备表 DO
 *
 * @author mcr
 */
@TableName("equip_equip")
@KeySequence("equip_equip_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipDO extends DataPermissionDO {
    /**
     * 主键id;【设备档案表存放的是一个个设备，而设备表存放的是一种设备】
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 父id
     */
    private String supId;
    /**
     * 设备名称
     */
    private String equipName;
    /**
     * 设备规格
     */
    private String equipSpecification;
    /**
     * 设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】
     */
    private Integer equipAttribute;
    /**
     * 设备类别id
     */
    private String equiptypeId;
    /**
     * 设备类别名称
     */
    private String equiptypeName;
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