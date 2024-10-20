package cn.newness.imip.module.oam.controller.admin.maintaindetail.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.newness.imip.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import static cn.newness.imip.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 保养内容表分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaintainDetailPageReqVO extends PageParam {

    @Schema(description = "主键id", example = "23200")
    private String id;

    @Schema(description = "保养计划id", example = "15928")
    private String equipMaintainPlanId;

    @Schema(description = "保养计划名称", example = "赵六")
    private String equipMaintainPlanName;

    @Schema(description = "设备id", example = "11594")
    private String equipId;

    @Schema(description = "是否特殊设备【字典：0否；1是】")
    private Integer isSpecial;

    @Schema(description = "设备档案id【只有当is_special值为1时才有此值】")
    private String equipprofileId;

    @Schema(description = "是否参照其他非特殊设备保养内容【0否，1是】")
    private Integer isReferto;

    @Schema(description = "内容参照对象id")
    private String refertoId;

    @Schema(description = "设备名称", example = "王五")
    private String equipName;

    @Schema(description = "设备规格")
    private String equipSpecification;

    @Schema(description = "保养周期")
    private BigDecimal maintainCycle;

    @Schema(description = "是否更换自身【字典：0否；1是】")
    private Integer replaceSelf;

    @Schema(description = "保养内容")
    private String details;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "更新者")
    private String updater;

    @Schema(description = "更新时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] updateTime;

}