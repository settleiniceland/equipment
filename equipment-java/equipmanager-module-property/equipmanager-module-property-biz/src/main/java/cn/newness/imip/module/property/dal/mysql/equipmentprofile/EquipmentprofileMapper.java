package cn.newness.imip.module.property.dal.mysql.equipmentprofile;

import java.util.*;

import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.newness.imip.framework.mybatis.core.mapper.BaseMapperX;
import cn.newness.imip.module.property.dal.dataobject.equipmentprofile.EquipmentprofileDO;
import org.apache.ibatis.annotations.Mapper;
import cn.newness.imip.module.property.controller.admin.equipmentprofile.vo.*;

/**
 * 设备档案数据 Mapper
 *
 * @author mcr
 */
@Mapper
public interface EquipmentprofileMapper extends BaseMapperX<EquipmentprofileDO> {

    default List<EquipmentprofileDO> selectList(EquipmentprofileListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EquipmentprofileDO>()
                .eqIfPresent(EquipmentprofileDO::getId, reqVO.getId())
                .eqIfPresent(EquipmentprofileDO::getEquipAttribute, reqVO.getEquipAttribute())
                .likeIfPresent(EquipmentprofileDO::getCode, reqVO.getCode())
                .eqIfPresent(EquipmentprofileDO::getQrCode, reqVO.getQrCode())
                .eqIfPresent(EquipmentprofileDO::getManufacturerId, reqVO.getManufacturerId())
                .likeIfPresent(EquipmentprofileDO::getManufacturerName, reqVO.getManufacturerName())
                .eqIfPresent(EquipmentprofileDO::getEquipId, reqVO.getEquipId())
                .likeIfPresent(EquipmentprofileDO::getEquipName, reqVO.getEquipName())
                .likeIfPresent(EquipmentprofileDO::getEquipSpecification, reqVO.getEquipSpecification())
                .eqIfPresent(EquipmentprofileDO::getSupId, reqVO.getSupId())
                .eqIfPresent(EquipmentprofileDO::getEquiptypeId, reqVO.getEquiptypeId())
                .likeIfPresent(EquipmentprofileDO::getEquiptypeName, reqVO.getEquiptypeName())
                .likeIfPresent(EquipmentprofileDO::getDutyName, reqVO.getDutyName())
                .eqIfPresent(EquipmentprofileDO::getStatus1, reqVO.getStatus1())
                .eqIfPresent(EquipmentprofileDO::getStatus2, reqVO.getStatus2())
                .betweenIfPresent(EquipmentprofileDO::getInstallDate, reqVO.getInstallDate())
                .betweenIfPresent(EquipmentprofileDO::getBuyTime, reqVO.getBuyTime())
                .likeIfPresent(EquipmentprofileDO::getFileUrls, reqVO.getFileUrls())
                .likeIfPresent(EquipmentprofileDO::getIconUrls, reqVO.getIconUrls())
                .eqIfPresent(EquipmentprofileDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(EquipmentprofileDO::getWorkshopId, reqVO.getWorkshopId())
                .likeIfPresent(EquipmentprofileDO::getWorkshopName, reqVO.getWorkshopName())
                .likeIfPresent(EquipmentprofileDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(EquipmentprofileDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(EquipmentprofileDO::getUpdater, reqVO.getUpdater())
                .betweenIfPresent(EquipmentprofileDO::getUpdateTime, reqVO.getUpdateTime())
                .orderByDesc(EquipmentprofileDO::getId));
    }

	default Long selectCountByNeIdAndCode(String id, String code) {
        Long num = selectCount(new LambdaQueryWrapperX<EquipmentprofileDO>()
                .neIfPresent(EquipmentprofileDO::getId, id)
                .eq(EquipmentprofileDO::getCode, code));
        return num;
	}

    default Long selectCountBySupId(String supId) {
        return selectCount(EquipmentprofileDO::getSupId, supId);
    }
}