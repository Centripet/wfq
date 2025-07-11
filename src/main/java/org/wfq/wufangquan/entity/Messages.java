package org.wfq.wufangquan.entity;

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
 * @since 2025-05-18
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("Messages")
public class Messages implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("MessageID")
    private String messageID;

    private String senderID;

    private String receiverID;

    private String content;

    /**
     * p1: 文本, p2: 图片, p3: 视频，p4：文件
     */
    private String type;

    private LocalDateTime timestamp;

    private Boolean readStatus;

    /**
     * 图片视频文件的网址
     */
    private String resURL;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 是否被收藏
     */
    private String favorites;

    @Override
    public String toString() {
        return "Messages{" +
        "messageID = " + messageID +
        ", senderID = " + senderID +
        ", receiverID = " + receiverID +
        ", content = " + content +
        ", type = " + type +
        ", timestamp = " + timestamp +
        ", readStatus = " + readStatus +
        ", resURL = " + resURL +
        ", fileName = " + fileName +
        ", favorites = " + favorites +
        "}";
    }
}
