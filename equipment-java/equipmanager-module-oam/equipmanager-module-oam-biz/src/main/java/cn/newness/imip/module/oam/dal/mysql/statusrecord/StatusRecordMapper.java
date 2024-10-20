package cn.newness.imip.module.oam.dal.mysql.statusrecord;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.oam.controller.admin.statusrecord.vo.StatusRecordPageReqVO;
import cn.newness.imip.module.oam.dal.dataobject.statusrecord.StatusRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 停机表 Mapper
 *
 * @author super超级管理员王大王
 */
@Mapper
public interface StatusRecordMapper extends BaseMapperX<StatusRecordDO> {

    default PageResult<StatusRecordDO> selectPage(StatusRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StatusRecordDO>()
                .eqIfPresent(StatusRecordDO::getId, reqVO.getId())
                .eqIfPresent(StatusRecordDO::getEquipmentprofileId, reqVO.getEquipmentprofileId())
                .likeIfPresent(StatusRecordDO::getEquipName, reqVO.getEquipName())
                .likeIfPresent(StatusRecordDO::getEquipCode, reqVO.getEquipCode())
                .eqIfPresent(StatusRecordDO::getOperationType, reqVO.getOperationType())
                .betweenIfPresent(StatusRecordDO::getOperationTime, reqVO.getOperationTime())
                .likeIfPresent(StatusRecordDO::getChangeDetails, reqVO.getChangeDetails())
                .eqIfPresent(StatusRecordDO::getNewStatus1, reqVO.getNewStatus1())
                .eqIfPresent(StatusRecordDO::getNewStatus2, reqVO.getNewStatus2())
                .likeIfPresent(StatusRecordDO::getDetails, reqVO.getDetails())
                .likeIfPresent(StatusRecordDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(StatusRecordDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(StatusRecordDO::getUpdater, reqVO.getUpdater())
                .betweenIfPresent(StatusRecordDO::getUpdateTime, reqVO.getUpdateTime())
                .orderByDesc(StatusRecordDO::getOperationTime));
    }

}