package cn.newness.imip.module.oam.api;

import cn.newness.imip.module.oam.api.dto.StatusRecordDto;
import cn.newness.imip.module.oam.controller.admin.statusrecord.vo.StatusRecordSaveReqVO;
import cn.newness.imip.module.oam.service.StatusRecordService;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author machuran
 * @date 9/19/2024
 * @time 9:01 AM
 * @Description
 */
@Service
public class StatusRecordApiImpl implements StatusRecordApi{
    @Resource
    private StatusRecordService statusRecordService;

    @Override
    public void addStatusRecord(StatusRecordDto statusRecordDto) {
        StatusRecordSaveReqVO statusRecordSaveReqVO=new StatusRecordSaveReqVO();
        statusRecordSaveReqVO.setEquipmentprofileId(statusRecordDto.getId());
        statusRecordSaveReqVO.setEquipName(statusRecordDto.getEquipName());
        statusRecordSaveReqVO.setEquipCode(statusRecordDto.getCode());
        statusRecordSaveReqVO.setOperationType(statusRecordDto.getOperationType());
        statusRecordSaveReqVO.setNewStatus1(statusRecordDto.getStatus1());
        statusRecordSaveReqVO.setNewStatus2(statusRecordDto.getStatus2());
        statusRecordSaveReqVO.setDetails(statusRecordDto.getRemark());
        statusRecordSaveReqVO.setChangeDetails(statusRecordDto.getChangeDetails());
        statusRecordService.saveStatusRecord(statusRecordSaveReqVO);
    }
}
