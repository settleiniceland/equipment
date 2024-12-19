package cn.newness.imip.module.property.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author machuran
 * @date 2024-11-15
 * @time 7:51 a.m.
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstalllocationDto {
    /**
     * 主键id
     */
    private String id;
    /**
     * 地区编码
     */
    private String code;
    /**
     * 父地区id
     */
    private String supId;
    /**
     * 地区名称
     */
    private String name;
    /**
     * 负责人
     */
    private String dutyName;
}
