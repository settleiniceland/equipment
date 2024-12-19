package cn.newness.imip.module.property.api;

import cn.newness.imip.framework.common.util.object.BeanUtils;
import cn.newness.imip.module.property.api.dto.EquipmentProfileDto;
import cn.newness.imip.module.property.controller.admin.equipmentprofile.vo.EquipmentprofileListReqVO;
import cn.newness.imip.module.property.dal.dataobject.equipmentprofile.EquipmentprofileDO;
import cn.newness.imip.module.property.dal.mysql.equipmentprofile.EquipmentprofileMapper;
import cn.newness.imip.module.property.service.EquipmentprofileService;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author machuran
 * @date 9/13/2024
 * @time 3:55 PM
 * @Description
 */
@Service
public class EquipmentProfileApiImpl implements EquipmentProfileApi{
    @Resource
    private EquipmentprofileMapper equipmentprofileMapper;
    @Resource
    private EquipmentprofileService equipmentprofileService;
    @Override
    public void batchUpdateEquipmentProfile(List<String> equipmentProfileIds) {
        LambdaUpdateChainWrapper<EquipmentprofileDO> equipmentprofileDOLambdaUpdateChainWrapper=new LambdaUpdateChainWrapper<>(equipmentprofileMapper);
        equipmentprofileDOLambdaUpdateChainWrapper
                .in(EquipmentprofileDO::getId, equipmentProfileIds)
                .set(EquipmentprofileDO::getStatus1, 2)
                .update();
    }

    @Override
    public List<EquipmentProfileDto> getAllAttribute2EquipmentProfileDtoSons(String equipmentProfileId) {
        return equipmentprofileService.getAllAttribute2EquipmentProfileDtoSons(equipmentProfileId);
    }

    @Override
    public EquipmentProfileDto getSupAttribute2EquipmentProfileDto(String equipmentProfileId) {
        return equipmentprofileService.getSupAttribute2EquipmentProfileDto(equipmentProfileId);
    }

    @Override
    public List<EquipmentProfileDto> getEquipmentProfileList(EquipmentProfileDto equipmentProfileDto) {
        List<EquipmentprofileDO> equipmentprofileList = equipmentprofileService.getEquipmentprofileList(BeanUtils.toBean(equipmentProfileDto, EquipmentprofileListReqVO.class));
        return BeanUtils.toBean(equipmentprofileList, EquipmentProfileDto.class);
    }
}
