package cn.newness.imip.module.property.service.equipmentprofile;

import cn.newness.imip.module.infra.api.file.FileApi;
import cn.newness.imip.module.oam.api.StatusRecordApi;
import cn.newness.imip.module.oam.api.dto.StatusRecordDto;
import cn.newness.imip.module.property.api.dto.EquipmentProfileDto;
import cn.newness.imip.module.property.service.EquipmentprofileService;
import cn.newness.imip.module.property.service.InstalllocationService;
import cn.newness.imip.module.system.api.equip.EquipProfileQrCodeApi;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import com.mzt.logapi.starter.annotation.LogRecord;
import io.micrometer.common.util.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.util.*;
import java.util.stream.Collectors;
import cn.newness.imip.module.property.controller.admin.equipmentprofile.vo.*;
import cn.newness.imip.module.property.dal.dataobject.equipmentprofile.EquipmentprofileDO;
import cn.newness.imip.framework.common.util.object.BeanUtils;
import cn.newness.imip.module.property.dal.mysql.equipmentprofile.EquipmentprofileMapper;
import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.property.enums.ErrorCodeConstants.*;
import static cn.newness.imip.module.property.enums.PropertyLogRecordConstants.*;

/**
 * 设备档案数据 Service 实现类
 *
 * @author mcr
 */
@Service
@Validated
public class EquipmentprofileServiceImpl implements EquipmentprofileService {

    @Resource
    private EquipmentprofileMapper equipmentprofileMapper;
    @Resource
    private InstalllocationService installlocationService;
    @Resource
    private FileApi fileApi;
    @Resource
    private EquipProfileQrCodeApi equipProfileQrCodeApi;
    @Resource
    private RedissonClient redissonClient;//对于联动修改的加锁
    @Resource
    private StatusRecordApi statusRecordApi;

    @Override
    @LogRecord(type = EQUIPPROFILE,subType = COMMON_ADD,
        bizNo = COMMON_BIZNO,success = EQUIPPROFILE_ADD_TYPE,
        fail = EQUIPPROFILE_ADD_TYPE_FAIL)
    public String createEquipmentprofile(EquipmentprofileSaveReqVO createReqVO) {
        createReqVO.setId(BeanUtils.createId());
        // 校验父设备档案id;最顶级父id为0的有效性
        validateParentEquipmentprofile(null, createReqVO.getSupId());
        // 校验设备编码的唯一性
        validateEquipmentprofileCodeUnique(null, createReqVO.getCode());
        //视情况为地址名称赋值【因为有设备组，设备，设备组件的区别】
        if(StringUtils.isNotEmpty(createReqVO.getLocationId())){
            createReqVO.setLocationName(installlocationService.getCompleteName(createReqVO.getLocationId()));
        }
        // 插入
        if(createReqVO.getEquipAttribute()==2){//如果是单体设备的话才生成二维码
            String qrCodeFileUrl = equipProfileQrCodeApi.createAndUploadQrCode(createReqVO.getId(),createReqVO.getEquipName(),createReqVO.getCode(),createReqVO.getCode()+createReqVO.getEquipName());
            createReqVO.setQrCode(qrCodeFileUrl);
        }
        EquipmentprofileDO equipmentprofile = BeanUtils.toBean(createReqVO, EquipmentprofileDO.class);
        equipmentprofile.setStatus1(2);
        equipmentprofileMapper.insert(equipmentprofile);
        // 返回
        return equipmentprofile.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = EQUIPPROFILE,subType = COMMON_UPDATE,
        bizNo = COMMON_BIZNO,success = EQUIPPROFILE_UPDATE_TYPE,
        fail = EQUIPPROFILE_UPDATE_TYPE_FAIL)
    public void updateEquipmentprofile(EquipmentprofileSaveReqVO updateReqVO) {
        // 校验存在
        EquipmentprofileDO oldEquipmentprofile = validateEquipmentprofileExists(updateReqVO.getId());
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT,BeanUtils.toBean(oldEquipmentprofile,EquipmentprofileSaveReqVO.class));
        LogRecordContext.putVariable("oldEquipmentprofile",oldEquipmentprofile);
        // 校验父设备档案id;最顶级父id为0的有效性
        validateParentEquipmentprofile(updateReqVO.getId(), updateReqVO.getSupId());
        // 校验设备编码的唯一性
        validateEquipmentprofileCodeUnique(updateReqVO.getId(), updateReqVO.getCode());
        //视情况求取位置名称值【因为有设备组，设备，设备组件的区别】
        if(StringUtils.isNotEmpty(updateReqVO.getLocationId())&&!(updateReqVO.getLocationId().equals(oldEquipmentprofile.getLocationId()))){
            //只有当修改项有位置id，并且修改项的位置id和数据库原位置id不一样时才进行赋值，因为求取完整位置层级需要多次网络io，还是比较耗时的；
            updateReqVO.setLocationName(installlocationService.getCompleteName(updateReqVO.getLocationId()));
        }
//        RLock lock = redissonClient.getLock("equipmentprofile:" + updateReqVO.getId());
//        lock.lock();
//        try{//todo 修改状态与修改设备档案基本信息分开写
//            if(oldEquipmentprofile.getStatus()!=updateReqVO.getStatus()){
//                StopDto stopDto=new StopDto();
//                stopDto.setEquipmentprofileId(updateReqVO.getId());
//                stopDto.setEquipName(updateReqVO.getEquipName());
//                stopDto.setEquipCode(updateReqVO.getCode());
//                if(updateReqVO.getStatus()==1){//手动开机
//                    stopDto.setEndTime(LocalDateTime.now());
//                    stopApi.updateStop(stopDto);
//                }else if(updateReqVO.getStatus()==2){//手动关机
//                    stopDto.setId(BeanUtils.createId());
//                    stopDto.setStopReason(2);
//                    stopDto.setBeginTime(LocalDateTime.now());
//                    stopApi.addStop(stopDto);
//                }
//            }
            // 更新
            EquipmentprofileDO updateObj = BeanUtils.toBean(updateReqVO, EquipmentprofileDO.class);
            equipmentprofileMapper.updateById(updateObj);
//        }finally {
//            lock.unlock();
//        }
    }

    @Override
    @LogRecord(type = EQUIPPROFILE,subType = COMMON_DEL,
        bizNo = COMMON_BIZNO,success = EQUIPPROFILE_DEL_TYPE,
        fail = EQUIPPROFILE_DEL_TYPE_FAIL)
    public void deleteEquipmentprofile(String id,String code,Integer equipAttribute) {
        // 校验存在
        EquipmentprofileDO oldEquipmentprofile = validateEquipmentprofileExists(id);
        LogRecordContext.putVariable("oldEquipmentprofile",oldEquipmentprofile);
        // 校验是否有子设备档案数据
        if (equipmentprofileMapper.selectCountBySupId(id) > 0) {
            throw exception(EQUIPMENTPROFILE_EXITS_CHILDREN);
        }
        // 删除
        equipmentprofileMapper.deleteById(id);
        // 【如果是单体设备的话，随即删除文件服务器该设备档案的文件，节省空间】
        if(equipAttribute==2){
            try {
                fileApi.deleteFileList(code+"/");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private EquipmentprofileDO validateEquipmentprofileExists(String id) {
        EquipmentprofileDO oldEquipmentprofile = equipmentprofileMapper.selectById(id);
        if (oldEquipmentprofile == null) {
            throw exception(EQUIPMENTPROFILE_NOT_EXISTS);
        }
        return oldEquipmentprofile;
    }

    private void validateParentEquipmentprofile(String id, String supId) {
        if (supId == null || EquipmentprofileDO.CUSTOM_SUP_ID_ROOT.equals(supId)) {
            return;
        }
        // 1. 不能设置自己为父设备档案数据
        if (Objects.equals(id, supId)) {
            throw exception(EQUIPMENTPROFILE_PARENT_ERROR);
        }
        // 2. 父设备档案数据不存在
        EquipmentprofileDO parentEquipmentprofile = equipmentprofileMapper.selectById(supId);
        if (parentEquipmentprofile == null) {
            throw exception(EQUIPMENTPROFILE_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父设备档案数据，如果父设备档案数据是自己的子设备档案数据，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            supId = parentEquipmentprofile.getSupId();
            if (Objects.equals(id, supId)) {
                throw exception(EQUIPMENTPROFILE_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父设备档案数据
            if (supId == null || EquipmentprofileDO.CUSTOM_SUP_ID_ROOT.equals(supId)) {
                break;
            }
            parentEquipmentprofile = equipmentprofileMapper.selectById(supId);
            if (parentEquipmentprofile == null) {
                break;
            }
        }
    }

    private void validateEquipmentprofileCodeUnique(String id, String code) {
        //直接根据id不相同(不为空时才对比不相同),code相同的条件查找,查到那就抛异常
        Long num = equipmentprofileMapper.selectCountByNeIdAndCode(id, code);
        if(num>0){
            throw exception(EQUIPMENTPROFILE_CODE_DUPLICATE);
        }
    }

    @Override
    public EquipmentprofileDO getEquipmentprofile(String id) {
        return equipmentprofileMapper.selectById(id);
    }

    @Override
    public List<EquipmentprofileDO> getEquipmentprofileList(EquipmentprofileListReqVO listReqVO) {
        return equipmentprofileMapper.selectList(listReqVO);
    }

    @Override
    public List<EquipmentprofileRespVO> getEquipmentprofileListByEquipId(String equipId) {
        List<EquipmentprofileDO> equipmentprofileDOList = equipmentprofileMapper.selectList("equip_id", equipId);
        return BeanUtils.toBean(equipmentprofileDOList, EquipmentprofileRespVO.class);
    }

    @Override
    public List<EquipmentprofileRespVO> getEquipmentprofileListForExcel(EquipmentprofileListReqVO listReqVO) {
        List<EquipmentprofileDO> equipmentprofileDOList = equipmentprofileMapper.selectList(listReqVO);
        List<String> supIds = new ArrayList<>();
        for (EquipmentprofileDO equipmentprofileDO : equipmentprofileDOList) {
            supIds.add(equipmentprofileDO.getSupId());
        }
        List<EquipmentprofileDO> supEquipments = equipmentprofileMapper.selectList("id", supIds);
        Map<String, String> supNameTool = new HashMap<>();
        for (EquipmentprofileDO supEquipment : supEquipments) {
            supNameTool.put(supEquipment.getId(),supEquipment.getEquipName());
        }
        List<EquipmentprofileRespVO> equipmentprofileRespVOS = BeanUtils.toBean(equipmentprofileDOList, EquipmentprofileRespVO.class);
        equipmentprofileRespVOS.forEach(equipmentprofileRespVO -> {
            equipmentprofileRespVO.setSupName(supNameTool.getOrDefault(equipmentprofileRespVO.getSupId(),"---"));
            if(equipmentprofileRespVO.getEquipAttribute()==1){
                equipmentprofileRespVO.setEquipAttributeName("设备组");
            }else if(equipmentprofileRespVO.getEquipAttribute()==2){
                equipmentprofileRespVO.setEquipAttributeName("设备");
                if(equipmentprofileRespVO.getStatus1()==1){
                    equipmentprofileRespVO.setStatus1Name("开机");
                }else if(equipmentprofileRespVO.getStatus1()==2){
                    equipmentprofileRespVO.setStatus1Name("关机");
                }
            }else if(equipmentprofileRespVO.getEquipAttribute()==3){
                equipmentprofileRespVO.setEquipAttributeName("设备组件");
            }
            if(equipmentprofileRespVO.getStatus2()==3){
                equipmentprofileRespVO.setStatus2Name("异动中");
            }else if(equipmentprofileRespVO.getStatus2()==4){
                equipmentprofileRespVO.setStatus2Name("异动完毕");
            }else if(equipmentprofileRespVO.getStatus2()==5){
                equipmentprofileRespVO.setStatus2Name("回国返修中");
            }else if(equipmentprofileRespVO.getStatus2()==6){
                equipmentprofileRespVO.setStatus2Name("回国返修完毕");
            }else if(equipmentprofileRespVO.getStatus2()==7){
                equipmentprofileRespVO.setStatus2Name("报废");
            }
        });
        return equipmentprofileRespVOS;
    }

    @Override
    public List<EquipmentProfileDto> getAllAttribute2EquipmentProfileDtoSons(String equipmentProfileId) {
        List<EquipmentprofileDO> equipmentprofileDOList = new ArrayList<>();
        EquipmentprofileDO equipmentprofileDO = new EquipmentprofileDO();
        equipmentprofileDO.setId(equipmentProfileId);
        equipmentprofileDOList.add(equipmentprofileDO);
        return BeanUtils.toBean(getAllA2SonsIds(equipmentprofileDOList), EquipmentProfileDto.class);
    }

    @Override
    public EquipmentProfileDto getSupAttribute2EquipmentProfileDto(String equipmentProfileId) {
        EquipmentprofileDO equipmentprofileDO = new EquipmentprofileDO();
        equipmentprofileDO.setId(equipmentProfileId);
        return BeanUtils.toBean(getSupA2Id(equipmentprofileDO), EquipmentProfileDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = EQUIPPROFILE,subType = COMMON_UPDATE,
        bizNo = COMMON_BIZNO,success = EQUIPPROFILE_STATUS_UPDATE_TYPE,
        fail = EQUIPPROFILE_STATUS_UPDATE_TYPE_FAIL)
    public void updateEquipmentprofileStatus(EquipmentprofileStatusSaveReqVO statusUpdateReqVO) {
        //校验存在,之后操作日志相关
        EquipmentprofileDO oldStatusEquipmentprofile = validateEquipmentprofileExists(statusUpdateReqVO.getId());
        LogRecordContext.putVariable("oldStatusEquipmentprofile",oldStatusEquipmentprofile);
        EquipmentprofileDO newStatusEquipmentprofile = BeanUtils.toBean(oldStatusEquipmentprofile, EquipmentprofileDO.class);
        newStatusEquipmentprofile.setStatus1(statusUpdateReqVO.getStatus1());
        newStatusEquipmentprofile.setStatus2(statusUpdateReqVO.getStatus2());
        LogRecordContext.putVariable("newStatusEquipmentprofile",newStatusEquipmentprofile);
        //真正业务逻辑
        String cd1="1";String cd2="1";
        if(Objects.equals(newStatusEquipmentprofile.getStatus1(),oldStatusEquipmentprofile.getStatus1())){
            cd1="0";
        }
        if(Objects.equals(newStatusEquipmentprofile.getStatus2(),oldStatusEquipmentprofile.getStatus2())){
            cd2="0";
        }
        if("0".equals(cd1)&&"0".equals(cd2)){
            throw exception(EQUIPMENTPROFILE_STATUS_NO_CHANGE);
        }
        statusUpdateReqVO.setOperationType(2);
        RLock lock = redissonClient.getLock("equipmentprofile" + statusUpdateReqVO.getId());
        lock.lock();
        try {
            LambdaUpdateChainWrapper<EquipmentprofileDO> updateChainWrapper = new LambdaUpdateChainWrapper<>(equipmentprofileMapper);
            updateChainWrapper.eq(EquipmentprofileDO::getId,statusUpdateReqVO.getId())
                    .set(EquipmentprofileDO::getStatus1,statusUpdateReqVO.getStatus1())
                    .set(EquipmentprofileDO::getStatus2,statusUpdateReqVO.getStatus2())
                    .update();
            StatusRecordDto srd = BeanUtils.toBean(statusUpdateReqVO, StatusRecordDto.class);
            srd.setChangeDetails(cd1+"_"+cd2);
            statusRecordApi.addStatusRecord(srd);
        }finally {
            lock.unlock();
        }
    }

    /**
    * 广度优先算法取出所有的单体设备ids
    *
    * @param [equipmentProfileIdList]
    * @author machuran
    * @date 9/13/2024
    * @Return java.util.List<java.lang.String>
    */
    private List<EquipmentprofileDO> getAllA2SonsIds(List<EquipmentprofileDO> equipmentProfileList) {
        List<EquipmentprofileDO> sonsProfiles = new ArrayList<>();
        List<EquipmentprofileDO> No3Profiles = new ArrayList<>();
        List<EquipmentprofileDO> equipmentprofileDOList = equipmentprofileMapper.selectList("sup_id",equipmentProfileList.stream()
                .map(EquipmentprofileDO::getId).collect(Collectors.toList()));
        equipmentprofileDOList.forEach(equipmentprofileDO -> {
            if(equipmentprofileDO.getEquipAttribute()==2){
                sonsProfiles.add(equipmentprofileDO);
                No3Profiles.add(equipmentprofileDO);
            }else if(equipmentprofileDO.getEquipAttribute()==1){
                No3Profiles.add(equipmentprofileDO);
            }
        });
        if(No3Profiles.size()>0){
            sonsProfiles.addAll(getAllA2SonsIds(No3Profiles));
        }
        return sonsProfiles;
    }
    /**
    * 循环取出第一个遇见的上层单体设备id
    *
    * @param [equipmentProfileId]
    * @author machuran
    * @date 9/13/2024
    * @Return java.lang.String
    */
    private EquipmentprofileDO getSupA2Id(EquipmentprofileDO equipProfileDO){
        if("0".equals(equipProfileDO.getId())){
            return null;
        }
        EquipmentprofileDO equipmentprofileDO = equipmentprofileMapper.selectById(equipProfileDO.getId());
        if(equipmentprofileDO.getEquipAttribute()==2){
            return equipmentprofileDO;
        }else {
            equipmentprofileDO.setId(equipmentprofileDO.getSupId());
            return getSupA2Id(equipmentprofileDO);
        }
    }
}