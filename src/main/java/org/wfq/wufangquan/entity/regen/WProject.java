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
@TableName("w_project")
public class WProject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("project_id")
    private String project_id;

    /**
     * *
     */
    private Boolean status;

    private LocalDateTime create_time;

    private String project_name;

    /**
     * *
     */
    private LocalDateTime deadline;

    /**
     * *
     */
    private String description;

    private String current_stage;

    public static final String PROJECT_ID = "project_id";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String PROJECT_NAME = "project_name";

    public static final String DEADLINE = "deadline";

    public static final String DESCRIPTION = "description";

    public static final String CURRENT_STAGE = "current_stage";

    @Override
    public String toString() {
        return "W_project{" +
        "project_id = " + project_id +
        ", status = " + status +
        ", create_time = " + create_time +
        ", project_name = " + project_name +
        ", deadline = " + deadline +
        ", description = " + description +
                ", current_stage = " + current_stage +
        "}";
    }
}
