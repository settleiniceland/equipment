package cn.newness.imip.module.oam.service;

import jakarta.validation.*;
import cn.newness.imip.module.oam.controller.admin.maintaindetail.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.maintaindetail.MaintainDetailDO;
import cn.newness.imip.framework.common.pojo.PageResult;

/**
 * 保养内容表 Service 接口
 *
 * @author mcr
 */
public interface MaintainDetailService {

    /**
     * 创建保养内容表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMaintainDetail(@Valid MaintainDetailSaveReqVO createReqVO);

    /**
     * 更新保养内容表
     *
     * @param updateReqVO 更新信息
     */
    void updateMaintainDetail(@Valid MaintainDetailSaveReqVO updateReqVO);

    /**
     * 删除保养内容表
     *
     * @param id 编号
     */
    void deleteMaintainDetail(String id);

    /**
     * 获得保养内容表
     *
     * @param id 编号
     * @return 保养内容表
     */
    MaintainDetailRespVO getMaintainDetail(String id);

    /**
     * 获得保养内容表分页
     *
     * @param pageReqVO 分页查询
     * @return 保养内容表分页
     */
    PageResult<MaintainDetailRespVO> getMaintainDetailPage(MaintainDetailPageReqVO pageReqVO);
    /**
    * 根据保养计划id删除
    *
    * @param
    * @author machuran
    * @date 10/15/2024
    * @Return
    */
    void deleteMaintainDetailByPlanId(String planId);
}