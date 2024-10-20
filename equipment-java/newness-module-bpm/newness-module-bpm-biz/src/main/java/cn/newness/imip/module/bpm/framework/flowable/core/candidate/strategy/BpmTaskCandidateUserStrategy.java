package cn.newness.imip.module.bpm.framework.flowable.core.candidate.strategy;

import cn.newness.imip.framework.common.util.string.StrUtils;
import cn.newness.imip.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import cn.newness.imip.module.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import cn.newness.imip.module.system.api.user.AdminUserApi;
import jakarta.annotation.Resource;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 用户 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @author kyle
 */
@Component
public class BpmTaskCandidateUserStrategy implements BpmTaskCandidateStrategy {

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.USER;
    }

    @Override
    public void validateParam(String param) {
        adminUserApi.validateUserList(StrUtils.splitToLongSet(param));
    }

    @Override
    public Set<Long> calculateUsers(DelegateExecution execution, String param) {
        return StrUtils.splitToLongSet(param);
    }

}