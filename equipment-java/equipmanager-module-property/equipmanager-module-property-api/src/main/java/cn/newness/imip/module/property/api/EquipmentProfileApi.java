package cn.newness.imip.module.property.api;

import cn.newness.imip.module.property.api.dto.EquipmentProfileDto;

import java.util.List;

/**
 * @author machuran
 * @date 9/13/2024
 * @time 3:33 PM
 * @Description
 */
public interface EquipmentProfileApi {
    /**
    * 批量改变设备启停机状态
    *
    * @param [equipmentProfileIds]
    * @author machuran
    * @date 9/13/2024
    * @Return void
    */
    void batchUpdateEquipmentProfile(List<String> equipmentProfileIds);
    /**
    * 根据设备组id获取其下面所有的单体设备集合
    *
    * @param [equipmentProfileId]
    * @author machuran
    * @date 9/13/2024
    * @Return List<EquipmentProfileDto>
    */
    List<EquipmentProfileDto> getAllAttribute2EquipmentProfileDtoSons(String equipmentProfileId);
    /**
    * 根据设备组件id获取其上级单体设备
    *
    * @param [equipmentProfileId]
    * @author machuran
    * @date 9/13/2024
    * @Return EquipmentProfileDto
    */
    EquipmentProfileDto getSupAttribute2EquipmentProfileDto(String equipmentProfileId);
}
