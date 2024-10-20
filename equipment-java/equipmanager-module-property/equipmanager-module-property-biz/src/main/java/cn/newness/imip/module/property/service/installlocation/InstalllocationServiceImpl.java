package cn.newness.imip.module.property.service.installlocation;

import cn.newness.imip.module.property.dal.dataobject.equipmentprofile.EquipmentprofileDO;
import cn.newness.imip.module.property.dal.mysql.equipmentprofile.EquipmentprofileMapper;
import cn.newness.imip.module.property.service.InstalllocationService;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.newness.imip.module.property.controller.admin.installlocation.vo.*;
import cn.newness.imip.module.property.dal.dataobject.installlocation.InstalllocationDO;
import cn.newness.imip.framework.common.util.object.BeanUtils;

import cn.newness.imip.module.property.dal.mysql.installlocation.InstalllocationMapper;

import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.property.enums.ErrorCodeConstants.*;
import static cn.newness.imip.module.property.enums.PropertyLogRecordConstants.*;

/**
 * 设备安装位置 Service 实现类
 *
 * @author mcr
 */
@Service
@Validated
public class InstalllocationServiceImpl implements InstalllocationService {

    @Resource
    private InstalllocationMapper installlocationMapper;
    @Resource
    private EquipmentprofileMapper equipmentprofileMapper;
    @Resource
    private RedissonClient redissonClient;//对于联动修改的加锁

    @Override
    @LogRecord(type = INSTALLLOCATION,subType = COMMON_ADD,
        bizNo = COMMON_BIZNO,success = INSTALLLOCATION_ADD_TYPE,
        fail = INSTALLLOCATION_ADD_TYPE_FAIL)
    public String createInstalllocation(InstalllocationSaveReqVO createReqVO) {
        //创建id
        createReqVO.setId(BeanUtils.createId());
        // 校验父地区id的有效性
        validateParentInstalllocation(null, createReqVO.getSupId());
        // 校验地区名称和编码的唯一性
        validateInstalllocationNameUnique(null, createReqVO.getSupId(), createReqVO.getName(), createReqVO.getCode());
        // 插入
        InstalllocationDO installlocation = BeanUtils.toBean(createReqVO, InstalllocationDO.class);
        installlocationMapper.insert(installlocation);
        // 返回
        return installlocation.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = INSTALLLOCATION,subType = COMMON_UPDATE,
        bizNo = COMMON_BIZNO,success = INSTALLLOCATION_UPDATE_TYPE,
        fail = INSTALLLOCATION_UPDATE_TYPE_FAIL)
    public void updateInstalllocation(InstalllocationSaveReqVO updateReqVO) {
        // 校验存在
        InstalllocationDO oldInstalllocation = validateInstalllocationExists(updateReqVO.getId());
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT,BeanUtils.toBean(oldInstalllocation, InstalllocationSaveReqVO.class));
        LogRecordContext.putVariable("oldInstalllocation",oldInstalllocation);
        // 校验父地区id的有效性
        validateParentInstalllocation(updateReqVO.getId(), updateReqVO.getSupId());
        // 校验地区名称和编码的唯一性
        validateInstalllocationNameUnique(updateReqVO.getId(), updateReqVO.getSupId(), updateReqVO.getName(), updateReqVO.getCode());

        // 更新
        RLock lock = redissonClient.getLock("installLocation" + updateReqVO.getId());
        lock.lock();
        try {
            InstalllocationDO updateObj = BeanUtils.toBean(updateReqVO, InstalllocationDO.class);
            installlocationMapper.updateById(updateObj);//先改地址名
            LambdaUpdateChainWrapper<EquipmentprofileDO> equipmentprofileDOLambdaUpdateChainWrapper=new LambdaUpdateChainWrapper<>(equipmentprofileMapper);
            equipmentprofileDOLambdaUpdateChainWrapper
                    .eq(EquipmentprofileDO::getLocationId, updateReqVO.getId())
                    .set(EquipmentprofileDO::getLocationName,getCompleteName(updateReqVO.getId()))
                    .update();//再该设备档案信息【因为涉及到全层级名，需要查数据库，所以必须先改库地址数据才行】
        }finally {
            lock.unlock();
        }
    }

    @Override
    @LogRecord(type = INSTALLLOCATION,subType = COMMON_DEL,
        bizNo = COMMON_BIZNO,success = INSTALLLOCATION_DEL_TYPE,
        fail = INSTALLLOCATION_DEL_TYPE_FAIL)
    public void deleteInstalllocation(String id) {
        // 校验存在
        InstalllocationDO oldInstalllocation = validateInstalllocationExists(id);
        LogRecordContext.putVariable("oldInstalllocation",oldInstalllocation);
        // 校验是否有子设备安装位置
        if (installlocationMapper.selectCountBySupId(id) > 0) {
            throw exception(INSTALLLOCATION_EXITS_CHILDREN);
        }
        if(equipmentprofileMapper.selectCount("location_id",id)>0) {
            throw exception(INSTALLLOCATION_EXITS_EQUIPMENTPROFILE);
        }
        // 删除
        installlocationMapper.deleteById(id);
    }



    @Override
    public InstalllocationDO getInstalllocation(String id) {
        return installlocationMapper.selectById(id);
    }

    @Override
    public List<InstalllocationDO> getInstalllocationList(InstalllocationListReqVO listReqVO) {
        return installlocationMapper.selectList(listReqVO);
    }

    @Override
    public List<InstalllocationRespVO> getInstalllocationListForExcel(InstalllocationListReqVO listReqVO) {
        List<InstalllocationDO> installlocationDOS = installlocationMapper.selectList(listReqVO);
        List<InstalllocationRespVO> installlocationRespVOS = BeanUtils.toBean(installlocationDOS, InstalllocationRespVO.class);
        Map<String,String> toolMap=new HashMap<>();
        for (InstalllocationRespVO installlocationRespVO : installlocationRespVOS) {
            toolMap.put(installlocationRespVO.getId(), installlocationRespVO.getName());
        }
        installlocationRespVOS.forEach(item->{
            if("0".equals(item.getSupId())){
                item.setSupName("莫罗瓦利园区Morowali");
            }else {
                item.setSupName(toolMap.get(item.getSupId()));
            }
        });
        return installlocationRespVOS;
    }

    @Override
    public String getCompleteName(String id) {
        return getLocationNameById(id,"莫罗瓦利园区");
    }

    //    以下是工具方法
    private InstalllocationDO validateInstalllocationExists(String id) {
        InstalllocationDO installlocationDO = installlocationMapper.selectById(id);
        if (installlocationDO == null) {
            throw exception(INSTALLLOCATION_NOT_EXISTS);
        }
        return installlocationDO;
    }

    private void validateParentInstalllocation(String id, String supId) {
        if (supId == null || InstalllocationDO.CUSTOM_SUP_ID_ROOT.equals(supId)) {
            return;
        }
        // 1. 不能设置自己为父设备安装位置
        if (Objects.equals(id, supId)) {
            throw exception(INSTALLLOCATION_PARENT_ERROR);
        }
        // 2. 父设备安装位置不存在
        InstalllocationDO parentInstalllocation = installlocationMapper.selectById(supId);
        if (parentInstalllocation == null) {
            throw exception(INSTALLLOCATION_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父设备安装位置，如果父设备安装位置是自己的子设备安装位置，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            supId = parentInstalllocation.getSupId();
            if (Objects.equals(id, supId)) {
                throw exception(INSTALLLOCATION_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父设备安装位置
            if (supId == null || InstalllocationDO.CUSTOM_SUP_ID_ROOT.equals(supId)) {
                break;
            }
            parentInstalllocation = installlocationMapper.selectById(supId);
            if (parentInstalllocation == null) {
                break;
            }
        }
    }

    private void validateInstalllocationNameUnique(String id, String supId, String name, String code) {
        //先校验code唯一性
        List<InstalllocationDO> installlocationDOList = installlocationMapper.selectList("code", code);
        installlocationDOList.forEach(installlocationDO -> {
            if(!Objects.equals(id, installlocationDO.getId())||id == null) {
                throw exception(INSTALLLOCATION_CODE_DUPLICATE);
            }
        });
        //再校验name唯一性
        InstalllocationDO installlocation = installlocationMapper.selectBySupIdAndName(supId, name);
        if (installlocation == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的设备安装位置
        if (id == null) {
            throw exception(INSTALLLOCATION_NAME_DUPLICATE);
        }
        if (!Objects.equals(installlocation.getId(), id)) {
            throw exception(INSTALLLOCATION_NAME_DUPLICATE);
        }
    }
    /**
     * 查找设备位置全层级
     *
     * @param [id, originName]
     * @author machuran
     * @date 8/15/2024
     * @Return java.lang.String
     */
    private String getLocationNameById(String id,String originName) {
        InstalllocationDO installlocationDO = installlocationMapper.selectById(id);
        if("0".equals(installlocationDO.getSupId())) {
            return originName+"->"+installlocationDO.getName();
        }
        return getLocationNameById(installlocationDO.getSupId(),originName)+"->"+installlocationDO.getName();
    }
}