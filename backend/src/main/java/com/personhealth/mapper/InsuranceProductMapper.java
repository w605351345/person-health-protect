package com.personhealth.mapper;

import com.personhealth.entity.InsuranceProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 保险产品 Mapper 接口
 */
@Mapper
public interface InsuranceProductMapper {

    /**
     * 根据ID查询产品
     */
    InsuranceProduct findById(Long id);

    /**
     * 根据产品类型查询产品
     */
    List<InsuranceProduct> findByType(Integer productType);

    /**
     * 根据年龄范围查询产品
     */
    List<InsuranceProduct> findByAgeRange(@Param("minAge") int minAge, @Param("maxAge") int maxAge);

    /**
     * 查询所有上架产品
     */
    List<InsuranceProduct> findAll();

    /**
     * 插入产品
     */
    int insert(InsuranceProduct product);

    /**
     * 更新产品
     */
    int update(InsuranceProduct product);

    /**
     * 删除产品
     */
    int delete(Long id);
}
