package cn.newness.imip.module.oam.service;

import jakarta.validation.*;
import cn.newness.imip.module.oam.controller.admin.maintainprofile.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.maintainprofile.MaintainProfileDO;
import cn.newness.imip.framework.common.pojo.PageResult;

/**
 * 保养日志表 Service 接口
 *
 * @author mcr
 */
public interface MaintainProfileService {

    /**
     * 创建保养日志表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMaintainProfile(@Valid MaintainProfileSaveReqVO createReqVO);

    /**
     * 更新保养日志表
     *
     * @param updateReqVO 更新信息
     */
    void updateMaintainProfile(@Valid MaintainProfileSaveReqVO updateReqVO);

    /**
     * 删除保养日志表
     *
     * @param id 编号
     */
    void deleteMaintainProfile(String id);

    /**
     * 获得保养日志表
     *
     * @param id 编号
     * @return 保养日志表
     */
    MaintainProfileDO getMaintainProfile(String id);

    /**
     * 获得保养日志表分页
     *
     * @param pageReqVO 分页查询
     * @return 保养日志表分页
     */
    PageResult<MaintainProfileDO> getMaintainProfilePage(MaintainProfilePageReqVO pageReqVO);

}