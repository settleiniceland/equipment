package cn.newness.imip.module.oam.controller.admin.inspectplan;

import cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.InspectionSubstancePageReqVO;
import cn.newness.imip.module.oam.controller.admin.inspectionsubstance.vo.InspectionSubstanceRespVO;
import cn.newness.imip.module.oam.dal.dataobject.inspectionsubstancemap.InspectionSubstanceMapDO;
import cn.newness.imip.module.oam.service.InspectionSubstanceService;
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

import cn.newness.imip.module.oam.controller.admin.inspectplan.vo.*;
import cn.newness.imip.module.oam.dal.dataobject.inspectplan.InspectplanDO;
import cn.newness.imip.module.oam.service.InspectplanService;

@Tag(name = "管理后台 - 点检计划表")
@RestController
@RequestMapping("/oam/inspectplan")
@Validated
public class InspectplanController {

    @Resource
    private InspectplanService inspectplanService;
    @Resource
    private InspectionSubstanceService inspectionSubstanceService;

    @PostMapping("/create")
    @Operation(summary = "创建点检计划表")
    @PreAuthorize("@ss.hasPermission('oam:inspectplan:create')")
    public CommonResult<String> createInspectplan(@Valid @RequestBody InspectplanSaveReqVO createReqVO) {
        return success(inspectplanService.createInspectplan(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新点检计划表")
    @PreAuthorize("@ss.hasPermission('oam:inspectplan:update')")
    public CommonResult<Boolean> updateInspectplan(@Valid @RequestBody InspectplanSaveReqVO updateReqVO) {
        inspectplanService.updateInspectplan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除点检计划表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('oam:inspectplan:delete')")
    public CommonResult<Boolean> deleteInspectplan(@RequestParam("id") String id,@RequestParam("status") Integer status) {
        inspectplanService.deleteInspectplan(id,status);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得点检计划表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('oam:inspectplan:query')")
    public CommonResult<InspectplanRespVO> getInspectplan(@RequestParam("id") String id) {
        InspectplanDO inspectplan = inspectplanService.getInspectplan(id);
        return success(BeanUtils.toBean(inspectplan, InspectplanRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得点检计划表分页")
    @PreAuthorize("@ss.hasPermission('oam:inspectplan:query')")
    public CommonResult<PageResult<InspectplanRespVO>> getInspectplanPage(@Valid InspectplanPageReqVO pageReqVO) {
        PageResult<InspectplanDO> pageResult = inspectplanService.getInspectplanPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InspectplanRespVO.class));
    }

//    @GetMapping("/export-excel")
//    @Operation(summary = "导出点检计划表 Excel")
//    @PreAuthorize("@ss.hasPermission('oam:inspectplan:export')")
//    @ApiAccessLog(operateType = EXPORT)
//    public void exportInspectplanExcel(@Valid InspectplanPageReqVO pageReqVO,
//              HttpServletResponse response) throws IOException {
//        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
//        List<InspectplanDO> list = inspectplanService.getInspectplanPage(pageReqVO).getList();
//        // 导出 Excel
//        ExcelUtils.write(response, "点检计划表.xls", "数据", InspectplanRespVO.class,
//                        BeanUtils.toBean(list, InspectplanRespVO.class));
//    }

    @PostMapping("/addSubstances")
    @Operation(summary = "为某点检计划添加点检内容项")
    @PreAuthorize("@ss.hasPermission('oam:inspectplan:update')")
    public void addSubstancesToPlan(@RequestParam String planId,
                                   @RequestBody List<String> substanceIds) {
        inspectplanService.addSubstancesToPlan(planId,substanceIds);
    }

    @GetMapping("/delSubstance")
    @Operation(summary = "为某点检计划移除点检内容项")
    @PreAuthorize("@ss.hasPermission('oam:inspectplan:delete')")
    public void delSubstance(@RequestParam("planId")String planId,
                             @RequestParam("substanceId")String substanceId) {
        InspectionSubstanceMapDO mapDO=new InspectionSubstanceMapDO();
        mapDO.setInspectionplanId(planId);
        mapDO.setInspectionSubstanceId(substanceId);
        inspectplanService.delSubstanceForPlan(mapDO);
    }
    @GetMapping("/export-plan-detail")
    @Operation(summary = "导出某点检计划表")
    @PreAuthorize("@ss.hasPermission('oam:inspectplan:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportInspectplanDetailExcel(@Valid InspectionSubstancePageReqVO substancePageReqVO,
                                                    HttpServletResponse response) throws IOException {
        List<InspectionSubstanceRespVO> substances = inspectionSubstanceService.getInspectionSubstanceByPlanIdForExcel(substancePageReqVO);
        Map<String,Integer> countTool=new HashMap<>();
        substances.forEach(substance->{
            if(countTool.get(substance.getEquipId()+"-"+substance.getDetails())==null){//没有值,新增
                countTool.put(substance.getEquipId()+"-"+substance.getDetails(),0);
            }else {//有值,计数增加
                countTool.put(substance.getEquipId()+"-"+substance.getDetails(),
                        countTool.get(substance.getEquipId()+"-"+substance.getDetails())+1);
                substance.setEquipName(
                        substance.getEquipName()+countTool.get(substance.getEquipId()+"-"+substance.getDetails())
                );
            }
        });
        //重新排序
        substances.sort(Comparator.comparing(InspectionSubstanceRespVO::getEquipName));
        // 导出 Excel
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("planName",substancePageReqVO.getPlanName());
        ExcelUtils.writeByModel(response,
                "点检计划详情.xlsx",
                InspectionSubstanceRespVO.class,
                dataMap,
                substances,"templates/excel/inspectPlanTemplates.xlsx",
                Collections.singletonList(0),
                Arrays.asList(2, 3, 4, 5));
    }

    @GetMapping("/list")
    @Operation(summary = "获得所有点检计划表")
    @PreAuthorize("@ss.hasPermission('oam:inspectplan:query')")
    public CommonResult<List<InspectplanRespVO>> getInspectplanList() {
        InspectplanPageReqVO pageReqVO = new InspectplanPageReqVO();
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<InspectplanDO> planList = inspectplanService.getInspectplanPage(pageReqVO).getList();
        return success(BeanUtils.toBean(planList, InspectplanRespVO.class));
    }
}