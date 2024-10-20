package cn.newness.imip.module.oam.service.inspectionsubstance;

import cn.newness.imip.framework.common.exception.ErrorCode;
import cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo.InspectionProfileSaveReqVO;
import cn.newness.imip.module.oam.dal.mysql.inspectplan.InspectplanMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.inspectionsubstance.InspectionSubstanceDO;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;

import cn.newness.imip.module.oam.dal.mysql.inspectionsubstance.InspectionSubstanceMapper;
import cn.newness.imip.module.oam.service.InspectionSubstanceService;

import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.oam.enums.ErrorCodeConstants.*;

/**
 * 点检内容 Service 实现类
 *
 * @author super超级管理员王中王
 */
@Service
@Validated
public class InspectionSubstanceServiceImpl implements InspectionSubstanceService {

    @Resource
    private InspectionSubstanceMapper inspectionSubstanceMapper;
    @Resource
    private InspectplanMapper inspectplanMapper;

    @Override
    public String createInspectionSubstance(InspectionSubstanceSaveReqVO createReqVO) {
        createReqVO.setId(BeanUtils.createId());
        // 插入
        InspectionSubstanceDO inspectionSubstance = BeanUtils.toBean(createReqVO, InspectionSubstanceDO.class);
        inspectionSubstanceMapper.insert(inspectionSubstance);
        // 返回
        return inspectionSubstance.getId();
    }

    @Override
    public void updateInspectionSubstance(InspectionSubstanceSaveReqVO updateReqVO) {
        // 校验存在
        validateInspectionSubstanceExists(updateReqVO.getId());
        // 更新
        InspectionSubstanceDO updateObj = BeanUtils.toBean(updateReqVO, InspectionSubstanceDO.class);
        inspectionSubstanceMapper.updateById(updateObj);
    }

    @Override
    public void deleteInspectionSubstance(String id) {
        // 校验存在
        validateInspectionSubstanceExists(id);
        //校验是否又对应的计划
        List<String> planNameList = inspectplanMapper.getPlanNameBySubstanceId(id);
        if(planNameList.size()>0){
//            throw exception(INSPECTION_PLAN_EXISTS);
            String planNames = "《";
            for (int i=0;i<planNameList.size();i++) {
                planNames+=planNameList.get(i)+"》";
                if(i!=planNameList.size()-1){
                    planNames+=",《";
                }
            }
            throw exception(new ErrorCode(INSPECTION_PLAN_EXISTS.getCode(),"删除失败，仍然存在以下点检计划关联此内容："+planNames));
        }
        // 删除
        inspectionSubstanceMapper.deleteById(id);
    }

    private InspectionSubstanceDO validateInspectionSubstanceExists(String id) {
        InspectionSubstanceDO inspectionSubstanceDO = inspectionSubstanceMapper.selectById(id);
        if (inspectionSubstanceDO == null) {
            throw exception(INSPECTION_SUBSTANCE_NOT_EXISTS);
        }
        return inspectionSubstanceDO;
    }

    @Override
    public InspectionSubstanceDO getInspectionSubstance(String id) {
        return inspectionSubstanceMapper.selectById(id);
    }

    @Override
    public PageResult<InspectionSubstanceDO> getInspectionSubstancePage(InspectionSubstancePageReqVO pageReqVO) {
        return inspectionSubstanceMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<InspectionSubstanceRespVO> getInspectionSubstancePageByPlanId(InspectionSubstancePageReqVO pageReqVO) {
        List<InspectionSubstanceRespVO> inspectionSubstanceRespVOList = inspectionSubstanceMapper.selectPageByPlanId(pageReqVO);
        return new PageResult<>(inspectionSubstanceRespVOList, (long) inspectionSubstanceMapper.selectPageByPlanIdForExcel(pageReqVO).size());
    }

    @Override
    public List<InspectionSubstanceRespVO> getInspectionSubstanceByPlanIdForExcel(InspectionSubstancePageReqVO pageReqVO) {
        return inspectionSubstanceMapper.selectPageByPlanIdForExcel(pageReqVO);
    }

    @Override
    public PageResult<InspectionSubstanceRespVO> getInspectionSubstanceAddPageByPlanId(InspectionSubstancePageReqVO pageReqVO) {
        List<InspectionSubstanceRespVO> inspectionSubstanceRespVOList = inspectionSubstanceMapper.selectAddPageByPlanId(pageReqVO);
        Long count = inspectionSubstanceMapper.selectAddPageCountByPlanId(pageReqVO);
        return new PageResult<>(inspectionSubstanceRespVOList, count);
    }

}