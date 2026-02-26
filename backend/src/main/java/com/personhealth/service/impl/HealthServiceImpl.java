package com.personhealth.service.impl;

import com.personhealth.dto.HealthRecordRequest;
import com.personhealth.dto.HealthTrendRequest;
import com.personhealth.entity.HealthRecord;
import com.personhealth.entity.UserProfile;
import com.personhealth.mapper.HealthRecordMapper;
import com.personhealth.mapper.UserProfileMapper;
import com.personhealth.security.SecurityContextHolder;
import com.personhealth.service.HealthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 健康服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HealthServiceImpl implements HealthService {

    private final HealthRecordMapper healthRecordMapper;
    private final UserProfileMapper userProfileMapper;

    @Override
    @Transactional
    public void recordHealth(HealthRecordRequest request) {
        Long userId = SecurityContextHolder.getUserId();

        // 验证用户档案
        UserProfile profile = userProfileMapper.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("请先完善个人档案");
        }

        // 创建健康记录
        HealthRecord record = HealthRecord.builder()
                .userId(userId)
                .weight(request.getWeight())
                .height(request.getHeight())
                .systolicPressure(request.getSystolicPressure())
                .diastolicPressure(request.getDiastolicPressure())
                .bloodSugar(request.getBloodSugar())
                .heartRate(request.getHeartRate())
                .bloodLipid(request.getBloodLipid())
                .oxygenSaturation(request.getOxygenSaturation())
                .bodyTemperature(request.getBodyTemperature())
                .remark(request.getRemark())
                .recordTime(LocalDateTime.now())
                .build();

        healthRecordMapper.insert(record);
        log.info("健康记录录入成功，用户ID：{}", userId);
    }

    @Override
    public List<HealthRecord> getHealthRecords(Integer page, Integer size) {
        Long userId = SecurityContextHolder.getUserId();
        int offset = (page - 1) * size;
        return healthRecordMapper.findByUserId(userId, offset, size);
    }

    @Override
    public List<HealthRecord> getHealthTrends(HealthTrendRequest request) {
        Long userId = SecurityContextHolder.getUserId();
        List<HealthRecord> records = healthRecordMapper.findByUserIdAndTimeRange(
                userId, request.getStartDate(), request.getEndDate());

        // 根据指标类型过滤
        if (request.getMetricType() != null && !"all".equals(request.getMetricType())) {
            records = records.stream()
                    .filter(record -> {
                        switch (request.getMetricType()) {
                            case "weight":
                                return record.getWeight() != null;
                            case "pressure":
                                return record.getSystolicPressure() != null && record.getDiastolicPressure() != null;
                            case "sugar":
                                return record.getBloodSugar() != null;
                            default:
                                return true;
                        }
                    })
                    .collect(Collectors.toList());
        }

        return records;
    }

    @Override
    public String generateHealthReport() {
        Long userId = SecurityContextHolder.getUserId();

        // 获取最近的健康记录
        List<HealthRecord> recentRecords = healthRecordMapper.findRecent(userId, 10);
        if (recentRecords.isEmpty()) {
            return "暂无健康数据，请先录入健康指标";
        }

        // 获取用户档案
        UserProfile profile = userProfileMapper.findByUserId(userId);

        // 生成健康报告
        StringBuilder report = new StringBuilder();
        report.append("=== 健康分析报告 ===\n\n");
        report.append(String.format("姓名：%s\n", profile.getRealName()));
        report.append(String.format("年龄：%d岁\n", java.time.Period.between(profile.getBirthday(), java.time.LocalDate.now()).getYears()));
        report.append("\n--- 健康指标概览 ---\n");

        HealthRecord latest = recentRecords.get(0);
        if (latest.getWeight() != null) {
            report.append(String.format("体重：%.1f kg\n", latest.getWeight()));
        }
        if (latest.getSystolicPressure() != null && latest.getDiastolicPressure() != null) {
            report.append(String.format("血压：%d/%d mmHg\n", latest.getSystolicPressure(), latest.getDiastolicPressure()));
        }
        if (latest.getBloodSugar() != null) {
            report.append(String.format("血糖：%.1f mmol/L\n", latest.getBloodSugar()));
        }
        if (latest.getHeartRate() != null) {
            report.append(String.format("心率：%d 次/分\n", latest.getHeartRate()));
        }

        report.append("\n--- 健康建议 ---\n");
        report.append("1. 建议定期测量血压、血糖等指标\n");
        report.append("2. 保持健康的生活方式，合理饮食，适量运动\n");
        report.append("3. 如指标异常，请及时就医\n");

        return report.toString();
    }
}
