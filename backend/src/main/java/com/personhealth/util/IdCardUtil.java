package com.personhealth.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 身份证工具类
 */
public class IdCardUtil {

    private static final int LENGTH_18 = 18;
    private static final int LENGTH_15 = 15;

    /**
     * 验证身份证号格式
     */
    public static boolean isValid(String idCard) {
        if (idCard == null || idCard.isEmpty()) {
            return false;
        }

        String id = idCard.trim();

        if (id.length() == LENGTH_18) {
            return validate18IdCard(id);
        } else if (id.length() == LENGTH_15) {
            return validate15IdCard(id);
        }

        return false;
    }

    /**
     * 验证 18 位身份证号
     */
    private static boolean validate18IdCard(String idCard) {
        // 校验前 17 位是否为数字
        if (!idCard.substring(0, 17).matches("\\d+")) {
            return false;
        }

        // 校验校验码
        String verifyCode = getVerifyCode(idCard.substring(0, 17));
        if (!idCard.substring(17).equalsIgnoreCase(verifyCode)) {
            return false;
        }

        // 校验出生日期
        return validateBirthday(idCard.substring(6, 14));
    }

    /**
     * 验证 15 位身份证号
     */
    private static boolean validate15IdCard(String idCard) {
        // 校验是否为数字
        if (!idCard.matches("\\d+")) {
            return false;
        }

        // 校验出生日期
        return validateBirthday("19" + idCard.substring(6, 12));
    }

    /**
     * 校验出生日期
     */
    private static boolean validateBirthday(String birthdayStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthday = LocalDate.parse(birthdayStr, formatter);

            // 检查是否在合理范围内
            LocalDate now = LocalDate.now();
            LocalDate minDate = now.minusYears(120);
            LocalDate maxDate = now;

            return !birthday.isBefore(minDate) && !birthday.isAfter(maxDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * 计算校验码
     */
    private static String getVerifyCode(String id17) {
        int[] factor = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        String[] verifyCodes = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += Integer.parseInt(id17.substring(i, i + 1)) * factor[i];
        }

        return verifyCodes[sum % 11];
    }

    /**
     * 提取出生日期
     */
    public static LocalDate extractBirthday(String idCard) {
        String birthdayStr;

        if (idCard.length() == LENGTH_18) {
            birthdayStr = idCard.substring(6, 14);
        } else if (idCard.length() == LENGTH_15) {
            birthdayStr = "19" + idCard.substring(6, 12);
        } else {
            throw new IllegalArgumentException("身份证号格式错误");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(birthdayStr, formatter);
    }

    /**
     * 提取性别
     * @return 1-男，2-女
     */
    public static Integer extractGender(String idCard) {
        String genderCode;

        if (idCard.length() == LENGTH_18) {
            genderCode = idCard.substring(16, 17);
        } else if (idCard.length() == LENGTH_15) {
            genderCode = idCard.substring(14, 15);
        } else {
            throw new IllegalArgumentException("身份证号格式错误");
        }

        return Integer.parseInt(genderCode) % 2 == 0 ? 2 : 1;
    }

    /**
     * 脱敏身份证号
     */
    public static String mask(String idCard) {
        if (idCard == null || idCard.length() < 8) {
            return idCard;
        }

        int length = idCard.length();
        if (length == LENGTH_18) {
            return idCard.substring(0, 6) + "********" + idCard.substring(14);
        } else if (length == LENGTH_15) {
            return idCard.substring(0, 6) + "*****" + idCard.substring(11);
        }

        return idCard;
    }
}
