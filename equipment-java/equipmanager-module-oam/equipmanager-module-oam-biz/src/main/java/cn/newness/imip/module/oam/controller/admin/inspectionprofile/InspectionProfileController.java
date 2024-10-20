package cn.newness.imip.module.oam.controller.admin.inspectionprofile;

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

import cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.inspectionprofile.InspectionProfileDO;
import cn.newness.imip.module.oam.service.InspectionProfileService;

@Tag(name = "管理后台 - 点检日志表")
@RestController
@RequestMapping("/oam/inspection-profile")
@Validated
public class InspectionProfileController {

    @Resource
    private InspectionProfileService inspectionProfileService;

    @GetMapping("/get")
    @Operation(summary = "获得点检日志表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('oam:inspection-profile:query')")
    public CommonResult<InspectionProfileRespVO> getInspectionProfile(@RequestParam("id") String id) {
        InspectionProfileDO inspectionProfile = inspectionProfileService.getInspectionProfile(id);
        return success(BeanUtils.toBean(inspectionProfile, InspectionProfileRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得点检日志表分页")
    @PreAuthorize("@ss.hasPermission('oam:inspection-profile:query')")
    public CommonResult<PageResult<InspectionProfileRespVO>> getInspectionProfilePage(@Valid InspectionProfilePageReqVO pageReqVO) {
        PageResult<InspectionProfileRespVO> pageResult = inspectionProfileService.getInspectionProfilePage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出点检日志表 Excel")
    @PreAuthorize("@ss.hasPermission('oam:inspection-profile:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportInspectionProfileExcel(@Valid InspectionProfilePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<InspectionProfileRespVO> list = inspectionProfileService.getInspectionProfilePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "点检日志表.xls", "数据", InspectionProfileRespVO.class,list);
    }

}