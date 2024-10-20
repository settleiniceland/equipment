package cn.newness.imip.module.oam.controller.admin.maintainplan;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;
import cn.newness.imip.framework.common.pojo.PageParam;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.pojo.CommonResult;
import static cn.newness.imip.framework.common.pojo.CommonResult.success;
import cn.newness.imip.framework.excel.core.util.ExcelUtils;
import cn.newness.imip.framework.apilog.core.annotation.ApiAccessLog;
import static cn.newness.imip.framework.apilog.core.enums.OperateTypeEnum.*;
import cn.newness.imip.module.oam.controller.admin.maintainplan.vo.*;
import cn.newness.imip.module.oam.service.MaintainPlanService;

@Tag(name = "管理后台 - 保养计划")
@RestController
@RequestMapping("/oam/maintain-plan")
@Validated
public class MaintainPlanController {

    @Resource
    private MaintainPlanService maintainPlanService;

    @PostMapping("/create")
    @Operation(summary = "创建保养计划")
    @PreAuthorize("@ss.hasPermission('oam:maintain-plan:create')")
    public CommonResult<String> createMaintainPlan(@Valid @RequestBody MaintainPlanSaveReqVO createReqVO) {
        return success(maintainPlanService.createMaintainPlan(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新保养计划")
    @PreAuthorize("@ss.hasPermission('oam:maintain-plan:update')")
    public CommonResult<Boolean> updateMaintainPlan(@Valid @RequestBody MaintainPlanSaveReqVO updateReqVO) {
        maintainPlanService.updateMaintainPlan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除保养计划")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('oam:maintain-plan:delete')")
    public CommonResult<Boolean> deleteMaintainPlan(@RequestParam("id") String id) {
        maintainPlanService.deleteMaintainPlan(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得保养计划")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('oam:maintain-plan:query')")
    public CommonResult<MaintainPlanRespVO> getMaintainPlan(@RequestParam("id") String id) {
        MaintainPlanRespVO planRespVO = maintainPlanService.getMaintainPlan(id);
        return success(planRespVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得保养计划分页")
    @PreAuthorize("@ss.hasPermission('oam:maintain-plan:query')")
    public CommonResult<PageResult<MaintainPlanRespVO>> getMaintainPlanPage(@Valid MaintainPlanPageReqVO pageReqVO) {
        PageResult<MaintainPlanRespVO> maintainPlanPage = maintainPlanService.getMaintainPlanPage(pageReqVO);
        return success(maintainPlanPage);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出保养计划 Excel")
    @PreAuthorize("@ss.hasPermission('oam:maintain-plan:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMaintainPlanExcel(@Valid MaintainPlanPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MaintainPlanRespVO> list = maintainPlanService.getMaintainPlanPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "保养计划.xls", "数据", MaintainPlanRespVO.class,list);
    }
}