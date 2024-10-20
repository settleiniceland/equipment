package cn.newness.imip.module.oam.controller.app.maintaindetail;

import cn.newness.imip.framework.common.pojo.CommonResult;
import cn.newness.imip.framework.common.pojo.PageParam;
import cn.newness.imip.module.oam.controller.admin.maintaindetail.vo.MaintainDetailPageReqVO;
import cn.newness.imip.module.oam.controller.admin.maintaindetail.vo.MaintainDetailRespVO;
import cn.newness.imip.module.oam.service.MaintainDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.newness.imip.framework.common.pojo.CommonResult.success;

/**
 * @author machuran
 * @date 10/19/2024
 * @time 10:30 AM
 * @Description
 */
@Tag(name = "移动端 - 保养计划详情")
@RestController
@RequestMapping("/oam/maintain-detail")
@Validated
public class AppMaintainDetailController {

    @Resource
    private MaintainDetailService maintainDetailService;

    @GetMapping("/list")
    @Operation(summary = "根据条件获取保养内容list")
    @PreAuthorize("@ss.hasPermission('oam:maintain-detail:query')")
    public CommonResult<List<MaintainDetailRespVO>> getMaintainDetailList(@Valid MaintainDetailPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        return success(maintainDetailService.getMaintainDetailPage(pageReqVO).getList());
    }
}
