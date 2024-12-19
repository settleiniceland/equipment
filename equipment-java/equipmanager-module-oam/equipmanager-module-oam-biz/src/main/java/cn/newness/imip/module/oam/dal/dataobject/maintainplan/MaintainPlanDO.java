package cn.newness.imip.module.oam.dal.dataobject.maintainplan;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.newness.imip.framework.mybatis.core.dataobject.DataPermissionDO;

/**
 * 保养计划 DO
 *
 * @author mcr
 */
@TableName("equip_maintain_plan")
@KeySequence("equip_maintain_plan_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintainPlanDO extends
    DataPermissionDO
{

    /**
     * 主键id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 计划名称
     */
    private String name;
    /**
     * 执行部门id
     */
    private String executeDeptId;
    /**
     * 执行部门名称
     */
    private String executeDeptName;
    /**
     * 计划状态【字典：0正常；1禁用】
     */
    private Integer status;
    /**
     * 设备区域id
     */
    private String equiplocationId;
    /**
     * 设备区域名称
     */
    private String equiplocationName;
    /**
     * 备注
     */
    private String remark;
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