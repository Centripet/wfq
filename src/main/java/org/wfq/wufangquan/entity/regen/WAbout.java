package org.wfq.wufangquan.entity.regen;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

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
public class WAbout implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("about_name")
    private String about_name;

    private String about_value;

    public static final String ABOUT_NAME = "about_name";

    public static final String ABOUT_VALUE = "about_value";

    @Override
    public String toString() {
        return "W_about{" +
        "about_name = " + about_name +
        ", about_value = " + about_value +
        "}";
    }
}
