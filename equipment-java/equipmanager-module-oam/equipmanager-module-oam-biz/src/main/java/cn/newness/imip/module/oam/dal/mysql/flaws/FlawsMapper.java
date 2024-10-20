package cn.newness.imip.module.oam.dal.mysql.flaws;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.oam.dal.dataobject.flaws.FlawsDO;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.oam.controller.admin.flaws.vo.*;

/**
 * 缺陷库 Mapper
 *
 * @author super超级管理员王大王
 */
@Mapper
public interface FlawsMapper extends BaseMapperX<FlawsDO> {

    default PageResult<FlawsDO> selectPage(FlawsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FlawsDO>()
                .eqIfPresent(FlawsDO::getId, reqVO.getId())
                .eqIfPresent(FlawsDO::getEquipProfileId, reqVO.getEquipProfileId())
                .likeIfPresent(FlawsDO::getEquipProfileName, reqVO.getEquipProfileName())
                .likeIfPresent(FlawsDO::getEquipCode, reqVO.getEquipCode())
                .eqIfPresent(FlawsDO::getEquipAttribute, reqVO.getEquipAttribute())
                .eqIfPresent(FlawsDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FlawsDO::getIsStop, reqVO.getIsStop())
                .likeIfPresent(FlawsDO::getDetails, reqVO.getDetails())
                .betweenIfPresent(FlawsDO::getBeginTime, reqVO.getBeginTime())
                .betweenIfPresent(FlawsDO::getSolveTime, reqVO.getSolveTime())
                .eqIfPresent(FlawsDO::getFixPlanId, reqVO.getFixPlanId())
                .likeIfPresent(FlawsDO::getFixPlanName, reqVO.getFixPlanName())
                .eqIfPresent(FlawsDO::getSolveDuration, reqVO.getSolveDuration())
                .eqIfPresent(FlawsDO::getSolveDeptId, reqVO.getSolveDeptId())
                .likeIfPresent(FlawsDO::getSolveDeptName, reqVO.getSolveDeptName())
                .eqIfPresent(FlawsDO::getSolveHumanNum, reqVO.getSolveHumanNum())
                .eqIfPresent(FlawsDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(FlawsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FlawsDO::getUpdater, reqVO.getUpdater())
                .betweenIfPresent(FlawsDO::getUpdateTime, reqVO.getUpdateTime())
                .orderByDesc(FlawsDO::getBeginTime));
    }

}