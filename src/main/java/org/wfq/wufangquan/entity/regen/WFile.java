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
 * @since 2025-06-21
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_file")
public class WFile implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("file_id")
    private String file_id;

    private String file_key;

    private Boolean is_public_read;

    private String uploader;

    private LocalDateTime create_time;

    private Integer type;

    private String suffix;

    private String title;

    private String info;

    private String origin_name;

    private long file_size;

    @TableField(exist = false)
    private String oss_url;

    @TableField(exist = false)
    private String preview_url;

    public static final String FILE_ID = "file_id";

    public static final String FILE_KEY = "file_key";

    public static final String IS_PUBLIC_READ = "is_public_read";

    public static final String UPLOADER = "uploader";

    public static final String CREATE_TIME = "create_time";

    public static final String TYPE = "type";

    public static final String SUFFIX = "suffix";

    public static final String TITLE = "title";

    public static final String INFO = "info";

    public static final String ORIGIN_NAME = "origin_name";

    @Override
    public String toString() {
        return "W_file{" +
        "file_id = " + file_id +
        ", file_key = " + file_key +
        ", is_public_read = " + is_public_read +
        ", uploader = " + uploader +
        ", create_time = " + create_time +
        ", type = " + type +
        ", suffix = " + suffix +
        ", title = " + title +
        ", info = " + info +
        ", info = " + origin_name +
        "}";
    }
}
