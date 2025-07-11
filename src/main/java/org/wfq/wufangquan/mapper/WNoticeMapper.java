package org.wfq.wufangquan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.wfq.wufangquan.entity.regen.WNotice;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Centripet
 * @since 2025-06-02
 */
@Mapper
public interface WNoticeMapper extends BaseMapper<WNotice> {
    @Select("""
        SELECT
        a.notice_id,
        a.title,
            CASE
                WHEN LENGTH(a.content) > 50 THEN CONCAT(SUBSTRING(a.content, 1, 50), '...')
                ELSE a.content
            END AS content,
            a.create_time,
            CASE
                WHEN b.notice_id IS NOT NULL THEN TRUE
                ELSE FALSE
            END AS is_read
        FROM w_notice a
        LEFT JOIN (SELECT * FROM w_notice_read WHERE user_id = #{user_id}) b
        ON a.notice_id = b.notice_id
        ORDER BY a.create_time DESC
        LIMIT #{limit} OFFSET #{offset};
    """)
    List<WNotice> getNoticesWithReadStatus(
            @Param("user_id") String user_id,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Select("""
        SELECT
        a.*,
            CASE
                WHEN b.notice_id IS NOT NULL THEN TRUE
                ELSE FALSE
            END AS is_read
        FROM w_notice a
        LEFT JOIN (SELECT * FROM w_notice_read WHERE user_id = #{user_id}) b
        ON a.notice_id = b.notice_id
        WHERE a.notice_id = #{notice_id}
        LIMIT 1
    """)
    Optional<WNotice> getNoticeByNoticeIdWithReadStatus(
            @Param("user_id") String user_id,
            @Param("notice_id") String notice_id
    );
}
