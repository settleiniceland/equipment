package cn.newness.imip.module.oam.service;

import jakarta.validation.*;
import cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.inspectionprofile.InspectionProfileDO;
import cn.newness.imip.framework.common.pojo.PageResult;

import java.util.List;

/**
 * 点检日志表 Service 接口
 *
 * @author super超级管理员王中王
 */
public interface InspectionProfileService {

    /**
     * 创建点检日志表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createInspectionProfile(@Valid InspectionProfileSaveReqVO createReqVO);

    /**
     * 更新点检日志表
     *
     * @param updateReqVO 更新信息
     */
    void updateInspectionProfile(@Valid InspectionProfileSaveReqVO updateReqVO);

    /**
     * 删除点检日志表
     *
     * @param id 编号
     */
    void deleteInspectionProfile(String id);

    /**
     * 获得点检日志表
     *
     * @param id 编号
     * @return 点检日志表
     */
    InspectionProfileDO getInspectionProfile(String id);

    /**
     * 获得点检日志表分页
     *
     * @param pageReqVO 分页查询
     * @return 点检日志表分页
     */
    PageResult<InspectionProfileRespVO> getInspectionProfilePage(InspectionProfilePageReqVO pageReqVO);
    /**
    * 批量添加点检日志【一次添加一计划的点检日志】
    *
    * @param [profileVOList]
    * @author machuran
    * @date 9/7/2024
    * @Return void
    */
    void addProfileList(List<InspectionProfileSaveReqVO> profileVOList);
}