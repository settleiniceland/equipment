package cn.newness.imip.module.oam.service;

import jakarta.validation.*;
import cn.newness.imip.module.oam.controller.admin.statusrecord.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.statusrecord.StatusRecordDO;
import cn.newness.imip.framework.common.pojo.PageResult;

/**
 * 停机表 Service 接口
 *
 * @author super超级管理员王大王
 */
public interface StatusRecordService {
    /**
     * 更新停机表
     *
     * @param updateReqVO 更新信息
     */
    void updateStatusRecord(@Valid StatusRecordSaveReqVO  updateReqVO);

    /**
     * 删除停机表
     *
     * @param id 编号
     */
    void deleteStatusRecord(String id);

    /**
     * 获得停机表
     *
     * @param id 编号
     * @return 停机表
     */
    StatusRecordDO getStatusRecord(String id);

    /**
     * 获得停机表分页
     *
     * @param pageReqVO 分页查询
     * @return 停机表分页
     */
    PageResult<StatusRecordRespVO> getStatusRecordPage(StatusRecordPageReqVO pageReqVO);
    /**
    * 新增一条状态记录
    *
    * @param
    * @author machuran
    * @date 10/8/2024
    * @Return
    */
    void saveStatusRecord(StatusRecordSaveReqVO addReqVO);
}