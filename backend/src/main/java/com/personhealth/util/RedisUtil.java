package com.personhealth.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置值
     */
    public void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            log.debug("Redis set 成功，key：{}", key);
        } catch (Exception e) {
            log.error("Redis set 失败，key：{}", key, e);
        }
    }

    /**
     * 设置值（带过期时间）
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
            log.debug("Redis set 成功，key：{}，过期时间：{}", key, timeout);
        } catch (Exception e) {
            log.error("Redis set 失败，key：{}", key, e);
        }
    }

    /**
     * 获取值
     */
    public Object get(String key) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            log.debug("Redis get 成功，key：{}", key);
            return value;
        } catch (Exception e) {
            log.error("Redis get 失败，key：{}", key, e);
            return null;
        }
    }

    /**
     * 删除值
     */
    public void delete(String key) {
        try {
            redisTemplate.delete(key);
            log.debug("Redis delete 成功，key：{}", key);
        } catch (Exception e) {
            log.error("Redis delete 失败，key：{}", key, e);
        }
    }

    /**
     * 检查 key 是否存在
     */
    public boolean exists(String key) {
        try {
            Boolean result = redisTemplate.hasKey(key);
            return result != null && result;
        } catch (Exception e) {
            log.error("Redis exists 失败，key：{}", key, e);
            return false;
        }
    }

    /**
     * 设置过期时间
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        try {
            Boolean result = redisTemplate.expire(key, timeout, unit);
            log.debug("Redis expire 成功，key：{}，过期时间：{}", key, timeout);
            return result != null && result;
        } catch (Exception e) {
            log.error("Redis expire 失败，key：{}", key, e);
            return false;
        }
    }

    /**
     * 获取过期时间
     */
    public long getExpire(String key) {
        try {
            Long result = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            return result != null ? result : -1;
        } catch (Exception e) {
            log.error("Redis getExpire 失败，key：{}", key, e);
            return -1;
        }
    }
}
