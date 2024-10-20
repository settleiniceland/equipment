package cn.newness.imip.module.property.controller.admin.equiptype;

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

import cn.newness.imip.module.property.controller.admin.equiptype.vo.*;
import cn.newness.imip.module.property.dal.dataobject.equiptype.EquiptypeDO;
import cn.newness.imip.module.property.service.EquiptypeService;

@Tag(name = "管理后台 - 设备类别")
@RestController
@RequestMapping("/property/equiptype")
@Validated
public class EquiptypeController {

    @Resource
    private EquiptypeService equiptypeService;

    @PostMapping("/create")
    @Operation(summary = "创建设备类别")
    @PreAuthorize("@ss.hasPermission('property:equiptype:create')")
    public CommonResult<String> createEquiptype(@Valid @RequestBody EquiptypeSaveReqVO createReqVO) {
        return success(equiptypeService.createEquiptype(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备类别")
    @PreAuthorize("@ss.hasPermission('property:equiptype:update')")
    public CommonResult<Boolean> updateEquiptype(@Valid @RequestBody EquiptypeSaveReqVO updateReqVO) {
        equiptypeService.updateEquiptype(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备类别")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('property:equiptype:delete')")
    public CommonResult<Boolean> deleteEquiptype(@RequestParam("id") String id) {
        equiptypeService.deleteEquiptype(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备类别")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('property:equiptype:query')")
    public CommonResult<EquiptypeRespVO> getEquiptype(@RequestParam("id") String id) {
        EquiptypeDO equiptype = equiptypeService.getEquiptype(id);
        return success(BeanUtils.toBean(equiptype, EquiptypeRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备类别列表")
    @PreAuthorize("@ss.hasPermission('property:equiptype:query')")
    public CommonResult<List<EquiptypeRespVO>> getEquiptypeList(@Valid EquiptypeListReqVO listReqVO) {
        List<EquiptypeDO> list = equiptypeService.getEquiptypeList(listReqVO);
        return success(BeanUtils.toBean(list, EquiptypeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备类别 Excel")
    @PreAuthorize("@ss.hasPermission('property:equiptype:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEquiptypeExcel(@Valid EquiptypeListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<EquiptypeRespVO> equiptypeListForExcel = equiptypeService.getEquiptypeListForExcel(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "设备类别.xls", "数据", EquiptypeRespVO.class,equiptypeListForExcel);
    }

}