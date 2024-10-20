package cn.newness.imip.module.oam.dal.dataobject.maintaindetail;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.newness.imip.framework.mybatis.core.dataobject.DataPermissionDO;

/**
 * 保养内容表 DO
 *
 * @author mcr
 */
@TableName("equip_maintain_detail")
@KeySequence("equip_maintain_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintainDetailDO extends
    DataPermissionDO
{

    /**
     * 主键id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 保养计划id
     */
    private String equipMaintainPlanId;
    /**
     * 保养计划名称
     */
    private String equipMaintainPlanName;
    /**
     * 正常情况下是设备id，如果是特殊设备的话这里就是设备档案id
     */
    private String equipId;
    /**
     * 是否特殊设备【字典：0否；1是】
     */
    private Integer isSpecial;
    /**
    * 设备档案id【只有当is_special值为1时才有此值】
    */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String equipprofileId;
    /**
    * 是否参照其他非特殊设备保养内容【0否，1是】
    */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer isReferto;
    /**
     * 参照对象id【只有是特殊设备时此项才可能有值，且参照对象一定是非特殊设备保养内容】
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String refertoId;
    /**
     * 设备名称
     */
    private String equipName;
    /**
     * 设备规格
     */
    private String equipSpecification;
    /**
     * 保养周期
     */
    private BigDecimal maintainCycle;
    /**
     * 是否更换自身【字典：0否；1是】
     */
    private Integer replaceSelf;
    /**
     * 保养内容
     */
    private String details;
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