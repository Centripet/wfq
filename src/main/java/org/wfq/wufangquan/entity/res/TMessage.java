package org.wfq.wufangquan.entity.res;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message_id;
    private String sender_id;
    private String receiver_id;
    private String content;
    private int type;
    private LocalDateTime create_time;
    private Boolean is_read;
    private String session_user_id;

}
