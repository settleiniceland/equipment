package cn.newness.imip.module.oam.service;

import jakarta.validation.*;
import cn.newness.imip.module.oam.dal.dataobject.inspectionsubstancemap.InspectionSubstanceMapDO;

/**
 * 点检计划内容映射表 Service 接口
 *
 * @author super超级管理员王中王
 */
public interface InspectionSubstanceMapService {

    /**
     * 创建点检计划内容映射表
     *
     * @return 编号
     */
    String createInspectionSubstanceMap(@Valid InspectionSubstanceMapDO createDo);

    /**
     * 更新点检计划内容映射表
     *
     */
    void updateInspectionSubstanceMap(@Valid InspectionSubstanceMapDO updateDo);

    /**
     * 删除点检计划内容映射表
     *
     * @param id 编号
     */
    void deleteInspectionSubstanceMap(String id);

    /**
     * 获得点检计划内容映射表
     *
     * @param id 编号
     * @return 点检计划内容映射表
     */
    InspectionSubstanceMapDO getInspectionSubstanceMap(String id);

}