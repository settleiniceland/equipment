package cn.newness.imip.module.property.service;

import java.util.*;
import jakarta.validation.*;
import cn.newness.imip.module.property.controller.admin.equip.vo.*;
import cn.newness.imip.module.property.dal.dataobject.equip.EquipDO;

/**
 * 设备表 Service 接口
 *
 * @author mcr
 */
public interface EquipService {

    /**
     * 创建设备表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createEquip(@Valid EquipSaveReqVO createReqVO);

    /**
     * 更新设备表
     *
     * @param updateReqVO 更新信息
     */
    void updateEquip(@Valid EquipSaveReqVO updateReqVO);

    /**
     * 删除设备表
     *
     * @param id 编号
     */
    void deleteEquip(String id);

    /**
     * 获得设备表
     *
     * @param id 编号
     * @return 设备表
     */
    EquipDO getEquip(String id);

    /**
     * 获得设备表列表
     *
     * @param listReqVO 查询条件
     * @return 设备表列表
     */
    List<EquipDO> getEquipList(EquipListReqVO listReqVO);
    /**
    *  获得设备表列表【专供导出excel表】
    *
    * @param [listReqVO]
    * @author machuran
    * @date 8/18/2024
    * @Return java.util.List<cn.newness.imip.module.property.controller.admin.equip.vo.EquipRespVO>
    */
    List<EquipRespVO> getEquipListForExcel(EquipListReqVO listReqVO);
    /**
    * 获取设备的全层级名
    *
    * @param [id]
    * @author machuran
    * @date 2024.11.19
    * @Return java.lang.String
    */
    String getCompleteEquipName(String id);
}