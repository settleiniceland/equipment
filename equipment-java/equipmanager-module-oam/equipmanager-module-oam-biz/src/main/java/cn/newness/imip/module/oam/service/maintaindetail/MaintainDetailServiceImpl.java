package cn.newness.imip.module.oam.service.maintaindetail;

import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
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

    @Override
    public String createMaintainDetail(MaintainDetailSaveReqVO createReqVO) {
        RLock lock = redissonClient.getLock("maintainDetail_add"+createReqVO.getEquipId());
        lock.lock();
        try{
            createReqVO.setId(BeanUtils.createId());
            //判断同一设备/特殊设备档案是否已经有了更换自身的保养项？有了就重复了：
            // 1、非特殊设备重复；[equipId相同，isSpecial都为0]
            // 2、特殊设备设备档案重复[equipId相同，isSpecial都为1，equipprofileId相同]
            if(createReqVO.getReplaceSelf()==1){
                MaintainDetailPageReqVO md=new MaintainDetailPageReqVO();
                md.setEquipId(createReqVO.getEquipId());
                md.setReplaceSelf(1);
                md.setIsSpecial(createReqVO.getIsSpecial());
                if(createReqVO.getIsSpecial()==1){
                    md.setEquipprofileId(createReqVO.getEquipprofileId());
                }
                int count = getList(md).size();
                if(count>0){
                    throw exception(MAINTAIN_MORE_REPLACE_ERROR);
                }
            }
            if(createReqVO.getIsSpecial()==0 && createReqVO.getRefertoId() != null && !"".equals(createReqVO.getRefertoId())){
                throw exception(MAINTAIN_REFERTO_ERROR);
            }
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
        MaintainDetailDO oldMaintainDetail;//之后用作操作日志
        RLock lock = redissonClient.getLock("maintainDetail" + updateReqVO.getId());
        lock.lock();
        try{
            if(updateReqVO.getReplaceSelf()==1){
                //判断同一设备是否已经有了更换自身的保养项？有了就重复了：1、非特殊设备重复；2、特殊设备设备档案重复
                MaintainDetailPageReqVO md=new MaintainDetailPageReqVO();
                md.setEquipId(updateReqVO.getEquipId());
                md.setReplaceSelf(1);
                md.setIsSpecial(updateReqVO.getIsSpecial());
                if(updateReqVO.getIsSpecial()==1){
                    md.setEquipprofileId(updateReqVO.getEquipprofileId());
                }
                List<MaintainDetailDO> detailDOList = getList(md);
                for (MaintainDetailDO detail:detailDOList){
                    if(detail.getId().equals(updateReqVO.getId())){
                        oldMaintainDetail = detail;
                    }else {
                        throw exception(MAINTAIN_MORE_REPLACE_ERROR);
                    }
                }
            }else {
                oldMaintainDetail = validateMaintainDetailExists(updateReqVO.getId());
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
            if(maintainDetailRespVO.getReplaceSelf()==0){//是否更换自身
                maintainDetailRespVO.setReplaceSelfName("否");
            }else if(maintainDetailRespVO.getReplaceSelf()==1) {
                maintainDetailRespVO.setReplaceSelfName("是");
            }
        });
        return maintainDetailRespVOS;
    }

    private List<MaintainDetailDO> getList(MaintainDetailPageReqVO md){
        LambdaQueryWrapperX<MaintainDetailDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(MaintainDetailDO::getEquipId, md.getEquipId());
        queryWrapperX.eq(MaintainDetailDO::getIsSpecial, md.getIsSpecial());
        queryWrapperX.eqIfPresent(MaintainDetailDO::getEquipprofileId,md.getEquipprofileId());
        queryWrapperX.eq(MaintainDetailDO::getReplaceSelf,md.getReplaceSelf());
        return maintainDetailMapper.selectList(queryWrapperX);
    }
}