package org.wfq.wufangquan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wfq.wufangquan.controller.requestFormation.messageHistoryRequest;
import org.wfq.wufangquan.entity.regen.SUser;
import org.wfq.wufangquan.entity.regen.WGroupMessage;
import org.wfq.wufangquan.entity.regen.WGroupMessageRead;
import org.wfq.wufangquan.entity.regen.WUser;
import org.wfq.wufangquan.entity.res.TGroupMessage;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-09
 */
public interface IWGroupMessageService extends IService<WGroupMessage> {

    void newGroupMessage(WGroupMessage groupMessage);

    List<SUser> getGroupMembers(String groupId);

    List<TGroupMessage> sessionList(String userId);

    void addNewReadMessage(WGroupMessageRead gmr);

    List<Map<String, Object>> messageHistory(String userId, messageHistoryRequest request);
}
