package cn.newness.imip.module.oam.dal.dataobject.maintainprofile;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.newness.imip.framework.mybatis.core.dataobject.DataPermissionDO;

/**
 * 保养日志表 DO
 *
 * @author mcr
 */
@TableName("equip_maintain_profile")
@KeySequence("equip_maintain_profile_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintainProfileDO extends
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
     * 保养内容id
     */
    private String equipMaintainDetailId;
    /**
     * 保养内容
     */
    private String equipMaintainDetail;
    /**
     * 是否特殊设备【字典：0否；1是】
     */
    private Integer isSpecial;
    /**
     * 设备档案id
     */
    private String equipmentprofileId;
    /**
     * 设备档案编码
     */
    private String equipmentprofileCode;
    /**
     * 设备名称
     */
    private String equipName;
    /**
     * 保养周期
     */
    private BigDecimal maintainCycle;
    /**
     * 是否更换自身【字典：0否；1是】
     */
    private Integer replaceSelf;
    /**
     * 保养图片【地址，中间以-_-隔开】
     */
    private String resultPhotos;
    /**
     * 保养日期
     */
    private LocalDateTime maintainDate;
    /**
     * 计划执行部门id【从保养计划中来】
     */
    private String executeDeptId;
    /**
     * 计划执行部门名称【从保养计划中来】
     */
    private String executeDeptName;
    /**
     * 实际保养人
     */
    private String actualMaintainNames;
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