package com.personhealth.entity;

import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 保险产品实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceProduct {
    /**
     * 产品ID
     */
    private Long id;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 保险公司
     */
    private String insuranceCompany;

    /**
     * 产品类型：0-医疗险，1-重疾险，2-意外险，3-寿险，4-年金险
     */
    private Integer productType;

    /**
     * 产品描述
     */
    private String description;

    /**
     * 保障内容
     */
    private String coverage;

    /**
     * 保险金额
     */
    private BigDecimal coverageAmount;

    /**
     * 年保费
     */
    private BigDecimal annualPremium;

    /**
     * 最低年龄
     */
    private Integer minAge;

    /**
     * 最高年龄
     */
    private Integer maxAge;

    /**
     * 犹豫期（天）
     */
    private Integer coolOffPeriod;

    /**
     * 等待期（天）
     */
    private Integer waitingPeriod;

    /**
     * 投保年龄要求
     */
    private String ageRequirement;

    /**
     * 健康告知内容
     */
    private String healthDisclosure;

    /**
     * 购买链接
     */
    private String purchaseUrl;

    /**
     * 产品状态：0-下架，1-上架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
