package cn.newness.imip.module.bpm.controller.admin.definition;

import cn.newness.imip.framework.common.pojo.CommonResult;
import cn.newness.imip.framework.common.pojo.PageResult;
import cn.newness.imip.framework.common.util.object.BeanUtils;
import cn.newness.imip.module.bpm.controller.admin.definition.vo.listener.BpmProcessListenerPageReqVO;
import cn.newness.imip.module.bpm.controller.admin.definition.vo.listener.BpmProcessListenerRespVO;
import cn.newness.imip.module.bpm.controller.admin.definition.vo.listener.BpmProcessListenerSaveReqVO;
import cn.newness.imip.module.bpm.dal.dataobject.definition.BpmProcessListenerDO;
import cn.newness.imip.module.bpm.service.definition.BpmProcessListenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.newness.imip.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - BPM 流程监听器")
@RestController
@RequestMapping("/bpm/process-listener")
@Validated
public class BpmProcessListenerController {

    @Resource
    private BpmProcessListenerService processListenerService;

    @PostMapping("/create")
    @Operation(summary = "创建流程监听器")
    @PreAuthorize("@ss.hasPermission('bpm:process-listener:create')")
    public CommonResult<Long> createProcessListener(@Valid @RequestBody BpmProcessListenerSaveReqVO createReqVO) {
        return success(processListenerService.createProcessListener(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新流程监听器")
    @PreAuthorize("@ss.hasPermission('bpm:process-listener:update')")
    public CommonResult<Boolean> updateProcessListener(@Valid @RequestBody BpmProcessListenerSaveReqVO updateReqVO) {
        processListenerService.updateProcessListener(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除流程监听器")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bpm:process-listener:delete')")
    public CommonResult<Boolean> deleteProcessListener(@RequestParam("id") Long id) {
        processListenerService.deleteProcessListener(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得流程监听器")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bpm:process-listener:query')")
    public CommonResult<BpmProcessListenerRespVO> getProcessListener(@RequestParam("id") Long id) {
        BpmProcessListenerDO processListener = processListenerService.getProcessListener(id);
        return success(BeanUtils.toBean(processListener, BpmProcessListenerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得流程监听器分页")
    @PreAuthorize("@ss.hasPermission('bpm:process-listener:query')")
    public CommonResult<PageResult<BpmProcessListenerRespVO>> getProcessListenerPage(
            @Valid BpmProcessListenerPageReqVO pageReqVO) {
        PageResult<BpmProcessListenerDO> pageResult = processListenerService.getProcessListenerPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BpmProcessListenerRespVO.class));
    }

}