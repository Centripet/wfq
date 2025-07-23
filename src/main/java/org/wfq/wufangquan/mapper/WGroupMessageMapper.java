package org.wfq.wufangquan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.wfq.wufangquan.entity.regen.SUser;
import org.wfq.wufangquan.entity.regen.WGroupMessage;
import org.wfq.wufangquan.entity.res.TGroupMessage;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Centripet
 * @since 2025-06-09
 */
@Mapper
public interface WGroupMessageMapper extends BaseMapper<WGroupMessage> {
    @Select("""
    SELECT
      t2.user_id,
      t2.account,
      t2.create_time,
      t2.icon,
      t2.account_status,
      t2.role,
      t2.nick_name,
      t2.organization,
      t2.sex
    FROM w_group_member AS t1
    LEFT JOIN w_user AS t2
    ON t1.user_id=t2.user_id
    WHERE t1.group_id=#{groupId}
""")
    List<SUser> getGroupMembers(String groupId);

    @Select("""
    SELECT
        gm.message_id,
        gm.group_id,
        gm.sender_id,
        gm.content,
        gm.type,
        gm.create_time,
        gmr.is_read
    FROM (
        SELECT
            MAX(message_id) AS latest_message_id
        FROM w_group_message
        WHERE group_id IN (
            SELECT group_id
            FROM w_group_member
            WHERE user_id = #{userId}
        )
        GROUP BY group_id
    ) AS latest
    INNER JOIN w_group_message gm
    ON gm.message_id = latest.latest_message_id
    LEFT JOIN w_group_message_read gmr
    ON gm.message_id = latest.latest_message_id
    AND user_id = #{userId}
    ORDER BY gm.create_time DESC;
""")
    List<TGroupMessage> sessionList(String userId);

    @Select("""
        SELECT * FROM w_group_message AS gm
        LEFT JOIN w_group_message_read AS gmr
        ON gm.message_id=gmr.message_id
        WHERE group_id=#{group_id}
        ORDER BY create_time DESC
        LIMIT #{limit} OFFSET #{offset};
""")
    List<Map<String, Object>> messageHistory(String userId, String group_id, Integer limit, int offset);
}
