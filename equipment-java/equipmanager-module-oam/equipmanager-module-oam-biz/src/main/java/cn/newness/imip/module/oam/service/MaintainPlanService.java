package cn.newness.imip.module.oam.service;

import jakarta.validation.*;
import cn.newness.imip.module.oam.controller.admin.maintainplan.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.maintainplan.MaintainPlanDO;
import cn.newness.imip.framework.common.pojo.PageResult;

/**
 * 保养计划 Service 接口
 *
 * @author mcr
 */
public interface MaintainPlanService {

    /**
     * 创建保养计划
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMaintainPlan(@Valid MaintainPlanSaveReqVO createReqVO);

    /**
     * 更新保养计划
     *
     * @param updateReqVO 更新信息
     */
    void updateMaintainPlan(@Valid MaintainPlanSaveReqVO updateReqVO);

    /**
     * 删除保养计划
     *
     * @param id 编号
     */
    void deleteMaintainPlan(String id);

    /**
     * 获得保养计划
     *
     * @param id 编号
     * @return 保养计划
     */
    MaintainPlanRespVO getMaintainPlan(String id);

    /**
     * 获得保养计划分页
     *
     * @param pageReqVO 分页查询
     * @return 保养计划分页
     */
    PageResult<MaintainPlanRespVO> getMaintainPlanPage(MaintainPlanPageReqVO pageReqVO);

}