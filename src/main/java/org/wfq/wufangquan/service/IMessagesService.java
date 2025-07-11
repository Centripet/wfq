package org.wfq.wufangquan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.wfq.wufangquan.controller.requestFormation.messageHistoryRequest;
import org.wfq.wufangquan.entity.Messages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-05-18
 */
public interface IMessagesService extends IService<Messages> {

    void newMessage(Messages messages);

    IPage<Messages> getMessagesByPage(String userId, messageHistoryRequest request);

    void setReadStatus(String messageId, boolean status);

    List<Map<String, Object>> getDepartmentsWithUsers();

}
