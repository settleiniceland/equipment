package cn.newness.imip.module.oam.service.maintaindetail;

import cn.newness.imip.module.property.api.EquipApi;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import java.util.*;
import cn.newness.imip.module.oam.controller.admin.maintaindetail.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.maintaindetail.MaintainDetailDO;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;
import cn.newness.imip.module.oam.dal.mysql.maintaindetail.MaintainDetailMapper;
import cn.newness.imip.module.oam.service.MaintainDetailService;
import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.oam.enums.ErrorCodeConstants.*;

/**
 * 保养内容表 Service 实现类
 *
 * @author mcr
 */
@Service
@Validated
public class MaintainDetailServiceImpl implements MaintainDetailService {

    @Resource
    private MaintainDetailMapper maintainDetailMapper;
    @Resource
    private RedissonClient redissonClient;//对于条件修改的加锁
    @Resource
    private EquipApi equipApi;

    @Override
    public String createMaintainDetail(MaintainDetailSaveReqVO createReqVO) {
        RLock lock = redissonClient.getLock("maintainDetail_add"+createReqVO.getEquipId());
        lock.lock();
        try{
            createReqVO.setId(BeanUtils.createId());
            if(createReqVO.getIsSpecial()==0){
                if(createReqVO.getRefertoId() != null && !"".equals(createReqVO.getRefertoId())){
                    throw exception(MAINTAIN_REFERTO_ERROR);
                }else {
                    createReqVO.setIsReferto(0);
                }
            }
            //赋值为全层级名
            createReqVO.setEquipName(equipApi.getAllLayerEquipName(createReqVO.getEquipId()));
            // 插入
            MaintainDetailDO maintainDetail = BeanUtils.toBean(createReqVO, MaintainDetailDO.class);
            maintainDetailMapper.insert(maintainDetail);
            // 返回
            return maintainDetail.getId();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void updateMaintainDetail(MaintainDetailSaveReqVO updateReqVO) {
        MaintainDetailDO oldMaintainDetail = validateMaintainDetailExists(updateReqVO.getId());//之后用作操作日志
        RLock lock = redissonClient.getLock("maintainDetail" + updateReqVO.getId());
        lock.lock();
        try{
            //先判断该项内容有没有被作为参考对象，有的话那就只允许修改周期，其他均不允许被修改
            if(maintainDetailMapper.selectList("referto_id",updateReqVO.getId()).size()>0){
                if(
                        !updateReqVO.getIsSpecial().equals(oldMaintainDetail.getIsSpecial()) ||
                        !updateReqVO.getEquipId().equals(oldMaintainDetail.getEquipId()) ||
                        !updateReqVO.getDetails().equals(oldMaintainDetail.getDetails())
                ){
                    throw exception(MAINTAIN_UPDATE_ERROR);
                }
            }
            //修改时如果是非特殊设备也需要搞全层级设备名，不管是不是定期更换计划
            if(updateReqVO.getIsSpecial()==0){
                if(updateReqVO.getRefertoId() != null && !"".equals(updateReqVO.getRefertoId())){
                    throw exception(MAINTAIN_REFERTO_ERROR);
                }else {
                    updateReqVO.setIsReferto(0);
                }
            }
            if (!oldMaintainDetail.getEquipId().equals(updateReqVO.getEquipId())) {
                //赋值为全层级名
                updateReqVO.setEquipName(equipApi.getAllLayerEquipName(updateReqVO.getEquipId()));
            }
            // 更新
            MaintainDetailDO updateObj = BeanUtils.toBean(updateReqVO, MaintainDetailDO.class);
            maintainDetailMapper.updateById(updateObj);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void deleteMaintainDetail(String id) {
        // 校验存在
        validateMaintainDetailExists(id);
        //校验是否被作为参考项目
        if(maintainDetailMapper.selectList("referto_id",id).size()>0){
            throw exception(MAINTAIN_DELETE_ERROR);
        }
        // 删除
        maintainDetailMapper.deleteById(id);
    }

    private MaintainDetailDO validateMaintainDetailExists(String id) {
        MaintainDetailDO maintainDetailDO = maintainDetailMapper.selectById(id);
        if (maintainDetailDO == null) {
            throw exception(MAINTAIN_DETAIL_NOT_EXISTS);
        }
        return maintainDetailDO;
    }

    @Override
    public MaintainDetailRespVO getMaintainDetail(String id) {
        MaintainDetailRespVO respVO = BeanUtils.toBean(maintainDetailMapper.selectById(id), MaintainDetailRespVO.class);
        List<MaintainDetailRespVO> respVOList=new ArrayList<>();
        respVOList.add(respVO);
        return addName(respVOList).get(0);
    }

    @Override
    public PageResult<MaintainDetailRespVO> getMaintainDetailPage(MaintainDetailPageReqVO pageReqVO) {
        PageResult<MaintainDetailRespVO> pageResult = BeanUtils.toBean(maintainDetailMapper.selectPage(pageReqVO), MaintainDetailRespVO.class);
        pageResult.setList(addName(pageResult.getList()));
        return pageResult;
    }

    @Override
    public void deleteMaintainDetailByPlanId(String planId) {
        maintainDetailMapper.delete("equip_maintain_plan_id",planId);
    }

    private List<MaintainDetailRespVO> addName(List<MaintainDetailRespVO> maintainDetailRespVOS) {
        maintainDetailRespVOS.forEach(maintainDetailRespVO -> {
            if(maintainDetailRespVO.getIsSpecial()==0){//是否特殊设备
                maintainDetailRespVO.setIsSpecialName("否");
            }else if(maintainDetailRespVO.getIsSpecial()==1){
                maintainDetailRespVO.setIsSpecialName("是");
            }
            if(maintainDetailRespVO.getIsReferto()!=null){
                if(maintainDetailRespVO.getIsReferto()==0){//是否参照其他
                    maintainDetailRespVO.setIsRefertoName("否");
                }else if(maintainDetailRespVO.getIsReferto()==1){
                    maintainDetailRespVO.setIsRefertoName("是");
                }
            }
        });
        return maintainDetailRespVOS;
    }
}