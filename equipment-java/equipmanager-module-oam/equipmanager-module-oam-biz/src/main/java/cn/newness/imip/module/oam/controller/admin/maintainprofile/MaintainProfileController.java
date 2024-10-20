package cn.newness.imip.module.oam.controller.admin.maintainprofile;

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

import cn.newness.imip.module.oam.controller.admin.maintainprofile.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.maintainprofile.MaintainProfileDO;
import cn.newness.imip.module.oam.service.MaintainProfileService;

@Tag(name = "管理后台 - 保养日志表")
@RestController
@RequestMapping("/oam/maintain-profile")
@Validated
public class MaintainProfileController {

    @Resource
    private MaintainProfileService maintainProfileService;

    @PostMapping("/create")
    @Operation(summary = "创建保养日志表")
    @PreAuthorize("@ss.hasPermission('oam:maintain-profile:create')")
    public CommonResult<String> createMaintainProfile(@Valid @RequestBody MaintainProfileSaveReqVO createReqVO) {
        return success(maintainProfileService.createMaintainProfile(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新保养日志表")
    @PreAuthorize("@ss.hasPermission('oam:maintain-profile:update')")
    public CommonResult<Boolean> updateMaintainProfile(@Valid @RequestBody MaintainProfileSaveReqVO updateReqVO) {
        maintainProfileService.updateMaintainProfile(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除保养日志表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('oam:maintain-profile:delete')")
    public CommonResult<Boolean> deleteMaintainProfile(@RequestParam("id") String id) {
        maintainProfileService.deleteMaintainProfile(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得保养日志表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('oam:maintain-profile:query')")
    public CommonResult<MaintainProfileRespVO> getMaintainProfile(@RequestParam("id") String id) {
        MaintainProfileDO maintainProfile = maintainProfileService.getMaintainProfile(id);
        return success(BeanUtils.toBean(maintainProfile, MaintainProfileRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得保养日志表分页")
    @PreAuthorize("@ss.hasPermission('oam:maintain-profile:query')")
    public CommonResult<PageResult<MaintainProfileRespVO>> getMaintainProfilePage(@Valid MaintainProfilePageReqVO pageReqVO) {
        PageResult<MaintainProfileDO> pageResult = maintainProfileService.getMaintainProfilePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MaintainProfileRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出保养日志表 Excel")
    @PreAuthorize("@ss.hasPermission('oam:maintain-profile:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMaintainProfileExcel(@Valid MaintainProfilePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MaintainProfileDO> list = maintainProfileService.getMaintainProfilePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "保养日志表.xls", "数据", MaintainProfileRespVO.class,
                        BeanUtils.toBean(list, MaintainProfileRespVO.class));
    }

}