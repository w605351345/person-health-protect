package com.personhealth.service.impl;

import com.personhealth.entity.InsuranceProduct;
import com.personhealth.entity.MedicalVisit;
import com.personhealth.entity.UserPolicy;
import com.personhealth.entity.UserProfile;
import com.personhealth.mapper.InsuranceProductMapper;
import com.personhealth.mapper.MedicalVisitMapper;
import com.personhealth.mapper.UserPolicyMapper;
import com.personhealth.mapper.UserProfileMapper;
import com.personhealth.security.SecurityContextHolder;
import com.personhealth.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 保险服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceProductMapper insuranceProductMapper;
    private final UserPolicyMapper userPolicyMapper;
    private final UserProfileMapper userProfileMapper;
    private final MedicalVisitMapper medicalVisitMapper;

    @Override
    public List<InsuranceProduct> getInsuranceProducts() {
        Long userId = SecurityContextHolder.getUserId();
        UserProfile profile = userProfileMapper.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("请先完善个人档案");
        }

        // 计算用户年龄
        int age = Period.between(profile.getBirthday(), LocalDate.now()).getYears();

        // 获取符合年龄段的保险产品
        return insuranceProductMapper.findByAgeRange(age, age);
    }

    @Override
    public List<InsuranceProduct> recommendInsurance() {
        Long userId = SecurityContextHolder.getUserId();
        UserProfile profile = userProfileMapper.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("请先完善个人档案");
        }

        // 计算用户年龄
        int age = Period.between(profile.getBirthday(), LocalDate.now()).getYears();

        // 获取符合年龄段的保险产品
        List<InsuranceProduct> products = insuranceProductMapper.findByAgeRange(age, age);

        // 获取用户医疗记录，用于智能推荐
        List<MedicalVisit> medicalVisits = medicalVisitMapper.findByUserId(userId);

        // TODO: 基于医疗记录进行智能推荐
        // 这里返回符合年龄段的全部产品，实际可以根据病史进行个性化推荐
        return products.stream()
                .filter(product -> product.getStatus() == 1) // 只返回上架状态
                .collect(Collectors.toList());
    }

    @Override
    public List<InsuranceProduct> filterInsurance() {
        Long userId = SecurityContextHolder.getUserId();
        UserProfile profile = userProfileMapper.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("请先完善个人档案");
        }

        // 计算用户年龄
        int age = Period.between(profile.getBirthday(), LocalDate.now()).getYears();

        // 获取符合年龄段的保险产品
        List<InsuranceProduct> products = insuranceProductMapper.findByAgeRange(age, age);

        // 获取用户医疗记录
        List<MedicalVisit> medicalVisits = medicalVisitMapper.findByUserId(userId);

        // 过滤无法投保的保险产品
        // 基于健康告知进行过滤
        List<InsuranceProduct> filteredProducts = products.stream()
                .filter(product -> {
                    // 只返回上架状态
                    if (product.getStatus() != 1) {
                        return false;
                    }

                    // TODO: 实现基于健康告知的过滤逻辑
                    // 这里简化处理，只检查是否有重大疾病诊断
                    boolean hasMajorDisease = medicalVisits.stream()
                            .anyMatch(visit -> {
                                if (visit.getDiagnosis() == null) return false;
                                String diagnosis = visit.getDiagnosis().toLowerCase();
                                return diagnosis.contains("癌") || diagnosis.contains("肿瘤") ||
                                       diagnosis.contains("心脏病") || diagnosis.contains("糖尿病") ||
                                       diagnosis.contains("高血压");
                            });

                    // 如果有重大疾病，可能无法投保某些产品
                    // 这里简化处理，实际需要根据产品的健康告知进行匹配
                    return !hasMajorDisease || product.getProductType() == 0; // 医疗险可能可投保
                })
                .collect(Collectors.toList());

        log.info("保险产品过滤完成，用户ID：{}，过滤后数量：{}", userId, filteredProducts.size());

        return filteredProducts;
    }

    @Override
    public InsuranceProduct getInsuranceDetail(Long id) {
        InsuranceProduct product = insuranceProductMapper.findById(id);
        if (product == null) {
            throw new RuntimeException("保险产品不存在");
        }
        return product;
    }

    @Override
    public List<UserPolicy> getMyPolicies() {
        Long userId = SecurityContextHolder.getUserId();
        return userPolicyMapper.findByUserId(userId);
    }

    @Override
    public String getPurchaseUrl(Long id) {
        InsuranceProduct product = insuranceProductMapper.findById(id);
        if (product == null) {
            throw new RuntimeException("保险产品不存在");
        }

        if (product.getPurchaseUrl() == null || product.getPurchaseUrl().isEmpty()) {
            throw new RuntimeException("暂无购买链接");
        }

        return product.getPurchaseUrl();
    }
}
