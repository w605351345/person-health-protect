package com.personhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 健康记录实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthRecord {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 体重（kg）
     */
    private BigDecimal weight;

    /**
     * 身高（cm）
     */
    private BigDecimal height;

    /**
     * 收缩压（mmHg）
     */
    private Integer systolicPressure;

    /**
     * 舒张压（mmHg）
     */
    private Integer diastolicPressure;

    /**
     * 血糖（mmol/L）
     */
    private BigDecimal bloodSugar;

    /**
     * 心率（次/分）
     */
    private Integer heartRate;

    /**
     * 血脂
     */
    private BigDecimal bloodLipid;

    /**
     * 血氧饱和度（%）
     */
    private Integer oxygenSaturation;

    /**
     * 体温（℃）
     */
    private BigDecimal bodyTemperature;

    /**
     * 备注
     */
    private String remark;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
