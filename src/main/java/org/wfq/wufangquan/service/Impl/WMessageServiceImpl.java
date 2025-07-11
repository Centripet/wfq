package org.wfq.wufangquan.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.controller.requestFormation.messageHistoryRequest;
import org.wfq.wufangquan.entity.Messages;
import org.wfq.wufangquan.entity.regen.WMessage;
import org.wfq.wufangquan.entity.res.TMessage;
import org.wfq.wufangquan.mapper.WGroupMessageMapper;
import org.wfq.wufangquan.mapper.WMessageMapper;
import org.wfq.wufangquan.service.IWMessageService;

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
public class WMessageServiceImpl extends ServiceImpl<WMessageMapper, WMessage> implements IWMessageService {

    private final WMessageMapper messageMapper;

    @Override
    @Async
    public void newMessage(WMessage message) {

        String UUID = generateUUID();

        message.setMessage_id(UUID);
        message.setCreate_time(LocalDateTime.now());

        messageMapper.insert(message);

    }

    @Override
    public List<TMessage> sessionList(String userId) {
        return messageMapper.sessionList(userId);
    }

    @Override
    public Object messageHistory(String userId, messageHistoryRequest request) {
        return messageMapper.messageHistory(userId, request.uuid(), request.size(), (request.page() - 1) * request.size());
    }

}
