package com.personhealth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

/**
 * 用户档案请求DTO
 */
@Data
@Schema(description = "用户档案请求")
public class UserProfileRequest {

    @Schema(description = "真实姓名", example = "张三")
    private String realName;

    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$", message = "身份证号格式不正确")
    @Schema(description = "身份证号", example = "110101199001011234")
    private String idCardNumber;

    @Schema(description = "性别：0-未知，1-男，2-女", example = "1")
    private Integer gender;

    @Schema(description = "出生日期", example = "1990-01-01")
    private LocalDate birthday;

    @Schema(description = "民族", example = "汉族")
    private String nation;

    @Schema(description = "地址", example = "北京市朝阳区")
    private String address;

    @Schema(description = "紧急联系人姓名", example = "李四")
    private String emergencyContactName;

    @Schema(description = "紧急联系人电话", example = "13900139000")
    private String emergencyContactPhone;
}
