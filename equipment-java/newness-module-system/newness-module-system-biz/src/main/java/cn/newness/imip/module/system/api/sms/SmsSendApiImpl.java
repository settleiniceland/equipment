package cn.newness.imip.module.system.api.sms;

import cn.newness.imip.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;
import cn.newness.imip.module.system.service.sms.SmsSendService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

/**
 * 短信发送 API 接口
 *
 * @author 新奇源码
 */
@Service
@Validated
public class SmsSendApiImpl implements SmsSendApi {

    @Resource
    private SmsSendService smsSendService;

    @Override
    public Long sendSingleSmsToAdmin(SmsSendSingleToUserReqDTO reqDTO) {
        return smsSendService.sendSingleSmsToAdmin(reqDTO.getMobile(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

    @Override
    public Long sendSingleSmsToMember(SmsSendSingleToUserReqDTO reqDTO) {
        return smsSendService.sendSingleSmsToMember(reqDTO.getMobile(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

}