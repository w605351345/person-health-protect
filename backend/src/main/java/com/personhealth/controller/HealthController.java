package com.personhealth.controller;

import com.personhealth.dto.HealthRecordRequest;
import com.personhealth.dto.HealthTrendRequest;
import com.personhealth.entity.HealthRecord;
import com.personhealth.service.HealthService;
import com.personhealth.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 健康数据控制器
 */
@Tag(name = "健康数据", description = "健康指标录入、查询、趋势分析等接口")
@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

    private final HealthService healthService;

    /**
     * 录入健康指标
     */
    @Operation(summary = "录入健康指标", description = "记录体重、血压、血糖等健康数据")
    @PostMapping("/record")
    public Result<Void> recordHealth(@Valid @RequestBody HealthRecordRequest request) {
        healthService.recordHealth(request);
        return Result.success();
    }

    /**
     * 获取健康记录列表
     */
    @Operation(summary = "获取健康记录", description = "分页查询用户的健康记录")
    @GetMapping("/records")
    public Result<List<HealthRecord>> getHealthRecords(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        List<HealthRecord> records = healthService.getHealthRecords(page, size);
        return Result.success(records);
    }

    /**
     * 健康趋势分析
     */
    @Operation(summary = "健康趋势分析", description = "查询指定时间段内的健康数据趋势")
    @PostMapping("/trends")
    public Result<List<HealthRecord>> getHealthTrends(@RequestBody HealthTrendRequest request) {
        List<HealthRecord> trends = healthService.getHealthTrends(request);
        return Result.success(trends);
    }

    /**
     * 生成健康报告
     */
    @Operation(summary = "生成健康报告", description = "根据历史数据生成健康分析报告")
    @GetMapping("/report")
    public Result<String> generateReport() {
        String report = healthService.generateHealthReport();
        return Result.success(report);
    }
}
