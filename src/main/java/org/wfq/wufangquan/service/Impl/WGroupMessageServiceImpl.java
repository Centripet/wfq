package org.wfq.wufangquan.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.controller.requestFormation.messageHistoryRequest;
import org.wfq.wufangquan.entity.regen.SUser;
import org.wfq.wufangquan.entity.regen.WGroupMessage;
import org.wfq.wufangquan.entity.regen.WGroupMessageRead;
import org.wfq.wufangquan.entity.regen.WUser;
import org.wfq.wufangquan.entity.res.TGroupMessage;
import org.wfq.wufangquan.mapper.WGroupMessageMapper;
import org.wfq.wufangquan.mapper.WGroupMessageReadMapper;
import org.wfq.wufangquan.service.IWGroupMessageService;

import java.time.LocalDateTime;
import java.util.List;

import static org.wfq.wufangquan.util.CaptchaGenerator.generateUUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-09
 */
@Service
@RequiredArgsConstructor
public class WGroupMessageServiceImpl extends ServiceImpl<WGroupMessageMapper, WGroupMessage> implements IWGroupMessageService {

    private final WGroupMessageMapper groupMessageMapper;
    private final WGroupMessageReadMapper groupMessageReadMapper;

    @Override
    @Async
    public void newGroupMessage(WGroupMessage groupMessage) {

        String UUID = generateUUID();

        groupMessage.setMessage_id(UUID);
        groupMessage.setCreate_time(LocalDateTime.now());

        groupMessageMapper.insert(groupMessage);

    }

    @Override
    public List<SUser> getGroupMembers(String groupId) {
        return groupMessageMapper.getGroupMembers(groupId);
    }

    @Override
    public List<TGroupMessage> sessionList(String userId) {
        return groupMessageMapper.sessionList(userId);
    }

    @Override
    @Async
    public void addNewReadMessage(WGroupMessageRead gmr) {
        String UUID = generateUUID();

        gmr.setMessage_id(UUID);
        groupMessageReadMapper.insert(gmr);
    }

    @Override
    public List<WGroupMessage> messageHistory(String userId, messageHistoryRequest request) {
        return groupMessageMapper.messageHistory(userId, request.uuid(), request.size(), (request.page() - 1) * request.size());
    }


}
