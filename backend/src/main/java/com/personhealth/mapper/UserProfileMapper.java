package com.personhealth.mapper;

import com.personhealth.entity.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户档案 Mapper 接口
 */
@Mapper
public interface UserProfileMapper {

    /**
     * 根据ID查询档案
     */
    UserProfile findById(Long id);

    /**
     * 根据用户ID查询档案
     */
    UserProfile findByUserId(Long userId);

    /**
     * 根据身份证号查询档案
     */
    UserProfile findByIdCardNumber(String idCardNumber);

    /**
     * 插入档案
     */
    int insert(UserProfile profile);

    /**
     * 更新档案
     */
    int update(UserProfile profile);
}
