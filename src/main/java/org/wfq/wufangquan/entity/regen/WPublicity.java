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
 * @since 2025-06-21
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_publicity")
public class WPublicity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("pub_id")
    private String pub_id;

    private String file;

    public static final String PUB_ID = "pub_id";

    public static final String FILE = "file";

    @Override
    public String toString() {
        return "W_publicity{" +
        "pub_id = " + pub_id +
        ", file = " + file +
        "}";
    }
}
