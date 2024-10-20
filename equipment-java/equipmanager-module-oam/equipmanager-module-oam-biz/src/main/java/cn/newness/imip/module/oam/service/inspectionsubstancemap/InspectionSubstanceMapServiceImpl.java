package cn.newness.imip.module.oam.service.inspectionsubstancemap;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.newness.imip.module.oam.dal.dataobject.inspectionsubstancemap.InspectionSubstanceMapDO;
import cn.newness.imip.framework.common.util.object.BeanUtils;

import cn.newness.imip.module.oam.dal.mysql.inspectionsubstancemap.InspectionSubstanceMapMapper;
import cn.newness.imip.module.oam.service.InspectionSubstanceMapService;

import static cn.newness.imip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.newness.imip.module.oam.enums.ErrorCodeConstants.*;

/**
 * 点检计划内容映射表 Service 实现类
 *
 * @author super超级管理员王中王
 */
@Service
@Validated
public class InspectionSubstanceMapServiceImpl implements InspectionSubstanceMapService {

    @Resource
    private InspectionSubstanceMapMapper inspectionSubstanceMapMapper;

    @Override
    public String createInspectionSubstanceMap(InspectionSubstanceMapDO createDo) {
        //创建id
        createDo.setId(BeanUtils.createId());
        // 插入
        inspectionSubstanceMapMapper.insert(createDo);
        // 返回
        return createDo.getId();
    }

    @Override
    public void updateInspectionSubstanceMap(InspectionSubstanceMapDO updateDo) {
        // 校验存在
        validateInspectionSubstanceMapExists(updateDo.getId());
        // 更新
        inspectionSubstanceMapMapper.updateById(updateDo);
    }

    @Override
    public void deleteInspectionSubstanceMap(String id) {
        // 校验存在
        validateInspectionSubstanceMapExists(id);
        // 删除
        inspectionSubstanceMapMapper.deleteById(id);
    }

    private void validateInspectionSubstanceMapExists(String id) {
        if (inspectionSubstanceMapMapper.selectById(id) == null) {
            throw exception(INSPECTION_SUBSTANCE_MAP_NOT_EXISTS);
        }
    }

    @Override
    public InspectionSubstanceMapDO getInspectionSubstanceMap(String id) {
        return inspectionSubstanceMapMapper.selectById(id);
    }

}