package cn.newness.imip.module.property.dal.dataobject.equiptype;

import cn.newness.imip.framework.mybatis.core.dataobject.DataPermissionDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.newness.imip.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备类别 DO
 *
 * @author mcr
 */
@TableName("equip_equiptype")
@KeySequence("equip_equiptype_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquiptypeDO extends DataPermissionDO {
    /**
     * 主键id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 设备类别编码
     */
    private String code;
    /**
     * 设备类别名称
     */
    private String name;
    /**
     * 父类别id;比如生产类设备下面有子类型切割类设备，切割类设备下面还有手持式切割类设备和固定式切割类设备等等
     */
    private String supId;
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