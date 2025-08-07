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
 * @since 2025-06-02
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_notice")
public class WNotice implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("notice_id")
    private String notice_id;

    private String title;

    private String content;

    private LocalDateTime create_time;

    @TableField(exist = false)
    private Boolean is_read;

    public static final String NOTICE_ID = "notice_id";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final String CREATE_TIME = "create_time";

    public static final String IS_READ = "is_read";

    @Override
    public String toString() {
        return "W_notice{" +
        "notice_id = " + notice_id +
        ", title = " + title +
        ", content = " + content +
        ", create_time = " + create_time +
        ", is_read = " + is_read +
        "}";
    }
}
