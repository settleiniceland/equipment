package cn.newness.imip.module.oam.dal.dataobject.statusrecord;

import lombok.*;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.newness.imip.framework.mybatis.core.dataobject.DataPermissionDO;

/**
 * 停机表 DO
 *
 * @author super超级管理员王大王
 */
@TableName("equip_status_record")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusRecordDO extends
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
    private String equipmentprofileId;
    /**
     * 设备名称
     */
    private String equipName;
    /**
     * 设备编码
     */
    private String equipCode;
    /**
     * 操作类型【字典:1点检结果异常:2手动操作】
     */
    private Integer operationType;
    /**
     * 改变详情
     */
    private String changeDetails;
    /**
     * 操作时间
     */
    private LocalDateTime operationTime;
    /**
     * 启停状态【字典：1开机；2停机】
     */
    private Integer newStatus1;
    /**
     * 异动返修等状态【字典：3异动中；4异动完毕；5回国返修中；6回国返修完毕；7报废】
     */
    private Integer newStatus2;
    /**
     * 详情【若为点巡检异常的话则自动生成内容格式为“设备名称+设备编码-->点检计划名称-->点检内容详情-->点检日期-->点检结果-->点检结果详情”】
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