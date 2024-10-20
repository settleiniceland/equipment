package cn.newness.imip.module.oam.controller.admin.maintaindetail;

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
import cn.newness.imip.module.oam.controller.admin.maintaindetail.vo.*;
import cn.newness.imip.module.oam.service.MaintainDetailService;

@Tag(name = "管理后台 - 保养内容表")
@RestController
@RequestMapping("/oam/maintain-detail")
@Validated
public class MaintainDetailController {

    @Resource
    private MaintainDetailService maintainDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建保养内容表")
    @PreAuthorize("@ss.hasPermission('oam:maintain-detail:create')")
    public CommonResult<String> createMaintainDetail(@Valid @RequestBody MaintainDetailSaveReqVO createReqVO) {
        return success(maintainDetailService.createMaintainDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新保养内容表")
    @PreAuthorize("@ss.hasPermission('oam:maintain-detail:update')")
    public CommonResult<Boolean> updateMaintainDetail(@Valid @RequestBody MaintainDetailSaveReqVO updateReqVO) {
        maintainDetailService.updateMaintainDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除保养内容表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('oam:maintain-detail:delete')")
    public CommonResult<Boolean> deleteMaintainDetail(@RequestParam("id") String id) {
        maintainDetailService.deleteMaintainDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得保养内容表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('oam:maintain-detail:query')")
    public CommonResult<MaintainDetailRespVO> getMaintainDetail(@RequestParam("id") String id) {
        return success(maintainDetailService.getMaintainDetail(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得保养内容表分页")
    @PreAuthorize("@ss.hasPermission('oam:maintain-detail:query')")
    public CommonResult<PageResult<MaintainDetailRespVO>> getMaintainDetailPage(@Valid MaintainDetailPageReqVO pageReqVO) {
        return success(maintainDetailService.getMaintainDetailPage(pageReqVO));
    }

    @GetMapping("/list")
    @Operation(summary = "根据条件获取保养内容list")
    @PreAuthorize("@ss.hasPermission('oam:maintain-detail:query')")
    public CommonResult<List<MaintainDetailRespVO>> getMaintainDetailList(@Valid MaintainDetailPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        return success(maintainDetailService.getMaintainDetailPage(pageReqVO).getList());
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出保养内容表 Excel")
    @PreAuthorize("@ss.hasPermission('oam:maintain-detail:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMaintainDetailExcel(@Valid MaintainDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MaintainDetailRespVO> list = maintainDetailService.getMaintainDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "保养内容表.xls", "数据", MaintainDetailRespVO.class,list);
    }

}