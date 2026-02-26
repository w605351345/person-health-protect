package com.personhealth.mapper;

import com.personhealth.entity.UserPolicy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户保单 Mapper 接口
 */
@Mapper
public interface UserPolicyMapper {

    /**
     * 根据ID查询保单
     */
    UserPolicy findById(Long id);

    /**
     * 根据用户ID查询保单
     */
    List<UserPolicy> findByUserId(Long userId);

    /**
     * 根据保单号查询保单
     */
    UserPolicy findByPolicyNumber(String policyNumber);

    /**
     * 插入保单
     */
    int insert(UserPolicy policy);

    /**
     * 更新保单
     */
    int update(UserPolicy policy);

    /**
     * 删除保单
     */
    int delete(Long id);
}
