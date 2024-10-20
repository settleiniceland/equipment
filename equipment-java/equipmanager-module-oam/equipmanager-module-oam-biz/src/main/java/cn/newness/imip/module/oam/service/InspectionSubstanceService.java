package cn.newness.imip.module.oam.service;

import cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo.InspectionProfileSaveReqVO;
import jakarta.validation.*;
import cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.inspectionsubstance.InspectionSubstanceDO;
import cn.newness.imip.framework.common.pojo.PageResult;

import java.util.List;

/**
 * 点检内容 Service 接口
 *
 * @author super超级管理员王中王
 */
public interface InspectionSubstanceService {

    /**
     * 创建点检内容
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createInspectionSubstance(@Valid InspectionSubstanceSaveReqVO createReqVO);

    /**
     * 更新点检内容
     *
     * @param updateReqVO 更新信息
     */
    void updateInspectionSubstance(@Valid InspectionSubstanceSaveReqVO updateReqVO);

    /**
     * 删除点检内容
     *
     * @param id 编号
     */
    void deleteInspectionSubstance(String id);

    /**
     * 获得点检内容
     *
     * @param id 编号
     * @return 点检内容
     */
    InspectionSubstanceDO getInspectionSubstance(String id);

    /**
     * 获得点检内容分页
     *
     * @param pageReqVO 分页查询
     * @return 点检内容分页
     */
    PageResult<InspectionSubstanceDO> getInspectionSubstancePage(InspectionSubstancePageReqVO pageReqVO);

    /**
    * 根据点检计划id获取对应的点检内容列表
    *
    * @param [pageReqVO]
    * @author machuran
    * @date 8/26/2024
    * @Return cn.newness.imip.framework.common.pojo.PageResult<cn.newness.imip.module.oam.dal.dataobject.inspectionsubstance.InspectionSubstanceDO>
    */
    PageResult<InspectionSubstanceRespVO> getInspectionSubstancePageByPlanId(InspectionSubstancePageReqVO pageReqVO);
    /**
     * 根据点检计划id获取对应的点检内容列表(不分页,专供导出使用)
     *
     * @param [pageReqVO]
     * @author machuran
     * @date 8/27/2024
     * @Return cn.newness.imip.framework.common.pojo.PageResult<cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.InspectionSubstanceRespVO>
     */
    List<InspectionSubstanceRespVO> getInspectionSubstanceByPlanIdForExcel(InspectionSubstancePageReqVO pageReqVO);
    /**
    * 根据点检计划id获取对应的点检内容之外的点检内容列表
    *
    * @param [pageReqVO]
    * @author machuran
    * @date 8/26/2024
    * @Return cn.newness.imip.framework.common.pojo.PageResult<cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.InspectionSubstanceRespVO>
    */
    PageResult<InspectionSubstanceRespVO> getInspectionSubstanceAddPageByPlanId(InspectionSubstancePageReqVO pageReqVO);
}