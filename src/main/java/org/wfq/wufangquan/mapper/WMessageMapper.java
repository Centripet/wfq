package org.wfq.wufangquan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.wfq.wufangquan.entity.regen.WMessage;
import org.wfq.wufangquan.entity.res.TMessage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Centripet
 * @since 2025-06-09
 */
@Mapper
public interface WMessageMapper extends BaseMapper<WMessage> {

    @Select("""
    SELECT
        sub.message_id,
        m.sender_id,
        m.receiver_id,
        m.content,
        m.type,
        m.create_time,
        m.is_read,
        CASE
            WHEN m.sender_id = #{userId} THEN m.receiver_id
            ELSE m.sender_id
        END AS session_user_id
    FROM (
        SELECT
            MAX(message_id) AS message_id,
            CASE
                WHEN sender_id < receiver_id THEN CONCAT(sender_id, '_', receiver_id)
                ELSE CONCAT(receiver_id, '_', sender_id)
            END AS session_key
        FROM w_message
        WHERE sender_id = #{userId} OR receiver_id = #{userId}
        GROUP BY session_key
    ) sub
    JOIN w_message m ON m.message_id = sub.message_id
    ORDER BY m.create_time DESC;
""")
    List<TMessage> sessionList(String userId);


    @Select("""
        SELECT * FROM w_message
        WHERE sender_id IN (#{userId},#{receiver_id})
        AND receiver_id IN (#{userId},#{receiver_id})
        ORDER BY create_time DESC
        LIMIT #{limit} OFFSET #{offset};
""")
    Object messageHistory(String userId,String receiver_id, Integer size, int offset);
}
