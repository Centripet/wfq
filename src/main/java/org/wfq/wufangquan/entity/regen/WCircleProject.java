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
@TableName("w_circle_project")
public class WCircleProject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("circle_id")
    private String circle_id;

    private String project_id;

    private LocalDateTime create_time;

    public static final String CIRCLE_ID = "circle_id";

    public static final String PROJECT_ID = "project_id";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "W_circle_project{" +
        "circle_id = " + circle_id +
        ", project_id = " + project_id +
        ", create_time = " + create_time +
        "}";
    }
}
