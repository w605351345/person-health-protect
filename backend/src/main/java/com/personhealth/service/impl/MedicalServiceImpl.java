package com.personhealth.service.impl;

import com.personhealth.entity.MedicalVisit;
import com.personhealth.entity.UserProfile;
import com.personhealth.mapper.MedicalVisitMapper;
import com.personhealth.mapper.UserProfileMapper;
import com.personhealth.security.SecurityContextHolder;
import com.personhealth.service.MedicalService;
import com.personhealth.service.external.HospitalApiServiceImpl;
import com.personhealth.service.external.InsuranceApiServiceImpl;
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
    private final HospitalApiServiceImpl hospitalApiService;
    private final InsuranceApiServiceImpl insuranceApiService;

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

        try {
            // 对接国家医保平台API查询参保人信息
            var insuredInfo = insuranceApiService.queryInsuredInfo(profile.getIdCardNumber());

            // 从医保服务获取就医记录
            List<MedicalVisit> visits = insuranceApiService.fetchMedicalVisitsFromInsurance(profile.getIdCardNumber());

            // 设置用户ID
            visits.forEach(visit -> visit.setUserId(userId));

            log.info("从医保服务查询就医记录成功，用户ID：{}，记录数：{}", userId, visits.size());
            return visits;
        } catch (Exception e) {
            log.error("从医保服务查询就医记录失败", e);
            // 返回数据库中已有的医保记录
            return medicalVisitMapper.findByUserIdAndDataSource(userId, 0);
        }
    }

    @Override
    public List<MedicalVisit> getVisitsFromHospital() {
        Long userId = SecurityContextHolder.getUserId();
        UserProfile profile = userProfileMapper.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("请先完善个人档案");
        }

        try {
            // 从医院CRM系统查询检查报告
            var reportData = hospitalApiService.queryReportFromCrm("patient_" + userId, "all");

            // 根据检查报告构建就医记录
            List<MedicalVisit> visits = new ArrayList<>();
            if (!reportData.isEmpty()) {
                MedicalVisit visit = hospitalApiService.buildMedicalVisit(reportData);
                visit.setUserId(userId);
                visits.add(visit);
            }

            log.info("从医院系统查询就医记录成功，用户ID：{}，记录数：{}", userId, visits.size());
            return visits;
        } catch (Exception e) {
            log.error("从医院系统查询就医记录失败", e);
            // 返回数据库中已有的医院记录
            return medicalVisitMapper.findByUserIdAndDataSource(userId, 1);
        }
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
            // 对接医保服务API
            List<MedicalVisit> insuranceVisits = fetchFromInsuranceService(profile);
            insuranceVisits.forEach(visit -> {
                visit.setUserId(userId);
                visit.setDataSource(0); // 医保来源
                medicalVisitMapper.insert(visit);
            });

            // 对接医院系统API
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
     * 从医保服务获取数据
     */
    private List<MedicalVisit> fetchFromInsuranceService(UserProfile profile) {
        try {
            // 从国家医保平台查询参保人信息
            var insuredInfo = insuranceApiService.queryInsuredInfo(profile.getIdCardNumber());

            // 从地方医保API查询报销记录
            String regionCode = "110000"; // 示例：北京
            var policy = insuranceApiService.queryReimbursementPolicy(regionCode, "普通门诊");

            // 根据医保数据构建就医记录
            List<MedicalVisit> visits = new ArrayList<>();
            if (!policy.isEmpty()) {
                MedicalVisit visit = insuranceApiService.buildMedicalVisit(insuredInfo);
                visit.setHospitalName("医保定点医院");
                visit.setHospitalLevel("三甲");
                visit.setDepartment("内科");
                visit.setDoctorName("医保医生");
                visit.setVisitDate(LocalDateTime.now().minusMonths(1));
                visit.setDiagnosis("医保记录疾病");
                visit.setPrescription("医保药品");
                visit.setMedicalExpense(new java.math.BigDecimal("500.00"));
                visit.setInsuranceReimbursement(new java.math.BigDecimal("300.00"));
                visit.setVisitType(0); // 门诊
                visit.setDataSource(0); // 医保来源
                visits.add(visit);
            }

            return visits;
        } catch (Exception e) {
            log.error("从医保服务获取数据失败", e);
            return new ArrayList<>();
        }
    }

    /**
     * 从医院系统获取数据
     */
    private List<MedicalVisit> fetchFromHospitalService(UserProfile profile) {
        try {
            // 从医院CRM系统同步患者数据
            var patientData = hospitalApiService.syncPatientFromCrm(profile.getIdCardNumber(), null);

            // 从医院CRM系统查询检查报告
            String patientId = patientData.isEmpty() ? "" : (String) patientData.get("patientId");
            var reportData = hospitalApiService.queryReportFromCrm(patientId, "all");

            // 根据医院数据构建就医记录
            List<MedicalVisit> visits = new ArrayList<>();
            if (!reportData.isEmpty()) {
                MedicalVisit visit = hospitalApiService.buildMedicalVisit(reportData);
                visit.setHospitalName(reportData.get("hospitalName") != null ?
                        reportData.get("hospitalName").toString() : "合作医院");
                visit.setDepartment(reportData.get("department") != null ?
                        reportData.get("department").toString() : "全科");
                visit.setDoctorName(reportData.get("doctorName") != null ?
                        reportData.get("doctorName").toString() : "值班医生");
                visit.setVisitDate(LocalDateTime.now().minusDays(15));
                visit.setDiagnosis(reportData.get("reportResult") != null ?
                        reportData.get("reportResult").toString() : "健康检查");
                visit.setPrescription(reportData.get("prescription") != null ?
                        reportData.get("prescription").toString() : "无");
                visit.setMedicalExpense(new java.math.BigDecimal("328.00"));
                visit.setInsuranceReimbursement(new java.math.BigDecimal("0.00"));
                visit.setVisitType(0); // 门诊
                visit.setDataSource(1); // 医院来源
                visits.add(visit);
            }

            return visits;
        } catch (Exception e) {
            log.error("从医院系统获取数据失败", e);
            return new ArrayList<>();
        }
    }
}
