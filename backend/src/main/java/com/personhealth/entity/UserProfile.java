package com.personhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户档案实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    /**
     * 档案ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号（AES加密存储）
     */
    private String idCardNumber;

    /**
     * 身份证正面照片
     */
    private String idCardFrontPhoto;

    /**
     * 身份证背面照片
     */
    private String idCardBackPhoto;

    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 民族
     */
    private String nation;

    /**
     * 地址
     */
    private String address;

    /**
     * 紧急联系人姓名
     */
    private String emergencyContactName;

    /**
     * 紧急联系人电话
     */
    private String emergencyContactPhone;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
