package cn.newness.imip.module.property.dal.mysql.installlocation;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.property.dal.dataobject.installlocation.InstalllocationDO;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.property.controller.admin.installlocation.vo.*;

/**
 * 设备安装位置 Mapper
 *
 * @author mcr
 */
@Mapper
public interface InstalllocationMapper extends BaseMapperX<InstalllocationDO> {

    default List<InstalllocationDO> selectList(InstalllocationListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<InstalllocationDO>()
                .eqIfPresent(InstalllocationDO::getId, reqVO.getId())
                .likeIfPresent(InstalllocationDO::getCode, reqVO.getCode())
                .eqIfPresent(InstalllocationDO::getSupId, reqVO.getSupId())
                .likeIfPresent(InstalllocationDO::getName, reqVO.getName())
                .likeIfPresent(InstalllocationDO::getDutyName, reqVO.getDutyName())
                .likeIfPresent(InstalllocationDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(InstalllocationDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(InstalllocationDO::getUpdater, reqVO.getUpdater())
                .betweenIfPresent(InstalllocationDO::getUpdateTime, reqVO.getUpdateTime())
                .orderByDesc(InstalllocationDO::getId));
    }

	default InstalllocationDO selectBySupIdAndName(String supId, String name) {
	    return selectOne(InstalllocationDO::getSupId, supId, InstalllocationDO::getName, name);
	}

    default Long selectCountBySupId(String supId) {
        return selectCount(InstalllocationDO::getSupId, supId);
    }

}