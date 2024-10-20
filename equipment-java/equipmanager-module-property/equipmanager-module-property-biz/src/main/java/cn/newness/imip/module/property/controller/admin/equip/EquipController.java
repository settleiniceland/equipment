package cn.newness.imip.module.property.controller.admin.equip;

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

import cn.newness.imip.module.property.controller.admin.equip.vo.*;
import cn.newness.imip.module.property.dal.dataobject.equip.EquipDO;
import cn.newness.imip.module.property.service.EquipService;

@Tag(name = "管理后台 - 设备表")
@RestController
@RequestMapping("/property/equip")
@Validated
public class EquipController {

    @Resource
    private EquipService equipService;

    @PostMapping("/create")
    @Operation(summary = "创建设备表")
    @PreAuthorize("@ss.hasPermission('property:equip:create')")
    public CommonResult<String> createEquip(@Valid @RequestBody EquipSaveReqVO createReqVO) {
        return success(equipService.createEquip(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备表")
    @PreAuthorize("@ss.hasPermission('property:equip:update')")
    public CommonResult<Boolean> updateEquip(@Valid @RequestBody EquipSaveReqVO updateReqVO) {
        equipService.updateEquip(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('property:equip:delete')")
    public CommonResult<Boolean> deleteEquip(@RequestParam("id") String id) {
        equipService.deleteEquip(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('property:equip:query')")
    public CommonResult<EquipRespVO> getEquip(@RequestParam("id") String id) {
        EquipDO equip = equipService.getEquip(id);
        return success(BeanUtils.toBean(equip, EquipRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备表列表")
    @PreAuthorize("@ss.hasPermission('property:equip:query')")
    public CommonResult<List<EquipRespVO>> getEquipList(@Valid EquipListReqVO listReqVO) {
        List<EquipDO> list = equipService.getEquipList(listReqVO);
        return success(BeanUtils.toBean(list, EquipRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备表 Excel")
    @PreAuthorize("@ss.hasPermission('property:equip:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEquipExcel(@Valid EquipListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<EquipRespVO> equipListForExcel = equipService.getEquipListForExcel(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "设备表.xls", "数据", EquipRespVO.class,equipListForExcel);
    }

}