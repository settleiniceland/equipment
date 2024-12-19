package cn.newness.imip.module.oam.dal.dataobject.inspectionsubstance;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.newness.imip.framework.mybatis.core.dataobject.DataPermissionDO;

/**
 * 点检内容 DO
 *
 * @author super超级管理员王中王
 */
@TableName("equip_inspection_substance")
@KeySequence("equip_inspection_substance_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionSubstanceDO extends
    DataPermissionDO
{

    /**
     * id
     */
    @TableId(type = IdType.INPUT)
    private String id;
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
     * 点检内容
     */
    private String details;
    /**
     * 点检标准
     */
    private String standard;
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