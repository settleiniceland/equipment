package cn.newness.imip.module.property.api;

import cn.newness.imip.module.property.api.dto.InstalllocationDto;

/**
 * @author machuran
 * @date 8/22/2024
 * @time 5:00 PM
 * @Description
 */
public interface LocationApi {
    /**
    * 获取全层级完整位置名
    *
    * @param []
    * @author machuran
    * @date 8/22/2024
    * @Return java.lang.String
    */
    String getCompleteLocationName(String id);
    /**
    * 根据id获取安装位置对象
    *
    * @param [id]
    * @author machuran
    * @date 2024-11-15
    * @Return cn.newness.imip.module.property.api.dto.InstalllocationDto
    */
    InstalllocationDto getById(String id);
}
