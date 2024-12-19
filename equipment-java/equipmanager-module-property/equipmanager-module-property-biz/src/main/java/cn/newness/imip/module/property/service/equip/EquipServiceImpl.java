package cn.newness.imip.module.property.service.equip;

import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.module.property.dal.dataobject.equipmentprofile.EquipmentprofileDO;
import cn.newness.imip.module.property.dal.mysql.equipmentprofile.EquipmentprofileMapper;
import cn.newness.imip.module.property.service.EquipService;
import cn.newness.imip.module.property.service.EquipmentprofileService;
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
import cn.newness.imip.module.property.controller.admin.equip.vo.*;
import cn.newness.imip.module.property.dal.dataobject.equip.EquipDO;
import cn.newness.imip.framework.common.util.object.BeanUtils;

import cn.newness.imip.module.property.dal.mysql.equip.EquipMapper;

import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.property.enums.ErrorCodeConstants.*;
import static cn.newness.imip.module.property.enums.PropertyLogRecordConstants.*;

/**
 * 设备表 Service 实现类
 *
 * @author mcr
 */
@Service
@Validated
public class EquipServiceImpl implements EquipService {

    @Resource
    private EquipMapper equipMapper;

    @Resource
    private EquipmentprofileService equipmentprofileService;

    @Resource
    private EquipmentprofileMapper equipmentprofileMapper;

    @Resource
    private RedissonClient redissonClient;//对于联动修改的加锁

    @Override
    @LogRecord(type = EQUIPFRAME,subType = COMMON_ADD,
        bizNo = COMMON_BIZNO,success = EQUIPFRAME_ADD_TYPE,
        fail = EQUIPFRAME_ADD_TYPE_FAIL)
    public String createEquip(EquipSaveReqVO createReqVO) {
        //添加id
        createReqVO.setId(BeanUtils.createId());
        // 校验父id的有效性
        validateParentEquip(null, createReqVO.getSupId());
        // 校验设备名称的唯一性
        validateEquipEquipNameUnique(null, createReqVO.getSupId(), createReqVO.getEquipName());
        // 校验设备属性更改的是否符合逻辑（如果不是顶端设备的话，其新属性级别不能高于父设备属性
        // 父设备dad，新属性new，必须new>=dad）
        if(!"0".equals(createReqVO.getSupId())){
            Integer dad = equipMapper.selectById(createReqVO.getSupId()).getEquipAttribute();
            if(!(dad<=createReqVO.getEquipAttribute())){
                throw exception(EQUIP_UNRESONABLE_ATTRIBUTE);
            }
        }
        // 插入
        EquipDO equip = BeanUtils.toBean(createReqVO, EquipDO.class);
        equipMapper.insert(equip);
        // 返回
        return equip.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = EQUIPFRAME,subType = COMMON_UPDATE,
        bizNo = COMMON_BIZNO,success = EQUIPFRAME_UPDATE_TYPE,
        fail = EQUIPFRAME_UPDATE_TYPE_FAIL)
    public void updateEquip(EquipSaveReqVO updateReqVO) {
        // 校验存在
        EquipDO oldEquip = validateEquipExists(updateReqVO.getId());
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT,BeanUtils.toBean(oldEquip, EquipSaveReqVO.class));
        LogRecordContext.putVariable("oldEquip",oldEquip);
        // 校验父id的有效性
        validateParentEquip(updateReqVO.getId(), updateReqVO.getSupId());
        // 校验设备名称的唯一性
        validateEquipEquipNameUnique(updateReqVO.getId(), updateReqVO.getSupId(), updateReqVO.getEquipName());
        // 校验设备属性更改的是否符合逻辑（1、有儿子的话查到儿子的属性son（儿子可能有多个，那就取值最小(值最小级别最高)的那个儿子）,不然son=1；2、非顶端设备的话查到他爹的属性dad,不然dad=3；
        // 新属性是new，则必须son>=new>=dad）
        Integer son=3;
        Integer dad=1;
        List<EquipDO> sonEquipDOList = equipMapper.selectList(new LambdaQueryWrapperX<EquipDO>()
                .eq(EquipDO::getSupId, updateReqVO.getId())
                .orderByAsc(EquipDO::getEquipAttribute));
        if(sonEquipDOList.size()>0){
            son=sonEquipDOList.get(0).getEquipAttribute();
        }
        if(!"0".equals(updateReqVO.getSupId())){
            dad = equipMapper.selectById(updateReqVO.getSupId()).getEquipAttribute();
        }
        if(!(updateReqVO.getEquipAttribute()>=dad && updateReqVO.getEquipAttribute()<=son)){
            throw exception(EQUIP_UNRESONABLE_ATTRIBUTE);
        }
        //当修改层级关系时，判断该设备下是否有实体设备，有的话是不允许修修改的
        if(!oldEquip.getSupId().equals(updateReqVO.getSupId())){
            List<EquipmentprofileDO> equipmentprofileDOList = equipmentprofileMapper.selectList("equip_id", updateReqVO.getId());
            if(equipmentprofileDOList.size()>0){
                //不允许修改
                throw exception(EQUIP_HIERARCHY_ERROR);
            }
        }
        RLock lock = redissonClient.getLock("equip" + updateReqVO.getId());
        lock.lock();
        try {
            // equipprofile表更新
            LambdaUpdateChainWrapper<EquipmentprofileDO> equipmentprofileDOLambdaUpdateChainWrapper=new LambdaUpdateChainWrapper<>(equipmentprofileMapper);
            equipmentprofileDOLambdaUpdateChainWrapper
                    .eq(EquipmentprofileDO::getEquipId,updateReqVO.getId())
                    .set(EquipmentprofileDO::getEquipName,updateReqVO.getEquipName())
                    .set(EquipmentprofileDO::getEquipAttribute,updateReqVO.getEquipAttribute())
                    .set(EquipmentprofileDO::getEquipSpecification,updateReqVO.getEquipSpecification())
                    .set(EquipmentprofileDO::getEquiptypeId,updateReqVO.getEquiptypeId())
                    .set(EquipmentprofileDO::getEquiptypeName,updateReqVO.getEquiptypeName())
                    .update();
            // equip表更新
            EquipDO updateObj = BeanUtils.toBean(updateReqVO, EquipDO.class);
            equipMapper.updateById(updateObj);
        }finally {
            lock.unlock();
        }
    }

    @Override
    @LogRecord(type = EQUIPFRAME,subType = COMMON_DEL,
        bizNo = COMMON_BIZNO,success = EQUIPFRAME_DEL_TYPE,
        fail = EQUIPFRAME_DEL_TYPE_FAIL)
    public void deleteEquip(String id) {
        // 校验存在
        EquipDO oldEquip = validateEquipExists(id);
        LogRecordContext.putVariable("oldEquip",oldEquip);
        // 校验是否有子设备表
        if (equipMapper.selectCountBySupId(id) > 0) {
            throw exception(EQUIP_EXITS_CHILDREN);
        }
        if(equipmentprofileService.getEquipmentprofileListByEquipId(id).size()>0){
            throw exception(EQUIP_EXIST_EQUIPPROFILE);
        }
        // 删除
        equipMapper.deleteById(id);
    }

    @Override
    public EquipDO getEquip(String id) {
        return equipMapper.selectById(id);
    }

    @Override
    public List<EquipDO> getEquipList(EquipListReqVO listReqVO) {
        return equipMapper.selectList(listReqVO);
    }

    @Override
    public List<EquipRespVO> getEquipListForExcel(EquipListReqVO listReqVO) {
        List<EquipDO> equipDOList = equipMapper.selectList(listReqVO);
        List<EquipRespVO> equipRespVOList = BeanUtils.toBean(equipDOList, EquipRespVO.class);
        Map<String,String> toolMap=new HashMap<>();
        equipRespVOList.forEach(item->{
            toolMap.put(item.getId(),item.getEquipName());
        });
        equipRespVOList.forEach(item->{
            if("0".equals(item.getSupId())){
                item.setSupName("---");
            }else {
                item.setSupName(toolMap.get(item.getSupId()));
            }
            if(item.getEquipAttribute()==1){
                item.setEquipAttributeName("设备组");
            }else if(item.getEquipAttribute()==2){
                item.setEquipAttributeName("设备");
            }else if(item.getEquipAttribute()==3){
                item.setEquipAttributeName("设备组件");
            }
        });
        return equipRespVOList;
    }

    @Override
    public String getCompleteEquipName(String id) {
        return getAllLayerNameById(id);
    }
    /**
    * 求设备全层级名
    *
    * @param [id]
    * @author machuran
    * @date 2024.11.19
    * @Return java.lang.String
    */
    private String getAllLayerNameById(String id) {
        EquipDO equipDO = equipMapper.selectById(id);
        if("0".equals(equipDO.getSupId())){
            return equipDO.getEquipName();
        }
        return getAllLayerNameById(equipDO.getSupId())+"->"+equipDO.getEquipName();
    }

    private EquipDO validateEquipExists(String id) {
        EquipDO equipDO = equipMapper.selectById(id);
        if (equipDO == null) {
            throw exception(EQUIP_NOT_EXISTS);
        }
        return equipDO;
    }

    private void validateParentEquip(String id, String supId) {
        if (supId == null || EquipDO.CUSTOM_SUP_ID_ROOT.equals(supId)) {
            return;
        }
        // 1. 不能设置自己为父设备表
        if (Objects.equals(id, supId)) {
            throw exception(EQUIP_PARENT_ERROR);
        }
        // 2. 父设备表不存在
        EquipDO parentEquip = equipMapper.selectById(supId);
        if (parentEquip == null) {
            throw exception(EQUIP_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父设备表，如果父设备表是自己的子设备表，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            supId = parentEquip.getSupId();
            if (Objects.equals(id, supId)) {
                throw exception(EQUIP_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父设备表
            if (supId == null || EquipDO.CUSTOM_SUP_ID_ROOT.equals(supId)) {
                break;
            }
            parentEquip = equipMapper.selectById(supId);
            if (parentEquip == null) {
                break;
            }
        }
    }

    private void validateEquipEquipNameUnique(String id, String supId, String equipName) {
        EquipDO equip = equipMapper.selectBySupIdAndEquipName(supId, equipName);
        if (equip == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的设备表
        if (id == null) {
            throw exception(EQUIP_EQUIP_NAME_DUPLICATE);
        }
        if (!Objects.equals(equip.getId(), id)) {
            throw exception(EQUIP_EQUIP_NAME_DUPLICATE);
        }
    }
}