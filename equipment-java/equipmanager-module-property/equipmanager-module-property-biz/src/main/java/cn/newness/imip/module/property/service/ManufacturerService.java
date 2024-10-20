package cn.newness.imip.module.property.service;

import jakarta.validation.*;
import cn.newness.imip.module.property.controller.admin.manufacturer.vo.*;
import cn.newness.imip.module.property.dal.dataobject.manufacturer.ManufacturerDO;
import cn.newness.imip.framework.common.pojo.PageResult;

import java.util.List;

/**
 * 设备生产厂家信息 Service 接口
 *
 * @author mcr
 */
public interface ManufacturerService {

    /**
     * 创建设备生产厂家信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createManufacturer(@Valid ManufacturerSaveReqVO createReqVO);

    /**
     * 更新设备生产厂家信息
     *
     * @param updateReqVO 更新信息
     */
    void updateManufacturer(@Valid ManufacturerSaveReqVO updateReqVO);

    /**
     * 删除设备生产厂家信息
     *
     * @param id 编号
     */
    void deleteManufacturer(String id);

    /**
     * 获得设备生产厂家信息
     *
     * @param id 编号
     * @return 设备生产厂家信息
     */
    ManufacturerDO getManufacturer(String id);

    /**
     * 获得设备生产厂家信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备生产厂家信息分页
     */
    PageResult<ManufacturerDO> getManufacturerPage(ManufacturerPageReqVO pageReqVO);
    /**
    * 专门导出excel表的
    *
    * @param
    * @author machuran
    * @date 2024/8/5
    * @Return cn.newness.imip.framework.common.pojo.PageResult<cn.newness.imip.module.property.dal.dataobject.manufacturer.ManufacturerDO>
    */
    List<ManufacturerRespVO> getManufacturerPageForExport(ManufacturerPageReqVO pageReqVO);
    /**
    * 查询所有状态正常的生产厂商
    *
    * @param []
    * @author machuran
    * @date 8/10/2024
    * @Return cn.newness.imip.framework.common.pojo.PageResult<cn.newness.imip.module.property.dal.dataobject.manufacturer.ManufacturerDO>
    */
    List<ManufacturerDO> getAllNormalManufacturer();
}