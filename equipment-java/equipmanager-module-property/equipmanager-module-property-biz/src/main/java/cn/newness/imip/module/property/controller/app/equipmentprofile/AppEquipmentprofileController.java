package cn.newness.imip.module.property.controller.app.equipmentprofile;

import cn.newness.imip.framework.common.pojo.CommonResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;
import cn.newness.imip.module.property.controller.admin.equipmentprofile.vo.EquipmentprofileListReqVO;
import cn.newness.imip.module.property.controller.admin.equipmentprofile.vo.EquipmentprofileRespVO;
import cn.newness.imip.module.property.dal.dataobject.equipmentprofile.EquipmentprofileDO;
import cn.newness.imip.module.property.service.EquipmentprofileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.newness.imip.framework.common.pojo.CommonResult.success;

/**
 * @author machuran
 * @date 9/5/2024
 * @time 10:31 AM
 * @Description
 */
@Tag(name = "移动端 - 设备档案数据")
@RestController
@RequestMapping("/property/equipmentprofile")
@Validated
public class AppEquipmentprofileController {
    @Resource
    private EquipmentprofileService equipmentprofileService;
    @GetMapping("/list")
    @Operation(summary = "获得设备档案数据列表")
    public CommonResult<List<EquipmentprofileRespVO>> getEquipmentprofileList(@RequestParam("locationId") String locationId,
                                                                              @RequestParam(value = "equipId",required = false) String equipId) {
        EquipmentprofileListReqVO reqVO = new EquipmentprofileListReqVO();
        reqVO.setLocationId(locationId);
        reqVO.setEquipId(equipId);
        List<EquipmentprofileDO> list = equipmentprofileService.getEquipmentprofileList(reqVO);
        return success(BeanUtils.toBean(list, EquipmentprofileRespVO.class));
    }
}
