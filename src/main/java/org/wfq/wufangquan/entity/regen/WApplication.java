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
@TableName("w_application")
public class WApplication implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("app_id")
    private String app_id;

    private String app_name;

    private String description;

    private String icon;

    private Integer install_count;

    private String download_url;

    private LocalDateTime create_time;

    /**
     * *
     */
    private Boolean is_install;

    /**
     * *
     */
    private Boolean is_available;

    public static final String APP_ID = "app_id";

    public static final String APP_NAME = "app_name";

    public static final String DESCRIPTION = "description";

    public static final String ICON = "icon";

    public static final String INSTALL_COUNT = "install_count";

    public static final String DOWNLOAD_URL = "download_url";

    public static final String CREATE_TIME = "create_time";

    public static final String IS_INSTALL = "is_install";

    public static final String IS_AVAILABLE = "is_available";

    @Override
    public String toString() {
        return "W_application{" +
        "app_id = " + app_id +
        ", app_name = " + app_name +
        ", description = " + description +
        ", icon = " + icon +
        ", install_count = " + install_count +
        ", download_url = " + download_url +
        ", create_time = " + create_time +
        ", is_install = " + is_install +
        ", is_available = " + is_available +
        "}";
    }
}
