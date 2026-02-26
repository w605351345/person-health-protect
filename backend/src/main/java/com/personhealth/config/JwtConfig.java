package com.personhealth.config;

import com.personhealth.security.JwtAuthenticationFilter;
import com.personhealth.security.JwtAuthenticationEntryPoint;
import com.personhealth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JWT 配置类
 */
@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtUtil jwtUtil;

    /**
     * JWT 认证过滤器
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    /**
     * JWT 认证入口点
     */
    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }
}
