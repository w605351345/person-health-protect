-- ========================================
-- 家庭健康档案系统 - 数据库建表脚本
-- 版本：1.0.0
-- 数据库：MySQL 8.0+
-- ========================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS person_health DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE person_health;

-- ========================================
-- 用户相关表
-- ========================================

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（BCrypt加密）',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip` VARCHAR(50) DEFAULT NULL COMMENT '最后登录IP',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_phone` (`phone`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 用户档案表
CREATE TABLE IF NOT EXISTS `user_profile` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '档案ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
    `id_card_number` VARCHAR(255) NOT NULL COMMENT '身份证号（AES加密）',
    `id_card_front_photo` VARCHAR(500) DEFAULT NULL COMMENT '身份证正面照片',
    `id_card_back_photo` VARCHAR(500) DEFAULT NULL COMMENT '身份证背面照片',
    `gender` TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    `birthday` DATE DEFAULT NULL COMMENT '出生日期',
    `nation` VARCHAR(20) DEFAULT NULL COMMENT '民族',
    `address` VARCHAR(255) DEFAULT NULL COMMENT '地址',
    `emergency_contact_name` VARCHAR(50) DEFAULT NULL COMMENT '紧急联系人姓名',
    `emergency_contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '紧急联系人电话',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`),
    KEY `idx_id_card` (`id_card_number`),
    CONSTRAINT `fk_user_profile_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户档案表';

-- ========================================
-- 健康数据表
-- ========================================

-- 健康记录表
CREATE TABLE IF NOT EXISTS `health_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `weight` DECIMAL(5,2) DEFAULT NULL COMMENT '体重（kg）',
    `height` DECIMAL(5,2) DEFAULT NULL COMMENT '身高（cm）',
    `systolic_pressure` INT DEFAULT NULL COMMENT '收缩压（mmHg）',
    `diastolic_pressure` INT DEFAULT NULL COMMENT '舒张压（mmHg）',
    `blood_sugar` DECIMAL(5,2) DEFAULT NULL COMMENT '血糖（mmol/L）',
    `heart_rate` INT DEFAULT NULL COMMENT '心率（次/分）',
    `blood_lipid` DECIMAL(5,2) DEFAULT NULL COMMENT '血脂',
    `oxygen_saturation` INT DEFAULT NULL COMMENT '血氧饱和度（%）',
    `body_temperature` DECIMAL(4,1) DEFAULT NULL COMMENT '体温（℃）',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `record_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_record_time` (`record_time`),
    CONSTRAINT `fk_health_record_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康记录表';

-- ========================================
-- 医疗记录表
-- ========================================

-- 就医记录表
CREATE TABLE IF NOT EXISTS `medical_visit` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `hospital_name` VARCHAR(255) DEFAULT NULL COMMENT '医院名称',
    `hospital_level` VARCHAR(50) DEFAULT NULL COMMENT '医院等级',
    `department` VARCHAR(100) DEFAULT NULL COMMENT '科室',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '医生姓名',
    `visit_date` DATETIME DEFAULT NULL COMMENT '就诊日期',
    `diagnosis` TEXT COMMENT '主要诊断',
    `prescription` TEXT COMMENT '处方信息',
    `medical_expense` DECIMAL(10,2) DEFAULT NULL COMMENT '医疗费用',
    `insurance_reimbursement` DECIMAL(10,2) DEFAULT NULL COMMENT '医保报销金额',
    `visit_type` TINYINT DEFAULT 0 COMMENT '就诊类型：0-门诊，1-住院，2-急诊',
    `data_source` TINYINT DEFAULT 0 COMMENT '数据来源：0-医保，1-医院，2-用户录入',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_visit_date` (`visit_date`),
    KEY `idx_data_source` (`data_source`),
    CONSTRAINT `fk_medical_visit_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='就医记录表';

-- 既往病史表
CREATE TABLE IF NOT EXISTS `medical_history` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `disease_name` VARCHAR(255) NOT NULL COMMENT '疾病名称',
    `diagnosis_date` DATE DEFAULT NULL COMMENT '诊断日期',
    `diagnosis_hospital` VARCHAR(255) DEFAULT NULL COMMENT '诊断医院',
    `treatment_status` TINYINT DEFAULT 0 COMMENT '治疗状态：0-未治疗，1-治疗中，2-已治愈，3-慢性病',
    `description` TEXT COMMENT '描述',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_disease_name` (`disease_name`),
    CONSTRAINT `fk_medical_history_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='既往病史表';

-- ========================================
-- 保险相关表
-- ========================================

-- 保险产品表
CREATE TABLE IF NOT EXISTS `insurance_product` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '产品ID',
    `product_name` VARCHAR(255) NOT NULL COMMENT '产品名称',
    `insurance_company` VARCHAR(255) NOT NULL COMMENT '保险公司',
    `product_type` TINYINT NOT NULL COMMENT '产品类型：0-医疗险，1-重疾险，2-意外险，3-寿险，4-年金险',
    `description` TEXT COMMENT '产品描述',
    `coverage` TEXT COMMENT '保障内容',
    `coverage_amount` DECIMAL(12,2) DEFAULT NULL COMMENT '保险金额',
    `annual_premium` DECIMAL(10,2) DEFAULT NULL COMMENT '年保费',
    `min_age` INT DEFAULT 0 COMMENT '最低年龄',
    `max_age` INT DEFAULT 100 COMMENT '最高年龄',
    `cool_off_period` INT DEFAULT NULL COMMENT '犹豫期（天）',
    `waiting_period` INT DEFAULT NULL COMMENT '等待期（天）',
    `age_requirement` VARCHAR(255) DEFAULT NULL COMMENT '投保年龄要求',
    `health_disclosure` TEXT COMMENT '健康告知内容',
    `purchase_url` VARCHAR(500) DEFAULT NULL COMMENT '购买链接',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '产品状态：0-下架，1-上架',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_product_type` (`product_type`),
    KEY `idx_status` (`status`),
    KEY `idx_age_range` (`min_age`, `max_age`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='保险产品表';

-- 用户保单表
CREATE TABLE IF NOT EXISTS `user_policy` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '保单ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `product_id` BIGINT NOT NULL COMMENT '产品ID',
    `policy_number` VARCHAR(100) DEFAULT NULL COMMENT '保单号',
    `insurance_company` VARCHAR(255) DEFAULT NULL COMMENT '保险公司',
    `product_name` VARCHAR(255) DEFAULT NULL COMMENT '产品名称',
    `coverage_amount` DECIMAL(12,2) DEFAULT NULL COMMENT '保障金额',
    `annual_premium` DECIMAL(10,2) DEFAULT NULL COMMENT '年保费',
    `insurance_date` DATE DEFAULT NULL COMMENT '投保日期',
    `effective_date` DATE DEFAULT NULL COMMENT '生效日期',
    `expiry_date` DATE DEFAULT NULL COMMENT '到期日期',
    `status` TINYINT DEFAULT 0 COMMENT '保单状态：0-待生效，1-生效中，2-已失效，3-已退保',
    `insured_name` VARCHAR(50) DEFAULT NULL COMMENT '被保险人姓名',
    `insured_id_card` VARCHAR(255) DEFAULT NULL COMMENT '被保险人身份证',
    `beneficiary_info` TEXT COMMENT '受益人信息',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_policy_number` (`policy_number`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_product_id` (`product_id`),
    KEY `idx_status` (`status`),
    CONSTRAINT `fk_user_policy_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_user_policy_product` FOREIGN KEY (`product_id`) REFERENCES `insurance_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户保单表';

-- ========================================
-- 初始化数据
-- ========================================

-- 插入示例保险产品
INSERT INTO `insurance_product` (`product_name`, `insurance_company`, `product_type`, `description`, `coverage`, `coverage_amount`, `annual_premium`, `min_age`, `max_age`, `cool_off_period`, `waiting_period`, `health_disclosure`, `status`) VALUES
('百万医疗险', '平安保险', 0, '高额医疗费用保障', '住院医疗、特殊门诊、门诊手术', 1000000.00, 365.00, 0, 60, 15, 30, '无既往重大疾病', 1),
('重疾无忧', '中国人寿', 1, '重大疾病保障', '覆盖120种重疾、25种中症、40种轻症', 500000.00, 5000.00, 0, 55, 10, 90, '无既往重大疾病', 1),
('百万意外险', '太平洋保险', 2, '意外伤害保障', '意外身故、意外伤残、意外医疗', 1000000.00, 299.00, 0, 65, 0, 0, '无职业限制', 1),
('终身寿险', '泰康人寿', 3, '身故保障', '终身身故保障', 500000.00, 8000.00, 0, 60, 15, 180, '健康告知', 1),
('养老年金', '华夏保险', 4, '养老保障', '定期领取年金', 300000.00, 10000.00, 0, 55, 15, 0, '健康告知', 1);

-- ========================================
-- 创建视图
-- ========================================

-- 用户档案视图
CREATE OR REPLACE VIEW `v_user_profile` AS
SELECT
    u.id,
    u.phone,
    u.nickname,
    u.avatar,
    up.real_name,
    up.id_card_number,
    up.gender,
    up.birthday,
    TIMESTAMPDIFF(YEAR, up.birthday, CURDATE()) AS age,
    up.nation,
    up.address
FROM `user` u
LEFT JOIN `user_profile` up ON u.id = up.user_id;

-- ========================================
-- 创建索引优化
-- ========================================

-- 为高频查询字段添加复合索引
CREATE INDEX idx_user_health_record ON health_record(user_id, record_time);
CREATE INDEX idx_user_medical_visit ON medical_visit(user_id, visit_date);
CREATE INDEX idx_user_policy_status ON user_policy(user_id, status);

-- ========================================
-- 数据库创建完成
-- ========================================
