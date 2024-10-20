package cn.newness.imip.module.property.service;

import java.util.*;
import jakarta.validation.*;
import cn.newness.imip.module.property.controller.admin.installlocation.vo.*;
import cn.newness.imip.module.property.dal.dataobject.installlocation.InstalllocationDO;

/**
 * 设备安装位置 Service 接口
 *
 * @author mcr
 */
public interface InstalllocationService {

    /**
     * 创建设备安装位置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createInstalllocation(@Valid InstalllocationSaveReqVO createReqVO);

    /**
     * 更新设备安装位置
     *
     * @param updateReqVO 更新信息
     */
    void updateInstalllocation(@Valid InstalllocationSaveReqVO updateReqVO);

    /**
     * 删除设备安装位置
     *
     * @param id 编号
     */
    void deleteInstalllocation(String id);

    /**
     * 获得设备安装位置
     *
     * @param id 编号
     * @return 设备安装位置
     */
    InstalllocationDO getInstalllocation(String id);

    /**
     * 获得设备安装位置列表
     *
     * @param listReqVO 查询条件
     * @return 设备安装位置列表
     */
    List<InstalllocationDO> getInstalllocationList(InstalllocationListReqVO listReqVO);
    /**
     * 专门用于导出excel
     *
     * @param [listReqVO]
     * @author machuran
     * @date 8/6/2024
     * @Return java.util.List<cn.newness.imip.module.property.dal.dataobject.installlocation.InstalllocationDO>
     */
    List<InstalllocationRespVO> getInstalllocationListForExcel(InstalllocationListReqVO listReqVO);
    /**
    * 获取完整设备位置名【全层级】
    *
    * @param [id]
    * @author machuran
    * @date 8/17/2024
    * @Return java.lang.String
    */
    String getCompleteName(String id);
}