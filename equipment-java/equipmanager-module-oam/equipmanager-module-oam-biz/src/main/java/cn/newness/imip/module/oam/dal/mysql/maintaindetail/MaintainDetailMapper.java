package cn.newness.imip.module.oam.dal.mysql.maintaindetail;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.oam.dal.dataobject.maintaindetail.MaintainDetailDO;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.oam.controller.admin.maintaindetail.vo.*;

/**
 * 保养内容表 Mapper
 *
 * @author mcr
 */
@Mapper
public interface MaintainDetailMapper extends BaseMapperX<MaintainDetailDO> {

    default PageResult<MaintainDetailDO> selectPage(MaintainDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaintainDetailDO>()
                .eqIfPresent(MaintainDetailDO::getId, reqVO.getId())
                .eqIfPresent(MaintainDetailDO::getEquipMaintainPlanId, reqVO.getEquipMaintainPlanId())
                .likeIfPresent(MaintainDetailDO::getEquipMaintainPlanName, reqVO.getEquipMaintainPlanName())
                .eqIfPresent(MaintainDetailDO::getEquipId, reqVO.getEquipId())
                .eqIfPresent(MaintainDetailDO::getIsSpecial, reqVO.getIsSpecial())
                .eqIfPresent(MaintainDetailDO::getEquipprofileId, reqVO.getEquipprofileId())
                .eqIfPresent(MaintainDetailDO::getIsReferto, reqVO.getIsReferto())
                .eqIfPresent(MaintainDetailDO::getRefertoId, reqVO.getRefertoId())
                .likeIfPresent(MaintainDetailDO::getEquipName, reqVO.getEquipName())
                .likeIfPresent(MaintainDetailDO::getEquipSpecification, reqVO.getEquipSpecification())
                .eqIfPresent(MaintainDetailDO::getMaintainCycle, reqVO.getMaintainCycle())
                .eqIfPresent(MaintainDetailDO::getReplaceSelf, reqVO.getReplaceSelf())
                .likeIfPresent(MaintainDetailDO::getDetails, reqVO.getDetails())
                .eqIfPresent(MaintainDetailDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(MaintainDetailDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MaintainDetailDO::getUpdater, reqVO.getUpdater())
                .betweenIfPresent(MaintainDetailDO::getUpdateTime, reqVO.getUpdateTime())
                .orderByDesc(MaintainDetailDO::getUpdateTime));
    }
}