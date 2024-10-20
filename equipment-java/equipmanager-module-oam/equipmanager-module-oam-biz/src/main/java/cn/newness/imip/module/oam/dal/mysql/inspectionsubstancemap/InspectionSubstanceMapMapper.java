package cn.newness.imip.module.oam.dal.mysql.inspectionsubstancemap;

import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.module.oam.dal.dataobject.inspectionsubstancemap.InspectionSubstanceMapDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 点检计划内容映射表 Mapper
 *
 * @author super超级管理员王中王
 */
@Mapper
public interface InspectionSubstanceMapMapper extends BaseMapperX<InspectionSubstanceMapDO> {
    default int delForPlan(InspectionSubstanceMapDO mapDO){
        return delete(new LambdaQueryWrapperX<InspectionSubstanceMapDO>()
                .eqIfPresent(InspectionSubstanceMapDO::getId, mapDO.getId())
                .eqIfPresent(InspectionSubstanceMapDO::getInspectionplanId, mapDO.getInspectionplanId())
                .eqIfPresent(InspectionSubstanceMapDO::getInspectionSubstanceId, mapDO.getInspectionSubstanceId())
        );
    }
}