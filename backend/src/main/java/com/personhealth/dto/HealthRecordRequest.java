package com.personhealth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 健康记录请求DTO
 */
@Data
@Schema(description = "健康记录请求")
public class HealthRecordRequest {

    @Schema(description = "体重（kg）", example = "70.5")
    private BigDecimal weight;

    @Schema(description = "身高（cm）", example = "175")
    private BigDecimal height;

    @Schema(description = "收缩压（mmHg）", example = "120")
    private Integer systolicPressure;

    @Schema(description = "舒张压（mmHg）", example = "80")
    private Integer diastolicPressure;

    @Schema(description = "血糖（mmol/L）", example = "5.4")
    private BigDecimal bloodSugar;

    @Schema(description = "心率（次/分）", example = "75")
    private Integer heartRate;

    @Schema(description = "血脂", example = "4.5")
    private BigDecimal bloodLipid;

    @Schema(description = "血氧饱和度（%）", example = "98")
    private Integer oxygenSaturation;

    @Schema(description = "体温（℃）", example = "36.5")
    private BigDecimal bodyTemperature;

    @Schema(description = "备注", example = "正常")
    private String remark;
}
