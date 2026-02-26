package com.personhealth.controller;

import com.personhealth.dto.LoginRequest;
import com.personhealth.dto.RegisterRequest;
import com.personhealth.dto.TokenResponse;
import com.personhealth.service.AuthService;
import com.personhealth.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@Tag(name = "用户认证", description = "用户注册、登录、登出等接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户注册
     */
    @Operation(summary = "用户注册", description = "通过手机号注册新用户")
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return Result.success();
    }

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "通过手机号密码登录")
    @PostMapping("/login")
    public Result<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);
        return Result.success(response);
    }

    /**
     * 刷新Token
     */
    @Operation(summary = "刷新Token", description = "使用RefreshToken获取新的AccessToken")
    @PostMapping("/refresh-token")
    public Result<TokenResponse> refreshToken(@RequestHeader("Refresh-Token") String refreshToken) {
        TokenResponse response = authService.refreshToken(refreshToken);
        return Result.success(response);
    }

    /**
     * 用户登出
     */
    @Operation(summary = "用户登出", description = "用户退出登录")
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return Result.success();
    }
}
