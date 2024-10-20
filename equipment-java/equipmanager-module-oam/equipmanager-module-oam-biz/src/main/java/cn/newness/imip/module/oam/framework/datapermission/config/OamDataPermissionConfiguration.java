package cn.newness.imip.module.oam.framework.datapermission.config;

import cn.newness.imip.framework.datapermission.core.rule.dept.DeptDataPermissionRule;
import cn.newness.imip.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import cn.newness.imip.module.oam.dal.dataobject.flaws.FlawsDO;
import cn.newness.imip.module.oam.dal.dataobject.inspectionprofile.InspectionProfileDO;
import cn.newness.imip.module.oam.dal.dataobject.inspectionsubstance.InspectionSubstanceDO;
import cn.newness.imip.module.oam.dal.dataobject.inspectplan.InspectplanDO;
import cn.newness.imip.module.oam.dal.dataobject.maintaindetail.MaintainDetailDO;
import cn.newness.imip.module.oam.dal.dataobject.maintainplan.MaintainPlanDO;
import cn.newness.imip.module.oam.dal.dataobject.statusrecord.StatusRecordDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author machuran
 * @date 9/5/2024
 * @time 8:19 AM
 * @Description
 */
@Configuration(proxyBeanMethods = false)
public class OamDataPermissionConfiguration {
    @Bean
    public DeptDataPermissionRuleCustomizer oamDataPermissionRuleCustomizer() {
        return rule -> {
            // dept
            rule.addDeptColumn(InspectplanDO.class);
            rule.addDeptColumn(InspectionSubstanceDO.class);
            rule.addDeptColumn(InspectionProfileDO.class);
            rule.addDeptColumn(FlawsDO.class);
            rule.addDeptColumn(StatusRecordDO.class);
            rule.addDeptColumn(MaintainPlanDO.class);
            rule.addDeptColumn(MaintainDetailDO.class);
        };
    }
}
