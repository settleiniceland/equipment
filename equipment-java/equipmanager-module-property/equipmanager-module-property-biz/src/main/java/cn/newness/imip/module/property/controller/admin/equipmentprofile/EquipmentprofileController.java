package cn.newness.imip.module.property.controller.admin.equipmentprofile;

import cn.newness.imip.module.system.api.dept.DeptApi;
import cn.newness.imip.module.system.api.dept.dto.DeptRespDTO;
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
import cn.newness.imip.module.property.controller.admin.equipmentprofile.vo.*;
import cn.newness.imip.module.property.dal.dataobject.equipmentprofile.EquipmentprofileDO;
import cn.newness.imip.module.property.service.EquipmentprofileService;

@Tag(name = "管理后台 - 设备档案数据")
@RestController
@RequestMapping("/property/equipmentprofile")
@Validated
public class EquipmentprofileController {

    @Resource
    private EquipmentprofileService equipmentprofileService;
    @Resource
    private DeptApi deptApi;

    @PostMapping("/create")
    @Operation(summary = "创建设备档案数据")
    @PreAuthorize("@ss.hasPermission('property:equipmentprofile:create')")
    public CommonResult<String> createEquipmentprofile(@Valid @RequestBody EquipmentprofileSaveReqVO createReqVO) {
        return success(equipmentprofileService.createEquipmentprofile(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备档案数据")
    @PreAuthorize("@ss.hasPermission('property:equipmentprofile:update')")
    public CommonResult<Boolean> updateEquipmentprofile(@Valid @RequestBody EquipmentprofileSaveReqVO updateReqVO) {
        equipmentprofileService.updateEquipmentprofile(updateReqVO);
        return success(true);
    }

    @PutMapping("/update/status")
    @Operation(summary = "更新设备档案状态")
    @PreAuthorize("@ss.hasPermission('property:equipmentprofile:update')")
    public CommonResult<Boolean> updateEquipmentprofileStatus(@Valid @RequestBody EquipmentprofileStatusSaveReqVO statusUpdateReqVO) {
        equipmentprofileService.updateEquipmentprofileStatus(statusUpdateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备档案数据")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('property:equipmentprofile:delete')")
    public CommonResult<Boolean> deleteEquipmentprofile(@RequestParam("id") String id,
                                                        @RequestParam("code") String code,
                                                        @RequestParam("equipAttribute") Integer equipAttribute) {
        equipmentprofileService.deleteEquipmentprofile(id,code,equipAttribute);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备档案数据")
    @Parameter(name = "id", description = "主键id", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('property:equipmentprofile:query')")
    public CommonResult<EquipmentprofileRespVO> getEquipmentprofile(@RequestParam("id") String id) {
        EquipmentprofileDO equipmentprofile = equipmentprofileService.getEquipmentprofile(id);
        return success(BeanUtils.toBean(equipmentprofile, EquipmentprofileRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备档案数据列表")
    @PreAuthorize("@ss.hasPermission('property:equipmentprofile:query')")
    public CommonResult<List<EquipmentprofileRespVO>> getEquipmentprofileList(@Valid EquipmentprofileListReqVO listReqVO) {
        List<EquipmentprofileDO> list = equipmentprofileService.getEquipmentprofileList(listReqVO);
        return success(BeanUtils.toBean(list, EquipmentprofileRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备档案数据 Excel")
    @PreAuthorize("@ss.hasPermission('property:equipmentprofile:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEquipmentprofileExcel(@Valid EquipmentprofileListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<EquipmentprofileRespVO> equipmentprofileRespVOList=equipmentprofileService.getEquipmentprofileListForExcel(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "设备档案数据.xls", "数据", EquipmentprofileRespVO.class,equipmentprofileRespVOList);
    }

    @GetMapping("/getAllDeptList")
    @Operation(summary = "获取部门列表[全部]")
    public CommonResult<List<DeptRespDTO>> getAllDeptList() {
        return success(deptApi.getDeptList());
    }

    @GetMapping("/getListByEquipId")
    @Operation(summary = "根据设备id获得设备档案数据列表")
    public CommonResult<List<EquipmentprofileRespVO>> getEquipmentprofileListByEquipId(@RequestParam("equipId") String equipId) {
        return success(equipmentprofileService.getEquipmentprofileListByEquipId(equipId));
    }
}