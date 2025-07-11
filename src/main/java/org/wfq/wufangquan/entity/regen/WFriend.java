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
@TableName("w_friend")
public class WFriend implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private String user_id;

    private String friend_id;

    private LocalDateTime create_time;

    public static final String USER_ID = "user_id";

    public static final String FRIEND_ID = "friend_id";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "W_friend{" +
        "user_id = " + user_id +
        ", friend_id = " + friend_id +
        ", create_time = " + create_time +
        "}";
    }
}
