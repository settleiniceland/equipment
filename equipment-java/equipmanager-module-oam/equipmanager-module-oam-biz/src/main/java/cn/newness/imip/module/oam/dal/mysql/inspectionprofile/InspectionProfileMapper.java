package cn.newness.imip.module.oam.dal.mysql.inspectionprofile;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.oam.dal.dataobject.inspectionprofile.InspectionProfileDO;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo.*;

/**
 * 点检日志表 Mapper
 *
 * @author super超级管理员王中王
 */
@Mapper
public interface InspectionProfileMapper extends BaseMapperX<InspectionProfileDO> {

    default PageResult<InspectionProfileDO> selectPage(InspectionProfilePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InspectionProfileDO>()
                .eqIfPresent(InspectionProfileDO::getId, reqVO.getId())
                .eqIfPresent(InspectionProfileDO::getInspectionPlanId, reqVO.getInspectionPlanId())
                .eqIfPresent(InspectionProfileDO::getInspectionDetailId, reqVO.getInspectionDetailId())
                .likeIfPresent(InspectionProfileDO::getInspectionDetail, reqVO.getInspectionDetail())
                .eqIfPresent(InspectionProfileDO::getEquipProfileId, reqVO.getEquipProfileId())
                .likeIfPresent(InspectionProfileDO::getEquipProfileName, reqVO.getEquipProfileName())
                .eqIfPresent(InspectionProfileDO::getIsStop, reqVO.getIsStop())
                .likeIfPresent(InspectionProfileDO::getEquipCode, reqVO.getEquipCode())
                .eqIfPresent(InspectionProfileDO::getEquipAttribute, reqVO.getEquipAttribute())
                .eqIfPresent(InspectionProfileDO::getResult, reqVO.getResult())
                .likeIfPresent(InspectionProfileDO::getResultDetail, reqVO.getResultDetail())
                .betweenIfPresent(InspectionProfileDO::getInspectionDate, reqVO.getInspectionDate())
                .likeIfPresent(InspectionProfileDO::getInspectionUsers, reqVO.getInspectionUsers())
                .likeIfPresent(InspectionProfileDO::getDutyUsers, reqVO.getDutyUsers())
                .likeIfPresent(InspectionProfileDO::getInspectionPlanName, reqVO.getInspectionPlanName())
                .eqIfPresent(InspectionProfileDO::getInspectionType, reqVO.getInspectionType())
                .eqIfPresent(InspectionProfileDO::getInspectionCycle, reqVO.getInspectionCycle())
                .eqIfPresent(InspectionProfileDO::getEquiplocationId, reqVO.getEquiplocationId())
                .likeIfPresent(InspectionProfileDO::getEquiplocationName, reqVO.getEquiplocationName())
                .orderByDesc(InspectionProfileDO::getInspectionDate));
    }
    List<InspectionProfileDO> getNewestProfileList(List<String> idList);
}