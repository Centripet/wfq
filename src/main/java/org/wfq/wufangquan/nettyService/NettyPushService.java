package org.wfq.wufangquan.nettyService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.wfq.wufangquan.controller.requestFormation.circleShareRequest;
import org.wfq.wufangquan.entity.regen.*;
import org.wfq.wufangquan.service.IWCircleService;
import org.wfq.wufangquan.service.IWGroupMessageService;
import org.wfq.wufangquan.service.IWMessageService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NettyPushService {

    private final NettyServerHandler nettyServerHandler;
    private final ObjectMapper objectMapper;
    private final IWMessageService messageService;
    private final IWGroupMessageService groupMessageService;
    private final IWCircleService wCircleService;

    public void circlePush(String user_id, circleShareRequest request) {

        String link = "/api/circle/circleDetail/"+request.circle_id();
        WCircle circle = wCircleService.circleInfo(user_id, request.circle_id());
        String content = circle.getContent();

        if (!request.user_ids().isEmpty()) {
            for (String id : request.user_ids()) {
                ChannelHandlerContext targetCtx = nettyServerHandler.getUserChannel(id);

                WMessage msg = WMessage.builder()
                        .content(content)
                        .sender_id(user_id)
                        .receiver_id(id)
                        .type(5)
                        .link_url(request.circle_id())
                        .is_read(true)
                        .build();

                String json = null;
                try {
                    json = objectMapper.writeValueAsString(msg);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                if (targetCtx != null && targetCtx.channel().isActive()) {
                    targetCtx.writeAndFlush(new TextWebSocketFrame(json));
                } else {
                    System.out.println("用户 " + user_id + " 不在线");
                    msg.setIs_read(false);
                }

                try {
                    messageService.newMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    // 可以考虑通知发送方消息存储失败
                }

            }

        }

        if (!request.group_ids().isEmpty()) {
            for (String id : request.group_ids()) {
                List<SUser> members = groupMessageService.getGroupMembers(id);
                try {
                    WGroupMessage groupMessage = WGroupMessage.builder()
                            .content(content)
                            .sender_id(user_id)
                            .group_id(id)
                            .type(5)
                            .link_url(request.circle_id())
                            .build();

                    for (SUser member : members) {
                        ChannelHandlerContext targetCtx = nettyServerHandler.getUserChannel(member.getUser_id());

                        WGroupMessageRead gmr = WGroupMessageRead.builder()
                                .user_id(member.getUser_id())
                                .is_read(true)
                                .build();

                        String json = null;
                        try {
                            json = objectMapper.writeValueAsString(groupMessage);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        if (targetCtx != null && targetCtx.channel().isActive()) {
                            targetCtx.writeAndFlush(new TextWebSocketFrame(json));
                        } else {
                            System.out.println("用户 " + member.getUser_id() + " 不在线");
                            gmr.setIs_read(false);
                        }

                        groupMessageService.addNewReadMessage(gmr);

                    }

                    groupMessageService.newGroupMessage(groupMessage);

                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    // 可以考虑通知发送方消息存储失败
                }
            }
        }






    }



    public void pushToUser(String userId, Object messageObj) {
        ChannelHandlerContext ctx = nettyServerHandler.getUserChannel(userId);
        if (ctx != null && ctx.channel().isActive()) {
            try {
                String json = objectMapper.writeValueAsString(messageObj);
                ctx.writeAndFlush(new TextWebSocketFrame(json));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        } else {
            System.out.println("用户 " + userId + " 不在线");
        }
    }

    public void pushToGroup(List<String> userIds, Object messageObj) {
        for (String userId : userIds) {
            pushToUser(userId, messageObj);
        }
    }

}