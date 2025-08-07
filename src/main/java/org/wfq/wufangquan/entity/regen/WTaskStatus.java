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
 * @since 2025-08-01
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_task_status")
public class WTaskStatus implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("status_id")
    private String status_id;

    private String task_id;

    /**
     * ACCEPT/REJECT/START/COMPLETE
     */
    private String action;

    private String comment;

    private String location;

    private LocalDateTime create_time;

    private String department;

    private String user_id;

    private String status;

    public static final String STATUS_ID = "status_id";

    public static final String TASK_ID = "task_id";

    public static final String ACTION = "action";

    public static final String COMMENT = "comment";

    public static final String LOCATION = "location";

    public static final String CREATE_TIME = "create_time";

    public static final String DEPARTMENT = "apartment";

    public static final String USER_ID = "user_id";

    public static final String STATUS = "status";

    @Override
    public String toString() {
        return "W_task_status{" +
                "status_id=" + status_id +
        "task_id = " + task_id +
        ", action = " + action +
        ", comment = " + comment +
        ", location = " + location +
        ", create_time = " + create_time +
        ", apartment = " + department +
        ", user_id = " + user_id +
        ", status = " + status +
        "}";
    }
}
