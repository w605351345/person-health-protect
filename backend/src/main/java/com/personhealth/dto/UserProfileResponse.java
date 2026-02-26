package com.personhealth.dto;

import com.personhealth.entity.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 用户档案响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户档案响应")
public class UserProfileResponse {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "身份证号（脱敏）", example = "110101********1234")
    private String idCardNumberMasked;

    @Schema(description = "身份证正面照片")
    private String idCardFrontPhoto;

    @Schema(description = "身份证背面照片")
    private String idCardBackPhoto;

    @Schema(description = "性别：0-未知，1-男，2-女")
    private Integer gender;

    @Schema(description = "出生日期")
    private LocalDate birthday;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "民族")
    private String nation;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "紧急联系人姓名")
    private String emergencyContactName;

    @Schema(description = "紧急联系人电话（脱敏）", example = "139****9000")
    private String emergencyContactPhoneMasked;
}
