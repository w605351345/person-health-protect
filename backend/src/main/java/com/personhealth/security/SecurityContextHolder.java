package com.personhealth.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security 上下文持有者
 * 用于从 Security 上下文中获取当前用户信息
 */
public class SecurityContextHolder {

    /**
     * 获取当前用户 ID
     */
    public static Long getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Long) {
            return (Long) principal;
        }
        throw new RuntimeException("未登录");
    }

    /**
     * 检查用户是否已登录
     */
    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
               SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
