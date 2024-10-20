package cn.newness.imip.module.oam.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author machuran
 * @date 9/19/2024
 * @time 9:02 AM
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusRecordDto {
    private String id;
    private String equipName;
    private String code;
    private Integer operationType;
    private String changeDetails;
    private Integer status1;
    private Integer status2;
    private String remark;
}
