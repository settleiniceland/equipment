package cn.newness.imip.module.oam.controller.app.inspectionsubstance;

import cn.newness.imip.framework.common.pojo.CommonResult;
import cn.newness.imip.framework.common.pojo.PageParam;
import cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.InspectionSubstancePageReqVO;
import cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.InspectionSubstanceRespVO;
import cn.newness.imip.module.oam.service.InspectionSubstanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.newness.imip.framework.common.pojo.CommonResult.success;

/**
 * @author machuran
 * @date 9/3/2024
 * @time 3:53 PM
 * @Description
 */
@Tag(name = "移动端 - 点检内容")
@RestController
@RequestMapping("/oam/inspection-substance")
@Validated
public class AppInspectionSubstanceController {
    @Resource
    private InspectionSubstanceService inspectionSubstanceService;

    @GetMapping("/pageByPlanId")
    @Operation(summary = "查询某计划的所有点检内容")
    public CommonResult<List<InspectionSubstanceRespVO>> getInspectionSubstancePageByPlanId(@RequestParam("id")String id) {
        InspectionSubstancePageReqVO reqVO = new InspectionSubstancePageReqVO();
        reqVO.setPlanId(id);
        reqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<InspectionSubstanceRespVO> substanceRespVOList = inspectionSubstanceService.getInspectionSubstancePageByPlanId(reqVO).getList();
        return success(substanceRespVOList);
    }
}
