package com.personhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 就医记录实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalVisit {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 医院等级
     */
    private String hospitalLevel;

    /**
     * 科室
     */
    private String department;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 就诊日期
     */
    private LocalDateTime visitDate;

    /**
     * 主要诊断
     */
    private String diagnosis;

    /**
     * 处方信息
     */
    private String prescription;

    /**
     * 医疗费用
     */
    private BigDecimal medicalExpense;

    /**
     * 医保报销金额
     */
    private BigDecimal insuranceReimbursement;

    /**
     * 就诊类型：0-门诊，1-住院，2-急诊
     */
    private Integer visitType;

    /**
     * 数据来源：0-医保，1-医院，2-用户录入
     */
    private Integer dataSource;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
