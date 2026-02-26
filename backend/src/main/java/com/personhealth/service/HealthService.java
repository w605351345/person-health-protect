package com.personhealth.service;

import com.personhealth.dto.HealthRecordRequest;
import com.personhealth.dto.HealthTrendRequest;
import com.personhealth.entity.HealthRecord;

import java.util.List;

/**
 * 健康服务接口
 */
public interface HealthService {

    /**
     * 录入健康指标
     */
    void recordHealth(HealthRecordRequest request);

    /**
     * 获取健康记录
     */
    List<HealthRecord> getHealthRecords(Integer page, Integer size);

    /**
     * 获取健康趋势
     */
    List<HealthRecord> getHealthTrends(HealthTrendRequest request);

    /**
     * 生成健康报告
     */
    String generateHealthReport();
}
