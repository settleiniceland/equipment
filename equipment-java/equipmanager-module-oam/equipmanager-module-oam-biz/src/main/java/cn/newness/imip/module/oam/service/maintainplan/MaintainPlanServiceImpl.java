package cn.newness.imip.module.oam.service.maintainplan;

import cn.newness.imip.module.oam.service.MaintainDetailService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import cn.newness.imip.module.oam.controller.admin.maintainplan.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.maintainplan.MaintainPlanDO;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;
import cn.newness.imip.module.oam.dal.mysql.maintainplan.MaintainPlanMapper;
import cn.newness.imip.module.oam.service.MaintainPlanService;
import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.oam.enums.ErrorCodeConstants.*;

/**
 * 保养计划 Service 实现类
 *
 * @author mcr
 */
@Service
@Validated
public class MaintainPlanServiceImpl implements MaintainPlanService {

    @Resource
    private MaintainPlanMapper maintainPlanMapper;
    @Resource
    private MaintainDetailService maintainDetailService;

    @Override
    public String createMaintainPlan(MaintainPlanSaveReqVO createReqVO) {
        createReqVO.setId(BeanUtils.createId());
        // 插入
        MaintainPlanDO maintainPlan = BeanUtils.toBean(createReqVO, MaintainPlanDO.class);
        maintainPlanMapper.insert(maintainPlan);
        // 返回
        return maintainPlan.getId();
    }

    @Override
    public void updateMaintainPlan(MaintainPlanSaveReqVO updateReqVO) {
        // 校验存在
        validateMaintainPlanExists(updateReqVO.getId());
        // 更新
        MaintainPlanDO updateObj = BeanUtils.toBean(updateReqVO, MaintainPlanDO.class);
        maintainPlanMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMaintainPlan(String id) {
        // 校验存在
        validateMaintainPlanExists(id);
        // 删除【顺带删除下面所有的内容】
        maintainDetailService.deleteMaintainDetailByPlanId(id);
        maintainPlanMapper.deleteById(id);
    }

    private MaintainPlanDO validateMaintainPlanExists(String id) {
        MaintainPlanDO maintainPlanDO = maintainPlanMapper.selectById(id);
        if (maintainPlanDO == null) {
            throw exception(MAINTAIN_PLAN_NOT_EXISTS);
        }
        return maintainPlanDO;
    }

    @Override
    public MaintainPlanRespVO getMaintainPlan(String id) {
        MaintainPlanRespVO maintainPlan = BeanUtils.toBean(maintainPlanMapper.selectById(id), MaintainPlanRespVO.class);
        List<MaintainPlanRespVO> plist=new ArrayList<>();
        plist.add(maintainPlan);
        return addName(plist).get(0);
    }

    @Override
    public PageResult<MaintainPlanRespVO> getMaintainPlanPage(MaintainPlanPageReqVO pageReqVO) {
        PageResult<MaintainPlanRespVO> respPage = BeanUtils.toBean(maintainPlanMapper.selectPage(pageReqVO), MaintainPlanRespVO.class);
        respPage.setList(addName(respPage.getList()));
        return respPage;
    }
    private List<MaintainPlanRespVO> addName(List<MaintainPlanRespVO> maintainPlanRespVOList){
        maintainPlanRespVOList.forEach(maintainPlanRespVO -> {
            if (maintainPlanRespVO.getStatus() == 0) {
                maintainPlanRespVO.setStatusName("正常");
            }else if(maintainPlanRespVO.getStatus() == 1) {
                maintainPlanRespVO.setStatusName("禁用");
            }
        });
        return maintainPlanRespVOList;
    }
}