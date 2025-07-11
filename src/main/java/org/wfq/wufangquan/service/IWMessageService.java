package org.wfq.wufangquan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wfq.wufangquan.controller.requestFormation.messageHistoryRequest;
import org.wfq.wufangquan.entity.regen.WMessage;
import org.wfq.wufangquan.entity.res.TMessage;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-09
 */
public interface IWMessageService extends IService<WMessage> {

    void newMessage(WMessage message);

    List<TMessage> sessionList(String userId);

    Object messageHistory(String userId, messageHistoryRequest request);

}
