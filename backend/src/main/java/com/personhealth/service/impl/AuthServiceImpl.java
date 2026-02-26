package com.personhealth.service.impl;

import com.personhealth.dto.LoginRequest;
import com.personhealth.dto.RegisterRequest;
import com.personhealth.dto.TokenResponse;
import com.personhealth.entity.User;
import com.personhealth.mapper.UserMapper;
import com.personhealth.service.AuthService;
import com.personhealth.util.JwtUtil;
import com.personhealth.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        // 检查手机号是否已存在
        User existingUser = userMapper.findByPhone(request.getPhone());
        if (existingUser != null) {
            throw new RuntimeException("手机号已注册");
        }

        // 创建新用户
        User user = User.builder()
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname("用户" + request.getPhone().substring(7))
                .status(1)
                .build();

        userMapper.insert(user);
        log.info("用户注册成功，手机号：{}", request.getPhone());
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        // 查找用户
        User user = userMapper.findByPhone(request.getPhone());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用");
        }

        // 生成 Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("phone", user.getPhone());

        String accessToken = jwtUtil.generateToken(claims, jwtUtil.getExpiration());
        String refreshToken = jwtUtil.generateToken(claims, jwtUtil.getRefreshExpiration());

        // 保存 RefreshToken 到 Redis
        redisUtil.set(REFRESH_TOKEN_PREFIX + user.getId(), refreshToken, jwtUtil.getRefreshExpiration(), TimeUnit.MILLISECONDS);

        // 更新最后登录信息
        userMapper.updateLastLogin(user.getId());

        log.info("用户登录成功，手机号：{}", request.getPhone());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getExpiration())
                .build();
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        // 验证 RefreshToken
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("RefreshToken 无效");
        }

        // 提取用户信息
        Long userId = jwtUtil.getUserIdFromToken(refreshToken);
        User user = userMapper.findById(userId);
        if (user == null || user.getStatus() == 0) {
            throw new RuntimeException("用户不存在或已被禁用");
        }

        // 验证 Redis 中的 RefreshToken
        String storedToken = redisUtil.get(REFRESH_TOKEN_PREFIX + userId);
        if (!refreshToken.equals(storedToken)) {
            throw new RuntimeException("RefreshToken 不匹配");
        }

        // 生成新的 AccessToken
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("phone", user.getPhone());

        String newAccessToken = jwtUtil.generateToken(claims, jwtUtil.getExpiration());

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getExpiration())
                .build();
    }

    @Override
    public void logout(String token) {
        try {
            Long userId = jwtUtil.getUserIdFromToken(token);
            // 删除 Redis 中的 RefreshToken
            redisUtil.delete(REFRESH_TOKEN_PREFIX + userId);
            log.info("用户登出成功，用户ID：{}", userId);
        } catch (Exception e) {
            log.error("登出失败", e);
        }
    }
}
