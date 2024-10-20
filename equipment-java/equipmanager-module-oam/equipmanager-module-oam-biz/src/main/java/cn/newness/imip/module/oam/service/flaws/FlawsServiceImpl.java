package cn.newness.imip.module.oam.service.flaws;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.newness.imip.module.oam.controller.admin.flaws.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.flaws.FlawsDO;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;

import cn.newness.imip.module.oam.dal.mysql.flaws.FlawsMapper;
import cn.newness.imip.module.oam.service.FlawsService;

import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.oam.enums.ErrorCodeConstants.*;

/**
 * 缺陷库 Service 实现类
 *
 * @author super超级管理员王大王
 */
@Service
@Validated
public class FlawsServiceImpl implements FlawsService {

    @Resource
    private FlawsMapper flawsMapper;

    @Override
    public String createFlaws(FlawsSaveReqVO createReqVO) {
        createReqVO.setId(BeanUtils.createId());
        // 插入
        FlawsDO flaws = BeanUtils.toBean(createReqVO, FlawsDO.class);
        flaws.setStatus(1);
        flawsMapper.insert(flaws);
        // 返回
        return flaws.getId();
    }

    @Override
    public void updateFlaws(FlawsSaveReqVO updateReqVO) {
        // 校验存在
        validateFlawsExists(updateReqVO.getId());
        // 更新
        FlawsDO updateObj = BeanUtils.toBean(updateReqVO, FlawsDO.class);
        flawsMapper.updateById(updateObj);
    }

    @Override
    public void deleteFlaws(String id) {
        // 校验存在
        validateFlawsExists(id);
        // 删除
        flawsMapper.deleteById(id);
    }

    private void validateFlawsExists(String id) {
        if (flawsMapper.selectById(id) == null) {
            throw exception(FLAWS_NOT_EXISTS);
        }
    }

    @Override
    public FlawsDO getFlaws(String id) {
        return flawsMapper.selectById(id);
    }

    @Override
    public PageResult<FlawsRespVO> getFlawsPage(FlawsPageReqVO pageReqVO) {
        PageResult<FlawsDO> DOpageResult = flawsMapper.selectPage(pageReqVO);
        PageResult<FlawsRespVO> RespPageResult = BeanUtils.toBean(DOpageResult, FlawsRespVO.class);
        RespPageResult.getList().forEach(resp -> {
            if(resp.getStatus()==1){
                resp.setStatusName("待排维修计划");
            }else if(resp.getStatus()==2){
                resp.setStatusName("已排计划待维修");
            }else if(resp.getStatus()==3){
                resp.setStatusName("已维修");
            }
            if(resp.getIsStop()==1){
                resp.setIsStopName("开机");
            }else if(resp.getIsStop()==2){
                resp.setIsStopName("停机");
            }
            if(resp.getEquipAttribute()==1){
                resp.setEquipAttributeName("设备组");
            } else if(resp.getEquipAttribute()==2){
                resp.setEquipAttributeName("单个设备");
            } else if(resp.getEquipAttribute()==3){
                resp.setEquipAttributeName("设备组件");
            }
        });
        return RespPageResult;
    }
}