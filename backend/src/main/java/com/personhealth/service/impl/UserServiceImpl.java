package com.personhealth.service.impl;

import com.personhealth.dto.UserProfileRequest;
import com.personhealth.dto.UserProfileResponse;
import com.personhealth.entity.User;
import com.personhealth.entity.UserProfile;
import com.personhealth.mapper.UserMapper;
import com.personhealth.mapper.UserProfileMapper;
import com.personhealth.security.SecurityContextHolder;
import com.personhealth.service.UserService;
import com.personhealth.util.FileUtil;
import com.personhealth.util.IdCardUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserProfileMapper userProfileMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getCurrentUser() {
        Long userId = SecurityContextHolder.getUserId();
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // 不返回密码
        user.setPassword(null);
        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        Long userId = SecurityContextHolder.getUserId();
        User existingUser = userMapper.findById(userId);
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 只更新允许修改的字段
        existingUser.setNickname(user.getNickname());
        existingUser.setAvatar(user.getAvatar());

        userMapper.update(existingUser);
        log.info("用户信息更新成功，用户ID：{}", userId);
    }

    @Override
    @Transactional
    public void bindIdCard(UserProfileRequest request) {
        Long userId = SecurityContextHolder.getUserId();

        // 检查是否已绑定
        UserProfile existingProfile = userProfileMapper.findByUserId(userId);
        if (existingProfile != null) {
            throw new RuntimeException("已绑定身份证，不可重复绑定");
        }

        // 检查身份证号是否已被其他用户绑定
        UserProfile otherProfile = userProfileMapper.findByIdCardNumber(request.getIdCardNumber());
        if (otherProfile != null) {
            throw new RuntimeException("该身份证号已被绑定");
        }

        // 从身份证号提取信息
        LocalDate birthday = IdCardUtil.extractBirthday(request.getIdCardNumber());
        Integer gender = IdCardUtil.extractGender(request.getIdCardNumber());

        // 创建用户档案
        UserProfile profile = UserProfile.builder()
                .userId(userId)
                .realName(request.getRealName())
                .idCardNumber(request.getIdCardNumber())
                .gender(gender)
                .birthday(birthday)
                .nation(request.getNation())
                .address(request.getAddress())
                .emergencyContactName(request.getEmergencyContactName())
                .emergencyContactPhone(request.getEmergencyContactPhone())
                .build();

        userProfileMapper.insert(profile);
        log.info("用户绑定身份证成功，用户ID：{}", userId);
    }

    @Override
    public String uploadIdCardPhoto(MultipartFile file, Integer type) {
        // 验证文件类型（仅允许身份证照片）
        if (!FileUtil.isIdCardPhoto(file)) {
            throw new RuntimeException("仅支持上传身份证照片");
        }

        // 上传文件
        String url = FileUtil.uploadFile(file, "idcard");

        Long userId = SecurityContextHolder.getUserId();
        UserProfile profile = userProfileMapper.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("请先绑定身份证信息");
        }

        // 更新照片路径
        if (type == 1) {
            profile.setIdCardFrontPhoto(url);
        } else if (type == 2) {
            profile.setIdCardBackPhoto(url);
        } else {
            throw new RuntimeException("照片类型错误");
        }

        userProfileMapper.update(profile);
        log.info("身份证照片上传成功，用户ID：{}", userId);

        return url;
    }

    @Override
    public UserProfileResponse getUserProfile() {
        Long userId = SecurityContextHolder.getUserId();
        UserProfile profile = userProfileMapper.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("用户档案不存在");
        }

        User user = userMapper.findById(userId);

        // 计算年龄
        int age = Period.between(profile.getBirthday(), LocalDate.now()).getYears();

        // 手机号脱敏
        String phoneMasked = user.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");

        // 身份证号脱敏
        String idCardMasked = profile.getIdCardNumber().replaceAll("(\\d{6})\\d{8}(\\d{4})", "$1********$2");

        // 紧急联系人电话脱敏
        String emergencyPhoneMasked = null;
        if (profile.getEmergencyContactPhone() != null) {
            emergencyPhoneMasked = profile.getEmergencyContactPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }

        return UserProfileResponse.builder()
                .userId(userId)
                .realName(profile.getRealName())
                .idCardNumberMasked(idCardMasked)
                .idCardFrontPhoto(profile.getIdCardFrontPhoto())
                .idCardBackPhoto(profile.getIdCardBackPhoto())
                .gender(profile.getGender())
                .birthday(profile.getBirthday())
                .age(age)
                .nation(profile.getNation())
                .address(profile.getAddress())
                .emergencyContactName(profile.getEmergencyContactName())
                .emergencyContactPhoneMasked(emergencyPhoneMasked)
                .build();
    }

    @Override
    @Transactional
    public void updateUserProfile(UserProfileRequest request) {
        Long userId = SecurityContextHolder.getUserId();
        UserProfile profile = userProfileMapper.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("用户档案不存在");
        }

        // 更新档案信息
        profile.setRealName(request.getRealName());
        profile.setNation(request.getNation());
        profile.setAddress(request.getAddress());
        profile.setEmergencyContactName(request.getEmergencyContactName());
        profile.setEmergencyContactPhone(request.getEmergencyContactPhone());

        userProfileMapper.update(profile);
        log.info("用户档案更新成功，用户ID：{}", userId);
    }
}
