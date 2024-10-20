package cn.newness.imip.module.oam.service;

import jakarta.validation.*;
import cn.newness.imip.module.oam.controller.admin.flaws.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.flaws.FlawsDO;
import cn.newness.imip.framework.common.pojo.PageResult;

/**
 * 缺陷库 Service 接口
 *
 * @author super超级管理员王大王
 */
public interface FlawsService {

    /**
     * 创建缺陷库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createFlaws(@Valid FlawsSaveReqVO createReqVO);

    /**
     * 更新缺陷库
     *
     * @param updateReqVO 更新信息
     */
    void updateFlaws(@Valid FlawsSaveReqVO updateReqVO);

    /**
     * 删除缺陷库
     *
     * @param id 编号
     */
    void deleteFlaws(String id);

    /**
     * 获得缺陷库
     *
     * @param id 编号
     * @return 缺陷库
     */
    FlawsDO getFlaws(String id);

    /**
     * 获得缺陷库分页
     *
     * @param pageReqVO 分页查询
     * @return 缺陷库分页
     */
    PageResult<FlawsRespVO> getFlawsPage(FlawsPageReqVO pageReqVO);
}