package cn.newness.imip.module.oam.controller.app.inspectplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author machuran
 * @date 9/2/2024
 * @time 10:15 AM
 * @Description
 */
@Schema(description = "移动端 - 点检计划表")
@Data
public class InspectplanAppVO {
    @Schema(description = "主键id", example = "30856")
    private String id;

    @Schema(description = "当前是否到达点检时间：true到达，false未到达")
    private Boolean timeUp;

    @Schema(description = "点检类型【字典：1普通点检；2重点点检；】", example = "2")
    private Integer inspectionType;

    @Schema(description = "点检周期【单位h】")
    private BigDecimal inspectionCycle;

    @Schema(description = "点检计划名称", example = "张三")
    private String name;

    @Schema(description = "设备区域id", example = "8544")
    private String equiplocationId;

    @Schema(description = "设备区域名称", example = "王五")
    private String equiplocationName;

    @Schema(description = "备注")
    private String detail;
}
