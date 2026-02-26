package com.personhealth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 注册请求DTO
 */
@Data
@Schema(description = "用户注册请求")
public class RegisterRequest {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,20}$", message = "密码必须包含字母和数字，长度6-20位")
    @Schema(description = "密码", example = "password123")
    private String password;

    @Schema(description = "验证码", example = "123456")
    private String verificationCode;
}
