package com.personhealth.service.impl;

import com.personhealth.entity.MedicalVisit;
import com.personhealth.entity.UserProfile;
import com.personhealth.mapper.MedicalVisitMapper;
import com.personhealth.mapper.UserProfileMapper;
import com.personhealth.security.SecurityContextHolder;
import com.personhealth.service.MedicalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 医疗服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicalServiceImpl implements MedicalService {

    private final MedicalVisitMapper medicalVisitMapper;
    private final UserProfileMapper userProfileMapper;

    @Override
    public List<MedicalVisit> getMedicalVisits(LocalDateTime startDate, LocalDateTime endDate) {
        Long userId = SecurityContextHolder.getUserId();
        return medicalVisitMapper.findByUserIdAndTimeRange(userId, startDate, endDate);
    }

    @Override
    public List<MedicalVisit> getVisitsFromInsurance() {
        Long userId = SecurityContextHolder.getUserId();
        UserProfile profile = userProfileMapper.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("请先完善个人档案");
        }

        // TODO: 对接医保服务接口
        // 这里模拟数据，实际开发中需要调用真实的医保服务API
        log.info("从医保服务查询就医记录，用户ID：{}", userId);

        // 返回数据来源为医保的记录
        return medicalVisitMapper.findByUserIdAndDataSource(userId, 0);
    }

    @Override
    public List<MedicalVisit> getVisitsFromHospital() {
        Long userId = SecurityContextHolder.getUserId();
        UserProfile profile = userProfileMapper.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("请先完善个人档案");
        }

        // TODO: 对接医院系统接口
        // 这里模拟数据，实际开发中需要调用真实的医院系统API
        log.info("从医院系统查询就医记录，用户ID：{}", userId);

        // 返回数据来源为医院的记录
        return medicalVisitMapper.findByUserIdAndDataSource(userId, 1);
    }

    @Override
    public String getMedicalHistory() {
        Long userId = SecurityContextHolder.getUserId();

        // TODO: 从数据库或外部服务获取既往病史
        // 这里返回模拟数据
        List<MedicalVisit> visits = medicalVisitMapper.findByUserId(userId);
        if (visits.isEmpty()) {
            return "暂无既往病史记录";
        }

        StringBuilder history = new StringBuilder();
        history.append("=== 既往病史 ===\n\n");

        // 从诊断中提取病史信息
        visits.forEach(visit -> {
            if (visit.getDiagnosis() != null && !visit.getDiagnosis().isEmpty()) {
                history.append(String.format("诊断：%s\n", visit.getDiagnosis()));
                history.append(String.format("医院：%s\n", visit.getHospitalName()));
                history.append(String.format("时间：%s\n", visit.getVisitDate()));
                history.append("\n");
            }
        });

        return history.toString();
    }

    @Override
    public String getMedications() {
        Long userId = SecurityContextHolder.getUserId();

        // TODO: 从数据库或外部服务获取用药记录
        // 这里返回模拟数据
        List<MedicalVisit> visits = medicalVisitMapper.findByUserId(userId);
        if (visits.isEmpty()) {
            return "暂无用要记录";
        }

        StringBuilder medications = new StringBuilder();
        medications.append("=== 用药记录 ===\n\n");

        // 从处方中提取用药信息
        visits.forEach(visit -> {
            if (visit.getPrescription() != null && !visit.getPrescription().isEmpty()) {
                medications.append(String.format("处方：%s\n", visit.getPrescription()));
                medications.append(String.format("医院：%s\n", visit.getHospitalName()));
                medications.append(String.format("时间：%s\n", visit.getVisitDate()));
                medications.append("\n");
            }
        });

        return medications.toString();
    }

    @Override
    public void syncMedicalRecords() {
        Long userId = SecurityContextHolder.getUserId();
        UserProfile profile = userProfileMapper.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("请先完善个人档案");
        }

        log.info("开始同步医疗记录，用户ID：{}", userId);

        try {
            // TODO: 对接医保服务API
            List<MedicalVisit> insuranceVisits = fetchFromInsuranceService(profile);
            insuranceVisits.forEach(visit -> {
                visit.setUserId(userId);
                visit.setDataSource(0); // 医保来源
                medicalVisitMapper.insert(visit);
            });

            // TODO: 对接医院系统API
            List<MedicalVisit> hospitalVisits = fetchFromHospitalService(profile);
            hospitalVisits.forEach(visit -> {
                visit.setUserId(userId);
                visit.setDataSource(1); // 医院来源
                medicalVisitMapper.insert(visit);
            });

            log.info("医疗记录同步完成，用户ID：{}", userId);
        } catch (Exception e) {
            log.error("医疗记录同步失败", e);
            throw new RuntimeException("同步失败：" + e.getMessage());
        }
    }

    /**
     * 从医保服务获取数据（模拟）
     */
    private List<MedicalVisit> fetchFromInsuranceService(UserProfile profile) {
        // TODO: 实现真实的医保服务调用
        // 这里返回空列表，实际开发中需要调用真实的API
        return new ArrayList<>();
    }

    /**
     * 从医院系统获取数据（模拟）
     */
    private List<MedicalVisit> fetchFromHospitalService(UserProfile profile) {
        // TODO: 实现真实的医院系统调用
        // 这里返回空列表，实际开发中需要调用真实的API
        return new ArrayList<>();
    }
}
