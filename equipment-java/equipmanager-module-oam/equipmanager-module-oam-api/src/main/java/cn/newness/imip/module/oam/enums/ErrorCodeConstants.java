package cn.newness.imip.module.oam.enums;

import cn.newness.imip.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 *
 * system 系统，使用 1-002-000-000 段
 */
public interface ErrorCodeConstants {
//    点巡检计划
    ErrorCode INSPECTPLAN_NOT_EXISTS = new ErrorCode(0_000_001_000, "点检计划不存在");
    ErrorCode INSPECTPLAN_NAME_REPETITION = new ErrorCode(0_000_001_001, "计划名不能重复");
    ErrorCode INSPECTION_RUNNING = new ErrorCode(0_000_001_002, "点检计划启动中,不可删除");
    ErrorCode INSPECTION_HAVE_PROFILE = new ErrorCode(0_000_001_003, "点检计划已有对应点检记录,不可删除");
    ErrorCode INSPECTION_NO_EQUIP = new ErrorCode(0_000_001_021,"此位置下无此类型设备");
    ErrorCode INSPECTION_UPDATE_EQUIPID_ERROR = new ErrorCode(0_000_001_022,"该计划下存在内容，不可修改关联设备");
//    点检计划内容
    ErrorCode INSPECTION_SUBSTANCE_NOT_EXISTS = new ErrorCode(0_000_001_004, "点检内容不存在");
    ErrorCode INSPECTION_PLAN_EXISTS = new ErrorCode(0_000_001_005, "点检内容绑定有点检计划,不可删除");
//    点检计划内容映射表
    ErrorCode INSPECTION_SUBSTANCE_MAP_NOT_EXISTS = new ErrorCode(0_000_001_006, "映射关系不存在");
    ErrorCode INSPECTION_INSERT_FAIL = new ErrorCode(0_000_001_007, "点检内容插入失败");
    ErrorCode INSPECTION_DEL_FAIL = new ErrorCode(0_000_001_010, "点检内容剔除失败");
//    点检日志表
    ErrorCode INSPECTION_PROFILE_NOT_EXISTS = new ErrorCode(0_000_001_011, "点检日志表不存在");
//    缺陷库
    ErrorCode FLAWS_NOT_EXISTS = new ErrorCode(0_000_001_012, "缺陷库不存在");
//    设备状态记录
    ErrorCode SR_NOT_EXISTS = new ErrorCode(0_000_001_013, "设备状态记录不存在");
//     保养计划
    ErrorCode MAINTAIN_PLAN_NOT_EXISTS = new ErrorCode(0_000_001_014, "保养计划不存在");
    ErrorCode MAINTAIN_PLAN_UPDATE_ERROR = new ErrorCode(0_000_001_026, "保养计划存在内容，不允许修改保养设备地址");
//      保养内容
    ErrorCode MAINTAIN_DETAIL_NOT_EXISTS = new ErrorCode(0_000_001_015, "保养内容表不存在");
    ErrorCode MAINTAIN_MORE_REPLACE_ERROR = new ErrorCode(0_000_001_017, "此设备已存在定期更换计划");
    ErrorCode MAINTAIN_REFERTO_ERROR = new ErrorCode(0_000_001_020, "非特殊设备的保养内容不允许参考其他");
    ErrorCode MIANTAIN_DATA_ERROR = new ErrorCode(0_000_001_023,"该计划数据存在问题，同一设备相同条件存在多个定期更换计划");
    ErrorCode MAINTAIN_UPDATE_ERROR = new ErrorCode(0_000_001_024,"此项内容已被作为参考对象，只允许修改其周期");
    ErrorCode MAINTAIN_DELETE_ERROR = new ErrorCode(0_000_001_025,"此项内容已被作为参考对象，禁止删除");
//      保养日志
    ErrorCode MAINTAIN_PROFILE_NOT_EXISTS = new ErrorCode(0_000_001_016, "保养日志表不存在");
}
