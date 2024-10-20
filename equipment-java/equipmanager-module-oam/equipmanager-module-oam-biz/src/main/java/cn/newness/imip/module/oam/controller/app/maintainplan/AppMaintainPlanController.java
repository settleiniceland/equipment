package cn.newness.imip.module.oam.controller.app.maintainplan;

import cn.newness.imip.framework.common.pojo.CommonResult;
import cn.newness.imip.framework.common.pojo.PageParam;
import cn.newness.imip.module.oam.controller.admin.maintainplan.vo.MaintainPlanPageReqVO;
import cn.newness.imip.module.oam.controller.admin.maintainplan.vo.MaintainPlanRespVO;
import cn.newness.imip.module.oam.service.MaintainPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.newness.imip.framework.common.pojo.CommonResult.success;

/**
 * @author machuran
 * @date 10/18/2024
 * @time 5:04 PM
 * @Description
 */
@Tag(name = "移动端 - 保养计划")
@RestController
@RequestMapping("/oam/maintain-plan")
@Validated
public class AppMaintainPlanController {
    @Resource
    private MaintainPlanService maintainPlanService;

    @GetMapping("/list")
    @Operation(summary = "获得保养计划列表")
    @PreAuthorize("@ss.hasPermission('oam:maintain-plan:query')")
    public CommonResult<List<MaintainPlanRespVO>> getList(@Valid MaintainPlanPageReqVO pageReqVO){
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        return success(maintainPlanService.getMaintainPlanPage(pageReqVO).getList());
    }
}
