package cn.newness.imip.module.oam.controller.app.inspectionprofile;

import cn.newness.imip.framework.common.pojo.CommonResult;
import cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo.InspectionProfileRespVO;
import cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo.InspectionProfileSaveReqVO;
import cn.newness.imip.module.oam.service.InspectionProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.newness.imip.framework.common.pojo.CommonResult.success;

/**
 * @author machuran
 * @date 9/7/2024
 * @time 2:14 PM
 * @Description
 */
@Tag(name = "移动端 - 点检日志表")
@RestController
@RequestMapping("/oam/inspection-profile")
@Validated
public class AppInspectionProfileController {
    @Resource
    private InspectionProfileService inspectionProfileService;

    @PostMapping("/addEquipInspectionProfileList")
    @Operation(summary = "批量添加点检计划的一个设备对象的点检日志")
    public void addEquipInspectionProfileList(@RequestBody List<InspectionProfileSaveReqVO> profileVOList){
        inspectionProfileService.addProfileList(profileVOList);
    }

    @GetMapping("/getPlanNewestExecuteTime")
    @Operation(summary = "获取最新轮次计划执行的点检时间")
    @PreAuthorize("@ss.hasPermission('oam:inspection-profile:query')")
    public CommonResult<InspectionProfileRespVO> getPlanNewestExecuteTime(@RequestParam("planId") String planId){
        InspectionProfileRespVO ipResp = inspectionProfileService.getPlanNewestExecuteTime(planId);
        return success(ipResp);
    }
}
