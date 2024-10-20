package cn.newness.imip.module.property.service.manufacturer;

import cn.newness.imip.module.property.dal.dataobject.equipmentprofile.EquipmentprofileDO;
import cn.newness.imip.module.property.dal.mysql.equipmentprofile.EquipmentprofileMapper;
import cn.newness.imip.module.property.service.ManufacturerService;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.newness.imip.module.property.controller.admin.manufacturer.vo.*;
import cn.newness.imip.module.property.dal.dataobject.manufacturer.ManufacturerDO;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;

import cn.newness.imip.module.property.dal.mysql.manufacturer.ManufacturerMapper;

import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.property.enums.ErrorCodeConstants.*;
import static cn.newness.imip.module.property.enums.PropertyLogRecordConstants.*;

/**
 * 设备生产厂家信息 Service 实现类
 *
 * @author mcr
 */
@Service
@Validated
public class ManufacturerServiceImpl implements ManufacturerService {

    @Resource
    private ManufacturerMapper manufacturerMapper;
    @Resource
    private EquipmentprofileMapper equipmentprofileMapper;
    @Resource
    private RedissonClient redissonClient;//对于联动修改的加锁

    @Override
    @LogRecord(type = MANUFACTURER,subType = COMMON_ADD,
            bizNo = COMMON_BIZNO,success = MANUFACTURER_ADD_TYPE,
            fail = MANUFACTURER_ADD_TYPE_FAIL)
    public String createManufacturer(ManufacturerSaveReqVO createReqVO) {
        //生成id
        createReqVO.setId(BeanUtils.createId());
        //校验厂家编码是否重复
        validateManufacturerCodeUnique(createReqVO.getCode());
        // 插入
        ManufacturerDO manufacturer = BeanUtils.toBean(createReqVO, ManufacturerDO.class);
        manufacturerMapper.insert(manufacturer);
        // 返回
        return manufacturer.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = MANUFACTURER,subType = COMMON_UPDATE,
            bizNo = COMMON_BIZNO,success = MANUFACTURER_UPDATE_TYPE,
            fail = MANUFACTURER_UPDATE_TYPE_FAIL)
    public void updateManufacturer(ManufacturerSaveReqVO updateReqVO) {
        // 校验存在
        ManufacturerDO oldManufacturer = validateManufacturerExists(updateReqVO.getId());
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT,BeanUtils.toBean(oldManufacturer,ManufacturerSaveReqVO.class));
        LogRecordContext.putVariable("oldManufacturer",oldManufacturer);
        //校验厂家编码是否重复
        validateManufacturerCodeUnique(updateReqVO.getCode());
        //判断是否厂家状态改为禁用并且该厂家下还有设备，是的话禁止更改
        if(updateReqVO.getStatus()==1&&equipmentprofileMapper.selectCount("manufacturer_id",updateReqVO.getId())>0){
            throw exception(EXISTS_EQUIPMENTPROFILE);
        }
        // 更新
        RLock lock = redissonClient.getLock("manufacturer" + updateReqVO.getId());
        lock.lock();
        try {
            ManufacturerDO updateObj = BeanUtils.toBean(updateReqVO, ManufacturerDO.class);
            manufacturerMapper.updateById(updateObj);
            LambdaUpdateChainWrapper<EquipmentprofileDO> equipmentprofileDOLambdaUpdateChainWrapper=new LambdaUpdateChainWrapper<>(equipmentprofileMapper);
            equipmentprofileDOLambdaUpdateChainWrapper
                    .eq(EquipmentprofileDO::getManufacturerId,updateObj.getId())
                    .set(EquipmentprofileDO::getManufacturerName,updateObj.getName())
                    .update();
        }finally {
            lock.unlock();
        }
    }

    @Override
    @LogRecord(type = MANUFACTURER,subType = COMMON_DEL,
            bizNo = COMMON_BIZNO,success = MANUFACTURER_DEL_TYPE,
            fail = MANUFACTURER_DEL_TYPE_FAIL)
    public void deleteManufacturer(String id) {
        // 校验存在
        ManufacturerDO oldManufacturer = validateManufacturerExists(id);
        LogRecordContext.putVariable("oldManufacturer",oldManufacturer);
        // 删除
        if(equipmentprofileMapper.selectCount("manufacturer_id",id)>0){
            throw exception(EXISTS_EQUIPMENTPROFILE);
        }
        manufacturerMapper.deleteById(id);
    }


    @Override
    public ManufacturerDO getManufacturer(String id) {
        return manufacturerMapper.selectById(id);
    }

    @Override
    public PageResult<ManufacturerDO> getManufacturerPage(ManufacturerPageReqVO pageReqVO) {
        return manufacturerMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ManufacturerRespVO> getManufacturerPageForExport(ManufacturerPageReqVO pageReqVO) {
        List<ManufacturerDO> manufacturerDOS = manufacturerMapper.selectPage(pageReqVO).getList();
        List<ManufacturerRespVO> manufacturerRespVOS = BeanUtils.toBean(manufacturerDOS, ManufacturerRespVO.class);
        manufacturerRespVOS.forEach(item->{
            if(item.getStatus()==0){
                item.setStatusName("正常");
            }else if(item.getStatus()==1){
                item.setStatusName("禁用");
            }
        });
        return manufacturerRespVOS;
    }

    @Override
    public List<ManufacturerDO> getAllNormalManufacturer() {
        return manufacturerMapper.selectList("status", 0);
    }

    //    以下是工具方法
    private ManufacturerDO validateManufacturerExists(String id) {
        ManufacturerDO manufacturerDO = manufacturerMapper.selectById(id);
        if ( manufacturerDO == null) {
            throw exception(MANUFACTURER_NOT_EXISTS);
        }
        return manufacturerDO;
    }
    private void validateManufacturerCodeUnique(String code) {
        if(manufacturerMapper.selectCount("code",code)>0){
            throw exception(EXISTS__CODE_DUPLICATE);
        }
    }
}