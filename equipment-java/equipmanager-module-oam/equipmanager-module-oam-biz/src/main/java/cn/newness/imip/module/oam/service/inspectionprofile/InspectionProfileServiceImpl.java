package cn.newness.imip.module.oam.service.inspectionprofile;

import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.module.oam.controller.admin.statusrecord.vo.StatusRecordSaveReqVO;
import cn.newness.imip.module.oam.dal.dataobject.flaws.FlawsDO;
import cn.newness.imip.module.oam.dal.dataobject.statusrecord.StatusRecordDO;
import cn.newness.imip.module.oam.dal.mysql.flaws.FlawsMapper;
import cn.newness.imip.module.oam.dal.mysql.statusrecord.StatusRecordMapper;
import cn.newness.imip.module.property.api.EquipmentProfileApi;
import cn.newness.imip.module.property.api.dto.EquipmentProfileDto;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.inspectionprofile.InspectionProfileDO;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;
import cn.newness.imip.module.oam.dal.mysql.inspectionprofile.InspectionProfileMapper;
import cn.newness.imip.module.oam.service.InspectionProfileService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.oam.enums.ErrorCodeConstants.*;

/**
 * 点检日志表 Service 实现类
 *
 * @author super超级管理员王中王
 */
@Service
@Validated
public class InspectionProfileServiceImpl implements InspectionProfileService {

    @Resource
    private InspectionProfileMapper inspectionProfileMapper;
    @Resource
    private FlawsMapper flawsMapper;
    @Resource
    private EquipmentProfileApi equipmentProfileApi;
    @Resource
    private StatusRecordMapper statusRecordMapper;

    @Override
    public String createInspectionProfile(InspectionProfileSaveReqVO createReqVO) {
        // 插入
        InspectionProfileDO inspectionProfile = BeanUtils.toBean(createReqVO, InspectionProfileDO.class);
        inspectionProfileMapper.insert(inspectionProfile);
        // 返回
        return inspectionProfile.getId();
    }

    @Override
    public void updateInspectionProfile(InspectionProfileSaveReqVO updateReqVO) {
        // 校验存在
        validateInspectionProfileExists(updateReqVO.getId());
        // 更新
        InspectionProfileDO updateObj = BeanUtils.toBean(updateReqVO, InspectionProfileDO.class);
        inspectionProfileMapper.updateById(updateObj);
    }

    @Override
    public void deleteInspectionProfile(String id) {
        // 校验存在
        validateInspectionProfileExists(id);
        // 删除
        inspectionProfileMapper.deleteById(id);
    }

    private InspectionProfileDO validateInspectionProfileExists(String id) {
        InspectionProfileDO inspectionProfileDO = inspectionProfileMapper.selectById(id);
        if (inspectionProfileDO == null) {
            throw exception(INSPECTION_PROFILE_NOT_EXISTS);
        }
        return inspectionProfileDO;
    }

    @Override
    public InspectionProfileDO getInspectionProfile(String id) {
        return inspectionProfileMapper.selectById(id);
    }

    @Override
    public PageResult<InspectionProfileRespVO> getInspectionProfilePage(InspectionProfilePageReqVO pageReqVO) {
        PageResult<InspectionProfileDO> inspectionProfileDOPageResult = inspectionProfileMapper.selectPage(pageReqVO);
        PageResult<InspectionProfileRespVO> inspectionProfileRespVOPageResult = BeanUtils.toBean(inspectionProfileDOPageResult, InspectionProfileRespVO.class);
        inspectionProfileRespVOPageResult.getList().forEach(item -> {
            if(item.getResult()==1){
                item.setResultName("正常");
            } else if(item.getResult()==2) {
                item.setResultName("异常");
            }
            if(item.getInspectionType()==1){
                item.setInspectionTypeName("普通点检");
            } else if(item.getInspectionType()==2) {
                item.setInspectionTypeName("重点点检");
            } else if(item.getInspectionType()==3) {
                item.setInspectionTypeName("专项点检");
            } else if(item.getInspectionType()==4) {
                item.setInspectionTypeName("拆检");
            }
            if(item.getIsStop()==1){
                item.setIsStopName("开机");
            } else if(item.getIsStop()==2) {
                item.setIsStopName("停机");
            }
            if(item.getEquipAttribute()==1){
                item.setEquipAttributeName("设备组");
            } else if(item.getEquipAttribute()==2){
                item.setEquipAttributeName("单个设备");
            } else if(item.getEquipAttribute()==3){
                item.setEquipAttributeName("设备组件");
            }
        });
        return inspectionProfileRespVOPageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProfileList(List<InspectionProfileSaveReqVO> profileVOList) {
        List<String> equipProfileList = new ArrayList<>();//盛放要停机的设备档案id的集合
        List<FlawsDO> flawsDOList = new ArrayList<>();//盛放要添加的缺陷对象集合
        List<StatusRecordSaveReqVO> statusRecordSaveReqVOList=new ArrayList<>();//盛放停机记录
        profileVOList.forEach(profile -> {
            profile.setId(BeanUtils.createId());
            if(profile.getResult()==1){//如果点检结果正常的话不可能需要停机，这里直接设置为开机
                profile.setIsStop(1);
            }else if(profile.getResult()==2){//如果结果异常的话就直接添加到缺陷库
                //缺陷库
                FlawsDO flawsDO = new FlawsDO();
                flawsDO.setId(BeanUtils.createId());
                flawsDO.setEquipProfileId(profile.getEquipProfileId());
                flawsDO.setEquipProfileName(profile.getEquipProfileName());
                flawsDO.setEquipCode(profile.getEquipCode());
                flawsDO.setEquipAttribute(profile.getEquipAttribute());
                flawsDO.setStatus(1);
                flawsDO.setIsStop(profile.getIsStop());
                flawsDO.setDetails(profile.getResultDetail());
                flawsDO.setBeginTime(profile.getInspectionDate());
                flawsDOList.add(flawsDO);
            }
            if(profile.getResult()==2 && profile.getIsStop()==2){
                /* 原先的设计是点检对象可能是设备组，设备或设备组件，现改为只有设备，这个代码功能依旧正常，就不做修改了 */
                if(profile.getEquipAttribute()==1){//设备组将下面所有单体设备都停机了
                    List<EquipmentProfileDto> equipmentProfileDtos = equipmentProfileApi.getAllAttribute2EquipmentProfileDtoSons(profile.getEquipProfileId());
                    equipProfileList.addAll(equipmentProfileDtos.stream().map(EquipmentProfileDto::getId).collect(Collectors.toList()));
                    equipmentProfileDtos.forEach(equipmentProfileDto -> {
                        StatusRecordSaveReqVO statusRecordSave = new StatusRecordSaveReqVO();
                        statusRecordSave.setId(BeanUtils.createId());
                        statusRecordSave.setEquipmentprofileId(equipmentProfileDto.getId());
                        statusRecordSave.setEquipName(equipmentProfileDto.getEquipName());
                        statusRecordSave.setEquipCode(equipmentProfileDto.getCode());
                        statusRecordSave.setOperationType(1);
                        statusRecordSave.setOperationTime(LocalDateTime.now());
                        statusRecordSave.setNewStatus1(2);
                        statusRecordSave.setNewStatus2(equipmentProfileDto.getStatus2());
                        statusRecordSave.setChangeDetails("1_0");
                        statusRecordSave.setDetails(
                                     profile.getEquipProfileName()+profile.getEquipCode()+"-->"+
                                     profile.getInspectionPlanName()+"-->"+
                                     profile.getInspectionDetail()+"-->"+
                                     profile.getInspectionDate()+"-->"+
                                     (profile.getResult() == 1 ? "正常" : "异常") +"-->"+
                                     profile.getResultDetail());
                        statusRecordSaveReqVOList.add(statusRecordSave);
                    });
                }else if(profile.getEquipAttribute()==2){
                    equipProfileList.add(profile.getEquipProfileId());
                    StatusRecordSaveReqVO statusRecordSave = new StatusRecordSaveReqVO();
                    statusRecordSave.setId(BeanUtils.createId());
                    statusRecordSave.setEquipmentprofileId(profile.getEquipProfileId());
                    statusRecordSave.setEquipName(profile.getEquipProfileName());
                    statusRecordSave.setEquipCode(profile.getEquipCode());
                    statusRecordSave.setOperationType(1);
                    statusRecordSave.setOperationTime(LocalDateTime.now());
                    statusRecordSave.setNewStatus1(2);
                    statusRecordSave.setNewStatus2(profile.getStatus2());
                    statusRecordSave.setChangeDetails("1_0");
                    statusRecordSave.setDetails(
                            profile.getEquipProfileName()+profile.getEquipCode()+"-->"+
                                    profile.getInspectionPlanName()+"-->"+
                                    profile.getInspectionDetail()+"-->"+
                                    profile.getInspectionDate()+"-->"+
                                    (profile.getResult() == 1 ? "正常" : "异常") +"-->"+
                                    profile.getResultDetail());
                    statusRecordSaveReqVOList.add(statusRecordSave);
                }else if(profile.getEquipAttribute()==3){//把其上面的单体设备停机了
                    EquipmentProfileDto equipmentProfileDto = equipmentProfileApi.getSupAttribute2EquipmentProfileDto(profile.getEquipProfileId());
                    if(equipmentProfileDto != null){
                        equipProfileList.add(equipmentProfileDto.getId());
                        StatusRecordSaveReqVO statusRecordSave = new StatusRecordSaveReqVO();
                        statusRecordSave.setId(BeanUtils.createId());
                        statusRecordSave.setEquipmentprofileId(equipmentProfileDto.getId());
                        statusRecordSave.setEquipName(equipmentProfileDto.getEquipName());
                        statusRecordSave.setEquipCode(equipmentProfileDto.getCode());
                        statusRecordSave.setOperationType(1);
                        statusRecordSave.setOperationTime(LocalDateTime.now());
                        statusRecordSave.setNewStatus1(2);
                        statusRecordSave.setNewStatus2(equipmentProfileDto.getStatus2());
                        statusRecordSave.setChangeDetails("1_0");
                        statusRecordSave.setDetails(
                                    profile.getEquipProfileName()+profile.getEquipCode()+"-->"+
                                    profile.getInspectionPlanName()+"-->"+
                                    profile.getInspectionDetail()+"-->"+
                                    profile.getInspectionDate()+"-->"+
                                    (profile.getResult() == 1 ? "正常" : "异常") +"-->"+
                                    profile.getResultDetail());
                        statusRecordSaveReqVOList.add(statusRecordSave);
                    }
                }
            }
        });
        if(equipProfileList.size()>0){
            equipmentProfileApi.batchUpdateEquipmentProfile(equipProfileList);
            statusRecordMapper.insertBatch(BeanUtils.toBean(statusRecordSaveReqVOList, StatusRecordDO.class));
        }
        if(flawsDOList.size()>0){
            flawsMapper.insertBatch(flawsDOList);
        }
        inspectionProfileMapper.insertBatch(BeanUtils.toBean(profileVOList, InspectionProfileDO.class));
    }

    @Override
    public InspectionProfileRespVO getPlanNewestExecuteTime(String planId) {
        LambdaQueryWrapperX<InspectionProfileDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(InspectionProfileDO::getInspectionPlanId, planId)
                .orderByDesc(InspectionProfileDO::getPlanExecuteCount)
                .orderByAsc(InspectionProfileDO::getInspectionDate);
        List<InspectionProfileDO> inspectionProfileDOS = inspectionProfileMapper.selectList(queryWrapper);
        if(inspectionProfileDOS.size()==0){
            return null;
        }
        return BeanUtils.toBean(inspectionProfileDOS.get(0), InspectionProfileRespVO.class);
    }
}