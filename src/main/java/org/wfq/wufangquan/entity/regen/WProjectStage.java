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
@TableName("w_project_stage")
public class WProjectStage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("stage_id")
    private String stage_id;

    private String project_id;

    private String stage_name;

    private LocalDateTime create_time;

    public static final String STAGE_ID = "stage_id";

    public static final String PROJECT_ID = "project_id";

    public static final String STAGE_NAME = "stage_name";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "W_project_stage{" +
        "stage_id = " + stage_id +
        ", project_id = " + project_id +
        ", stage_name = " + stage_name +
        ", create_time = " + create_time +
        "}";
    }
}
