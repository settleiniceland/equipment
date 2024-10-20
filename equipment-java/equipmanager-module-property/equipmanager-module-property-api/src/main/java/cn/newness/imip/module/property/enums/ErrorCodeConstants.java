package cn.newness.imip.module.property.enums;

import cn.newness.imip.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 *
 * system 系统，使用 1-002-000-000 段
 */
public interface ErrorCodeConstants {
//    生产厂家部分
    ErrorCode MANUFACTURER_NOT_EXISTS = new ErrorCode(2_000_000_000, "设备生产厂家信息不存在");
    ErrorCode EXISTS_EQUIPMENTPROFILE = new ErrorCode(2_100_000_000, "该厂家下存在设备,不允许删除/禁用");
    ErrorCode EXISTS__CODE_DUPLICATE = new ErrorCode(2_000_000_027, "生产厂家编码重复，不允许新增/修改");
//    设备安装位置部分
    ErrorCode INSTALLLOCATION_NOT_EXISTS = new ErrorCode(2_000_000_001, "设备安装位置不存在");
    ErrorCode INSTALLLOCATION_EXITS_CHILDREN = new ErrorCode(2_000_000_002, "存在子设备安装位置，无法删除");
    ErrorCode INSTALLLOCATION_EXITS_EQUIPMENTPROFILE = new ErrorCode(2_100_000_002, "存在设备档案，无法删除");
    ErrorCode INSTALLLOCATION_PARENT_NOT_EXITS = new ErrorCode(2_000_000_003,"父级设备安装位置不存在");
    ErrorCode INSTALLLOCATION_PARENT_ERROR = new ErrorCode(2_000_000_004, "不能设置自己为父设备安装位置");
    ErrorCode INSTALLLOCATION_NAME_DUPLICATE = new ErrorCode(2_000_000_005, "已经存在该地区名称的设备安装位置");
    ErrorCode INSTALLLOCATION_CODE_DUPLICATE = new ErrorCode(2_000_000_026, "已经存在该地区编码的设备安装位置");
    ErrorCode INSTALLLOCATION_PARENT_IS_CHILD = new ErrorCode(2_000_000_006, "不能设置自己的子Installlocation为父Installlocation");
//    设备类别
    ErrorCode EQUIPTYPE_NOT_EXISTS = new ErrorCode(2_000_000_007, "设备类别不存在");
    ErrorCode EQUIPTYPE_EXITS_CHILDREN = new ErrorCode(2_000_000_008, "存在子设备类别，无法删除");
    ErrorCode EQUIPTYPE_PARENT_NOT_EXITS = new ErrorCode(2_000_000_009,"父级设备类别不存在");
    ErrorCode EQUIPTYPE_PARENT_ERROR = new ErrorCode(2_000_000_010, "不能设置自己为父设备类别");
    ErrorCode EQUIPTYPE_NAME_DUPLICATE = new ErrorCode(2_000_000_011, "已经存在该设备类别名称的设备类别");
    ErrorCode EQUIPTYPE_CODE_DUPLICATE = new ErrorCode(2_000_000_028, "已经存在该设备类别编码的设备类别");
    ErrorCode EQUIPTYPE_PARENT_IS_CHILD = new ErrorCode(2_000_000_012, "不能设置自己的下级类别为自己的上级类别");
    ErrorCode EQUIPTYPE_EXITS_EQUIP = new ErrorCode(2_100_000_012, "该类别下存在设备，无法删除");
//    设备
    ErrorCode EQUIP_NOT_EXISTS = new ErrorCode(2_000_000_013, "设备表不存在");
    ErrorCode EQUIP_EXITS_CHILDREN = new ErrorCode(2_000_000_014, "存在子设备表，无法删除");
    ErrorCode EQUIP_PARENT_NOT_EXITS = new ErrorCode(2_000_000_015,"父级设备表不存在");
    ErrorCode EQUIP_PARENT_ERROR = new ErrorCode(2_000_000_016, "不能设置自己为父设备表");
    ErrorCode EQUIP_EQUIP_NAME_DUPLICATE = new ErrorCode(2_000_000_017, "已经存在该设备名称的设备表");
    ErrorCode EQUIP_PARENT_IS_CHILD = new ErrorCode(2_000_000_018, "不能设置自己的子Equip为父Equip");
    ErrorCode EQUIP_UNRESONABLE_ATTRIBUTE = new ErrorCode(2_100_000_018,"设备属性设置不合理");
    ErrorCode EQUIP_EXIST_EQUIPPROFILE = new ErrorCode(2_110_000_018,"该设备下存在实体设备档案，无法删除");
    ErrorCode EQUIP_HIERARCHY_ERROR = new ErrorCode(2_000_000_025,"该设备下存在实体设备档案，不允许修改其层级关系");
//    设备档案
    ErrorCode EQUIPMENTPROFILE_NOT_EXISTS = new ErrorCode(2_000_000_019, "设备档案数据不存在");
    ErrorCode EQUIPMENTPROFILE_EXITS_CHILDREN = new ErrorCode(2_000_000_020, "存在子设备档案数据，无法删除");
    ErrorCode EQUIPMENTPROFILE_PARENT_NOT_EXITS = new ErrorCode(2_000_000_021,"父级设备档案数据不存在");
    ErrorCode EQUIPMENTPROFILE_PARENT_ERROR = new ErrorCode(2_000_000_022, "不能设置自己为父设备档案数据");
    ErrorCode EQUIPMENTPROFILE_CODE_DUPLICATE = new ErrorCode(2_000_000_023, "已经存在该设备编码的设备档案数据");
    ErrorCode EQUIPMENTPROFILE_PARENT_IS_CHILD = new ErrorCode(2_000_000_024, "不能设置自己的子设备档案为自己的父设备档案");
    ErrorCode EQUIPMENTPROFILE_STATUS_NO_CHANGE = new ErrorCode(2_000_000_029,"设备状态未改变");
}
