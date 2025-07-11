package org.wfq.wufangquan.entity.regen;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("w_circle")
public class WCircle implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("circle_id")
    private String circle_id;

    private String title;

    private String content;

    private LocalDateTime create_time;

    private Boolean is_public;

    private Integer view_count;

    private String publisher;

    private LocalDateTime update_time;

    @TableField(exist = false)
    private String project_id;
    /**
     * 1文本2文件3图片4视频
     */
    private Integer type;

    private Boolean is_deleted;

    public static final String CIRCLE_ID = "circle_id";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final String CREATE_TIME = "create_time";

    public static final String IS_PUBLIC = "is_public";

    public static final String VIEW_COUNT = "view_count";

    public static final String PUBLISHER = "publisher";

    public static final String TYPE = "type";

    @Override
    public String toString() {
        return "W_circle{" +
        "circle_id = " + circle_id +
        ", title = " + title +
        ", content = " + content +
        ", create_time = " + create_time +
        ", is_public = " + is_public +
        ", view_count = " + view_count +
        ", publisher = " + publisher +
        ", type = " + type +
        "}";
    }
}
