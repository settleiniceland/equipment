package cn.newness.imip.module.oam.dal.mysql.inspectplan;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.oam.controller.app.inspectplan.vo.InspectplanAppVO;
import cn.newness.imip.module.oam.dal.dataobject.inspectplan.InspectplanDO;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.oam.controller.admin.inspectplan.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 点检计划表 Mapper
 *
 * @author super超级管理员王中王
 */
@Mapper
public interface InspectplanMapper extends BaseMapperX<InspectplanDO> {

    default PageResult<InspectplanDO> selectPage(InspectplanPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InspectplanDO>()
                .eqIfPresent(InspectplanDO::getId, reqVO.getId())
                .eqIfPresent(InspectplanDO::getStatus, reqVO.getStatus())
                .eqIfPresent(InspectplanDO::getInspectionType, reqVO.getInspectionType())
                .eqIfPresent(InspectplanDO::getInspectionCycle, reqVO.getInspectionCycle())
                .likeIfPresent(InspectplanDO::getName, reqVO.getName())
                .eqIfPresent(InspectplanDO::getEquiplocationId, reqVO.getEquiplocationId())
                .likeIfPresent(InspectplanDO::getEquiplocationName, reqVO.getEquiplocationName())
                .eqIfPresent(InspectplanDO::getDetail, reqVO.getDetail())
                .orderByDesc(InspectplanDO::getId));
    }

    List<InspectplanAppVO> getInspectplanListForApp(InspectplanAppVO inspectplanAppVO);

    List<String> getPlanNameBySubstanceId(@Param("id") String id);
}