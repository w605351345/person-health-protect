package com.personhealth.mapper;

import com.personhealth.entity.MedicalVisit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 就医记录 Mapper 接口
 */
@Mapper
public interface MedicalVisitMapper {

    /**
     * 根据ID查询记录
     */
    MedicalVisit findById(Long id);

    /**
     * 根据用户ID查询记录
     */
    List<MedicalVisit> findByUserId(Long userId);

    /**
     * 根据用户ID和时间范围查询记录
     */
    List<MedicalVisit> findByUserIdAndTimeRange(@Param("userId") Long userId,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

    /**
     * 根据用户ID和数据来源查询记录
     */
    List<MedicalVisit> findByUserIdAndDataSource(@Param("userId") Long userId, @Param("dataSource") Integer dataSource);

    /**
     * 插入记录
     */
    int insert(MedicalVisit visit);

    /**
     * 更新记录
     */
    int update(MedicalVisit visit);

    /**
     * 删除记录
     */
    int delete(Long id);
}
