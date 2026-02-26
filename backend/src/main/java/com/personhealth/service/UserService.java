package com.personhealth.service;

import com.personhealth.dto.HealthRecordRequest;
import com.personhealth.dto.HealthTrendRequest;
import com.personhealth.entity.HealthRecord;
import com.personhealth.entity.User;
import com.personhealth.dto.UserProfileResponse;
import com.personhealth.dto.UserProfileRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 获取当前用户
     */
    User getCurrentUser();

    /**
     * 更新用户信息
     */
    void updateUser(User user);

    /**
     * 绑定身份证
     */
    void bindIdCard(UserProfileRequest request);

    /**
     * 上传身份证照片
     */
    String uploadIdCardPhoto(MultipartFile file, Integer type);

    /**
     * 获取用户档案
     */
    UserProfileResponse getUserProfile();

    /**
     * 更新用户档案
     */
    void updateUserProfile(UserProfileRequest request);
}
