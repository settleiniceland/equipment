package cn.newness.imip.module.property.dal.mysql.equip;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.property.dal.dataobject.equip.EquipDO;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.property.controller.admin.equip.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 设备表 Mapper
 *
 * @author mcr
 */
@Mapper
public interface EquipMapper extends BaseMapperX<EquipDO> {

    default List<EquipDO> selectList(EquipListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EquipDO>()
                .eqIfPresent(EquipDO::getId, reqVO.getId())
                .eqIfPresent(EquipDO::getSupId, reqVO.getSupId())
                .likeIfPresent(EquipDO::getEquipName, reqVO.getEquipName())
                .likeIfPresent(EquipDO::getEquipSpecification, reqVO.getEquipSpecification())
                .eqIfPresent(EquipDO::getEquipAttribute, reqVO.getEquipAttribute())
                .eqIfPresent(EquipDO::getEquiptypeId, reqVO.getEquiptypeId())
                .likeIfPresent(EquipDO::getEquiptypeName, reqVO.getEquiptypeName())
                .eqIfPresent(EquipDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(EquipDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(EquipDO::getUpdater, reqVO.getUpdater())
                .betweenIfPresent(EquipDO::getUpdateTime, reqVO.getUpdateTime())
                .orderByDesc(EquipDO::getId));
    }

	default EquipDO selectBySupIdAndEquipName(String supId, String equipName) {
	    return selectOne(EquipDO::getSupId, supId, EquipDO::getEquipName, equipName);
	}

    default Long selectCountBySupId(String supId) {
        return selectCount(EquipDO::getSupId, supId);
    }
    /**
    * 联动,根据类型id修改类型名
    *
    * @param [typeId, typeName]
    * @author machuran
    * @date 8/8/2024
    * @Return void
    */
    void updateTypeNameByTypeId(@Param("typeId")String typeId, @Param("typeName") String typeName);
}