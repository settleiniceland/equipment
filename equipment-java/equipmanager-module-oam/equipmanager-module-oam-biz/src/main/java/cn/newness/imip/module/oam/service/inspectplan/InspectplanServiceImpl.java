package cn.newness.imip.module.oam.service.inspectplan;

import cn.newness.imip.framework.common.pojo.PageParam;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo.InspectionProfilePageReqVO;
import cn.newness.imip.module.oam.controller.app.inspectplan.vo.InspectplanAppVO;
import cn.newness.imip.module.oam.dal.dataobject.inspectionprofile.InspectionProfileDO;
import cn.newness.imip.module.oam.dal.dataobject.inspectionsubstancemap.InspectionSubstanceMapDO;
import cn.newness.imip.module.oam.dal.mysql.inspectionprofile.InspectionProfileMapper;
import cn.newness.imip.module.oam.dal.mysql.inspectionsubstancemap.InspectionSubstanceMapMapper;
import cn.newness.imip.module.property.api.LocationApi;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.newness.imip.module.oam.controller.admin.inspectplan.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.inspectplan.InspectplanDO;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;
import cn.newness.imip.module.oam.dal.mysql.inspectplan.InspectplanMapper;
import cn.newness.imip.module.oam.service.InspectplanService;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.oam.enums.ErrorCodeConstants.*;

/**
 * 点检计划表 Service 实现类
 *
 * @author super超级管理员王中王
 */
@Service
@Validated
public class InspectplanServiceImpl implements InspectplanService {

    @Resource
    private InspectplanMapper inspectplanMapper;
    @Resource
    private LocationApi locationApi;
    @Resource
    private InspectionSubstanceMapMapper mapMapper;
    @Resource
    private InspectionProfileMapper inspectionProfileMapper;

    @Override
    public String createInspectplan(InspectplanSaveReqVO createReqVO) {
        //id赋值
        createReqVO.setId(BeanUtils.createId());
        //校验名字是否重复
        validateInspectplanNameRepetition(createReqVO.getName(), createReqVO.getId());
        //全层级区域名称赋值
        createReqVO.setEquiplocationName(locationApi.getCompleteLocationName(createReqVO.getEquiplocationId()));
        // 插入
        InspectplanDO inspectplan = BeanUtils.toBean(createReqVO, InspectplanDO.class);
        inspectplanMapper.insert(inspectplan);
        // 返回
        return inspectplan.getId();
    }

    @Override
    public void updateInspectplan(InspectplanSaveReqVO updateReqVO) {
        // 校验存在
        validateInspectplanExists(updateReqVO.getId());
        //校验名字是否重复
        validateInspectplanNameRepetition(updateReqVO.getName(), updateReqVO.getId());
        //位置名修改
        if(!inspectplanMapper.selectById(updateReqVO.getId()).getEquiplocationId().equals(updateReqVO.getEquiplocationId())){
            updateReqVO.setEquiplocationName(locationApi.getCompleteLocationName(updateReqVO.getEquiplocationId()));
        }
        // 更新
        InspectplanDO updateObj = BeanUtils.toBean(updateReqVO, InspectplanDO.class);
        inspectplanMapper.updateById(updateObj);
    }

    @Override
    public void deleteInspectplan(String id, Integer status) {
        // 校验存在
        validateInspectplanExists(id);
        if(status == 0){
            throw  exception(INSPECTION_RUNNING);
        }
        InspectionProfilePageReqVO pageReqVO=new InspectionProfilePageReqVO();
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        pageReqVO.setInspectionPlanId(id);
        int size = inspectionProfileMapper.selectPage(pageReqVO).getList().size();
        if(size > 0){
            throw exception(INSPECTION_HAVE_PROFILE);
        }
        // 删除
        inspectplanMapper.deleteById(id);
    }

    @Override
    public InspectplanDO getInspectplan(String id) {
        return inspectplanMapper.selectById(id);
    }

    @Override
    public PageResult<InspectplanDO> getInspectplanPage(InspectplanPageReqVO pageReqVO) {
        return inspectplanMapper.selectPage(pageReqVO);
    }

    private InspectplanDO validateInspectplanExists(String id) {
        InspectplanDO inspectplanDO = inspectplanMapper.selectById(id);
        if (inspectplanDO == null) {
            throw exception(INSPECTPLAN_NOT_EXISTS);
        }
        return inspectplanDO;
    }
    private void validateInspectplanNameRepetition(String inspectplanName, String id) {
        Long l = inspectplanMapper.selectCount(new LambdaQueryWrapperX<InspectplanDO>()
                .eqIfPresent(InspectplanDO::getName, inspectplanName)
                .ne(InspectplanDO::getId, id));
        if(l > 0) {
            throw exception(INSPECTPLAN_NAME_REPETITION);
        }
    }

    @Override
    public void addSubstancesToPlan(String planId, List<String> substanceIds) {
        List<InspectionSubstanceMapDO> mapDOList=new ArrayList<>();
        substanceIds.forEach( substanceId ->{
            InspectionSubstanceMapDO inspectionSubstanceMapDO=new InspectionSubstanceMapDO();
            inspectionSubstanceMapDO.setId(BeanUtils.createId());
            inspectionSubstanceMapDO.setInspectionSubstanceId(substanceId);
            inspectionSubstanceMapDO.setInspectionplanId(planId);
            mapDOList.add(inspectionSubstanceMapDO);
        });
        Boolean b = mapMapper.insertBatch(mapDOList);
        if(!b){
            throw exception(INSPECTION_INSERT_FAIL);
        }
    }

    @Override
    public void delSubstanceForPlan(InspectionSubstanceMapDO mapDO) {
        int i = mapMapper.delForPlan(mapDO);
        if(i<=0){
            throw exception(INSPECTION_DEL_FAIL);
        }
    }
    /********************************************************************>>>>>>>>>>>>>APP端<<<<<<<<<<<<<<<<<<******************************************************************/
    @Override
    public List<InspectplanAppVO> getInspectplanListForApp(InspectplanAppVO inspectplanAppVO) {
        //先sql查出修改时间倒序所有启动的点检计划，后交给getInspectplanListWithTimeUpStatus方法求出所有计划的到时间状态
        List<InspectplanAppVO> inspectplanAppVOList=getInspectplanListWithTimeUpStatus(inspectplanMapper.getInspectplanListForApp(inspectplanAppVO));
        return inspectplanAppVOList;
    }
    //获取计划是否到点检时间
    private List<InspectplanAppVO> getInspectplanListWithTimeUpStatus(List<InspectplanAppVO> inspectplanAppVOList) {
        List<String> planIdList=new ArrayList<>();
        Map<String,InspectionProfileDO> profileToolMap=new HashMap<>();
        inspectplanAppVOList.forEach( inspectplanAppVO -> {
            planIdList.add(inspectplanAppVO.getId());
        });
        List<InspectionProfileDO> newestProfileList = inspectionProfileMapper.getNewestProfileList(planIdList);
        newestProfileList.forEach( profile -> {
            profileToolMap.put(profile.getInspectionPlanId(),profile);
        });
        inspectplanAppVOList.forEach( inspectplanAppVO -> {
            if(profileToolMap.get(inspectplanAppVO.getId()) == null){
                inspectplanAppVO.setTimeUp(true);
                return;
            }
            LocalDateTime inspectionDate = profileToolMap.get(inspectplanAppVO.getId()).getInspectionDate();
            BigDecimal cycle = inspectplanAppVO.getInspectionCycle();
            Duration cycleSeconds = Duration.ofSeconds(cycle.multiply(BigDecimal.valueOf(3600)).longValue());
            if(inspectionDate.plus(cycleSeconds).isAfter(LocalDateTime.now())){
                //如果相加后的时间比当前时间晚，说明还未到点检时间
                inspectplanAppVO.setTimeUp(false);
            }else {//负责说明已经到点检时间了，该点检了
                inspectplanAppVO.setTimeUp(true);
            }
        });
        inspectplanAppVOList.sort(Comparator.comparing(InspectplanAppVO::getTimeUp).reversed());
        return inspectplanAppVOList;
    }
}