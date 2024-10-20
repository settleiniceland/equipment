package cn.newness.imip.module.property.controller.admin.manufacturer;

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
import cn.newness.imip.module.property.controller.admin.manufacturer.vo.*;
import cn.newness.imip.module.property.dal.dataobject.manufacturer.ManufacturerDO;
import cn.newness.imip.module.property.service.ManufacturerService;

@Tag(name = "管理后台 - 设备生产厂家信息")
@RestController
@RequestMapping("/property/manufacturer")
@Validated
public class ManufacturerController {

    @Resource
    private ManufacturerService manufacturerService;

    @PostMapping("/create")
    @Operation(summary = "创建设备生产厂家信息")
    @PreAuthorize("@ss.hasPermission('property:manufacturer:create')")
    public CommonResult<String> createManufacturer(@Valid @RequestBody ManufacturerSaveReqVO createReqVO) {
        return success(manufacturerService.createManufacturer(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备生产厂家信息")
    @PreAuthorize("@ss.hasPermission('property:manufacturer:update')")
    public CommonResult<Boolean> updateManufacturer(@Valid @RequestBody ManufacturerSaveReqVO updateReqVO) {
        manufacturerService.updateManufacturer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备生产厂家信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('property:manufacturer:delete')")
    public CommonResult<Boolean> deleteManufacturer(@RequestParam("id") String id) {
        manufacturerService.deleteManufacturer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备生产厂家信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('property:manufacturer:query')")
    public CommonResult<ManufacturerRespVO> getManufacturer(@RequestParam("id") String id) {
        ManufacturerDO manufacturer = manufacturerService.getManufacturer(id);
        return success(BeanUtils.toBean(manufacturer, ManufacturerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备生产厂家信息分页")
    @PreAuthorize("@ss.hasPermission('property:manufacturer:query')")
    public CommonResult<PageResult<ManufacturerRespVO>> getManufacturerPage(@Valid ManufacturerPageReqVO pageReqVO) {
        PageResult<ManufacturerDO> pageResult = manufacturerService.getManufacturerPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ManufacturerRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得所有状态为正常(0)的设备生产厂家信息")
    @PreAuthorize("@ss.hasPermission('property:equipmentprofile:create')")
    public CommonResult<List<ManufacturerRespVO>> getManufacturerList() {
        List<ManufacturerDO> allNormalManufacturer = manufacturerService.getAllNormalManufacturer();
        return success(BeanUtils.toBean(allNormalManufacturer, ManufacturerRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备生产厂家信息 Excel")
    @PreAuthorize("@ss.hasPermission('property:manufacturer:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportManufacturerExcel(@Valid ManufacturerPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ManufacturerRespVO> manufacturerPageForExport = manufacturerService.getManufacturerPageForExport(pageReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "设备生产厂家信息.xls", "数据", ManufacturerRespVO.class,manufacturerPageForExport);
    }

}