package org.wfq.wufangquan.entity.regen;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Centripet
 * @since 2025-06-11
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_group_member")
public class WGroupMember implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("group_id")
    private String group_id;

    private String user_id;

    /**
     * 1普通群员2管理员3群主
     */
    private int group_role;

    private LocalDateTime joined_at;

    public static final String GROUP_ID = "group_id";

    public static final String USER_ID = "user_id";

    public static final String GROUP_ROLE = "group_role";

    public static final String JOINED_AT = "joined_at";

    @Override
    public String toString() {
        return "W_group_member{" +
        "group_id = " + group_id +
        ", user_id = " + user_id +
        ", group_role = " + group_role +
        ", joined_at = " + joined_at +
        "}";
    }
}
