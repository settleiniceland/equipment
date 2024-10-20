package cn.newness.imip.module.oam.controller.app.inspectionprofile;

import cn.newness.imip.module.oam.controller.admin.inspectionprofile.vo.InspectionProfileSaveReqVO;
import cn.newness.imip.module.oam.service.InspectionProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Operation(summary = "批量添加一整个计划的点检日志")
    public void addEquipInspectionProfileList(@RequestBody List<InspectionProfileSaveReqVO> profileVOList){
        inspectionProfileService.addProfileList(profileVOList);
    }
}
