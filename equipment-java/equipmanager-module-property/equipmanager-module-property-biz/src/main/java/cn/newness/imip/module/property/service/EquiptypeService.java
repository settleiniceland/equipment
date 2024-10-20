package cn.newness.imip.module.property.service;

import java.util.*;
import jakarta.validation.*;
import cn.newness.imip.module.property.controller.admin.equiptype.vo.*;
import cn.newness.imip.module.property.dal.dataobject.equiptype.EquiptypeDO;

/**
 * 设备类别 Service 接口
 *
 * @author mcr
 */
public interface EquiptypeService {

    /**
     * 创建设备类别
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createEquiptype(@Valid EquiptypeSaveReqVO createReqVO);

    /**
     * 更新设备类别
     *
     * @param updateReqVO 更新信息
     */
    void updateEquiptype(@Valid EquiptypeSaveReqVO updateReqVO);

    /**
     * 删除设备类别
     *
     * @param id 编号
     */
    void deleteEquiptype(String id);

    /**
     * 获得设备类别
     *
     * @param id 编号
     * @return 设备类别
     */
    EquiptypeDO getEquiptype(String id);

    /**
     * 获得设备类别列表
     *
     * @param listReqVO 查询条件
     * @return 设备类别列表
     */
    List<EquiptypeDO> getEquiptypeList(EquiptypeListReqVO listReqVO);
    /**
    * 专供导出excel
    *
    * @param [listReqVO]
    * @author machuran
    * @date 8/6/2024
    * @Return java.util.List<cn.newness.imip.module.property.controller.admin.equiptype.vo.EquiptypeRespVO>
    */
    List<EquiptypeRespVO> getEquiptypeListForExcel(EquiptypeListReqVO listReqVO);
}