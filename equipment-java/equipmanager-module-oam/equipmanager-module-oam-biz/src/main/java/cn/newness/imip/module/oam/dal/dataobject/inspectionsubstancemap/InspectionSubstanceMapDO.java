package cn.newness.imip.module.oam.dal.dataobject.inspectionsubstancemap;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.newness.imip.framework.mybatis.core.dataobject.BaseDO;

/**
 * 点检计划内容映射表 DO
 *
 * @author super超级管理员王中王
 */
@TableName("equip_inspection_substance_map")
@KeySequence("equip_inspection_substance_map_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionSubstanceMapDO extends
    BaseDO
{

    /**
     * 主键id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 点检计划id
     */
    private String inspectionplanId;
    /**
     * 点检内容id
     */
    private String inspectionSubstanceId;

}