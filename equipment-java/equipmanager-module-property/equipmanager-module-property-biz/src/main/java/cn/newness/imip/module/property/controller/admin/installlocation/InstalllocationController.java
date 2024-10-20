package cn.newness.imip.module.property.controller.admin.installlocation;

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

import cn.newness.imip.framework.common.pojo.CommonResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;
import static cn.newness.imip.framework.common.pojo.CommonResult.success;

import cn.newness.imip.framework.excel.core.util.ExcelUtils;

import cn.newness.imip.framework.apilog.core.annotation.ApiAccessLog;
import static cn.newness.imip.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.newness.imip.module.property.controller.admin.installlocation.vo.*;
import cn.newness.imip.module.property.dal.dataobject.installlocation.InstalllocationDO;
import cn.newness.imip.module.property.service.InstalllocationService;

@Tag(name = "管理后台 - 设备安装位置")
@RestController
@RequestMapping("/property/installlocation")
@Validated
public class InstalllocationController {

    @Resource
    private InstalllocationService installlocationService;

    @PostMapping("/create")
    @Operation(summary = "创建设备安装位置")
    @PreAuthorize("@ss.hasPermission('property:installlocation:create')")
    public CommonResult<String> createInstalllocation(@Valid @RequestBody InstalllocationSaveReqVO createReqVO) {
        return success(installlocationService.createInstalllocation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备安装位置")
    @PreAuthorize("@ss.hasPermission('property:installlocation:update')")
    public CommonResult<Boolean> updateInstalllocation(@Valid @RequestBody InstalllocationSaveReqVO updateReqVO) {
        installlocationService.updateInstalllocation(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备安装位置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('property:installlocation:delete')")
    public CommonResult<Boolean> deleteInstalllocation(@RequestParam("id") String id) {
        installlocationService.deleteInstalllocation(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备安装位置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('property:installlocation:query')")
    public CommonResult<InstalllocationRespVO> getInstalllocation(@RequestParam("id") String id) {
        InstalllocationDO installlocation = installlocationService.getInstalllocation(id);
        return success(BeanUtils.toBean(installlocation, InstalllocationRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备安装位置列表")
    @PreAuthorize("@ss.hasPermission('property:installlocation:query')")
    public CommonResult<List<InstalllocationRespVO>> getInstalllocationList(@Valid InstalllocationListReqVO listReqVO) {
        List<InstalllocationDO> list = installlocationService.getInstalllocationList(listReqVO);
        return success(BeanUtils.toBean(list, InstalllocationRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备安装位置 Excel")
    @PreAuthorize("@ss.hasPermission('property:installlocation:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportInstalllocationExcel(@Valid InstalllocationListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<InstalllocationRespVO> listForExcel = installlocationService.getInstalllocationListForExcel(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "设备安装位置.xls", "数据", InstalllocationRespVO.class,listForExcel);
    }

}