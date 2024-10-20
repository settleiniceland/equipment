package cn.newness.imip.module.oam.service;

import cn.newness.imip.module.oam.controller.app.inspectplan.vo.InspectplanAppVO;
import cn.newness.imip.module.oam.dal.dataobject.inspectionsubstancemap.InspectionSubstanceMapDO;
import jakarta.validation.*;
import cn.newness.imip.module.oam.controller.admin.inspectplan.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.inspectplan.InspectplanDO;
import cn.newness.imip.framework.common.pojo.PageResult;

import java.util.List;

/**
 * 点检计划表 Service 接口
 *
 * @author super超级管理员王中王
 */
public interface InspectplanService {

    /**
     * 创建点检计划表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createInspectplan(@Valid InspectplanSaveReqVO createReqVO);

    /**
     * 更新点检计划表
     *
     * @param updateReqVO 更新信息
     */
    void updateInspectplan(@Valid InspectplanSaveReqVO updateReqVO);

    /**
     * 删除点检计划表
     *
     * @param id     编号
     * @param status
     */
    void deleteInspectplan(String id, Integer status);

    /**
     * 获得点检计划表
     *
     * @param id 编号
     * @return 点检计划表
     */
    InspectplanDO getInspectplan(String id);

    /**
     * 获得点检计划表分页
     *
     * @param pageReqVO 分页查询
     * @return 点检计划表分页
     */
    PageResult<InspectplanDO> getInspectplanPage(InspectplanPageReqVO pageReqVO);
    /**
    * 批量为点检计划添加点检内容
    *
    * @param [planId, substanceIds]
    * @author machuran
    * @date 8/26/2024
    * @Return void
    */
    void addSubstancesToPlan(String planId, List<String> substanceIds);
    /**
    * 从点检计划中剔除某项内容
    *
    * @param [mapDO]
    * @author machuran
    * @date 8/27/2024
    * @Return void
    */
    void delSubstanceForPlan(InspectionSubstanceMapDO mapDO);

/********************************************************************>>>>>>>>>>>>>APP端<<<<<<<<<<<<<<<<<<******************************************************************/

    /**
    * app端，获取所有启用的计划，按照修改时间逆序排序，并识别出所有到达点检时间的点检计划
    *
    * @param [pageReqVO]
    * @author machuran
    * @date 9/2/2024
    * @Return java.util.List<cn.newness.imip.module.oam.controller.admin.inspectplan.vo.InspectplanRespVO>
    */
    List<InspectplanAppVO> getInspectplanListForApp(@Valid InspectplanAppVO inspectplanAppVO);
}