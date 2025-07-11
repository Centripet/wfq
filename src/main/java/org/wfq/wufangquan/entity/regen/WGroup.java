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
 * @since 2025-06-11
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_group")
public class WGroup implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("group_id")
    private String group_id;

    private String group_name;

    private String creator_id;

    private LocalDateTime create_time;

    private String group_icon;

    private String group_owner;

    public static final String GROUP_ID = "group_id";

    public static final String GROUP_NAME = "group_name";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_TIME = "create_time";

    public static final String GROUP_ICON = "group_icon";

    public static final String GROUP_OWNER = "group_owner";

    @Override
    public String toString() {
        return "W_group{" +
        "group_id = " + group_id +
        ", group_name = " + group_name +
        ", creator_id = " + creator_id +
        ", create_time = " + create_time +
        ", group_icon = " + group_icon +
        ", group_owner = " + group_owner +
        "}";
    }
}
