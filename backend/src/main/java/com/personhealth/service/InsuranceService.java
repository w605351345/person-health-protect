package com.personhealth.service;

import com.personhealth.entity.InsuranceProduct;
import com.personhealth.entity.UserPolicy;

import java.util.List;

/**
 * 保险服务接口
 */
public interface InsuranceService {

    /**
     * 获取保险产品列表
     */
    List<InsuranceProduct> getInsuranceProducts();

    /**
     * 智能推荐保险
     */
    List<InsuranceProduct> recommendInsurance();

    /**
     * 过滤保险
     */
    List<InsuranceProduct> filterInsurance();

    /**
     * 获取保险详情
     */
    InsuranceProduct getInsuranceDetail(Long id);

    /**
     * 获取我的保单
     */
    List<UserPolicy> getMyPolicies();

    /**
     * 获取购买链接
     */
    String getPurchaseUrl(Long id);
}
