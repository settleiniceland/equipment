package cn.newness.imip.module.property.api;

import cn.newness.imip.framework.common.util.object.BeanUtils;
import cn.newness.imip.module.property.api.dto.InstalllocationDto;
import cn.newness.imip.module.property.dal.dataobject.installlocation.InstalllocationDO;
import cn.newness.imip.module.property.service.InstalllocationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author machuran
 * @date 8/22/2024
 * @time 5:06 PM
 * @Description
 */
@Service
public class LocationApiImpl implements LocationApi {
    @Resource
    private InstalllocationService installlocationService;

    @Override
    public String getCompleteLocationName(String id) {
        return installlocationService.getCompleteName(id);
    }

    @Override
    public InstalllocationDto getById(String id) {
        InstalllocationDO installlocation = installlocationService.getInstalllocation(id);
        return BeanUtils.toBean(installlocation, InstalllocationDto.class);
    }
}
