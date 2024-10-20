package cn.newness.imip.module.oam.controller.app.inspectplan;

import cn.newness.imip.framework.common.pojo.CommonResult;
import cn.newness.imip.module.oam.controller.app.inspectplan.vo.InspectplanAppVO;
import cn.newness.imip.module.oam.service.InspectplanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.newness.imip.framework.common.pojo.CommonResult.success;

/**移动APP端接口
 * @author machuran
 * @date 9/2/2024
 * @time 10:03 AM
 * @Description
 */
@Tag(name = "APP端 - 点检计划表")
@RestController
@RequestMapping("/oam/inspectplan")
@Validated
public class AppInspectplanController {
    @Resource
    private InspectplanService inspectplanService;
    @PostMapping("/list")
    @Operation(summary = "获得点检计划列表")
    public CommonResult<List<InspectplanAppVO>> getInspectplanPage(@Valid InspectplanAppVO inspectplanAppVO) {
        List<InspectplanAppVO> inspectplanAppVOList = inspectplanService.getInspectplanListForApp(inspectplanAppVO);
        return success(inspectplanAppVOList);
    }
}
