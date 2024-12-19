package cn.newness.imip.module.oam.dal.mysql.inspectionsubstance;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.oam.dal.dataobject.inspectionsubstance.InspectionSubstanceDO;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.*;

/**
 * 点检内容 Mapper
 *
 * @author super超级管理员王中王
 */
@Mapper
public interface InspectionSubstanceMapper extends BaseMapperX<InspectionSubstanceDO> {

    default PageResult<InspectionSubstanceDO> selectPage(InspectionSubstancePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InspectionSubstanceDO>()
                .eqIfPresent(InspectionSubstanceDO::getId, reqVO.getId())
                .eqIfPresent(InspectionSubstanceDO::getEquipId, reqVO.getEquipId())
                .likeIfPresent(InspectionSubstanceDO::getEquipName, reqVO.getEquipName())
                .likeIfPresent(InspectionSubstanceDO::getEquipSpecification, reqVO.getEquipSpecification())
                .likeIfPresent(InspectionSubstanceDO::getDetails, reqVO.getDetails())
                .likeIfPresent(InspectionSubstanceDO::getStandard, reqVO.getStandard())
                .eqIfPresent(InspectionSubstanceDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(InspectionSubstanceDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(InspectionSubstanceDO::getUpdater, reqVO.getUpdater())
                .betweenIfPresent(InspectionSubstanceDO::getUpdateTime, reqVO.getUpdateTime())
                .orderByDesc(InspectionSubstanceDO::getEquipName, InspectionSubstanceDO::getDetails));
    }
    /**
    * 根据点巡检计划id及一些其余条件查询与该计划关联的那些点巡检内容
    *
    * @param [pageReqVO]
    * @author machuran
    * @date 8/26/2024
    * @Return java.util.List<cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.InspectionSubstanceRespVO>
    */
    List<InspectionSubstanceRespVO> selectPageByPlanId(InspectionSubstancePageReqVO pageReqVO);
    /**
    * 据点巡检计划id及一些其余条件查询与该计划关联的那些点巡检内容(不分页,全部)
    *
    * @param \
    * @author machuran
    * @date 8/27/2024
    * @Return java.util.List<cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.InspectionSubstanceRespVO>
    */
    List<InspectionSubstanceRespVO> selectPageByPlanIdForExcel(InspectionSubstancePageReqVO pageReqVO);
    /**
    * 根据点巡检计划id及一些其余条件查询与该计划不关联的那些点巡检内容
    *
    * @param [pageReqVO]
    * @author machuran
    * @date 8/26/2024
    * @Return java.util.List<cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.InspectionSubstanceRespVO>
    */
    List<InspectionSubstanceRespVO> selectAddPageByPlanId(InspectionSubstancePageReqVO pageReqVO);
    /**
    * 辅助分页查询，查到符合条件的总数量
    *
    * @param [pageReqVO]
    * @author machuran
    * @date 9/9/2024
    * @Return java.lang.Long
    */
    Long selectAddPageCountByPlanId(InspectionSubstancePageReqVO pageReqVO);
}