package com.personhealth.controller;

import com.personhealth.entity.MedicalVisit;
import com.personhealth.service.MedicalService;
import com.personhealth.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 医疗记录控制器
 */
@Tag(name = "医疗记录", description = "就医记录、既往病史查询等接口")
@RestController
@RequestMapping("/medical")
@RequiredArgsConstructor
public class MedicalController {

    private final MedicalService medicalService;

    /**
     * 获取就医记录
     */
    @Operation(summary = "获取就医记录", description = "从医保和医院查询用户的就医记录")
    @GetMapping("/visits")
    public Result<List<MedicalVisit>> getMedicalVisits(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate) {
        List<MedicalVisit> visits = medicalService.getMedicalVisits(startDate, endDate);
        return Result.success(visits);
    }

    /**
     * 从医保服务查询记录
     */
    @Operation(summary = "从医保服务查询", description = "对接医保服务查询就医记录")
    @GetMapping("/visits/insurance")
    public Result<List<MedicalVisit>> getVisitsFromInsurance() {
        List<MedicalVisit> visits = medicalService.getVisitsFromInsurance();
        return Result.success(visits);
    }

    /**
     * 从医院查询记录
     */
    @Operation(summary = "从医院查询", description = "对接医院系统查询就医记录")
    @GetMapping("/visits/hospital")
    public Result<List<MedicalVisit>> getVisitsFromHospital() {
        List<MedicalVisit> visits = medicalService.getVisitsFromHospital();
        return Result.success(visits);
    }

    /**
     * 获取既往病史
     */
    @Operation(summary = "获取既往病史", description = "查询用户的既往病史和诊断记录")
    @GetMapping("/history")
    public Result<String> getMedicalHistory() {
        String history = medicalService.getMedicalHistory();
        return Result.success(history);
    }

    /**
     * 获取用药记录
     */
    @Operation(summary = "获取用药记录", description = "查询用户的用药历史")
    @GetMapping("/medications")
    public Result<String> getMedications() {
        String medications = medicalService.getMedications();
        return Result.success(medications);
    }

    /**
     * 同步医疗记录
     */
    @Operation(summary = "同步医疗记录", description = "手动触发从外部服务同步医疗记录")
    @PostMapping("/sync")
    public Result<Void> syncMedicalRecords() {
        medicalService.syncMedicalRecords();
        return Result.success();
    }
}
