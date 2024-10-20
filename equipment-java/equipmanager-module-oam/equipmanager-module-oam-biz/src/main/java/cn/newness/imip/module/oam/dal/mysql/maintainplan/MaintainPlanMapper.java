package cn.newness.imip.module.oam.dal.mysql.maintainplan;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.oam.dal.dataobject.maintainplan.MaintainPlanDO;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.oam.controller.admin.maintainplan.vo.*;

/**
 * 保养计划 Mapper
 *
 * @author mcr
 */
@Mapper
public interface MaintainPlanMapper extends BaseMapperX<MaintainPlanDO> {

    default PageResult<MaintainPlanDO> selectPage(MaintainPlanPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaintainPlanDO>()
                .eqIfPresent(MaintainPlanDO::getId, reqVO.getId())
                .likeIfPresent(MaintainPlanDO::getName, reqVO.getName())
                .eqIfPresent(MaintainPlanDO::getExecuteDeptId, reqVO.getExecuteDeptId())
                .likeIfPresent(MaintainPlanDO::getExecuteDeptName, reqVO.getExecuteDeptName())
                .eqIfPresent(MaintainPlanDO::getStatus, reqVO.getStatus())
                .likeIfPresent(MaintainPlanDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MaintainPlanDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(MaintainPlanDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MaintainPlanDO::getUpdater, reqVO.getUpdater())
                .betweenIfPresent(MaintainPlanDO::getUpdateTime, reqVO.getUpdateTime())
                .orderByDesc(MaintainPlanDO::getId));
    }

}