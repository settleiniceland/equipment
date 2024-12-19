package cn.newness.imip.module.property.api;

import cn.newness.imip.module.property.service.EquipService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author machuran
 * @date 2024.11.19
 * @time 下午 01:53
 * @Description
 */
@Service
public class EquipApiImpl implements EquipApi{
    @Resource
    private EquipService equipService;

    @Override
    public String getAllLayerEquipName(String equipId) {
        return equipService.getCompleteEquipName(equipId);
    }
}
