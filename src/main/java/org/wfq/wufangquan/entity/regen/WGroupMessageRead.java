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
 * @since 2025-06-12
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_group_message_read")
public class WGroupMessageRead implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("message_id")
    private String message_id;

    private String user_id;

    private Boolean is_read;

    /**
     * 0未读1已读
     */
    private LocalDateTime read_time;

    public static final String MESSAGE_ID = "message_id";

    public static final String USER_ID = "user_id";

    public static final String IS_READ = "is_read";

    public static final String READ_TIME = "read_time";

    @Override
    public String toString() {
        return "W_group_message_read{" +
        "message_id = " + message_id +
        ", user_id = " + user_id +
        ", is_read = " + is_read +
        ", read_time = " + read_time +
        "}";
    }
}
