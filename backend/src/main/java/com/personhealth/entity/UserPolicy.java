package com.personhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户保单实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPolicy {
    /**
     * 保单ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 保单号
     */
    private String policyNumber;

    /**
     * 保险公司
     */
    private String insuranceCompany;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 保障金额
     */
    private BigDecimal coverageAmount;

    /**
     * 年保费
     */
    private BigDecimal annualPremium;

    /**
     * 投保日期
     */
    private LocalDate insuranceDate;

    /**
     * 生效日期
     */
    private LocalDate effectiveDate;

    /**
     * 到期日期
     */
    private LocalDate expiryDate;

    /**
     * 保单状态：0-待生效，1-生效中，2-已失效，3-已退保
     */
    private Integer status;

    /**
     * 被保险人姓名
     */
    private String insuredName;

    /**
     * 被保险人身份证
     */
    private String insuredIdCard;

    /**
     * 受益人信息
     */
    private String beneficiaryInfo;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
