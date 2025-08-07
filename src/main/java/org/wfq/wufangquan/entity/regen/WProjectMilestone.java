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
@TableName("w_project_milestone")
public class WProjectMilestone implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String project_id;

    private String stage_id;

    @TableId("task_id")
    private String task_id;

    private String mile_id;

    private String mile_name;

    private LocalDateTime create_time;

    public static final String PROJECT_ID = "project_id";

    public static final String STAGE_ID = "stage_id";

    public static final String TASK_ID = "task_id";

    public static final String MILE_ID = "mile_id";

    public static final String MILE_NAME = "mile_name";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "W_project_milestone{" +
        "project_id = " + project_id +
        ", stage_id = " + stage_id +
        ", task_id = " + task_id +
        ", mile_id = " + mile_id +
        ", mile_name = " + mile_name +
        ", create_time = " + create_time +
        "}";
    }
}
