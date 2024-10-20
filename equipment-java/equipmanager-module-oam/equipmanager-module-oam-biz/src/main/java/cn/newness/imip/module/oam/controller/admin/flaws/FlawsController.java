package cn.newness.imip.module.oam.controller.admin.flaws;

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
import cn.newness.imip.framework.common.util.object.BeanUtils;
import static cn.newness.imip.framework.common.pojo.CommonResult.success;

import cn.newness.imip.framework.excel.core.util.ExcelUtils;

import cn.newness.imip.framework.apilog.core.annotation.ApiAccessLog;
import static cn.newness.imip.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.newness.imip.module.oam.controller.admin.flaws.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.flaws.FlawsDO;
import cn.newness.imip.module.oam.service.FlawsService;

@Tag(name = "管理后台 - 缺陷库")
@RestController
@RequestMapping("/oam/flaws")
@Validated
public class FlawsController {

    @Resource
    private FlawsService flawsService;

    @PostMapping("/create")
    @Operation(summary = "创建缺陷库")
    @PreAuthorize("@ss.hasPermission('oam:flaws:create')")
    public CommonResult<String> createFlaws(@Valid @RequestBody FlawsSaveReqVO createReqVO) {
        return success(flawsService.createFlaws(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新缺陷库")
    @PreAuthorize("@ss.hasPermission('oam:flaws:update')")
    public CommonResult<Boolean> updateFlaws(@Valid @RequestBody FlawsSaveReqVO updateReqVO) {
        flawsService.updateFlaws(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除缺陷库")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('oam:flaws:delete')")
    public CommonResult<Boolean> deleteFlaws(@RequestParam("id") String id) {
        flawsService.deleteFlaws(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得缺陷库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('oam:flaws:query')")
    public CommonResult<FlawsRespVO> getFlaws(@RequestParam("id") String id) {
        FlawsDO flaws = flawsService.getFlaws(id);
        return success(BeanUtils.toBean(flaws, FlawsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得缺陷库分页")
    @PreAuthorize("@ss.hasPermission('oam:flaws:query')")
    public CommonResult<PageResult<FlawsRespVO>> getFlawsPage(@Valid FlawsPageReqVO pageReqVO) {
        PageResult<FlawsRespVO> pageResult = flawsService.getFlawsPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出缺陷库 Excel")
    @PreAuthorize("@ss.hasPermission('oam:flaws:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportFlawsExcel(@Valid FlawsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<FlawsRespVO> list = flawsService.getFlawsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "缺陷库.xls", "数据", FlawsRespVO.class,list);
    }

}