package cn.newness.imip.module.property.framework.datapermission.config;

import cn.newness.imip.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import cn.newness.imip.module.property.dal.dataobject.equip.EquipDO;
import cn.newness.imip.module.property.dal.dataobject.equipmentprofile.EquipmentprofileDO;
import cn.newness.imip.module.property.dal.dataobject.equiptype.EquiptypeDO;
import cn.newness.imip.module.property.dal.dataobject.installlocation.InstalllocationDO;
import cn.newness.imip.module.property.dal.dataobject.manufacturer.ManufacturerDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author machuran
 * @date 8/6/2024
 * @time 10:42 AM
 * @Description
 */

@Configuration(proxyBeanMethods = false)
public class PropertyDataPermissionConfiguration {
    @Bean
    public DeptDataPermissionRuleCustomizer propertyDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // dept
            rule.addDeptColumn(InstalllocationDO.class);
            rule.addDeptColumn(ManufacturerDO.class);
            rule.addDeptColumn(EquiptypeDO.class);
            rule.addDeptColumn(EquipDO.class);
            rule.addDeptColumn(EquipmentprofileDO.class);
        };
    }
}