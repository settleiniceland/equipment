package cn.newness.imip.module.property.service.equiptype;

import cn.newness.imip.module.property.dal.dataobject.equipmentprofile.EquipmentprofileDO;
import cn.newness.imip.module.property.dal.mysql.equip.EquipMapper;
import cn.newness.imip.module.property.dal.mysql.equipmentprofile.EquipmentprofileMapper;
import cn.newness.imip.module.property.service.EquiptypeService;
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
import cn.newness.imip.module.property.controller.admin.equiptype.vo.*;
import cn.newness.imip.module.property.dal.dataobject.equiptype.EquiptypeDO;
import cn.newness.imip.framework.common.util.object.BeanUtils;

import cn.newness.imip.module.property.dal.mysql.equiptype.EquiptypeMapper;

import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.property.enums.ErrorCodeConstants.*;
import static cn.newness.imip.module.property.enums.PropertyLogRecordConstants.*;

/**
 * 设备类别 Service 实现类
 *
 * @author mcr
 */
@Service
@Validated
public class EquiptypeServiceImpl implements EquiptypeService {

    @Resource
    private EquiptypeMapper equiptypeMapper;
    @Resource
    private EquipMapper equipMapper;
    @Resource
    private EquipmentprofileMapper equipmentprofileMapper;
    @Resource
    private RedissonClient redissonClient;//对于联动修改的加锁

    @Override
    @LogRecord(type = EQUIPTYPE,subType = COMMON_ADD,
        bizNo = COMMON_BIZNO,success = EQUIPTYPE_ADD_TYPE,
        fail = EQUIPTYPE_ADD_TYPE_FAIL)
    public String createEquiptype(EquiptypeSaveReqVO createReqVO) {
        //插入id
        createReqVO.setId(BeanUtils.createId());
        // 校验父类别id
        validateParentEquiptype(null, createReqVO.getSupId());
        // 校验设备类别名称的唯一性
        validateEquiptypeNameUnique(null, createReqVO.getSupId(), createReqVO.getName(), createReqVO.getCode());
        // 插入
        EquiptypeDO equiptype = BeanUtils.toBean(createReqVO, EquiptypeDO.class);
        equiptypeMapper.insert(equiptype);
        // 返回
        return equiptype.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = EQUIPTYPE,subType = COMMON_UPDATE,
        bizNo = COMMON_BIZNO,success = EQUIPTYPE_UPDATE_TYPE,
        fail = EQUIPTYPE_UPDATE_TYPE_FAIL)
    public void updateEquiptype(EquiptypeSaveReqVO updateReqVO) {
        // 校验存在
        EquiptypeDO oldEquiptype = validateEquiptypeExists(updateReqVO.getId());
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT,BeanUtils.toBean(oldEquiptype,EquiptypeSaveReqVO.class));
        LogRecordContext.putVariable("oldEquiptype",oldEquiptype);
        // 校验父类别id;比如生产类设备下面有子类型切割类设备，切割类设备下面还有手持式切割类设备和固定式切割类设备等等的有效性
        validateParentEquiptype(updateReqVO.getId(), updateReqVO.getSupId());
        // 校验设备类别名称和编码的唯一性
        validateEquiptypeNameUnique(updateReqVO.getId(), updateReqVO.getSupId(), updateReqVO.getName(),updateReqVO.getCode());
        // 更新
        EquiptypeDO updateObj = BeanUtils.toBean(updateReqVO, EquiptypeDO.class);
        RLock lock = redissonClient.getLock("equipType" + updateObj.getId());
        lock.lock();
        try {
            equipMapper.updateTypeNameByTypeId(updateObj.getId(),updateObj.getName());
            equiptypeMapper.updateById(updateObj);
            LambdaUpdateChainWrapper<EquipmentprofileDO> equipmentprofileDOLambdaUpdateChainWrapper=new LambdaUpdateChainWrapper<>(equipmentprofileMapper);
            equipmentprofileDOLambdaUpdateChainWrapper
                    .eq(EquipmentprofileDO::getEquiptypeId,updateObj.getId())
                    .set(EquipmentprofileDO::getEquiptypeName,updateObj.getName())
                    .update();
        }finally {
            lock.unlock();
        }
    }

    @Override
    @LogRecord(type = EQUIPTYPE,subType = COMMON_DEL,
        bizNo = COMMON_BIZNO,success = EQUIPTYPE_DEL_TYPE,
        fail = EQUIPTYPE_DEL_TYPE_FAIL)
    public void deleteEquiptype(String id) {
        // 校验存在
        EquiptypeDO oldEquiptype = validateEquiptypeExists(id);
        LogRecordContext.putVariable("oldEquiptype",oldEquiptype);
        // 校验是否有子设备类别
        if (equiptypeMapper.selectCountBySupId(id) > 0) {
            throw exception(EQUIPTYPE_EXITS_CHILDREN);
        }
        // 校验是否有对应的设备,有的话禁止删除
        if (equipMapper.selectCount("equiptype_id",id) > 0){
            throw exception(EQUIPTYPE_EXITS_EQUIP);
        }
        // 删除
        equiptypeMapper.deleteById(id);
    }

    @Override
    public EquiptypeDO getEquiptype(String id) {
        return equiptypeMapper.selectById(id);
    }

    @Override
    public List<EquiptypeDO> getEquiptypeList(EquiptypeListReqVO listReqVO) {
        return equiptypeMapper.selectList(listReqVO);
    }

    @Override
    public List<EquiptypeRespVO> getEquiptypeListForExcel(EquiptypeListReqVO listReqVO) {
        List<EquiptypeDO> equiptypeDOS = equiptypeMapper.selectList(listReqVO);
        List<EquiptypeRespVO> equiptypeRespVOS = BeanUtils.toBean(equiptypeDOS, EquiptypeRespVO.class);
        Map<String,String> toolmap=new HashMap<>();
        equiptypeRespVOS.forEach(item->{
            toolmap.put(item.getId(),item.getName());
        });
        equiptypeRespVOS.forEach(item->{
            if("0".equals(item.getSupId())){
                item.setSupName("---");
            }else {
                item.setSupName(toolmap.get(item.getSupId()));
            }
        });
        return equiptypeRespVOS;
    }

    private EquiptypeDO validateEquiptypeExists(String id) {
        EquiptypeDO equiptypeDO = equiptypeMapper.selectById(id);
        if (equiptypeDO == null) {
            throw exception(EQUIPTYPE_NOT_EXISTS);
        }
        return equiptypeDO;
    }

    private void validateParentEquiptype(String id, String supId) {
        if (supId == null || EquiptypeDO.CUSTOM_SUP_ID_ROOT.equals(supId)) {
            return;
        }
        // 1. 不能设置自己为父设备类别
        if (Objects.equals(id, supId)) {
            throw exception(EQUIPTYPE_PARENT_ERROR);
        }
        // 2. 父设备类别不存在
        EquiptypeDO parentEquiptype = equiptypeMapper.selectById(supId);
        if (parentEquiptype == null) {
            throw exception(EQUIPTYPE_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父设备类别，如果父设备类别是自己的子设备类别，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            supId = parentEquiptype.getSupId();
            if (Objects.equals(id, supId)) {
                throw exception(EQUIPTYPE_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父设备类别
            if (supId == null || EquiptypeDO.CUSTOM_SUP_ID_ROOT.equals(supId)) {
                break;
            }
            parentEquiptype = equiptypeMapper.selectById(supId);
            if (parentEquiptype == null) {
                break;
            }
        }
    }

    private void validateEquiptypeNameUnique(String id, String supId, String name, String code) {
        //先校验code唯一
        List<EquiptypeDO> equiptypeDOList = equiptypeMapper.selectList("code", code);
        equiptypeDOList.forEach(equiptypeDO -> {
            if(!Objects.equals(id, equiptypeDO.getId())||id==null){
                throw exception(EQUIPTYPE_CODE_DUPLICATE);
            }
        });
        //再校验name唯一
        EquiptypeDO equiptype = equiptypeMapper.selectBySupIdAndName(supId, name);
        if (equiptype == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的设备类别
        if (id == null) {
            throw exception(EQUIPTYPE_NAME_DUPLICATE);
        }
        if (!Objects.equals(equiptype.getId(), id)) {
            throw exception(EQUIPTYPE_NAME_DUPLICATE);
        }
    }

}