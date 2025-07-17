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
 * @since 2025-05-31
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_user")
public class SUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private String user_id;

    private String account;

    private String phone;

    private String nick_name;

    private Boolean account_status;

    private String organization;

    private String sex;

    private String icon;

    private LocalDateTime create_time;

    private String role;

    private String department;

    private String observer_url;

    private String learning_url;

    @TableField(exist = false)
    private String icon_url;

    @Override
    public String toString() {
        return "WUser{" +
        "user_id = " + user_id +
        ", account = " + account +
        ", phone = " + phone +
        ", nick_name = " + nick_name +
        ", account_status = " + account_status +
        ", organization = " + organization +
        ", sex = " + sex +
        ", icon = " + icon +
        ", create_time = " + create_time +
        ", role = " + role +
        ", role = " + department +
        ", observer_url = " + observer_url +
        ", learning_url = " + learning_url +
        ", icon_url = " + icon_url +
        "}";
    }
}
