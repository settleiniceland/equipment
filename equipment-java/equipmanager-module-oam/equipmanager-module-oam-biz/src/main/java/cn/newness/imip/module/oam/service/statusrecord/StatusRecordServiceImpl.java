package cn.newness.imip.module.oam.service.statusrecord;

import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.module.oam.dal.mysql.statusrecord.StatusRecordMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.newness.imip.module.oam.controller.admin.statusrecord.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.statusrecord.StatusRecordDO;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;

import cn.newness.imip.module.oam.service.StatusRecordService;

import java.time.LocalDateTime;

import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.oam.enums.ErrorCodeConstants.*;

/**
 * 停机表 Service 实现类
 *
 * @author super超级管理员王大王
 */
@Service
@Validated
public class StatusRecordServiceImpl implements StatusRecordService {

    @Resource
    private StatusRecordMapper statusRecordMapper;

    @Override
    public void updateStatusRecord(StatusRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateStatusRecordExists(updateReqVO.getId());
        // 更新
        StatusRecordDO updateObj = BeanUtils.toBean(updateReqVO, StatusRecordDO.class);
        statusRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteStatusRecord(String id) {
        // 校验存在
        validateStatusRecordExists(id);
        // 删除
        statusRecordMapper.deleteById(id);
    }

    private void validateStatusRecordExists(String id) {
        if (statusRecordMapper.selectById(id) == null) {
            throw exception(SR_NOT_EXISTS);
        }
    }

    @Override
    public StatusRecordDO getStatusRecord(String id) {
        return statusRecordMapper.selectById(id);
    }

    @Override
    public PageResult<StatusRecordRespVO> getStatusRecordPage(StatusRecordPageReqVO pageReqVO) {
        PageResult<StatusRecordDO> statusRecordDOPageResult = statusRecordMapper.selectPage(pageReqVO);
        PageResult<StatusRecordRespVO> statusRecordRespVOPage = BeanUtils.toBean(statusRecordDOPageResult, StatusRecordRespVO.class);
        statusRecordRespVOPage.getList().forEach(statusRecord -> {
            //操作类型赋值
            if(statusRecord.getOperationType()==1){
                statusRecord.setOperationTypeName("点检结果异常");
            }else if(statusRecord.getOperationType()==2){
                statusRecord.setOperationTypeName("手动操作");
            }
            //启停状态赋值
            if(statusRecord.getNewStatus1()!=null){
                if(statusRecord.getNewStatus1()==1){
                    statusRecord.setNewStatus1Name("开机");
                }else if(statusRecord.getNewStatus1()==2){
                    statusRecord.setNewStatus1Name("停机");
                }
            }
            //其他状态赋值
            if(statusRecord.getNewStatus2()!=null){
                if(statusRecord.getNewStatus2()==3){
                    statusRecord.setNewStatus2Name("异动中");
                }else if(statusRecord.getNewStatus2()==4){
                    statusRecord.setNewStatus2Name("异动完毕");
                }else if(statusRecord.getNewStatus2()==5){
                    statusRecord.setNewStatus2Name("回国返修中");
                }else if(statusRecord.getNewStatus2()==6){
                    statusRecord.setNewStatus2Name("回国返修完毕");
                }else if(statusRecord.getNewStatus2()==7){
                    statusRecord.setNewStatus2Name("报废");
                }
            }
            String cdn="";
            String[] split = statusRecord.getChangeDetails().split("_");
            if("1".equals(split[0])){
                cdn+="启停状态改变；";
            }
            if("1".equals(split[1])){
                cdn+="\n其他状态改变；";
            }
            statusRecord.setChangeDetailsName(cdn);
        });
        return statusRecordRespVOPage;
    }

    @Override
    public void saveStatusRecord(StatusRecordSaveReqVO addReqVO) {
        addReqVO.setId(BeanUtils.createId());
        addReqVO.setOperationTime(LocalDateTime.now());
        statusRecordMapper.insert(BeanUtils.toBean(addReqVO, StatusRecordDO.class));
    }
}