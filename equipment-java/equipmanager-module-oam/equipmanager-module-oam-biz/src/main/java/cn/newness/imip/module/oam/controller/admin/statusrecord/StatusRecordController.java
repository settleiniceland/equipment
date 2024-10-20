package cn.newness.imip.module.oam.controller.admin.statusrecord;

import cn.newness.imip.module.oam.controller.admin.statusrecord.vo.StatusRecordPageReqVO;
import cn.newness.imip.module.oam.controller.admin.statusrecord.vo.StatusRecordRespVO;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import cn.newness.imip.framework.common.pojo.PageParam;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.pojo.CommonResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;
import static cn.newness.imip.framework.common.pojo.CommonResult.success;

import cn.newness.imip.framework.excel.core.util.ExcelUtils;

import cn.newness.imip.framework.apilog.core.annotation.ApiAccessLog;
import static cn.newness.imip.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.newness.imip.module.oam.dal.dataobject.statusrecord.StatusRecordDO;
import cn.newness.imip.module.oam.service.StatusRecordService;

@Tag(name = "管理后台 - 停机表")
@RestController
@RequestMapping("/oam/status/record")
@Validated
public class StatusRecordController {

    @Resource
    private StatusRecordService statusRecordService;

    @DeleteMapping("/delete")
    @Operation(summary = "删除停机表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('oam:stop:delete')")
    public CommonResult<Boolean> deleteStop(@RequestParam("id") String id) {
        statusRecordService.deleteStatusRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得停机表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('oam:stop:query')")
    public CommonResult<StatusRecordRespVO> getStop(@RequestParam("id") String id) {
        StatusRecordDO stop = statusRecordService.getStatusRecord(id);
        return success(BeanUtils.toBean(stop, StatusRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得停机表分页")
    @PreAuthorize("@ss.hasPermission('oam:stop:query')")
    public CommonResult<PageResult<StatusRecordRespVO>> getStopPage(@Valid StatusRecordPageReqVO pageReqVO) {
        PageResult<StatusRecordRespVO> pageResult = statusRecordService.getStatusRecordPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出停机表 Excel")
    @PreAuthorize("@ss.hasPermission('oam:stop:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportStopExcel(@Valid StatusRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<StatusRecordRespVO> list = statusRecordService.getStatusRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "停机表.xls", "数据", StatusRecordRespVO.class,list);
    }

}