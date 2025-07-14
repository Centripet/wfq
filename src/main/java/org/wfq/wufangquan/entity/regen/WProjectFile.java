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
@TableName("w_about")
public class WProjectFile implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("task_id")
    private String task_id;

    private String file_id;

    private LocalDateTime create_time;

    public static final String TASK_ID = "task_id";

    public static final String FILE_ID = "file_id";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "W_project_file{" +
        "task_id = " + task_id +
        ", file_id = " + file_id +
        ", create_time = " + create_time +
        "}";
    }
}
