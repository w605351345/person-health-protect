package com.personhealth.service;

import com.personhealth.dto.LoginRequest;
import com.personhealth.dto.RegisterRequest;
import com.personhealth.dto.TokenResponse;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户注册
     */
    void register(RegisterRequest request);

    /**
     * 用户登录
     */
    TokenResponse login(LoginRequest request);

    /**
     * 刷新Token
     */
    TokenResponse refreshToken(String refreshToken);

    /**
     * 用户登出
     */
    void logout(String token);
}
