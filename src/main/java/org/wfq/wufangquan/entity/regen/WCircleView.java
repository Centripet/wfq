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
 * @since 2025-06-17
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_circle_view")
public class WCircleView implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("circle_id")
    private String circle_id;

    private String user_id;

    private LocalDateTime create_time;

    public static final String CIRCLE_ID = "circle_id";

    public static final String USER_ID = "user_id";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "W_circle_view{" +
        "circle_id = " + circle_id +
        ", user_id = " + user_id +
        ", create_time = " + create_time +
        "}";
    }
}
