package com.personhealth.service;

import com.personhealth.entity.MedicalVisit;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 医疗服务接口
 */
public interface MedicalService {

    /**
     * 获取就医记录
     */
    List<MedicalVisit> getMedicalVisits(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 从医保服务查询
     */
    List<MedicalVisit> getVisitsFromInsurance();

    /**
     * 从医院查询
     */
    List<MedicalVisit> getVisitsFromHospital();

    /**
     * 获取既往病史
     */
    String getMedicalHistory();

    /**
     * 获取用药记录
     */
    String getMedications();

    /**
     * 同步医疗记录
     */
    void syncMedicalRecords();
}
