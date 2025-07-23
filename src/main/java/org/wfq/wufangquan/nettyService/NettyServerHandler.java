package org.wfq.wufangquan.nettyService;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.wfq.wufangquan.entity.regen.*;
import org.wfq.wufangquan.entity.res.uploadSubmit;
import org.wfq.wufangquan.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

//    public static final Map<String, ChannelHandlerContext> userChannelMap = new ConcurrentHashMap<>();
//    private final Map<ChannelId, Boolean> authMap = new ConcurrentHashMap<>();
//    private final Map<ChannelId, String> userMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ChannelHandlerContext> userChannelMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<ChannelId, Boolean> authMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<ChannelId, String> userMap = new ConcurrentHashMap<>();

    private final JwtUtils jwtUtils;
    private final JwtService jwtService;
    private final IWMessageService messageService;
    private final IWGroupMessageService groupMessageService;
    private final IWFileService fileService;
    private final AliOssService ossService;
    private final ObjectMapper objectMapper;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
// {
//  "action": "auth",
//  "token": "xxx",
//  "refreshToken": "yyy"*
// }

// {
//  "action": "message",
//  "data": {
//    "messageID": "msg-123456",*
//    "senderID": "user-001",*
//    "receiverID": "user-002",
//    "content": "你好，这是测试消息",
//    "type": "p1",
//    "timestamp": "2025-05-18T10:30:00",*
//    "readStatus": 0,*
//    "resURL": "",
//    "fileName": "",
//    "favorites": "0"*
//  }
//}

//{
// "action": "auth",
// "token": ""
//}
//
//{
//  "action": "message",
//  "data": {
//    "receiver_id": "6c43d1a846944081acbdadf6b2cad897",
//    "content": "bbbbb",
//    "type": "2",
//    "link_url": "",
//    "file_url": "",
//    "file": {
//        "key": "uploads/general/6c43d1a846944081acbdadf6b2cad897/2025/07/17/46dd02e7cbb14d2f8c85a5b54c890ea8.jpg",
//        "suffix": ".jpg",
//        "origin_name": "图片.jpg",
//        "type": "1",
//        "title": "图片",
//        "info": "图片"
//    }
//  }
//}

        String msgJson = frame.text();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        JsonNode root = null;
        try {
            root = mapper.readTree(msgJson);
        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
            return;
        }

        String action = root.get("action").asText();
        JsonNode dataNode = root.get("data");

        ChannelId channelId = ctx.channel().id();
        boolean authenticated = authMap.getOrDefault(channelId, false);
        System.out.println("收到消息: " + msgJson);

        String sender_id = userMap.get(channelId);

        switch (action) {
            case "auth":
                if (!authenticated) {
                    String token = root.get("token").asText();

                    try {
                        if(!jwtService.isTokenValid(token)) {
                            ctx.writeAndFlush(new TextWebSocketFrame("AUTH_FAIL"));
                            ctx.close();
                        };

                        String user_id = jwtUtils.getUserIdFromToken(token);
                        System.out.println(user_id);
                        userChannelMap.put(user_id, ctx);
                        authMap.put(channelId, true);
                        userMap.put(channelId, user_id);
                        ctx.writeAndFlush(new TextWebSocketFrame("AUTH_SUCCESS"));
                        System.out.println("用户 " + user_id + " 已连接");

                    } catch (Exception e) {
                        ctx.writeAndFlush(new TextWebSocketFrame("AUTH_FAIL: " + e.getMessage()));
                        ctx.close();
                    }
                    return;
                }
                break;

            case "message":

                if (!authenticated) {
                    ctx.writeAndFlush(new TextWebSocketFrame("未认证，请先发送 AUTH:<token>"));
                    return;
                }

                System.out.println(sender_id);

                WMessage message = null;
                try {
                    message = mapper.treeToValue(dataNode, WMessage.class);
                    message.setCreate_time(LocalDateTime.now());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return;
                }
//                if (StringUtils.isBlank(message.getContent())) {
//                    ctx.writeAndFlush(new TextWebSocketFrame("消息内容不能为空"));
//                    return;
//                }

                message.setSender_id(sender_id);
                message.setIs_read(true);
                WFile file = null;
                if (2<=message.getType() && message.getType()<=5) {
                    uploadSubmit upload = null;
                    try {
                        upload = mapper.treeToValue(dataNode.get("file"), uploadSubmit.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return;
                    }
                    if (upload != null) {
                        if (ossService.fileExists(upload.getKey())) {
                            file = fileService.uploadSubmit(sender_id, upload, false);
                            String url = ossService.generateDownloadUrl(file.getFile_key(), AliOssService.EXPIRE_TIME_7_DAY);
                            message.setFile_url(url);
                            message.setFile_id(file.getFile_id());
                        } else {
                            ctx.writeAndFlush(new TextWebSocketFrame("文件上传失败"));
                            return;
                        }
                    }
                }

                String toUserId = message.getReceiver_id();
//                String messageReceive = mapper.writeValueAsString(dataNode);
                Map<String, Object> messageReceive = BeanUtil.beanToMap(message);
                messageReceive.put("file", file);

                ChannelHandlerContext targetCtx = userChannelMap.get(toUserId);
                if (targetCtx != null) {
                    try {
                        targetCtx.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(messageReceive)));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
                else {
//                    ctx.writeAndFlush(new TextWebSocketFrame("用户 " + toUserId + " 不在线"));
                    message.setIs_read(false);
                }

                try {
                    messageService.newMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    // 可以考虑通知发送方消息存储失败
                }

                break;

            case "group":

                if (!authenticated) {
                    ctx.writeAndFlush(new TextWebSocketFrame("未认证，请先发送 AUTH:<token>"));
                    return;
                }

                System.out.println(sender_id);

                WGroupMessage groupMessage = null;
                try {
                    groupMessage = mapper.treeToValue(dataNode, WGroupMessage.class);
                    groupMessage.setCreate_time(LocalDateTime.now());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
//                if (StringUtils.isBlank(groupMessage.getContent())) {
//                    ctx.writeAndFlush(new TextWebSocketFrame("消息内容不能为空"));
//                    return;
//                }

                groupMessage.setSender_id(sender_id);
                file = null;
                if (2<=groupMessage.getType() && groupMessage.getType()<=5) {
                    uploadSubmit upload = null;
                    try {
                        upload = mapper.treeToValue(dataNode.get("file"), uploadSubmit.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return;
                    }
                    if (upload != null) {
                        if (ossService.fileExists(upload.getKey())) {
                            file = fileService.uploadSubmit(sender_id, upload, false);
                            String url = ossService.generateDownloadUrl(file.getFile_key(), AliOssService.EXPIRE_TIME_7_DAY);
                            groupMessage.setFile_url(url);
                            groupMessage.setFile_id(file.getFile_id());
                        } else {
                            ctx.writeAndFlush(new TextWebSocketFrame("文件上传失败"));
                            return;
                        }
                    }
                }

                String group_id = groupMessage.getGroup_id();
//                String messageReceive = mapper.writeValueAsString(dataNode);
                List<SUser> members = groupMessageService.getGroupMembers(group_id);

                Map<String, Object> messageReceiveGroup = BeanUtil.beanToMap(groupMessage);
                messageReceiveGroup.put("file", file);

                try {
                    for (SUser member : members) {
                        ChannelHandlerContext targetCtxGroup = userChannelMap.get(member.getUser_id());
                        WGroupMessageRead gmr = WGroupMessageRead.builder()
                                .user_id(member.getUser_id())
                                .build();

                        if (targetCtxGroup != null) {
                            //  转发消息
                            targetCtxGroup.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(messageReceiveGroup)));
                            gmr.setIs_read(true);
                        } else {
                            // ctx.writeAndFlush(new TextWebSocketFrame("用户 " + toUserId + " 不在线"));
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

                break;
            default:
                ctx.writeAndFlush(new TextWebSocketFrame("未知操作类型"));
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ChannelId id = ctx.channel().id();
        String uid = userMap.get(id);
        System.err.println("异常发生，连接关闭：" + (uid != null ? "用户 " + uid : id.asShortText()));
        cause.printStackTrace();
        log.error(cause.getMessage());
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("新连接加入：" + ctx.channel().id());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ChannelId id = ctx.channel().id();
        authMap.remove(id);
        String uid = userMap.remove(id);
        if (uid != null) {
            userChannelMap.remove(uid);
            System.out.println("用户 " + uid + " 断开连接");
        } else {
            System.out.println("连接断开：" + id.asShortText());
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        ChannelId id = ctx.channel().id();
        authMap.remove(id);
        String uid = userMap.remove(id);
        if (uid != null) {
            userChannelMap.remove(uid);
        }
    }

    public ChannelHandlerContext getUserChannel(String userId) {
        return userChannelMap.get(userId);
    }

}