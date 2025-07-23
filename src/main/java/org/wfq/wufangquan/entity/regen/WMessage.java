package org.wfq.wufangquan.entity.regen;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@TableName("w_message")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("message_id")
    private String message_id;

    private String sender_id;

    private String receiver_id;

    private String content;

    private int type;

    private LocalDateTime create_time;

    private String link_url;

    private String file_url;

    private Boolean is_read;

    private String file_id;

    private String file_key;

    private String preview_url;

    public static final String MESSAGE_ID = "message_id";

    public static final String SENDER_ID = "sender_id";

    public static final String RECEIVER_ID = "receiver_id";

    public static final String CONTENT = "content";

    public static final String TYPE = "type";

    public static final String CREATE_TIME = "create_time";

    public static final String LINK_URL = "link_url";

    public static final String FILE_URL = "file_url";

    public static final String IS_READ = "is_read";

    @Override
    public String toString() {
        return "W_message{" +
        "message_id = " + message_id +
        ", sender_id = " + sender_id +
        ", receiver_id = " + receiver_id +
        ", content = " + content +
        ", type = " + type +
        ", create_time = " + create_time +
        ", link_url = " + link_url +
        ", file_url = " + file_url +
        ", is_read = " + is_read +
                ", file_id = " + file_id +
                ", file_key = " + file_key +
                ", preview_url = " + preview_url +
        "}";
    }
}
