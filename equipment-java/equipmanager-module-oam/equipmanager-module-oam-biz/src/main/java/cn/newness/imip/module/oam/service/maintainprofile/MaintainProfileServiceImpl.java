package cn.newness.imip.module.oam.service.maintainprofile;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.newness.imip.module.oam.controller.admin.maintainprofile.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.maintainprofile.MaintainProfileDO;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.pojo.PageParam;
import cn.newness.imip.framework.common.util.object.BeanUtils;

import cn.newness.imip.module.oam.dal.mysql.maintainprofile.MaintainProfileMapper;
import cn.newness.imip.module.oam.service.MaintainProfileService;

import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.oam.enums.ErrorCodeConstants.*;

/**
 * 保养日志表 Service 实现类
 *
 * @author mcr
 */
@Service
@Validated
public class MaintainProfileServiceImpl implements MaintainProfileService {

    @Resource
    private MaintainProfileMapper maintainProfileMapper;

    @Override
    public String createMaintainProfile(MaintainProfileSaveReqVO createReqVO) {
        createReqVO.setId(BeanUtils.createId());
        // 插入
        MaintainProfileDO maintainProfile = BeanUtils.toBean(createReqVO, MaintainProfileDO.class);
        maintainProfileMapper.insert(maintainProfile);
        // 返回
        return maintainProfile.getId();
    }

    @Override
    public void updateMaintainProfile(MaintainProfileSaveReqVO updateReqVO) {
        // 校验存在
        validateMaintainProfileExists(updateReqVO.getId());
        // 更新
        MaintainProfileDO updateObj = BeanUtils.toBean(updateReqVO, MaintainProfileDO.class);
        maintainProfileMapper.updateById(updateObj);
    }

    @Override
    public void deleteMaintainProfile(String id) {
        // 校验存在
        validateMaintainProfileExists(id);
        // 删除
        maintainProfileMapper.deleteById(id);
    }

    private MaintainProfileDO validateMaintainProfileExists(String id) {
        MaintainProfileDO maintainProfileDO = maintainProfileMapper.selectById(id);
        if (maintainProfileDO == null) {
            throw exception(MAINTAIN_PROFILE_NOT_EXISTS);
        }
        return maintainProfileDO;
    }

    @Override
    public MaintainProfileDO getMaintainProfile(String id) {
        return maintainProfileMapper.selectById(id);
    }

    @Override
    public PageResult<MaintainProfileDO> getMaintainProfilePage(MaintainProfilePageReqVO pageReqVO) {
        return maintainProfileMapper.selectPage(pageReqVO);
    }

}