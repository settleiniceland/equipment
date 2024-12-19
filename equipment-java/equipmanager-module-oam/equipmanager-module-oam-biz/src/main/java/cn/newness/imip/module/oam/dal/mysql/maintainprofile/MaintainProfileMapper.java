package cn.newness.imip.module.oam.dal.mysql.maintainprofile;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.oam.dal.dataobject.maintainprofile.MaintainProfileDO;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.oam.controller.admin.maintainprofile.vo.*;

/**
 * 保养日志表 Mapper
 *
 * @author mcr
 */
@Mapper
public interface MaintainProfileMapper extends BaseMapperX<MaintainProfileDO> {

    default PageResult<MaintainProfileDO> selectPage(MaintainProfilePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaintainProfileDO>()
                .eqIfPresent(MaintainProfileDO::getId, reqVO.getId())
                .eqIfPresent(MaintainProfileDO::getEquipMaintainPlanId, reqVO.getEquipMaintainPlanId())
                .likeIfPresent(MaintainProfileDO::getEquipMaintainPlanName, reqVO.getEquipMaintainPlanName())
                .eqIfPresent(MaintainProfileDO::getEquipMaintainDetailId, reqVO.getEquipMaintainDetailId())
                .likeIfPresent(MaintainProfileDO::getEquipMaintainDetail, reqVO.getEquipMaintainDetail())
                .eqIfPresent(MaintainProfileDO::getIsSpecial, reqVO.getIsSpecial())
                .eqIfPresent(MaintainProfileDO::getEquipmentprofileId, reqVO.getEquipmentprofileId())
                .likeIfPresent(MaintainProfileDO::getEquipmentprofileCode, reqVO.getEquipmentprofileCode())
                .likeIfPresent(MaintainProfileDO::getEquipName, reqVO.getEquipName())
                .eqIfPresent(MaintainProfileDO::getMaintainCycle, reqVO.getMaintainCycle())
                .eqIfPresent(MaintainProfileDO::getResultPhotos, reqVO.getResultPhotos())
                .betweenIfPresent(MaintainProfileDO::getMaintainDate, reqVO.getMaintainDate())
                .eqIfPresent(MaintainProfileDO::getExecuteDeptId, reqVO.getExecuteDeptId())
                .likeIfPresent(MaintainProfileDO::getExecuteDeptName, reqVO.getExecuteDeptName())
                .likeIfPresent(MaintainProfileDO::getActualMaintainNames, reqVO.getActualMaintainNames())
                .likeIfPresent(MaintainProfileDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MaintainProfileDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(MaintainProfileDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MaintainProfileDO::getUpdater, reqVO.getUpdater())
                .betweenIfPresent(MaintainProfileDO::getUpdateTime, reqVO.getUpdateTime())
                .orderByDesc(MaintainProfileDO::getId));
    }

}