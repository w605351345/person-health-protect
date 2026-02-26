-- ========================================
-- 测试数据脚本
-- 测试用户：张三
-- ========================================

USE person_health;

-- 1. 插入测试用户：张三
INSERT INTO `user` (`phone`, `password`, `nickname`, `avatar`, `status`, `create_time`, `update_time`)
VALUES ('13800138000', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '张三', '/static/avatars/zhangsan.jpg', 1, NOW(), NOW());
-- 密码是：123456（BCrypt加密后）

-- 获取用户ID（假设为1）
SET @user_id = LAST_INSERT_ID();

-- 2. 插入张三的用户档案
INSERT INTO `user_profile` (
    `user_id`, `real_name`, `id_card_number`, `id_card_front_photo`, `id_card_back_photo`,
    `gender`, `birthday`, `nation`, `address`, `emergency_contact_name`, `emergency_contact_phone`,
    `create_time`, `update_time`
) VALUES (
    @user_id, '张三', '110101199001011234', '/static/idcard/front.jpg', '/static/idcard/back.jpg',
    1, '1990-01-01', '汉族', '北京市朝阳区建国路88号', '李四', '13900139000',
    NOW(), NOW()
);

-- 3. 插入张三的健康记录
INSERT INTO `health_record` (`user_id`, `weight`, `height`, `systolic_pressure`, `diastolic_pressure`,
                                `blood_sugar`, `heart_rate`, `blood_lipid`, `oxygen_saturation`,
                                `body_temperature`, `remark`, `record_time`, `create_time`) VALUES
(@user_id, 70.5, 175, 120, 80, 5.4, 75, 4.5, 98, 36.5, '正常', NOW() - INTERVAL 1 DAY, NOW()),
(@user_id, 70.2, 175, 118, 78, 5.3, 72, 4.4, 98, 36.4, '正常', NOW() - INTERVAL 3 DAYS, NOW()),
(@user_id, 70.8, 175, 122, 82, 5.5, 76, 4.6, 97, 36.6, '正常', NOW() - INTERVAL 5 DAYS, NOW()),
(@user_id, 70.0, 175, 119, 79, 5.2, 73, 4.3, 98, 36.5, '正常', NOW() - INTERVAL 7 DAYS, NOW()),
(@user_id, 70.3, 175, 121, 81, 5.4, 74, 4.5, 98, 36.5, '正常', NOW() - INTERVAL 10 DAYS, NOW());

-- 4. 插入张三的就医记录
INSERT INTO `medical_visit` (`user_id`, `hospital_name`, `hospital_level`, `department`, `doctor_name`,
                               `visit_date`, `diagnosis`, `prescription`, `medical_expense`, `insurance_reimbursement`,
                               `visit_type`, `data_source`, `create_time`, `update_time`) VALUES
(@user_id, '北京协和医院', '三甲', '内科', '王医生', NOW() - INTERVAL 15 DAYS,
 '轻微感冒，咽喉红肿', '感冒灵颗粒 3盒、阿莫西林 1盒', 156.50, 45.00, 0, 0, NOW(), NOW()),
(@user_id, '北京朝阳医院', '三甲', '全科', '李医生', NOW() - INTERVAL 2 MONTHS,
 '健康体检', '维生素B族 1瓶', 328.00, 0.00, 0, 0, NOW(), NOW()),
(@user_id, '北京同仁医院', '三甲', '眼科', '陈医生', NOW() - INTERVAL 6 MONTHS,
 '视力检查，无异常', '无', 120.00, 36.00, 0, 1, NOW(), NOW());

-- 5. 插入张三的保单
INSERT INTO `user_policy` (`user_id`, `product_id`, `policy_number`, `insurance_company`, `product_name`,
                            `coverage_amount`, `annual_premium`, `insurance_date`, `effective_date`, `expiry_date`,
                            `status`, `insured_name`, `insured_id_card`, `beneficiary_info`, `create_time`, `update_time`) VALUES
(@user_id, 1, 'POL202400001234567', '平安保险', '百万医疗险',
 1000000.00, 365.00, '2024-01-01', '2024-01-15', '2025-01-14',
 1, '张三', '110101199001011234', '法定继承人', NOW(), NOW()),
(@user_id, 2, 'POL202400007654321', '中国人寿', '重疾无忧',
 500000.00, 5000.00, '2024-02-01', '2024-02-15', '2025-02-14',
 1, '张三', '110101199001011234', '张四（配偶）', NOW(), NOW());

-- 6. 验证数据插入结果
SELECT '用户数据' as '数据类型', COUNT(*) as '数量' FROM `user`;
SELECT '用户档案' as '数据类型', COUNT(*) as '数量' FROM `user_profile`;
SELECT '健康记录' as '数据类型', COUNT(*) as '数量' FROM `health_record`;
SELECT '就医记录' as '数据类型', COUNT(*) as '数量' FROM `medical_visit`;
SELECT '用户保单' as '数据类型', COUNT(*) as '数量' FROM `user_policy`;

-- 7. 显示张三的完整信息
SELECT
    u.phone as '手机号',
    u.nickname as '昵称',
    up.real_name as '真实姓名',
    CASE up.gender WHEN 1 THEN '男' WHEN 2 THEN '女' ELSE '未知' END as '性别',
    up.birthday as '出生日期',
    TIMESTAMPDIFF(YEAR, up.birthday, CURDATE()) as '年龄',
    up.address as '地址'
FROM `user` u
LEFT JOIN `user_profile` up ON u.id = up.user_id
WHERE u.phone = '13800138000';
