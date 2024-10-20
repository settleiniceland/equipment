package cn.newness.imip.module.property.service;

import java.util.*;

import cn.newness.imip.module.property.api.dto.EquipmentProfileDto;
import jakarta.validation.*;
import cn.newness.imip.module.property.controller.admin.equipmentprofile.vo.*;
import cn.newness.imip.module.property.dal.dataobject.equipmentprofile.EquipmentprofileDO;

/**
 * 设备档案数据 Service 接口
 *
 * @author mcr
 */
public interface EquipmentprofileService {

    /**
     * 创建设备档案数据
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createEquipmentprofile(@Valid EquipmentprofileSaveReqVO createReqVO);

    /**
     * 更新设备档案数据
     *
     * @param updateReqVO 更新信息
     */
    void updateEquipmentprofile(@Valid EquipmentprofileSaveReqVO updateReqVO);

    /**
     * 删除设备档案数据
     *
     * @param id 编号
     */
    void deleteEquipmentprofile(String id,String code,Integer equipAttribute);

    /**
     * 获得设备档案数据
     *
     * @param id 编号
     * @return 设备档案数据
     */
    EquipmentprofileDO getEquipmentprofile(String id);

    /**
     * 获得设备档案数据列表
     *
     * @param listReqVO 查询条件
     * @return 设备档案数据列表
     */
    List<EquipmentprofileDO> getEquipmentprofileList(EquipmentprofileListReqVO listReqVO);
    /**
    * 通过设备id获取设备档案id
    *
    * @param [equipId]
    * @author machuran
    * @date 8/18/2024
    * @Return java.util.List<cn.newness.imip.module.property.controller.admin.equipmentprofile.vo.EquipmentprofileRespVO>
    */
    List<EquipmentprofileRespVO> getEquipmentprofileListByEquipId(String equipId);
    /**
    * 查询设备档案列表，专供导出excel
    *
    * @param [listReqVO]
    * @author machuran
    * @date 8/18/2024
    * @Return java.util.List<cn.newness.imip.module.property.controller.admin.equipmentprofile.vo.EquipmentprofileRespVO>
    */
    List<EquipmentprofileRespVO> getEquipmentprofileListForExcel(EquipmentprofileListReqVO listReqVO);
    /**
    * 根据设备组id获取其下面所有的单体设备
    *
    * @param [equipmentProfileId]
    * @author machuran
    * @date 9/13/2024
    * @Return java.util.List<EquipmentProfileDto>
    */
    List<EquipmentProfileDto> getAllAttribute2EquipmentProfileDtoSons(String equipmentProfileId);
    /**
    *  根据设备组件id获取其上级单体设备
    *
    * @param [equipmentProfileId]
    * @author machuran
    * @date 9/13/2024
    * @Return EquipmentProfileDto
    */
    EquipmentProfileDto getSupAttribute2EquipmentProfileDto(String equipmentProfileId);
    /**
    * 更新设备档案的状态
    *
    * @param [updateReqVO]
    * @author machuran
    * @date 10/6/2024
    * @Return void
    */
    void updateEquipmentprofileStatus(EquipmentprofileStatusSaveReqVO statusUpdateReqVO);
}