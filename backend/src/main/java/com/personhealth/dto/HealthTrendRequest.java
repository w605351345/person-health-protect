package com.personhealth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 健康趋势请求DTO
 */
@Data
@Schema(description = "健康趋势请求")
public class HealthTrendRequest {

    @Schema(description = "开始时间", example = "2024-01-01T00:00:00")
    private LocalDateTime startDate;

    @Schema(description = "结束时间", example = "2024-12-31T23:59:59")
    private LocalDateTime endDate;

    @Schema(description = "指标类型：weight-体重，pressure-血压，sugar-血糖，all-全部", example = "all")
    private String metricType;
}
