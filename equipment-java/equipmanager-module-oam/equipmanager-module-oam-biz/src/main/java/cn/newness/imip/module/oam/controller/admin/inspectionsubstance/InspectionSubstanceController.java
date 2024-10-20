package cn.newness.imip.module.oam.controller.admin.inspectionsubstance;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.newness.imip.framework.common.pojo.PageParam;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.pojo.CommonResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;
import static cn.newness.imip.framework.common.pojo.CommonResult.success;

import cn.newness.imip.framework.excel.core.util.ExcelUtils;

import cn.newness.imip.framework.apilog.core.annotation.ApiAccessLog;
import static cn.newness.imip.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.inspectionsubstance.InspectionSubstanceDO;
import cn.newness.imip.module.oam.service.InspectionSubstanceService;

@Tag(name = "管理后台 - 点检内容")
@RestController
@RequestMapping("/oam/inspection-substance")
@Validated
public class InspectionSubstanceController {

    @Resource
    private InspectionSubstanceService inspectionSubstanceService;

    @PostMapping("/create")
    @Operation(summary = "创建点检内容")
    @PreAuthorize("@ss.hasPermission('oam:inspection-substance:create')")
    public CommonResult<String> createInspectionSubstance(@Valid @RequestBody InspectionSubstanceSaveReqVO createReqVO) {
        return success(inspectionSubstanceService.createInspectionSubstance(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新点检内容")
    @PreAuthorize("@ss.hasPermission('oam:inspection-substance:update')")
    public CommonResult<Boolean> updateInspectionSubstance(@Valid @RequestBody InspectionSubstanceSaveReqVO updateReqVO) {
        inspectionSubstanceService.updateInspectionSubstance(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除点检内容")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('oam:inspection-substance:delete')")
    public CommonResult<Boolean> deleteInspectionSubstance(@RequestParam("id") String id) {
        inspectionSubstanceService.deleteInspectionSubstance(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得点检内容")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('oam:inspection-substance:query')")
    public CommonResult<InspectionSubstanceRespVO> getInspectionSubstance(@RequestParam("id") String id) {
        InspectionSubstanceDO inspectionSubstance = inspectionSubstanceService.getInspectionSubstance(id);
        return success(BeanUtils.toBean(inspectionSubstance, InspectionSubstanceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得点检内容分页")
    @PreAuthorize("@ss.hasPermission('oam:inspection-substance:query')")
    public CommonResult<PageResult<InspectionSubstanceRespVO>> getInspectionSubstancePage(@Valid InspectionSubstancePageReqVO pageReqVO) {
        PageResult<InspectionSubstanceDO> pageResult = inspectionSubstanceService.getInspectionSubstancePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InspectionSubstanceRespVO.class));
    }

    @GetMapping("/pageByPlanId")
    @Operation(summary = "分页查询某计划的点检内容")
    @PreAuthorize("@ss.hasPermission('oam:inspection-substance:query')")
    public CommonResult<PageResult<InspectionSubstanceRespVO>> getInspectionSubstancePageByPlanId(@Valid InspectionSubstancePageReqVO pageReqVO) {
        PageResult<InspectionSubstanceRespVO> pageResult = inspectionSubstanceService.getInspectionSubstancePageByPlanId(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/addPageByPlanId")
    @Operation(summary = "分页查询某计划之外的点检内容")
    @PreAuthorize("@ss.hasPermission('oam:inspection-substance:query')")
    public CommonResult<PageResult<InspectionSubstanceRespVO>> getInspectionSubstanceAddPageByPlanId(@Valid InspectionSubstancePageReqVO pageReqVO) {
        PageResult<InspectionSubstanceRespVO> pageResult = inspectionSubstanceService.getInspectionSubstanceAddPageByPlanId(pageReqVO);
        return success(pageResult);
    }

//    @GetMapping("/export-excel")
//    @Operation(summary = "导出点检内容 Excel")
//    @PreAuthorize("@ss.hasPermission('oam:inspection-substance:export')")
//    @ApiAccessLog(operateType = EXPORT)
//    public void exportInspectionSubstanceExcel(@Valid InspectionSubstancePageReqVO pageReqVO,
//              HttpServletResponse response) throws IOException {
//        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
//        List<InspectionSubstanceDO> list = inspectionSubstanceService.getInspectionSubstancePage(pageReqVO).getList();
//        // 导出 Excel
//        ExcelUtils.write(response, "点检内容.xls", "数据", InspectionSubstanceRespVO.class,
//                        BeanUtils.toBean(list, InspectionSubstanceRespVO.class));
//    }

}