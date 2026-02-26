package com.personhealth.mapper;

import com.personhealth.entity.HealthRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 健康记录 Mapper 接口
 */
@Mapper
public interface HealthRecordMapper {

    /**
     * 根据ID查询记录
     */
    HealthRecord findById(Long id);

    /**
     * 根据用户ID查询记录（分页）
     */
    List<HealthRecord> findByUserId(@Param("userId") Long userId, @Param("offset") int offset, @Param("size") int size);

    /**
     * 根据用户ID和时间范围查询记录
     */
    List<HealthRecord> findByUserIdAndTimeRange(@Param("userId") Long userId,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);

    /**
     * 查询最近的记录
     */
    List<HealthRecord> findRecent(@Param("userId") Long userId, @Param("limit") int limit);

    /**
     * 插入记录
     */
    int insert(HealthRecord record);

    /**
     * 更新记录
     */
    int update(HealthRecord record);

    /**
     * 删除记录
     */
    int delete(Long id);
}
