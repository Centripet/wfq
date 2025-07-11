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
@TableName("w_application_user")
public class WApplicationUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("app_id")
    private String app_id;

    private String user_id;

    private Boolean is_installed;

    private Integer download_progress;

    private LocalDateTime last_open_time;

    public static final String APP_ID = "app_id";

    public static final String USER_ID = "user_id";

    public static final String IS_INSTALLED = "is_installed";

    public static final String DOWNLOAD_PROGRESS = "download_progress";

    public static final String LAST_OPEN_TIME = "last_open_time";

    @Override
    public String toString() {
        return "W_application_user{" +
        "app_id = " + app_id +
        ", user_id = " + user_id +
        ", is_installed = " + is_installed +
        ", download_progress = " + download_progress +
        ", last_open_time = " + last_open_time +
        "}";
    }
}
