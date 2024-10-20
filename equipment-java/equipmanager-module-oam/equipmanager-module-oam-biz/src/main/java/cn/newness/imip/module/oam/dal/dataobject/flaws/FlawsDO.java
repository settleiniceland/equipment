package cn.newness.imip.module.oam.dal.dataobject.flaws;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.newness.imip.framework.mybatis.core.dataobject.DataPermissionDO;

/**
 * 缺陷库 DO
 *
 * @author super超级管理员王大王
 */
@TableName("equip_flaws")
@KeySequence("equip_flaws_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlawsDO extends
    DataPermissionDO
{

    /**
     * 主键id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 设备档案id
     */
    private String equipProfileId;
    /**
     * 设备档案名称
     */
    private String equipProfileName;
    /**
     * 设备档案编码
     */
    private String equipCode;
    /**
     * 设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】
     */
    private Integer equipAttribute;
    /**
     * 缺陷状态【字典：1待排维修计划；2已排计划待维修；3已维修】
     */
    private Integer status;
    /**
     * 是否停机【字典：1开机；2停机】
     */
    private Integer isStop;
    /**
     * 缺陷详情
     */
    private String details;
    /**
     * 缺陷开始时间
     */
    private LocalDateTime beginTime;
    /**
     * 缺陷解决时间【缺陷状态为 3 时才有此值】
     */
    private LocalDateTime solveTime;
    /**
     * 对应维修计划id【缺陷状态为 2或3 时才有此值】
     */
    private String fixPlanId;
    /**
     * 对应维修计划名称【缺陷状态为 2或3 时才有此值】
     */
    private String fixPlanName;
    /**
     * 维修时长【单位h,可小数】【缺陷状态为 3 时才有此值】
     */
    private Long solveDuration;
    /**
     * 缺陷解决车间id【缺陷状态为 3 时才有此值】
     */
    private String solveDeptId;
    /**
     * 缺陷解决车间名称【缺陷状态为 3 时才有此值】
     */
    private String solveDeptName;
    /**
     * 缺陷解决人数【缺陷状态为 3 时才有此值】
     */
    private Integer solveHumanNum;
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