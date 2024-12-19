package cn.newness.imip.module.oam.dal.dataobject.inspectionprofile;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.newness.imip.framework.mybatis.core.dataobject.DataPermissionDO;

/**
 * 点检日志表 DO
 *
 * @author super超级管理员王中王
 */
@TableName("equip_inspection_profile")
@KeySequence("equip_inspection_profile_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionProfileDO extends
    DataPermissionDO
{

    /**
     * 主键id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 点检计划id
     */
    private String inspectionPlanId;
    /**
     * 计划执行次数
     */
    private Integer planExecuteCount;
    /**
     * 点检计划名称【点检计划表中来】
     */
    private String inspectionPlanName;
    /**
     * 点检类型【点检计划表中来】
     */
    private Integer inspectionType;
    /**
     * 点检周期【点检计划表中来】
     */
    private Long inspectionCycle;
    /**
     * 设备区域id【点检计划表中来】
     */
    private String equiplocationId;
    /**
     * 设备区域名称【点检计划表中来】
     */
    private String equiplocationName;
    /**
     * 点检内容id
     */
    private String inspectionDetailId;
    /**
     * 点击内容详情【点检内容表中来】
     */
    private String inspectionDetail;
    /**
     * 设备档案id【根据点检内容表中的设备id列一个列表供选择】
     */
    private String equipProfileId;
    /**
     * 设备档案名称【跟随档案id】
     */
    private String equipProfileName;
    /**
     * 设备是否停机
     */
    private Integer isStop;
    /**
     * 设备档案编码【跟随档案id】
     */
    private String equipCode;
    /**
     * 设备属性【字典，1代表设备组；2代表单个完整设备；3代表设备组件】
     */
    private Integer equipAttribute;
    /**
     * 点检结果【字典1正常；2异常】
     */
    private Integer result;
    /**
     * 点检结果图片,中间以"-_-"分割
     */
    private String resultPhotos;
    /**
     * 点检结果详情
     */
    private String resultDetail;
    /**
     * 点检日期
     */
    private LocalDateTime inspectionDate;
    /**
     * 点检人【每组数据之间用特殊符号隔开，每组数据又包含用户id和用户名，之间同样以特殊符号隔开】
     */
    private String inspectionUsers;
    /**
     * 区域负责人【每组数据之间用特殊符号隔开，每组数据又包含用户id和用户名，之间同样以特殊符号隔开】
     */
    private String dutyUsers;
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