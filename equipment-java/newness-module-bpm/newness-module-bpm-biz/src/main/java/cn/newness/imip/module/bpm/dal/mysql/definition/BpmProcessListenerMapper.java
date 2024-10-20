package cn.newness.imip.module.bpm.dal.mysql.definition;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.module.bpm.controller.admin.definition.vo.listener.BpmProcessListenerPageReqVO;
import cn.newness.imip.module.bpm.dal.dataobject.definition.BpmProcessListenerDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * BPM 流程监听器 Mapper
 *
 * @author 新奇源码
 */
@Mapper
public interface BpmProcessListenerMapper extends BaseMapperX<BpmProcessListenerDO> {

    default PageResult<BpmProcessListenerDO> selectPage(BpmProcessListenerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BpmProcessListenerDO>()
                .likeIfPresent(BpmProcessListenerDO::getName, reqVO.getName())
                .eqIfPresent(BpmProcessListenerDO::getType, reqVO.getType())
                .eqIfPresent(BpmProcessListenerDO::getEvent, reqVO.getEvent())
                .eqIfPresent(BpmProcessListenerDO::getStatus, reqVO.getStatus())
                .orderByDesc(BpmProcessListenerDO::getId));
    }

}