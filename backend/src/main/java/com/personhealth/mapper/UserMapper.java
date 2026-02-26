package com.personhealth.mapper;

import com.personhealth.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper 接口
 */
@Mapper
public interface UserMapper {

    /**
     * 根据ID查询用户
     */
    User findById(Long id);

    /**
     * 根据手机号查询用户
     */
    User findByPhone(String phone);

    /**
     * 插入用户
     */
    int insert(User user);

    /**
     * 更新用户
     */
    int update(User user);

    /**
     * 更新最后登录信息
     */
    int updateLastLogin(Long id);
}
