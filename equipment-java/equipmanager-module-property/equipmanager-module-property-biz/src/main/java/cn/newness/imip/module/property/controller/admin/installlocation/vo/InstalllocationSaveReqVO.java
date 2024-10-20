package cn.newness.imip.module.property.controller.admin.installlocation.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 设备安装位置新增/修改 Request VO")
@Data
public class InstalllocationSaveReqVO {

    @Schema(description = "主键id", example = "17180")
    private String id;

    @Schema(description = "地区编码")
    @DiffLogField(name = "地区编码")
    private String code;

    @Schema(description = "父地区id", example = "6754")
    @DiffLogField(name = "父地区id")
    private String supId;

    @Schema(description = "地区名称", example = "芋艿")
    @DiffLogField(name = "地区名称")
    private String name;

    @Schema(description = "负责人", example = "张三")
    @DiffLogField(name = "负责人")
    private String dutyName;

}