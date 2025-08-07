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
@TableName("w_annotation")
public class WAnnotation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("annotation_id")
    private String annotation_id;

    private String file_id;

    private LocalDateTime create_time;

    private String content;

    private String user_id;

    /**
     * marker/text/measurement
     */
    private int type;

    private String coordinate;

    public static final String ANNOTATION_ID = "annotation_id";

    public static final String FILE_ID = "file_id";

    public static final String CREATE_TIME = "create_time";

    public static final String CONTENT = "content";

    public static final String USER_ID = "user_id";

    public static final String TYPE = "type";

    public static final String COORDINATE = "coordinate";

    @Override
    public String toString() {
        return "W_annotation{" +
        "annotation_id = " + annotation_id +
        ", file_id = " + file_id +
        ", create_time = " + create_time +
        ", content = " + content +
        ", user_id = " + user_id +
        ", type = " + type +
        ", coordinate = " + coordinate +
        "}";
    }
}
