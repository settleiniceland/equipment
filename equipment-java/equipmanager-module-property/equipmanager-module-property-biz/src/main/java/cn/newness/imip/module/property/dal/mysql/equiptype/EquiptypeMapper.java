package cn.newness.imip.module.property.dal.mysql.equiptype;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.property.dal.dataobject.equiptype.EquiptypeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.property.controller.admin.equiptype.vo.*;

/**
 * 设备类别 Mapper
 *
 * @author mcr
 */
@Mapper
public interface EquiptypeMapper extends BaseMapperX<EquiptypeDO> {

    default List<EquiptypeDO> selectList(EquiptypeListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EquiptypeDO>()
                .eqIfPresent(EquiptypeDO::getId, reqVO.getId())
                .likeIfPresent(EquiptypeDO::getCode, reqVO.getCode())
                .likeIfPresent(EquiptypeDO::getName, reqVO.getName())
                .eqIfPresent(EquiptypeDO::getSupId, reqVO.getSupId())
                .eqIfPresent(EquiptypeDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(EquiptypeDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(EquiptypeDO::getUpdater, reqVO.getUpdater())
                .betweenIfPresent(EquiptypeDO::getUpdateTime, reqVO.getUpdateTime())
                .orderByDesc(EquiptypeDO::getId));
    }

	default EquiptypeDO selectBySupIdAndName(String supId, String name) {
	    return selectOne(EquiptypeDO::getSupId, supId, EquiptypeDO::getName, name);
	}

    default Long selectCountBySupId(String supId) {
        return selectCount(EquiptypeDO::getSupId, supId);
    }

}