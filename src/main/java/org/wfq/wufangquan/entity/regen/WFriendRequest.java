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
 * @since 2025-06-09
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_friend_request")
public class WFriendRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("request_id")
    private String request_id;

    private String sender_id;

    private String receiver_id;

    private int status;

    private String message;

    private LocalDateTime create_time;

    private LocalDateTime handle_time;

    public static final String REQUEST_ID = "request_id";

    public static final String SENDER_ID = "sender_id";

    public static final String RECEIVER_ID = "receiver_id";

    public static final String STATUS = "status";

    public static final String MESSAGE = "message";

    public static final String CREATE_TIME = "create_time";

    public static final String HANDLE_TIME = "handle_time";

    @Override
    public String toString() {
        return "W_friend_request{" +
        "request_id = " + request_id +
        ", sender_id = " + sender_id +
        ", receiver_id = " + receiver_id +
        ", status = " + status +
        ", message = " + message +
        ", create_time = " + create_time +
        ", handle_time = " + handle_time +
        "}";
    }
}
