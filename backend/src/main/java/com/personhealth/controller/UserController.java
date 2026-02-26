package com.personhealth.controller;

import com.personhealth.dto.UserProfileRequest;
import com.personhealth.dto.UserProfileResponse;
import com.personhealth.entity.User;
import com.personhealth.service.UserService;
import com.personhealth.vo.PageResult;
import com.personhealth.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户管理控制器
 */
@Tag(name = "用户管理", description = "用户信息管理、档案管理等接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 获取当前用户信息
     */
    @Operation(summary = "获取当前用户信息", description = "获取登录用户的详细信息")
    @GetMapping("/profile")
    public Result<User> getCurrentUser() {
        User user = userService.getCurrentUser();
        return Result.success(user);
    }

    /**
     * 更新用户信息
     */
    @Operation(summary = "更新用户信息", description = "更新用户的昵称、头像等信息")
    @PutMapping("/profile")
    public Result<Void> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success();
    }

    /**
     * 绑定身份证
     */
    @Operation(summary = "绑定身份证", description = "绑定用户身份证信息")
    @PostMapping("/bind-idcard")
    public Result<Void> bindIdCard(@Valid @RequestBody UserProfileRequest request) {
        userService.bindIdCard(request);
        return Result.success();
    }

    /**
     * 上传身份证照片
     */
    @Operation(summary = "上传身份证照片", description = "上传身份证正反面照片")
    @PostMapping("/upload-idcard")
    public Result<String> uploadIdCard(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") Integer type) {
        String url = userService.uploadIdCardPhoto(file, type);
        return Result.success(url);
    }

    /**
     * 获取用户档案
     */
    @Operation(summary = "获取用户档案", description = "获取用户详细档案信息")
    @GetMapping("/profile-detail")
    public Result<UserProfileResponse> getUserProfile() {
        UserProfileResponse profile = userService.getUserProfile();
        return Result.success(profile);
    }

    /**
     * 更新用户档案
     */
    @Operation(summary = "更新用户档案", description = "更新用户档案信息")
    @PutMapping("/profile-detail")
    public Result<Void> updateUserProfile(@RequestBody UserProfileRequest request) {
        userService.updateUserProfile(request);
        return Result.success();
    }
}
