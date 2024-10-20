package cn.newness.imip.module.property.dal.mysql.manufacturer;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.property.dal.dataobject.manufacturer.ManufacturerDO;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.property.controller.admin.manufacturer.vo.*;

/**
 * 设备生产厂家信息 Mapper
 *
 * @author mcr
 */
@Mapper
public interface ManufacturerMapper extends BaseMapperX<ManufacturerDO> {

    default PageResult<ManufacturerDO> selectPage(ManufacturerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ManufacturerDO>()
                .likeIfPresent(ManufacturerDO::getName, reqVO.getName())
                .likeIfPresent(ManufacturerDO::getCode, reqVO.getCode())
                .eqIfPresent(ManufacturerDO::getStatus, reqVO.getStatus())
                .likeIfPresent(ManufacturerDO::getRemark, reqVO.getRemark())
                .orderByDesc(ManufacturerDO::getId));
    }

}