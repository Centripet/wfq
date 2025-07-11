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
 * @since 2025-07-12
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_project_task")
public class WProjectTask implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("task_id")
    private String task_id;

    private String stage_id;

    private String project_id;

    private String task_name;

    /**
     * 0未开始1进行中2已完成
     */
    private Boolean status;

    private LocalDateTime deadline;

    private String description;

    private LocalDateTime create_time;

    public static final String TASK_ID = "task_id";

    public static final String STAGE_ID = "stage_id";

    public static final String PROJECT_ID = "project_id";

    public static final String TASK_NAME = "task_name";

    public static final String STATUS = "status";

    public static final String DEADLINE = "deadline";

    public static final String DESCRIPTION = "description";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "W_project_task{" +
        "task_id = " + task_id +
        ", stage_id = " + stage_id +
        ", project_id = " + project_id +
        ", task_name = " + task_name +
        ", status = " + status +
        ", deadline = " + deadline +
        ", description = " + description +
        ", create_time = " + create_time +
        "}";
    }
}
